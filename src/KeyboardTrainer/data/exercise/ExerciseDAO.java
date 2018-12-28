package KeyboardTrainer.data.exercise;

import KeyboardTrainer.data.DAO;
import KeyboardTrainer.data.JDBCDriverManager;
import KeyboardTrainer.data.KeyboardZone;
import KeyboardTrainer.language.Language;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.logging.Logger;

public class ExerciseDAO implements DAO<Exercise> {
    private static ExerciseDAO instance;
    private static JDBCDriverManager jdbcDriverManager;
    private static Logger log = Logger.getLogger(ExerciseDAO.class.getName());


    /**
     * Проходимся по зонам и добавляем к итоговому числу 10^(номер зоны)
     * Например, если задейстовована 4 зона, то 10^4 = 10000 прибавится к итогу
     * @param exercise
     * @return
     */
    int createNumberFromZones(Exercise exercise){
        int resultNumber = 0;
            Iterator<KeyboardZone> itr = exercise.getKeyboardZones().iterator();
            while (itr.hasNext()) {
                resultNumber += Math.pow(10,itr.next().getNumber() - 1);
            }
            return resultNumber;
    }

    /**
     * Ход обратный: берём число - входной параметр и пытаемся превратить его в Set.
     * Для этого проверяем больше оно чем число в степени номера зоны или равно ему. Если да, то добавляем эту зону в
     * результат и отнимаем данное число от входного параметра.
     * Например, если входной параметр 10001, то это означает что закодированы 4 и 1 зоны.
     * При прохождении проверки на 4 зону, окажется что число 10001 > 10^4 = 10000 и после входной параметр станет 10001 - 10000 = 1
     * @param number
     * @return HashSet
     */
    Set<KeyboardZone> createZonesFromNumber(int number){
        Set<KeyboardZone> resultSet = new HashSet<KeyboardZone>();
        for(int i = 4; i >= 0; i--){
            if(number >= Math.pow(10,i)){
                resultSet.add(KeyboardZone.byNumber(i+1));
                number = number - (int)Math.pow(10,i);
            }
        }
        return resultSet;
    }

    @Override
    public Exercise create(Exercise newEntity) {
        Exercise exercise;
        try {
            PreparedStatement statement = jdbcDriverManager.getConnection().prepareStatement(
                    "INSERT INTO exercise(name, level, text, keyboardZone, maxErrorCount, maxAveragePressingTime, language) VALUES(?, ?, ?, ?, ?, ?, ?)");
            statement.setObject(1, newEntity.getName());
            statement.setObject(2, newEntity.getLevel());
            statement.setObject(3, newEntity.getText());
            statement.setObject(4, createNumberFromZones(newEntity));
            statement.setObject(5, newEntity.getMaxErrorsCount());
            statement.setObject(6, newEntity.getMaxAveragePressingTime());
	        statement.setObject(7, newEntity.getLanguage().toString());
            statement.execute();

            return exercise = getByName(newEntity.getName());
        }
        catch (SQLException e) {
            log.info("Problem with Query");
            e.printStackTrace();
            return null;
        }
        catch (Exception e){
            log.info("Another problem");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Exercise get(int id) {
        Exercise exercise;
        try (PreparedStatement statement = jdbcDriverManager.getConnection().prepareStatement(
                "SELECT id, name, level, text, keyboardZone, maxErrorCount, maxAveragePressingTime, language FROM exercise WHERE id = ?")) {
            statement.setObject(1, id);
            statement.execute();

            ResultSet resultSet = statement.getResultSet();
            exercise = new ExerciseImpl(resultSet.getString("name"),
                                        resultSet.getInt("level"),
                                        resultSet.getString("text"),
                                        createZonesFromNumber(resultSet.getInt("keyboardZone")),
                                        resultSet.getInt("maxErrorCount"),
                                        resultSet.getLong("maxAveragePressingTime"),
                                        resultSet.getInt("id"),
                                        Language.valueOf(resultSet.getString("language")));
            return exercise;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Exercise getByName(String name) {
        Exercise exercise;
        try (PreparedStatement statement = jdbcDriverManager.getConnection().prepareStatement(
                "SELECT id, name, level, text, keyboardZone, maxErrorCount, maxAveragePressingTime, language FROM exercise WHERE name = ?")) {
            statement.setObject(1, name);
            statement.execute();

            ResultSet resultSet = statement.getResultSet();
	        exercise = new ExerciseImpl(resultSet.getString("name"),
	                                    resultSet.getInt("level"),
	                                    resultSet.getString("text"),
	                                    createZonesFromNumber(resultSet.getInt("keyboardZone")),
	                                    resultSet.getInt("maxErrorCount"),
	                                    resultSet.getLong("maxAveragePressingTime"),
	                                    resultSet.getInt("id"),
	                                    Language.valueOf(resultSet.getString("language")));
            return exercise;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void set(Exercise entity) {
        try (PreparedStatement statement = jdbcDriverManager.getConnection().prepareStatement(
                "UPDATE exercise SET  name = ?, level = ?, text = ?, keyboardZone = ?, maxErrorCount = ?, maxAveragePressingTime = ?, language = ? WHERE id = ?;")) {
            statement.setObject(1, entity.getName());
            statement.setObject(2, entity.getLevel());
            statement.setObject(3, entity.getText());
            statement.setObject(4, createNumberFromZones(entity));
            statement.setObject(5, entity.getMaxErrorsCount());
            statement.setObject(6, entity.getMaxAveragePressingTime());
            statement.setObject(7, entity.getId());
	        statement.setObject(8, entity.getLanguage().toString());
            statement.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try (PreparedStatement statement = jdbcDriverManager.getConnection().prepareStatement(
                "DELETE FROM exercise WHERE id = ?")) {
            statement.setObject(1, id);
            statement.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Was tested
     */
    @Override
    public List<Exercise> getAll() {
        try (Statement statement = jdbcDriverManager.getConnection().createStatement()) {
            List<Exercise> users = new ArrayList<Exercise>();
            ResultSet resultSet = statement.executeQuery("SELECT id, name, level, text, keyboardZone, maxErrorCount, maxAveragePressingTime, language FROM exercise");
            while (resultSet.next()) {
                users.add(new ExerciseImpl(resultSet.getString("name"),
                                           resultSet.getInt("level"),
                                           resultSet.getString("text"),
                                           createZonesFromNumber(resultSet.getInt("keyboardZone")),
                                           resultSet.getInt("maxErrorCount"),
                                           resultSet.getLong("maxAveragePressingTime"),
                                           resultSet.getInt("id"),
                                           Language.valueOf(resultSet.getString("language"))));
            }
            return users;
        }
        catch (SQLException e) {
            e.printStackTrace();
            // Если произошла ошибка - возвращаем пустую коллекцию
            return Collections.emptyList();
        }
    }

    public static ExerciseDAO getInstance() {
        if (instance == null) {
            instance = new ExerciseDAO();
        }
        try{
            jdbcDriverManager = JDBCDriverManager.getInstance();
        }
        catch (java.sql.SQLException exceptionObject) {
            log.info("Connection hasn't been created");
        }
        return instance;
    }
}

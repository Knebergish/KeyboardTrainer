package KeyboardTrainer.data.statistics;

import KeyboardTrainer.data.DAO;
import KeyboardTrainer.data.JDBCDriverManager;
import KeyboardTrainer.data.user.UserImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class StatisticsDAO implements DAO<Statistics> {
    private static StatisticsDAO instance;
    private static JDBCDriverManager jdbcDriverManager;
    private static Logger log = Logger.getLogger(StatisticsDAO.class.getName());

    /**
     * @author AliRakhmaev
     * @return Ввиду требования возвращения нового объекта из базы данных, потребовалось идти на хитрость:
     * 1) Он будет последним в списке, значит его можно забрать из getAll просто обратившись к  последнему элементу.
     * 2) Муторно? Предложите лучше.
     */
    @Override
    public Statistics create(Statistics newEntity) {
        Statistics statistics;
        try {
            PreparedStatement statement = jdbcDriverManager.getConnection().prepareStatement(
                    "INSERT INTO statistic (userId, exerciseId, totalTime, errorsCount, averagePressingTime, completedPercents)  VALUES(?, ?, ?, ?, ?, ?)");
            statement.setObject(1, newEntity.getUserId());
            statement.setObject(2, newEntity.getExerciseId());
            statement.setObject(2, newEntity.getTotalTime());
            statement.setObject(2, newEntity.getErrorsCount());
            statement.setObject(2, newEntity.getAveragePressingTime());
            statement.setObject(2, newEntity.getCompletePercents());
            statement.execute();

            return statistics = getAll().get(getAll().size()-1);
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
    public Statistics get(int id) {
        Statistics statistic;
        try (PreparedStatement statement = jdbcDriverManager.getConnection().prepareStatement(
                "SELECT id, userId, exerciseId, totalTime, errorsCount, averagePressingTime, completedPercents FROM statistic WHERE id = ?")) {
            statement.setObject(1, id);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            statistic = new StatisticsImpl(resultSet.getInt("id"), resultSet.getInt("userId"),
                    resultSet.getInt("exerciseId"), resultSet.getLong("totalTime"),
                    resultSet.getInt("errorsCount"), resultSet.getLong("averagePressingTime"),
                    resultSet.getInt("completedPercents"));
            return statistic;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void set(Statistics entity) {
        try (PreparedStatement statement = jdbcDriverManager.getConnection().prepareStatement(
                "UPDATE statistic SET  userId = ?, exerciseId = ?, totalTime = ?, errorsCount = ?," +
                        " averagePressingTime = ?, completedPercents = ? WHERE id = ?;")) {
            statement.setObject(1, entity.getUserId());
            statement.setObject(2, entity.getExerciseId());
            statement.setObject(3, entity.getTotalTime());
            statement.setObject(4, entity.getErrorsCount());
            statement.setObject(5, entity.getAveragePressingTime());
            statement.setObject(6, entity.getCompletePercents());
            statement.setObject(7, entity.getId());
            statement.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try (PreparedStatement statement = jdbcDriverManager.getConnection().prepareStatement(
                "DELETE FROM statistic WHERE id = ?")) {
            statement.setObject(1, id);
            statement.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Statistics> getAll() {
        try (Statement statement = jdbcDriverManager.getConnection().createStatement()) {
            List<Statistics> statistics = new ArrayList<Statistics>();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT id, userId, exerciseId, totalTime, errorsCount, averagePressingTime, completedPercents FROM statistic");
            while (resultSet.next()) {
                statistics.add(new StatisticsImpl(resultSet.getInt("id"), resultSet.getInt("userId"),
                        resultSet.getInt("exerciseId"), resultSet.getLong("totalTime"),
                        resultSet.getInt("errorsCount"), resultSet.getLong("averagePressingTime"),
                        resultSet.getInt("completedPercents")));
            }
            return statistics;
        }
        catch (SQLException e) {
            e.printStackTrace();
            // Если произошла ошибка - возвращаем пустую коллекцию
            return Collections.emptyList();
        }
    }

    public static StatisticsDAO getInstance() {
        if (instance == null) {
            instance = new StatisticsDAO();
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

package KeyboardTrainer.data;

import KeyboardTrainer.Main;
import KeyboardTrainer.data.user.UserImpl;
import org.sqlite.JDBC;

import java.sql.*;
import java.util.*;
import java.util.logging.Logger;


public class JDBCDriverManager {
    private static Logger log = Logger.getLogger(JDBCDriverManager.class.getName());


    // Константа, в которой хранится адрес подключения
    private static final String CON_STR = "jdbc:sqlite:keyboardTrainer.db";
    // Используем шаблон одиночка, чтобы не плодить множество
    // экземпляров класса DbHandler
    private static JDBCDriverManager instance = null;

    public static synchronized JDBCDriverManager getInstance() throws SQLException {
        if (instance == null)
            instance = new JDBCDriverManager();
        return instance;
    }

    // Объект, в котором будет храниться соединение с БД
    private Connection connection;

    private JDBCDriverManager() throws SQLException {
        // Регистрируем драйвер, с которым будем работать
        // в нашем случае Sqlite
        DriverManager.registerDriver(new JDBC());
        // Выполняем подключение к базе данных
        this.connection = DriverManager.getConnection(CON_STR);
    }


    public static  void main(String[] args) {

        JDBCDriverManager jdbcDriverManager = null;

        try {
            jdbcDriverManager = JDBCDriverManager.getInstance();
        } catch (java.sql.SQLException exceptionObject) {
            log.info("Huston!!!!");
        }

        jdbcDriverManager.getAllUsers();
    }




    public List<UserImpl> getAllUsers() {

        // Statement используется для того, чтобы выполнить sql-запрос
        try (Statement statement = this.connection.createStatement()) {
            // В данный список будем загружать наши продукты, полученные из БД
            List<UserImpl> users = new ArrayList<UserImpl>();
            // В resultSet будет храниться результат нашего запроса,
            // который выполняется командой statement.executeQuery()
            ResultSet resultSet = statement.executeQuery("SELECT id, login, password, isAdmin, isDisabled FROM user");
            // Проходимся по нашему resultSet и заносим данные в products
            while (resultSet.next()) {
                users.add(new UserImpl(resultSet.getInt("id"),
                        resultSet.getString("login"),
                        resultSet.getString("password"),
                        Boolean.getBoolean(resultSet.getString("isAdmin")),
                        Boolean.getBoolean(resultSet.getString("isDisabled"))));
            }

            System.out.println(users.toString());
            // Возвращаем наш список
            return users;

        } catch (SQLException e) {
            e.printStackTrace();
            // Если произошла ошибка - возвращаем пустую коллекцию
            return Collections.emptyList();
        }
    }


 /* Test

    // Добавление продукта в БД
    public void addProduct(UserImpl product) {
        // Создадим подготовленное выражение, чтобы избежать SQL-инъекций
        try (PreparedStatement statement = this.connection.prepareStatement(
                "INSERT INTO Products(`good`, `price`, `category_name`) " +
                        "VALUES(?, ?, ?)")) {
            statement.setObject(1, product.good);
            statement.setObject(2, product.price);
            statement.setObject(3, product.category_name);
            // Выполняем запрос
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Удаление продукта по id
    public void deleteProduct(int id) {
        try (PreparedStatement statement = this.connection.prepareStatement(
                "DELETE FROM Products WHERE id = ?")) {
            statement.setObject(1, id);
            // Выполняем запрос
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    Test */
}

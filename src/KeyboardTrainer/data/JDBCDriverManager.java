package KeyboardTrainer.data;

import KeyboardTrainer.Main;
import KeyboardTrainer.data.user.User;
import KeyboardTrainer.data.user.UserDAO;
import KeyboardTrainer.data.user.UserImpl;
import org.sqlite.JDBC;

import java.sql.*;
import java.util.*;
import java.util.logging.Logger;


public class JDBCDriverManager {
    private static Logger log = Logger.getLogger(JDBCDriverManager.class.getName());
    private Connection connection;
    // Константа, в которой хранится адрес подключения
    private static final String CON_STR = "jdbc:sqlite:keyboardTrainer.db";
    private static JDBCDriverManager instance = null;

    public static synchronized JDBCDriverManager getInstance() throws SQLException {
        if (instance == null)
            instance = new JDBCDriverManager();
        return instance;
    }

    private JDBCDriverManager() throws SQLException {
        // Регистрируем драйвер, с которым будем работать
        // в нашем случае Sqlite
        DriverManager.registerDriver(new JDBC());
        // Выполняем подключение к базе данных
        this.connection = DriverManager.getConnection(CON_STR);
    }

    /**
     * Necessary for providing to external classes this connection
     * @return connection
     */
    public Connection getConnection() {
        return connection;
    }

    public static  void main(String[] args) {

        JDBCDriverManager jdbcDriverManager = null;

        try {
            jdbcDriverManager = JDBCDriverManager.getInstance();
        } catch (java.sql.SQLException exceptionObject) {
            log.info("Huston!!!!");
        }

        UserDAO userDAO = UserDAO.getInstance();

        User user = new UserImpl(1, "UpdateTest", "UpdateTest", false, false);

        List<User> users = userDAO.getAll();

        for (User NewUser: users
             ) {
            UserImpl user1 = (UserImpl)NewUser;
            System.out.println(user1.toString());
        }

     //   userDAO.delete(11);
        //  UserImpl resultUser;

     //   resultUser = (UserImpl)userDAO.get(1);

      //  System.out.print(resultUser.toString());
    }


}

package KeyboardTrainer.data.user;


import KeyboardTrainer.data.DAO;
import KeyboardTrainer.data.JDBCDriverManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;


public class UserDAO implements DAO<User> {
	private static UserDAO instance;
	private static JDBCDriverManager jdbcDriverManager;
	private static Logger log = Logger.getLogger(UserDAO.class.getName());


	/**
	 * Was tested
	 */
	@Override
	public User create(User newEntity) {
		User user;
		// Создадим подготовленное выражение, чтобы избежать SQL-инъекций
		try {
			PreparedStatement statement = jdbcDriverManager.getConnection().prepareStatement("INSERT INTO user(login, password, isAdmin, isDisabled) VALUES(?, ?, ?, ?)");
			statement.setObject(1, newEntity.getLogin());
			statement.setObject(2, newEntity.getPassword());
			statement.setObject(3, newEntity.isAdmin() ? 1 : 0);
			statement.setObject(4, newEntity.isDisabled() ? 1 : 0);
			statement.execute();

			return user = getByLogin(newEntity.getLogin());
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

	/**
	 * Was tested
	 */
	@Override
	public User get(int id) {
		User user;
		try (PreparedStatement statement = jdbcDriverManager.getConnection().prepareStatement(
				"SELECT id, login, password, isAdmin, isDisabled FROM user WHERE id = ?")) {
			statement.setObject(1, id);
			statement.execute();
			ResultSet resultSet = statement.getResultSet();
			user = new UserImpl(resultSet.getInt("id"), resultSet.getString("login"),
						  		resultSet.getString("password"), resultSet.getBoolean("isAdmin"),
							    resultSet.getBoolean("isDisabled"));
			return user;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Was tested
	 */
	public User getByLogin(String login) {
		User user;
		try (PreparedStatement statement = jdbcDriverManager.getConnection().prepareStatement(
				"SELECT id, login, password, isAdmin, isDisabled FROM user WHERE login = ?")) {
			statement.setObject(1, login);
			statement.execute();
			ResultSet resultSet = statement.getResultSet();
			user = new UserImpl(resultSet.getInt("id"), resultSet.getString("login"),
					resultSet.getString("password"), resultSet.getBoolean("isAdmin"),
					resultSet.getBoolean("isDisabled"));
			return user;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}


	/**
	 * Was tested
	 */
	@Override
	public void set(User entity) {
		try (PreparedStatement statement = jdbcDriverManager.getConnection().prepareStatement(
				"UPDATE user SET  login = ?, password = ?, isAdmin = ?, isDisabled = ? WHERE id = ?;")) {
			statement.setObject(1, entity.getLogin());
			statement.setObject(2, entity.getPassword());
			statement.setObject(3, entity.isAdmin() ? 1 : 0);
			statement.setObject(4, entity.isDisabled() ? 1 : 0);
			statement.setObject(5, entity.getId());
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
	public void delete(int id) {
		try (PreparedStatement statement = jdbcDriverManager.getConnection().prepareStatement(
				"DELETE FROM user WHERE id = ?")) {
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
	public List<User> getAll() {
		try (Statement statement = jdbcDriverManager.getConnection().createStatement()) {
			List<User> users = new ArrayList<User>();
			ResultSet resultSet = statement.executeQuery("SELECT id, login, password, isAdmin, isDisabled FROM user");
			while (resultSet.next()) {
				users.add(new UserImpl(resultSet.getInt("id"),
						resultSet.getString("login"),
						resultSet.getString("password"),
						               resultSet.getBoolean("isAdmin"),
						               resultSet.getBoolean("isDisabled")));
			}
			return users;
		}
		catch (SQLException e) {
			e.printStackTrace();
			// Если произошла ошибка - возвращаем пустую коллекцию
			return Collections.emptyList();
		}
	}
	
	/* public List<String> getByMask(String mask) {
		List<String> logins = new ArrayList<String>();
		try (PreparedStatement statement = jdbcDriverManager.getConnection().prepareStatement(
				"SELECT FROM user WHERE login LIKE '?%'")) {
			statement.setObject(1, mask);
			statement.execute();
			ResultSet resultSet = statement.getResultSet();
			while (resultSet.next()) {
				logins.add(resultSet.getString("login"));
			}
			return logins;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	} */

	public static UserDAO getInstance() {
		if (instance == null) {
			instance = new UserDAO();
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

package KeyboardTrainer.data.user;


import KeyboardTrainer.data.DAO;

import java.util.List;


public class UserDAO implements DAO<User> {
	private static UserDAO instance;
	
	@Override
	public User create(User newEntity) {
		return null;
	}
	
	@Override
	public User get(int id) {
		return null;
	}
	
	@Override
	public void set(User entity) {
	
	}
	
	@Override
	public void delete(int id) {
	
	}
	
	@Override
	public List<User> getAll() {
		return null;
	}
	
	public User getUserByName(String name) {
		return null;
	}
	
	public static UserDAO getInstance() {
		if (instance == null) {
			instance = new UserDAO();
		}
		return instance;
	}
}

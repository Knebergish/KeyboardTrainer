package KeyboardTrainer.data.user;


import KeyboardTrainer.data.Entity;


public interface User extends Entity {
	String getLogin();
	
	String getPassword();
	
	boolean isAdmin();
	
	boolean getDisabled();
}

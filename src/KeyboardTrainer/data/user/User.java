package KeyboardTrainer.data.user;


import KeyboardTrainer.data.Entity;


public interface User extends Entity {
	String getPassword(); // или getPasswordHash()

	String getLogin(); // разрешим менять?

	boolean isAdmin();
}

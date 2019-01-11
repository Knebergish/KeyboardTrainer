package KeyboardTrainer;


import KeyboardTrainer.data.user.User;


public final class Session {
	private static User loggedUser = null;
	
	private Session() {
	}
	
	public static User getLoggedUser() {
		return loggedUser;
	}
	
	static void setLoggedUser(User loggedUser) {
		Session.loggedUser = loggedUser;
	}
}

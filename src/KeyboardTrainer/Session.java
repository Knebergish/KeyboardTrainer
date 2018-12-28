package KeyboardTrainer;


import KeyboardTrainer.data.user.User;


public class Session {
	private static User loggedUser;
	
	public static User getLoggedUser() {
		return loggedUser;
	}
	
	static void setLoggedUser(User loggedUser) {
		Session.loggedUser = loggedUser;
	}
}

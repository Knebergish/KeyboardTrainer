package KeyboardTrainer.data.user;


public class UserImpl implements User {
	private final int     id;
	private final String  login;
	private final String  password;
	private final boolean isAdmin;
	private final boolean isDisabled;
	
	public UserImpl(int id, String login, String password, boolean isAdmin, boolean isDisabled) {
		this.id = id;
		this.login = login;
		this.password = password;
		this.isAdmin = isAdmin;
		this.isDisabled = isDisabled;
	}


    /**
     * Constructor without parametrs for DAO functions. Add default values because final statement demands initialisation.
     * @author AliRakhmaev
     */
    public UserImpl() {
        id = 1;
        login = "null";
        password = "null";
        isAdmin = false;
        isDisabled = false;
    }

    @Override
	public int getId() {
		return id;
	}
	
	@Override
	public String getLogin() {
		return login;
	}
	
	@Override
	public String getPassword() {
		return password;
	}
	
	@Override
	public boolean isAdmin() {
		return isAdmin;
	}
	
	public boolean isDisabled() {
		return isDisabled;
	}

	@Override
	public String toString() {
		return "UserImpl{" +
				"id=" + id +
				", login='" + login + '\'' +
				", password='" + password + '\'' +
				", isAdmin=" + isAdmin +
				", isDisabled=" + isDisabled +
				'}';
	}
}

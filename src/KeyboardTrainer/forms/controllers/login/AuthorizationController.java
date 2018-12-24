package KeyboardTrainer.forms.controllers.login;


import KeyboardTrainer.data.user.User;
import KeyboardTrainer.data.user.UserDAO;
import KeyboardTrainer.forms.controllers.ActionHandler;
import KeyboardTrainer.forms.general.AlertFormManager;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.function.Consumer;


public class AuthorizationController implements LoginController {
	public TextField     loginTextField;
	public PasswordField passwordTextField;
	public Button        loginButton;
	public Button        registerButton;
	
	private Consumer<User> loginUser;
	
	public void init(Consumer<User> loginUser, ActionHandler registerHandler) {
		this.loginUser = loginUser;
		loginButton.setOnAction(event -> login());
		registerButton.setOnAction(event -> registerHandler.handle());
	}
	
	private void login() {
		String login    = loginTextField.getText();
		String password = passwordTextField.getText();
		
		User userByLogin = UserDAO.getInstance().getByLogin(login);
		if (userByLogin == null || !userByLogin.getPassword().equals(password)) {
			AlertFormManager.showAlert(Alert.AlertType.ERROR, "Неверные данные",
			                           "Пользователь с такими логином и паролем отсутствует.",
			                           "Попробуйте повторить ввод данных.", null);
		} else {
			loginUser.accept(userByLogin);
		}
	}
}

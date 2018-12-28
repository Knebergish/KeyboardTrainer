package KeyboardTrainer.forms.controllers.login;


import KeyboardTrainer.data.user.User;
import KeyboardTrainer.data.user.UserDAO;
import KeyboardTrainer.forms.controllers.ActionHandler;
import KeyboardTrainer.forms.general.AlertFormManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.function.Consumer;


public class AuthorizationController implements LoginController {
	@FXML
	private TextField     loginTextField;
	@FXML
	private PasswordField passwordTextField;
	@FXML
	private Button        loginButton;
	@FXML
	private Button        registerButton;
	
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
		} else if (userByLogin.isDisabled()) {
			AlertFormManager.showAlert(Alert.AlertType.ERROR, "Ты не пройдёшь!",
			                           "Ваш аккаунт заблокирован.",
			                           "Для разблокировки вашего аккаунта обратитесь к администратору.", null);
		} else {
			loginUser.accept(userByLogin);
		}
	}
}

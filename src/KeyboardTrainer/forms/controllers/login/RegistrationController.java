package KeyboardTrainer.forms.controllers.login;


import KeyboardTrainer.data.user.User;
import KeyboardTrainer.data.user.UserDAO;
import KeyboardTrainer.data.user.UserImpl;
import KeyboardTrainer.forms.controllers.ActionHandler;
import KeyboardTrainer.forms.general.AlertFormManager;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.function.Consumer;


public class RegistrationController implements LoginController {
	public  TextField      loginTextField;
	public  PasswordField  primoPasswordField;
	public  PasswordField  secundoPasswordField;
	public  Button         registerButton;
	public  Button         cancelButton;
	private Consumer<User> loginUser;
	
	public void init(Consumer<User> loginUser, ActionHandler cancelHandler) {
		this.loginUser = loginUser;
		registerButton.setOnAction(event -> register());
		cancelButton.setOnAction(event -> cancelHandler.handle());
	}
	
	private void register() {
		User userByLogin = UserDAO.getInstance().getByLogin(loginTextField.getText());
		if (userByLogin != null) {
			AlertFormManager.showAlert(Alert.AlertType.ERROR, "Ошибка",
			                           "Пользователь с таким именем уже существует.",
			                           "Введите другое имя пользователя.", null);
		} else if (!primoPasswordField.getText().equals(secundoPasswordField.getText())) {
			AlertFormManager.showAlert(Alert.AlertType.ERROR, "Ошибка",
			                           "Введённые пароли не совпадают.",
			                           "Повторите ввод пароля.", null);
		} else {
			User user = new UserImpl(-1, loginTextField.getText(), primoPasswordField.getText(),
			                         false, false);
			user = UserDAO.getInstance().create(user);
			loginUser.accept(user);
		}
	}
}

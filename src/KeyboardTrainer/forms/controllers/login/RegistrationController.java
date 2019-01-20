package KeyboardTrainer.forms.controllers.login;


import KeyboardTrainer.data.user.User;
import KeyboardTrainer.data.user.UserDAO;
import KeyboardTrainer.data.user.UserImpl;
import KeyboardTrainer.forms.common.AlertFormManager;
import KeyboardTrainer.forms.common.Validator;
import KeyboardTrainer.forms.common.Validator.Checker;
import KeyboardTrainer.forms.controllers.ActionHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.List;
import java.util.function.Consumer;


public class RegistrationController implements LoginController {
	@FXML
	private TextField     loginTextField;
	@FXML
	private PasswordField primoPasswordField;
	@FXML
	private PasswordField secundoPasswordField;
	@FXML
	private Button        registerButton;
	@FXML
	private Button        cancelButton;
	
	private ActionHandler cancelHandler;
	private Validator      validator;
	
	@Override
	public void init(Consumer<User> loginUser, ActionHandler cancelHandler) {
		this.cancelHandler = cancelHandler;
		
		registerButton.setOnAction(event -> register());
		cancelButton.setOnAction(event -> cancelHandler.handle());
		
		validator = new Validator(
				List.of(new Checker(() -> loginTextField.getText().chars().mapToObj(value -> (char) value)
				                                        .allMatch(Character::isLetterOrDigit),
				                    "Логин может содержать только буквы и цифры.",
				                    "Удалите из логина недопустимые символы."),
				        new Checker(() -> loginTextField.getText().length() >= 3,
				                    "Слишком короткий логин.",
				                    "Длина логина должна быть не менее 3-х символов."),
				        new Checker(() -> UserDAO.getInstance().getByLogin(loginTextField.getText()) == null,
				                    "Пользователь с таким именем уже существует.",
				                    "Введите другое имя пользователя."),
				        new Checker(() -> primoPasswordField.getText().length() >= 5,
				                    "Слишком короткий пароль.",
				                    "Длина пароля должна быть не менее 5-и символов."),
				        new Checker(() -> primoPasswordField.getText().equals(secundoPasswordField.getText()),
				                    "Введённые пароли не совпадают.",
				                    "Повторите ввод пароля.")
				       ));
		
	}
	
	private void register() {
		if (validator.validateOrAlert()) {
			User newUser = new UserImpl(-1, loginTextField.getText(), primoPasswordField.getText(),
			                            false, false);
			UserDAO.getInstance().create(newUser);
			cancelHandler.handle();
			AlertFormManager.showAlert(Alert.AlertType.INFORMATION,
			                           "Успешная регистрация",
			                           "Регистрация успешно завершена.",
			                           "Теперь вы можете войти в программу с вашими данными.",
			                           null);
		}
	}
}

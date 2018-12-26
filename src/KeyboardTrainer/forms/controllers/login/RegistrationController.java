package KeyboardTrainer.forms.controllers.login;


import KeyboardTrainer.data.user.User;
import KeyboardTrainer.data.user.UserDAO;
import KeyboardTrainer.data.user.UserImpl;
import KeyboardTrainer.forms.Validator;
import KeyboardTrainer.forms.Validator.Checker;
import KeyboardTrainer.forms.controllers.ActionHandler;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.List;
import java.util.function.Consumer;


public class RegistrationController implements LoginController {
	public TextField     loginTextField;
	public PasswordField primoPasswordField;
	public PasswordField secundoPasswordField;
	public Button        registerButton;
	public Button        cancelButton;
	
	private Consumer<User> loginUser;
	private Validator      validator;
	
	
	public void init(Consumer<User> loginUser, ActionHandler cancelHandler) {
		this.loginUser = loginUser;
		
		registerButton.setOnAction(event -> register());
		cancelButton.setOnAction(event -> cancelHandler.handle());
		
		validator = new Validator(
				List.of(new Checker(() -> UserDAO.getInstance().getByLogin(loginTextField.getText()) == null,
				                    "Пользователь с таким именем уже существует.",
				                    "Введите другое имя пользователя."),
				        new Checker(() -> loginTextField.getText().length() >= 3,
				                    "Слишком короткий логин.",
				                    "Длина логина должна быть не менее 3-х символов."),
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
			newUser = UserDAO.getInstance().create(newUser);
			loginUser.accept(newUser);
		}
	}
}

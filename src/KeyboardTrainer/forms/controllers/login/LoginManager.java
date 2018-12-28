package KeyboardTrainer.forms.controllers.login;


import KeyboardTrainer.data.user.User;
import KeyboardTrainer.forms.controllers.ActionHandler;
import KeyboardTrainer.forms.general.fxml.FXMLManager;
import KeyboardTrainer.forms.general.fxml.RootWithController;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.function.Consumer;


public class LoginManager {
	private final Consumer<User> loginUser;
	
	public LoginManager(Consumer<User> loginUser) {
		this.loginUser = loginUser;
	}
	
	public void startLogin() {
		authorize();
	}
	
	private void authorize() {
		showForm("Authorization", "Авторизация", this::register, event -> System.exit(0));
	}
	
	private void register() {
		showForm("Registration", "Регистрация", this::authorize, event -> authorize());
	}
	
	private void showForm(String formName, String title, ActionHandler otherActionHandler,
	                      EventHandler<WindowEvent> onCloseRequest) {
		RootWithController<LoginController> load  = FXMLManager.load(formName);
		Stage                               stage = FXMLManager.createStage(load.getRoot(), title);
		
		load.getController().init(user -> {
			loginUser.accept(user);
			stage.close();
		}, () -> {
			otherActionHandler.handle();
			stage.close();
		});
		
		stage.setOnCloseRequest(onCloseRequest);
		stage.show();
	}
}

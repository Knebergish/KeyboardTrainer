package KeyboardTrainer;


import KeyboardTrainer.data.user.User;
import KeyboardTrainer.forms.controllers.login.LoginManager;
import KeyboardTrainer.forms.general.AdminForm;
import KeyboardTrainer.forms.general.GeneralForm;
import KeyboardTrainer.forms.general.PupilForm;
import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		LoginManager loginManager = new LoginManager(this::showGeneralForm);
		loginManager.startLogin();
	}
	
	private void showGeneralForm(User user) {
		Session.setLoggedUser(user);
		GeneralForm generalForm = user.isAdmin() ? new AdminForm() : new PupilForm();
		generalForm.show();
	}
}

package KeyboardTrainer;


import KeyboardTrainer.forms.general.AdminForm;
import KeyboardTrainer.forms.general.GeneralForm;
import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		GeneralForm generalForm;
//		generalForm = new PupilForm();
		generalForm = new AdminForm();
		generalForm.show();
	}
}

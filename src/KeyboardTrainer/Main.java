package KeyboardTrainer;


import KeyboardTrainer.forms.controllers.exercise_player.ExercisePlayerController;
import KeyboardTrainer.forms.general.GeneralForm;
import KeyboardTrainer.forms.general.fxml.FXMLManager;
import KeyboardTrainer.forms.general.fxml.RootWithController;
import KeyboardTrainer.forms.general.menu.ChangeContentMenuButton;
import KeyboardTrainer.forms.general.menu.CustomActionMenuButton;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.stage.Stage;


public class Main extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		GeneralForm generalForm = new GeneralForm();
		generalForm.setTitle("Лучший клавиатурный тренажёр");
		generalForm.addMenuButton(
				new CustomActionMenuButton("Продолжить",
				                           () -> {
					                           RootWithController<ExercisePlayerController> rootWithController =
							                           FXMLManager.load(
									                           "KeyboardTrainer/forms/layouts/ExercisePlayer.fxml");
					
					                           Stage stage = FXMLManager.createStage(
							                           rootWithController.getRoot(),
							                           "Ваше следующее упражнение",
							                           700, 400);
					
					                           rootWithController.getController().init();
					                           stage.setResizable(false);
					                           stage.show();
				                           }));
		generalForm.addMenuButton(new ChangeContentMenuButton("Выбор упражнения",
		                                                      "KeyboardTrainer/forms/layouts/ExerciseChooser.fxml"));
		generalForm.addMenuButton(new CustomActionMenuButton("Статистика",
		                                                     () -> {
			                                                     Alert alert = new Alert(Alert.AlertType.WARNING);
			                                                     alert.setTitle("Внимание");
			                                                     alert.setHeaderText("Ведутся технические работы");
			                                                     alert.setContentText(
					                                                     "Данная форма пока недоступна, вернитесь позже.");
			                                                     alert.show();
		                                                     }));
		generalForm.addMenuButton(new ChangeContentMenuButton("Справка",
		                                                      "KeyboardTrainer/forms/layouts/Logo.fxml"));
		
		generalForm.show();
	}
}

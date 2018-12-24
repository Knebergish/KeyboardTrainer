package KeyboardTrainer.forms.general;


import KeyboardTrainer.forms.controllers.exercise_player.ExercisePlayerController;
import KeyboardTrainer.forms.general.fxml.FXMLManager;
import KeyboardTrainer.forms.general.fxml.RootWithController;
import KeyboardTrainer.forms.general.menu.ChangeContentMenuButton;
import KeyboardTrainer.forms.general.menu.CustomActionMenuButton;
import javafx.scene.control.Alert;
import javafx.stage.Stage;


public class PupilForm extends GeneralForm {
	public PupilForm() {
		setTitle("Лучший клавиатурный тренажёр");
		addMenuButton(
				new CustomActionMenuButton("Продолжить",
				                           () -> {
					                           RootWithController<ExercisePlayerController> rootWithController =
							                           FXMLManager.load(
									                           "KeyboardTrainer/forms/layouts/ExercisePlayer.fxml");
					
					                           Stage stage = FXMLManager.createStage(
							                           rootWithController.getRoot(),
							                           "Ваше следующее упражнение");
					
					                           rootWithController.getController().init();
					                           stage.setResizable(false);
					                           stage.show();
				                           }));
		addMenuButton(new ChangeContentMenuButton("Выбор упражнения",
		                                          "KeyboardTrainer/forms/layouts/ExerciseChooser.fxml"));
		addMenuButton(new CustomActionMenuButton("Статистика",
		                                         () -> {
			                                         Alert alert = new Alert(Alert.AlertType.WARNING);
			                                         alert.setTitle("Внимание");
			                                         alert.setHeaderText("Ведутся технические работы");
			                                         alert.setContentText(
					                                         "Данная форма пока недоступна, вернитесь позже.");
			                                         alert.show();
		                                         }));
		addMenuButton(new ChangeContentMenuButton("Справка",
		                                          "KeyboardTrainer/forms/layouts/Logo.fxml"));
	}
}

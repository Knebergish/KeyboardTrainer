package KeyboardTrainer.forms.general;


import KeyboardTrainer.data.exercise.ExerciseImpl;
import KeyboardTrainer.forms.controllers.exercise.player.ExercisePlayerController;
import KeyboardTrainer.forms.controllers.exercise.tree.ExerciseChooserController;
import KeyboardTrainer.forms.general.fxml.FXMLManager;
import KeyboardTrainer.forms.general.fxml.RootWithController;
import KeyboardTrainer.forms.general.menu.ChangeContentMenuButton;
import KeyboardTrainer.forms.general.menu.CustomActionMenuButton;
import KeyboardTrainer.language.Language;
import javafx.scene.control.Alert;
import javafx.stage.Stage;


public class PupilForm extends GeneralForm {
	public PupilForm() {
		setTitle("Лучший клавиатурный тренажёр");
		addMenuButton(
				new CustomActionMenuButton("Продолжить",
				                           () -> {
					                           RootWithController<ExercisePlayerController> rootWithController =
							                           FXMLManager.load("ExercisePlayer");
					
					                           Stage stage = FXMLManager.createStage(
							                           rootWithController.getRoot(),
							                           "Ваше следующее упражнение");
					
					                           rootWithController.getController().init(
							                           //TODO: искать первое непройденное
							                           new ExerciseImpl("TestExercise",
							                                            1,
							                                            "12345",
							                                            null,
							                                            12,
							                                            1000,
							                                            -1,
							                                            Language.RUSSIAN));
					                           stage.setResizable(false);
					                           stage.show();
				                           }));
		addMenuButton(new ChangeContentMenuButton("Выбор упражнения", "ExerciseGeneral",
		                                          new ExerciseChooserController()));
		addMenuButton(new CustomActionMenuButton("Статистика",
		                                         () -> {
			                                         Alert alert = new Alert(Alert.AlertType.WARNING);
			                                         alert.setTitle("Внимание");
			                                         alert.setHeaderText("Ведутся технические работы");
			                                         alert.setContentText(
					                                         "Данная форма пока недоступна, вернитесь позже.");
			                                         alert.show();
		                                         }));
		addMenuButton(new ChangeContentMenuButton("Справка", "Logo"));
	}
}

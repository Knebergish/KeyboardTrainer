package KeyboardTrainer.forms.controllers.exercise.tree;


import KeyboardTrainer.forms.controllers.exercise.player.ExercisePlayerController;
import KeyboardTrainer.forms.general.fxml.FXMLManager;
import KeyboardTrainer.forms.general.fxml.RootWithController;
import javafx.stage.Stage;


public class ExerciseChooserController extends AbstractExerciseTreeController {
	@Override
	public void init() {
		super.init();
		
		primoButton.setText("Контрольное упражнение");
		secundoButton.setText("Статистика");
		tertioButton.setText("Начать");
		
		tertioButton.setOnAction(event -> startExercise());
	}
	
	private void startExercise() {
		if (selectedExercise == null) {
			return;
		}
		
		RootWithController<ExercisePlayerController> rootWithController = FXMLManager.load("ExercisePlayer");
		
		Stage stage = FXMLManager.createStage(rootWithController.getRoot(), selectedExercise.getName());
		rootWithController.getController().init(selectedExercise);
		stage.show();
	}
}

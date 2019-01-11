package KeyboardTrainer.forms.controllers.exercise.tree;


import KeyboardTrainer.data.exercise.Exercise;
import KeyboardTrainer.data.exercise.ExerciseDAO;
import KeyboardTrainer.data.exercise.ExerciseImpl;
import KeyboardTrainer.forms.common.fxml.FXMLManager;
import KeyboardTrainer.forms.common.fxml.RootWithController;
import KeyboardTrainer.forms.controllers.exercise.ExerciseSettingsController;
import KeyboardTrainer.language.Language;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Set;


public class ExerciseManagerController extends AbstractExerciseTreeController {
	@Override
	public void init() {
		super.init();
		
		primoButton.setText("Добавить");
		secundoButton.setText("Удалить");
		tertioButton.setText("Редактировать");
		
		primoButton.setOnAction(event -> {
			Exercise newExercise = readExerciseParameters(new ExerciseImpl("",
			                                                               0,
			                                                               "",
			                                                               Set.of(),
			                                                               0,
			                                                               0,
			                                                               -1,
			                                                               Language.RUSSIAN));
			if (newExercise == null) {
				return;
			}
			
			newExercise = ExerciseDAO.getInstance().create(newExercise);
			exercisesTree.addExercise(newExercise);
			exercisesTree.selectExercise(newExercise);
			selectedExercise = newExercise;
		});
		secundoButton.setOnAction(event -> {
			ExerciseDAO.getInstance().delete(selectedExercise.getId());
			exercisesTree.removeExercise(selectedExercise);
		});
		tertioButton.setOnAction(event -> {
			if (selectedExercise == null) {
				return;
			}
			Exercise exercise = readExerciseParameters(selectedExercise);
			if (exercise == null) {
				return;
			}
			ExerciseDAO.getInstance().set(exercise);
			exercisesTree.getRoot().getChildren()
			             .filtered(item -> item.getValue().isExercise()
			                               && item.getValue().getExercise() == selectedExercise);
			
			exercisesTree.removeExercise(selectedExercise);
			exercisesTree.addExercise(exercise);
			exercisesTree.selectExercise(exercise);
			selectedExercise = exercise;
		});
	}
	
	private Exercise readExerciseParameters(Exercise exercise) {
		RootWithController<ExerciseSettingsController> load = FXMLManager.load("ExerciseSettings");
		
		Stage stage = FXMLManager.createStage(load.getRoot(), "Добавление упражнения");
		stage.sizeToScene();
		stage.initModality(Modality.APPLICATION_MODAL);
		load.getController().init(exercise);
		stage.showAndWait();
		
		return load.getController().getNewExercise();
	}
}

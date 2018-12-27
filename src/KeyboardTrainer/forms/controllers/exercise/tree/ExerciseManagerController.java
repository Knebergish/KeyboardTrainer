package KeyboardTrainer.forms.controllers.exercise.tree;


import KeyboardTrainer.data.exercise.Exercise;
import KeyboardTrainer.data.exercise.ExerciseDAO;
import KeyboardTrainer.data.exercise.ExerciseImpl;
import KeyboardTrainer.forms.controllers.exercise.ExerciseSettingsController;
import KeyboardTrainer.forms.general.fxml.FXMLManager;
import KeyboardTrainer.forms.general.fxml.RootWithController;
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
			Exercise exercise = readExerciseParameters(new ExerciseImpl("",
			                                                            0,
			                                                            0,
			                                                            "",
			                                                            Set.of(),
			                                                            0,
			                                                            0,
			                                                            -1));
			ExerciseDAO.getInstance().create(exercise);
			exercisesTreeView.addExercise(exercise);
			exercisesTreeView.selectExercise(exercise);
			selectedExercise = exercise;
		});
		secundoButton.setOnAction(event -> {
			ExerciseDAO.getInstance().delete(selectedExercise.getId());
			exercisesTreeView.removeExercise(selectedExercise);
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
			exercisesTreeView.getRoot().getChildren()
			                 .filtered(item -> item.getValue().isExercise()
			                                   && item.getValue().getExercise() == selectedExercise);
			
			exercisesTreeView.removeExercise(selectedExercise);
			exercisesTreeView.addExercise(exercise);
			exercisesTreeView.selectExercise(exercise);
			selectedExercise = exercise;
		});
	}
	
	private Exercise readExerciseParameters(Exercise exercise) {
		RootWithController<ExerciseSettingsController> load = FXMLManager.load(
				"KeyboardTrainer/forms/layouts/ExerciseSettings.fxml");
		
		Stage stage = FXMLManager.createStage(load.getRoot(), "Добавление упражнения");
		stage.sizeToScene();
		stage.setResizable(false);
		stage.initModality(Modality.APPLICATION_MODAL);
		load.getController().init(exercise);
		stage.showAndWait();
		
		return load.getController().getNewExercise();
	}
}

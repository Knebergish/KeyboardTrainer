package KeyboardTrainer.forms.controllers;


import KeyboardTrainer.data.KeyboardZone;
import KeyboardTrainer.data.exercise.Exercise;
import KeyboardTrainer.data.exercise.ExerciseImpl;
import KeyboardTrainer.forms.components.details.DetailsFiller;
import KeyboardTrainer.forms.components.details.DetailsGridPane;
import KeyboardTrainer.forms.components.details.ExerciseDetailsFiller;
import KeyboardTrainer.forms.components.tree.exercise.ExerciseTree;
import KeyboardTrainer.forms.components.tree.exercise.ExerciseTreeItem;
import KeyboardTrainer.forms.controllers.exercise_player.ExercisePlayerController;
import KeyboardTrainer.forms.general.ContentArea;
import KeyboardTrainer.forms.general.fxml.FXMLManager;
import KeyboardTrainer.forms.general.fxml.RootWithController;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Set;


@SuppressWarnings("Duplicates")
public class ExerciseChooserController implements ContentArea {
	public GridPane detailsParentGridPane;
	public Button   controlTestButton;
	public Button   statisticsButton;
	public Button   startExerciseButton;
	public GridPane treeParentGridPane;
	
	private TreeView<ExerciseTreeItem> exercisesTreeView;
	private DetailsFiller<Exercise>    detailsFiller;
	
	private Exercise selectedExercise;
	
	
	public ExerciseChooserController() {
		selectedExercise = null;
	}
	
	@Override
	public void init() {
		initDetailsGridPane();
		initExerciseTreeView();
		
		startExerciseButton.setOnAction(event -> startExercise());
	}
	
	private void initDetailsGridPane() {
		detailsFiller = new ExerciseDetailsFiller();
		detailsFiller.fillDetails(null);
		DetailsGridPane detailsGridPane = detailsFiller.getDetailsGridPane();
		GridPane.setRowIndex(detailsGridPane, 1);
		detailsParentGridPane.getChildren().add(detailsGridPane);
	}
	
	private void initExerciseTreeView() {
		exercisesTreeView = new ExerciseTree(exerciseTreeItem -> {
			selectedExercise = null;
			if (exerciseTreeItem != null && exerciseTreeItem.isExercise()) {
				selectedExercise = exerciseTreeItem.getExercise();
			}
			detailsFiller.fillDetails(selectedExercise);
		});
		treeParentGridPane.getChildren().add(exercisesTreeView);
		GridPane.setMargin(exercisesTreeView, new Insets(10, 5, 0, 10));
		
		// Тестовые данные
		for (int i = 0; i < 4; i++) {
			TreeItem<ExerciseTreeItem> level = new TreeItem<>(new ExerciseTreeItem(i + 1));
			for (int j = 0; j < 5; j++) {
				ExerciseImpl exercise = new ExerciseImpl("Упражнение " + (j + 1), i, (j + 1) * 5, "12345",
				                                         Set.of(KeyboardZone.byNumber(i + 1)), (j + 1) * 3,
				                                         (j + 1) * 14, i * 4 + j);
				TreeItem<ExerciseTreeItem> exerciseTreeItem = new TreeItem<>(new ExerciseTreeItem(exercise));
				level.getChildren().add(exerciseTreeItem);
			}
			exercisesTreeView.getRoot().getChildren().add(level);
		}
		//
	}
	
	private void startExercise() {
		if (selectedExercise == null) {
			return;
		}
		
		RootWithController<ExercisePlayerController> rootWithController = FXMLManager.load(
				"KeyboardTrainer/forms/layouts/ExercisePlayer.fxml");
		
		Stage stage = FXMLManager.createStage(rootWithController.getRoot(), selectedExercise.getName());
		rootWithController.getController().init();
		stage.show();
	}
}

package KeyboardTrainer.forms.controllers;


import KeyboardTrainer.data.exercise.Exercise;
import KeyboardTrainer.data.exercise.ExerciseDAO;
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

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


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
		
		updateTreeItems();
	}
	
	private void updateTreeItems() {
		exercisesTreeView.getRoot().getChildren().clear();
		
		Map<Integer, List<Exercise>> levels = ExerciseDAO.getInstance().getAll().parallelStream()
		                                                 .collect(Collectors.groupingBy(Exercise::getLevel));
		for (int i = 0; i < 4; i++) {
			TreeItem<ExerciseTreeItem> level = new TreeItem<>(new ExerciseTreeItem(i + 1));
			//TODO: ввести для упражнений свойство, упорядочивающее их в пределах уровня
			List<Exercise> exercises = levels.get(i + 1);
			for (Exercise exercise : exercises) {
				TreeItem<ExerciseTreeItem> exerciseTreeItem = new TreeItem<>(new ExerciseTreeItem(exercise));
				level.getChildren().add(exerciseTreeItem);
			}
			exercisesTreeView.getRoot().getChildren().add(level);
		}
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

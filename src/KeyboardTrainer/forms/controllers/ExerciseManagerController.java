package KeyboardTrainer.forms.controllers;


import KeyboardTrainer.data.exercise.Exercise;
import KeyboardTrainer.data.exercise.ExerciseDAO;
import KeyboardTrainer.data.exercise.ExerciseImpl;
import KeyboardTrainer.forms.components.details.DetailsFiller;
import KeyboardTrainer.forms.components.details.DetailsGridPane;
import KeyboardTrainer.forms.components.details.ExerciseDetailsFiller;
import KeyboardTrainer.forms.components.tree.exercise.ExerciseTree;
import KeyboardTrainer.forms.components.tree.exercise.ExerciseTreeItem;
import KeyboardTrainer.forms.general.ContentArea;
import KeyboardTrainer.forms.general.fxml.FXMLManager;
import KeyboardTrainer.forms.general.fxml.RootWithController;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


// Нужно общее этих форм как-то переиспользовать, но как обычно некогда
@SuppressWarnings("Duplicates")
public class ExerciseManagerController implements ContentArea {
	public GridPane detailsParentGridPane;
	public Button   addButton;
	public Button   deleteButton;
	public Button   editButton;
	public GridPane treeParentGridPane;
	
	private TreeView<ExerciseTreeItem> exercisesTreeView;
	private DetailsFiller<Exercise>    detailsFiller;
	
	private Exercise selectedExercise;
	
	
	public ExerciseManagerController() {
		selectedExercise = null;
	}
	
	@Override
	public void init() {
		detailsFiller = new ExerciseDetailsFiller();
		detailsFiller.fillDetails(null);
		DetailsGridPane detailsGridPane = detailsFiller.getDetailsGridPane();
		GridPane.setRowIndex(detailsGridPane, 1);
		detailsParentGridPane.getChildren().add(detailsGridPane);
		
		initExerciseTreeView();
		
		addButton.setOnAction(event -> {
			Exercise exercise = readExerciseParameters(new ExerciseImpl("",
			                                                            1,
			                                                            0,
			                                                            "",
			                                                            Set.of(),
			                                                            0,
			                                                            0,
			                                                            -1));
			ExerciseDAO.getInstance().create(exercise);
			updateTreeItems();
		});
		editButton.setOnAction(event -> {
			if (selectedExercise == null) {
				return;
			}
			Exercise exercise = readExerciseParameters(selectedExercise);
			ExerciseDAO.getInstance().set(exercise);
			updateTreeItems();
		});
		deleteButton.setOnAction(event -> {
			ExerciseDAO.getInstance().delete(selectedExercise.getId());
			updateTreeItems();
		});
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

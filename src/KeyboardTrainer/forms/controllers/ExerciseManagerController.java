package KeyboardTrainer.forms.controllers;


import KeyboardTrainer.data.KeyboardZone;
import KeyboardTrainer.data.exercise.Exercise;
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

import java.util.Set;


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
			                                                            4,
			                                                            0,
			                                                            "",
			                                                            Set.of(KeyboardZone.ZONE_1, KeyboardZone.ZONE_4),
			                                                            0,
			                                                            0,
			                                                            -1));
			System.out.println(exercise);
		});
		deleteButton.setOnAction(event -> System.out.println("Нет."));
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
				                                         null, (j + 1) * 3,
				                                         (j + 1) * 14, i * 4 + j);
				TreeItem<ExerciseTreeItem> exerciseTreeItem = new TreeItem<>(new ExerciseTreeItem(exercise));
				level.getChildren().add(exerciseTreeItem);
			}
			exercisesTreeView.getRoot().getChildren().add(level);
		}
		//
	}
	
	private Exercise readExerciseParameters(Exercise exercise) {
		RootWithController<ExerciseSettingsController> load = FXMLManager.load(
				"KeyboardTrainer/forms/layouts/ExerciseSettings.fxml");
		
		Stage stage = FXMLManager.createStage(load.getRoot(), "", 670, 320);
		stage.sizeToScene();
		stage.setResizable(false);
		stage.initModality(Modality.APPLICATION_MODAL);
		load.getController().init(exercise);
		stage.showAndWait();
		
		return load.getController().getNewExercise();
	}
}

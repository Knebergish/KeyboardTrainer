package KeyboardTrainer.forms.controllers;


import KeyboardTrainer.data.exercise.Exercise;
import KeyboardTrainer.data.exercise.ExerciseImpl;
import KeyboardTrainer.forms.components.details.DetailsFiller;
import KeyboardTrainer.forms.components.details.DetailsGridPane;
import KeyboardTrainer.forms.components.details.ExerciseDetailsFiller;
import KeyboardTrainer.forms.components.tree.exercise.ExerciseTree;
import KeyboardTrainer.forms.components.tree.exercise.ExerciseTreeItem;
import KeyboardTrainer.forms.general.ContentArea;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.GridPane;


// Нужно общее этих форм как-то переиспользовать, но как оычно некогда
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

//		editButton.setOnAction(event -> startExercise());
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
}

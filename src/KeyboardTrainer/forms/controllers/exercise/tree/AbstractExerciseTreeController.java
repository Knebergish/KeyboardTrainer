package KeyboardTrainer.forms.controllers.exercise.tree;


import KeyboardTrainer.data.exercise.Exercise;
import KeyboardTrainer.data.exercise.ExerciseDAO;
import KeyboardTrainer.forms.components.details.DetailsFiller;
import KeyboardTrainer.forms.components.details.DetailsGridPane;
import KeyboardTrainer.forms.components.details.ExerciseDetailsFiller;
import KeyboardTrainer.forms.components.tree.exercise.ExerciseTree;
import KeyboardTrainer.forms.general.ContentArea;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;


@SuppressWarnings("WeakerAccess")
public abstract class AbstractExerciseTreeController implements ContentArea {
	@FXML
	protected GridPane treeParentGridPane;
	@FXML
	protected GridPane detailsParentGridPane;
	@FXML
	protected Button   primoButton;
	@FXML
	protected Button   secundoButton;
	@FXML
	protected Button   tertioButton;
	
	protected ExerciseTree            exercisesTreeView;
	protected DetailsFiller<Exercise> detailsFiller;
	
	protected Exercise selectedExercise;
	
	
	protected AbstractExerciseTreeController() {
		selectedExercise = null;
	}
	
	@Override
	public void init() {
		initDetailsGridPane();
		initExerciseTreeView();
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
				secundoButton.setDisable(false);
				tertioButton.setDisable(false);
			} else {
				secundoButton.setDisable(true);
				tertioButton.setDisable(true);
			}
			detailsFiller.fillDetails(selectedExercise);
		});
		treeParentGridPane.getChildren().add(exercisesTreeView);
		GridPane.setMargin(exercisesTreeView, new Insets(10, 5, 0, 10));
		
		exercisesTreeView.setExercises(ExerciseDAO.getInstance().getAll());
	}
}

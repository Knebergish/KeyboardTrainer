package KeyboardTrainer.forms.controllers;


import KeyboardTrainer.data.exercise.Exercise;
import KeyboardTrainer.data.exercise.ExerciseImpl;
import KeyboardTrainer.forms.components.DetailsFiller;
import KeyboardTrainer.forms.controllers.exercise_player.ExercisePlayerController;
import KeyboardTrainer.forms.general.ContentArea;
import KeyboardTrainer.forms.general.fxml.FXMLManager;
import KeyboardTrainer.forms.general.fxml.RootWithController;
import javafx.scene.control.Button;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class ExerciseChooserController implements ContentArea {
	public  GridPane                   detailInfoGridPane;
	public  TreeView<ExerciseTreeItem> exercisesTreeView;
	public  Button                     controlTestButton;
	public  Button                     statisticsButton;
	public  Button                     startExerciseButton;
	private DetailsFiller              detailsFiller;
	
	private Exercise selectedExercise;
	
	
	public ExerciseChooserController() {
		selectedExercise = null;
	}
	
	@Override
	public void init() {
		detailsFiller = new DetailsFiller(detailInfoGridPane);
		detailsFiller.fillDetails(null);
		
		initExerciseTreeView();
		
		startExerciseButton.setOnAction(event -> startExercise());
	}
	
	private void initExerciseTreeView() {
		exercisesTreeView.setRoot(new TreeItem<>(null));
		exercisesTreeView.setShowRoot(false);
		// Учим дерево выводить уровни и упражнения
		exercisesTreeView.setCellFactory(tv -> new TreeCell<ExerciseTreeItem>() {
			@Override
			protected void updateItem(ExerciseTreeItem item, boolean empty) {
				super.updateItem(item, empty);
				if (!empty && item != null) {
					if (item.isExercise()) {
						setText(item.getExercise().getName());
					} else {
						setText(String.valueOf("Уровень " + item.getLevel()));
					}
				} else {
					setText("");
				}
			}
		});
		
		// Обрабатываем событие выбора упражнения в дереве
		exercisesTreeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			selectedExercise = null;
			ExerciseTreeItem exerciseTreeItem = newValue.getValue();
			if (exerciseTreeItem != null && exerciseTreeItem.isExercise) {
				selectedExercise = exerciseTreeItem.getExercise();
			}
			detailsFiller.fillDetails(selectedExercise);
		});
		
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
	
	private void startExercise() {
		if (selectedExercise == null) {
			return;
		}
		
		RootWithController<ExercisePlayerController> rootWithController = FXMLManager.load(
				"KeyboardTrainer/forms/layouts/ExercisePlayer.fxml");
		
		Stage stage = FXMLManager.createStage(rootWithController.getRoot(), selectedExercise.getName(), 700, 400);
		rootWithController.getController().init();
		stage.show();
	}
	
	
	private class ExerciseTreeItem {
		private final boolean  isExercise;
		private final int      level;
		private final Exercise exercise;
		
		private ExerciseTreeItem(Exercise exercise) {
			isExercise = true;
			this.exercise = exercise;
			this.level = -13; // Прост весело
		}
		
		private ExerciseTreeItem(int level) {
			isExercise = false;
			this.level = level;
			this.exercise = null;
		}
		
		public boolean isExercise() {
			return isExercise;
		}
		
		public int getLevel() {
			return level;
		}
		
		public Exercise getExercise() {
			return exercise;
		}
	}
}

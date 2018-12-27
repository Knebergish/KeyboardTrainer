package KeyboardTrainer.forms.components.tree.exercise;


import KeyboardTrainer.data.exercise.Exercise;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;


/**
 * Дерево упражнений, разделённых по уровням сложности.
 */
public class ExerciseTree extends TreeView<ExerciseTreeItem> {
	private final List<TreeItem<ExerciseTreeItem>> levels;
	
	public ExerciseTree(Consumer<ExerciseTreeItem> selectionHandler) {
		this.setRoot(new TreeItem<>(null));
		this.setShowRoot(false);
		
		// Добавляем и запоминаем уровни
		levels = new ArrayList<>(4);
		for (int i = 0; i < 4; i++) {
			levels.add(new TreeItem<>(new ExerciseTreeItem(i)));
		}
		this.getRoot().getChildren().addAll(levels);
		
		// Учим дерево выводить объект
		this.setCellFactory(tv -> new TreeCell<>() {
			@Override
			protected void updateItem(ExerciseTreeItem item, boolean empty) {
				super.updateItem(item, empty);
				if (!empty && item != null) {
					setText(getTextForItem(item));
				} else {
					setText("");
				}
			}
		});
		
		// Обрабатываем событие выбора объекта в дереве
		this.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				selectionHandler.accept(newValue.getValue());
			}
		});
	}
	
	private String getTextForItem(ExerciseTreeItem item) {
		String text;
		if (item.isExercise()) {
			text = item.getExercise().getName();
		} else {
			text = "Уровень " + (item.getLevel() + 1);
		}
		return text;
	}
	
	public void removeExercise(Exercise exercise) {
		TreeItem<ExerciseTreeItem> levelItem = levels.get(exercise.getLevel());
		Optional<TreeItem<ExerciseTreeItem>> removeItem =
				levelItem.getChildren().parallelStream()
				         .filter(item -> item.getValue().isExercise() && item.getValue().getExercise() == exercise)
				         .findFirst();
		//noinspection OptionalIsPresent
		if (removeItem.isPresent()) {
			levelItem.getChildren().remove(removeItem.get());
		}
	}
	
	public void addExercise(Exercise exercise) {
		levels.get(exercise.getLevel()).getChildren().add(new TreeItem<>(new ExerciseTreeItem(exercise)));
	}
	
	public void selectExercise(Exercise exercise) {
		TreeItem<ExerciseTreeItem> selectionItem =
				levels.get(exercise.getLevel()).getChildren().parallelStream()
				      .filter(item -> item.getValue().getExercise() == exercise)
				      .findFirst().orElseGet(() -> levels.get(0));
		this.getSelectionModel().select(selectionItem);
	}
	
	public void setExercises(List<Exercise> exercises) {
		for (TreeItem<ExerciseTreeItem> level : levels) {
			level.getChildren().clear();
		}
		
		for (Exercise exercise : exercises) {
			addExercise(exercise);
		}
	}
}

package KeyboardTrainer.forms.components.tree.exercise;


import KeyboardTrainer.forms.components.tree.GenericTree;

import java.util.function.Consumer;


public class ExerciseTree extends GenericTree<ExerciseTreeItem> {
	
	public ExerciseTree(Consumer<ExerciseTreeItem> selectionHandler) {
		super(selectionHandler);
	}
	
	@Override
	protected String getTextForItem(ExerciseTreeItem item) {
		String text;
		if (item.isExercise()) {
			text = item.getExercise().getName();
		} else {
			text = "Уровень " + item.getLevel();
		}
		return text;
	}
}

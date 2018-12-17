package KeyboardTrainer.forms.components.tree;


import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.util.function.Consumer;


/**
 * Дерево для вывода объектов определённого типа.
 */
public abstract class GenericTree<T> extends TreeView<T> {
	public GenericTree(Consumer<T> selectionHandler) {
		this.setRoot(new TreeItem<>(null));
		this.setShowRoot(false);
		
		// Учим дерево выводить объект
		this.setCellFactory(tv -> new TreeCell<>() {
			@Override
			protected void updateItem(T item, boolean empty) {
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
			selectionHandler.accept(newValue.getValue());
		});
	}
	
	protected abstract String getTextForItem(T item);
}

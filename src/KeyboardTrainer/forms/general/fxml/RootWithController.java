package KeyboardTrainer.forms.general.fxml;


import javafx.scene.Parent;


/**
 * Агрегирует разметку и её контроллер.
 *
 * @param <T> тип контроллера
 */
public class RootWithController<T> {
	private final Parent root;
	private final T      controller;
	
	public RootWithController(Parent root, T controller) {
		this.root = root;
		this.controller = controller;
	}
	
	public Parent getRoot() {
		return root;
	}
	
	public T getController() {
		return controller;
	}
}

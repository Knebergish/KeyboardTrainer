package KeyboardTrainer.forms.general.fxml;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class FXMLManager {
	/**
	 * Загружает разметку из файла.
	 *
	 * @param filePath путь к файлу с разметкой
	 * @param <T>      тип контроллера к этой разметке
	 * @return разметка и контроллер к ней
	 */
	public static <T> RootWithController<T> load(String filePath) {
		final Parent root;
		final T      controller;
		
		FXMLLoader loader = new FXMLLoader(FXMLManager.class.getClassLoader().getResource(filePath));
		try {
			root = loader.load();
		} catch (Exception e) {
			throw new RuntimeException("Форма с таким именем не найдена: " + filePath, e);
		}
		controller = loader.getController();
		
		return new RootWithController<>(root, controller);
	}
	
	public static Stage createStage(Parent root, String title) {
		return createStage(root, title, 0, 0);
	}
	
	/**
	 * Оборачивает разметку в Stage для открытия в новом окне.
	 *
	 * @param root   разметка
	 * @param title  заголовок окна
	 * @param width  ширина окна
	 * @param height высота окна
	 * @return Stage с разметкой
	 */
	public static Stage createStage(Parent root, String title, int width, int height) {
		Stage stage = new Stage();
		stage.setTitle(title);
		if (width > 0) {
			stage.setWidth(width);
		}
		if (height > 0) {
			stage.setHeight(height);
		}
		Scene scene = new Scene(root);
		stage.setScene(scene);
		return stage;
	}
}

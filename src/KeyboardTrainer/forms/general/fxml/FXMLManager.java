package KeyboardTrainer.forms.general.fxml;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


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
		} catch (IOException e) {
			throw new RuntimeException("Форма с таким именем не найдена: " + filePath, e);
		}
		controller = loader.getController();
		
		return new RootWithController<>(root, controller);
	}
	
	/**
	 * Оборачивает разметку в Stage для открытия в новом окне.
	 *
	 * @param root   разметка
	 * @param title  заголовок окна
	 * @param width  ширина окна
	 * @param heigth высота окна
	 * @return Stage с разметкой
	 */
	public static Stage createStage(Parent root, String title, int width, int heigth) {
		Stage stage = new Stage();
		stage.setTitle(title);
		stage.setWidth(width);
		stage.setHeight(heigth);
		Scene scene = new Scene(root);
		stage.setScene(scene);
		return stage;
	}
}

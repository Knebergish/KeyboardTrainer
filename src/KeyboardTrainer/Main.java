package KeyboardTrainer;


import KeyboardTrainer.forms.MainForm;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		FXMLLoader loader     = new FXMLLoader(getClass().getResource("forms/MainForm.fxml"));
		Parent     root       = loader.load();
		MainForm   controller = loader.getController();
		
		primaryStage.setTitle("Keeeeeey!");
		primaryStage.setWidth(700);
		primaryStage.setHeight(400);
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		
		
		primaryStage.show();
		controller.init();
	}
}

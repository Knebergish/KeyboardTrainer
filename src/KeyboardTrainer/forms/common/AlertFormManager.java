package KeyboardTrainer.forms.common;


import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogEvent;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class AlertFormManager {
	public static void showAlert(Alert.AlertType alertType, String title, String headerText, String text,
	                             EventHandler<DialogEvent> closeEvent) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("file:res/icon.jpg"));
		alert.setHeaderText(headerText);
		alert.setContentText(text);
		
		if (closeEvent != null) {
			alert.setOnCloseRequest(closeEvent);
		}
		
		alert.show();
	}
}

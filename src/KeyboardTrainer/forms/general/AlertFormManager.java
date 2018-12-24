package KeyboardTrainer.forms.general;


import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogEvent;


public class AlertFormManager {
	public static void showAlert(Alert.AlertType alertType, String title, String headerText, String text,
	                             EventHandler<DialogEvent> closeEvent) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(text);
		
		if (closeEvent != null) {
			alert.setOnCloseRequest(closeEvent);
		}
		
		alert.show();
	}
}

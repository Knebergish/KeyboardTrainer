package KeyboardTrainer.forms.common;


import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogEvent;
import javafx.stage.StageStyle;


public final class AlertFormManager {
	private AlertFormManager() {
	}
	
	public static void showAlert(Alert.AlertType alertType, String title, String headerText, String text,
	                             EventHandler<DialogEvent> closeEvent) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(text);
		alert.initStyle(StageStyle.UTILITY);
		
		if (closeEvent != null) {
			alert.setOnCloseRequest(closeEvent);
		}
		
		alert.show();
	}
}

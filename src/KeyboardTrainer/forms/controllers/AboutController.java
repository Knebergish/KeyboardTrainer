package KeyboardTrainer.forms.controllers;


import KeyboardTrainer.forms.general.ContentArea;
import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;


public class AboutController implements ContentArea {
	@FXML
	private WebView webView;
	
	@Override
	public void init() {
		WebEngine webEngine = webView.getEngine();
		webEngine.load("file://ТУТ_ПУТЬ_К_СТРАНИЦЕ.html");
	}
}

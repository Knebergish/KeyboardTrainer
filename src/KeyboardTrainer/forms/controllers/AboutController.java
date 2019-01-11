package KeyboardTrainer.forms.controllers;


import KeyboardTrainer.forms.general.ContentArea;
import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.File;


public class AboutController implements ContentArea {
	@FXML
	private WebView webView;
	
	@Override
	public void init() {
		WebEngine webEngine = webView.getEngine();
		webEngine.load("file:/" + new File("").getAbsolutePath() + "\\res\\html\\index.html");
	}
}

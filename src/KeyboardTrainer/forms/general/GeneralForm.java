package KeyboardTrainer.forms.general;


import KeyboardTrainer.forms.common.fxml.FXMLManager;
import KeyboardTrainer.forms.common.fxml.RootWithController;
import KeyboardTrainer.forms.general.menu.MenuButton;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.util.List;


/**
 * Общая форма, содержащая область с кнопками меню и область для вывода контента.
 */
public abstract class GeneralForm {
	@FXML
	private GridPane   menuGridPane;
	@FXML
	private AnchorPane contentAnchorPane;
	
	public void init() {
		List<MenuButton> buttons = createButtonsList();
		
		for (int i = 0; i < buttons.size(); i++) {
			ColumnConstraints columnConstraints = new ColumnConstraints();
			columnConstraints.setHalignment(HPos.CENTER);
			columnConstraints.setHgrow(Priority.SOMETIMES);
			menuGridPane.getColumnConstraints().add(columnConstraints);
			
			MenuButton button = buttons.get(i);
			button.setGeneralForm(this);
			menuGridPane.add(button, i, 0);
		}
		
		RootWithController<Object> logo = FXMLManager.load("Logo");
		setContent(logo.getRoot());
	}
	
	protected abstract List<MenuButton> createButtonsList();
	
	public abstract String getTitle();
	
	public void setContent(Parent root) {
		AnchorPane.setLeftAnchor(root, .0);
		AnchorPane.setBottomAnchor(root, .0);
		AnchorPane.setRightAnchor(root, .0);
		AnchorPane.setTopAnchor(root, .0);
		contentAnchorPane.getChildren().clear();
		contentAnchorPane.getChildren().add(root);
	}
}

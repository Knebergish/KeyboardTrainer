package KeyboardTrainer.forms.general.menu;


import KeyboardTrainer.forms.general.ContentArea;
import KeyboardTrainer.forms.general.fxml.FXMLManager;
import KeyboardTrainer.forms.general.fxml.RootWithController;
import javafx.scene.Parent;


/**
 * Кнопка меню, меняющая содержимое главной формы.
 */
public class ChangeContentMenuButton extends MenuButton {
	private Parent root;
	
	public ChangeContentMenuButton(String text, String formName) {
		super(text);
		
		RootWithController<ContentArea> rootWithController = FXMLManager.load(formName);
		if (rootWithController.getController() != null) {
			rootWithController.getController().init();
		}
		root = rootWithController.getRoot();
	}
	
	@Override
	protected void action() {
		generalForm.setContent(root);
	}
}

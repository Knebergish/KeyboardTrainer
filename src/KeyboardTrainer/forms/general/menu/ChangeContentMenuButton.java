package KeyboardTrainer.forms.general.menu;


import KeyboardTrainer.forms.common.fxml.FXMLManager;
import KeyboardTrainer.forms.common.fxml.RootWithController;
import KeyboardTrainer.forms.general.ContentArea;

import java.util.function.Supplier;


/**
 * Кнопка меню, меняющая содержимое главной формы.
 */
public class ChangeContentMenuButton extends MenuButton {
	private final String                formName;
	private final Supplier<ContentArea> controllerSupplier;
	
	public ChangeContentMenuButton(String text, String formName) {
		this(text, formName, () -> null);
	}
	
	public ChangeContentMenuButton(String text, String formName, Supplier<ContentArea> controllerSupplier) {
		super(text);
		this.formName = formName;
		this.controllerSupplier = controllerSupplier;
	}
	
	@Override
	protected void action() {
		RootWithController<ContentArea> rootWithController = FXMLManager.load(formName, controllerSupplier.get());
		if (rootWithController.getController() != null) {
			rootWithController.getController().init();
		}
		generalForm.setContent(rootWithController.getRoot());
	}
}

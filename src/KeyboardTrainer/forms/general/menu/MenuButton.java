package KeyboardTrainer.forms.general.menu;


import KeyboardTrainer.forms.general.GeneralForm;
import javafx.scene.control.Button;
import javafx.scene.text.TextAlignment;


/**
 * Кнопка на панели меню.
 */
public abstract class MenuButton extends Button {
	@SuppressWarnings("WeakerAccess")
	protected GeneralForm generalForm;
	
	MenuButton(String text) {
		super(text);
		setPrefWidth(100);
		setPrefHeight(50);
		setWrapText(true);
		setTextAlignment(TextAlignment.CENTER);
		
		setOnAction(event -> action());
	}
	
	protected abstract void action();
	
	public void setGeneralForm(GeneralForm generalForm) {
		this.generalForm = generalForm;
	}
}

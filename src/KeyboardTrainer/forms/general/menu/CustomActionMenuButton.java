package KeyboardTrainer.forms.general.menu;


import KeyboardTrainer.forms.controllers.exercise_player.ActionHandler;


/**
 * Кнопка меню, делающая что угодно.
 */
public class CustomActionMenuButton extends MenuButton {
	private final ActionHandler action;
	
	public CustomActionMenuButton(String text, ActionHandler action) {
		super(text);
		this.action = action;
	}
	
	@Override
	protected void action() {
		action.handle();
	}
}

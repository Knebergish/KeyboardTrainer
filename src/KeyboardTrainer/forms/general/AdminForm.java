package KeyboardTrainer.forms.general;


import KeyboardTrainer.forms.controllers.exercise.tree.ExerciseManagerController;
import KeyboardTrainer.forms.general.menu.ChangeContentMenuButton;
import KeyboardTrainer.forms.general.menu.CustomActionMenuButton;
import javafx.scene.control.Alert;


public class AdminForm extends GeneralForm {
	public AdminForm() {
		setTitle("Лучшая админ-панель");
		addMenuButton(new ChangeContentMenuButton("Управление упражнениями", "ExerciseGeneral",
		                                          ExerciseManagerController::new));
		addMenuButton(new ChangeContentMenuButton("Управление аккаунтами",
		                                          "UsersManager"));
		addMenuButton(new ChangeContentMenuButton("Статистика", "Statistics"));
		addMenuButton(new ChangeContentMenuButton("Справка", "About"));
	}
}

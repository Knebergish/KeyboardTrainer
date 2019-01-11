package KeyboardTrainer.forms.general;


import KeyboardTrainer.forms.controllers.exercise.tree.ExerciseManagerController;
import KeyboardTrainer.forms.general.menu.ChangeContentMenuButton;
import KeyboardTrainer.forms.general.menu.MenuButton;

import java.util.List;


public class AdminForm extends GeneralForm {
	@Override
	protected List<MenuButton> createButtonsList() {
		return List.of(
				new ChangeContentMenuButton("Управление упражнениями", "ExerciseGeneral",
				                            ExerciseManagerController::new),
				new ChangeContentMenuButton("Управление аккаунтами",
				                            "UsersManager"),
				new ChangeContentMenuButton("Статистика", "AdminStatistics"),
				new ChangeContentMenuButton("Справка", "About")
		              );
	}
	
	@Override
	public String getTitle() {
		return "Лучшая админ-панель";
	}
}

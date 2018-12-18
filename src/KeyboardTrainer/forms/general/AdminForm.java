package KeyboardTrainer.forms.general;


import KeyboardTrainer.forms.general.menu.ChangeContentMenuButton;
import KeyboardTrainer.forms.general.menu.CustomActionMenuButton;
import javafx.scene.control.Alert;


public class AdminForm extends GeneralForm {
	public AdminForm() {
		setTitle("Лучшая админ-панель");
		addMenuButton(new ChangeContentMenuButton("Управление упражнениями",
		                                          "KeyboardTrainer/forms/layouts/ExerciseManager.fxml"));
		addMenuButton(new ChangeContentMenuButton("Управление аккаунтами",
		                                          "KeyboardTrainer/forms/layouts/AccountsManager.fxml"));
		addMenuButton(new CustomActionMenuButton("Статистика",
		                                         () -> {
			                                         Alert alert = new Alert(Alert.AlertType.WARNING);
			                                         alert.setTitle("Внимание");
			                                         alert.setHeaderText("Ведутся технические работы");
			                                         alert.setContentText(
					                                         "Данная форма пока недоступна, вернитесь позже.");
			                                         alert.show();
		                                         }));
		addMenuButton(new ChangeContentMenuButton("Справка",
		                                          "KeyboardTrainer/forms/layouts/Logo.fxml"));
	}
}

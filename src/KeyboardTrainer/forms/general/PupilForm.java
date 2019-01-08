package KeyboardTrainer.forms.general;


import KeyboardTrainer.Session;
import KeyboardTrainer.data.exercise.ExerciseImpl;
import KeyboardTrainer.data.statistics.StatisticsDAO;
import KeyboardTrainer.forms.common.fxml.FXMLManager;
import KeyboardTrainer.forms.common.fxml.RootWithController;
import KeyboardTrainer.forms.controllers.exercise.player.ExercisePlayerController;
import KeyboardTrainer.forms.controllers.exercise.tree.ExerciseChooserController;
import KeyboardTrainer.forms.controllers.statistics.AverageStatisticsController;
import KeyboardTrainer.forms.general.menu.ChangeContentMenuButton;
import KeyboardTrainer.forms.general.menu.CustomActionMenuButton;
import KeyboardTrainer.language.Language;
import javafx.stage.Stage;


public class PupilForm extends GeneralForm {
	public PupilForm() {
		setTitle("Лучший клавиатурный тренажёр");
		addMenuButton(
				new CustomActionMenuButton("Продолжить", this::continueAction));
		addMenuButton(new ChangeContentMenuButton("Выбор упражнения", "ExerciseGeneral",
		                                          ExerciseChooserController::new));
		addMenuButton(new ChangeContentMenuButton("Статистика", "AverageStatistics",
		                                          this::getAverageStatisticsController));
		addMenuButton(new ChangeContentMenuButton("Справка", "About"));
	}
	
	private void continueAction() {
		RootWithController<ExercisePlayerController> load = FXMLManager.load("ExercisePlayer");
		
		Stage stage = FXMLManager.createStage(load.getRoot(), "Ваше следующее упражнение");
		stage.setResizable(false);
		
		load.getController().init(
				//TODO: искать первое непройденное
				new ExerciseImpl("TestExercise",
				                 1,
				                 "12345",
				                 null,
				                 12,
				                 1000,
				                 -1,
				                 Language.RUSSIAN));
		
		
		stage.show();
	}
	
	private AverageStatisticsController getAverageStatisticsController() {
		int userId     = Session.getLoggedUser().getId();
		var statistics = StatisticsDAO.getInstance().getAverageStatisticsForUser(userId);
		return new AverageStatisticsController(statistics);
	}
}

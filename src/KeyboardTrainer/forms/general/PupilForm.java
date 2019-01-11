package KeyboardTrainer.forms.general;


import KeyboardTrainer.Session;
import KeyboardTrainer.data.exercise.Exercise;
import KeyboardTrainer.data.exercise.ExerciseDAO;
import KeyboardTrainer.data.statistics.StatisticsDAO;
import KeyboardTrainer.forms.common.Utils;
import KeyboardTrainer.forms.controllers.exercise.tree.ExerciseChooserController;
import KeyboardTrainer.forms.controllers.statistics.AverageStatisticsController;
import KeyboardTrainer.forms.general.menu.ChangeContentMenuButton;
import KeyboardTrainer.forms.general.menu.CustomActionMenuButton;
import KeyboardTrainer.forms.general.menu.MenuButton;
import javafx.stage.Stage;

import java.util.List;


public class PupilForm extends GeneralForm {
	@Override
	protected List<MenuButton> createButtonsList() {
		return List.of(
				new CustomActionMenuButton("Продолжить", this::continueAction),
				new ChangeContentMenuButton("Выбор упражнения", "ExerciseGeneral",
				                            ExerciseChooserController::new),
				new ChangeContentMenuButton("Статистика", "AverageStatistics",
				                            this::getAverageStatisticsController),
				new ChangeContentMenuButton("Справка", "About")
		              );
	}
	
	private void continueAction() {
		Exercise exercise = ExerciseDAO.getInstance().getFirstNotPassedExercise(Session.getLoggedUser().getId());
		
		Stage stage = Utils.getExercisePlayerStage(exercise);
		stage.show();
	}
	
	private AverageStatisticsController getAverageStatisticsController() {
		int userId     = Session.getLoggedUser().getId();
		var statistics = StatisticsDAO.getInstance().getAverageStatisticsForUser(userId);
		return new AverageStatisticsController(statistics);
	}
	
	@Override
	public String getTitle() {
		return "Лучший клавиатурный тренажёр";
	}
}

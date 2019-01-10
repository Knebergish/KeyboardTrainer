package KeyboardTrainer.forms.controllers.exercise.tree;


import KeyboardTrainer.Session;
import KeyboardTrainer.data.exercise.Exercise;
import KeyboardTrainer.data.exercise.ExerciseImpl;
import KeyboardTrainer.forms.common.AlertFormManager;
import KeyboardTrainer.forms.common.Utils;
import KeyboardTrainer.forms.common.fxml.FXMLManager;
import KeyboardTrainer.forms.common.fxml.RootWithController;
import KeyboardTrainer.forms.controllers.exercise.player.ExercisePlayerController;
import KeyboardTrainer.forms.controllers.statistics.StatisticsMagic;
import KeyboardTrainer.language.Language;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.util.Set;


public class ExerciseChooserController extends AbstractExerciseTreeController {
	@Override
	public void init() {
		super.init();
		
		primoButton.setText("Контрольное упражнение");
		secundoButton.setText("Статистика");
		tertioButton.setText("Начать");
		
		primoButton.setOnAction(event -> controlExercise());
		secundoButton.setOnAction(
				event -> StatisticsMagic.showUserStatisticsForExercise(Session.getLoggedUser(), selectedExercise));
		tertioButton.setOnAction(event -> startExercise());
	}
	
	private void startExercise() {
		if (selectedExercise == null) {
			return;
		}
		
		Stage stage = Utils.getExercisePlayerStage(selectedExercise);
		stage.show();
	}
	
	private void controlExercise() {
		final int  maxErrorsCount         = 10;
		final long maxAveragePressingTime = 1000;
		Exercise exercise = new ExerciseImpl("Контрольный в голову",
		                                     13,
		                                     "Попробуй успеть выпить этих мягких французских булок да съесть этого прелестного чаю.",
		                                     Set.of(),
		                                     maxErrorsCount,
		                                     maxAveragePressingTime,
		                                     -7,
		                                     Language.RUSSIAN);
		
		RootWithController<ExercisePlayerController> load = FXMLManager.load("ExercisePlayer");
		
		Stage stage = FXMLManager.createStage(load.getRoot(), exercise.getName());
		load.getController().init(exercise, statistics -> {
			String result = "";
			if (statistics.getCompletePercents() == 100) {
				result = "Ну вы молодцы, удаляйте это приложение, оно вам не нужно.";
			} else if (statistics.getAveragePressingTime() >= maxAveragePressingTime) {
				result = "Вам надо поработать над скоростью печати (очень сложный алгоритм подбора рекомендаций).";
			} else if (statistics.getErrorsCount() >= maxErrorsCount) {
				result = "У вас очень низкая точность нажатия клавиш. Цельтесь лучше.";
			}
			AlertFormManager.showAlert(Alert.AlertType.INFORMATION,
			                           "Результаты контрольного упражнения",
			                           "Заключение экспертов можете прочитать ниже.",
			                           result,
			                           null);
		});
		stage.show();
	}
}

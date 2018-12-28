package KeyboardTrainer.forms.controllers.exercise;


import KeyboardTrainer.Session;
import KeyboardTrainer.data.exercise.Exercise;
import KeyboardTrainer.data.statistics.Statistics;
import KeyboardTrainer.forms.Utils;
import KeyboardTrainer.forms.controllers.statistics.StatisticsMagic;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Window;
import javafx.stage.WindowEvent;


public class ExerciseResultController {
	@FXML
	private Label  titleLabel;
	@FXML
	private Label  totalTimeLabel;
	@FXML
	private Label  errorsCountLabel;
	@FXML
	private Label  averagePressingTimeLabel;
	@FXML
	private Button toMenuButton;
	@FXML
	private Button toStatisticsButton;
	@FXML
	private Label  completePercentsLabel;
	
	public void init(Exercise exercise, Statistics statistics) {
		titleLabel.setText("Упражнение " + (statistics.getCompletePercents() == 100 ? "" : "не ") + "пройдено!");
		totalTimeLabel.setText(Utils.formatTime(statistics.getTotalTime(), "mm:ss"));
		errorsCountLabel.setText(statistics.getErrorsCount() + " / " + exercise.getMaxErrorsCount());
		averagePressingTimeLabel.setText(Utils.formatTime(statistics.getAveragePressingTime(), "s.SSS")
		                                 + " / "
		                                 + Utils.formatTime(exercise.getMaxAveragePressingTime(), "s.SSS"));
		completePercentsLabel.setText(statistics.getCompletePercents() + "%");
		
		toMenuButton.setOnAction(event -> {
			Window window = titleLabel.getScene().getWindow();
			window.fireEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
		});
		toStatisticsButton.setOnAction(
				event -> StatisticsMagic.showUserStatisticsForExercise(Session.getLoggedUser(), exercise));
	}
}

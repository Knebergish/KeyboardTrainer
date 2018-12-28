package KeyboardTrainer.forms.controllers.exercise;


import KeyboardTrainer.data.exercise.Exercise;
import KeyboardTrainer.data.statistics.Statistics;
import KeyboardTrainer.forms.Utils;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Window;
import javafx.stage.WindowEvent;


public class ExerciseResultController {
	public Label  titleLabel;
	public Label  totalTimeLabel;
	public Label  errorsCountLabel;
	public Label  averagePressingTimeLabel;
	public Button toMenuButton;
	public Button toStatisticsButton;
	public Label  completePercentsLabel;
	
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
	}
}

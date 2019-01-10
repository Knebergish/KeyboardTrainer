package KeyboardTrainer.forms.controllers.statistics;


import KeyboardTrainer.forms.general.ContentArea;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


@SuppressWarnings("unused")
public class AverageStatisticsController implements ContentArea {
	private final AverageStatistics statistics;
	
	@FXML
	private Label averageErrorsCountLabel;
	@FXML
	private Label averagePressingTimeLabel;
	@FXML
	private Label averageTotalTimeLabel;
	
	
	public AverageStatisticsController(AverageStatistics statistics) {
		this.statistics = statistics;
	}
	
	@Override
	public void init() {
		averageErrorsCountLabel.setText(String.format("%.2f", statistics.getAverageErrorsCount()));
		averagePressingTimeLabel.setText(String.format("%.2f мс", statistics.getAveragePressingTime()));
		averageTotalTimeLabel.setText(String.format("%.2f мс", statistics.getAverageTotalTime()));
	}
}

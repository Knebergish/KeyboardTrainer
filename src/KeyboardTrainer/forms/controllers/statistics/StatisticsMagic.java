package KeyboardTrainer.forms.controllers.statistics;


import KeyboardTrainer.data.exercise.Exercise;
import KeyboardTrainer.data.statistics.Statistics;
import KeyboardTrainer.data.statistics.StatisticsDAO;
import KeyboardTrainer.data.user.User;
import KeyboardTrainer.forms.common.fxml.FXMLManager;
import KeyboardTrainer.forms.common.fxml.RootWithController;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public final class StatisticsMagic {
	private StatisticsMagic() {
	}
	
	public static void showUserStatisticsForExercise(User user, Exercise exercise) {
		List<Statistics> statistics =
				StatisticsDAO.getInstance().getUserStatisticsForExercise(user.getId(), exercise.getId());
		
		List<Integer> errors = statistics.parallelStream()
		                                 .map(Statistics::getErrorsCount)
		                                 .collect(Collectors.toList());
		
		List<Integer> numbers = IntStream.range(1, errors.size() + 1).boxed().collect(Collectors.toList());
		showChart("Статистика допущенных ошибок",
		          numbers,
		          1,
		          errors.size() + 1,
		          "Попытка",
		          errors,
		          0,
		          errors.parallelStream().max(Integer::compareTo).orElse(0) + 1,
		          "Ошибки");
	}
	
	@SuppressWarnings("SameParameterValue")
	private static <T extends Number> void showChart(String title, List<T> itemsX, Integer minX, Integer maxX,
	                                                 String XAxisText, List<T> itemsY, Integer minY, Integer maxY,
	                                                 String YAxisText) {
		RootWithController<ChartFormController> load = FXMLManager.load("ChartForm");
		load.getController().setChart(itemsX, minX, maxX, XAxisText, itemsY, minY, maxY, YAxisText);
		Stage stage = FXMLManager.createStage(load.getRoot(), title, 500, 500);
		stage.show();
	}
}

package KeyboardTrainer.forms.controllers.statistics;


import KeyboardTrainer.data.Entity;
import KeyboardTrainer.data.exercise.Exercise;
import KeyboardTrainer.data.statistics.Statistics;
import KeyboardTrainer.data.statistics.StatisticsDAO;
import KeyboardTrainer.data.user.User;
import KeyboardTrainer.forms.general.fxml.FXMLManager;
import KeyboardTrainer.forms.general.fxml.RootWithController;
import javafx.stage.Stage;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class StatisticsMagic {
	public static void showUserStatisticsForExercise(User user, Exercise exercise) {
		List<Statistics> statistics = StatisticsDAO.getInstance().getAll().parallelStream()
		                                           .filter(s -> s.getUserId() == user.getId())
		                                           .filter(s -> s.getExerciseId() == exercise.getId())
		                                           .sorted(Comparator.comparingInt(Entity::getId))
		                                           .collect(Collectors.toList());
		
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
	
	private static <T extends Number> void showChart(String title, List<T> itemsX, Integer minX, Integer maxX,
	                                                 String XAxisText, List<T> itemsY, Integer minY, Integer maxY,
	                                                 String YAxisText) {
		RootWithController<ChartFormController> load = FXMLManager.load("ChartForm");
		load.getController().setChart(itemsX, minX, maxX, XAxisText, itemsY, minY, maxY, YAxisText);
		Stage stage = FXMLManager.createStage(load.getRoot(), title, 500, 500);
		stage.show();
	}
}

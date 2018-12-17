package KeyboardTrainer.forms.controllers.exercise_player;


import KeyboardTrainer.data.exercise.ExerciseImpl;
import KeyboardTrainer.data.statistics.Statistics;
import KeyboardTrainer.data.user.UserImpl;
import KeyboardTrainer.forms.components.details.DetailsFiller;
import KeyboardTrainer.forms.components.details.DetailsGridPane;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class ExercisePlayerController {
	public GridPane gridPane;
	public Button   breakButton;
	
	private ExerciseVisualizer        exerciseVisualizer;
	private ExerciseManager           exerciseManager;
	private DetailsFiller<Statistics> statisticsDetailsFiller;
	
	public void init() {
		// Тестовые данные
		UserImpl user = new UserImpl();
		ExerciseImpl exercise = new ExerciseImpl("TestExercise", 1, 13, "12345",
		                                         null, 12, 1000, 0);
		//
		
		//noinspection Convert2MethodRef
		exerciseManager = new ExerciseManager(user,
		                                      exercise,
		                                      this::updateStatistics,
		                                      (statistic) -> this.endExercise(statistic));
		
		initExerciseVisualizer();
		initStatisticsDetails(exercise);
		
		// Если об этом станет известно широкому кругу лиц, плохо будет всем, так что не распространяйтесь.
		// 1) Метод init отрабатывает ДО полной отрисовки формы, т.е. некоторые её компоненты ещё не полностью смасштабированы.
		// 2) При масштабировании слетает фон у буквы в богоподобном визуализаторе.
		// 3) Решение - так давайте отрисуем этот фон через очень малое количество секунд после открытия формы, когда уже всё смасштабирвоано!
		// P.S.: Перерисовка фона буквы по событию изменения размера богоподобного визуализатора работает почти нет.
		//TODO: найти событие, срабатывающее после полной отрисовки формы.
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(() -> startExercise());
			}
		}, 100);
	}
	
	private void endExercise(Statistics statistics) {
		System.out.println(statistics);
		breakButton.setDisable(true);
	}
	
	private void initExerciseVisualizer() {
		exerciseVisualizer = exerciseManager.getExerciseVisualizer();
		gridPane.getChildren().add(exerciseVisualizer.getRegion());
		gridPane.getScene().setOnKeyTyped(keyEvent -> exerciseManager.handleKey(keyEvent.getCharacter()));
		GridPane.setMargin(exerciseVisualizer.getRegion(), new Insets(10, 10, 10, 10));
	}
	
	private void initStatisticsDetails(ExerciseImpl exercise) {
		statisticsDetailsFiller = new DetailsFiller<>(
				List.of(new Pair<>("Время", (Statistics stat) -> formatTime(stat.getTotalTime(), "mm:ss")),
				        new Pair<>("Ошибки",
				                   (Statistics stat) -> stat.getErrorsCount() + " / " + exercise.getMaxErrorsCount()),
				        new Pair<>("Ср. время нажатия",
				                   (Statistics stat) -> {
					                   String mask    = "s.SSS";
					                   String current = formatTime(stat.getAveragePressingTime(), mask);
					                   String max     = formatTime(exercise.getMaxAveragePressingTime(), mask);
					                   return current + " / " + max;
				                   }))
		);
		DetailsGridPane detailsGridPane = statisticsDetailsFiller.getDetailsGridPane();
		GridPane.setColumnIndex(detailsGridPane, 1);
		detailsGridPane.getColumnConstraints().get(0).setMinWidth(120);
		gridPane.getChildren().add(detailsGridPane);
	}
	
	private void startExercise() {
		breakButton.setDisable(false);
		exerciseManager.startExercise();
		exerciseVisualizer.getRegion().requestFocus();
	}
	
	private String formatTime(long time, String format) {
		LocalDateTime date = LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault());
		return date.format(DateTimeFormatter.ofPattern(format));
	}
	
	private void updateStatistics(Statistics statistics) {
		statisticsDetailsFiller.fillDetails(statistics);
	}
	
	public void breakExercise() {
		exerciseManager.endExercise();
	}
}
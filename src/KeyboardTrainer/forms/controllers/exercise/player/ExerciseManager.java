package KeyboardTrainer.forms.controllers.exercise.player;


import KeyboardTrainer.data.exercise.Exercise;
import KeyboardTrainer.data.statistics.Statistics;
import KeyboardTrainer.data.user.User;
import javafx.application.Platform;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;


class ExerciseManager {
	private final Exercise             exercise;
	private final Consumer<Statistics> onUpdateStatistics;
	private final EndHandler           onEndExerciseHandler;
	private final String               text;
	private final StatisticsBuilder    statisticsBuilder;
	private final ExerciseVisualizer   exerciseVisualizer;
	
	private ScheduledExecutorService clockExecutor;
	private int                      currentLetterIndex;
	private boolean                  isStarted;
	private boolean                  isFinish;
	
	ExerciseManager(User user,
	                Exercise exercise,
	                Consumer<Statistics> onUpdateStatistics,
	                EndHandler onEndExerciseHandler) {
		isStarted = false;
		isFinish = true;
		
		this.exercise = exercise;
		this.onUpdateStatistics = onUpdateStatistics;
		this.onEndExerciseHandler = onEndExerciseHandler;
		
		text = exercise.getText();
		
		statisticsBuilder = new StatisticsBuilder();
		statisticsBuilder.setUserId(user);
		statisticsBuilder.setExerciseId(exercise);
		
		exerciseVisualizer = new GodlikeVisualizer(exercise.getText());
	}
	
	void startExercise() {
		currentLetterIndex = 0;
		statisticsBuilder.setExerciseId(exercise);
		clockExecutor = Executors.newScheduledThreadPool(1);
		isFinish = false;
		
		exerciseVisualizer.start();
	}
	
	/**
	 * Нужно для начала отсчёта времени после первого нажатия клавиши.
	 * Крутое название. Кто-нибудь, придумайте другое.
	 */
	private void realStartExercise() {
		statisticsBuilder.startBuild();
		
		// Тикает раз в секунду и обновляет статистику
		clockExecutor.scheduleAtFixedRate(() -> Platform.runLater(this::updateState),
		                                  0L,
		                                  1L,
		                                  TimeUnit.SECONDS);
	}
	
	void handleKey(String key) {
		if (isFinish) {
			return;
		}
		if (!isStarted) {
			realStartExercise();
			isStarted = true;
		}
		
		String currentLetter = String.valueOf(text.charAt(currentLetterIndex));
		if (currentLetter.equals(key)
		    || (isEndLine(currentLetter) && isEndLine(key))) {
			exerciseVisualizer.handleGoodKey();
			currentLetterIndex++;
		} else {
			exerciseVisualizer.handleBadKey();
			statisticsBuilder.incrementErrorsCount();
		}
		statisticsBuilder.incrementPressingsCount();
		
		updateState();
	}
	
	private boolean isEndLine(String s) {
		return s.equals("\n") || s.equals("\r");
	}
	
	private void updateState() {
		Statistics currentStatistics = statisticsBuilder.getStatistics();
		onUpdateStatistics.accept(currentStatistics);
		
		if (checkEndExercise(currentStatistics)) {
			endExercise();
		}
	}
	
	private boolean checkEndExercise(Statistics currentStatistics) {
		return currentLetterIndex >= text.length()
		       || currentStatistics.getErrorsCount() >= exercise.getMaxErrorsCount()
		       || currentStatistics.getAveragePressingTime() >= exercise.getMaxAveragePressingTime();
	}
	
	void endExercise() {
		isStarted = false;
		isFinish = true;
		clockExecutor.shutdown();
		Statistics statistics = statisticsBuilder.getStatistics();
		onEndExerciseHandler.endExercise(statistics);
	}
	
	ExerciseVisualizer getExerciseVisualizer() {
		return exerciseVisualizer;
	}
}
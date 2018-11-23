package KeyboardTrainer.forms.exercise;


import KeyboardTrainer.data.exercise.Exercise;
import KeyboardTrainer.data.statistics.Statistics;
import KeyboardTrainer.data.statistics.StatisticsBuilder;
import KeyboardTrainer.data.user.User;


public class ExerciseManager {
	private final User               user;
	private final Exercise           exercise;
	private final ActionHandler      onErrorHandler;
	private final ActionHandler      onPressHandler;
	private final ActionHandler      onTimeUpdateHandler;
	private final EndHandler         onEndExerciseHandler;
	private final String             text;
	private final StatisticsBuilder  statisticsBuilder;
	private final ExerciseVisualizer exerciseVisualizer;
	
	private int     currentLetterIndex;
	private boolean isFinish = true;
	
	public ExerciseManager(User user,
	                       Exercise exercise,
	                       ActionHandler onErrorHandler,
	                       ActionHandler onPressHandler,
	                       ActionHandler onTimeUpdateHandler,
	                       EndHandler onEndExerciseHandler) {
		this.user = user;
		this.exercise = exercise;
		this.onErrorHandler = onErrorHandler;
		this.onPressHandler = onPressHandler;
		this.onTimeUpdateHandler = onTimeUpdateHandler;
		this.onEndExerciseHandler = onEndExerciseHandler;
		
		text = exercise.getText();
		
		statisticsBuilder = new StatisticsBuilder();
		statisticsBuilder.setUserId(user.getId());
		statisticsBuilder.setExerciseId(exercise.getId());
		
		exerciseVisualizer = new GodlikeVisualizer(exercise.getText());
	}
	
	public void startExercise() {
		currentLetterIndex = 0;
		statisticsBuilder.startBuild();
		exerciseVisualizer.start();
		isFinish = false;
	}
	
	public void handleKey(String key) {
		if (isFinish) {
			return;
		}
		
		String currentLetter = String.valueOf(text.charAt(currentLetterIndex));
		if (currentLetter.equals(key)
		    || (isEndLine(currentLetter) && isEndLine(key))) {
			exerciseVisualizer.handleGoodKey();
			currentLetterIndex++;
		} else {
			exerciseVisualizer.handleBadKey();
			statisticsBuilder.incrementErrorsCount();
			onErrorHandler.handle();
		}
		statisticsBuilder.incrementPressingsCount();
		onPressHandler.handle();
		
		if (currentLetterIndex == text.length()) {
			endExercise();
		}
	}
	
	private boolean isEndLine(String s) {
		return s.equals("\n") || s.equals("\r");
	}
	
	public void endExercise() {
		isFinish = true;
		Statistics statistics = statisticsBuilder.stopBuild();
		onEndExerciseHandler.endExercise(statistics);
		exerciseVisualizer.end();
	}
	
	public ExerciseVisualizer getExerciseVisualizer() {
		return exerciseVisualizer;
	}
}
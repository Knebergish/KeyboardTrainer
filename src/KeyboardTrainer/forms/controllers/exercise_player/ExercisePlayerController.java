package KeyboardTrainer.forms.controllers.exercise_player;


import KeyboardTrainer.data.exercise.ExerciseImpl;
import KeyboardTrainer.data.statistics.Statistics;
import KeyboardTrainer.data.user.UserImpl;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;


public class ExercisePlayerController {
	public  GridPane           gridPane;
	public  Button             startButton;
	public  Button             breakButton;
	private ExerciseVisualizer exerciseVisualizer;
	private ExerciseManager    exerciseManager;
	
	public void init() {
		UserImpl user = new UserImpl();
		ExerciseImpl exercise = new ExerciseImpl("TestExercise", 1, 13, "12345",
		                                         null, 12, 100, 0);
		//noinspection Convert2MethodRef
		exerciseManager = new ExerciseManager(user, exercise, () -> System.out.println("Error"),
		                                      () -> System.out.println("Press"),
		                                      () -> System.out.println("Time update"),
		                                      (statistic) -> this.endExercise(statistic));
		
		exerciseVisualizer = exerciseManager.getExerciseVisualizer();
		exerciseVisualizer.getRegion().setPrefHeight(gridPane.getHeight());
		exerciseVisualizer.getRegion().setPrefWidth(gridPane.getPrefWidth() - 100);
		gridPane.getChildren().add(exerciseVisualizer.getRegion());
		gridPane.getScene().setOnKeyTyped(keyEvent -> exerciseManager.handleKey(keyEvent.getCharacter()));
		GridPane.setMargin(exerciseVisualizer.getRegion(), new Insets(10, 10, 10, 10));
	}
	
	private void endExercise(Statistics statistics) {
		System.out.println(statistics);
		startButton.setDisable(false);
		breakButton.setDisable(true);
	}
	
	public void startExercise() {
		startButton.setDisable(true);
		breakButton.setDisable(false);
		exerciseManager.startExercise();
		exerciseVisualizer.getRegion().requestFocus();
	}
	
	public void breakExercise() {
		exerciseManager.endExercise();
	}
}
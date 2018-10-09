package KeyboardTrainer.forms;


import KeyboardTrainer.data.exercise.ExerciseImpl;
import KeyboardTrainer.data.statistics.Statistics;
import KeyboardTrainer.data.user.UserImpl;
import KeyboardTrainer.forms.exercise.ExerciseManager;
import KeyboardTrainer.forms.exercise.ExerciseVisualizer;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;


public class MainForm {
	public  GridPane           gridPane;
	public  Button             startButton;
	public  Button             breakButton;
	private ExerciseVisualizer exerciseVisualizer;
	private ExerciseManager    exerciseManager;
	
	public void init() {
		UserImpl     user     = new UserImpl();
		ExerciseImpl exercise = new ExerciseImpl();
		exerciseManager = new ExerciseManager(user,
		                                      exercise,
		                                      () -> System.out.println("Error"),
		                                      () -> System.out.println("Press"),
		                                      () -> System.out.println("Time update"),
		                                      this::endExercise);
		
		exerciseVisualizer = exerciseManager.getExerciseVisualizer();
		exerciseVisualizer.getRegion().setPrefHeight(gridPane.getHeight());
		exerciseVisualizer.getRegion().setPrefWidth(gridPane.getPrefWidth() - 100);
		gridPane.getChildren().add(exerciseVisualizer.getRegion());
		gridPane.getScene().setOnKeyTyped(keyEvent -> exerciseManager.handleKey(keyEvent.getCharacter()));
		GridPane.setMargin(exerciseVisualizer.getRegion(), new Insets(10, 10, 10, 10));
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
	
	private void endExercise(Statistics statistics) {
		System.out.println(statistics);
		startButton.setDisable(false);
		breakButton.setDisable(true);
	}
}

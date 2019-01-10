package KeyboardTrainer.forms.common;


import KeyboardTrainer.data.exercise.Exercise;
import KeyboardTrainer.forms.common.fxml.FXMLManager;
import KeyboardTrainer.forms.common.fxml.RootWithController;
import KeyboardTrainer.forms.controllers.exercise.player.ExercisePlayerController;
import javafx.stage.Stage;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;


public class Utils {
	public static String formatTime(long time, String format) {
		LocalDateTime date = LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault());
		return date.format(DateTimeFormatter.ofPattern(format));
	}
	
	public static Stage getExercisePlayerStage(Exercise exercise) {
		RootWithController<ExercisePlayerController> load = FXMLManager.load("ExercisePlayer");
		
		Stage stage = FXMLManager.createStage(load.getRoot(), exercise.getName());
		load.getController().init(exercise);
		return stage;
	}
}

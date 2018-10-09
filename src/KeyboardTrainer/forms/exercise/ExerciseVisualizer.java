package KeyboardTrainer.forms.exercise;


import javafx.scene.layout.Region;


public interface ExerciseVisualizer {
	void start();

	void handleGoodKey();

	void handleBadKey();

	void end();

	Region getRegion();
}

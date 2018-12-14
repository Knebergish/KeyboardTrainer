package KeyboardTrainer.forms.exercise;


import javafx.scene.layout.Region;


public interface ExerciseVisualizer {
	void start();
	
	void reset();
	
	void handleGoodKey();
	
	void handleBadKey();
	
	Region getRegion();
}

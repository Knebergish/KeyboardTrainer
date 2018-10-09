package KeyboardTrainer.data.exercise;


import KeyboardTrainer.data.Entity;
import KeyboardTrainer.data.KeyboardZone;
import KeyboardTrainer.data.level.Level;

import java.util.Set;


public interface Exercise extends Entity {
	String getName();
	
	Level getLevel();
	
	int getLength();
	
	String getText();
	
	Set<KeyboardZone> getKeyboardZones();
	
	int getMaxErrorsCount();
	
	long getMaxAveragePressingTime(); // миллисекунды
}

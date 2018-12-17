package KeyboardTrainer.data.statistics;


import KeyboardTrainer.data.Entity;


public interface Statistics extends Entity {
	int getUserId();
	
	int getExerciseId();
	
	int getErrorsCount();
	
	long getAveragePressingTime(); // миллисекунды
	
	long getTotalTime(); // миллисекунды
}

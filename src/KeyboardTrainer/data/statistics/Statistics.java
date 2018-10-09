package KeyboardTrainer.data.statistics;


import KeyboardTrainer.data.Entity;


public interface Statistics extends Entity {
	int getUserId();
	
	int getExerciseId();
	
	long getTotalTime(); // миллисекунды
	
	int getErrorsCount();
	
	long getAveragePressingTime(); // миллисекунды
}

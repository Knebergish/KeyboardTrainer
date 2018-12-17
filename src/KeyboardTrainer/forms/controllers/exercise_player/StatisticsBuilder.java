package KeyboardTrainer.forms.controllers.exercise_player;


import KeyboardTrainer.data.statistics.Statistics;
import KeyboardTrainer.data.statistics.StatisticsImpl;

import java.util.concurrent.TimeUnit;


public class StatisticsBuilder {
	private int  userId;
	private int  exerciseId;
	private long startTime;
	private int  errorsCount    = 0;
	private int  pressingsCount = 0;
	
	void startBuild() {
		errorsCount = 0;
		pressingsCount = 0;
		startTime = System.nanoTime();
	}
	
	void incrementErrorsCount() {
		errorsCount++;
	}
	
	void incrementPressingsCount() {
		pressingsCount++;
	}
	
	Statistics getStatistics() {
		long endTime             = System.nanoTime();
		long totalTime           = startTime == 0 ? 0 : TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
		long averagePressingTime = pressingsCount == 0 ? 0 : totalTime / pressingsCount;
		int  completePercents    = ((pressingsCount - errorsCount) / exerciseId) * 100;
		
		return new StatisticsImpl(-1,
		                          userId,
		                          exerciseId,
		                          totalTime,
		                          errorsCount,
		                          averagePressingTime,
		                          completePercents); //TODO: потом переделать
	}
	
	long getTotalTime() {
		return System.nanoTime() - startTime;
	}
	
	void setExerciseId(int exerciseId) {
		this.exerciseId = exerciseId;
	}
	
	void setUserId(int userId) {
		this.userId = userId;
	}
}

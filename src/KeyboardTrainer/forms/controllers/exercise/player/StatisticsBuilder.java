package KeyboardTrainer.forms.controllers.exercise.player;


import KeyboardTrainer.data.exercise.Exercise;
import KeyboardTrainer.data.statistics.Statistics;
import KeyboardTrainer.data.statistics.StatisticsImpl;
import KeyboardTrainer.data.user.User;

import java.util.concurrent.TimeUnit;


class StatisticsBuilder {
	private User     user;
	private Exercise exercise;
	private long     startTime;
	private int      errorsCount    = 0;
	private int      pressingsCount = 0;
	
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
		int  completePercents    = (int) ((((double) pressingsCount - errorsCount) / exercise.getLength()) * 100);
		
		return new StatisticsImpl(-1,
		                          user.getId(),
		                          exercise.getId(),
		                          totalTime,
		                          errorsCount,
		                          averagePressingTime,
		                          completePercents);
	}
	
	long getTotalTime() {
		return System.nanoTime() - startTime;
	}
	
	void setExerciseId(Exercise exercise) {
		this.exercise = exercise;
	}
	
	void setUserId(User user) {
		this.user = user;
	}
}

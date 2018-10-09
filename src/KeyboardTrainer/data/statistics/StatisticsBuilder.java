package KeyboardTrainer.data.statistics;


import java.util.concurrent.TimeUnit;


public class StatisticsBuilder {
	private int  userId;
	private int  exerciseId;
	private long startTime;
	private int  errorsCount    = 0;
	private int  pressingsCount = 0;
	
	public void startBuild() {
		errorsCount = 0;
		pressingsCount = 0;
		startTime = System.nanoTime();
	}
	
	public Statistics stopBuild() {
		long endTime             = System.nanoTime();
		long totalTime           = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
		long averagePressingTime = totalTime / pressingsCount;
		
		return new StatisticsImpl(-1,
		                          userId,
		                          exerciseId,
		                          totalTime,
		                          errorsCount,
		                          averagePressingTime);
	}
	
	public void incrementErrorsCount() {
		errorsCount++;
	}
	
	public void incrementPressingsCount() {
		pressingsCount++;
	}
	
	public long getTotalTime() {
		return System.nanoTime() - startTime;
	}
	
	public void setExerciseId(int exerciseId) {
		this.exerciseId = exerciseId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
}

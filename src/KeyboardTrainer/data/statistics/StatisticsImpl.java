package KeyboardTrainer.data.statistics;


public class StatisticsImpl implements Statistics {
	private final int  id;
	private final int  userId;
	private final int  exerciseId;
	private final long totalTime;
	private final int  errorsCount;
	private final long averagePressingTime;
	
	
	public StatisticsImpl(int id,
	                      int userId,
	                      int exerciseId,
	                      long totalTime,
	                      int errorsCount,
	                      long averagePressingTime) {
		this.id = id;
		this.userId = userId;
		this.exerciseId = exerciseId;
		this.totalTime = totalTime;
		this.errorsCount = errorsCount;
		this.averagePressingTime = averagePressingTime;
	}
	
	@Override
	public String toString() {
		return "StatisticsImpl{" +
		       "id=" + id +
		       ", userId=" + userId +
		       ", exerciseId=" + exerciseId +
		       ", totalTime=" + totalTime +
		       ", errorsCount=" + errorsCount +
		       ", averagePressingTime=" + averagePressingTime +
		       '}';
	}
	
	@Override
	public int getId() {
		return id;
	}
	
	@Override
	public int getUserId() {
		return userId;
	}
	
	@Override
	public int getExerciseId() {
		return exerciseId;
	}
	
	@Override
	public long getTotalTime() {
		return totalTime;
	}
	
	@Override
	public int getErrorsCount() {
		return errorsCount;
	}
	
	@Override
	public long getAveragePressingTime() {
		return averagePressingTime;
	}
}

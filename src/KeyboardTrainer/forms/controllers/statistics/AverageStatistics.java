package KeyboardTrainer.forms.controllers.statistics;


@SuppressWarnings("WeakerAccess")
public class AverageStatistics {
	private double averageErrorsCount;
	private double averagePressingTime;
	private double averageTotalTime;
	
	public double getAverageErrorsCount() {
		return averageErrorsCount;
	}
	
	public double getAveragePressingTime() {
		return averagePressingTime;
	}
	
	public double getAverageTotalTime() {
		return averageTotalTime;
	}
	
	public void setAverageErrorsCount(double averageErrorsCount) {
		this.averageErrorsCount = averageErrorsCount;
	}
	
	public void setAveragePressingTime(double averagePressingTime) {
		this.averagePressingTime = averagePressingTime;
	}
	
	public void setAverageTotalTime(double averageTotalTime) {
		this.averageTotalTime = averageTotalTime;
	}
}
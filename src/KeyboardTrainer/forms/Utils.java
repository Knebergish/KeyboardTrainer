package KeyboardTrainer.forms;


import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;


public class Utils {
	public static String formatTime(long time, String format) {
		LocalDateTime date = LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault());
		return date.format(DateTimeFormatter.ofPattern(format));
	}
}

package KeyboardTrainer.data.exercise;


import KeyboardTrainer.data.KeyboardZone;

import java.util.HashSet;
import java.util.Set;


// Заглушка
public class ExerciseImpl implements Exercise {
	private final String            name;
	private final int               level;
	private final int               length;
	private final String            text;
	private final Set<KeyboardZone> keyboardZones;
	private final int               maxErrorsCount;
	private final long              maxAveragePressingTime;
	private final int               id;
	
	public ExerciseImpl(String name, int level, int length, String text,
	                    Set<KeyboardZone> keyboardZones, int maxErrorsCount, long maxAveragePressingTime, int id) {
		this.name = name;
		this.level = level;
		this.length = length;
		this.text = text;
		this.maxErrorsCount = maxErrorsCount;
		this.maxAveragePressingTime = maxAveragePressingTime;
		this.id = id;
		
		this.keyboardZones = new HashSet<>();
		for (int i = 0; i < level + 1; i++) {
			this.keyboardZones.add(KeyboardZone.valueOf("ZONE_" + (i + 1)));
		}
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public int getLevel() {
		return level;
	}
	
	@Override
	public int getLength() {
		return length;
	}
	
	@Override
	public String getText() {
		return "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";
//		StringBuilder s = new StringBuilder();
//		for (int i = 0; i < 10; i++) {
//			s.append(i).append("\n");
//		}
//		s.append(0);
//		return s.toString();
	}
	
	@Override
	public Set<KeyboardZone> getKeyboardZones() {
		return keyboardZones;
	}
	
	@Override
	public int getMaxErrorsCount() {
		return maxErrorsCount;
	}
	
	@Override
	public long getMaxAveragePressingTime() {
		return maxAveragePressingTime;
	}
	
	@Override
	public int getId() {
		return id;
	}
}

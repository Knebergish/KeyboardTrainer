package KeyboardTrainer.data.exercise;


import KeyboardTrainer.data.KeyboardZone;

import java.util.Set;


public class ExerciseImpl implements Exercise {
	@Override
	public String getName() {
		return null;
	}
	
	@Override
	public int getLevel() {
		return -13;
	}
	
	@Override
	public int getLength() {
		return 0;
	}
	
	@Override
	public String getText() {
//		return "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < 10; i++) {
			s.append(i).append("\n");
		}
		s.append(0);
		return s.toString();
	}
	
	@Override
	public Set<KeyboardZone> getKeyboardZones() {
		return null;
	}
	
	@Override
	public int getMaxErrorsCount() {
		return 0;
	}
	
	@Override
	public long getMaxAveragePressingTime() {
		return 0;
	}
	
	@Override
	public int getId() {
		return 0;
	}
}

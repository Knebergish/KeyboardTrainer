package KeyboardTrainer.data.exercise;


import KeyboardTrainer.data.KeyboardZone;
import KeyboardTrainer.language.Language;

import java.util.Set;


public class ExerciseImpl implements Exercise {
	private String            name;
	private int               level;
	private int               length;
	private String            text;
	private Set<KeyboardZone> keyboardZones;
	private int               maxErrorsCount;
	private long              maxAveragePressingTime;
	private int               id;
	private Language          language;
	
	/**
	 * Modified version. As soon as we can achieve length value from String.lenght() method.
	 * @author AliRakhmaev
	 */
	public ExerciseImpl(String name, int level, String text, Set<KeyboardZone> keyboardZones, int maxErrorsCount,
	                    long maxAveragePressingTime, int id, Language language) {
		this.name = name;
		this.level = level;
		this.length = text.length();
		this.text = text;
		this.maxErrorsCount = maxErrorsCount;
		this.maxAveragePressingTime = maxAveragePressingTime;
		this.id = id;
		this.keyboardZones = keyboardZones;
		this.language = language;
	}

	
	@Override
	public String toString() {
		return "ExerciseImpl{" +
		       "id=" + id +
		       ", name='" + name + '\'' +
		       ", level=" + level +
		       ", length=" + length +
		       ", text='" + text + '\'' +
		       ", keyboardZones=" + keyboardZones +
		       ", maxErrorsCount=" + maxErrorsCount +
		       ", maxAveragePressingTime=" + maxAveragePressingTime +
		       ", language=" + language.toString() +
		       '}';
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
		return text;
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
	
	@Override
	public Language getLanguage() {
		return language;
	}


	public void setName(String name) {
		this.name = name;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setKeyboardZones(Set<KeyboardZone> keyboardZones) {
		this.keyboardZones = keyboardZones;
	}

	public void setMaxErrorsCount(int maxErrorsCount) {
		this.maxErrorsCount = maxErrorsCount;
	}

	public void setMaxAveragePressingTime(long maxAveragePressingTime) {
		this.maxAveragePressingTime = maxAveragePressingTime;
	}
	
	
	public void setLanguage(Language language) {
		this.language = language;
	}
}

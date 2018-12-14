package KeyboardTrainer.language;


import KeyboardTrainer.data.KeyboardZone;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public abstract class AbstractLanguage implements Language {
	private final String                             name;
	protected     Map<KeyboardZone, List<Character>> zonesSymbols;
	
	protected AbstractLanguage(String name) {
		this.name = name;
		zonesSymbols = new HashMap<>();
	}
	
	@Override
	public List<Character> getSymbols(KeyboardZone zone) {
		return zonesSymbols.get(zone);
	}
	
	@Override
	public String getName() {
		return name;
	}
}

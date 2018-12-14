package KeyboardTrainer.language;


import KeyboardTrainer.data.KeyboardZone;

import java.util.List;


public interface Language {
	List<Character> getSymbols(KeyboardZone zone);
	
	String getName();
}

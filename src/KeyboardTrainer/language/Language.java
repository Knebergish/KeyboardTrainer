package KeyboardTrainer.language;


import KeyboardTrainer.data.KeyboardZone;

import java.util.List;


public interface Language {
	String getName();
	
	List<Character> getSymbols(KeyboardZone zone);
}

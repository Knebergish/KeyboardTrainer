package KeyboardTrainer.language;


import KeyboardTrainer.data.KeyboardZone;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


public enum Language {
	RUSSIAN("Русский", Map.of(KeyboardZone.ZONE_1,
	                          Arrays.asList('к', 'е', 'н', 'г', 'а', 'п', 'р', 'о', 'м', 'и', 'т', 'ь', '4', '5', '6',
	                                        '7', ';', '%', ':', '?'),
	                          KeyboardZone.ZONE_2,
	                          Arrays.asList('у', 'в', 'с', 'ш', 'л', 'б', '3', '8', '№', '*'),
	                          KeyboardZone.ZONE_3,
	                          Arrays.asList('ц', 'ы', 'ч', 'щ', 'д', 'ю', '2', '9', '\'', '('),
	                          KeyboardZone.ZONE_4,
	                          Arrays.asList('й', 'ф', 'я', 'ё', 'з', 'ж', 'х', 'э', 'ъ', '1', '0', '!', ')', '-', '=',
	                                        '_', '+', '.', ',')
	                         )),
	ENGLISH("Английский", Map.of(KeyboardZone.ZONE_1,
	                             Arrays.asList('r', 't', 'y', 'u', 'f', 'g', 'h', 'j', 'v', 'b', 'n', 'm', '4', '5',
	                                           '6', '7', '$', '%', '^', '&'),
	                             KeyboardZone.ZONE_2,
	                             Arrays.asList('e', 'd', 'c', 'i', 'k', ',', '3', '8', '#', '*'),
	                             KeyboardZone.ZONE_3,
	                             Arrays.asList('w', 's', 'x', 'o', 'l', '.', '2', '9', '@', '('),
	                             KeyboardZone.ZONE_4,
	                             Arrays.asList('q', 'a', 'z', '`', 'p', ';', '[', '\'', ']', '1', '0', '!', ')', '-',
	                                           '=', '_', '+', '/', '?')
	                            ));
	
	private final String                             name;
	protected     Map<KeyboardZone, List<Character>> zonesSymbols;
	
	Language(String name, Map<KeyboardZone, List<Character>> zonesSymbols) {
		this.name = name;
		this.zonesSymbols = zonesSymbols;
	}
	
	
	public List<Character> getSymbols(KeyboardZone zone) {
		return zonesSymbols.get(zone);
	}
	
	public String getName() {
		return name;
	}
}

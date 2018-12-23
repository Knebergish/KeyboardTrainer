package KeyboardTrainer.language;


import KeyboardTrainer.data.KeyboardZone;

import java.util.Arrays;


public class RussianLanguage extends AbstractLanguage {
	public RussianLanguage() {
		super("Русский");
		
		zonesSymbols.put(KeyboardZone.ZONE_1,
		                 Arrays.asList('к', 'е', 'н', 'г', 'а', 'п', 'р', 'о', 'м', 'и', 'т', 'ь', '4', '5', '6', '7',
		                               ';', '%', ':', '?'));
		zonesSymbols.put(KeyboardZone.ZONE_2,
		                 Arrays.asList('у', 'в', 'с', 'ш', 'л', 'б', '3', '8', '№', '*'));
		zonesSymbols.put(KeyboardZone.ZONE_3,
		                 Arrays.asList('ц', 'ы', 'ч', 'щ', 'д', 'ю', '2', '9', '\'', '('));
		zonesSymbols.put(KeyboardZone.ZONE_4,
		                 Arrays.asList('й', 'ф', 'я', 'ё', 'з', 'ж', 'х', 'э', 'ъ', '1', '0', '!', ')', '-', '=', '_',
		                               '+', '.', ','));
	}
}
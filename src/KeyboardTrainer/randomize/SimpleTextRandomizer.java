package KeyboardTrainer.randomize;


import KeyboardTrainer.data.KeyboardZone;
import KeyboardTrainer.language.Language;
import KeyboardTrainer.language.RussianLanguage;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


public class SimpleTextRandomizer {
	private static final int AVERAGE_WORD_LENGTH   = 5;
	private static final int AVERAGE_NUMBER_LENGTH = 3;
	
	private final Random          random = new Random();
	private final List<Character> letters;
	private final List<Character> digits;
	
	public SimpleTextRandomizer(Language language, KeyboardZone... zones) {
		List<Character> symbols = Arrays.stream(zones).parallel()
		                                .flatMap(zone -> language.getSymbols(zone).stream())
		                                .distinct()
		                                .collect(Collectors.toList());
		
		letters = symbols.parallelStream()
		                 .filter(Character::isLetter)
		                 .collect(Collectors.toList());
		digits = symbols.parallelStream()
		                .filter(Character::isDigit)
		                .collect(Collectors.toList());
	}
	
	public static void main(String[] args) {
		SimpleTextRandomizer randomizer = new SimpleTextRandomizer(new RussianLanguage(), KeyboardZone.ZONE_1,
		                                                           KeyboardZone.ZONE_2, KeyboardZone.ZONE_3,
		                                                           KeyboardZone.ZONE_4);
		String text = randomizer.generateText(300);
		System.out.println(text.length());
		System.out.println(text);
	}
	
	public String generateText(int length) {
		StringBuilder stringBuilder = new StringBuilder(length);
		while (stringBuilder.length() < length) {
			stringBuilder.append(generateSequence(letters, getNormal(AVERAGE_WORD_LENGTH)));
			stringBuilder.append(" ");
			stringBuilder.append(generateSequence(digits, getNormal(AVERAGE_NUMBER_LENGTH)));
			stringBuilder.append(" ");
		}
		
		String text = stringBuilder.substring(0, length - 1);
		text = text.concat(String.valueOf(getRandomSymbol(letters)));
		
		return text;
	}
	
	private String generateSequence(List<Character> symbols, int length) {
		StringBuilder word = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			word.append(getRandomSymbol(symbols));
		}
		return word.toString();
	}
	
	private int getNormal(int value) {
		return (int) Math.abs(value + (random.nextGaussian() / 3) * value) + 1;
	}
	
	private Character getRandomSymbol(List<Character> symbols) {
		return symbols.get(random.nextInt(symbols.size()));
	}
}

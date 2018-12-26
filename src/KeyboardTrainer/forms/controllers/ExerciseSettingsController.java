package KeyboardTrainer.forms.controllers;


import KeyboardTrainer.data.KeyboardZone;
import KeyboardTrainer.data.exercise.Exercise;
import KeyboardTrainer.data.exercise.ExerciseDAO;
import KeyboardTrainer.data.exercise.ExerciseImpl;
import KeyboardTrainer.forms.Validator;
import KeyboardTrainer.forms.Validator.Checker;
import KeyboardTrainer.forms.general.AlertFormManager;
import KeyboardTrainer.language.Language;
import KeyboardTrainer.randomize.SimpleTextRandomizer;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import javafx.util.converter.IntegerStringConverter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;


public class ExerciseSettingsController {
	public TextField           titleTextField;
	public TextField           lengthTextField;
	public TextField           maxErrorsCountTextField;
	public TextField           maxAveragePressingTimeTextField;
	public ChoiceBox<Integer>  levelChoiceBox;
	public ChoiceBox<Language> languageChoiceBox;
	public GridPane            zonesGridPane;
	public Button              generateTextButton;
	public Button              selectFileButton;
	public Button              cancelButton;
	public Button              saveButton;
	public TextArea            textTextArea;
	
	private Exercise  newExercise;
	private Validator validator;
	
	public void init(Exercise exercise) {
		newExercise = null;
		
		for (int i = 1; i < 5; i++) {
			levelChoiceBox.getItems().add(i);
		}
		
		languageChoiceBox.getItems().add(Language.RUSSIAN);
		
		UnaryOperator<TextFormatter.Change> integerFilter = change -> {
			String newText = change.getControlNewText();
			if (newText.matches("([1-9][0-9]*)?")) {
				return change;
			}
			return null;
		};
		lengthTextField.setTextFormatter(
				new TextFormatter<>(new IntegerStringConverter(), 0, integerFilter));
		maxErrorsCountTextField.setTextFormatter(
				new TextFormatter<>(new IntegerStringConverter(), 0, integerFilter));
		maxAveragePressingTimeTextField.setTextFormatter(
				new TextFormatter<>(new IntegerStringConverter(), 100, integerFilter));
		
		
		// Заполнение полей данными из инициализирующего упражнения
		titleTextField.setText(exercise.getName());
		lengthTextField.setText(String.valueOf(exercise.getLength()));
		maxErrorsCountTextField.setText(String.valueOf(exercise.getMaxErrorsCount()));
		maxAveragePressingTimeTextField.setText(String.valueOf(exercise.getMaxAveragePressingTime()));
		levelChoiceBox.getSelectionModel().select(exercise.getLevel() - 1);
		setSelectedKeyboardZones(exercise.getKeyboardZones());
		languageChoiceBox.getSelectionModel().select(Language.RUSSIAN); //TODO: добавить язык в упражнение
		textTextArea.setText(exercise.getText());
		
		textTextArea.setOnKeyTyped(keyEvent -> updateTextLength());
		
		generateTextButton.setOnAction(event -> generateText());
		selectFileButton.setOnAction(event -> loadTextFromFile());
		saveButton.setOnAction(event -> {
			if (!validator.validateOrAlert()) {
				return;
			}
			
			Set<KeyboardZone> zones = getSelectedKeyboardZones();
			
			newExercise = new ExerciseImpl(titleTextField.getText(),
			                               levelChoiceBox.getValue(),
			                               textTextArea.getText().length(),
			                               textTextArea.getText(),
			                               zones,
			                               Integer.valueOf(maxErrorsCountTextField.getText()),
			                               Integer.valueOf(maxAveragePressingTimeTextField.getText()),
			                               exercise.getId());
			
			Window window = titleTextField.getScene().getWindow();
			window.fireEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
		});
		cancelButton.setOnAction(event -> {
			Window window = titleTextField.getScene().getWindow();
			window.fireEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
		});
		
		validator = new Validator(List.of(
				new Checker(() -> titleTextField.getText().length() >= 3,
				            "Слишком короткое название упражнения.",
				            "Название упражнения должно быть не менее 3-х символов."),
				new Checker(() -> {
					if (exercise.getId() == -1 || !exercise.getName().equals(titleTextField.getText())) {
						return ExerciseDAO.getInstance().getByName(titleTextField.getText()) == null;
					} else {
						return true;
					}
				},
				            "Упражнение с таким названием уже существует.",
				            "Придумайте другое название упражнения."),
				new Checker(() -> textTextArea.getText().length() >= 25,
				            "Слишком короткий текст упражнения.",
				            "Длина упражнения должна быть не менее 25-и символов."),
				new Checker(() -> textTextArea.getText().length() <= 300,
				            "Слишком длинный текст упражнения.",
				            "Длина упражнения должна быть не более 300 символов."),
				new Checker(() -> Integer.valueOf(maxErrorsCountTextField.getText()) <=
				                  (double) textTextArea.getText().length() * 0.1,
				            "Максимальное количество ошибок не может превышать 10% от длины упражнения.",
				            "Для данного текста количество ошибок должно быть не более "
				            + ((double) textTextArea.getText().length() * 0.1)),
				new Checker(() -> Integer.valueOf(maxAveragePressingTimeTextField.getText()) >= 100,
				            "Слишком малое среднее время нажатия клавиш.",
				            "Среднее время нажатия клавиш должно быть не менее 100 мс."),
				new Checker(() -> Integer.valueOf(maxAveragePressingTimeTextField.getText()) <= 4000,
				            "Слишком большое среднее время нажатия клавиш.",
				            "Среднее время нажатия клавиш должно быть не более 4000 мс."),
				new Checker(() -> levelChoiceBox.getSelectionModel().getSelectedIndex() + 1
				                  == getSelectedKeyboardZones().size(),
				            "Выбранный уровень не совпадает с количеством выбранных зон клавиатуры.",
				            "Выберите требуемое количество зон клавиатуры или измените уровень упражнения."),
				new Checker(() -> {
					List<Character> allValidCharacters = getAllValidCharacters(
							getCharsInZone(languageChoiceBox.getSelectionModel().getSelectedItem()));
					return textTextArea.getText().chars().parallel()
					                   .mapToObj(c -> (char) c)
					                   .map(Character::toLowerCase)
					                   .allMatch(allValidCharacters::contains);
				},
				            "Текст содержит недопустимые символы.",
				            "Удалите из текста недопустимые символы."),
				new Checker(() -> {
					Set<KeyboardZone> selectedKeyboardZones = getSelectedKeyboardZones();
					Set<KeyboardZone> zonesFromText = getZonesFromText(textTextArea.getText(),
					                                                   getCharsInZone(
							                                                   languageChoiceBox.getSelectionModel()
							                                                                    .getSelectedItem()));
					zonesFromText.remove(null); //TODO: подумать над пробелом и символом перевода строки
					return zonesFromText.parallelStream().allMatch(selectedKeyboardZones::contains);
				},
				            "Текст содержит символы из невыбранных зон.",
				            "Выберите все используемые в тексте зоны.")
		                                 ));
	}
	
	private void updateTextLength() {
		lengthTextField.setText(String.valueOf(textTextArea.getText().length()));
	}
	
	private void generateText() {
		SimpleTextRandomizer simpleTextRandomizer = new SimpleTextRandomizer(languageChoiceBox.getValue(),
		                                                                     getSelectedKeyboardZones());
		textTextArea.setText(simpleTextRandomizer.generateText(Integer.parseInt(lengthTextField.getText())));
		updateTextLength();
	}
	
	private void loadTextFromFile() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Выбор файла с текстом");
		FileChooser.ExtensionFilter extFilter =
				new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt");
		fileChooser.getExtensionFilters().add(extFilter);
		
		//TODO: нормальное получение window
		File file = fileChooser.showOpenDialog(titleTextField.getScene().getWindow());
		if (file == null) {
			return;
		}
		
		List<String> strings;
		try {
			strings = Files.readAllLines(file.toPath());
		} catch (IOException e) {
			AlertFormManager.showAlert(Alert.AlertType.ERROR,
			                           "Ошибка открытия файла",
			                           "Невозможно считать данные из выбранного файла.",
			                           "Проверьте наличие файла и корректность его расширения.",
			                           null);
			e.printStackTrace();
			return;
		}
		String text = String.join("\n", strings);
		
		Language language = languageChoiceBox.getSelectionModel().getSelectedItem();
		
		HashMap<List<Character>, KeyboardZone> charsInZone        = getCharsInZone(language);
		List<Character>                        allValidCharacters = getAllValidCharacters(charsInZone);
		String                                 trueText           = filterChars(text, allValidCharacters);
		
		Set<KeyboardZone> zones = getZonesFromText(trueText, charsInZone);
		setSelectedKeyboardZones(zones);
		
		textTextArea.setText(trueText);
		updateTextLength();
	}
	
	/**
	 * Формирует список всех допустимых символов
	 */
	private List<Character> getAllValidCharacters(HashMap<List<Character>, KeyboardZone> charsInZone) {
		List<Character> allCharacters = charsInZone.keySet().parallelStream()
		                                           .flatMap(Collection::parallelStream).collect(Collectors.toList());
		allCharacters.add(' ');
		allCharacters.add('\n');
		return allCharacters;
	}
	
	/**
	 * Формирует соответствие символов из языка их зоне
	 *
	 * @param language язык, из которого будут взяты символы
	 * @return карта соответствия символов из языка их зоне
	 */
	private HashMap<List<Character>, KeyboardZone> getCharsInZone(Language language) {
		return Arrays.stream(KeyboardZone.values())
		             .collect(Collectors.toMap(language::getSymbols,
		                                       o -> o,
		                                       (characters, characters2) -> null,
		                                       HashMap::new));
	}
	
	/**
	 * Удаляет из строки все символы, не входящие в список допустимых
	 *
	 * @param text            фильтруемая строка
	 * @param validCharacters допустимые символы
	 * @return строка только с допустимыми символами
	 */
	private String filterChars(String text, List<Character> validCharacters) {
		return text.chars().parallel()
		           .mapToObj(c -> (char) c)
		           .filter(c -> validCharacters.contains(Character.toLowerCase(c)))
		           .map(String::valueOf)
		           .collect(Collectors.joining());
	}
	
	/**
	 * Определяет, какие зоны клавиатуры используются в строке
	 *
	 * @param text        анализируемая строка
	 * @param charsInZone соответствие символов их зоне
	 * @return зоны, используемые в строке
	 */
	private Set<KeyboardZone> getZonesFromText(String text, HashMap<List<Character>, KeyboardZone> charsInZone) {
		return text.chars().parallel()
		           .mapToObj(c -> (char) c)
		           .map(c -> charsInZone.get(charsInZone.keySet()
		                                                .parallelStream()
		                                                .filter(chars -> chars.contains(c))
		                                                .findFirst()
		                                                .orElse(null)))
		           .collect(Collectors.toSet());
	}
	
	private Set<KeyboardZone> getSelectedKeyboardZones() {
		return zonesGridPane.getChildren().stream()
		                    .filter(node -> !node.isDisabled())
		                    .map(node -> (CheckBox) node)
		                    .filter(CheckBox::isSelected)
		                    .map(CheckBox::getText)
		                    .map(Integer::valueOf)
		                    .map(KeyboardZone::byNumber)
		                    .collect(Collectors.toSet());
	}
	
	private void setSelectedKeyboardZones(Set<KeyboardZone> zones) {
		zonesGridPane.getChildren().stream()
		             .filter(node -> !node.isDisabled())
		             .map(node -> (CheckBox) node)
		             .filter(checkBox -> zones.contains(KeyboardZone.byNumber(Integer.valueOf(checkBox.getText()))))
		             .forEach(checkBox -> checkBox.setSelected(true));
	}
	
	public Exercise getNewExercise() {
		return newExercise;
	}
}

package KeyboardTrainer.forms.controllers;


import KeyboardTrainer.data.KeyboardZone;
import KeyboardTrainer.data.exercise.Exercise;
import KeyboardTrainer.data.exercise.ExerciseImpl;
import KeyboardTrainer.forms.general.AlertFormManager;
import KeyboardTrainer.language.Language;
import KeyboardTrainer.language.RussianLanguage;
import KeyboardTrainer.randomize.SimpleTextRandomizer;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
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
	
	private Exercise newExercise;
	
	public void init(Exercise exercise) {
		newExercise = null;
		
		for (int i = 1; i < 5; i++) {
			levelChoiceBox.getItems().add(i);
		}
		
		languageChoiceBox.getItems().add(new RussianLanguage());
		
		saveButton.setOnAction(event -> {
			Set<KeyboardZone> zones = getKeyboardZones();
			
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
		
		titleTextField.setText(exercise.getName());
		lengthTextField.setText(String.valueOf(exercise.getLength()));
		maxErrorsCountTextField.setText(String.valueOf(exercise.getMaxErrorsCount()));
		maxAveragePressingTimeTextField.setText(String.valueOf(exercise.getMaxAveragePressingTime()));
		levelChoiceBox.getSelectionModel().select(exercise.getLevel() - 1);
		selectKeyboardZones(exercise.getKeyboardZones());
		languageChoiceBox.getSelectionModel().select(new RussianLanguage()); //TODO: добавить язык в упражнение
		textTextArea.setText(exercise.getText());
		
		generateTextButton.setOnAction(event -> generateText());
		selectFileButton.setOnAction(event -> loadTextFromFile());
	}
	
	private void selectKeyboardZones(Set<KeyboardZone> zones) {
		zonesGridPane.getChildren().stream()
		             .filter(node -> !node.isDisabled())
		             .map(node -> (CheckBox) node)
		             .filter(checkBox -> zones.contains(KeyboardZone.byNumber(Integer.valueOf(checkBox.getText()))))
		             .forEach(checkBox -> checkBox.setSelected(true));
	}
	
	private void generateText() {
		SimpleTextRandomizer simpleTextRandomizer = new SimpleTextRandomizer(languageChoiceBox.getValue(),
		                                                                     getKeyboardZones());
		textTextArea.setText(simpleTextRandomizer.generateText(Integer.parseInt(lengthTextField.getText())));
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
		
		// Формируем соответствие символов их зоне
		HashMap<List<Character>, KeyboardZone> charsInZone =
				Arrays.stream(KeyboardZone.values())
				      .collect(Collectors.toMap(language::getSymbols,
				                                o -> o,
				                                (characters, characters2) -> null,
				                                HashMap::new));
		
		// Формируем список всех допустимых символов выбранного языка
		List<Character> allCharacters = charsInZone.keySet().parallelStream()
		                                           .flatMap(Collection::parallelStream).collect(Collectors.toList());
		allCharacters.add(' ');
		allCharacters.add('\n');
		
		// Оставляем в тексте только допустимые символы
		String trueText = text.chars().parallel()
		                      .mapToObj(c -> (char) c)
		                      .filter(c -> allCharacters.contains(Character.toLowerCase(c)))
		                      .map(String::valueOf)
		                      .collect(Collectors.joining());
		
		// Выбираем зоны, которые используются в тексте
		Set<KeyboardZone> zones = trueText.chars().parallel()
		                                  .mapToObj(c -> (char) c)
		                                  .map(c -> charsInZone.get(charsInZone.keySet()
		                                                                       .parallelStream()
		                                                                       .filter(chars -> chars.contains(c))
		                                                                       .findFirst()
		                                                                       .orElse(null)))
		                                  .collect(Collectors.toSet());
		selectKeyboardZones(zones);
		
		textTextArea.setText(trueText);
	}
	
	private Set<KeyboardZone> getKeyboardZones() {
		return zonesGridPane.getChildren().stream()
		                    .filter(node -> !node.isDisabled())
		                    .map(node -> (CheckBox) node)
		                    .filter(CheckBox::isSelected)
		                    .map(CheckBox::getText)
		                    .map(Integer::valueOf)
		                    .map(KeyboardZone::byNumber)
		                    .collect(Collectors.toSet());
	}
	
	public Exercise getNewExercise() {
		return newExercise;
	}
}

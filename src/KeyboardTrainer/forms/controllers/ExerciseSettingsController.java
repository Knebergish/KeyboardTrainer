package KeyboardTrainer.forms.controllers;


import KeyboardTrainer.data.KeyboardZone;
import KeyboardTrainer.data.exercise.Exercise;
import KeyboardTrainer.data.exercise.ExerciseImpl;
import KeyboardTrainer.language.Language;
import KeyboardTrainer.language.RussianLanguage;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

import java.util.Set;
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
		
		for (int i = 1; i < 6; i++) {
			levelChoiceBox.getItems().add(i);
		}
		levelChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			updateLevel(newValue);
		});
		
		languageChoiceBox.getItems().add(new RussianLanguage());
		
		saveButton.setOnAction(event -> {
			Set<KeyboardZone> zones = zonesGridPane.getChildren().stream()
			                                       .filter(node -> !node.isDisabled())
			                                       .map(node -> (CheckBox) node)
			                                       .filter(CheckBox::isSelected)
			                                       .map(CheckBox::getText)
			                                       .map(Integer::valueOf)
			                                       .map(KeyboardZone::byNumber)
			                                       .collect(Collectors.toSet());
			
			newExercise = new ExerciseImpl(titleTextField.getText(),
			                               levelChoiceBox.getValue(),
			                               Integer.valueOf(lengthTextField.getText()),
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
		levelChoiceBox.getSelectionModel().select(exercise.getLevel());
		zonesGridPane.getChildren().stream()
		             .filter(node -> !node.isDisabled())
		             .map(node -> (CheckBox) node)
		             .filter(checkBox -> exercise.getKeyboardZones()
		                                         .contains(KeyboardZone.byNumber(Integer.valueOf(checkBox.getText()))))
		             .forEach(checkBox -> checkBox.setSelected(true));
		languageChoiceBox.getSelectionModel().select(new RussianLanguage()); //TODO: доавить язык в упражнение
		textTextArea.setText(exercise.getText());
	}
	
	private void updateLevel(Integer level) {
		ObservableList<Node> children = zonesGridPane.getChildren();
		for (int i = 0; i < children.size(); i++) {
			boolean  disable  = i >= level;
			CheckBox checkBox = (CheckBox) children.get(i);
			
			checkBox.setDisable(disable);
			if (disable) {
				checkBox.setSelected(false);
			}
		}
	}
	
	public Exercise getNewExercise() {
		return newExercise;
	}
}

package KeyboardTrainer.forms.components;


import KeyboardTrainer.data.KeyboardZone;
import KeyboardTrainer.data.exercise.Exercise;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;


// Надо бы унаследовать от GridPane и встраивать в форму, но лень.


/**
 * Умеет заполнять GridPane информацией о конкретном упражнении.
 */
public class DetailsFiller {
	private final int ROW_HEIGHT = 20;
	
	// Список свойств упражнения. Пара - название свойства и функция получения его значения из объекта Exercise
	private final List<Pair<String, Function<Exercise, String>>> mappers;
	
	// Список полей для вывода информации на форму
	private final List<Pair<Label, Label>> rows;
	
	public DetailsFiller(GridPane detailsGridPane) {
		mappers = new ArrayList<>();
		mappers.add(new Pair<>("Длина", exercise -> String.valueOf(exercise.getLength())));
		mappers.add(new Pair<>("Зоны", exercise -> exercise.getKeyboardZones()
		                                                   .parallelStream()
		                                                   .map(KeyboardZone::getNumber)
		                                                   .sorted()
		                                                   .map(Objects::toString)
		                                                   .collect(Collectors.joining(", "))));
		mappers.add(new Pair<>("Макс. кол-во ошибок", exercise -> String.valueOf(exercise.getMaxErrorsCount())));
		mappers.add(new Pair<>("Макс. ср. время нажатия клавиш:",
		                       exercise -> String.valueOf(exercise.getMaxAveragePressingTime())));
		mappers.add(new Pair<>("Язык", exercise -> String.valueOf("Русский")));
		
		///////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Создаём необходимое количество строк в таблице и вставляем в них поля
		rows = new ArrayList<>(mappers.size());
		for (int i = 0; i < mappers.size(); i++) {
			rows.add(new Pair<>(new Label(mappers.get(i).getKey()), new Label("")));
			RowConstraints rowConstraints = new RowConstraints();
			rowConstraints.setPrefHeight(ROW_HEIGHT);
			detailsGridPane.getRowConstraints().add(rowConstraints);
			detailsGridPane.add(rows.get(i).getKey(), 0, i);
			detailsGridPane.add(rows.get(i).getValue(), 1, i);
		}
		
		// Читы для вывода нормальной рамки ячеек таблицы
		detailsGridPane.setStyle("-fx-background-color: black; -fx-padding: 1; -fx-hgap: 1; -fx-vgap: 1;");
		detailsGridPane.setSnapToPixel(false); //???
		for (Node n : detailsGridPane.getChildren()) {
			Control control = (Control) n;
			control.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			control.setStyle(
					"-fx-background-color: -fx-background; -fx-padding: 0 0 0 5; -fx-text-alignment: left");
		}
		
		// Обрезаем лишнюю высоту
		detailsGridPane.setMaxHeight(rows.size() * ROW_HEIGHT);
	}
	
	public void fillDetails(Exercise exercise) {
		for (int i = 0; i < mappers.size(); i++) {
			String value = exercise == null ? "" : mappers.get(i).getValue().apply(exercise);
			rows.get(i).getValue().setText(value);
		}
	}
}
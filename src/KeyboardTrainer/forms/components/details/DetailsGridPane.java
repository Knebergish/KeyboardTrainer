package KeyboardTrainer.forms.components.details;


import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DetailsGridPane extends GridPane {
	private final int ROW_HEIGHT = 20;
	
	// Список полей для вывода информации на форму
	private final Map<String, Label> detailLabels;
	
	
	public DetailsGridPane(List<String> rowsNames) {
		detailLabels = new HashMap<>();
		
		ColumnConstraints primoColumn = new ColumnConstraints();
		primoColumn.setHgrow(Priority.SOMETIMES);
		this.getColumnConstraints().add(primoColumn);
		ColumnConstraints secundoColumn = new ColumnConstraints();
		secundoColumn.setHgrow(Priority.SOMETIMES);
		secundoColumn.setMinWidth(80);
		secundoColumn.setPrefWidth(80);
		secundoColumn.setMaxWidth(80);
		this.getColumnConstraints().add(secundoColumn);
		
		GridPane.setValignment(this, VPos.TOP);
		GridPane.setMargin(this, new Insets(10, 10, 0, 0));
		
		// Создаём необходимое количество строк в таблице и вставляем в них поля
		for (int i = 0; i < rowsNames.size(); i++) {
			Label key   = new Label(rowsNames.get(i) + ":");
			Label value = new Label("");
			
			detailLabels.put(rowsNames.get(i), value);
			
			RowConstraints rowConstraints = new RowConstraints();
			rowConstraints.setPrefHeight(ROW_HEIGHT);
			this.getRowConstraints().add(rowConstraints);
			this.add(key, 0, i);
			this.add(value, 1, i);
		}
		
		// Читы для вывода нормальной рамки ячеек таблицы
		this.setStyle("-fx-background-color: black; -fx-padding: 1; -fx-hgap: 1; -fx-vgap: 1;");
		this.setSnapToPixel(false); //???
		for (Node n : this.getChildren()) {
			Control control = (Control) n;
			control.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			control.setStyle(
					"-fx-background-color: -fx-background; -fx-padding: 0 0 0 5; -fx-text-alignment: left");
		}
		
		// Обрезаем лишнюю высоту
		this.setMaxHeight(detailLabels.size() * ROW_HEIGHT);
	}
	
	public void setValue(String key, String value) {
		detailLabels.get(key).setText(value);
	}
}

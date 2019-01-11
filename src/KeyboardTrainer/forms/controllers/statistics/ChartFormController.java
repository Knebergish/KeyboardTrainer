package KeyboardTrainer.forms.controllers.statistics;


import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;

import java.util.List;


/**
 * Очень умно конструирует график на форме.
 */
public class ChartFormController {
	private static final double ANCHOR = 10;
	
	@FXML
	private AnchorPane anchorPane;
	
	<T extends Number> void setChart(List<T> itemsX, Integer minX, Integer maxX, String XAxisText,
	                                 List<T> itemsY, Integer minY, Integer maxY, String YAxisText) {
		if (itemsX.size() != itemsY.size()) {
			throw new IllegalArgumentException("Наборы значений должны быть одинакового размера: "
			                                   + itemsX.size() + " " + itemsY.size());
		}
		
		XYChart.Series<Number, Number> series = new XYChart.Series<>();
		for (int i = 0; i < itemsY.size(); i++) {
			XYChart.Data<Number, Number> data = new XYChart.Data<>(itemsX.get(i), itemsY.get(i));
			series.getData().add(data);
		}
		
		NumberAxis xAxis = new NumberAxis(XAxisText, minX, maxX, 1);
		NumberAxis yAxis = new NumberAxis(YAxisText, minY, maxY, 1);
		
		LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
		lineChart.getData().add(series);
		lineChart.setLegendVisible(false); // Скрытие области названий серий
		
		AnchorPane.setBottomAnchor(lineChart, ANCHOR);
		AnchorPane.setTopAnchor(lineChart, ANCHOR);
		AnchorPane.setLeftAnchor(lineChart, ANCHOR);
		AnchorPane.setRightAnchor(lineChart, ANCHOR);
		
		// Всплывающие подсказки при наведении на точки
		ObservableList<XYChart.Data<Number, Number>> dataList = lineChart.getData().get(0).getData();
		dataList.forEach(data -> {
			Node    node    = data.getNode();
			Tooltip tooltip = new Tooltip('(' + data.getXValue().toString() + ';' + data.getYValue() + ')');
			Tooltip.install(node, tooltip);
		});
		
		anchorPane.getChildren().add(lineChart);
	}
}

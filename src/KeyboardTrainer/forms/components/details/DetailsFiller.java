package KeyboardTrainer.forms.components.details;


import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * Умеет заполнять DetailsGridPane информацией о конкретном объекте.
 */
public class DetailsFiller<T> {
	// Список свойств объекта. Пара - название свойства и функция получения его значения из объекта.
	private final List<Pair<String, Function<T, String>>> mappers;
	
	private final DetailsGridPane detailsGridPane;
	
	public DetailsFiller(List<Pair<String, Function<T, String>>> mappers) {
		this.mappers = new ArrayList<>(mappers);
		
		this.detailsGridPane = new DetailsGridPane(mappers.stream()
		                                                  .map(Pair::getKey)
		                                                  .collect(Collectors.toList()));
	}
	
	public void fillDetails(T object) {
		for (Pair<String, Function<T, String>> mapper : mappers) {
			String value = object == null ? "" : mapper.getValue().apply(object);
			detailsGridPane.setValue(mapper.getKey(), value);
		}
	}
	
	public DetailsGridPane getDetailsGridPane() {
		return detailsGridPane;
	}
}
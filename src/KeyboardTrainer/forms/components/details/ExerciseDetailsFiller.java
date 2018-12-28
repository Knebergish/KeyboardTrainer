package KeyboardTrainer.forms.components.details;


import KeyboardTrainer.data.KeyboardZone;
import KeyboardTrainer.data.exercise.Exercise;
import KeyboardTrainer.forms.common.Utils;
import javafx.util.Pair;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class ExerciseDetailsFiller extends DetailsFiller<Exercise> {
	public ExerciseDetailsFiller() {
		super(List.of(new Pair<>("Длина текста", exercise -> String.valueOf(exercise.getLength())),
		              new Pair<>("Зоны клавиатуры", exercise -> exercise.getKeyboardZones()
		                                                                .parallelStream()
		                                                                .map(KeyboardZone::getNumber)
		                                                                .sorted()
		                                                                .map(Objects::toString)
		                                                                .collect(Collectors.joining(", "))),
		              new Pair<>("Максимальное количество ошибок",
		                         exercise -> String.valueOf(exercise.getMaxErrorsCount())),
		              new Pair<>("Максимальное среднее время нажатия клавиш",
		                         exercise -> Utils.formatTime(exercise.getMaxAveragePressingTime(), "s.SSS")),
		              new Pair<>("Язык", exercise -> exercise.getLanguage().getName())));
		
	}
}

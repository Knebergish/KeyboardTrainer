package KeyboardTrainer.forms.components.details;


import KeyboardTrainer.data.KeyboardZone;
import KeyboardTrainer.data.exercise.Exercise;
import javafx.util.Pair;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class ExerciseDetailsFiller extends DetailsFiller<Exercise> {
	public ExerciseDetailsFiller() {
		super(List.of(new Pair<>("Длина", (Exercise exercise) -> String.valueOf(exercise.getLength())),
		              new Pair<>("Зоны", exercise -> exercise.getKeyboardZones()
		                                                     .parallelStream()
		                                                     .map(KeyboardZone::getNumber)
		                                                     .sorted()
		                                                     .map(Objects::toString)
		                                                     .collect(Collectors.joining(", "))),
		              new Pair<>("Макс. кол-во ошибок", exercise -> String.valueOf(exercise.getMaxErrorsCount())),
		              new Pair<>("Макс. ср. время нажатия клавиш",
		                         exercise -> String.valueOf(exercise.getMaxAveragePressingTime())),
		              new Pair<>("Язык", exercise -> String.valueOf("Русский"))));
		
	}
}

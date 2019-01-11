package KeyboardTrainer.forms.controllers.exercise.player;


import KeyboardTrainer.data.statistics.Statistics;


@FunctionalInterface
public interface EndHandler {
	void endExercise(Statistics statistics);
}
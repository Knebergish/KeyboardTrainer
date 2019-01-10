package KeyboardTrainer.forms.controllers.exercise.player;


import KeyboardTrainer.Session;
import KeyboardTrainer.data.exercise.Exercise;
import KeyboardTrainer.data.statistics.Statistics;
import KeyboardTrainer.data.statistics.StatisticsDAO;
import KeyboardTrainer.forms.common.Utils;
import KeyboardTrainer.forms.common.fxml.FXMLManager;
import KeyboardTrainer.forms.common.fxml.RootWithController;
import KeyboardTrainer.forms.components.details.DetailsFiller;
import KeyboardTrainer.forms.components.details.DetailsGridPane;
import KeyboardTrainer.forms.controllers.exercise.ExerciseResultController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import javafx.util.Pair;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;


public class ExercisePlayerController {
	@FXML
	private CheckBox virtualKeyboardCheckBox;
	@FXML
	private GridPane mainGridPane;
	@FXML
	private GridPane gridPane;
	@FXML
	private Button   breakButton;
	
	private ExerciseManager           exerciseManager;
	private ExerciseVisualizer        exerciseVisualizer;
	private DetailsFiller<Statistics> statisticsDetailsFiller;
	
	private Consumer<Statistics> endHandler;
	
	public void init(Exercise exercise) {
		init(exercise, statistics -> {
			statistics = StatisticsDAO.getInstance().create(statistics);
			RootWithController<ExerciseResultController> rootWithController =
					FXMLManager.load("ExerciseResult");
			rootWithController.getController().init(exercise, statistics);
			Stage stage = FXMLManager.createStage(rootWithController.getRoot(), "Результаты");
			stage.show();
		});
	}
	
	public void init(Exercise exercise, Consumer<Statistics> endHandler) {
		this.endHandler = endHandler;
		
		exerciseManager = new ExerciseManager(Session.getLoggedUser(),
		                                      exercise,
		                                      this::updateStatistics,
		                                      this::endExercise);
		
		initExerciseVisualizer();
		initStatisticsDetails(exercise);
		
		GridPane.setHalignment(breakButton, HPos.RIGHT);
		
		gridPane.getScene().getWindow().setOnCloseRequest(event -> {
			                                                  if (!exerciseManager.isFinish()) {
				                                                  exerciseManager.breakExercise();
			                                                  }
		                                                  }
		                                                 );
		
		// Если об этом станет известно широкому кругу лиц, плохо будет всем, так что не распространяйтесь.
		// 1) Метод init отрабатывает ДО полной отрисовки формы, т.е. некоторые её компоненты ещё не полностью смасштабированы.
		// 2) При масштабировании слетает фон у буквы в богоподобном визуализаторе.
		// 3) Решение - так давайте отрисуем этот фон через очень малое количество секунд после открытия формы, когда уже всё смасштабирвоано!
		// P.S.: Перерисовка фона буквы по событию изменения размера богоподобного визуализатора работает почти нет.
		//TODO: найти событие, срабатывающее после полной отрисовки формы.
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(() -> startExercise());
			}
		}, 100);
		
		KeyboardVisualizer keyboardVisualizer = exerciseManager.getKeyboardVisualizer();
		GridPane.setHalignment(keyboardVisualizer, HPos.CENTER);
		GridPane.setValignment(keyboardVisualizer, VPos.TOP);
		GridPane.setMargin(keyboardVisualizer, new Insets(10, 0, 0, 0));
		mainGridPane.add(keyboardVisualizer, 0, 1);
		
		virtualKeyboardCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
			final int d      = 210; //TODO: проклятые размеры
			final int height = newValue ? d : 0;
			
			RowConstraints rowConstraints = mainGridPane.getRowConstraints().get(1);
			rowConstraints.setMinHeight(height);
			rowConstraints.setPrefHeight(height);
			rowConstraints.setMaxHeight(height);
			mainGridPane.getRowConstraints().set(1, rowConstraints);
			
			double newHeight = mainGridPane.getScene().getWindow().getHeight() + (newValue ? d : -d);
			mainGridPane.getScene().getWindow().setHeight(newHeight);
		});
		
		gridPane.getScene().setOnKeyTyped(keyEvent -> exerciseManager.handleKey(keyEvent.getCharacter()));
	}
	
	private void endExercise(Statistics statistics) {
		endHandler.accept(statistics);
		
		Window window = gridPane.getScene().getWindow();
		window.fireEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
	}
	
	private void initExerciseVisualizer() {
		exerciseVisualizer = exerciseManager.getExerciseVisualizer();
		gridPane.getChildren().add(exerciseVisualizer.getRegion());
		GridPane.setMargin(exerciseVisualizer.getRegion(), new Insets(10, 10, 10, 10));
	}
	
	private void initStatisticsDetails(Exercise exercise) {
		statisticsDetailsFiller = new DetailsFiller<>(
				List.of(new Pair<>("Время", (Statistics stat) -> Utils.formatTime(stat.getTotalTime(), "mm:ss")),
				        new Pair<>("Ошибки",
				                   (Statistics stat) -> stat.getErrorsCount() + " / " + exercise.getMaxErrorsCount()),
				        new Pair<>("Ср. время нажатия",
				                   (Statistics stat) -> {
					                   String mask    = "s.SSS";
					                   String current = Utils.formatTime(stat.getAveragePressingTime(), mask);
					                   String max     = Utils.formatTime(exercise.getMaxAveragePressingTime(), mask);
					                   return current + " / " + max;
				                   }))
		);
		DetailsGridPane detailsGridPane = statisticsDetailsFiller.getDetailsGridPane();
		GridPane.setColumnIndex(detailsGridPane, 1);
		detailsGridPane.getColumnConstraints().get(0).setMinWidth(120);
		gridPane.getChildren().add(detailsGridPane);
	}
	
	private void startExercise() {
		breakButton.setDisable(false);
		exerciseManager.startExercise();
		exerciseVisualizer.getRegion().requestFocus();
	}
	
	private void updateStatistics(Statistics statistics) {
		statisticsDetailsFiller.fillDetails(statistics);
	}
	
	public void breakExercise() {
		Window window = gridPane.getScene().getWindow();
		window.fireEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
	}
}
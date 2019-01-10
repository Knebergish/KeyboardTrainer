package KeyboardTrainer.forms.controllers;


import KeyboardTrainer.data.exercise.Exercise;
import KeyboardTrainer.data.exercise.ExerciseDAO;
import KeyboardTrainer.data.statistics.StatisticsDAO;
import KeyboardTrainer.data.user.User;
import KeyboardTrainer.data.user.UserDAO;
import KeyboardTrainer.forms.common.fxml.FXMLManager;
import KeyboardTrainer.forms.components.UsersList;
import KeyboardTrainer.forms.components.tree.exercise.ExerciseTree;
import KeyboardTrainer.forms.controllers.statistics.AverageStatistics;
import KeyboardTrainer.forms.controllers.statistics.AverageStatisticsController;
import KeyboardTrainer.forms.general.ContentArea;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.stream.Collectors;


public class StatisticsController implements ContentArea {
	@FXML
	private GridPane componentsGridPane;
	@FXML
	private Button   exerciseStatisticsButton;
	@FXML
	private Button   userStatisticsButton;
	@FXML
	private Button   fullStatisticsButton;
	
	private Exercise selectedExercise = null;
	private User     selectedUser     = null;
	
	@Override
	public void init() {
		initExerciseTree();
		initUsersListVBox();
		
		exerciseStatisticsButton.setOnAction(event -> {
			AverageStatistics averageStatisticsForUser =
					StatisticsDAO.getInstance().getAverageStatisticsForExercise(selectedExercise.getId());
			String title = "Статистика упражнения " + selectedExercise.getName();
			
			showStatistics(averageStatisticsForUser, title);
		});
		userStatisticsButton.setOnAction(event -> {
			AverageStatistics averageStatisticsForUser =
					StatisticsDAO.getInstance().getAverageStatisticsForUser(selectedUser.getId());
			String title = "Статистика пользователя " + selectedUser.getLogin();
			
			showStatistics(averageStatisticsForUser, title);
		});
		fullStatisticsButton.setOnAction(event -> {
			AverageStatistics averageStatisticsForUser =
					StatisticsDAO.getInstance().getAverageUserStatisticsForExercise(selectedUser.getId(),
					                                                                selectedExercise.getId());
			String title = "Статистика для";
			
			showStatistics(averageStatisticsForUser, title);
		});
		updateButtonsDisable();
	}
	
	private void showStatistics(AverageStatistics averageStatistics, String title) {
		var   controller = new AverageStatisticsController(averageStatistics);
		var   load       = FXMLManager.load("AverageStatistics", controller);
		Stage stage      = FXMLManager.createStage(load.getRoot(), title);
		load.getController().init();
		stage.show();
	}
	
	private void initUsersListVBox() {
		UsersList usersList = new UsersList(user -> {
			selectedUser = user;
			updateButtonsDisable();
		});
		
		usersList.setUsersList(UserDAO.getInstance().getAll().parallelStream()
		                              .filter(user -> !user.isAdmin())
		                              .collect(Collectors.toList()));
		usersList.setPadding(new Insets(10, 10, 0, 10));
		componentsGridPane.add(usersList, 1, 0);
	}
	
	private void initExerciseTree() {
		ExerciseTree exercisesTree = new ExerciseTree(exerciseTreeItem -> {
			if (exerciseTreeItem != null && exerciseTreeItem.isExercise()) {
				selectedExercise = exerciseTreeItem.getExercise();
			} else {
				selectedExercise = null;
			}
			updateButtonsDisable();
		});
		exercisesTree.setExercises(ExerciseDAO.getInstance().getAll());
		GridPane.setMargin(exercisesTree, new Insets(10, 10, 0, 10));
		componentsGridPane.add(exercisesTree, 0, 0);
	}
	
	private void updateButtonsDisable() {
		exerciseStatisticsButton.setDisable(selectedExercise == null);
		userStatisticsButton.setDisable(selectedUser == null);
		fullStatisticsButton.setDisable(selectedExercise == null || selectedUser == null);
	}
}

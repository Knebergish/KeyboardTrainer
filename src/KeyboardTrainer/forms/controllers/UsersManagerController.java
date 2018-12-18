package KeyboardTrainer.forms.controllers;


import KeyboardTrainer.data.user.User;
import KeyboardTrainer.data.user.UserImpl;
import KeyboardTrainer.forms.general.ContentArea;
import javafx.collections.FXCollections;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class UsersManagerController implements ContentArea {
	public TextField      searchTextField;
	public ListView<User> usersListView;
	public Label          loginLabel;
	public CheckBox       disabledCheckBox;
	public Button         deleteButton;
	public Button         saveButton;
	
	private List<User> users;
	private User       selectedUser;
	
	@Override
	public void init() {
		initUsersListView();
		
		deleteButton.setOnAction(event -> {
			deleteUser(selectedUser);
		});
		
		saveButton.setOnAction(event -> {
			User user = new UserImpl(selectedUser.getId(),
			                         selectedUser.getLogin(),
			                         selectedUser.getPassword(),
			                         selectedUser.isAdmin(),
			                         disabledCheckBox.isSelected());
			deleteUser(selectedUser);
			users.add(user);
			users.sort(Comparator.comparingInt(User::getId));
			selectUser(user);
		});
	}
	
	private void deleteUser(User user) {
		users.remove(user);
		usersListView.refresh();
		selectUser(null);
	}
	
	private void initUsersListView() {
		usersListView.setCellFactory(param -> new ListCell<>() {
			@Override
			protected void updateItem(User item, boolean empty) {
				super.updateItem(item, empty);
				if (item != null && !empty) {
					setText(item.getLogin());
				} else {
					setText(null);
					setGraphic(null);
				}
			}
		});
		usersListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			selectUser(newValue);
		});
		
		
		users = new ArrayList<>();
		// Тестовые данные
		for (int i = 0; i < 10; i++) {
			User user = new UserImpl(i,
			                         "Пользователь " + i,
			                         String.valueOf(i * 10 + i),
			                         false,
			                         i % 2 == 0);
			
			users.add(user);
		}
		usersListView.setItems(FXCollections.observableList(users));
		//
	}
	
	private void selectUser(User user) {
		selectedUser = user;
		
		String  login;
		boolean disabled;
		
		if (user == null) {
			login = "";
			disabled = false;
		} else {
			login = user.getLogin();
			disabled = user.isDisabled();
		}
		
		loginLabel.setText(login);
		disabledCheckBox.setSelected(disabled);
	}
}

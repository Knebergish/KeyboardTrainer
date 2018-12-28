package KeyboardTrainer.forms.controllers;


import KeyboardTrainer.data.user.User;
import KeyboardTrainer.data.user.UserDAO;
import KeyboardTrainer.data.user.UserImpl;
import KeyboardTrainer.forms.general.ContentArea;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;
import java.util.stream.Collectors;


public class UsersManagerController implements ContentArea {
	@FXML
	private TextField      searchTextField;
	@FXML
	private ListView<User> usersListView;
	@FXML
	private Label          loginLabel;
	@FXML
	private CheckBox       disabledCheckBox;
	@FXML
	private Button         deleteButton;
	@FXML
	private Button         saveButton;
	
	private List<User> users;
	private User       selectedUser;
	
	@Override
	public void init() {
		initUsersListView();
		
		searchTextField.setOnKeyTyped(event -> updateUsersListView());
		deleteButton.setOnAction(event -> deleteSelectedUser());
		saveButton.setOnAction(event -> saveSelectedUser());
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
			selectedUser = newValue;
			updateSelectedUserDetails();
		});
		
		users = UserDAO.getInstance().getAll().parallelStream()
		               .filter(user -> !user.isAdmin())
		               .collect(Collectors.toList());
		updateUsersListView();
	}
	
	private void updateUsersListView() {
		User temp = selectedUser;
		
		List<User> filteredUsers = filterUsers();
		usersListView.setItems(FXCollections.observableList(filteredUsers));
		usersListView.refresh();
		
		selectedUser = temp;
	}
	
	private List<User> filterUsers() {
		return users.parallelStream()
		            .filter(user -> user.getLogin()
		                                .toLowerCase()
		                                .contains(searchTextField.getText().toLowerCase()))
		            .collect(Collectors.toList());
	}
	
	private void deleteSelectedUser() {
		users.remove(selectedUser);
		UserDAO.getInstance().delete(selectedUser.getId());
		selectedUser = null;
		
		updateUsersListView();
		updateSelectedUserDetails();
	}
	
	private void saveSelectedUser() {
		User user = new UserImpl(selectedUser.getId(),
		                         selectedUser.getLogin(),
		                         selectedUser.getPassword(),
		                         selectedUser.isAdmin(),
		                         disabledCheckBox.isSelected());
		UserDAO.getInstance().set(user);
		users.set(users.indexOf(selectedUser), user);
		
		updateUsersListView();
		selectedUser = user;
		usersListView.getSelectionModel().select(selectedUser);
	}
	
	private void updateSelectedUserDetails() {
		String  login;
		boolean disabled;
		
		if (selectedUser == null) {
			login = "";
			disabled = false;
		} else {
			login = selectedUser.getLogin();
			disabled = selectedUser.isDisabled();
		}
		
		loginLabel.setText(login);
		disabledCheckBox.setSelected(disabled);
	}
}

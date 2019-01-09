package KeyboardTrainer.forms.controllers;


import KeyboardTrainer.data.user.User;
import KeyboardTrainer.data.user.UserDAO;
import KeyboardTrainer.data.user.UserImpl;
import KeyboardTrainer.forms.components.UsersListVBox;
import KeyboardTrainer.forms.general.ContentArea;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.util.stream.Collectors;


public class UsersManagerController implements ContentArea {
	@FXML
	private HBox     mainHBox;
	@FXML
	private Label    loginLabel;
	@FXML
	private CheckBox disabledCheckBox;
	@FXML
	private Button   deleteButton;
	@FXML
	private Button   saveButton;
	
	private UsersListVBox usersListVBox;
	
	@Override
	public void init() {
		usersListVBox = new UsersListVBox(this::updateSelectedUserDetails);
		usersListVBox.setUsersList(UserDAO.getInstance().getAll().parallelStream()
		                                  .filter(user -> !user.isAdmin())
		                                  .collect(Collectors.toList()));
		mainHBox.getChildren().add(0, usersListVBox);
		
		deleteButton.setOnAction(event -> deleteSelectedUser());
		saveButton.setOnAction(event -> saveSelectedUser());
	}
	
	
	private void deleteSelectedUser() {
		UserDAO.getInstance().delete(usersListVBox.getSelectedUser().getId());
		usersListVBox.deleteUser(usersListVBox.getSelectedUser());
	}
	
	private void saveSelectedUser() {
		User selectedUser = usersListVBox.getSelectedUser();
		User updatedUser = new UserImpl(selectedUser.getId(),
		                                selectedUser.getLogin(),
		                                selectedUser.getPassword(),
		                                selectedUser.isAdmin(),
		                                disabledCheckBox.isSelected());
		UserDAO.getInstance().set(updatedUser);
		
		usersListVBox.saveUser(selectedUser, updatedUser);
	}
	
	private void updateSelectedUserDetails(User selectedUser) {
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

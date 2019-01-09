package KeyboardTrainer.forms.components;


import KeyboardTrainer.data.user.User;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;


public class UsersListVBox extends VBox {
	private TextField      searchTextField;
	private ListView<User> usersListView;
	
	private List<User> users;
	private User       selectedUser;
	
	
	public UsersListVBox(Consumer<User> selectionHandler) {
		initSearchTextField();
		initUsersListView(selectionHandler);
		
		this.setMinWidth(300);
		this.setMaxWidth(300);
		this.setSpacing(5);
		this.setPadding(new Insets(10));
		this.getChildren().addAll(searchTextField, usersListView);
	}
	
	private void initSearchTextField() {
		searchTextField = new TextField();
		searchTextField.setPromptText("Поиск");
		searchTextField.setOnKeyTyped(event -> updateUsersListView());
	}
	
	private void initUsersListView(Consumer<User> selectionHandler) {
		usersListView = new ListView<>();
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
			selectionHandler.accept(selectedUser);
		});
	}
	
	private void updateUsersListView() {
		User temp = selectedUser;
		
		List<User> filteredUsers = getFilteredUsers();
		usersListView.setItems(FXCollections.observableList(filteredUsers));
		usersListView.refresh();
		
		selectedUser = temp;
	}
	
	public void saveUser(User oldUser, User newUser) {
		users.set(users.indexOf(oldUser), newUser);
		
		updateUsersListView();
		selectUser(selectedUser == oldUser ? newUser : selectedUser);
	}
	
	public void deleteUser(User user) {
		users.remove(user);
		
		updateUsersListView();
		selectUser(selectedUser == user ? null : selectedUser);
	}
	
	private void selectUser(User user) {
		selectedUser = user;
		usersListView.getSelectionModel().select(selectedUser);
	}
	
	private List<User> getFilteredUsers() {
		return users.parallelStream()
		            .filter(user -> user.getLogin()
		                                .toLowerCase()
		                                .contains(searchTextField.getText().toLowerCase()))
		            .collect(Collectors.toList());
	}
	
	public User getSelectedUser() {
		return selectedUser;
	}
	
	public void setUsersList(List<User> users) {
		this.users = users;
		updateUsersListView();
	}
}

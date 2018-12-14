package KeyboardTrainer.forms.general;


import KeyboardTrainer.forms.general.menu.MenuButton;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;
import javafx.stage.Stage;


/**
 * Общая форма, содержащая область с кнопками меню и область для вывода контента.
 */
public class GeneralForm {
	private final Stage      stage;
	private final VBox       mainBox;
	private final GridPane   menuGridPane;
	private final AnchorPane contentPane;
	
	private int currentButtonNumber;
	
	
	public GeneralForm() {
		currentButtonNumber = 0;
		
		menuGridPane = new GridPane();
		menuGridPane.setMinHeight(70);
		RowConstraints rowConstraints = new RowConstraints();
		rowConstraints.setVgrow(Priority.SOMETIMES);
		rowConstraints.setValignment(VPos.CENTER);
		menuGridPane.getRowConstraints().add(rowConstraints);
		
		Separator separator = new Separator();
		
		contentPane = new AnchorPane();
		contentPane.setMinHeight(300);
		contentPane.setPrefWidth(600);
		
		mainBox = new VBox();
		mainBox.getChildren().addAll(menuGridPane, separator, contentPane);
		
		stage = new Stage();
		stage.setScene(new Scene(mainBox));
		stage.sizeToScene();
		stage.setResizable(false);
	}
	
	public void show() {
		stage.show();
	}
	
	public void addMenuButton(MenuButton button) {
		ColumnConstraints columnConstraints = new ColumnConstraints();
		columnConstraints.setHalignment(HPos.CENTER);
		columnConstraints.setHgrow(Priority.SOMETIMES);
		menuGridPane.getColumnConstraints().add(columnConstraints);
		
		button.setGeneralForm(this);
		menuGridPane.add(button, currentButtonNumber++, 0);
	}
	
	public VBox getMainBox() {
		return mainBox;
	}
	
	public GridPane getMenuGridPane() {
		return menuGridPane;
	}
	
	public Pane getContentPane() {
		return contentPane;
	}
	
	public Stage getStage() {
		return stage;
	}
	
	public void setContent(Parent root) {
		AnchorPane.setLeftAnchor(root, .0);
		AnchorPane.setBottomAnchor(root, .0);
		AnchorPane.setRightAnchor(root, .0);
		AnchorPane.setTopAnchor(root, .0);
		contentPane.getChildren().clear();
		contentPane.getChildren().add(root);
	}
	
	public void setTitle(String title) {
		stage.setTitle(title);
	}
}

package KeyboardTrainer.forms.general;


import KeyboardTrainer.forms.common.fxml.FXMLManager;
import KeyboardTrainer.forms.common.fxml.RootWithController;
import KeyboardTrainer.forms.general.menu.MenuButton;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;


/**
 * Общая форма, содержащая область с кнопками меню и область для вывода контента.
 */
public abstract class GeneralForm {
	private final Stage      stage;
	private final GridPane   menuGridPane;
	private final AnchorPane contentPane;
	
	private int currentButtonNumber;
	
	
	GeneralForm() {
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
		contentPane.setMaxHeight(300);
		contentPane.setPrefWidth(600);
		
		VBox mainBox = new VBox();
		mainBox.getChildren().addAll(menuGridPane, separator, contentPane);
		
		stage = new Stage();
		stage.getIcons().add(new Image("file:res/icon.jpg"));
		stage.setScene(new Scene(mainBox));
		stage.sizeToScene();
		stage.setResizable(false);
		stage.setOnCloseRequest(event -> System.exit(0));
		
		RootWithController<Object> logo = FXMLManager.load("Logo");
		setContent(logo.getRoot());
	}
	
	public void show() {
		stage.show();
	}
	
	void addMenuButton(MenuButton button) {
		ColumnConstraints columnConstraints = new ColumnConstraints();
		columnConstraints.setHalignment(HPos.CENTER);
		columnConstraints.setHgrow(Priority.SOMETIMES);
		menuGridPane.getColumnConstraints().add(columnConstraints);
		
		button.setGeneralForm(this);
		menuGridPane.add(button, currentButtonNumber++, 0);
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
	
	void setTitle(String title) {
		stage.setTitle(title);
	}
}

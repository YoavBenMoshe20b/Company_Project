package view;

import controller.Controller;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class View extends Application {
	private Controller controller;
	@Override
	public void start(Stage stage) throws Exception {
		controller = new Controller();
		stage.setTitle("Company simulation");
		BorderPane bp = new BorderPane();
		Text companyDA = new Text("Company was not created");
		Text profitPerHour = new Text();
		FlowPane fp = new FlowPane(companyDA,profitPerHour);
		
		
		GridPane gp = new GridPane();
		gp.setHgap(10);
		gp.setVgap(10);
		Text txCompany = new Text("Company:");
		TextField tfCompany = new TextField();
		
		
		Text txProfitHour = new Text("Profit Hour:");
		TextField tfProfitHour = new TextField();
		
		Button btn = new Button();
	
		btn.setText("Save");
		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				companyDA.setText(tfCompany.getText());
				profitPerHour.setText(tfProfitHour.getText());
			}
			
		});
		gp.add(txCompany, 0, 0);
		gp.add(tfCompany, 1, 0);
		gp.add(txProfitHour, 0, 1);
		gp.add(tfProfitHour, 1, 1);
		gp.add(btn, 0, 2,2,1);
		
		MenuBar mb = new MenuBar();
		Menu m1 = new Menu("Department");
		mb.getMenus().add(m1);
		
		MenuItem mIDepartment = new MenuItem("Add department");
		mIDepartment.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				GridPane gpDepartment = new GridPane();
				gpDepartment.setHgap(10);
				gpDepartment.setVgap(10);
				Text txtName = new Text("Name:");
				TextField tfName = new TextField();
				CheckBox cbIsSync =new  CheckBox();
				CheckBox cbIsChange = new CheckBox();
				Text txtIsSync = new Text("is sync");
				Text txtIsChange = new Text("is change");
				Button btnSaveDepartment = new Button("Save ");
				 btnSaveDepartment.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						if(controller.isEmptyCompany() == true) {
							Alert alert = new Alert(AlertType.ERROR);
							alert.setTitle("Create Department");
							alert.setContentText("Company does not exist");
							alert.showAndWait();
							return;
						}
						//controller.createDepartment(tfName.getText(),cbIsSync.isSelected() ,cbIsChange.isSelected(), );
					}
					 
				 });
				
				gpDepartment.add(txtName, 0, 0);
				gpDepartment.add(tfName, 1, 0);
				gpDepartment.add(txtIsSync,0,1);
				gpDepartment.add(txtIsChange,1,1);
				gpDepartment.add(cbIsSync,0,2);
				gpDepartment.add(cbIsChange,1,2);
				gpDepartment.add( btnSaveDepartment,0,3, 2, 1);
				bp.setBottom(gpDepartment);
				
			}
		});
		m1.getItems().add(mIDepartment);
		
		VBox vb = new VBox(mb);
		bp.setCenter(fp);
		bp.setTop(mb);
		bp.setBottom(gp);
		
		Scene scene = new Scene(bp,300,200);
		
		stage.setScene(scene);
		stage.show();
		
	}
	public static void main(String[] args) {
		launch(args);
	}
	
	
	
}

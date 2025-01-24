package view;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import controller.Controller;
import model.Department;
import model.WorkingHour;
import program.Program;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class DepartmentView extends AbstractView {

	public DepartmentView(Controller controller, Program view) {
		super(controller, view);
	}

	@Override
	public GridPane addEntity() {
		GridPane gpRoot = new GridPane();
		gpRoot.setPadding(new Insets(35));
		gpRoot.setAlignment(Pos.TOP_LEFT);
		gpRoot.setVgap(10);
		gpRoot.setHgap(15);

		WorkingHoursView whView = new WorkingHoursView(controller, view);
		
		// department name field
		Label lblDepartmentName = new Label("Department Name:");
		CheckBox cbIsSync = new CheckBox("Synchronizable");
		CheckBox cbIsChange = new CheckBox("Changable");
		
		TextField tfDepartmentName = new TextField();
		
		Button departmentBtn = new Button("Add Department"); // department button
		
		departmentBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				if (controller.isEmptyCompany()) { // == null?
					view.showErrorMessage("Company does not exist! Please create a company"); // if user tries to create
																							// department
					// before creating a company
					return;
				}
				if (tfDepartmentName.getText().length() == 0) {
					view.showErrorMessage("Department name is empty");
					return;
				} 
				
				
				if(whView.getStartTime().length() == 0 && whView.getEndTime().length() == 0 && whView.getCb() == false) {
					view.showSuccessMessage("department created with default working hours");
				}
			
				WorkingHour wh = null;
				try {
					wh = view.getWorkingHours(whView, "Department");
				}catch(NumberFormatException w) {
					view.showErrorMessage("Start time and end time must be a number\nbetween 00 and 24");
					return;
				}
				
				controller.createDepartment(tfDepartmentName.getText(), cbIsSync.isSelected(), cbIsChange.isSelected(), wh);
				
				view.showSuccessMessage("The Department " + tfDepartmentName.getText() + " was created successfully!");
			}

		});
		
		gpRoot.add(lblDepartmentName, 0, 0);
		gpRoot.add(cbIsSync, 0, 2);
		gpRoot.add(cbIsChange, 0, 3);
		
		gpRoot.add(tfDepartmentName, 1, 0);
		
		lblDepartmentName.setTextFill(Color.BLACK);
		cbIsSync.setTextFill(Color.BLACK);
		cbIsChange.setTextFill(Color.BLACK);
		departmentBtn.setTextFill(Color.SADDLEBROWN);
		
		lblDepartmentName.setStyle("-fx-font-size: 13px; -fx-font-family: Verdana");
		cbIsSync.setStyle("-fx-font-size: 13px; -fx-font-family: Verdana");
		cbIsChange.setStyle("-fx-font-size: 13px; -fx-font-family: Verdana");
		departmentBtn.setStyle("-fx-font-size: 13px; -fx-font-family: Verdana");

		gpRoot.add(whView.addEntity(), 0, 5, 2, 1);
		gpRoot.add(departmentBtn, 0, 8); // department button

		return gpRoot;
	}

	@Override
	public GridPane showEntity() {
		
		GridPane gpRoot = new GridPane();
		gpRoot.setPadding(new Insets(35));
		gpRoot.setAlignment(Pos.TOP_LEFT);
		gpRoot.setVgap(10);
		gpRoot.setHgap(15);

		Text tDepartment = new Text("All departments in company: ");
		Text tInfo = new Text("In order to delete select department:");
		
		ListView<Department> departemntList = new ListView<>();

		
		for (Department d : controller.getDepartments()) {
			departemntList.getItems().add(d);
		}

		
		
		Button deleteBtn = new Button("Delete");

	
		deleteBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				Department department = (Department) departemntList.getSelectionModel().getSelectedItem();
				if(department == null) {
					view.showErrorMessage("Please select a department");
					return;
				}
				if(department.getRoles().size() != 0 ) {
					view.showErrorMessage("we can not delete department without delete all roles in this deparments");
					return;
				}
				controller.deleteDepartment(department);
				departemntList.getItems().remove(department);
				
			
				
				view.showSuccessMessage("Department" + department.getName() + " deleted successfully");
				
				
				
			}

		});
		gpRoot.add(tInfo, 0, 0);
		gpRoot.add(deleteBtn, 0, 1);
		gpRoot.add(tDepartment, 0, 2);
		gpRoot.add(departemntList, 0,3);
		
		deleteBtn.setTextFill(Color.SADDLEBROWN);
		
		tInfo.setStyle("-fx-font-size: 13px; -fx-font-family: Verdana");
		deleteBtn.setStyle("-fx-font-size: 13px; -fx-font-family: Verdana");
		return gpRoot;
	}
	


}

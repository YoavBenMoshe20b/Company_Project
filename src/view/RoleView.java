package view;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import controller.Controller;
import model.Department;
import model.Role;
import model.WorkingHour;
import program.Program;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class RoleView extends AbstractView{

	public RoleView(Controller controller, Program view) {
		super(controller, view);
	}

	@Override
	public GridPane addEntity() {
		GridPane gpRoot = new GridPane();
		gpRoot.setPadding(new Insets(35));
		gpRoot.setAlignment(Pos.TOP_LEFT);
		gpRoot.setVgap(10);
		gpRoot.setHgap(15);

		// role name label
		Label lblRoleName = new Label("Role Name:");
		
		Text tDepartment = new Text("Select department:");
		
		TextField tfRoleName = new TextField();
		
		CheckBox cbIsSync = new CheckBox("Synchronizable"); 
		CheckBox cbIsChange = new CheckBox("Changable"); 
		
		CheckBox cbIsWork = new CheckBox("Work at home"); 
		
		ComboBox<Department> cbDepartment = new ComboBox<Department>();

		for (Department d : controller.getDepartments()) {
			cbDepartment.getItems().add(d);
		}
		// add button role
		Button roleBtn = new Button("Add Role");
		
		WorkingHoursView whView = new WorkingHoursView(controller, view);

		roleBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				if (tfRoleName.getText().length() == 0) {
					view.showErrorMessage("Role Name Is Empty"); // if role name is empty
					return;
				}
				if(cbDepartment.getValue() == null) {
					view.showErrorMessage("Department Is Empty"); // if role name is empty
					return;
				}
				WorkingHour wh = null;
				try {
					wh = view.getWorkingHours(whView, "Role");
				}catch(NumberFormatException w) {
					view.showErrorMessage("Starting time and ending time must be a number between 00 and 24");
					return;
				}
				
				Department selectedDep = null;
				for (Department d : controller.getCompany().getDepartments()) {
					if(cbDepartment.getValue().equals(d));
					selectedDep = d;
				}
				
				
				//Role r = new Role(tfRoleName.getText(), cbIsSync.isSelected(), cbIsChange.isSelected(), selectedDep , wh);
				controller.createRole(tfRoleName.getText(), cbIsSync.isSelected(), cbIsChange.isSelected(), selectedDep , wh);
				
				
				view.showSuccessMessage("The Role "+ tfRoleName.getText()+" Was Created!");

			}

		});
		
		gpRoot.add(lblRoleName, 0, 0); 
		gpRoot.add(cbIsSync, 0, 2);
		gpRoot.add(cbIsChange, 0, 3);
		gpRoot.add(tDepartment, 0, 5);
		
		gpRoot.add(tfRoleName, 1, 0);
		gpRoot.add(cbDepartment, 1, 5);
		
		lblRoleName.setTextFill(Color.BLACK);
		cbIsSync.setTextFill(Color.BLACK);
		cbIsChange.setTextFill(Color.BLACK);
		roleBtn.setTextFill(Color.SADDLEBROWN);
		
		lblRoleName.setStyle("-fx-font-size: 13px; -fx-font-family: Verdana");
		cbIsSync.setStyle("-fx-font-size: 13px; -fx-font-family: Verdana");
		cbIsChange.setStyle("-fx-font-size: 13px; -fx-font-family: Verdana");
		roleBtn.setStyle("-fx-font-size: 13px; -fx-font-family: Verdana");
		
		gpRoot.add(whView.addEntity(), 0, 6, 2, 1);
		gpRoot.add(roleBtn, 0, 7); // role button
		//bp.setBottom(whView.getView());
		return gpRoot;
	}

	@Override
	public GridPane showEntity() {
		
		GridPane gpRoot = new GridPane();
		gpRoot.setPadding(new Insets(35));
		gpRoot.setAlignment(Pos.TOP_LEFT);
		gpRoot.setVgap(10);
		gpRoot.setHgap(15);

		Text tRole = new Text("Roles:");
		Text tInfo = new Text("In order to delete select role:");
		
		//role list
		ListView<Role> roleList = new ListView<Role>();

		
		for (Department d : controller.getDepartments()) {
			for (Role r : d.getRoles()) {
				roleList.getItems().add(r);
				
			}
		}

		
		
		
		Button deleteBtn = new Button("Delete"); // delete role button

		deleteBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				Role role = roleList.getSelectionModel().getSelectedItem();
				if(role == null) {
					view.showErrorMessage("Please selct a role");
					return;
				}
				if(role.getEmployee() != null) {
					view.showErrorMessage("can not delete this role because there are an employee working in this role");
					return;
				}
				controller.deleteRole(role);
				roleList.getItems().remove(role);
				view.showSuccessMessage("Role deleted successfully");
				
				
				
				
				
			}

		});
		gpRoot.add(tInfo, 0, 0);
		gpRoot.add(deleteBtn, 0, 1);
		gpRoot.add(tRole, 0, 2);
		gpRoot.add(roleList, 0, 3);
		
		deleteBtn.setTextFill(Color.SADDLEBROWN);
		
		tInfo.setStyle("-fx-font-size: 13px; -fx-font-family: Verdana");
		deleteBtn.setStyle("-fx-font-size: 13px; -fx-font-family: Verdana");
		return gpRoot;
	}

}

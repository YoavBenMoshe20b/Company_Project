package view;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import controller.Controller;
import model.Department;
import model.Employee;
import model.Preference;
import model.Role;
import model.WorkingHour;
import model.Preference.preferenceType;
import program.Program;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;


public class EmployeeView extends AbstractView {

	public EmployeeView(Controller controller, Program view) {
		super(controller, view);
	}

	@Override
	public GridPane addEntity() {

		GridPane gpRoot = new GridPane();
		gpRoot.setPadding(new Insets(35));
		gpRoot.setAlignment(Pos.TOP_LEFT);
		gpRoot.setVgap(10);
		gpRoot.setHgap(10);

		Label lblEmployeeName = new Label("Employee Name:");
		Label lblEmployeeAddress = new Label("Employee Address:");
		Label lblSalaryPreference = new Label("Salary Kind:");
		Label lblHour = new Label("Enter Hours:");
		Label lblBonus = new Label("Enter Bonus:");
		
		Text tRole = new Text("Select role:");
		Text tSalaryNote = new Text("If you have chosen the hourly salary or bonus salary please fill in the required fields:");
		
		TextField tfEmployeeName = new TextField();
		TextField tfEmployeeAddress = new TextField();
		TextField tfHour = new TextField();
		tfHour.setDisable(true);
		TextField tfBonus = new TextField();
		tfBonus.setDisable(true);

		ComboBox<Role> cbRole = new ComboBox<Role>();

		for (Department d : controller.getDepartments()) {
			for (Role r : d.getRoles()) {
				if (r.getEmployee() == null)
					cbRole.getItems().add(r);
				
			}

		}

		ComboBox<String> cbPreferenceKind = new ComboBox<String>();
		cbPreferenceKind.getItems().addAll("Basic Salary", "Hour Salary", "Bonus Salary");
		cbPreferenceKind.getSelectionModel().select(0);

		cbPreferenceKind.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				switch (cbPreferenceKind.getValue()) {
				case "Basic Salary":
					tfHour.setDisable(true);
					tfBonus.setDisable(true);
					break;
				case "Hour Salary":
					tfHour.setDisable(false);
					tfBonus.setDisable(true);
					break;
				case "Bonus Salary":
					tfHour.setDisable(true);
					tfBonus.setDisable(false);
					break;
				}

			}

		});

		// Text tBonus = new Text("Enter bonus:");
		

		WorkingHoursView whView = new WorkingHoursView(controller, view);
		
		// gpRoot.add(tBonus, 0, 6);

		Button employeeBtn = new Button("Add Employee"); // add button employee
		
		employeeBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				int getPreference = -1;
				double bon =0.0;
				if (tfEmployeeName.getText().length() == 0) { // if employee name is empty
					view.showErrorMessage("Employee name is empty");
					return;
				}
				if (tfEmployeeAddress.getText().length() == 0) { // if employee address is empty
					view.showErrorMessage("Address is empty");
					return;
				}
				if(cbRole.getValue() == null) {
					view.showErrorMessage("Role is empty");
					return;
				}
				// if not role is available
				if (!(tfEmployeeName.getText().length() == 0 && tfEmployeeAddress.getText().length() == 0)) {

					Preference p = null;
					preferenceType ptb =preferenceType.BONUS;
					preferenceType pth =preferenceType.HOUR;
					preferenceType ptg =preferenceType.GLOBAL;
					
					switch (cbPreferenceKind.getValue()) {
					case "Basic Salary":
						p = Preference.setPreference();
						p.setType(ptg);
						break;
					case "Hour Salary":
						int hours = Integer.parseInt(tfHour.getText());
						p = Preference.setPreference(hours);
						p.setType(pth);
						p.setHourss(hours);
						break;
					case "Bonus Salary":
						double bonus = Double.parseDouble(tfBonus.getText());
						p = Preference.setPreference(bonus);
						p.setType(ptb);
					
						p.setBonus(bonus);
						break;
					}
					

					WorkingHour wh = null;
					try {
						wh = view.getWorkingHours(whView, "Employee");
					} catch (NumberFormatException w) {
						view.showErrorMessage("Starting time and ending time must be a number between 00 and 24");
						return;
					}
					controller.createEmployee(tfEmployeeName.getText(), tfEmployeeAddress.getText(), p, wh, cbRole.getValue());
					
					

					
					
					
					view.showSuccessMessage("The employee " + tfEmployeeName.getText() + " was created successfully!");

				}

			}

		});

		if (cbRole.getItems().size() == 0) {
			view.showErrorMessage("Employee can not be added if there are no available roles");
			employeeBtn.setDisable(true);
		}
		
		gpRoot.add(lblEmployeeName, 0, 0); 
		gpRoot.add(lblEmployeeAddress, 0, 1);
		gpRoot.add(lblSalaryPreference, 0, 2);
		gpRoot.add(tSalaryNote, 0, 3, 2, 1);
		gpRoot.add(lblHour, 0, 5);
		gpRoot.add(lblBonus, 0, 6);
		gpRoot.add(tRole, 0, 9);
		
		gpRoot.add(tfEmployeeName, 1, 0);
		gpRoot.add(tfEmployeeAddress, 1, 1);
		gpRoot.add(cbPreferenceKind, 1, 2);
		gpRoot.add(tfHour, 1, 5);
		gpRoot.add(tfBonus, 1, 6);
		gpRoot.add(cbRole, 1, 9);
				
		lblEmployeeName.setTextFill(Color.BLACK);
		lblEmployeeAddress.setTextFill(Color.BLACK);
		lblSalaryPreference.setTextFill(Color.BLACK);
		lblHour.setTextFill(Color.BLACK);
		lblBonus.setTextFill(Color.BLACK);
		employeeBtn.setTextFill(Color.SADDLEBROWN);
		
		lblEmployeeName.setStyle("-fx-font-size: 13px; -fx-font-family: Verdana");
		lblEmployeeAddress.setStyle("-fx-font-size: 13px; -fx-font-family: Verdana");
		lblSalaryPreference.setStyle("-fx-font-size: 13px; -fx-font-family: Verdana");
		lblHour.setStyle("-fx-font-size: 13px; -fx-font-family: Verdana");
		lblBonus.setStyle("-fx-font-size: 13px; -fx-font-family: Verdana");
		tRole.setStyle("-fx-font-size: 13px; -fx-font-family: Verdana");
		tSalaryNote.setStyle("-fx-font-size: 13px; -fx-font-family: Verdana");
		employeeBtn.setStyle("-fx-font-size: 13px; -fx-font-family: Verdana");

		gpRoot.add(whView.addEntity(), 0, 10, 2, 1);
		gpRoot.add(employeeBtn, 0, 11); // employee button
		return gpRoot;

	}

	@Override
	public GridPane showEntity() {

		GridPane gpRoot = new GridPane();
		gpRoot.setPadding(new Insets(35));
		gpRoot.setAlignment(Pos.TOP_LEFT);
		gpRoot.setVgap(10);
		gpRoot.setHgap(15);

		Text tEmployees = new Text("Employees:");
		Text tInfo = new Text("In order to delete select role:");
		
		// employee list 
		ListView<Employee> employeeList = new ListView<Employee>();

		for (Department d : controller.getDepartments()) {
			for (Role r : d.getRoles()) {
				if (r.getEmployee() != null) {
					employeeList.getItems().add(r.getEmployee());
				}
			}
		}
		
		Button deleteBtn = new Button("Delete"); // delete employee button
		
		deleteBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {

				Employee employee = employeeList.getSelectionModel().getSelectedItem();
				if(employee == null) {
					view.showErrorMessage("Please selcet an employee");
					return;
				}
				controller.deleteEmployee(employee);
				employeeList.getItems().remove(employee);			
				
				view.showSuccessMessage("Employee deleted successfully");
			}

		});
		gpRoot.add(tInfo, 0, 0);
		gpRoot.add(deleteBtn, 0, 1);
		gpRoot.add(tEmployees, 0, 2);
		gpRoot.add(employeeList,0,3);
		
		tEmployees.setStyle("-fx-font-size: 13px; -fx-font-family: Verdana");
		tInfo.setStyle("-fx-font-size: 13px; -fx-font-family: Verdana");
		deleteBtn.setStyle("-fx-font-size: 13px; -fx-font-family: Verdana");
		
		return gpRoot;

	}
	
	


}

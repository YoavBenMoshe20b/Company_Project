package program;

import java.sql.*;

import controller.Controller;
import model.Department;
import model.Employee;
import model.Preference;
import model.Role;
import model.WorkingHour;
import view.CompanyView;
import view.DepartmentView;
import view.EmployeeView;
import view.RoleView;
import view.WorkingHoursView;

import java.io.File;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Program extends Application {

	private Controller controller;

	private MenuBar menuBar;

	private BorderPane bp;

	private EmployeeView employeeView;
	private RoleView roleView;
	private CompanyView companyView;
	private DepartmentView departmentView;

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage stage) throws Exception {
		// title
		stage.setTitle("Company Simulator");
		controller = new Controller();
		bp = new BorderPane();
		// set the background image
		Image image = new Image(new File("123.png").toURI().toString());
		BackgroundSize backGroundSize = new BackgroundSize(70, 70, true, false, true, true);
		BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backGroundSize);
		Background background = new Background(backgroundImage);
		bp.setBackground(background);
		bp.setPadding(new Insets(20));

		// initialize view instances
		employeeView = new EmployeeView(controller, this);
		roleView = new RoleView(controller, this);
		departmentView = new DepartmentView(controller, this);
		companyView = new CompanyView(controller, this);

		// create menu
		Menu companyMenu = new Menu("Company");
		Menu departmentMenu = new Menu("Department");
		Menu roleMenu = new Menu("Role");
		Menu employeeMenu = new Menu("Employee");
		Menu calculateMenu = new Menu("Calculation");

		// set font , size to menu
		companyMenu.setStyle("-fx-font-size: 13px; -fx-font-family: Verdana");
		departmentMenu.setStyle("-fx-font-size: 13px; -fx-font-family: Verdana");
		roleMenu.setStyle("-fx-font-size: 13px; -fx-font-family: Verdana");
		employeeMenu.setStyle("-fx-font-size: 13px; -fx-font-family: Verdana");
		calculateMenu.setStyle("-fx-font-size: 13px; -fx-font-family: Verdana");
		// create menu items for company
		MenuItem companySimulatorMenuItem = new MenuItem("Create Company");
		MenuItem deleteCompany = new MenuItem("Delete Company");
		// create menu items for department
		MenuItem addDepartmentMenuItem = new MenuItem("Add Department");
		MenuItem showDepartmentsMenuItem = new MenuItem("Show Department");
		// create menu items for role
		MenuItem addRoleMenuItem = new MenuItem("Add Role");
		MenuItem showRoleMenuItem = new MenuItem("Show Role");
		// create menu items for employee
		MenuItem addEmployeeMenuItem = new MenuItem("Add Employee");
		MenuItem showEmployeeMenuItem = new MenuItem("Show Employee");
		// create menu item for calculation
		MenuItem calculateMenuItem = new MenuItem("Calculate");

		// add items to each menu
		companyMenu.getItems().addAll(companySimulatorMenuItem, deleteCompany);
		departmentMenu.getItems().addAll(addDepartmentMenuItem, showDepartmentsMenuItem);
		roleMenu.getItems().addAll(addRoleMenuItem, showRoleMenuItem);
		employeeMenu.getItems().addAll(addEmployeeMenuItem, showEmployeeMenuItem);
		calculateMenu.getItems().addAll(calculateMenuItem);

		menuBar = new MenuBar();// create menu bar
		// add all menus to menu bar
		menuBar.getMenus().addAll(companyMenu, departmentMenu, roleMenu, employeeMenu, calculateMenu);

		// set menu bar colors
		menuBar.setStyle(
				"-fx-selection-bar: #ada397;-fx-background-color: #c4b7a6  ;-fx-focused-text-base-color: #e3e0cc");

		bp.setTop(menuBar);
		// create a scene
		Scene sc = new Scene(bp, 1400, 520);
		stage.setScene(sc);
		stage.show();

		// company event handler :
		companySimulatorMenuItem.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				bp.setCenter(companyView.addEntity());
				bp.setBottom(null);
			}

		});
		deleteCompany.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				controller.deleteCompany();
				showSuccessMessage("Company deleted successfully!");
				bp.setCenter(companyView.addEntity());
				bp.setBottom(null);
			}

		});
		// department event handler :
		addDepartmentMenuItem.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				bp.setCenter(departmentView.addEntity());
			}

		});

		showDepartmentsMenuItem.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				bp.setCenter(departmentView.showEntity());

			}

		});

		showRoleMenuItem.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				bp.setCenter(roleView.showEntity());

			}

		});
		// role event handler :
		addRoleMenuItem.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				bp.setCenter(roleView.addEntity());
			}

		});

		// employee event handler:
		addEmployeeMenuItem.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				bp.setCenter(employeeView.addEntity());

			}

		});

		showEmployeeMenuItem.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				bp.setCenter(employeeView.showEntity());

			}

		});
		// calculate event handler:
		calculateMenuItem.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				calculateMenu();
			}

		});

		controller.load();
	}

	@Override
	public void stop() {
		// controller.save();
	}

	public WorkingHour getWorkingHours(WorkingHoursView whView, String entity) {

		if (whView.getStartTime().length() == 0 && whView.getEndTime().length() == 0 && whView.getCb() == false) {
			showSuccessMessage(entity + " created with default working hours \n starting hour: 8 \n ending hour: 17");
		}
		int startTime = 0;
		int endTime = 0;
		boolean workFromHome = false;

		startTime = Integer.parseInt(whView.getStartTime());
		endTime = Integer.parseInt(whView.getEndTime());

		WorkingHour wh = controller.createWorkingHour(startTime, endTime, workFromHome);
		return wh;
	}

	public void calculateMenu() {

		GridPane gpRoot = new GridPane();
		gpRoot.setPadding(new Insets(35));
		gpRoot.setAlignment(Pos.TOP_LEFT);
		gpRoot.setVgap(10);
		gpRoot.setHgap(15);

		// company level calculation
		Text tCalculateEfficiency = new Text();
		Text tCalculateProfit = new Text();
		Button calculateBtn = new Button("calculate");

		calculateBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {

				double result = controller.calculateProfit();
				double efficincy = controller.calculateEfficiency();
				tCalculateEfficiency.setText("Efficiency:  " + String.valueOf(efficincy));
				tCalculateProfit.setText("Profit: " + String.valueOf(result));

			}

		});
		gpRoot.add(calculateBtn, 0, 0);

		calculateBtn.setTextFill(Color.SADDLEBROWN);
		tCalculateProfit.setStyle("-fx-font-size: 13px; -fx-font-family: Verdana");
		tCalculateEfficiency.setStyle("-fx-font-size: 13px; -fx-font-family: Verdana");
		calculateBtn.setStyle("-fx-font-size: 13px; -fx-font-family: Verdana");

		if (controller.getCompany() == null) {
			calculateBtn.setDisable(true);
		}

		// department level calculation
		ListView<Department> departemntList = new ListView<>();

		for (Department d : controller.getDepartments()) {
			departemntList.getItems().add(d);
		}

		Text tInfo = new Text("In order to calculate select department:");

		Button calcDepartment = new Button("Calculate Department");

		calcDepartment.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				Department department = (Department) departemntList.getSelectionModel().getSelectedItem();
				if (department == null) {
					showErrorMessage("Please select a department");
					return;
				}
				double efficincy = controller.calculateDepartmentEfficiency(department);
				double result = controller.calculateProfit(efficincy);
				tCalculateEfficiency.setText("Efficiency:  " + String.valueOf(efficincy));
				tCalculateProfit.setText("Profit: " + String.valueOf(result));
			}

		});
		gpRoot.add(tInfo, 1, 1);
		gpRoot.add(calcDepartment, 1, 2);
		gpRoot.add(departemntList, 1, 3, 1, 1);
		calcDepartment.setTextFill(Color.SADDLEBROWN);
		tInfo.setStyle("-fx-font-size: 13px; -fx-font-family: Verdana");
		calcDepartment.setStyle("-fx-font-size: 13px; -fx-font-family: Verdana");

		// calculate role
		ListView<Role> roleList = new ListView<Role>();

		for (Department d : controller.getDepartments()) {
			for (Role r : d.getRoles()) {
				roleList.getItems().add(r);
			}
		}

		Text tRoleName = new Text("Select role:");
		Button calcRole = new Button("Calculate role");

		calcRole.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				Department department = (Department) departemntList.getSelectionModel().getSelectedItem();
				Role role = roleList.getSelectionModel().getSelectedItem();
				if (department == null) {
					showErrorMessage("Please select a department");
					return;
				}
				if (role == null) {
					showErrorMessage("Please select a role");
					return;
				}
				double efficiency = controller.calculateRoleEfficiency(role, department.getWorkingHour(),
						department.isChangable(), department.isSynchronizable(),
						department.getWorkingHour().isWorkFromHome());
				double result = controller.calculateProfit(efficiency);

				tCalculateEfficiency.setText("Efficiency:  " + String.valueOf(efficiency));
				tCalculateProfit.setText("Profit: " + String.valueOf(result));
			}

		});

		gpRoot.add(tRoleName, 0, 1);
		gpRoot.add(calcRole, 0, 2);
		gpRoot.add(roleList, 0, 3, 1, 1);

		gpRoot.add(tCalculateEfficiency, 0, 4, 2, 1);
		gpRoot.add(tCalculateProfit, 0, 5, 2, 1);

		if (controller.getCompany() == null) {
			calculateBtn.setDisable(true);
			calcDepartment.setDisable(true);
			calcRole.setDisable(true);
		}

		tRoleName.setStyle("-fx-font-size: 13px; -fx-font-family: Verdana");
		tInfo.setStyle("-fx-font-size: 13px; -fx-font-family: Verdana");
		bp.setCenter(gpRoot);

	}

	private double roundtoTwoDigits(double num) {
		return Math.round(num * 100.0) / 100;
	}

	public void showErrorMessage(String errorMessage) { // Error message
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error Message");
		alert.setHeaderText(null);
		alert.setContentText(errorMessage);
		alert.show();
	}

	public void showSuccessMessage(String succsesMessage) { // Success message
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Succsess Message");
		alert.setHeaderText(null);
		alert.setContentText(succsesMessage);
		alert.show();
	}

}

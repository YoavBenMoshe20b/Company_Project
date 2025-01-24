package view;

import controller.Controller;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class View01 extends Application {

	private Controller controller; //////// controller

	private Menu departmentMenu, ruleMenu, employeeMenu, companyMenu;
	private MenuItem addDepartmentMenuItems, showDepartmentMenuItems, addRuleMenuItems, showRuleMenuItems,
			addEmployeeMenuItems, showEmployeeMenuItems, companySimulatorMenuItems; // ������ ����
																					// ������
																					// ������
																					// ������

	private MenuBar menuBar;
	private VBox vb;
	// private HBox;
	// private Label;
	private GridPane gpRoot;
	// id , preference ,is sync , is change;
	private TextField employeeName, employeeAddress;
	private ComboBox<String> cbPreferenceKind;
	private Button departmentBtn, employeeBtn;

	@Override
	public void start(Stage s) throws Exception {
		// title
		s.setTitle("Company Simulator");

		// create menu
		departmentMenu = new Menu("Department");
		ruleMenu = new Menu("Role");
		employeeMenu = new Menu("Employee");
		companyMenu = new Menu("Company");

		// create menu items for department
		addDepartmentMenuItems = new MenuItem("Add Department");
		showDepartmentMenuItems = new MenuItem("Show Department");
		// create menu items for rule
		addRuleMenuItems = new MenuItem("Add Role");
		showRuleMenuItems = new MenuItem("Show Role");
		// create menu items for employee
		addEmployeeMenuItems = new MenuItem("Add Employee");
		showEmployeeMenuItems = new MenuItem("Show Employee");
		// create menu items for company
		// ???

		companySimulatorMenuItems = new MenuItem("Create Company");

		// add items to each menu
		departmentMenu.getItems().addAll(addDepartmentMenuItems, showDepartmentMenuItems);
		ruleMenu.getItems().addAll(addRuleMenuItems, showRuleMenuItems);
		employeeMenu.getItems().addAll(addEmployeeMenuItems, showEmployeeMenuItems);
		companyMenu.getItems().addAll(companySimulatorMenuItems);
		// create menu bar
		menuBar = new MenuBar();
		// add all menu to menu bar (sorted)
		menuBar.getMenus().addAll(companyMenu, departmentMenu, ruleMenu, employeeMenu);
		// create a VBox
		vb = new VBox(menuBar);
		// create a scene

		gpRoot = new GridPane();
		gpRoot.setPadding(new Insets(20));
		gpRoot.setAlignment(Pos.CENTER_LEFT);
		gpRoot.setVgap(10); // CHECK //
		gpRoot.setHgap(15); // CHECK //

		employeeName = new TextField();
		employeeAddress = new TextField();
		// create combo box preference
		cbPreferenceKind = new ComboBox<String>();
		cbPreferenceKind.getItems().addAll("Basic", "Hourly", "Bonus ");
		cbPreferenceKind.getSelectionModel().select(0);

		Label lblYourDeta = new Label();
		lblYourDeta.setWrapText(true);
		lblYourDeta.setTextFill(Color.BLUEVIOLET);

		gpRoot.add(new Label("Employee Name:"), 0, 0);
		gpRoot.add(employeeName, 1, 0);
		/// color ?

		gpRoot.add(new Label("Employee Address:"), 0, 1);
		gpRoot.add(employeeAddress, 1, 1);

		gpRoot.add(new Label("Preference Kind:"), 0, 2);
		gpRoot.add(cbPreferenceKind, 1, 2);

		CheckBox cbIsSync = new CheckBox("Synchronizable");
		// cbIsSync.setTextFill(Color.RED);
		CheckBox cbIsChange = new CheckBox("Changable");
		gpRoot.add(cbIsSync, 0, 3);
		gpRoot.add(cbIsChange, 0, 4);

		employeeBtn = new Button("Add Employee");
		employeeBtn.setTextFill(Color.BLUE);
		gpRoot.add(employeeBtn, 0, 6);

		/*
		 * gpRoot = new GridPane(); gpRoot.setPadding(new Insets(20));
		 * gpRoot.setAlignment(Pos.CENTER_LEFT); gpRoot.setVgap(8); // CHECK //
		 * gpRoot.setHgap(15); // CHECK //
		 * 
		 * TextField roleName = new TextField();
		 * 
		 * Label lblYourData = new Label(); lblYourData.setWrapText(true);
		 * //lblYourData.setTextFill(Color.BLUEVIOLET);
		 * ?????????????????????????????????
		 * 
		 * gpRoot.add(new Label("Role Name:"), 0, 0); // role name field
		 * gpRoot.add(roleName, 1, 0);
		 * 
		 * CheckBox cbIsSync = new CheckBox("Synchronizable"); // check box to sync
		 * CheckBox cbIsChange = new CheckBox("Changable"); // check box to change
		 * gpRoot.add(cbIsSync, 0, 1); gpRoot.add(cbIsChange, 0, 2);
		 * 
		 * employeeBtn = new Button("Add Role!");// add button employee
		 * employeeBtn.setTextFill(Color.BLUE); gpRoot.add(employeeBtn, 0, 6);
		 */

		Scene sc = new Scene(vb, 350, 300);

		gpRoot.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
		vb.getChildren().add(gpRoot);

		s.setScene(sc);
		s.show();

		gpRoot = new GridPane();
		employeeName = new TextField();

		// use controller;

	}

	public void addMouseClickingEventToMenu(EventHandler<ActionEvent> mouseClikingEvent) {
		// set action to department
		addDepartmentMenuItems.setOnAction(mouseClikingEvent);
		showDepartmentMenuItems.setOnAction(mouseClikingEvent);
		// set action to rule
		addRuleMenuItems.setOnAction(mouseClikingEvent);
		showRuleMenuItems.setOnAction(mouseClikingEvent);
		// set action to employee
		addEmployeeMenuItems.setOnAction(mouseClikingEvent);
		showEmployeeMenuItems.setOnAction(mouseClikingEvent);

	}

	public void ShowDetails(String deta) {
		ScrollPane sp = new ScrollPane();
		Label txt = new Label(deta);
		sp.setContent(txt);
		sp.setMinWidth(400);
		sp.setMinHeight(250);
		vb.getChildren().addAll(sp);
	}

	// public void showErrorMessege()
	// public void showSuccsessMessege()

	public String getEmployeeMenu() {
		return employeeName.getText();
	}

	public static void main(String[] args) {
		launch(args);
	}
}

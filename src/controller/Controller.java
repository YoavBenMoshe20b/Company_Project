package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.BonusPreference;
import model.Company;
import model.Department;
import model.Employee;
import model.HourPreference;
import model.Preference;
import model.Role;
import model.WorkingHour;
import model.WorkingHourable;
import persistence.CompanyDA;
import persistence.DepartmentDA;
import persistence.EmployeeDA;
import persistence.RoleDA;

public class Controller {

	private Company company;
	//private ArrayList<Employee> employees;
	//private WorkingHourable currrentHW;



	public Controller() {
		//this.employees = new ArrayList<>();
	}

	public void createCompany(String name, double profitPerHour) {
		company = new Company(name, profitPerHour);
		save();
	}

	public void createDepartment(String name, boolean isSync, boolean isChange, WorkingHour wh) {
		Department department = new Department(name, isSync, isChange);
		company.addDepartment(department);
		if(wh != null)
			department.setWorkingHour(wh);
		else
			department.setWorkingHour(defaultWorkingHours());
		
		save();
		saveDepartment(department);
		
	}

	private WorkingHour defaultWorkingHours() {
		return new WorkingHour(8, 17, false);
	}
	public boolean isEmptyCompany() {
		return company == null;
	}

	public synchronized void createRole(String roleName, boolean isSync, boolean isChange, Department department , WorkingHour wh) {
		Role role = new Role(roleName, isSync, isChange, null);
		role.setWorkingHour(wh);
		role.setDName(department.getName());
		department.addRole(role);
		
//		setCurrrentHW(role);
		save();
		saveRole(role);
	}

	public void createEmployee(String name, String address, Preference preference,WorkingHour wh, Role role) {
		Employee employee = new Employee(name, address, preference);
		employee.setIdRloe(role.getId());
		employee.setWorkingHour(wh);
		//employees.add(employee);
		//setCurrrentHW(employee);
		role.setEmployee(employee);
		role.getAllEmployess().add(employee);
		
		save();
		saveEmployee(employee);
	}
	

	public Preference createPreference() {
		return new Preference();
	}

	public Preference createPreference(int hours) {
		return new HourPreference(hours);
	}

	public Preference createPreference(double bonus) {
		return new BonusPreference(bonus);
	}

	public WorkingHour createWorkingHour(int startingHour, int endingHour, boolean workFromHome) {
		WorkingHour workingHour = new WorkingHour(startingHour, endingHour,workFromHome);
		return workingHour;
	}

	public void addWorkingHour(WorkingHour workingHour, WorkingHourable wh) {
		wh.setWorkingHour(workingHour);
	}

	
	public double calculateEfficiency() {
		double efficiency = 0;
		if(company == null) {
			return 0;
		}
		for(Department department : company.getDepartments()) {
			efficiency += calculateDepartmentEfficiency(department);
			
		}
		return efficiency;
	}
	public double calculateDepartmentEfficiency(Department department) {
		double efficiency = 0;
		
		for(Role role : department.getRoles()) {
			WorkingHour deptWh = department.getWorkingHour();
			efficiency += calculateRoleEfficiency(role, deptWh, department.isChangable(), department.isSynchronizable(), deptWh.isWorkFromHome());
		}
		
		return efficiency;
	}
	
	public double calculateRoleEfficiency(Role role, WorkingHour wh, boolean isChangeable, boolean isSynch, boolean workFromHome) {
		
		double efficiency = 0;
		
		WorkingHour roleWh = role.getWorkingHour();
		boolean roleChangeable = role.isChangable();
		boolean roleWorkFromHome = roleWh.isWorkFromHome();
		if(isChangeable == false) {
			roleChangeable = isChangeable;
		}
		if(isSynch == true) {
			roleWh = wh;
			roleWorkFromHome = wh.isWorkFromHome();
		}
		efficiency = calculateRoleEfficiency(role.getEmployee(), roleWh, roleChangeable, isSynch, roleWorkFromHome);
		
		return efficiency;
	}

	private double calculateRoleEfficiency(Employee employee, WorkingHour wh, boolean isChangeable, boolean isSynch, boolean workFromHome) {
		double efficiency = 0;
		
		if (employee == null) {
			return 0;
		}
		WorkingHour empWh = employee.getWorkingHour();
		int overlap = empWh.overLapping(wh);
		int notOverlap = empWh.notOverLapping(wh);
		int hours = empWh.getHours();
		
		if(empWh.isWorkFromHome() && workFromHome == true) {	// if employee wants to work from home and role / department allows it
			efficiency = hours * 1.1;
		} else if(notOverlap == 0) {		// employee wants the same hours as then role / department
			efficiency = hours * 1.2;	
		}else {			// employee wants to work in different hours than role / department
			if(isChangeable == false || isSynch == true) {
				efficiency = notOverlap * 0.8;
				efficiency += overlap * 1.2;
			}

		}
		
		return efficiency;
	}
	public double calculateProfit() {
		if(company == null) {
			return 0;
		}
		return calculateEfficiency() * company.getProfitPerHour();

	}
	
	public double calculateProfit(double efficiency) {
		if(company == null) {
			return 0;
		}
		return efficiency * company.getProfitPerHour();

	}

	private boolean workAtHome(Employee e) {
		return e.getWorkingHour().isWorkFromHome();

	}

	private boolean isPrivatePref(Employee e) { 
		return !(e.getWorkingHour().equals(company.getWorkingHour()));
	}

	public Company getCompany() {
		return company;
	}


	
	
	public void load() { // load data
		EmployeeDA e = new EmployeeDA();
		ArrayList<Employee> allEmployees = e.readEmployeeInfo();
		if(allEmployees == null) {
			allEmployees = new ArrayList<>();
		}

		RoleDA r = new RoleDA();
		ArrayList<Role> allRoles = r.readRoleInfo(allEmployees);
		if(allRoles == null) {
			allRoles = new ArrayList<>();
		}

		DepartmentDA d = new DepartmentDA();
		ArrayList<Department> allDepartments = d.readDepartmentInfo(allRoles);
		if(allDepartments == null) {
			allDepartments = new ArrayList<>();
		}

		CompanyDA c = new CompanyDA();
		this.company = c.readCompanyInfo(allDepartments);
		
		
	}

	
	public synchronized void save() { // save the data 
		
		
		ArrayList<Department> departments = null;
		ArrayList<Role> roles = null;
		ArrayList<Employee> employees = null;
		
		if(company != null) {
			departments = company.getDepartments();
			roles = new ArrayList<>();
			for (Department department : departments ) {
				roles.addAll(department.getRoles());

			}
			
			employees =  new ArrayList<>();
			for (Role role : roles) {
				if(role.getEmployee() != null)
				employees.add(role.getEmployee());

			}
		}

	}

public void saveDepartment(Department department ) { // save the data 
		
	DepartmentDA d = new DepartmentDA();
	d.writeInfo(department);
	}


public void saveRole(Role roles ) { // save the data 
	
	RoleDA r = new RoleDA();
	r.writeRoleInfo(roles);
	}



public void saveEmployee(Employee employee ) { // save the data 
	
	EmployeeDA e = new EmployeeDA();
	e.writeEmployeeInfo(employee);
	}

	public void deleteEmployeeFromSql(Employee employee ,Role r) {
		EmployeeDA e = new EmployeeDA();
		e.deleteEmployeeInfo(employee,r);
	}
	
	public void deleteRoleFromSql(Role role) {

		RoleDA r = new RoleDA();
		r.deleteRoleInfo(role);
	}

public void deleteDepartmentFromSql(Department departemnt) {
	DepartmentDA d = new DepartmentDA();
	d.deleteDepartment(departemnt);
	}


	public List<Department> getDepartments(){
		if(company == null) {
			return new ArrayList<Department>();
		}
		return company.getDepartments();
	}
	public void deleteRole(Role r) {
	
		for (Department department : company.getDepartments()) {
			if (department.getRoles().contains(r)) {
				department.getRoles().remove(r);
			}
		}
		deleteRoleFromSql( r);
		save();

	}
	
	public void deleteEmployee(Employee e) {

		for (Department department : company.getDepartments()) {
			for(Role r : department.getRoles()) {
				if(r.getEmployee() != null && r.getEmployee().equals(e)) {
					r.setEmployee(null);
					deleteEmployeeFromSql(e,r);
				}
			}
			
		}
		
		save();
	}
	
	public void deleteCompany() {
		this.company = null;
		save();
	}
	
	public void deleteDepartment(Department d ) {
		deleteDepartmentFromSql(d);
		company.getDepartments().remove(d);
		
		save();
	}
	
	
	public int calculateEfficiencyOld() {
		double efficiency = 0;

		for (Department department : company.getDepartments()) {
			boolean isChange, isSync;
			isChange = department.isChangable();
			isSync = department.isSynchronizable();

			for (Role role : department.getRoles()) {
				double percent = 0;
				WorkingHour wh = role.getWorkingHour();
				if (isSync) { // sync department
					wh = department.getWorkingHour();
					boolean syncPref = wh.equals(role.getEmployee().getWorkingHour());
					if (syncPref && workAtHome(role.getEmployee())) {
						percent = 1.3; // + 20% + 10%
					} else if (syncPref) { // if syncpref == syncpref department
						percent = 1.2; // + 20%
					} else {
						percent = 0.8; // -20%
					}
				} else { // Department not sync
					percent = 1;
					if (workAtHome(role.getEmployee())) { // if work at home
						percent += 0.1;
					}
					if (isPrivatePref(role.getEmployee())) { // if private preference
						if (role.isChangable()) {
							percent += 0.2;
						} else {
							percent -= 0.2;

						}

					}

				}
				efficiency += wh.getHours() * percent;
			}
		}
		return (int) efficiency;

	}

}

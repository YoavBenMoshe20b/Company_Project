package model;

import java.util.ArrayList;

public class Company implements WorkingHourable {

	private String name;
	private ArrayList<Department> departments;
	private WorkingHour workingHour;
	private double profitPerHour;

	public Company(String name , double profitPerHour) {
		setProfitPerHour(profitPerHour);
		setName(name);
		this.departments = new ArrayList<Department>();
	}

	public ArrayList<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(ArrayList<Department> departments) {
		this.departments = departments;
	}

	public void addDepartment(Department department) {
		departments.add(department);
	}
	
	public void deleteDepartment(Department department) {
		departments.remove(department);
	}

	public double getProfitPerHour() {
		return profitPerHour;
	}

	public void setProfitPerHour(double profitPerHour) {
		this.profitPerHour = profitPerHour;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void setWorkingHour(WorkingHour workingHour) {
	this.workingHour = workingHour;
		
	}

	@Override
	public WorkingHour getWorkingHour() {
		return this.workingHour;
	}

	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Name: " + name);
		sb.append("\n");
		sb.append("Departments: " + departments);
		sb.append("\n");
		return sb.toString();
	}

}

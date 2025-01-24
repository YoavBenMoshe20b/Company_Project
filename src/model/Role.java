package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

import controller.Controller;

public class Role implements Synchronizable, Changable, WorkingHourable {

	private static int roleIdGenerator = 500;
	private int id;
	private Employee employee;
	private String roleName , DName;
	private boolean isChange;
	private boolean isSync;
	private WorkingHour workingHour;
	private ArrayList<Employee> allEmployess = new ArrayList<Employee>();
	private Company c; 
    private Controller controller;

	public Role(String roleName, boolean isSync, boolean isChange, Employee employee, int id) {
		setChange(isChange);
		setSync(isSync);
		setName(roleName);
		setEmployee(employee);

		this.id = id;
		roleIdGenerator++;
		
	}

	public Role(String roleName, boolean isSync, boolean isChange, Employee employee) {
		this(roleName, isSync, isChange, employee, roleIdGenerator);
		roleIdGenerator++;

	}

	public Role(String roleName, boolean isSync, boolean isChange,int id) {
		this(roleName, isSync, isChange, null, id);

	}

	public String getDName() {
		return DName;
	}

	public void setDName(String dName) {
		DName = dName;
	}

	public String getName() {
		return roleName;
	}

	public void setName(String roleName) {
		this.roleName = roleName;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
		
	}

	public void setChange(boolean isChange) {
		this.isChange = isChange;
	}

	public void setSync(boolean isSync) {
		this.isSync = isSync;
	}

	public ArrayList<Employee> getAllEmployess() {
		return allEmployess;
	}

	public void setAllEmployess(ArrayList<Employee> allEmployess) {
		this.allEmployess = allEmployess;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Role name: " + roleName);
		if(this.employee != null) {
			sb.append("\nEmployee : " + employee.getName());
			sb.append("\n");
			sb.append("Employee id: " + employee.getId());
			sb.append("\n");
			sb.append(workingHour.toString());
		}
		else {
			sb.append("\nEmployee : No employee");
		}
		
		return sb.toString();
	}

	@Override
	public boolean isChangable() {
		return isChange;

	}

	@Override
	public boolean isSynchronizable() {
		return isSync;

	}

	@Override
	public void setWorkingHour(WorkingHour workingHour) {
		this.workingHour = workingHour;

	}

	@Override
	public WorkingHour getWorkingHour() {
		if (isChange && employee != null) {
			return employee.getWorkingHour();
		} else {
			return this.workingHour;
		}

	}

	public int getId() {
		return id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Role other = (Role) obj;
		return id == other.id;
	}
	
	public String getDepartment(int id) {
		ArrayList<Department> departments = new ArrayList<Department>();
		departments = c.getDepartments();
		
		for (Department d : departments) {
			for(Role r : d.getRoles())
			{
				if(r.getId()==id)
				{
					return d.getName();
				}
			}
		}
		return null;
	}
	
	

	

}

package model;

import java.util.ArrayList;

public class Department implements Synchronizable, Changable, WorkingHourable {

	private String name;
	// array list ---> employee 
	private ArrayList<Role> roles;
	private boolean isChange;
	private boolean isSync;
	private WorkingHour workingHour;

	public Department(String name, boolean isSync, boolean isChange) {
		setName(name);
		this.isChange = isChange;
		this.isSync = isSync;
		this.roles = new ArrayList<Role>();
	}

	public ArrayList<Role> getRoles() {
		if(roles ==null)
		{
			return null;
		}
		return roles;
	}

	public void setRoles(ArrayList<Role> roles) {
		this.roles = roles;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public WorkingHour getWorkingHour() {
		return workingHour;
	}

	public void setWorkingHour(WorkingHour workingHour) {
		this.workingHour = workingHour;
	}

	public void setChange(boolean isChange) {
		this.isChange = isChange;
	}

	public void setSync(boolean isSync) {
		this.isSync = isSync;
	}

	@Override
	public boolean isChangable() {
		return isChange;

	}

	@Override
	public boolean isSynchronizable() {
		return isSync;
	}
	
	public void addRole(Role role) {
		roles.add(role);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null || !( obj instanceof Department)) {
			return false;
		}
		Department other = (Department)obj;
		return this.name.equals(other.name);
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}
	@Override
	public String toString() {
		return this.name + "\n" + workingHour.toString();
	}
}

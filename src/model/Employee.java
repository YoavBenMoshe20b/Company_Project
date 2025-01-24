package model;

import java.util.ArrayList;

public class Employee implements WorkingHourable  {

	private static int idGenerator = 1;
	protected String name;
	protected String address;
	protected int id;
	Preference preference;
	private int idRloe;
	public int getIdRloe() {
		return idRloe;
	}
	public void setIdRloe(int idRloe) {
		this.idRloe = idRloe;
	}


	private Company c;

	private WorkingHour workingHour;

	// Constructor :
	
	public Employee(String name, String address, Preference preference, int id) {
		setName(name);
		setAddress(address);
		setPreference(preference);
		this.id = id;
		idGenerator++;
	}
	public Employee(String name, String address,  int id) {
		setName(name);
		setAddress(address);
		this.id = id;
		idGenerator++;
	}
	

	public Employee(String name, String address, Preference preference) {
		this(name,address,preference,idGenerator);
		
	}
	public Employee(String name, String address) {
		this(name,address,idGenerator);
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAddress(String address) {
		this.address = address;

	}
	
	

	public String getAddress() {
		return address;
	}
	
	public int getIdFromRole(int ID) {
		
		ArrayList<Department> departments = null;
		departments = c.getDepartments();
		
		for (Department d : departments) {
			for(Role r : d.getRoles())
			{
				for(Employee e : r.getAllEmployess())
				{
					if(e.getId()==id)
					{
						return r.getId();
					}
				}
				
			}
		}
		
		
		return 0;
	
	}

	public int getId() {
		return id;
	}

	@Override
	public int hashCode() {
		return id;
	}


	public Preference getPreference() {
		return preference;
	}

	public void setPreference(Preference preference) {
		this.preference = preference;
	}


	public WorkingHour getWorkingHour() {
		return workingHour;
	}

	public void setWorkingHour(WorkingHour workingHour) {
		this.workingHour = workingHour;
	}

	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Name: " + name);
		sb.append("\n");
		sb.append("Employee id: " + id);
		sb.append("\n");
		sb.append("Address: " + address);
		sb.append("\n");
		sb.append(workingHour.toString());
		return sb.toString();
	}

}

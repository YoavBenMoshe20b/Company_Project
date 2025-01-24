package persistence;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import model.Company;
import model.Department;
import model.Employee;
import model.Role;
import model.WorkingHour;
import program.DBConnection;



public class DepartmentDA {
	
	WorkingHourDA whDa = new WorkingHourDA();
	private String fileName = "Department.txt";
	private Company company;

	// read from sql:
	
	public ArrayList<Department> readDepartmentInfo(ArrayList<Role> roles)  {
		ArrayList<Department> allDepartments = new ArrayList<Department>();
		try {
			
			 Class.forName("com.mysql.cj.jdbc.Driver");
				Connection conDepartment = DBConnection.getInstance();
				String query4 = "SELECT * FROM departmentTable ";
				
				PreparedStatement pst4 = conDepartment.prepareStatement(query4);
			    ResultSet res = pst4.executeQuery(query4);
			    String name =null;
			    int start = -1, end =-1;
			    boolean isChange = false, isSync =false , work =false;
			    
				while (res.next() == true) {
					name= res.getString("departmentName");
					isChange = res.getBoolean("departmentIsChange");
					isSync = res.getBoolean("departmentIsSync");
					start = Integer.valueOf( res.getString("departmentStartingHour"));
					end =Integer.valueOf( res.getString("departmentEndingHour"));
					work = res.getBoolean("departmentWorkAtHome");
			         
					Department d = new Department(name, isSync, isChange);
					
					Role newRole = null;
					for (int i = 0 ; i <roles.size() ; i++ ) 
					{
						String depID =null , roleID =null;
						depID=d.getName();
						roleID =roles.get(i).getDName();
						
						if (depID.equals(roleID))
						{
								newRole= roles.get(i);
								d.addRole(newRole);
						}
					}
						
					WorkingHour wh = new WorkingHour(start,end,work);
					d.setWorkingHour(wh);
					allDepartments.add(d);
					
				}
				pst4.executeQuery();
			
		}
		 
		 catch(SQLException ex)
			{
				System.out.println(ex.getMessage());
				
			}
			catch(Exception x) 
			{
				
			}
		return allDepartments;
		 
	}

	
	public boolean writeInfo(Department d)  {
	
					try {
						Class.forName("com.mysql.cj.jdbc.Driver");
						Connection conDepartment;
						conDepartment = DriverManager.getConnection("jdbc:mysql://localhost:3306/companydb","root","Eden@1910");
						String query = "INSERT INTO departmentTable(departmentName,departmentIsChange, departmentIsSync,departmentStartingHour, departmentEndingHour, departmentWorkAtHome) VALUES(?,?,?,?,?,?)";
						PreparedStatement pst = conDepartment.prepareStatement(query);
						pst.setString(1, d.getName());
						pst.setBoolean(2,d.isChangable());
						pst.setBoolean(3,d.isSynchronizable());
						pst.setString(4,String.valueOf(d.getWorkingHour().getStartingHour()));
						pst.setString(5, String.valueOf(d.getWorkingHour().getEndingHour()));
						pst.setBoolean(6,d.getWorkingHour().isWorkFromHome());
						pst.executeUpdate();
					}
						
						
					catch(SQLException ex)
					{
						System.out.println(ex.getMessage());
						
					}
					catch(Exception x) 
					{
						
					}
				
				
			return true;
	}
	
	public void deleteDepartment(Department d) {
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conDEmployee;
			conDEmployee = DriverManager.getConnection("jdbc:mysql://localhost:3306/companydb","root","Eden@1910");
					
			String query4 = "DELETE FROM DepartmentTable WHERE DepartmentName = ?";
			PreparedStatement pst4 = conDEmployee.prepareStatement(query4);
			pst4.setString(1, d.getName());
				
	
			pst4.executeUpdate();
		
			
		}
		
		
			
		catch(SQLException ex)
		{
			System.out.println(ex.getMessage());
			
		}
		catch(Exception x) 
		{
			
		}
	}
	
	}



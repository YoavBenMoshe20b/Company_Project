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

import model.Employee;
import model.Role;
import model.WorkingHour;
import program.DBConnection;

public class RoleDA {

	WorkingHourDA whDa = new WorkingHourDA();
	private String fileName = "Role.txt";
	
	// read from sql:
	public ArrayList<Role> readRoleInfo(ArrayList<Employee> employees) {
		
		ArrayList<Role> allRoles = new ArrayList<Role>();
		try {
			 Class.forName("com.mysql.cj.jdbc.Driver");
				Connection conRole = DBConnection.getInstance();
				
				String query4 = "SELECT * FROM roleTable ";
				
				PreparedStatement pst4 = conRole.prepareStatement(query4);
			    ResultSet res = pst4.executeQuery(query4);
			    String dname =null,  roleName = null;
			    boolean isSync = false , isChange = false;
			    int id = -1 ,start =-1, end =-1;
			    boolean  work =false;
				while (res.next() == true) {
					id = res.getInt("roleID");
					dname= res.getString("DName");
					roleName = res.getString("roleName");
					isChange = res.getBoolean("roleIsChange");
					isSync = res.getBoolean("roleIsSync");
					start = Integer.valueOf(res.getString("roleStartingHour"));
					end =Integer.valueOf( res.getString("roleEndingHour"));
					work = res.getBoolean("roleWorkAtHome");
					
					Employee employee = null;
					int erid =returnRole(id);
					if(erid !=-1) {
						for(Employee e : employees) {
							
							if(erid == e.getId()) {
								employee=e;
							}
						}
					}
				
					WorkingHour wh = new WorkingHour(start,end,work);
					Role r = new Role(roleName, isSync, isChange,employee,id);
					r.setDName(dname);
					r.setWorkingHour(wh);
					allRoles.add(r);
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
		
		return allRoles;

	}

	public boolean writeRoleInfo(Role r) {
		
	
			
					try {
						Class.forName("com.mysql.cj.jdbc.Driver");
						Connection conRole;
						conRole = DriverManager.getConnection("jdbc:mysql://localhost:3306/companydb","root","Eden@1910");
						String query1 = "INSERT INTO roleTable(roleID,DName, roleName,roleIsChange, roleIsSync, roleStartingHour, roleEndingHour,roleWorkAtHome) VALUES(?,?,?,?,?,?,?,?)";
						PreparedStatement pst1 = conRole.prepareStatement(query1);
						pst1.setInt(1,r.getId());
						pst1.setString(2,r.getDName());
						pst1.setString(3,r.getName());
						pst1.setBoolean(4,r.isChangable());
						pst1.setBoolean(5, r.isSynchronizable());
						pst1.setString(6,String.valueOf(r.getWorkingHour().getStartingHour()));
						pst1.setString(7,String.valueOf(r.getWorkingHour().getEndingHour()));
						pst1.setBoolean(8,r.getWorkingHour().isWorkFromHome());
						
						pst1.executeUpdate();
						
						query1 = "INSERT INTO MannedTable(RID) VALUES(?)";
						 pst1 = conRole.prepareStatement(query1);
						pst1.setInt(1,r.getId());
			
						pst1.executeUpdate();
					
					
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
	
	public void deleteRoleInfo(Role r)
	{
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conRole;
			conRole = DriverManager.getConnection("jdbc:mysql://localhost:3306/companydb","root","Eden@1910");
			
			String query4 = "DELETE FROM MannedTable WHERE RID = " + r.getId()+";";
			PreparedStatement pst4 = conRole.prepareStatement(query4);
			pst4.executeUpdate();
			
		}
		
		
			
		catch(SQLException ex)
		{
			System.out.println(ex.getMessage());
			
		}
		catch(Exception x) 
		{
			
		}
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conDEmployee;
			conDEmployee = DriverManager.getConnection("jdbc:mysql://localhost:3306/companydb","root","Eden@1910");
			
			String query4 = "DELETE FROM roleTable WHERE roleID = " + r.getId()+";";
			PreparedStatement pst4 = conDEmployee.prepareStatement(query4);
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
	
	
	
	
public int returnRole(int ID) {
		
		int idrole =-1 , idEmployee = -1;
		String type = null;
		try {
			
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conDEmployee;
			conDEmployee = DriverManager.getConnection("jdbc:mysql://localhost:3306/companydb","root","Eden@1910");
			String query4 = "SELECT * FROM MannedTable WHERE RID = " + ID + "";
			
			PreparedStatement pst4 = conDEmployee.prepareStatement(query4);
		    ResultSet res = pst4.executeQuery(query4);
		    
			while (res.next() == true) {
				 idrole = res.getInt("RID");  
				 idEmployee = res.getInt("EID");
		         
		      
			}
		        
			pst4.executeQuery();
			if(idEmployee <= 0 )
			{
				return -1;
			}
			return idEmployee;
		}
		
	
		
			
		catch(SQLException ex)
		{
			System.out.println(ex.getMessage());
			
		}
		catch(Exception x) 
		{
			
		}
		

		return idEmployee;
		
	}
	
}

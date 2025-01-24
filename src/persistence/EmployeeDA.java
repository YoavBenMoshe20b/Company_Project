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
import model.Preference;
import model.Role;
import model.WorkingHour;
import program.DBConnection;
import model.Preference.preferenceType;

public class EmployeeDA {

	WorkingHourDA whDa = new WorkingHourDA();
	private String fileName = "Employee.txt";
	
	// read from sql:
	public ArrayList<Employee> readEmployeeInfo()  {
	
		ArrayList<Employee> allEmployees = new ArrayList<Employee>();
		try {
			 Class.forName("com.mysql.cj.jdbc.Driver");
				Connection conDEmployee = DBConnection.getInstance();
				String query4 = "SELECT * FROM employeeTable ";
				
				PreparedStatement pst4 = conDEmployee.prepareStatement(query4);
			    ResultSet res = pst4.executeQuery(query4);
			    String name =null,  address = null;
			    int id = -1 ,start =-1, end =-1;
			    boolean  work =false;
				while (res.next() == true) {
					id = res.getInt("employeeID");
					name= res.getString("employeeName");
					address = res.getString("employeeAddress");
					start = Integer.valueOf(res.getString("employeeStartingHour"));
					end =Integer.valueOf( res.getString("employeeEndingHour"));
					work = res.getBoolean("workAtHome");
				Employee newEmployee = new Employee(name, address, getPrefFromSql(id));
				WorkingHour wh = new WorkingHour(start,end,work);
				newEmployee.setWorkingHour(wh);
				allEmployees.add(newEmployee);
			      
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
			 
		
	
		return allEmployees;

	}

	public void writeEmployeeInfo(Employee e)  {
			
					try {
						Class.forName("com.mysql.cj.jdbc.Driver");
						Connection conEmployee = DBConnection.getInstance();
						String query = "INSERT INTO EmployeeTable(employeeID,employeeName, employeeAddress,employeeStartingHour, employeeEndingHour, WorkAtHome) VALUES(?,?,?,?,?,?)";
						PreparedStatement pst = conEmployee.prepareStatement(query);
						pst.setInt(1,e.getId() );
						pst.setString(2,e.getName());
						pst.setString(3,e.getAddress());
						pst.setString(4,String.valueOf(e.getWorkingHour().getStartingHour()));
						pst.setString(5,String.valueOf(e.getWorkingHour().getEndingHour()));
						pst.setBoolean(6,e.getWorkingHour().isWorkFromHome());
						pst.executeUpdate();
						
						query = "UPDATE MannedTable SET RID =? ,EID = ? WHERE RID = " + e.getIdRloe();
						pst = conEmployee.prepareStatement(query);
						pst.setInt(1,e.getIdRloe());
						pst.setInt(2, e.getId());
						
						pst.executeUpdate();
					}
						catch(SQLException ex)
						{
							System.out.println(ex.getMessage());
							
						}
						catch(Exception x) 
						{
							
						}
					
							Preference p = null;
							preferenceType ptb =preferenceType.BONUS;
							preferenceType pth =preferenceType.HOUR;
							preferenceType ptg =preferenceType.GLOBAL;
						try {
							
							
							Class.forName("com.mysql.cj.jdbc.Driver");
							
							Connection conEmployee = DBConnection.getInstance();
							String query = "INSERT INTO SalaryTable(EID,salaryType) VALUES(?,?)";
							PreparedStatement pst = conEmployee.prepareStatement(query);	
						
						if(e.getPreference().getType()== ptg || e.getPreference().getType()== ptb)
						{
							
							
							pst.setInt(1, e.getId());
							pst.setString(2,"G");
							//pst.executeUpdate();
						}
						else if(e.getPreference().getType()== pth)
						{
							
							pst.setInt(1, e.getId());
							pst.setString(2,"H");
							
						}
						pst.executeUpdate();
						}
						catch(SQLException ex)
						{
							System.out.println(ex.getMessage());
							
						}
						catch(Exception x) 
						{
							
						}
						
						if(e.getPreference().getType()== pth)
						{
						try {
							Class.forName("com.mysql.cj.jdbc.Driver");
							
							Connection conEmployee = DBConnection.getInstance();
							String query = "INSERT INTO hourSalaryTable(EID,paymentPerHours,hours) VALUES(?,?,?)";
							PreparedStatement pst = conEmployee.prepareStatement(query);
						
							
								
							pst.setInt(1,e.getId());
							pst.setDouble(2,e.getPreference().calculateSalary());
							pst.setInt(3,e.getPreference().getHourss());
							
						
						pst.executeUpdate();
						}
						catch(SQLException ex)
						{
							System.out.println(ex.getMessage());
							
						}
						catch(Exception x) 
						{
							
						}
						}
						
						if(e.getPreference().getType()== ptg || e.getPreference().getType()== ptb)
						{
						try{
							Class.forName("com.mysql.cj.jdbc.Driver");
							
							Connection conEmployee = DBConnection.getInstance();
							String query = "INSERT INTO globalSalaryTable(EID,globalPayment,globalHours,bonus) VALUES(?,?,?,?)";
							PreparedStatement pst = conEmployee.prepareStatement(query);	
							
						
							
							
							if(e.getPreference().getType()== ptg )
							{
								pst.setInt(1,e.getId() );
								pst.setDouble(2,e.getPreference().calculateSalary());
								pst.setInt(3,e.getPreference().getGolbal());
								pst.setDouble(4,0.0);
							}
							else if(e.getPreference().getType()== ptb)
							{
								pst.setInt(1,e.getId() );
								pst.setDouble(2,e.getPreference().calculateSalary());
								pst.setInt(3,e.getPreference().getGolbal());
								pst.setDouble(4,e.getPreference().calculateSalary());
							}
								
							pst.executeUpdate();
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
	
	
	
	public void deleteEmployeeInfo(Employee e, Role r)
	{
		int id= -1;
		String type =null;
		try {
			
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection conDEmployee = DBConnection.getInstance();
			String query4 = "SELECT * FROM salaryTable WHERE EID = " + e.getId()+ "";
			
			PreparedStatement pst4 = conDEmployee.prepareStatement(query4);
		    ResultSet res = pst4.executeQuery(query4);
		    
			while (res.next() == true) {
				 id = res.getInt("EID");  
		        type = res.getString("salaryType");
			}
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
			
			Connection conDEmployee = DBConnection.getInstance();
			String query4 = "DELETE FROM salaryTable WHERE EID = " + e.getId()+";";
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
		
		if(type.equals("G")) {
			
			
			try {
				
				Class.forName("com.mysql.cj.jdbc.Driver");
				
				Connection conDEmployee = DBConnection.getInstance();
				String query4 = "DELETE FROM globalSalaryTable WHERE EID = " + e.getId()+";";
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
			
			
			if(type.equals("H")) {
				
				
				try {
					
					
					Class.forName("com.mysql.cj.jdbc.Driver");
					
					Connection conDEmployee = DBConnection.getInstance();
					String query4 = "DELETE FROM hourSalaryTable WHERE EID = " + e.getId()+";";
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
			try {

				
				Class.forName("com.mysql.cj.jdbc.Driver");
				
				Connection conEmployee = DBConnection.getInstance();
				String query = "UPDATE MannedTable SET EID = NULL WHERE RID = " + r.getId() ;
				PreparedStatement pst = conEmployee.prepareStatement(query);
				pst.executeUpdate();
				
				
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
				
				Connection conDEmployee = DBConnection.getInstance();
				
				String query4 = "DELETE FROM EmployeeTable WHERE employeeID = " + e.getId()+";";
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
	
	
	
	
	
	
	
	
	
	
	public Preference getPrefFromSql(int ID) {
		Preference p = new Preference();
		int id =-1;
		String type = null;
		try {
			
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection conDEmployee = DBConnection.getInstance();
			String query4 = "SELECT * FROM salaryTable WHERE EID = " + ID + "";
			
			PreparedStatement pst4 = conDEmployee.prepareStatement(query4);
		    ResultSet res = pst4.executeQuery(query4);
		    
			while (res.next() == true) {
				 id = res.getInt("EID");  
		          type = res.getString("salaryType");
		         
		      
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
		

		
		if(type.equals("G")) {
			
		double global=-1, bonus =-1;
		int hour = -1;
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection conDEmployee = DBConnection.getInstance();
			String query4 = "SELECT * FROM globalSalaryTable WHERE EID = " + ID+";";
			PreparedStatement pst4 = conDEmployee.prepareStatement(query4);
			
			 ResultSet res = pst4.executeQuery(query4);
			    
				while (res.next() == true) {
					 id = res.getInt("EID");  
					 global = res.getDouble("globalPayment");
					 hour = res.getInt("globalHours");  
					 bonus = res.getDouble("bonus");
			      
				}
			        
			pst4.executeQuery();
			if (bonus==0)
			{
				p.setPreference();
			}
			if (bonus != 0 && bonus != -1 ) {
				p.setPreference(bonus);
			}
			return p;
		}
		
		
		catch(SQLException ex)
		{
			System.out.println(ex.getMessage());
			
		}
		catch(Exception x) 
		{
			
		}
		
		}
		
		
		if(type == "H") {
			
			
			try {
				
				int hour= -1;
				double payment = -1;
				Class.forName("com.mysql.cj.jdbc.Driver");
				
				Connection conDEmployee = DBConnection.getInstance();
				String query4 = "SELECT * FROM hourSalaryTable WHERE EID = " + ID+";";
				PreparedStatement pst4 = conDEmployee.prepareStatement(query4);
				
				 ResultSet res = pst4.executeQuery(query4);
				while (res.next() == true) {
					 id = res.getInt("EID");  
					 hour = res.getInt("hours");
					 payment = res.getDouble("pymentPerHours");
			      
				}
				pst4.executeQuery();
				p.setPreference(hour);
				return p;
				
			}
			
			
				
			catch(SQLException ex)
			{
				System.out.println(ex.getMessage());
				
			}
			catch(Exception x) 
			{
				
			}
			
			}
			return p;
	}

	}

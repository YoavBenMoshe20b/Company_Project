package persistence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import model.Company;
import model.Department;

public class CompanyDA {

	private String fileName = "Company.txt";
	// read from file:
	public Company readCompanyInfo(ArrayList<Department> department) {
		// search for the file and reads
		File f = new File(fileName);
		Scanner read = null;
		Company c = null;
		try {
			read = new Scanner(f);
		} catch (FileNotFoundException e) {
			return c;
		}
		// reading each question
		if(!read.hasNextLine()) {
			read.close();
			return c;
		}
		String name = read.nextLine();
		double profit = Double.valueOf(read.nextLine());
		c = new Company(name, profit);
		if(department != null) {
			c.setDepartments(department);
		}
		
		read.close();
		return c;

	}

	public boolean writeCompanyInfo(Company c) {
		StringBuilder sb = new StringBuilder();
		FileWriter fw;
		
		try {
		
			if(c != null) {
				sb.append(c.getName());
				sb.append("\n");
				sb.append(String.valueOf(c.getProfitPerHour()));
				sb.append("\n");
			}
			 fw = new FileWriter(fileName);
			 fw.write(sb.toString());

			fw.flush();
			fw.close();
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	

}

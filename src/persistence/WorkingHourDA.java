package persistence;

import java.util.Scanner;

import model.WorkingHour;

public class WorkingHourDA {

	public WorkingHour read (Scanner scanner) {
		
		WorkingHour wh = null;
		int startHour = Integer.parseInt(scanner.nextLine());
		int endHour = Integer.parseInt(scanner.nextLine());
		boolean workFromHome = Boolean.parseBoolean(scanner.nextLine());
		
		wh = new WorkingHour(startHour, endHour, workFromHome);
		return wh;
		
	}
	
	public String write(WorkingHour wh) {
			
		
		StringBuilder sb  = new StringBuilder();
		sb.append(wh.getStartingHour());
		sb.append("\n");
		sb.append(wh.getEndingHour());
		sb.append("\n");
		sb.append(wh.isWorkFromHome());
		sb.append("\n");
		
		return sb.toString();
		
	}
}

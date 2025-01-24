package model;



public class Preference {

 
 protected int golbal; // 160
 protected static final double SALARY = 5500;
 protected static final double HOURLY_RATE = 50.00;
 public static enum preferenceType {HOUR, GLOBAL, BONUS};
 private double Bonus;
 private int Hours;
 private double finellPayHours;
	preferenceType type ;
	
 
 // Prefrence factory
 
 public static Preference setPreference() {
	return new Preference(); 
 }
 
public static Preference setPreference(double bonus) {
	 return new BonusPreference(bonus);
 }
public static Preference setPreference(int hours) {
	
	return new HourPreference(hours);
}


	public Preference() {
		this.golbal = 160;
	}
	
	public int getGolbal() {
		return golbal;
	}
	public double getBonus() {
		return Bonus;
	}
	
	public double getFinellPayHours() {
		return finellPayHours;
	}

	

	public void setHours(int hours) {
		Hours = hours;
	}

	public void setFinellPayHours(double finellPayHours) {
		this.golbal = 160;
		finellPayHours =Hours *golbal;
		this.finellPayHours = finellPayHours;
	}

	public int getHourss() {
		return Hours;
	}
	
	public void setGolbal (int golbal) {
		this.golbal=golbal;
	}
	public void setBonus (double Bonus) {
		
		this.Bonus=Bonus;;
	}
	
	public void setHourss(int Hours) {
		this.Hours= Hours;
		
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Salary: " + calculateSalary()); 
		return sb.toString();
	}
	
	
	public double calculateSalary() {
			return SALARY;

	}

	public double getHourlyRate() {
		return HOURLY_RATE;
	}
	
	
	public String serialize() {
		return "";
	}
	public preferenceType getType() {
		return type;
	}
	public void setType(preferenceType type) {
		this.type = type;
	}

}

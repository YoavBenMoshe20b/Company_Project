package model;

public class HourPreference extends Preference {

	public HourPreference(int hours) {
		this.golbal = hours;
	}
	
	public void setHours(int hours) {
		this.golbal = hours;
	}

	@Override
	public double calculateSalary() {
		return golbal * HOURLY_RATE;

	}
	
	@Override
	public String serialize() {
		return String.valueOf(golbal);
	}

}

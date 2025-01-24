package model;

public class BonusPreference extends Preference {

	private double bonus;

	public BonusPreference(double bonus) {
		this.bonus = bonus;
	}

	
	public void setBonus(double bonus) {
		this.bonus = bonus;
	}


	public double calculateSalary() {

		return SALARY + bonus;

	}
	@Override
	public String serialize() {
		return String.valueOf(bonus);
	}

}
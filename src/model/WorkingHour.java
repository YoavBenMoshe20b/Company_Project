package model;

public class WorkingHour {

	private int startingHour;
	private int endingHour;
	private boolean workFromHome;

	public WorkingHour(int startingHour, int endingHour, boolean workFromHome) {
		if (!(validate(startingHour, endingHour))) {
			endingHour = 17;
			startingHour = 8;
		}
		setStartingHour(startingHour);
		setEndingHour(endingHour);
		setWorkFromHome(workFromHome);

	}

	public int getStartingHour() {
		return startingHour;
	}

	public void setStartingHour(int startingHour) {
		this.startingHour = startingHour;
	}

	public int getEndingHour() {
		return endingHour;
	}

	public void setEndingHour(int endingHour) {
		this.endingHour = endingHour;
	}

	public boolean isWorkFromHome() {
		return workFromHome;
	}

	public void setWorkFromHome(boolean workFromHome) {
		this.workFromHome = workFromHome;
	}

	@Override
	public String toString() {
		return startingHour + "-" + endingHour + " [Home: " + workFromHome + "]";
	}

	@Override
	public boolean equals(Object other) {
		if (other == null) {
			return false;
		}
		if (!(other instanceof WorkingHour)) {
			return false;
		}
		WorkingHour w = (WorkingHour) other;
		return w.startingHour == this.startingHour && w.endingHour == this.endingHour;
	}

	public int getHours() {
		return this.endingHour - this.startingHour;
	}

	private boolean validate(int startingHour, int endingHour) {
		if (startingHour < 0 || startingHour > 15) {
			return false;
		}
		if (endingHour < 9 || endingHour > 24) {
			return false;
		}
		if (endingHour - startingHour < 9) {
			return false;
		}
		return true;

	}

	public int notOverLapping(WorkingHour wh) {
		int count = 0;
		if (startingHour > wh.startingHour) {
			count = startingHour - wh.startingHour;
		} else if (startingHour < wh.startingHour) {
			count = wh.startingHour - startingHour;
		}
		return count;
	}

	public int overLapping(WorkingHour wh) {
		int count = 9 - notOverLapping(wh);
		return count;
	}

}

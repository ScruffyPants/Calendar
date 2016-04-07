import java.io.Serializable;
import java.util.LinkedList;
import java.util.Objects;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Schedule implements Serializable {
	private static final long serialVersionUID = 1504199602031999L;
	private String name;
	private String description;
	private int[] dates = new int[6];
	private boolean[] days = new boolean[7];
	private int delay;
	
	public Schedule(String n, String dsc, int[] dts, boolean[] days2, int del) {
		name = n;
		description = dsc;
		dates = dts;
		days = days2;
		delay = del;
	}
	
	public Schedule() {
		
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getYStart() {
		return dates[0];
	}
	public void setYStart(int yStart) {
		this.dates[0] = yStart;
	}
	public int getMStart() {
		return dates[1];
	}
	public void setMStart(int mStart) {
		this.dates[1] = mStart;
	}
	public int getDStart() {
		return dates[2];
	}
	public void setDStart(int dStart) {
		this.dates[2] = dStart;
	}
	public int getYEnd() {
		return dates[3];
	}
	public void setYEnd(int yEnd) {
		this.dates[3] = yEnd;
	}
	public int getMEnd() {
		return dates[4];
	}
	public void setMEnd(int mEnd) {
		this.dates[4] = mEnd;
	}
	public int getDEnd() {
		return dates[5];
	}
	public void setDEnd(int dEnd) {
		this.dates[5] = dEnd;
	}

	public int[] getDates() {
		return dates;
	}

	public void setDates(int[] dates) {
		this.dates = dates;
	}

	public boolean[] getDays() {
		return days;
	}

	public void setDays(boolean[] days) {
		this.days = days;
	}

	public boolean isOnMonday() {
		return days[0];
	}

	public void setOnMonday(boolean onMonday) {
		this.days[0] = onMonday;
	}

	public boolean isOnTuesday() {
		return days[1];
	}

	public void setOnTuesday(boolean onTuesday) {
		this.days[1] = onTuesday;
	}

	public boolean isOnWednesday() {
		return days[2];
	}

	public void setOnWednesday(boolean onWednesday) {
		this.days[2] = onWednesday;
	}

	public boolean isOnThursday() {
		return days[3];
	}

	public void setOnThursday(boolean onThursday) {
		this.days[3] = onThursday;
	}

	public boolean isOnFriday() {
		return days[4];
	}

	public void setOnFriday(boolean onFriday) {
		this.days[4] = onFriday;
	}

	public boolean isOnSaturday() {
		return days[5];
	}

	public void setOnSaturday(boolean onSaturday) {
		this.days[5] = onSaturday;
	}

	public boolean isOnSunday() {
		return days[6];
	}

	public void setOnSunday(boolean onSunday) {
		this.days[7] = onSunday;
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}
	
	
	
	
}

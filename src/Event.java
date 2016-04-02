
public class Event {
	private String name;
	private int year;
	private int month;
	private int day;
	
	public Event(String n, int y, int m, int d){
		name = n;
		year = ( y >= 1970 ) ? y : 1970 ;
		month = ( m >= 1 & m <= 12 ) ? m : 1 ;
		day = ( d >= 1 & d <= 31 ) ? d : 1 ;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}
}

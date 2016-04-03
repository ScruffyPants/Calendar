import java.io.Serializable;
import java.util.*;

public class Event implements Serializable, Comparator<Event>{
	private static final long serialVersionUID = 1504199602031999L;
	private String name;
	private String description;
	private int year;
	private int month;
	private int day;
	
	public Event(String n, int y, int m, int d, String ds){
		name = n;
		year = ( y >= 1970 ) ? y : 1970 ;
		month = ( m >= 1 & m <= 12 ) ? m : 1 ;
		day = ( d >= 1 & d <= 31 ) ? d : 1 ;
		description = ds;
	}
	
	public String getDescription(){
		return description; 
	}
	
	public void setDescription(String d){
		this.description = d;
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
	
	@Override
	public int compare(Event e1, Event e2){
		if(e1.getYear() > e2.getYear())return 1;
		else if(e1.getYear() < e2.getYear())return -1;
		else { //Equal year
			if(e1.getMonth() > e2.getMonth())return 1;
			else if(e1.getMonth() < e2.getMonth())return -1;
			else { //Equal year and equal month
				if(e1.getDay() > e2.getDay())return 1;
				else if(e1.getDay() < e2.getDay())return -1;
				else if(e1.getYear() == e2.getYear() && e1.getMonth() == e2.getMonth() && e1.getDay() == e1.getDay())return 0; //Equal year and equal month and equal day
				else return 400; //This should never occur
			}
		}
	}
}

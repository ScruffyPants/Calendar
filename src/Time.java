import java.util.Calendar;
import java.text.DateFormatSymbols;
public class Time {
	private int Year;
	private int Month;
	private int Day;
	private int Hour;
	private int Minute;
	private int DaysInMonth;
	private Calendar temp = Calendar.getInstance();
	private DateFormatSymbols time = new DateFormatSymbols();
	
	public Time(){
		Year = temp.get(Calendar.YEAR);
		Month = temp.get(Calendar.MONTH);
		Day = temp.get(Calendar.DATE);
		Hour = temp.get(Calendar.HOUR);
		Minute = temp.get(Calendar.MINUTE);
	}
	
	public int getYear(){
		return Year;
	}
	public int getMonth(){
		return Month;
	}
	public int getDay(){
		return Day;
	}
	public int getHour(){
		return Hour;
	}
	public int getMinute(){
		return Minute;
	}
	public void setYear(int year){
		temp.set(Calendar.YEAR, year);
		Year = temp.get(Calendar.YEAR);
	}
	public void setMonth(int month){
		temp.set(Calendar.MONTH, month);
		Month = temp.get(Calendar.MONTH);
		if(Month!=month){
			temp.set(Calendar.MONTH, month);
			Month = temp.get(Calendar.MONTH);
		}
	}

	public void setDay(int day) {
		temp.set(Calendar.DATE, day);
		Day = temp.get(Calendar.DATE);
	}

	public void setHour(int hour) {
		temp.set(Calendar.HOUR, hour);
		Hour = temp.get(Calendar.HOUR);
	}

	public void setMinute(int minute) {
		temp.set(Calendar.MINUTE, minute);
		Minute = temp.get(Calendar.MINUTE);
	}
	
	public int getDaysInMonth(){
		return temp.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
	public int getFirstDayOfMonth(Time time){
		Calendar cal = time.temp;
		cal.set(Calendar.DAY_OF_MONTH, 0);
		return cal.get(Calendar.DAY_OF_WEEK);
	}
	public String getMonthName(int month){
		String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};	
		return monthNames[month];
	}
}

import java.util.Calendar;
public class Time {
	private int Year;
	private int Month;
	private int Day;
	private int Hour;
	private int Minute;
	private int DaysInMonth;
	private Calendar temp = Calendar.getInstance();
	
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
}

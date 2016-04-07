import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

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
		//System.out.println("Month in getFirstDayOfMonth: "+time.getMonth());
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.MONTH)+1);
		//System.out.println("Month in getFirstDayOfMonth: "+time.getMonth());
		SimpleDateFormat sdf = new SimpleDateFormat("u");
		return Integer.parseInt(sdf.format(cal.getTime()));
	}
	public String getMonthName(int month){
		String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};	
		return monthNames[month];
	}
	
	public int getDayOfWeek(int y, int m, int d) {
		int Y = y;
		m = (m - 2) % 12;
		int w = (d + ((int)(2.6*m - 0.2) - ((int)(2.6*m - 0.2)%1)) + 5 * (Y % 4) + 4 * (Y % 100) + 6 * (Y % 400)) % 7;
		switch(w) {
		case 0: w = 7;
		}
		return w;
	}
}

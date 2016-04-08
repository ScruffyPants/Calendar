import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.lang.Math;
import java.text.ParseException;

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
	
	public Time(int y, int m, int d) {
		Year = y;
		Month = m;
		Day = d;
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
	
	public int getDaysInMonth2(int year, int month) {
		int ret;
		if (month == 4 || month == 6 || month == 9 || month == 11) {
			ret = 30;
		} else { 
			if (month == 2) {
				ret = (isLeapYear(year))  ? 29 : 28;
			} else {
				ret = 31;
			}
		}
		return ret;
	}
	
	public boolean isLeapYear(int y) {
		if( y % 4 == 0 ) {
			if( y % 100 == 0 ) {
				if( y % 400 == 0 ) {
					return true;
				} else {
					return false;
				}
			} else {
				return true;
			}
		} else {
			return false;
		}
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
		Calendar temp = Calendar.getInstance();
		temp.set(y, m-1, d);
		int a = temp.get(Calendar.DAY_OF_WEEK);
		if( a == 1) {
			a = 7;
		} else {
			a--;
		}
		return a;
	}
	
	public int getDaysBetweenDates(int y1, int m1, int d1, int y2, int m2, int d2) {
		Calendar temp1 = Calendar.getInstance();
		temp1.set(y1, m1-1, d1);
		Calendar temp2 = Calendar.getInstance();
		temp2.set(y2, m2-1, d2);
		long mil1 = temp1.getTimeInMillis();
		long mil2 = temp2.getTimeInMillis();
		long dif = (mil1 - mil2 >= 0) ? mil1 - mil2 : mil2 - mil1;
		int ret = (int) Math.floor(dif / 86400000);
		return ret;
	}
	
	public boolean legitimateWeek(int yS, int mS, int dS, int y, int m, int d, int del) {
		int dow = getDayOfWeek(yS, mS, dS);
		dS -= dow ; // Moving backward to nearest Sunday
		if( dS > getDaysInMonth2(y,m) ) {
			dS = dS % getDaysInMonth2(y,m);
			if( mS == 12 ) {
				mS = 1;
				yS++;
			} else {
				mS++;
			}
		}
		System.out.println("" + getDaysBetweenDates(yS,mS,dS,y,m,d));
		int WeeksFromStart = ((getDaysBetweenDates(yS,mS,dS,y,m,d)-1) - ((getDaysBetweenDates(yS,mS,dS,y,m,d)-1) % 7)) / 7; // Calculating full weeks between adjusted starting date and date to check
		if( WeeksFromStart == 0 ) {
			return true;
		} else if( WeeksFromStart == 1 && del != 0) {
			return false; 
		} else if( del != 0 ) {
			return (WeeksFromStart) % (del + 1) == 0; // WFS+1 equals the week the date to check is in (e.g. the second week). If the delay is 1 week, then the second week will pass, the fourth week will pass, and so on. If the delay is 2 weeks, then the third week will pass, the sixth week will pass, and so on. If the delay is 3 weeks, then the fourth week will pass, the eighth week will pass, and so on. Thus, for a delay of n weeks, every x(n+1), x in N, will pass
		} else {
			return true;
		}
	}
}

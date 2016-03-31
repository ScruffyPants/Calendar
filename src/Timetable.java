
public class Timetable {
	private static int year;
	private static int month;
	private static int day;
	
	public static void main(String[] args){
		Time time = new Time();
		year = time.getYear();
		month = time.getMonth();
		day = time.getDay();
		System.out.println(year+" "+month+" "+day);
		time.setYear(2002);
		time.setDay(20);
		year = time.getYear();
		day = time.getDay();
		System.out.println(year+" "+month+" "+day);
	}
	
}

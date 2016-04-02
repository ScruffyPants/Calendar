import java.util.LinkedList;

public class Timetable {
	private static int year;
	private static int month;
	private static int day;
	private static Time time = new Time();
	
	public static void main(String[] args){
		//===================PVZ kaip veikia time klase===============
			year = time.getYear();
			month = time.getMonth()+1;
			day = time.getDay();
			System.out.println(year+" "+month+" "+day);
			
			time.setYear(2002);
			time.setDay(20);
			year = time.getYear();
			day = time.getDay();
			System.out.println(year+" "+month+" "+day);
			
			//Event Debugging | Testing
			
			User user = new User("test", "test");
			Event one = new Event( "test", 2002, 12, 4 );
			Event two = new Event( "test", 2002, 12, 5 );
			Event three = new Event( "test", 2002, 11, 4 );
			Event four = new Event( "test", 2012, 12, 4 );
			Event five = new Event( "tes1t", 2002, 12, 4 );
			
			user.addEvent(one);
			user.addEvent(two);
			user.addEvent(three);
			user.addEvent(four);
			user.addEvent(five);
			
			LinkedList<Event> full = user.getEvents();
			LinkedList<Event> select = user.getEventsByDate(2002, 12, 4);
			Event given = null;
			for( int i = 0; i < full.size(); i++ ) {
				given = full.get(i);
				System.out.println(given.getYear() + " " + given.getMonth() + " " + given.getDay() + " (" + given.getName() + ")");
			}
			Event given2 = null;
			System.out.println("Events found when searching for 2002 / 12 / 4:");
			for( int i = 0; i < select.size(); i++ ) {
				given2 = select.get(i);
				System.out.println(given2.getYear() + " " + given2.getMonth() + " " + given2.getDay() + " (" + given2.getName() + ")");
			}
				
		//=============================================================
	}
	
}

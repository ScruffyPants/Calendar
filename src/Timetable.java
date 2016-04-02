import java.util.LinkedList;

public class Timetable {
	private static int year;
	private static int month;
	private static int day;
	private static Time time = new Time();
	
	public static void main(String[] args){
		//===================PVZ kaip veikia time klase===============
		/*
			year = time.getYear();
			month = time.getMonth()+1;
			day = time.getDay();
			System.out.println(year+" "+month+" "+day);
			
			time.setYear(2002);
			time.setDay(20);
			year = time.getYear();
			day = time.getDay();
			System.out.println(year+" "+month+" "+day);
		*/
		//==============================================================
			
			User user = new User("test","test", "12345");
			User user2 = new User("test2","test2", "123456");
			user2.setLname("test2");
			user.saveUser();
			user2.saveUser();
			//Log login = new Log();
			
			int daysinmonth = time.getDaysInMonth();
			System.out.println("Days in month = "+daysinmonth);

			//Event Debugging | Testing
			
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
			LinkedList<Event> select2 = user.getEventsByDateRange(2002, 12, 4, 2012, 12, 3);
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
			Event given3 = null;
			System.out.println("Events found when searching the range 2002 / 12 / 4  ---  2012 / 12 / 3:");
			for( int i = 0; i < select2.size(); i++ ) {
				given3 = select2.get(i);
				System.out.println(given3.getYear() + " " + given3.getMonth() + " " + given3.getDay() + " (" + given3.getName() + ")");
			}
			
			User test = new User("test", "test", "Allah");
			System.out.println("Testing password 'Bruh': " + test.checkPassword("Bruh"));
			System.out.println("Testing password 'Dawg': " + test.checkPassword("Dawg"));
			System.out.println("Testing password 'Allah': " + test.checkPassword("Allah"));
			System.out.println("Testing password 'Snoop Dogg': " + test.checkPassword("Snoop Dogg"));
		//=============================================================
	}
	
}

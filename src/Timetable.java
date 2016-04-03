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
			/*
			User user = new User("test","test", "12345");
			User user2 = new User("test2","test2", "123456");
			user2.setLname("test2");
			user.saveUser();
			user2.saveUser();
			Login login = new Login();
			*/
			
			//Login login = new Login();
			
			//int daysinmonth = time.getDaysInMonth();
			//System.out.println("Days in month = "+daysinmonth);
			
			//Login login = new Login();
		
			User user = new User();
			Event e1 = new Event("test", 2014, 3, 14);
			Event e2 = new Event("test2", 2010, 2, 15);
			Event e3 = new Event("test3", 2016, 6, 23);
			Event e4 = new Event("test4", 1999, 2, 30);
			user.addEvent(e1);
			user.addEvent(e2);
			user.addEvent(e3);
			user.addEvent(e4);
			LinkedList<Event> test = user.sortEvent(user.getEvents());
			for(Event a: test){
				System.out.println(a.getYear()+" "+a.getMonth()+" "+a.getDay());
			}
			//Body body = new Body(time);

			//Event Debugging | Testing
			/*
			User user = new User("test","test","12345");
			
			Event one = new Event( "test1", 2002, 12, 4 );
			Event two = new Event( "test2", 2002, 12, 5 );
			Event three = new Event( "test3", 2002, 11, 4 );
			Event four = new Event( "test4", 2012, 12, 4 );
			Event five = new Event( "test5", 2002, 12, 4 );
			Event six = new Event( "test6", 2012, 3, 6);
			
			user.addEvent(one);
			user.addEvent(two);
			user.addEvent(three);
			user.addEvent(four);
			user.addEvent(five);
			user.addEvent(six);
			
			LinkedList<Event> full = user.getEvents();
			//LinkedList<Event> select = user.getEventsByDate(2002, 12, 4);
			//LinkedList<Event> select2 = user.getEventsByDateRange(2002, 12, 4, 2012, 12, 3);
			Event given = null;
			System.out.println("All events");
			for( int i = 0; i < full.size(); i++ ) {
				given = full.get(i);
				System.out.println(given.getYear() + " " + given.getMonth() + " " + given.getDay() + " (" + given.getName() + ")");
			}
			/*Event given2 = null;
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
			System.out.println("Testing password 'Snoop Dogg': " + test.checkPassword("Snoop Dogg"));*/
			
			
		//=============================================================
	}
	
}

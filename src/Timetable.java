
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
		//=============================================================
			
		User user = new User("test", "test");
		System.out.println(user.getFname());
		user.saveUser();
		User user2 = new User("test2","test2");
		user2.saveUser();
		user.loadUser("test2");
		System.out.println(user.getFname());
	}
	
}

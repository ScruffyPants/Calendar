import java.util.LinkedList;
import java.io.*;

public class User implements Serializable{
	private String fname;
	private String lname;
	private LinkedList<Event> events = new LinkedList<Event>();//User will have an array with "events" that are later represented in Calendar
	private FileInputStream in = null;
	private FileOutputStream out = null;
	
	//Constructor for user
	public User(String fn, String ln){
		fname = fn;
		lname = ln; 
	}
	
	public User(){
		fname=null;
		lname=null;
	}
	
	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}
	
	public void addEvent(Event event){
		events.add(events.size(), event);
	}
	
	public void setEvents(LinkedList<Event> e){
		events = e;
	}
	
	public LinkedList<Event> getEvents(){
		return events;
	}
	
	public LinkedList<Event> getEventsByDate(int y, int m, int d) {
		LinkedList<Event> ret = new LinkedList<Event>();
		Event given = events.getFirst();
		for( int i = 0; i < events.size(); i++) {
			given = events.get(i);
			System.out.println("Year retrieved: " + given.getYear() + ", year specified: " + y);
			System.out.println("Month retrieved: " + given.getMonth() + ", month specified: " + m);
			System.out.println("Day retrieved: " + given.getDay() + ", day specified: " + d);
			if( given.getYear() == y ) {
				if( given.getMonth() == m ) {
					if( given.getDay() == d ) {
						ret.add(given);
						System.out.println("Match found!");
					}
				}
			}
		}
		return ret;
	}
	
	public void saveUser(){
		try{
			System.out.println("Saving User");
			String temp = "S:\\JAVA\\Calendar\\src\\Users\\"+this.getFname()+".txt"; // /Calendar/src/Users/test.txt
			System.out.println("File location = "+temp);
			out = new FileOutputStream(temp);
			ObjectOutputStream outObject = new ObjectOutputStream(out);
			outObject.writeObject(this);
			outObject.close();
			out.close();
		}
		catch(FileNotFoundException e){
			System.err.println("ERROR 404: FILE NOT FOUND");
		}
		catch(IOException i){
			System.err.println("IOException error");
		}
	}
	
	public void loadUser(String fName){
		try{
			System.out.println("Loading User");
			String temp = "S:\\JAVA\\Calendar\\src\\Users\\"+fName+".txt";
			in = new FileInputStream(temp);
			ObjectInputStream inObject = new ObjectInputStream (in);
			User user = new User();
			user = (User) inObject.readObject();
			this.setFname(user.getFname());
			this.setLname(user.getLname());
			this.setEvents(user.getEvents());
			inObject.close();
			out.close();
		}
		catch(FileNotFoundException e){
			System.err.println("ERROR 404: FILE NOT FOUND");
		}
		catch(IOException e){
			System.err.println("IOException error");
		}
		catch(ClassNotFoundException e){
			System.err.println("User class not found");
		}
	}
}

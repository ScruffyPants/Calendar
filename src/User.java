
public class User {
	private String fname;
	private String lname;
	private Event[] events;//User will have an array with "events" that are later represented in Calendar
	
	//Constructor for user
	public User(String fn, String ln){
		fname = fn;
		lname = ln; 
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

	public Event[] getEvents() {
		return events;
	}

	public void setEvents(Event[] events) {
		this.events = events;
	}
}

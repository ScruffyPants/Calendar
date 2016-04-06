import java.io.Serializable;
import java.util.LinkedList;

public class Group implements Serializable{
	private static final long serialVersionUID = 1504199602031999L;
	private String name;
	private LinkedList<User> users = new LinkedList<User>();
	private LinkedList<Event> events = new LinkedList<Event>();
	public Group(String n){
		name = n;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LinkedList<User> getUsers() {
		return users;
	}
	public void setUsers(LinkedList<User> users) {
		this.users = users;
	}
	public LinkedList<Event> getEvents() {
		return events;
	}
	public void setEvents(LinkedList<Event> events) {
		this.events = events;
	}
}

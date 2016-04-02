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
	
	public LinkedList<Event> getEvents(){
		return events;
	}
	
	public void saveUser(){
		try{
			in = new FileInputStream("Users/"+getFname()+".txt");
			
		}catch(FileNotFoundException e){
			System.err.println("ERROR 404: FILE NOT FOUND");
		}
	}
}

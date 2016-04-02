import java.util.LinkedList;
import java.util.Objects;
import java.io.*;
import java.security.MessageDigest;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class User implements Serializable{
	private static final long serialVersionUID = 1504199602031999L;
	private String fname;
	private String lname;
	private String pw_hash;
	private LinkedList<Event> events = new LinkedList<Event>();//User will have a linked list with "events" that are later represented in Calendar
	private FileInputStream in = null;
	private FileOutputStream out = null;
	private boolean IsTeacher = false;
	private boolean IsAdmin = false;
	
	//Constructor for user
	public User(String fn, String ln, String pw){
		fname = fn;
		lname = ln;
		try {
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] pw_hash_bytes = md.digest(pw.getBytes("UTF-8"));
        StringBuilder builder = new StringBuilder();
        for( byte a : pw_hash_bytes )
        {
            builder.append(String.format("%02X ", a ));
        }         
        pw_hash = builder.toString();
		} catch (UnsupportedEncodingException e) {
			System.out.println("The specified encoding is not supported on your machine");
		} catch (NoSuchAlgorithmException a) {
			System.out.println("The specified hashing function is not supported by your java implementation");
		}
	}
	
	public User(String fn){
		fname = fn;
	}
	
	public User(){
		fname=null;
		lname=null;
		pw_hash=null;
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
	
	public void addEvent(Event event) {
		events.add(events.size(), event);
	}
	
	public String getPW_Hash() {
		return pw_hash;
	}
	
	public void setEvents(LinkedList<Event> e) {
		events = e;
	}
	
	public LinkedList<Event> getEvents(){
		return events;
	}
	
	public void setIsTeacher(boolean a){
		IsTeacher = a;
	}
	
	public void setIsAdmin(boolean a){
		IsAdmin = a;
	}
	
	public LinkedList<Event> getEventsByDate(int y, int m, int d) {
		LinkedList<Event> ret = new LinkedList<Event>();
		Event given = events.getFirst();
		for( int i = 0; i < events.size(); i++) {
			given = events.get(i);
			//System.out.println("Year retrieved: " + given.getYear() + ", year specified: " + y);
			//System.out.println("Month retrieved: " + given.getMonth() + ", month specified: " + m);
			//System.out.println("Day retrieved: " + given.getDay() + ", day specified: " + d);
			if( given.getYear() == y & given.getMonth() == m & given.getDay() == d )
				ret.add(given);
				//System.out.println("Match found!");
		}
		return ret;
	}
	
	public boolean checkPassword (String pw)
	{
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] pw_hash_bytes = md.digest(pw.getBytes("UTF-8"));
			StringBuilder builder = new StringBuilder();
			for( byte a : pw_hash_bytes )
			{
				builder.append(String.format("%02X ", a ));
			}         
			String pw_hash_toCheck = builder.toString();
			if(Objects.equals(pw_hash_toCheck, this.getPW_Hash()))
				return true; 
			else
				return false; 
		} catch (UnsupportedEncodingException e) {
			System.out.println("The specified encoding is not supported on your machine");
		} catch (NoSuchAlgorithmException a) {
			System.out.println("The specified hashing function is not supported by your java implementation");
		} 
		System.out.println("Something went wrong...");
		return false;
	}
	
	public void saveUser(){
		try{
			System.out.println("Saving User");
			String temp = "S:\\JAVA\\Calendar\\src\\Users\\"+this.getFname()+".txt"; // /Calendar/src/Users/test.txt
			System.out.println("File location = "+temp);
			out = new FileOutputStream(temp);
			ObjectOutputStream outObject = new ObjectOutputStream(out);
			User user = new User();
			user.setEvents(this.getEvents());
			user.setFname(this.getFname());
			user.setLname(this.getLname());
			outObject.writeObject(user);
			outObject.close();
			out.close();
		}
		catch(FileNotFoundException e){
			System.err.println("ERROR 404: FILE NOT FOUND");
		}
		catch(IOException i){
			System.err.println("IOException error");
			i.printStackTrace();
		}
	}
	
	public void loadUser(String fName){
		try{
			System.out.println("Loading User");
			String temp = "S:\\JAVA\\Calendar\\src\\Users\\"+fName+".txt";
			in = new FileInputStream(temp);
			ObjectInputStream inObject = new ObjectInputStream(in);
			User user = new User();
			user = (User) inObject.readObject();
			this.setFname(user.getFname());
			this.setLname(user.getLname());
			this.setEvents(user.getEvents());
			inObject.close();
			in.close();
		}
		catch(FileNotFoundException e){
			System.err.println("ERROR 404: FILE NOT FOUND");
		}
		catch(IOException i){
			System.err.println("IOException error");
			i.printStackTrace();
		}
		catch(ClassNotFoundException e){
			System.err.println("User class not found");
		}
	}
}

import java.util.LinkedList;
import java.util.Objects;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class User implements Serializable{
	private static final long serialVersionUID = 1504199602031999L;
	private final String dir = System.getProperty("user.dir");
	private String fname;
	private String lname;
	private String nick;
	private String pw_hash;
	private LinkedList<Event> events = new LinkedList<Event>();//User will have a linked list with "events" that are later represented in Calendar
	private LinkedList<Event> pEvents = new LinkedList<Event>();//Public event made by teacher or admin
	private LinkedList<Schedule> schedules = new LinkedList<Schedule>();
	private LinkedList<String> groups = new LinkedList<String>();
	private Style style = new Style();
	private transient FileInputStream in = null;
	private transient FileOutputStream out = null;
	private boolean isTeacher = false;
	private boolean isAdmin = false;
	private boolean isVerified = false;
	
	//Constructor for user
	public User(String fn, String ln, String pw){
		fname = fn;
		lname = ln; 
        pw_hash = hashPassword(pw);
        style = new Style();
	}
	
	public User(String uname, String pw) {
		nick = uname;
		pw_hash = hashPassword(pw);
		style = new Style();
	}
	
	public User(String fn){
		fname = fn;
		style = new Style();
	}
	
	public User(){
		nick=null;
		fname=null;
		lname=null;
		pw_hash=null;
		style = new Style();
	}
	
	public String getFname() {
		return fname;
	}

	public void setFname(String a) {
		fname = a;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String a) {
		lname = a;
	}
	
	public String getNick() {
		return nick;
	}
	
	public void setNick(String a) {
		nick = a;
	}
	
	public LinkedList<Event> sortEvent(LinkedList<Event> event){
		LinkedList<Event> ans = new LinkedList<Event>();
		if(events.size()>1){
			int lenght = event.size(); 
			ans = quickSort(0, lenght-1, event);
			return ans;
		}
		return ans;
	}
	
	private LinkedList<Event> quickSort(int lIndex, int hIndex, LinkedList<Event> event){
		int i = lIndex;
		int j = hIndex;
		LinkedList<Event> ans = new LinkedList<Event>();
		
		int pivot = lIndex+(hIndex-lIndex)/2;
		
		while(i <= j){
			while(event.get(i).compare(event.get(i),event.get(pivot))==-1){
				i++;
			}
			while(event.get(i).compare(event.get(j), event.get(pivot))==1){
				j--;
			}
			if(i<=j){
				ans = exchangeNumbers(i,j,event);
				i++;
				j--;
			}
		}
		
		if(lIndex < j)quickSort(lIndex, j, event);
		if(i < hIndex)quickSort(i, hIndex, event);
		
		return ans; 	
	}
	
	private LinkedList<Event> exchangeNumbers(int i, int j, LinkedList<Event> event){
		Event temp = event.get(i);
		event.set(i, event.get(j));
		event.set(j, temp);
		return event;
	}
	
	public void addEvent(Event event) {
		//System.out.println("Event to add: " + event.getYear() + "/" + event.getMonth() + "/" + event.getDay());
		events.add(event);
	}
	
	public void addSchedule(Schedule schedule) {
		schedules.add(schedule);
	}
	
	public String getPW_Hash() {
		return pw_hash;
	}
	
	public void setPW_Hash(String pw_hash_new) {
		pw_hash = pw_hash_new;
	}
	
	public void setPEvents(LinkedList<Event> e) {
		pEvents = e;
	}
	
	public LinkedList<Event> getPEvents() {
		return pEvents;
	}
	
	public void setEvents(LinkedList<Event> e) {
		events = e;
	}
	
	public LinkedList<Event> getEvents() {
		return events;
	}
	
	public String printEvents() {
		LinkedList<Event> events = getEvents();
		StringBuilder sb = new StringBuilder();
		if( events != null )
		{
			Event given = null;
			for(int i = 0; i < events.size(); i++) {
				given = events.get(i);
				sb.append("==== Event #" + i + ": "+ given.getYear() + "/" + given.getMonth() + "/" + given.getDay() + ", " + given.getName() + " (" + given.getDescription() + ")");
			}
			return sb.toString();
		}
		return "No events.";
	}
	
	public boolean getIsTeacher() {
		return isTeacher;
	}
	
	public void setIsTeacher(boolean a) {
		isTeacher = a;
	}
	
	public boolean getIsAdmin() {
		return isAdmin;
	}
	
	public void setIsAdmin(boolean a) {
		isAdmin = a;
	}
	
	public boolean getIsVerified() {
		return isVerified;
	}
	
	public void setIsVerified(boolean s) {
		isVerified = s;
	}
	
	public LinkedList<Event> getEventsByDate(int y, int m, int d) {
		LinkedList<Event> ret = new LinkedList<Event>();
		if( events.size() > 0 ) {
			Event given = events.getFirst();
			for( int i = 0; i < events.size(); i++) {
				given = events.get(i);
				if( given.getYear() == y & given.getMonth() == m & given.getDay() == d )
					ret.add(given);
			}
			return ret; }
		else{
			return events;
		}
	}
	
	public LinkedList<Event> getPEventsByDate(int y, int m, int d) {
		LinkedList<Event> ret = new LinkedList<Event>();
		if( pEvents.size() > 0 ) {
			Event given = pEvents.getFirst();
			for( int i = 0; i < pEvents.size(); i++) {
				given = pEvents.get(i);
				if( given.getYear() == y & given.getMonth() == m & given.getDay() == d )
					ret.add(given);
			}
			return ret; 
		}
		else{
			return pEvents;
		}
	}
	
	public LinkedList<Event> getEventsByDateRange(int y1, int m1, int d1, int y2, int m2, int d2) {
		int yMax = (y1 > y2) ? y1 : y2;
		int yMin = (y1 < y2) ? y1 : y2;
		int mOfMax = (y1 > y2) ? m1 : m2;
		int mOfMin = (y1 < y2) ? m1 : m2;
		int dOfMax = (y1 > y2) ? d1 : d2;
		int dOfMin = (y1 < y2) ? d1 : d2;
		
		if( events.size() > 0) {
			LinkedList<Event> ret = new LinkedList<Event>();
			Event given = events.getFirst();
			for( int i = 0; i < events.size(); i++) {
				given = events.get(i);
				if( given.getYear()>= yMin && given.getYear() <= yMax ) {
					if( given.getYear() == yMin) {
						if( given.getMonth() >= mOfMin && given.getDay() >= dOfMin ) {
							ret.add(given);
						}
					}
					else if( given.getYear() == yMax ) {
						if( given.getMonth() <= mOfMax && given.getDay() <= dOfMax ) {
							ret.add(given);
						}
					}
					else
						ret.add(given);
				}
			}
		return ret;
		}
		throw new RuntimeException();
	}
	
	public LinkedList<Event> getEventsByName(String toCheck) {
		LinkedList<Event> ret = new LinkedList<Event>();
		Event given = events.getFirst();
		for( int i = 0; i < events.size(); i++) {
			given = events.get(i);
			if( Objects.equals(given.getName(), toCheck) )
				ret.add(given);
		}
		return ret;
	}
	
	public String hashPassword(String pw) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] pw_hash_bytes = md.digest(pw.getBytes("UTF-8"));
			StringBuilder builder = new StringBuilder();
			for( byte a : pw_hash_bytes )
			{
				builder.append(String.format("%02X", a ));
			}         
			String ret = builder.toString();
			return ret;
		} catch (UnsupportedEncodingException e) {
			System.out.println("The specified encoding is not supported on your machine");
		} catch (NoSuchAlgorithmException a) {
			System.out.println("The specified hashing function is not supported by your java implementation");
		} 
		System.out.println("Something went wrong...");
		return null;
	}
	
	public boolean checkPassword (String pw)
	{    
		String pw_hash_toCheck = hashPassword(pw);
		if(Objects.equals(pw_hash_toCheck, this.getPW_Hash()))
			return true; 
		else
			return false; 
	}
	
	public void saveUser(){
		try{
			//System.out.println("Saving User");
			String temp = dir + "/src/Users/"+this.getNick()+".txt";
			//System.out.println("File location = "+temp);
			out = new FileOutputStream(temp);
			ObjectOutputStream outObject = new ObjectOutputStream(out);
			User user = new User();
			user.setEvents(this.getEvents());
			user.setSchedules(this.getSchedules());
			user.setNick(this.getNick());
			user.setFname(this.getFname());
			user.setLname(this.getLname());
			user.setPW_Hash(this.getPW_Hash());
			user.setIsAdmin(this.getIsAdmin());
			user.setIsTeacher(this.getIsTeacher());
			user.setIsVerified(this.getIsVerified());
			user.setStyle(this.getStyle());
			user.setGroups(this.getGroups());
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
	
	public void loadUser(String Nick){
		try{
			//System.out.println("Loading User");
			String temp = dir + "/src/Users/" + Nick + ".txt";
			in = new FileInputStream(temp);
			ObjectInputStream inObject = new ObjectInputStream(in);
			User user = new User();
			user = (User) inObject.readObject();
			this.setFname(user.getFname());
			this.setLname(user.getLname());
			this.setNick(user.getNick());
			this.setEvents(user.getEvents());
			this.setSchedules(user.getSchedules());
			this.setPW_Hash(user.getPW_Hash());
			this.setIsAdmin(user.getIsAdmin());
			this.setIsTeacher(user.getIsTeacher());
			this.setIsVerified(user.getIsVerified());
			this.setStyle(user.getStyle());
			this.setGroups(user.getGroups());
			inObject.close();
			in.close();
		}
		catch(FileNotFoundException e){
			System.err.println("ERROR 404: FILE NOT FOUND");
			JOptionPane.showMessageDialog(null, "File for user "+Nick+" not found");
		}
		catch(IOException i){
			System.err.println("IOException error");
			i.printStackTrace();
		}
		catch(ClassNotFoundException e){
			System.err.println("User class not found");
		}
	}

	public Style getStyle() {
		return style;
	}

	public void setStyle(Style style) {
		this.style = style;
	}
	
	public void clearPEvents(){
		pEvents = new LinkedList<Event>();
	}
	
	public void clearEvents(){
		events = new LinkedList<Event>();
	}
	
	public LinkedList<Schedule> getSchedulesByDate(int y, int m, int d) {
		//System.out.println("Checking for date " + y + "//" + m + "//" + d);
		LinkedList<Schedule> ret = new LinkedList<Schedule>();
		Schedule given = null;
		Time time = new Time();
		int dow = time.getDayOfWeek(y, m, d);
		//System.out.println("" + dow);
		for( int i = 0; i < schedules.size(); i++ ) {
			given = schedules.get(i);
			if( checkInRange( y, m, d, given.getYStart(), given.getMStart(), given.getDStart(), given.getYEnd(), given.getMEnd(), given.getDEnd() ) ) {
				//System.out.println("" + given.getDays()[dow-1]);
				//System.out.println(y + "//" + m + "//" + d + " is in the range of " + given.getYStart() + "//" + given.getMStart() + "//" + given.getDStart() + " --- " + given.getYEnd() + "//" + given.getMEnd() + "//" + given.getDEnd());
				//System.out.println("Current day is within schedule range");
				if( given.getDays()[dow-1] ) {
					//System.out.println("Current day is an active day in schedule");
					if( time.legitimateWeek( given.getYStart(), given.getMStart(), given.getDStart(), y, m, d, given.getDelay()) ) {
						//System.out.println("Current week is legitimate");
						ret.add(given);
					}
				}
			}
		}
		return ret;
	}
	
	private boolean checkInRange( int yC, int mC, int dC, int y1, int m1, int d1, int y2, int m2, int d2) {
		if( y1 != y2 ) {
			if( yC >= y1 && yC <= y2 ) {
				if( yC == y1 ) {
					if( mC >= m1 ) {
						if( mC == m1 ) {
							if( dC >= d1 ) {
								//System.out.println("Date to check has equal year and month to beginning, but equal or later day");
								return true;
							} else {
								//System.out.println("Date to check has equal year and month to beginning, but earlier day");
								return false;
							}
						} else {
							//System.out.println("Date to check has equal year to beginning and later month");
							return true;
						}
					} else {
						//System.out.println("Date to check has equal year to beginning, but earlier month");
						return false;
					}
				} else if( yC == y2 ) {
					if( mC <= m2 ) {
						if( mC == m2 ) {
							if( dC <= d2 ) {
								//System.out.println("Date to check has equal year and month to end, but equal or earlier day");
								return true; 
							} else {
								//System.out.println("Date to check has equal year and month to end, but later day");
								return false;
							}
						} else {
							//System.out.println("Date to check has equal year to end and earlier month");
							return true; 
						}
					} else {
						//System.out.println("Date to check has equal year to end, but later month");
						return false;
					}
				} else {
					//System.out.println("Date to check has later year than beginning and earlier year than end");
					return true;
				}
			} else {
				//System.out.println("Date to check has earlier year than beginning or later year than end");
				return false;
			}
		} else { //Starting year is equal to ending year
			if( yC == y1 ) { //Year to check is equal to both starting and ending year
				if( m1 == m2 ) { //Starting month is equal to ending month
					if( mC == m1 ) { //Month to check is equal to both starting and ending month
						if( d1 == d2 ) { //Starting day is equal to ending day
							if( dC == d1 ) {
								return true; //Day to check is equal to both starting and ending day
							} else {
								return false; //Day to check is not equal to both starting and ending day
							}
						} else {
							if( dC >= d1 && dC <= d2 ) {
								return true; //Day to check is later than or equal to the starting day and the earlier than or equal to the ending day
							} else {
								return false; //Day to check is earlier than the starting day or later than the ending day
							}
						}
					} else { 
						return false; //
					}
				} else {
					if( mC >= m1 && mC <= m2 ) {
						if( mC == m1 ) {
							if( dC >= d1 ) {
								return true;
							} else {
								return false;
							}
						} else if( mC == m2 ) {
							if( dC <= d2 ) {
								return true;
							} else {
								return false;
							}
						} else {
							return true;
						}
					} else {
						return false;
					}
				}
			} else {
				return false;
			}
		}
	}

	public void removeGroup(String group){
		for(int i=0; i<groups.size(); i++){
			System.out.println(group+" and "+groups.get(i));
			if(groups.get(i).equals(group)){
				groups.remove(i);
				System.out.print("Removed from "+group);
			}
		}
	}
	
	public String printSchedules() {
		Schedule given = null;
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < schedules.size(); i++) {
			given = schedules.get(i);
			sb.append("=====" + given.getYStart() + "/" + given.getMStart() + "/" + given.getDStart() + " --- " + given.getYEnd() + "/" + given.getMEnd() + "/" + given.getDEnd() + "  " + given.getName() + "(" + given.getDescription() + ")");
		}
		return sb.toString();
	}

	//Deprecated
	
	public void verifyUser(User toVerify) {
		if( isAdmin && isVerified ) {
			toVerify.setIsVerified(true);
		} else {
			System.out.println("You are not authorized to perform this operation.");
		}
	}
	
	public void addPEvent(Event e){
		pEvents.add(e);
	}

	public LinkedList<Schedule> getSchedules() {
		return schedules;
	}

	public void setSchedules(LinkedList<Schedule> schedules) {
		this.schedules = schedules;
	}

	public LinkedList<String> getGroups() {
		return groups;
	}

	public void setGroups(LinkedList<String> groups) {
		this.groups = groups;
	}
	
	public void addGroup(String g){
		groups.add(g);
	}
}

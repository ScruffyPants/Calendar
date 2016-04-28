import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;
import javax.swing.*;
import java.util.Objects;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.Serializable;

public class Group implements Serializable{
	private static final long serialVersionUID = 1504199602031999L;
	private final String dir = System.getProperty("user.dir");
	private String name;
	private LinkedList<User> users = new LinkedList<User>();
	private LinkedList<User> admins = new LinkedList<User>();
	private LinkedList<Event> events = new LinkedList<Event>();
	private FileInputStream in = null;
	private FileOutputStream out = null;
	
	public Group(){
		name = "";
	}
	public Group(String n, User user){
		name = n;
		admins.add(user);
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
	public void addUser(User user){
		users.add(user);
	}
	public void addAdmin(User user){
		admins.add(user);
	}
	public LinkedList<User> getAdmins() {
		return admins;
	}
	public void setAdmins(LinkedList<User> admins) {
		this.admins = admins;
	}
	public void removeUser(String user){
		for(int i=0;i<users.size();i++)
			if(users.get(i).getNick().equals(user)){
				users.remove(i);
				break;
			}
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
	
	public void addEvent(Event a){
		events.add(a);
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
	
	public void saveGroup(){
		try{
			System.out.println("Saving Group");
			String temp = dir + "/src/Groups/"+this.getName()+".txt";
			System.out.println("File location = "+temp);
			out = new FileOutputStream(temp);
			ObjectOutputStream outObject = new ObjectOutputStream(out);
			Group group = new Group();
			group.setName(this.getName());
			group.setUsers(this.getUsers());
			group.setAdmins(this.getAdmins());
			group.setEvents(this.getEvents());
			outObject.writeObject(group);
			outObject.close();
			out.close();
		}		
		catch(FileNotFoundException e){
			System.err.println("Error group file for saving not found");
		}
		catch(IOException i){
			System.err.println("IOException error in group save");
			i.printStackTrace();
		}
	}
	public boolean isAdmin(User user){
		for(User a: admins){
			if(a.getNick().equals(user.getNick()))return true;
		}
		return false;
	}
	
	public void removeAdmin(User user){
		for(int i=0; i<admins.size(); i++){
			if(admins.get(i).getNick().equals(user.getNick()))admins.remove(i);
		}
	}
	
	public void loadGroup(String Name){
		try{
			System.out.println("Loading User");
			String temp = dir + "/src/Groups/" + Name + ".txt";
			in = new FileInputStream(temp);
			ObjectInputStream inObject = new ObjectInputStream(in);
			Group group = new Group();
			group = (Group) inObject.readObject();
			this.setName(group.getName());
			this.setAdmins(group.getAdmins());
			this.setUsers(group.getUsers());
			this.setEvents(group.getEvents());
			inObject.close();
			in.close();
		}
		catch(FileNotFoundException e){
			System.err.println("Error group file for loading not found");
		}
		catch(IOException i){
			System.err.println("IOException error in group load");
			i.printStackTrace();
		}
		catch(ClassNotFoundException e){
			System.err.println("User class not found in group load");
		}
	}
}

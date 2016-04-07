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

public class Group implements Serializable, ActionListener{
	private static final long serialVersionUID = 1504199602031999L;
	private final String dir = System.getProperty("user.dir");
	private String name;
	private LinkedList<User> users = new LinkedList<User>();
	private LinkedList<User> admins = new LinkedList<User>();
	private LinkedList<Event> events = new LinkedList<Event>();
	private FileInputStream in = null;
	private FileOutputStream out = null;
	
	private transient JTextPane namepane = new JTextPane();
	private transient JFrame frame = new JFrame();
	private transient User tempuser = new User();
	
	public Group(){
		name = null;
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
	
	public void loadGroup(String Name){
		try{
			System.out.println("Loading User");
			String temp = dir + "/src/Groups/" + Name + ".txt";
			in = new FileInputStream(temp);
			ObjectInputStream inObject = new ObjectInputStream(in);
			Group group = new Group();
			group = (Group) inObject.readObject();
			this.setName(group.getName());
			this.setEvents(group.getEvents());
			this.setUsers(group.getUsers());
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
	public Group createNewGroup(User user){
		tempuser = user;
		frame = new JFrame();
		JPanel panel = new JPanel();
		namepane = new JTextPane();
		JButton confirm = new JButton("Confirm");
		
		panel.setLayout(new GridLayout(0,1));
		panel.add(new JLabel("Name: "));
		panel.add(namepane);
		panel.add(confirm);

		
		frame.setLocationRelativeTo(null);
		frame.pack();
		frame.add(panel);
		frame.setMinimumSize(new Dimension(400,200));
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
		
		confirm.addActionListener(this);

		return this;
	}
	public void showGroupManage(User user){
		JPanel panel = new JPanel();
		JLabel a = new JLabel();
		JButton edit = new JButton("Edit");
		frame = new JFrame();
		namepane = new JTextPane();
		
		frame.setLayout(new GridLayout(0,4));
		panel.setLayout(new GridLayout(0,1));
		System.out.println("Checking for groups:"+user.getGroups().size());
		for(Group g: user.getGroups()){
			System.out.println("Found a group");
			a.setText(g.getName());
			panel.add(a);
			panel.add(new JLabel("Events:"));
			JScrollPane scrollpane = new JScrollPane();
			JPanel eventpanel = new JPanel();
			eventpanel.setLayout(new GridLayout(0,2));
			for(Event e: g.getEvents()){
				eventpanel.add(new JLabel(e.getYear()+" "+e.getMonth()+" "+e.getDay()));
				eventpanel.add(new JLabel(e.getName()));
				scrollpane.add(eventpanel);
			}
			panel.add(scrollpane);
			scrollpane = new JScrollPane();
			JPanel userpanel = new JPanel();
			for(User u: g.getUsers()){
				userpanel.add(new JLabel(u.getNick()));
				scrollpane.add(userpanel);
			}
			panel.add(scrollpane);
			panel.add(edit);
		}
		panel.setBorder(BorderFactory.createEtchedBorder());
		frame.add(panel);
		frame.setVisible(true);
		frame.setMinimumSize(new Dimension(400,200));
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
    public void actionPerformed(ActionEvent e) {
    	if(e.getActionCommand() == "Confirm"){
    		if(!namepane.getText().isEmpty()){
    			this.setName(namepane.getText());
    			this.addUser(tempuser);
    			this.addAdmin(tempuser);
    			frame.setVisible(false);
    			frame.dispose();
    		}
    	}
    	else if(e.getActionCommand() == "Edit"){
    		JFrame pFrame = new JFrame();
    		JPanel panel = new JPanel();
    	}
    }
}

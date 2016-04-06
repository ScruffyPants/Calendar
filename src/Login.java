import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.LinkedList;
import java.io.FileNotFoundException;
import java.io.IOException;


public class Login extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1504199602031999L;
	private static Time time = new Time();
	private final String dir = System.getProperty("user.dir");


	JPanel panel = new JPanel();
	
	JButton blogin = new JButton("Login");
	JButton register = new JButton("Register");
	JTextField txuser = new JTextField(15);
	JPasswordField pass = new JPasswordField(15);
	
	
	Login(){
		super("Login Authentification");
		setSize(300,180);
		setLocation(500,280);
		panel.setLayout (null);
	
		txuser.setBounds(90,30,150,20);
		pass.setBounds(90,65,150,20);
		blogin.setBounds(45,100,100,20);
		register.setBounds(155,100,100,20);
	
		panel.add(blogin);
		panel.add(txuser);
		panel.add(pass);
		panel.add(register);
		
		JLabel usr = new JLabel("Username:");
		JLabel pw = new JLabel("Password:");
		usr.setLocation(15,30);
		pw.setLocation(15,65);
		pw.setSize(pw.getPreferredSize());
		usr.setSize(usr.getPreferredSize());
		panel.add(pw);
		panel.add(usr);
	
		getContentPane().add(panel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		blogin.addActionListener(this);
		register.addActionListener(this);
	}
	
	@SuppressWarnings("unchecked")
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == blogin) {
			String puname = txuser.getText();
			String ppaswd = pass.getText();
			File temp = new File( dir + "/src/Users/"+puname+".txt"); 
			if(temp.exists() && !temp.isDirectory()){
				User user = new User(puname);
				user.loadUser(puname);
				boolean login = user.checkPassword(ppaswd);
				if(login){
					try{
					System.out.println("Logged in!");
					System.out.println("name = " + user.getLname());
					System.out.println("Login Month: "+time.getMonth());
					FileInputStream ftemp = new FileInputStream(dir + "\\src\\pEvents\\pEvents.txt");
					ObjectInputStream object = new ObjectInputStream(ftemp);
					LinkedList<Event> pEvents = new LinkedList<Event>();
					pEvents = (LinkedList<Event>) object.readObject();
					object.close();
					user.setPEvents(pEvents);
					} catch(FileNotFoundException ee) {
						
					} catch(ClassNotFoundException a) {
						
					} catch(IOException oo) {
						
					}
					
					Body body = new Body(time, user);
					JFrame w = (JFrame) SwingUtilities.getWindowAncestor(panel);
                    w.dispose();
				}
				else System.out.println("Invalid login details.");
			}
		}
		else if (e.getSource()==register){
			Registration regFace = new Registration();
			dispose();
		}
	}
}
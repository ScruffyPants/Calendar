import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class Login extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1504199602031999L;
	private static Time time = new Time();

	JPanel panel = new JPanel();
	
	public static void main(String[] args) {
		Login frameTabel = new Login();
	}
	JButton blogin = new JButton("Login");
	JButton register = new JButton("Register");
	JTextField txuser = new JTextField(15);
	JPasswordField pass = new JPasswordField(15);
	
	
	Login(){
		super("Login Authentification");
		setSize(320,200);
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
		blogin.addActionListener(this);
		register.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == blogin) {
			String puname = txuser.getText();
			String ppaswd = pass.getText();
			File temp = new File("C:\\Users\\Jonas\\Eigene Dokumente\\GitHub\\Calendar\\src\\Users\\"+puname+".txt"); 
			if(temp.exists() && !temp.isDirectory()){
				User user = new User(puname);
				user.loadUser(puname);
				boolean login = user.checkPassword(ppaswd);
				if(login){
					System.out.println("Logged in!");
					System.out.println("name = " + user.getLname());
					Body body = new Body(time);
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
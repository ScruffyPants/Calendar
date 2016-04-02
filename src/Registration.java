import javax.swing.*;
import java.awt.event.*;

public class Registration extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1504199602031999L;

	public static void main(String[] args) {
		Registration frameTabel = new Registration();
	}
	JButton register = new JButton("Register");
	JPanel panel = new JPanel();
	JTextField txuser = new JTextField(15);
	JPasswordField pass = new JPasswordField(15);
	String[] ranks = { "Student", "Teacher", "Admin" };
	JComboBox<String> rank = new JComboBox<>(ranks);
	
	
	
	Registration(){
		super("Login Authentification");
		setSize(320,200);
		setLocation(500,280);
		panel.setLayout (null);
	
	
		txuser.setBounds(90,30,150,20);
		pass.setBounds(90,65,150,20);
		register.setBounds(115,125,100,20);
		rank.setBounds(115,95,100,20);
	
		panel.add(txuser);
		panel.add(pass);
		panel.add(register);
		panel.add(rank);
		
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
		register.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == register){
			User created = new User(txuser.getText(), pass.getText());
			   if( rank.getSelectedIndex() == 1 )
				   created.setIsTeacher(true);
			   else if( rank.getSelectedIndex() == 2 )
				   created.setIsAdmin(true);
			   System.out.println("Nickname: " + created.getNick() + ", hashed password: " + created.getPW_Hash());
			   created.saveUser();
		}
	}
}
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class Log extends JFrame {

public static void main(String[] args) {
	Log frameTabel = new Log();
}

	JButton blogin = new JButton("Login");
	JPanel panel = new JPanel();
	JTextField txuser = new JTextField(15);
	JPasswordField pass = new JPasswordField(15);

	Log(){
		super("Login Authentification");
		setSize(300,200);
		setLocation(500,280);
		panel.setLayout (null);


		txuser.setBounds(70,30,150,20);
		pass.setBounds(70,65,150,20);
		blogin.setBounds(110,100,80,20);
	
		panel.add(blogin);
		panel.add(txuser);
		panel.add(pass);
	
		getContentPane().add(panel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		actionlogin();
	}

	public void actionlogin(){
		blogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String puname = txuser.getText();
				String ppaswd = pass.getText();
				
				File temp = new File("S:\\JAVA\\Calendar\\src\\Users\\"+puname+".txt"); 
				if(temp.exists() && !temp.isDirectory()){
					User user = new User(puname);
					user.loadUser(puname);
					boolean login = user.checkPassword(ppaswd);
					if(login){
						System.out.println("Logged in!");
						System.out.println("name = "+user.getLname());
					}
					else System.out.println("What is this?");
				}

			}	
		});
	}
}
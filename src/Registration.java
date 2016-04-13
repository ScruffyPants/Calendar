import javax.swing.*;
import java.awt.event.*;
import java.io.File;
 
public class Registration extends JFrame implements ActionListener{
    private static final long serialVersionUID = 1504199602031999L;
    private final String dir = System.getProperty("user.dir");
 
    JButton register = new JButton("Register");
    JButton goback = new JButton("Return");
    JPanel panel = new JPanel();
    JTextField txuser = new JTextField(15);
    JTextField name = new JTextField(15);
    JTextField lastname = new JTextField(15);
    JPasswordField pass = new JPasswordField(15);
    JPasswordField pass2 = new JPasswordField(15);
    String[] ranks = { "Student", "Teacher", "Admin" };
    JComboBox<String> rank = new JComboBox<>(ranks);
   
   
   
    Registration(){
        super("Account Registration");
        setSize(320,315);
        setLocation(500,280);
        panel.setLayout (null);
   
   
        txuser.setBounds(100,30,150,20);
        pass.setBounds(100,65,150,20);
        pass2.setBounds(100,100,150,20);
        name.setBounds(100,135,150,20);
        lastname.setBounds(100,170,150,20);
        register.setBounds(145,235,100,20);
        goback.setBounds(35,235,100,20);
        rank.setBounds(145,200,100,20);
   
        panel.add(txuser);
        panel.add(pass);
        panel.add(pass2);
        panel.add(register);
        panel.add(rank);
        panel.add(goback);
        panel.add(name);
        panel.add(lastname);
       
        JLabel usr = new JLabel("Username:");
        JLabel pw = new JLabel("Password:");
        JLabel pw2 = new JLabel("Repeat:");
        JLabel fname = new JLabel("First Name:");
        JLabel lname = new JLabel("Last Name:");
        usr.setLocation(15,30);
        pw.setLocation(15,65);
        pw2.setLocation(15, 100);
        fname.setLocation(15, 135);
        lname.setLocation(15, 170);
        usr.setSize(usr.getPreferredSize());
        pw.setSize(pw.getPreferredSize());
        pw2.setSize(pw2.getPreferredSize());
        fname.setSize(fname.getPreferredSize());
        lname.setSize(lname.getPreferredSize());
        
        panel.add(pw);
        panel.add(usr);
        panel.add(pw2);
        panel.add(fname);
        panel.add(lname);
   
        getContentPane().add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        panel.getRootPane().setDefaultButton(register);
        register.addActionListener(this);
        goback.addActionListener(this);
    }
 
    public void actionPerformed(ActionEvent e) {
    	if(e.getSource() == register){
    		File d = new File(dir + "/src/Users");
    		if( !d.exists() || !d.isDirectory() ) {
    			System.out.println("Users directory not found, creating..");
    			d.mkdir();
    		}
    		if(!txuser.getText().isEmpty() && !pass.getText().isEmpty() && pass.getText().equals(pass2.getText())) {
    			String temp = dir + "/src/Users/" + txuser.getText() + ".txt";
    			File f = new File(temp);
        		if(!f.exists()){
        			User created = new User(txuser.getText(), pass.getText());
        			created.setFname(name.getText());
        			created.setLname(lastname.getText());
               		if( rank.getSelectedIndex() == 1 )created.setIsTeacher(true);
               		else if( rank.getSelectedIndex() == 2 )created.setIsAdmin(true);
               
               		System.out.println("Nickname: " + created.getNick() + ", hashed password: " + created.getPW_Hash());
               		created.setIsVerified(true); //ā–�ā–�ā–�ā–�ā–�ā–�ā–�ā–�ā–�ā–�ā–�ā–�ā–�ā–�ā–�ā–�ā–�ā–�ā–�ā–�ā–�ā–�ā–�ā–�ā–�ā–�ā–�ā–�ā–� NEEDS TO BE DELETED BEFORE FINAL COMPILE  ā–�ā–�ā–�ā–�ā–�ā–�ā–�ā–�ā–�ā–�ā–�ā–�ā–�ā–�ā–�ā–�ā–�ā–�ā–�ā–�ā–�ā–�ā–�ā–�ā–�ā–�ā–�ā–�ā–�ā–�ā–�ā–�ā–�ā–�ā–�ā–�ā–�ā–�ā–�
               		created.saveUser();
               	
               		setVisible(false);
               		dispose();
               		Timetable.main(null);
        		}
        		else JOptionPane.showMessageDialog(null,"User already exists or password repeated incorrectly.");
    		}
    		else JOptionPane.showMessageDialog(null,"Registration requires at least a username and password.");
    	}
    	else if (e.getSource() == goback){
    		Login loginFace = new Login();
			dispose();
    	}
    }
}
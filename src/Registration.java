import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
 
public class Registration {
    
   private JFrame mainFrame;
   private JLabel headerLabel;
   private JLabel statusLabel;
   private JPanel controlPanel;
   private String text;
   private String pass;

   public Registration(){
      prepareGUI();
   }
   
   public void setText(String a) {
	   text = a;
   }
   
   public String getText() {
	   return text;
   }
   
   public void setPass(String a ) {
	   pass = a;
   }
   
   public String getPass() {
	   return pass;
   }

   public static void main(String[] args){
      Registration swingControlDemo = new Registration();      
   }

   private void prepareGUI(){
      mainFrame = new JFrame("Calendar Alpha");
      mainFrame.setSize(300,230);
      mainFrame.setLayout(null);
      mainFrame.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent windowEvent){
            System.exit(0);
         }        
      });    
      headerLabel = new JLabel("",JLabel.CENTER);        
      statusLabel = new JLabel("",JLabel.CENTER);   
      JButton bregister = new JButton("Register");
      JPanel panel = new JPanel(null);
      JTextField txuser = new JTextField(15);
      JPasswordField pass = new JPasswordField(15);
      txuser.setBounds(80,30,150,20);
      pass.setBounds(80,65,150,20);
      bregister.setBounds(100,135,110,20);
      
      JLabel usr = new JLabel("Username:");
      JLabel pw = new JLabel("Password:");
      usr.setLocation(10,30);
      pw.setLocation(10,65);
      pw.setSize(pw.getPreferredSize());
      usr.setSize(usr.getPreferredSize());
      mainFrame.add(pw);
      mainFrame.add(usr);
      

      statusLabel.setSize(350,100);

      controlPanel = new JPanel();
      controlPanel.setLayout(new FlowLayout());

      mainFrame.add(headerLabel);
      mainFrame.add(controlPanel);
      mainFrame.add(statusLabel);
      mainFrame.add(txuser);
      mainFrame.add(pass);
      mainFrame.add(bregister);
      //mainFrame.setVisible(true);  
      headerLabel.setText("Registration"); 

      final DefaultComboBoxModel rankName = new DefaultComboBoxModel();

      rankName.addElement("Student");
      rankName.addElement("Teacher");
      rankName.addElement("Admin");

      final JComboBox rankCombo = new JComboBox(rankName);    
      rankCombo.setSelectedIndex(0);
      rankCombo.setBounds(100,100,110,20);

      JScrollPane rankListScrollPane = new JScrollPane(rankCombo);    
      
      
      JButton registerButton = new JButton("Register");

      setText(txuser.getText());
      setPass(pass.getText());
      
      registerButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) { 
        	 User created = new User(getText(),getPass());
        	 if( rankCombo.getSelectedIndex() == 1 )
        		 created.setIsTeacher(true);
        	 else if( rankCombo.getSelectedIndex() == 2 )
        		 created.setIsAdmin(true);
        	 System.out.println("Nickname: " + created.getNick() + ", hashed password: " + created.getPW_Hash());
        	 //created.saveUser();
            }              
      }); 
      controlPanel.add(rankListScrollPane);          
      controlPanel.add(registerButton);
      mainFrame.add(rankCombo);
      mainFrame.setVisible(true);             
   }

}
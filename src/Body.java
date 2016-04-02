import javax.swing.*;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.*;

public class Body extends JFrame {
	
	JFrame frame = new JFrame("GridBagLayout");
	JPanel panel = new JPanel(new GridBagLayout());
	JPanel calendar = new JPanel(new GridLayout());
	JMenuBar menuBar = new JMenuBar();
	GridBagConstraints c = new GridBagConstraints();
	Time time;
	/*public static void main(String[] args) {
		Body frameTabel = new Body();
	}*/
	
	Body(Time t){
		time = t;
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 300);
		DrawMenu();
		DrawCalendar();
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.ipady = 0;
		c.weightx=1;
		c.weighty=1;
		panel.add(menuBar,c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 100;
		c.weightx = 0.0;
		c.weighty = 0;
		c.gridwidth = 3;
		c.gridheight = 2;
		c.gridx = 0;
		c.gridy = 1;
		panel.add(calendar,c);
		panel.setBorder(BorderFactory.createEtchedBorder());
		
		//frame.setSize(menuBar.getHeight(),calendar.getWidth());
		frame.add(panel);
		frame.setMinimumSize(new Dimension(500,270));
		frame.setMaximumSize(frame.getMinimumSize());
		frame.setVisible(true);
	}
	
	public void DrawMenu(){
		JMenu Calendar, Account, Info;
		JMenuItem Exit, Logout;
		
		Calendar = new JMenu("Calendar");
		Account = new JMenu("Account");
		Info = new JMenu("Info");
		
		menuBar.add(Calendar);
		menuBar.add(Account);
		menuBar.add(Info);
		
		Exit = new JMenuItem("Exit");
		Logout = new JMenuItem("Logout");
		
		Calendar.add(Exit);
		Account.add(Logout);
		
		//menuBar.setPreferredSize(new Dimension(100,500));
	}
	
	public void DrawCalendar(){
		double dim = (double)time.getDaysInMonth();
		int rows = (int)Math.ceil(dim/7);
		int cols = 7;
		System.out.println(rows+" x "+cols+" ");
		calendar.setLayout(new GridLayout(rows,cols,2,2));
		boolean done = false;
		int i=1;
		while(!done){
			if(i>dim){
				JLabel a = new JLabel(" ");
				calendar.add(a);
				if(i%7==0)done = true;
			}
			else{
				JButton a = new JButton(Integer.toString(i));
				a.setBackground(Color.white);
				a.setHorizontalAlignment(SwingConstants.LEFT);
				a.setBorder(null);
				a.setPreferredSize(new Dimension(10,10));
				calendar.add(a);
			}
			i++;
		}
		calendar.setPreferredSize(new Dimension(30,100));
		calendar.setBackground(Color.black);
	}
}

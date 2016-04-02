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
	
	public static void main(String[] args) {
		Body frameTabel = new Body();
	}
	
	Body(){
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		DrawMenu();
		DrawCalendar();
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 0;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx=0.5;
		panel.add(menuBar,c);

		c.fill = GridBagConstraints.HORIZONTAL;
		System.out.println(frame.getHeight()-menuBar.getHeight());
		c.ipady = 400;
		c.weightx = 0.0;
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = 1;
		panel.add(calendar,c);
		panel.setBorder(BorderFactory.createEtchedBorder());
		
		frame.setSize(menuBar.getHeight()+calendar.getHeight(), menuBar.getWidth()+calendar.getWidth());
		frame.add(panel);
		frame.setVisible(true);
	}
	
	public void DrawMenu(){
		JMenu Calendar, Account, Info;
		JMenuItem Exit, Logout;
		Calendar = new JMenu("Calendar");
		menuBar.add(Calendar);
		menuBar.setSize(100,frame.getWidth());
	}
	
	public void DrawCalendar(){
		calendar.setSize(500, 500);
		JButton button = new JButton("Test");
		calendar.add(button);
		calendar.setBackground(Color.black);
	}
}

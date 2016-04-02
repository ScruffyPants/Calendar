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
		
		DrawMenu();
		DrawCalendar();
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		panel.add(menuBar,c);
		
		c.anchor=GridBagConstraints.PAGE_END;
		panel.add(calendar,c);
		
		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 500);
		frame.setVisible(true);
	}
	
	public void DrawMenu(){
		JMenu Calendar, Account, Info;
		JMenuItem Exit, Logout;
		Calendar = new JMenu("Calendar");
		menuBar.add(Calendar);
	}
	
	public void DrawCalendar(){
		calendar.setSize(500, 300);
		JButton button = new JButton("Test");
		calendar.add(button);
		calendar.setBackground(Color.black);
	}
}

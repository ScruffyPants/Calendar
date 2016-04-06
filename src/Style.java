import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.Objects;

public class Style implements Serializable{
	private static final long serialVersionUID = 1504199602031999L;
	private Color background = Color.black;
	private Color foreground = Color.white;
	private Color dayBackground = Color.white;
	private Color eventBackground = Color.green;
	
	private JFrame frame = new JFrame();
	private JPanel panel = new JPanel();
	
	public void setStyle(){
		frame = new JFrame();
		panel = new JPanel(new GridLayout());
		panel.setLayout(new GridLayout(0,1));
		JButton setBackground = new JButton("Set Color of Background");
		JButton setForeground = new JButton("Set Color of Foreground");
		JButton setDayBackground = new JButton("Set Color of Day's Background");
		JButton setEventBackground = new JButton("Set Color of Event's Background");
		
		panel.add(setBackground);
		panel.add(setForeground);
		panel.add(setDayBackground);
		panel.add(setEventBackground);
		
		setBackground.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Color color = makeColor();
				setBackground(color);
			}
		});
		
		setForeground.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Color color = makeColor();
				setForeground(color);
			}
		});
		
		setDayBackground.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Color color = makeColor();
				setDayBackground(color);
			}
		});
		
		setEventBackground.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Color color = makeColor();
				setEventBackground(color);
			}
		});
		
		frame.add(panel);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	private Color makeColor(){
		Color color = JColorChooser.showDialog(null, "Choose a color", Color.black);
		return color;
	}
	public Color getBackground() {
		return background;
	}
	public void setBackground(Color background) {
		this.background = background;
	}
	public Color getForeground() {
		return foreground;
	}
	public void setForeground(Color foreground) {
		this.foreground = foreground;
	}
	public Color getDayBackground() {
		return dayBackground;
	}
	public void setDayBackground(Color dayBackground) {
		this.dayBackground = dayBackground;
	}
	public Color getEventBackground() {
		return eventBackground;
	}
	public void setEventBackground(Color eventBackground) {
		this.eventBackground = eventBackground;
	}
	
	
}
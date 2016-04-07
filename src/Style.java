import javax.swing.*;
import java.io.*;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.*;

public class Style implements Serializable{
	private static final long serialVersionUID = 1504199602031999L;
	private Color background = Color.black;
	private Color foreground = Color.white;
	private Color dayBackground = Color.white;
	private Color eventBackground = Color.green;
	
	private transient JFrame frame = new JFrame();
	private transient JPanel panel = new JPanel();
	private transient JButton setBackground = new JButton();
	private transient JButton setForeground = new JButton();
	private transient JButton setDayBackground = new JButton();
	private transient JButton setEventBackground = new JButton();
	
	public void setStyle(User user){
		frame = new JFrame();
		panel = new JPanel(new GridLayout());
		panel.setLayout(new GridLayout(0,1));
		setBackground = new JButton("Set Color of Background");
		setBackground.setBackground(user.getStyle().getBackground());
		setForeground = new JButton("Set Color of Foreground");
		setForeground.setBackground(user.getStyle().getForeground());
		setDayBackground = new JButton("Set Color of Day's Background");
		setDayBackground.setBackground(user.getStyle().getDayBackground());
		setEventBackground = new JButton("Set Color of Event's Background");
		setEventBackground.setBackground(user.getStyle().getEventBackground());
		
		panel.add(setBackground);
		panel.add(setForeground);
		panel.add(setDayBackground);
		panel.add(setEventBackground);
		
		
		
		setBackground.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Color color = makeColor();
				setBackground(color);
				setBackground.setBackground(color);
				if( color.getBlue() < 80 && color.getRed() < 80 && color.getGreen() < 80 ) {
					setBackground.setForeground(Color.WHITE);
				} else {
					setBackground.setForeground(Color.DARK_GRAY);
				}
			}
		});
		
		setForeground.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Color color = makeColor();
				setForeground(color);
				setForeground.setBackground(color);
				if( color.getBlue() < 80 && color.getRed() < 80 && color.getGreen() < 80 ) {
					setForeground.setForeground(Color.WHITE);
				} else {
					setForeground.setForeground(Color.DARK_GRAY);
				}
			}
		});
		
		setDayBackground.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Color color = makeColor();
				setDayBackground(color);
				setDayBackground.setBackground(color);
				if( color.getBlue() < 80 && color.getRed() < 80 && color.getGreen() < 80 ) {
					setDayBackground.setForeground(Color.WHITE);
				} else {
					setDayBackground.setForeground(Color.DARK_GRAY);
				}
			}
		});
		
		setEventBackground.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Color color = makeColor();
				setEventBackground(color);
				setEventBackground.setBackground(color);
				if( color.getBlue() < 80 && color.getRed() < 80 && color.getGreen() < 80 ) {
					setEventBackground.setForeground(Color.WHITE);
				} else {
					setEventBackground.setForeground(Color.DARK_GRAY);
				}
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
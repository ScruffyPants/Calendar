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

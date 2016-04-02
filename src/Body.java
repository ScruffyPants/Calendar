import javax.swing.*;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.*;

public class Body {
	JPanel pane = new JPanel();
	public static void main(String[] args) {
		Body frameTabel = new Body();
	}
	Body(){
		pane = new JPanel();
		pane.setLayout(new GridBagLayout());
		//pane.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pane.setVisible(true);
	}
}

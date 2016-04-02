import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Registration extends JFrame {

public static void main(String[] args) {
	Registration frameTabel = new Registration();
	}

	JLabel text= new JLabel(":)");
	JPanel panel = new JPanel();

	Registration(){
		super("Registration");
		setSize(300,200);
		setLocation(500,280);
		panel.setLayout (null);

		text.setBounds(70,50,150,60);

		panel.add(text);

		getContentPane().add(panel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

}
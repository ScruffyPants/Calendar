import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.*;

public class Clock extends JFrame{
	public static void main(String[] args) {
		Clock frameTabel = new Clock();
	}
	JLabel lblClock = new JLabel("Clock");
	JPanel pan = new JPanel();
	Clock(){
		super("Clock");
		setSize(220,100);
		setLocation(500,280);
		pan.setLayout (null);
		lblClock.setBounds(10,-80,200,200);
		clock();
		pan.add(lblClock);
		getContentPane().add(pan);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void clock(){
		Thread clock = new Thread(){
			public void run(){
				try {
					for(;;){
					Calendar cal = new GregorianCalendar();
					int day = cal.get(Calendar.DAY_OF_MONTH);
					int month = cal.get(Calendar.MONTH);
					int year = cal.get(Calendar.YEAR);
					int sec = cal.get(Calendar.SECOND);
					int min = cal.get(Calendar.MINUTE);
					int h = cal.get(Calendar.HOUR);
					lblClock.setText("Time: "+h+":"+min+":"+sec+"   Date: "+year+"/"+month+"/"+day);
					sleep(1000);
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		clock.start();
	}
}

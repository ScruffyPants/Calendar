import javax.swing.*;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;

public class Body extends JFrame {
	
	JFrame frame = new JFrame("Calendar");
	JFrame pFrame = new JFrame();
	JPanel panel = new JPanel(new GridBagLayout());
	JPanel calendar = new JPanel(new GridLayout());
	JMenuBar menuBar = new JMenuBar();
	JTextField name = new JTextField();
	JTextField year = new JTextField();
	JTextField month = new JTextField();
	JTextField day = new JTextField();
	JButton a = new JButton();
	JLabel label = new JLabel();
	GridBagConstraints c = new GridBagConstraints();
	Time time;
	User user;
	
	Body(Time t, User u){
		time = t;
		user = u;
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		DrawMenu();
		DrawCalendar();

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.ipady = 20;
		c.weightx=1;
		c.weighty=1;
		panel.add(menuBar,c);
		
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.LAST_LINE_START;
		c.ipady = 10;
		c.insets = new Insets(23,0,0,0);
		c.weightx = 1;
		c.weighty = 1;
		c.gridwidth = 2;
		c.gridheight = 2;
		c.gridx = 0;
		c.gridy = 0;
		panel.add(calendar,c);
		
		frame.setLocationRelativeTo(null);
		frame.add(panel);
		frame.setMinimumSize(new Dimension(500,270));
		frame.setVisible(true);
	}
	
	public void DrawMenu(){
		JMenu Calendar, Account, Info;
		JMenuItem Exit, Logout, AddEvent, Reload;
		
		Calendar = new JMenu("Calendar");
		Account = new JMenu("Account");
		Info = new JMenu("Info");
		
		menuBar.add(Calendar);
		menuBar.add(Account);
		menuBar.add(Info);
		
		Exit = new JMenuItem("Exit");
		Logout = new JMenuItem("Logout");
		AddEvent = new JMenuItem("Add Event");
		Reload = new JMenuItem("Reload");
		
		Calendar.add(Exit);
		Calendar.add(Reload);
		Account.add(AddEvent);
		Account.add(Logout);
		
		Exit.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					frame.setVisible(false);
					frame.dispose();
				}
		});
		
		Logout.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				frame.dispose();
				Timetable.main(null);
			}
		});
		
		AddEvent.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				PopoutEventAdd();
			}
		});
		
		Reload.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				frame.dispose();
				Body body = new Body(time, user);
			}
		});
		
		menuBar.setPreferredSize(new Dimension(300,500));
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
				a = new JButton(Integer.toString(i));
				if(user.getEventsByDate(time.getYear(), time.getMonth(), i).size()>0)a.setBackground(Color.green);
				else a.setBackground(Color.white);
				a.setHorizontalAlignment(SwingConstants.LEFT);
				a.setBorder(null);
				a.setPreferredSize(new Dimension(100,100));
				calendar.add(a);
				a.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						System.out.println("you pressed: "+e.getActionCommand());
						LinkedList<Event> events = user.getEventsByDate(time.getYear(), time.getMonth(), Integer.parseInt(e.getActionCommand()));
						PopoutEventShow(events);
					}
				});
			}
			i++;
		}
		calendar.setPreferredSize(new Dimension(700,1000));
		calendar.setBackground(Color.black);
	}
	
	public void PopoutEventShow(LinkedList<Event> events){
		pFrame = new JFrame();
		String string = "";
		for(Event a: events){
			string+=(a.getName());
		}
		JLabel label = new JLabel(string);
		pFrame.setLayout(new GridLayout(0,1));
		pFrame.add(label);
		pFrame.pack();
		pFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pFrame.setLocationRelativeTo(null);
		pFrame.setMinimumSize(new Dimension(200,200));
		pFrame.setVisible(true);
	}
	
	public void PopoutEventAdd(){
		JButton submit = new JButton("Submit");
		
		pFrame.add(new JLabel("name: "));
		pFrame.add(name);
		
		pFrame.add(new JLabel("year: "));
		pFrame.add(year);
		
		pFrame.add(new JLabel("month: "));
		pFrame.add(month);
		
		pFrame.add(new JLabel("day: "));
		pFrame.add(day);
		
		label = new JLabel("All fields must be filled!");
		//label.setForeground(Color.red);
		label.setVisible(false);
		pFrame.add(label);
		pFrame.add(submit);
		
		pFrame.setLayout(new GridLayout(0,1));
		pFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pFrame.pack();
		pFrame.setVisible(true);
		pFrame.setLocationRelativeTo(null);
		pFrame.setSize(pFrame.getHeight()+50,pFrame.getWidth()+50);
		
		submit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(!name.getText().isEmpty() || !year.getText().isEmpty()|| !month.getText().isEmpty() || !day.getText().isEmpty()){
					Event event = new Event(name.getText(), Integer.parseInt(year.getText()), Integer.parseInt(month.getText())-1, Integer.parseInt(day.getText()));
					user.addEvent(event);
					user.saveUser();
					pFrame.setVisible(false);
					pFrame.dispose();
					//Body body = new Body(time, user);
				}
				else{
					JFrame tFrame = new JFrame();
					tFrame.add(new JLabel("All fields must be filled"));
					tFrame.setSize(200, 200);
					tFrame.setLocationRelativeTo(pFrame);
					tFrame.setVisible(true);
					tFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				}
			}
		});
		//pFrame.setResizable(false);
	}
}

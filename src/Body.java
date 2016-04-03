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
	JButton forwards = new JButton(">");
	JButton backwards = new JButton("<");
	JButton a = new JButton();
	GridBagConstraints c = new GridBagConstraints();
	Time time;
	User user;
	
	Body(Time t, User u){
		time = t;
		user = u;
		int preferredWidth = 30;
		Dimension dimension = new Dimension(preferredWidth, 0);
		
		backwards.setBackground(Color.black);
		forwards.setBackground(Color.black);
		
		backwards.setForeground(Color.white);
		forwards.setForeground(Color.white);
		
		backwards.setBorder(null);
		forwards.setBorder(null);
		
		backwards.setPreferredSize(dimension);
		forwards.setPreferredSize(dimension);
		
		backwards.setSize(preferredWidth, 0);
		forwards.setSize(preferredWidth, 0);
		
		backwards.setMaximumSize(dimension);
		forwards.setMaximumSize(dimension);
		
		backwards.setMinimumSize(dimension);
		forwards.setMinimumSize(dimension);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		DrawMenu();
		DrawCalendar();
		DrawPanel();
		
		backwards.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				panel.remove(calendar);
				if(time.getMonth() == 0){
					time.setMonth(11);
					time.setYear(time.getYear()-1);
				}
				else{
					time.setMonth(time.getMonth()-1);
				}
				panel.validate();
				panel.repaint();
				DrawCalendar();
				DrawPanel();
				frame.remove(calendar);
				frame.add(panel);
				frame.validate();
				frame.repaint();
			}
		});
		
		
		frame.setLocationRelativeTo(null);
		frame.add(panel);
		frame.setMinimumSize(new Dimension(500,270));
		frame.setSize(500, 280);
		frame.setVisible(true);
	}
	public void DrawPanel(){
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.ipady=20;
		c.weightx=3;
		c.gridx=0;
		c.gridy=0;
		c.gridwidth = 3;
		panel.add(menuBar,c);
		
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.VERTICAL;
		c.anchor = GridBagConstraints.LINE_START;
		//c.insets = new Insets(0,0,0,preferredWidth);
		c.gridx = 0;
		c.gridy = 1;
		c.weighty = 1;
		c.gridwidth = 1;
		panel.add(backwards, c);
		
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 1;
		c.gridy = 1;
		c.weighty = 2;
		c.weightx = 3;
		c.gridwidth = 1;
		panel.add(calendar,c);
		
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.VERTICAL;
		c.anchor = GridBagConstraints.LINE_END;
		//c.insets = new Insets(0,preferredWidth,0,0);
		c.gridx = 2;
		c.gridy = 1;
		c.weighty = 1;
		c.gridwidth = 1;
		panel.add(forwards, c);
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
		calendar = new JPanel(new GridLayout());
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
				a.setPreferredSize(new Dimension(90,90));
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
		calendar.setPreferredSize(new Dimension(1000,1000));
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
		pFrame.setLocationRelativeTo(null);
		pFrame.setMinimumSize(new Dimension(200,200));
		pFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pFrame.setVisible(true);
	}
	
	public void PopoutEventAdd(){
		JButton submit = new JButton("Submit");
		pFrame = new JFrame();
		
		pFrame.add(new JLabel("name: "));
		pFrame.add(name);
		
		pFrame.add(new JLabel("year: "));
		pFrame.add(year);
		
		pFrame.add(new JLabel("month: "));
		pFrame.add(month);
		
		pFrame.add(new JLabel("day: "));
		pFrame.add(day);
		
		pFrame.add(submit);
		pFrame.setLayout(new GridLayout(0,1));
		pFrame.setLocationRelativeTo(null);
		pFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pFrame.pack();
		pFrame.setVisible(true);
		
		submit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(!name.getText().isEmpty() && !year.getText().isEmpty() && !month.getText().isEmpty() && !day.getText().isEmpty()){
					Event event = new Event(name.getText(), Integer.parseInt(year.getText()), Integer.parseInt(month.getText())-1, Integer.parseInt(day.getText()));
					user.addEvent(event);
					user.saveUser();
					pFrame.setVisible(false);
					pFrame.dispose();
					//Body body = new Body(time, user);
				}
				else{
					JFrame bFrame = new JFrame();
					bFrame.add(new JLabel("All fields must be filled!"));
					bFrame.setVisible(true);
					bFrame.setSize(200, 200);
					bFrame.setResizable(false);
					bFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				}
			}
		});
		//pFrame.setResizable(false);
	}
}

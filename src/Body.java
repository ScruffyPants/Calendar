import javax.swing.*;
import java.io.*;
import java.nio.file.Files;

import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.Objects;

public class Body extends JFrame {
	private static final long serialVersionUID = 1504199602031999L;
	private final String dir = System.getProperty("user.dir");
	private int ClickTracker = -1;
	
	JFrame frame = new JFrame("Calendar");
	JFrame pFrame = new JFrame();
	JPanel panel = new JPanel(new GridBagLayout());
	JPanel calendar = new JPanel();
	JMenuBar menuBar = new JMenuBar();
	JTextField name = new JTextField();
	JTextField year = new JTextField();
	JTextField month = new JTextField();
	JTextField day = new JTextField();
	JTextField description = new JTextField();
	JButton forwards = new JButton(">");
	JButton backwards = new JButton("<");
	JButton a = new JButton();
	JCheckBox pEvent = new JCheckBox();
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
				frame.remove(panel);
				if(time.getMonth() == 0){
					time.setMonth(11);
					time.setYear(time.getYear()-1);
				}
				else{
					time.setMonth(time.getMonth()-1);
				}
				frame.validate();
				frame.repaint();
				panel.remove(calendar);
				DrawCalendar();
				DrawPanel();
				frame.add(panel);
				frame.validate();
				frame.repaint();
			}
		});
		
		forwards.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				frame.remove(panel);
				if(time.getMonth() == 11){
					time.setMonth(0);
					time.setYear(time.getYear()+1);
				}
				else{
					time.setMonth(time.getMonth()+1);
				}
				frame.validate();
				frame.repaint();
				panel.remove(calendar);
				DrawCalendar();
				DrawPanel();
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
		c = new GridBagConstraints();
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
		JMenu Calendar, Account, Info, Admin;
		JMenuItem Exit, Logout, AddEvent, Reload, UserControl, AccountSettings, About;
		
		Calendar = new JMenu("Calendar");
		Account = new JMenu("Account");
		Info = new JMenu("Info");
		
		if(user.getIsAdmin() && user.getIsVerified()) {
			Admin = new JMenu("Admin");
			menuBar.add(Admin);
			UserControl = new JMenuItem("User Control Panel");
			Admin.add(UserControl);
			UserControl.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					PopoutUserControlDialog();
				}
			});
		}
		
		menuBar.add(Calendar);
		menuBar.add(Account);
		menuBar.add(Info);
		
		Exit = new JMenuItem("Exit");
		Logout = new JMenuItem("Logout");
		AddEvent = new JMenuItem("Add Event");
		Reload = new JMenuItem("Reload");
		AccountSettings = new JMenuItem("Account Settings");
		About = new JMenuItem("About");
		
		Info.add(About);
		Calendar.add(Exit);
		Calendar.add(Reload);
		Account.add(AddEvent);
		Account.add(Logout);
		Account.add(AccountSettings);
		
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
		
		AccountSettings.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				pFrame = new JFrame();
				JPanel panel1 = new JPanel();
				panel1.setLayout(new BoxLayout(panel1, BoxLayout.PAGE_AXIS));
				JLabel fName = new JLabel(user.getFname());
				JLabel lName = new JLabel(user.getLname());
				JLabel nick = new JLabel(user.getNick());
				JButton changePass = new JButton("Change Password");
				
				panel1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
				fName.setAlignmentX(Component.CENTER_ALIGNMENT);
				lName.setAlignmentX(Component.CENTER_ALIGNMENT);
				nick.setAlignmentX(Component.CENTER_ALIGNMENT);
				
				panel1.add(new JLabel("First Name:"));
				panel1.add(fName);
				panel1.add(new JLabel("Last Name:"));
				panel1.add(lName);
				panel1.add(new JLabel("Nickname:"));
				panel1.add(nick);
				panel1.add(changePass);
				
				pFrame.add(panel1);
				pFrame.setVisible(true);
				pFrame.setLocationRelativeTo(null);
				pFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				pFrame.setSize(300, 300);
				changePass.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						
					}
					});
			}
		});
		
		menuBar.setPreferredSize(new Dimension(300,500));
	}
	
	public void DrawCalendar(){
		JPanel main = new JPanel(new GridLayout());
		JPanel weekpanel = new JPanel(new GridLayout());
		JLabel label = new JLabel(time.getYear()+" "+time.getMonthName(time.getMonth()));
		calendar = new JPanel();
		boolean done = false;
		int i=1;
		int j=1;
		
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		label.setSize(100, 30);
		label.setForeground(Color.white);
		calendar.setLayout(new BoxLayout(calendar, BoxLayout.PAGE_AXIS));
		calendar.add(label, calendar);
		
		JLabel monday = new JLabel("Monday");
		JLabel tuesday = new JLabel("Tuesday");
		JLabel wednesday = new JLabel("Wednesday");
		JLabel thursday = new JLabel("Thursday");
		JLabel friday = new JLabel("Friday");
		JLabel saturday = new JLabel("Saturday");
		JLabel sunday = new JLabel("Sunday");
		
		weekpanel.setLayout(new GridLayout(0,7,2,2));
		weekpanel.setMaximumSize(new Dimension(9000,30));
		weekpanel.add(monday);
		weekpanel.add(tuesday);
		weekpanel.add(wednesday);
		weekpanel.add(thursday);
		weekpanel.add(friday);
		weekpanel.add(saturday);
		weekpanel.add(sunday);
		calendar.add(weekpanel, calendar);
		
		double dim = (double)time.getDaysInMonth();
		int fdom = time.getFirstDayOfMonth(time);
		main.setLayout(new GridLayout(0,7,2,2));
		while(!done){
			if(j>=fdom){
				if(i>dim){
						done = true;
						break;
				}
				else{
					a = new JButton(Integer.toString(i));
					if(user.getEventsByDate(time.getYear(), time.getMonth(), i).size()>0)a.setBackground(Color.green);
					else a.setBackground(Color.white);
					a.setHorizontalAlignment(SwingConstants.LEFT);
					a.setVerticalAlignment(SwingConstants.TOP);
					a.setBorder(null);
					a.setPreferredSize(new Dimension(90,90));
					main.add(a);
					a.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent e) {
							System.out.println("you pressed: "+e.getActionCommand());
							LinkedList<Event> events = user.getEventsByDate(time.getYear(), time.getMonth(), Integer.parseInt(e.getActionCommand()));
							PopoutEventShow(events);
						}
					});
					if(i==dim && i%7==0){
						done = true;
						break;
					}
				}
				i++;
			}
			else{
				JLabel a = new JLabel("");
				main.add(a);
			}
			j++;
		}
		
		main.setBackground(Color.black);
		calendar.add(main);
		calendar.setPreferredSize(new Dimension(1000,1000));
		calendar.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
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
		
		description = new JTextField();
		description.setSize(100,100);
		pFrame.add(new JLabel("description: "));
		pFrame.add(description);
		
		if(user.getIsTeacher() || user.getIsAdmin()){
			pEvent = new JCheckBox();
			pFrame.add(new JLabel("Public Event: "));
			pFrame.add(pEvent);
		}
		
		pFrame.add(submit);
		pFrame.setLayout(new GridLayout(0,1));
		pFrame.setLocationRelativeTo(null);
		pFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pFrame.pack();
		pFrame.setSize(pFrame.getWidth()+100, pFrame.getHeight()+100);
		pFrame.setVisible(true);
		
		submit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(!name.getText().isEmpty() && !year.getText().isEmpty() && !month.getText().isEmpty() && !day.getText().isEmpty()){
					Event event = new Event(name.getText(), Integer.parseInt(year.getText()), Integer.parseInt(month.getText())-1, Integer.parseInt(day.getText()), description.getText());
					if(!pEvent.isSelected())user.addEvent(event);
					else user.addPEvent(event);
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
					bFrame.setLocationRelativeTo(null);
					bFrame.setResizable(false);
					bFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				}
			}
		});
	}
	public void PopoutUserControlDialog() {
		JFrame pFrame = new JFrame();
		JMenuBar adminMenuBar = new JMenuBar();
		JMenu Options, Window, ChangeRank;
		JMenuItem Ban, Verify, Student, Teacher, Admin, Refresh;
		UserTable utable = new UserTable();
		DefaultTableModel dtm = utable.createUserTable();
		JTable table = new JTable(dtm);
		Container paneC = pFrame.getContentPane();
		JScrollPane sp = new JScrollPane(table);
		JPanel pane = new JPanel();
		
		Options = new JMenu("Options");
		Window = new JMenu("Window");
		Refresh = new JMenuItem("Refresh");
		Ban = new JMenuItem("Ban");
		Verify = new JMenuItem("Toggle Verification");
		Student = new JMenuItem("Student");
		Teacher = new JMenuItem("Teacher");
		Admin = new JMenuItem("Admin");
		ChangeRank = new JMenu("Change Rank");
		
		ChangeRank.add(Student);
		ChangeRank.add(Teacher);
		ChangeRank.add(Admin);
		Options.add(Ban);
		Options.add(Verify);
		Options.add(ChangeRank);
		Window.add(Refresh);
		adminMenuBar.add(Options);
		adminMenuBar.add(Window);
		
		table.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseClicked(java.awt.event.MouseEvent e) {
		        int row = table.rowAtPoint(e.getPoint());
		        int col = table.columnAtPoint(e.getPoint());
		        if (row >= 0 && col >= 0) {
		        	ClickTracker = row;
		        }
		        else
		        	ClickTracker = -1;
		    }
		});
		Ban.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {	
				if( ClickTracker >= 0 & !Objects.equals(dtm.getValueAt(ClickTracker, 0), user.getNick())) {
					File toDelete = new File(dir + "\\src\\Users\\" + dtm.getValueAt(ClickTracker, 0) + ".txt"); 
					if(!toDelete.isDirectory()) {
						toDelete.delete();
						dtm.removeRow(ClickTracker);
					}
				}
			}
		});
		Verify.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if( ClickTracker >= 0 & !Objects.equals(dtm.getValueAt(ClickTracker, 0), user.getNick()) ) {
					String userS = (String) dtm.getValueAt(ClickTracker, 0);
					User user2 = new User();
					user2.loadUser(userS);
					user2.setIsVerified(!user2.getIsVerified());
					table.setValueAt("" + user2.getIsVerified(), ClickTracker, 2);
					user2.saveUser();
				}
			}
		});
		Student.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if( ClickTracker >= 0 & !Objects.equals(dtm.getValueAt(ClickTracker, 0), user.getNick())) {
					String userS = (String) table.getValueAt(ClickTracker, 0);
					User user2 = new User();
					user2.loadUser(userS);
					user2.setIsTeacher(false);
					user2.setIsAdmin(false);
					user2.saveUser();
					table.setValueAt("Student", ClickTracker, 1);
				}
			}
		});
		Teacher.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if( ClickTracker >= 0 & !Objects.equals(dtm.getValueAt(ClickTracker, 0), user.getNick())) {
					String userS = (String) table.getValueAt(ClickTracker, 0);
					User user2 = new User();
					user2.loadUser(userS);
					user2.setIsTeacher(true);
					user2.setIsAdmin(false);
					user2.saveUser();
					table.setValueAt("Teacher", ClickTracker, 1);
				}
			}
		});
		Admin.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if( ClickTracker >= 0 & !Objects.equals(dtm.getValueAt(ClickTracker, 0), user.getNick())) {
					String userS = (String) table.getValueAt(ClickTracker, 0);
					User user2 = new User();
					user2.loadUser(userS);
					user2.setIsTeacher(false);
					user2.setIsAdmin(true);
					user2.saveUser();
					table.setValueAt("Admin", ClickTracker, 1);
				}
			}
		});
		Refresh.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				pFrame.setVisible(false);
				pFrame.dispose();
				PopoutUserControlDialog();
			}
		});
		
		pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));
		adminMenuBar.setAlignmentX(Component.RIGHT_ALIGNMENT);
		adminMenuBar.setMaximumSize(new Dimension(1920,30));
		sp.setAlignmentX(Component.RIGHT_ALIGNMENT);
		pane.add(adminMenuBar);
		pane.add(sp);
		pane.setMinimumSize(new Dimension(300,300));
		paneC.add(pane);
		paneC.setMinimumSize(new Dimension(300,300));
		//pane.setVisible(true);
		
		pFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pFrame.setMinimumSize(new Dimension(300,300));
		pFrame.pack();
		pFrame.setVisible(true);
	}
	}

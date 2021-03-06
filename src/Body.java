import javax.swing.*;
import javax.swing.border.*;

import java.io.*;
//import java.nio.file.Files;

import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.Objects;
import java.util.List;
import java.util.Arrays;
import java.util.Calendar;

public class Body extends JFrame {
	private static final long serialVersionUID = 1504199602031999L;
	private final String dir = System.getProperty("user.dir");
	
	JFrame frame = new JFrame("Calendar");
	JFrame pFrame = new JFrame();
	JFrame gFrame = new JFrame();
	JPanel panel = new JPanel(new GridBagLayout());
	JPanel calendar = new JPanel();
	User user2 = new User();
	JMenuBar menuBar = new JMenuBar();
	JTextField name = new JTextField();
	JTextField year = new JTextField();
	JTextField month = new JTextField();
	JTextField day = new JTextField();
	JTextField enteredYear = new JTextField();
	JTextField enteredMonth = new JTextField();
	JTextField fName2 = new JTextField();
	JTextField lName2 = new JTextField();
	JTextField yStart = new JTextField();
	JTextField mStart = new JTextField();
	JTextField dStart = new JTextField();
	JTextField yEnd = new JTextField();
	JTextField mEnd = new JTextField();
	JTextField dEnd = new JTextField();
	JTextField delay = new JTextField();
	JTextField usertoadd = new JTextField();
	JTextPane namepane = new JTextPane();
	JCheckBox Monday = new JCheckBox();
	JCheckBox Tuesday = new JCheckBox();
	JCheckBox Wednesday = new JCheckBox();
	JCheckBox Thursday = new JCheckBox();
	JCheckBox Friday = new JCheckBox();
	JCheckBox Saturday = new JCheckBox();
	JCheckBox Sunday = new JCheckBox();
	JTextPane description = new JTextPane();
	JPasswordField pass2 = new JPasswordField();
	JButton forwards = new JButton(">");
	JButton backwards = new JButton("<");
	JButton a = new JButton();
	JCheckBox pEvent = new JCheckBox();
	JTable table = new JTable();
	GridBagConstraints c = new GridBagConstraints();
	Table utable = new Table();
	DefaultTableModel dtm = utable.createUserTable();
	String d = null;
	Time time;
	Calendar today = Calendar.getInstance();
	User user;
	Group group = new Group();
	int select = table.getSelectedRow();
	
	//For the lecturer: The reason we globally define so many variables in Body.java is that they are also defined and used within action listeners, which are an enclosing scope.
	//Our JVMs reacted differently towards this, with Vilius' machine spewing out errors, while a friend of his got it working
	//without a problem and without the need to globally define whatever we use within action listeners, even on the same IDE.
	//We didn't know how to handle this, so please do tell us.
	
	Body(Time t, User u){
		time = t;
		today = Calendar.getInstance();
		user = u;
		//System.out.println("Constructor month: "+time.getMonth());
		int preferredWidth = 30;
		Dimension dimension = new Dimension(preferredWidth, 0);
		
		backwards.setBackground(user.getStyle().getBackground());
		forwards.setBackground(user.getStyle().getBackground());
		
		backwards.setForeground(user.getStyle().getForeground());
		forwards.setForeground(user.getStyle().getForeground());
		
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
					int temp = time.getYear();
					time.setYear(temp-1);
				}
				else{
					int temp = time.getMonth()-1;
					time.setMonth(temp);
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
					int temp = time.getYear();
					time.setYear(temp+1);
				}
				else{
					int temp = time.getMonth()+1;
					time.setMonth(temp);
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
		frame.setSize(700, 500);
		frame.setLocation(400,170);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		c.gridx = 2;
		c.gridy = 1;
		c.weighty = 1;
		c.gridwidth = 1;
		panel.add(forwards, c);
	}
	
	public void DrawMenu(){
		JMenu Calendar, Account, Add, Info, Admin, GroupsMenu;
		JMenuItem Exit, Logout, AddEvent, AddSchedule, Reload, UserControl, GroupControl, Settings, About, GetToDate, Style, GroupsAdd, GroupsManage;
		
		Calendar = new JMenu("Calendar");
		Account = new JMenu("Account");
		Add = new JMenu("Add");
		Info = new JMenu("Info");
		GroupsMenu = new JMenu("Groups");
		
		if(user.getIsAdmin() && user.getIsVerified()) {
			Admin = new JMenu("Admin");
			menuBar.add(Admin);
			UserControl = new JMenuItem("User Control Panel");
			GroupControl = new JMenuItem("Group Control Panel");
			Admin.add(UserControl);
			Admin.add(GroupControl);
			UserControl.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					PopoutUserControlDialog();
				}
			});
			GroupControl.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					PopoutGroupControlDialog();
				}
			});
		}
		
		menuBar.add(Calendar);
		menuBar.add(Account);
		menuBar.add(Info);
		
		Style = new JMenuItem("Style");
		Exit = new JMenuItem("Exit");
		Logout = new JMenuItem("Logout");
		AddEvent = new JMenuItem("Event");
		AddSchedule = new JMenuItem("Schedule");
		Reload = new JMenuItem("Reload");
		Settings = new JMenuItem("Settings");
		About = new JMenuItem("About");
		GetToDate = new JMenuItem("Get To Date");
		GroupsAdd = new JMenuItem("Make Group");
		GroupsManage = new JMenuItem("Manage Groups");
		
		Info.add(About);
		Calendar.add(Exit);
		Calendar.add(Reload);
		Calendar.add(GetToDate);
		Add.add(AddEvent);
		Add.add(AddSchedule);
		GroupsMenu.add(GroupsAdd);
		GroupsMenu.add(GroupsManage);
		Account.add(Add);
		Account.add(GroupsMenu);
		Account.add(Style);
		Account.add(Settings);
		Account.add(Logout);
		
		GroupsAdd.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				group = new Group();
				gFrame = new JFrame();
				JPanel gPanel = new JPanel();
				namepane = new JTextPane();
				JButton confirm = new JButton("Confirm");
				
				gPanel.setLayout(new GridLayout(0,1));
				gPanel.add(new JLabel("Name: "));
				gPanel.add(namepane);
				gPanel.add(confirm);

				
				gFrame.setLocationRelativeTo(null);
				gFrame.pack();
				gFrame.add(gPanel);
				gFrame.setMinimumSize(new Dimension(400,200));
				gFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				gFrame.setVisible(true);
				
				confirm.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						if(!namepane.getText().isEmpty()){
							group.setName(namepane.getText());
							group.addAdmin(user);
							group.addUser(user);
							group.saveGroup();
							System.out.println("Group with "+group.getAdmins().size()+" has been saved");
							user.addGroup(group.getName());
							user.saveUser();
							gFrame.setVisible(false);
							gFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						}
						else JOptionPane.showMessageDialog(null,"All fields must be filled");
					}
				});
			}
		});

		GroupsManage.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				gFrame = new JFrame();
				gFrame.setLayout(new GridLayout(1,0));
				for(String s: user.getGroups()){
					group = new Group();
					group.loadGroup(s);
					System.out.println(group.printEvents());
					System.out.println("Name: "+group.getName()+" Users: "+group.getUsers().size());
					JLabel groupName = new JLabel(group.getName());
					JPanel gPanel = new JPanel();
					JPanel mainPanel = new JPanel();
					JScrollPane scrollpane = new JScrollPane(gPanel,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
					JButton addEvent = new JButton("Add Event");
					JButton manageUsers = new JButton("Manage Users");
					
					addEvent.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent e){
							JButton submit = new JButton("Submit");
							pFrame = new JFrame();
							JPanel basicinfo = new JPanel(new GridLayout(0,1));
							
							name = new JTextField(20);
							year = new JTextField(20);
							month = new JTextField(20);
							day = new JTextField(20);
							
							basicinfo.add(new JLabel("Name: "));
							name.setHorizontalAlignment(SwingConstants.LEFT);
							basicinfo.add(name);
							
							basicinfo.add(new JLabel("Year: "));
							name.setHorizontalAlignment(SwingConstants.LEFT);
							basicinfo.add(year);
							
							basicinfo.add(new JLabel("Month: "));
							name.setHorizontalAlignment(SwingConstants.LEFT);
							basicinfo.add(month);
							
							basicinfo.add(new JLabel("Day: "));
							name.setHorizontalAlignment(SwingConstants.LEFT);
							basicinfo.add(day);
							
							description = new JTextPane();
							JScrollPane scrollpane = new JScrollPane(description,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
							scrollpane.setPreferredSize(new Dimension(300,300));
							scrollpane.setMinimumSize(new Dimension(20,20));
							
							pFrame.setLayout(new FlowLayout());
							pFrame.add(basicinfo);
							pFrame.add(new JLabel("Description: "));
							pFrame.add(scrollpane);
							
							pFrame.add(submit);
							pFrame.setLocationRelativeTo(null);
							pFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
							pFrame.setMinimumSize(new Dimension(150,150));
							pFrame.setSize(750,400);
							pFrame.setVisible(true);
							pFrame.setResizable(false);
							
							submit.addActionListener(new ActionListener(){
								public void actionPerformed(ActionEvent e) {
									List<String> MonthsUC = Arrays.asList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
									List<String> monthsLC = Arrays.asList("january", "february", "march", "april", "may", "june", "july", "august", "september", "october", "november", "december");
									if(!name.getText().isEmpty() && !year.getText().isEmpty() && !month.getText().isEmpty() && !day.getText().isEmpty()){
										Event event;
										if( MonthsUC.contains(month.getText()) ) {
											event = new Event(name.getText(), Integer.parseInt(year.getText()), MonthsUC.indexOf(month.getText()), Integer.parseInt(day.getText()), description.getText());
										}
										else if( monthsLC.contains(month.getText()) ) {
											event = new Event(name.getText(), Integer.parseInt(year.getText()), monthsLC.indexOf(month.getText()), Integer.parseInt(day.getText()), description.getText());
										}
										else {
											event = new Event(name.getText(), Integer.parseInt(year.getText()), Integer.parseInt(month.getText())-1, Integer.parseInt(day.getText()), description.getText());
										}
										
										group.addEvent(event);
										group.printEvents();
										
										group.saveGroup();
										pFrame.setVisible(false);
										pFrame.dispose();
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
					});
					
					manageUsers.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent e){
							PopoutGroupUserControlDialog(group);
						}
					});
					mainPanel.setLayout(new GridLayout(0,1));
				
					groupName.setHorizontalAlignment(SwingConstants.CENTER);
					groupName.setBorder(BorderFactory.createRaisedSoftBevelBorder());
					mainPanel.setBorder(new CompoundBorder(
							BorderFactory.createEmptyBorder(5, 5, 5, 5),
							BorderFactory.createEtchedBorder()));
					mainPanel.add(groupName);
					gPanel.setLayout(new GridLayout(0,1));
					JLabel users = new JLabel("Users: ");
					users.setVerticalAlignment(SwingConstants.BOTTOM);
					users.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
					mainPanel.add(users);
					
					for(User u: group.getUsers()){
						System.out.println(group.getUsers().size()+" Users found : "+u.getNick());
						JLabel nickName = new JLabel(u.getNick());
						nickName.setHorizontalAlignment(SwingConstants.LEFT);
						nickName.setVerticalAlignment(SwingConstants.TOP);
						gPanel.add(nickName);
					}
					
					mainPanel.add(scrollpane);
					scrollpane.setPreferredSize(new Dimension(200,200));
					
					scrollpane = new JScrollPane();
					gPanel = new JPanel();
					gPanel.setLayout(new GridLayout(0,1));
					JLabel events = new JLabel("Events: ");
					events.setVerticalAlignment(SwingConstants.BOTTOM);
					events.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
					mainPanel.add(events);
					scrollpane = new JScrollPane(gPanel,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
					
					for(Event event: group.getEvents()){
						System.out.println(group.getEvents().size()+" Events found: "+event.getName());
						gPanel.add(new JLabel("Event " + event.getYear() + "/" + event.getMonth() + "/" + event.getDay() + " : " + event.getName() + " (" + event.getDescription() + ")"));
					}
					
					scrollpane.setPreferredSize(new Dimension(200,200));
					mainPanel.add(scrollpane);
					
					gPanel = new JPanel();
					gPanel.setLayout(new GridLayout(0,1));
					JLabel admins = new JLabel("Admins: ");
					events.setVerticalAlignment(SwingConstants.BOTTOM);
					events.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
					mainPanel.add(admins);
					scrollpane = new JScrollPane(gPanel,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
					
					System.out.println(group.getAdmins().size()+" Admins found");
					for(User a: group.getAdmins()){
						System.out.println(group.getEvents().size()+" Admins found: "+a.getNick());
						JLabel nickName = new JLabel(a.getNick());
						nickName.setHorizontalAlignment(SwingConstants.LEFT);
						nickName.setVerticalAlignment(SwingConstants.TOP);
						gPanel.add(nickName);
					}
					
					scrollpane.setPreferredSize(new Dimension(200,200));
					mainPanel.add(scrollpane);
					
					mainPanel.add(addEvent);
					if(group.isAdmin(user))mainPanel.add(manageUsers);
					gFrame.add(mainPanel);
				}
				gFrame.setMinimumSize(new Dimension(300,300));
				gFrame.setVisible(true);
				gFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);	
				user.loadUser(user.getNick());
			}
		});
		
		Style.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				user.getStyle().setStyle(user);
				user.saveUser();
			}
		});
		
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
		
		AddSchedule.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				PopoutScheduleAdd();
			}
		});
		
		Reload.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				frame.dispose();
				user.saveUser();
				Body body = new Body(time, user);
			}
		});
		
		GetToDate.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				pFrame = new JFrame();
				enteredYear = new JTextField(4);
				enteredMonth = new JTextField(2);
				JButton confirm = new JButton("GO");
				
				pFrame.setLayout(new FlowLayout());
				pFrame.add(new JLabel("Year: "));
				pFrame.add(enteredYear);
				pFrame.add(new JLabel("Month: "));
				pFrame.add(enteredMonth);
				pFrame.add(confirm);
				pFrame.pack();
				pFrame.setVisible(true);
				pFrame.setLocationRelativeTo(null);
				pFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				
				confirm.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						if(!enteredYear.getText().isEmpty() && !enteredMonth.getText().isEmpty())
						time.setYear(Integer.parseInt(enteredYear.getText()));
						int temp = Integer.parseInt(enteredMonth.getText())-1;;;
						time.setMonth(temp);
						frame.remove(panel);
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
			}
		});

		Settings.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				JFrame pFrame = new JFrame();
				JPanel panel1 = new JPanel();
				panel1.setLayout(new BoxLayout(panel1, BoxLayout.PAGE_AXIS));
				JLabel fName1 = new JLabel("First Name:");
				fName2 = new JTextField(user.getFname());
				JButton editFName = new JButton("Commit");
				JLabel lName1 = new JLabel("Last Name:");
				lName2 = new JTextField(user.getLname());
				JButton editLName = new JButton("Commit");
				JLabel pass1 = new JLabel("Password:");
				pass2 = new JPasswordField();
				JButton changePass = new JButton("Commit");
				
				panel1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
				fName1.setAlignmentX(Component.CENTER_ALIGNMENT);
				editFName.setAlignmentX(Component.CENTER_ALIGNMENT);
				lName1.setAlignmentX(Component.CENTER_ALIGNMENT);
				editLName.setAlignmentX(Component.CENTER_ALIGNMENT);
				fName2.setAlignmentX(Component.CENTER_ALIGNMENT);
				lName2.setAlignmentX(Component.CENTER_ALIGNMENT);
				pass1.setAlignmentX(Component.CENTER_ALIGNMENT);
				pass2.setAlignmentX(Component.CENTER_ALIGNMENT);
				changePass.setAlignmentX(Component.CENTER_ALIGNMENT);
				
				panel1.add(fName1);
				panel1.add((Box.createRigidArea(new Dimension(0, 5))));
				panel1.add(fName2);
				panel1.add((Box.createRigidArea(new Dimension(0, 5))));
				panel1.add(editFName);
				panel1.add((Box.createRigidArea(new Dimension(0, 5))));
				panel1.add(lName1);
				panel1.add((Box.createRigidArea(new Dimension(0, 5))));
				panel1.add(lName2);
				panel1.add((Box.createRigidArea(new Dimension(0, 5))));
				panel1.add(editLName);
				panel1.add((Box.createRigidArea(new Dimension(0, 5))));
				panel1.add(pass1);
				panel1.add((Box.createRigidArea(new Dimension(0, 5))));
				panel1.add(pass2);
				panel1.add((Box.createRigidArea(new Dimension(0, 5))));
				panel1.add(changePass);
				panel1.add((Box.createRigidArea(new Dimension(0, 5))));
				
				pFrame.add(panel1);
				pFrame.setVisible(true);
				pFrame.setLocationRelativeTo(null);
				pFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				pFrame.setSize(225, 300);
				editFName.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						user.setFname(fName2.getText());
						user.saveUser();
					}
					});
				editLName.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						user.setLname(lName2.getText());
						user.saveUser();
					}
					});
				changePass.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						user.setPW_Hash(user.hashPassword(pass2.getText()));
						user.saveUser();
					}
					});
			}
		});
		
		About.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,"You are using Calendar Version JOption.noJpane.noJOptionPane.How do you do it? I forgot. showMessageDialog(). Okay, go and.");
			}
		});
		
		menuBar.setPreferredSize(new Dimension(300,500));
	}
	
	public void DrawCalendar(){
		//System.out.println("Draw calendar month: "+time.getMonth());
		JPanel main = new JPanel(new GridLayout());
		JPanel weekpanel = new JPanel(new GridLayout());
		JLabel label = new JLabel(time.getYear()+" "+time.getMonthName(time.getMonth()));
		calendar = new JPanel();
		boolean done = false;
		int i=1;
		int j=1;
		
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		label.setSize(100, 30);
		label.setForeground(user.getStyle().getForeground());
		calendar.setLayout(new BoxLayout(calendar, BoxLayout.PAGE_AXIS));
		calendar.add(label, calendar);
		
		JLabel monday = new JLabel("Monday");
		monday.setForeground(user.getStyle().getForeground());
		JLabel tuesday = new JLabel("Tuesday");
		tuesday.setForeground(user.getStyle().getForeground());
		JLabel wednesday = new JLabel("Wednesday");
		wednesday.setForeground(user.getStyle().getForeground());
		JLabel thursday = new JLabel("Thursday");
		thursday.setForeground(user.getStyle().getForeground());
		JLabel friday = new JLabel("Friday");
		friday.setForeground(user.getStyle().getForeground());
		JLabel saturday = new JLabel("Saturday");
		saturday.setForeground(user.getStyle().getForeground());
		JLabel sunday = new JLabel("Sunday");
		sunday.setForeground(user.getStyle().getForeground());
		
		weekpanel.setLayout(new GridLayout(0,7,2,2));
		weekpanel.setMaximumSize(new Dimension(9000,30));
		weekpanel.add(monday);
		weekpanel.add(tuesday);
		weekpanel.add(wednesday);
		weekpanel.add(thursday);
		weekpanel.add(friday);
		weekpanel.add(saturday);
		weekpanel.add(sunday);
		weekpanel.setBackground(user.getStyle().getWeekPanelBackground());
		calendar.add(weekpanel, calendar);
		
		double dim = (double)time.getDaysInMonth();
		int fdom = time.getFirstDayOfMonth(time);
		boolean now = (today.get(Calendar.YEAR) == time.getYear() && today.get(Calendar.MONTH) == time.getMonth());
		System.out.println(now);
		main.setLayout(new GridLayout(0,7,2,2));
		while(!done){
			if(j>=fdom){
				if(i>dim){
						done = true;
						break;
				}
				else{
					a = new JButton(Integer.toString(i));
					JScrollPane spane = new JScrollPane(a,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
					a.setLayout(new GridLayout(0,1));
					spane.setBorder(BorderFactory.createEmptyBorder());
					spane.getVerticalScrollBar().setPreferredSize(new Dimension(10,0));

					a.setBackground(user.getStyle().getDayBackground());
					if(user.getEventsByDate(time.getYear(), time.getMonth(), i).size()>0){
						a.setBackground(user.getStyle().getEventBackground());
						for(Event e: user.getEventsByDate(time.getYear(), time.getMonth(), i)){
							JLabel eventlabel = new JLabel(e.getName());
							eventlabel.setHorizontalAlignment(SwingConstants.CENTER);
							a.add(eventlabel);
						}
					}
					else a.setBackground(user.getStyle().getDayBackground());
					a.setHorizontalAlignment(SwingConstants.LEFT);
					a.setVerticalAlignment(SwingConstants.TOP);
					a.setBorder(null);
					if(user.getPEventsByDate(time.getYear(), time.getMonth(), i).size()>0){
						a.setBackground(user.getStyle().getEventBackground());
						for(Event e: user.getPEventsByDate(time.getYear(), time.getMonth(), i)){
							JLabel eventlabel = new JLabel(e.getName());
							eventlabel.setHorizontalAlignment(SwingConstants.CENTER);
							a.add(eventlabel);
						}
					}
					if(user.getSchedulesByDate(time.getYear(), time.getMonth()+1, i).size()>0){
						a.setBackground(user.getStyle().getEventBackground());
						for(Schedule e: user.getSchedulesByDate(time.getYear(), time.getMonth()+1, i)){
							JLabel eventlabel = new JLabel(e.getName());
							eventlabel.setHorizontalAlignment(SwingConstants.CENTER);
							a.add(eventlabel);
						}
					}
					if(user.getGEventsByDate(time.getYear(), time.getMonth()+1, i).size()>0){
						a.setBackground(user.getStyle().getEventBackground());
						for(Event e: user.getGEventsByDate(time.getYear(), time.getMonth()+1, i)){
							JLabel eventlabel = new JLabel(e.getName());
							eventlabel.setHorizontalAlignment(SwingConstants.CENTER);
							a.add(eventlabel);
						}
					}
					
					if( now && i == Calendar.getInstance().get(Calendar.DAY_OF_MONTH) ) {
						//System.out.println("Checking successful");
							//JLabel today = new JLabel("Today");
							//a.add(today);
							Border border = BorderFactory.createMatteBorder(2, 2, 2, 2, user.getStyle().getTodayBorder());
							a.setBorder(border);
					}
					
					a.setHorizontalAlignment(SwingConstants.LEFT);
					a.setVerticalAlignment(SwingConstants.TOP);
					//a.setBorder(null);
					spane.setPreferredSize(new Dimension(90,90));
					//a.setPreferredSize(new Dimension(90,90));
					main.add(spane);
					a.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent e) {
							//System.out.println("you pressed: "+e.getActionCommand());
							//LinkedList<Event> events = user.getEventsByDate(time.getYear(), time.getMonth(), Integer.parseInt(e.getActionCommand()));
							PopoutEventShow(Integer.parseInt(e.getActionCommand()));
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
		
		main.setBackground(user.getStyle().getBackground());
		calendar.add(main);
		calendar.setPreferredSize(new Dimension(1000,1000));
		calendar.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		calendar.setBackground(user.getStyle().getBackground());
	}
	
	public void PopoutEventShow(int d){
		pFrame = new JFrame();
		JButton create = new JButton("Create event");
		
		LinkedList<Event> events = user.getEventsByDate(time.getYear(), time.getMonth(), d);
		LinkedList<Event> pevents = user.getPEventsByDate(time.getYear(), time.getMonth(), d);
		
		for(Event a: events){
			JPanel panel = new JPanel();
			panel.setLayout(new GridBagLayout());
			
			JLabel info = new JLabel(a.getName()+" ("+Integer.toString(a.getYear())+" "+Integer.toString(a.getMonth())+" "+Integer.toString(a.getDay())+")");
			//JLabel desc = new JLabel("Description: \n"+a.getDescription());
			JTextArea desc = new JTextArea("Description: \n"+a.getDescription());
			
			desc.setEditable(false);
			desc.setFocusable(false);
			desc.setWrapStyleWord(true);
			desc.setLineWrap(true);
			desc.setFont(new Font("Serif", Font.BOLD, 15));
			
			info.setSize(100000, 50);
			info.setHorizontalAlignment(SwingConstants.LEFT);
			info.setVerticalAlignment(SwingConstants.BOTTOM);
			info.setForeground(user.getStyle().getForeground());
			desc.setForeground(user.getStyle().getForeground());
			desc.setBackground(user.getStyle().getBackground());
			
			c = new GridBagConstraints();
			c.anchor = GridBagConstraints.FIRST_LINE_START;
			c.gridx = 0;
			c.gridy = 0;
			panel.add(info,c);
			
			c = new GridBagConstraints();
			c.anchor = GridBagConstraints.LINE_START;
			c.gridx = 0;
			c.gridy = 1;
			JScrollPane scrollPane = new JScrollPane(desc);
			scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
			scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			scrollPane.setPreferredSize(new Dimension(300,300));
			panel.add(scrollPane,c);
			panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 10, 5));
			panel.setBorder(BorderFactory.createEtchedBorder());
			panel.setBackground(user.getStyle().getBackground());
			panel.setForeground(user.getStyle().getForeground());
			
			pFrame.add(panel);
		}
		
		for(Event a: pevents){
			JPanel panel = new JPanel();
			panel.setLayout(new GridBagLayout());
			
			JLabel info = new JLabel(a.getName()+" (Public) ("+Integer.toString(a.getYear())+" "+Integer.toString(a.getMonth())+" "+Integer.toString(a.getDay())+")");
			JTextArea desc = new JTextArea("Description: \n"+a.getDescription());
			
			desc.setEditable(false);
			desc.setFocusable(false);
			desc.setWrapStyleWord(true);
			desc.setLineWrap(true);
			desc.setFont(new Font("Serif", Font.BOLD, 15));
			
			info.setSize(100000, 50);
			info.setHorizontalAlignment(SwingConstants.LEFT);
			info.setVerticalAlignment(SwingConstants.BOTTOM);
			info.setForeground(user.getStyle().getForeground());
			desc.setForeground(user.getStyle().getForeground());
			desc.setBackground(user.getStyle().getBackground());
			
			c = new GridBagConstraints();
			c.anchor = GridBagConstraints.FIRST_LINE_START;
			c.gridx = 0;
			c.gridy = 0;
			panel.add(info,c);
			
			c = new GridBagConstraints();
			c.anchor = GridBagConstraints.LINE_START;
			c.gridx = 0;
			c.gridy = 1;
			JScrollPane scrollPane = new JScrollPane(desc);
			scrollPane.setPreferredSize(new Dimension(300,300));
			panel.add(scrollPane,c);
			panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 10, 5));
			panel.setBorder(BorderFactory.createEtchedBorder());
			panel.setBackground(user.getStyle().getBackground());
			panel.setForeground(user.getStyle().getForeground());
			
			pFrame.add(panel);
		}
		if(pevents.size()==0 && events.size()==0){
			JPanel panel = new JPanel();
			JLabel noEvents = new JLabel("No events for this day");
			noEvents.setForeground(user.getStyle().getForeground());
			noEvents.setHorizontalAlignment(SwingConstants.CENTER);
			noEvents.setVerticalAlignment(SwingConstants.CENTER);
			panel.add(noEvents);
			panel.setBackground(user.getStyle().getBackground());
			panel.setMinimumSize(new Dimension(300,300));
			panel.setLayout(new GridBagLayout());
			pFrame.add(panel);
		}
		
		pFrame.setBackground(user.getStyle().getBackground());
		if((pevents.size()==0 && events.size()==0) || (events.size()==1 || events.size()==1))pFrame.setLayout(new GridLayout(0,1));
		//else if(pevents.size()+events.size() > 4)pFrame.setLayout(new GridLayout(4,4));
		else pFrame.setLayout(new GridLayout(2,2));
		pFrame.setMinimumSize(new Dimension(300,300));
		pFrame.setResizable(false);
		pFrame.pack();
		pFrame.setLocationRelativeTo(null);
		pFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pFrame.setVisible(true);
	}
	
	public void PopoutEventAdd(){
		JButton submit = new JButton("Submit");
		pFrame = new JFrame();
		JPanel basicinfo = new JPanel(new GridLayout(0,1));
		
		name = new JTextField(20);
		year = new JTextField(20);
		month = new JTextField(20);
		day = new JTextField(20);
		
		basicinfo.add(new JLabel("Name: "));
		name.setHorizontalAlignment(SwingConstants.LEFT);
		basicinfo.add(name);
		
		basicinfo.add(new JLabel("Year: "));
		name.setHorizontalAlignment(SwingConstants.LEFT);
		basicinfo.add(year);
		
		basicinfo.add(new JLabel("Month: "));
		name.setHorizontalAlignment(SwingConstants.LEFT);
		basicinfo.add(month);
		
		basicinfo.add(new JLabel("Day: "));
		name.setHorizontalAlignment(SwingConstants.LEFT);
		basicinfo.add(day);
		
		description = new JTextPane();
		JScrollPane scrollpane = new JScrollPane(description,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollpane.setPreferredSize(new Dimension(300,300));
		scrollpane.setMinimumSize(new Dimension(20,20));
		
		pFrame.setLayout(new FlowLayout());
		pFrame.add(basicinfo);
		pFrame.add(new JLabel("Description: "));
		pFrame.add(scrollpane);
		
		if((user.getIsTeacher() && user.getIsVerified()) || (user.getIsAdmin() && user.getIsVerified() )){
			pEvent = new JCheckBox();
			pFrame.add(new JLabel("Public Event: "));
			pFrame.add(pEvent);
		}
		
		pFrame.add(submit);
		pFrame.setLocationRelativeTo(null);
		pFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pFrame.setMinimumSize(new Dimension(150,150));
		pFrame.setSize(750,400);
		pFrame.setVisible(true);
		pFrame.setResizable(false);
		
		submit.addActionListener(new ActionListener(){
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
				List<String> MonthsUC = Arrays.asList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
				List<String> monthsLC = Arrays.asList("january", "february", "march", "april", "may", "june", "july", "august", "september", "october", "november", "december");
				if(!name.getText().isEmpty() && !year.getText().isEmpty() && !month.getText().isEmpty() && !day.getText().isEmpty()){
					Event event;
					if( MonthsUC.contains(month.getText()) ) {
						event = new Event(name.getText(), Integer.parseInt(year.getText()), MonthsUC.indexOf(month.getText()), Integer.parseInt(day.getText()), description.getText());
					}
					else if( monthsLC.contains(month.getText()) ) {
						event = new Event(name.getText(), Integer.parseInt(year.getText()), monthsLC.indexOf(month.getText()), Integer.parseInt(day.getText()), description.getText());
					}
					else {
						event = new Event(name.getText(), Integer.parseInt(year.getText()), Integer.parseInt(month.getText())-1, Integer.parseInt(day.getText()), description.getText());
					}
					boolean isLegit = false;
					if( event.getYear() == today.get(Calendar.YEAR) ) {
						if( event.getMonth() == today.get(Calendar.MONTH) ) {
							if( event.getDay() >= today.get(Calendar.DATE) ) {
								isLegit = true;
							} else {
								isLegit = false;
							}
						} else if( event.getMonth() > today.get(Calendar.MONTH) ) {
							isLegit = true;
						} else {
							isLegit = false;
						}
					} else if( event.getYear() > today.get(Calendar.YEAR) ) {
						isLegit = true;
					} else {
						isLegit = false;
					}
					
					if( isLegit ) {
					if(!pEvent.isSelected())user.addEvent(event);
					else {
						try {
							FileInputStream fInTemp = new FileInputStream(dir + "/src/pEvents/pEvents.txt");
							ObjectInputStream inObject = new ObjectInputStream(fInTemp);
							LinkedList<Event> pEvents = new LinkedList<Event>();
							pEvents = (LinkedList<Event>) inObject.readObject();
							//System.out.println("pEvents List received from OBJIN");
							inObject.close();
							fInTemp.close();
							pEvents.add(event);
							user.setPEvents(pEvents);
							FileOutputStream fOutTemp = new FileOutputStream(dir + "/src/pEvents/pEvents.txt");
							ObjectOutputStream outObject = new ObjectOutputStream(fOutTemp);
							outObject.writeObject(pEvents);
							outObject.close();
							fOutTemp.close();
						} catch(FileNotFoundException ee) {
							System.out.println("404 ERROR: pEvents.txt or target directory not found");
						} catch(ClassNotFoundException a) {
							System.out.println("Corrupted pEvents.txt");
						} catch(IOException oo) {
							System.out.println("IOException");
						}
					}
					user.saveUser();
					pFrame.setVisible(false);
					pFrame.dispose();
					frame.setVisible(false);
					frame.dispose();
					user.saveUser();
					Body body = new Body(time, user);
					//Body body = new Body(time, user);
				} else {
					JOptionPane.showMessageDialog(null,"Event must be in the future");
				}
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
	
	public void PopoutScheduleAdd(){
		JButton submit = new JButton("Submit");
		pFrame = new JFrame();
		JPanel basicinfo = new JPanel(new GridLayout(0,1));
		
		name = new JTextField(20);
		yStart = new JTextField(20);
		mStart = new JTextField(20);
		dStart = new JTextField(20);
		yEnd = new JTextField(20);
		mEnd = new JTextField(20);
		dEnd = new JTextField(20);
		delay = new JTextField(2);
		
		basicinfo.add(new JLabel("Name: "));
		name.setHorizontalAlignment(SwingConstants.LEFT);
		basicinfo.add(name);
		
		basicinfo.add(new JLabel("Start Year: "));
		yStart.setHorizontalAlignment(SwingConstants.LEFT);
		basicinfo.add(yStart);
		
		basicinfo.add(new JLabel("Start Month: "));
		mStart.setHorizontalAlignment(SwingConstants.LEFT);
		basicinfo.add(mStart);
		
		basicinfo.add(new JLabel("Start Day: "));
		dStart.setHorizontalAlignment(SwingConstants.LEFT);
		basicinfo.add(dStart);
		
		basicinfo.add(new JLabel("End Year: "));
		yEnd.setHorizontalAlignment(SwingConstants.LEFT);
		basicinfo.add(yEnd);
		
		basicinfo.add(new JLabel("End Month: "));
		mEnd.setHorizontalAlignment(SwingConstants.LEFT);
		basicinfo.add(mEnd);
		
		basicinfo.add(new JLabel("End Day: "));
		dEnd.setHorizontalAlignment(SwingConstants.LEFT);
		basicinfo.add(dEnd);
		
		basicinfo.add(new JLabel("Delay between weeks of occurance (in weeks):"));
		dEnd.setHorizontalAlignment(SwingConstants.LEFT);
		basicinfo.add(delay);
		
		description = new JTextPane();
		JScrollPane scrollpane = new JScrollPane(description,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollpane.setPreferredSize(new Dimension(300,300));
		scrollpane.setMinimumSize(new Dimension(20,20));
		
		pFrame.setLayout(new FlowLayout());
		pFrame.add(basicinfo);
		pFrame.add(new JLabel("Description: "));
		pFrame.add(scrollpane);
		
		Monday = new JCheckBox();
		Tuesday = new JCheckBox();
		Wednesday = new JCheckBox();
		Thursday = new JCheckBox();
		Friday = new JCheckBox();
		Saturday = new JCheckBox();
		Sunday = new JCheckBox();
			
		pFrame.add(new JLabel("Monday: "));
		pFrame.add(Monday);
		pFrame.add(new JLabel("Tuesday: "));
		pFrame.add(Tuesday);
		pFrame.add(new JLabel("Wednesday: "));
		pFrame.add(Wednesday);
		pFrame.add(new JLabel("Thursday: "));
		pFrame.add(Thursday);
		pFrame.add(new JLabel("Friday: "));
		pFrame.add(Friday);
		pFrame.add(new JLabel("Saturday: "));
		pFrame.add(Saturday);
		pFrame.add(new JLabel("Sunday: "));
		pFrame.add(Sunday);
		
		pFrame.add(submit);
		pFrame.setLocationRelativeTo(null);
		pFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pFrame.setMinimumSize(new Dimension(150,150));
		pFrame.setSize(700,400);
		pFrame.setVisible(true);
		pFrame.setResizable(false);
		
		submit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int[] dates = {Integer.parseInt(yStart.getText(),10), Integer.parseInt(mStart.getText(),10),  Integer.parseInt(dStart.getText(),10), Integer.parseInt(yEnd.getText(),10), Integer.parseInt(mEnd.getText(),10), Integer.parseInt(dEnd.getText(),10)};
				boolean[] days = {Monday.isSelected(), Tuesday.isSelected(), Wednesday.isSelected(), Thursday.isSelected(), Friday.isSelected(), Saturday.isSelected(), Sunday.isSelected()};
				Schedule toAdd = new Schedule(name.getText(), description.getText(), dates, days, Integer.parseInt(delay.getText(),10) );                                              
				user.addSchedule(toAdd);
				user.printSchedules();
				user.saveUser();
				pFrame.setVisible(false);
				pFrame.dispose();
				frame.setVisible(false);
				frame.dispose();
				Body body = new Body(time, user);
			}
		});
	}
	
	public void PopoutGroupControlDialog() {
		pFrame = new JFrame();
		JMenuBar adminMenuBar = new JMenuBar();

		JMenu Options, Window, Edit;
		JMenuItem Ban, Refresh, Group;
		utable = new Table();
		dtm = utable.createGroupTable();
		table = new JTable(dtm);
		
		Container paneC = pFrame.getContentPane();
		JScrollPane sp = new JScrollPane(table);
		JPanel pane = new JPanel();
		
		Options = new JMenu("Options");
		Ban = new JMenuItem("Ban");
		
		Window = new JMenu("Window");
		Refresh = new JMenuItem("Refresh");
		
		Edit = new JMenu("Edit");
		Group = new JMenuItem("Group");
		
		Options.add(Ban);
		Window.add(Refresh);
		Edit.add(Group);
		adminMenuBar.add(Options);
		adminMenuBar.add(Window);
		adminMenuBar.add(Edit);
		
		Ban.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {	
				if( table.getSelectedRow() >= 0 ) {
					File toDelete = new File(dir + "/src/Groups/" + dtm.getValueAt(table.getSelectedRow(), 0) + ".txt"); 
					if(!toDelete.isDirectory()) {
						toDelete.delete();
						dtm.removeRow(table.getSelectedRow());
					}
				}
			}
		});
		Refresh.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				pFrame.setVisible(false);
				pFrame.dispose();
				PopoutGroupControlDialog();
			}
		});
		Group.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if( table.getSelectedRow() >= 0 ) {
					
				}
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
		
		pFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pFrame.setMinimumSize(new Dimension(300,300));
		pFrame.pack();
		pFrame.setVisible(true);
	}
	
	public void PopoutUserControlDialog() {
		pFrame = new JFrame();
		JMenuBar adminMenuBar = new JMenuBar();

		JMenu Options, Window, ChangeRank, Edit;
		JMenuItem Ban, Verify, Student, Teacher, Admin, Refresh, Account;
		utable = new Table();
		dtm = utable.createUserTable();
		table = new JTable(dtm);
		
		Container paneC = pFrame.getContentPane();
		JScrollPane sp = new JScrollPane(table);
		JPanel pane = new JPanel();
		
		Options = new JMenu("Options");
		Ban = new JMenuItem("Ban");
		Verify = new JMenuItem("Toggle Verification");
		
		ChangeRank = new JMenu("Change Rank");
		Student = new JMenuItem("Student");
		Teacher = new JMenuItem("Teacher");
		Admin = new JMenuItem("Admin");
		
		Window = new JMenu("Window");
		Refresh = new JMenuItem("Refresh");
		
		Edit = new JMenu("Edit");
		Account = new JMenuItem("Account");
		
		ChangeRank.add(Student);
		ChangeRank.add(Teacher);
		ChangeRank.add(Admin);
		Options.add(Ban);
		Options.add(Verify);
		Options.add(ChangeRank);
		Window.add(Refresh);
		Edit.add(Account);
		adminMenuBar.add(Options);
		adminMenuBar.add(Window);
		adminMenuBar.add(Edit);
		
		Ban.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {	
				if( table.getSelectedRow() >= 0 & !Objects.equals(dtm.getValueAt(table.getSelectedRow(), 0), user.getNick())) {
					File toDelete = new File(dir + "/src/Users/" + dtm.getValueAt(table.getSelectedRow(), 0) + ".txt"); 
					if(!toDelete.isDirectory()) {
						toDelete.delete();
						dtm.removeRow(table.getSelectedRow());
					}
				}
			}
		});
		Verify.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if( table.getSelectedRow() >= 0 & !Objects.equals(dtm.getValueAt(table.getSelectedRow(), 0), user.getNick()) ) {
					String userS = (String) dtm.getValueAt(table.getSelectedRow(), 0);
					User user2 = new User();
					user2.loadUser(userS);
					user2.setIsVerified(!user2.getIsVerified());
					table.setValueAt("" + user2.getIsVerified(), table.getSelectedRow(), 3);
					user2.saveUser();
				}
			}
		});
		Student.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if( table.getSelectedRow() >= 0 & !Objects.equals(dtm.getValueAt(table.getSelectedRow(), 0), user.getNick())) {
					String userS = (String) table.getValueAt(table.getSelectedRow(), 0);
					User user2 = new User();
					user2.loadUser(userS);
					user2.setIsTeacher(false);
					user2.setIsAdmin(false);
					user2.saveUser();
					table.setValueAt("Student", table.getSelectedRow(), 2);
				}
			}
		});
		Teacher.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if( table.getSelectedRow() >= 0 & !Objects.equals(dtm.getValueAt(table.getSelectedRow(), 0), user.getNick())) {
					String userS = (String) table.getValueAt(table.getSelectedRow(), 0);
					User user2 = new User();
					user2.loadUser(userS);
					user2.setIsTeacher(true);
					user2.setIsAdmin(false);
					user2.saveUser();
					table.setValueAt("Teacher", table.getSelectedRow(), 2);
				}
			}
		});
		Admin.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if( table.getSelectedRow() >= 0 & !Objects.equals(dtm.getValueAt(table.getSelectedRow(), 0), user.getNick())) {
					String userS = (String) table.getValueAt(table.getSelectedRow(), 0);
					User user2 = new User();
					user2.loadUser(userS);
					user2.setIsTeacher(false);
					user2.setIsAdmin(true);
					user2.saveUser();
					table.setValueAt("Admin", table.getSelectedRow(), 2);
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
		Account.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				//Edited duplicate of SettingsActionListener code
				if( table.getSelectedRow() >= 0 ) {
				select = table.getSelectedRow();
				String userS = (String) table.getValueAt(table.getSelectedRow(), 0);
				user2 = new User();
				user2.loadUser(userS);
				JFrame pFrame = new JFrame();
				JPanel panel1 = new JPanel();
				panel1.setLayout(new BoxLayout(panel1, BoxLayout.PAGE_AXIS));
				JLabel fName1 = new JLabel("First Name:");
				fName2 = new JTextField(user2.getFname());
				JButton editFName = new JButton("Commit");
				JLabel lName1 = new JLabel("Last Name:");
				lName2 = new JTextField(user2.getLname());
				JButton editLName = new JButton("Commit");
				JLabel pass1 = new JLabel("Password:");
				pass2 = new JPasswordField();
				JButton changePass = new JButton("Commit");
				
				panel1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
				fName1.setAlignmentX(Component.CENTER_ALIGNMENT);
				editFName.setAlignmentX(Component.CENTER_ALIGNMENT);
				lName1.setAlignmentX(Component.CENTER_ALIGNMENT);
				editLName.setAlignmentX(Component.CENTER_ALIGNMENT);
				fName2.setAlignmentX(Component.CENTER_ALIGNMENT);
				lName2.setAlignmentX(Component.CENTER_ALIGNMENT);
				pass1.setAlignmentX(Component.CENTER_ALIGNMENT);
				pass2.setAlignmentX(Component.CENTER_ALIGNMENT);
				changePass.setAlignmentX(Component.CENTER_ALIGNMENT);
				
				panel1.add(fName1);
				panel1.add((Box.createRigidArea(new Dimension(0, 5))));
				panel1.add(fName2);
				panel1.add((Box.createRigidArea(new Dimension(0, 5))));
				panel1.add(editFName);
				panel1.add((Box.createRigidArea(new Dimension(0, 5))));
				panel1.add(lName1);
				panel1.add((Box.createRigidArea(new Dimension(0, 5))));
				panel1.add(lName2);
				panel1.add((Box.createRigidArea(new Dimension(0, 5))));
				panel1.add(editLName);
				panel1.add((Box.createRigidArea(new Dimension(0, 5))));
				panel1.add(pass1);
				panel1.add((Box.createRigidArea(new Dimension(0, 5))));
				panel1.add(pass2);
				panel1.add((Box.createRigidArea(new Dimension(0, 5))));
				panel1.add(changePass);
				panel1.add((Box.createRigidArea(new Dimension(0, 5))));
				
				pFrame.add(panel1);
				pFrame.setVisible(true);
				pFrame.setLocationRelativeTo(null);
				pFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				pFrame.setSize(225, 300);
				editFName.addActionListener(new ActionListener(){
					
					public void actionPerformed(ActionEvent e) {
						user2.setFname(fName2.getText());
						user2.saveUser();
						if(user2.getFname() != null & user2.getLname() != null)
							table.setValueAt(user2.getFname() + " " + user2.getLname(), select, 1);
						else if(user2.getFname() != null)
							table.setValueAt(user2.getFname() + "N/A", select, 1);
						else if(user2.getLname() != null)
							table.setValueAt("N/A" + user2.getLname(), select, 1);
						else
							table.setValueAt("N/A", select, 1);
						user2.saveUser();
					}
					});
				editLName.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						user2.setLname(lName2.getText());
						user2.saveUser();
						if(user2.getFname() != null & user2.getLname() != null)
							table.setValueAt(user2.getFname() + " " + user2.getLname(), select, 1);
						else if(user2.getFname() != null)
							table.setValueAt(user2.getFname() + "N/A", select, 1);
						else if(user2.getLname() != null)
							table.setValueAt("N/A" + user2.getLname(), select, 1);
						else
							table.setValueAt("N/A", select, 1);
						user2.saveUser();
					}
					});
				changePass.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						user2.setPW_Hash(user2.hashPassword(pass2.getText()));
						user2.saveUser();
					}
					});
				}
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
		
		pFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pFrame.setMinimumSize(new Dimension(300,300));
		pFrame.pack();
		pFrame.setVisible(true);
	}
	
	public void PopoutGroupUserControlDialog(Group group2) {
		pFrame = new JFrame();
		JMenuBar adminMenuBar = new JMenuBar();
		group = group2;
		
		JMenu Options, Window, ChangeRank, Edit;
		JMenuItem Remove, User, Admin, Refresh, Account, AddUser;
		utable = new Table();
		dtm = utable.createGroupUserTable(group);
		table = new JTable(dtm);
		
		Container paneC = pFrame.getContentPane();
		JScrollPane sp = new JScrollPane(table);
		JPanel pane = new JPanel();
		
		Options = new JMenu("Options");
		Remove = new JMenuItem("Remove");
		
		ChangeRank = new JMenu("Change Rank");
		User = new JMenuItem("User");
		Admin = new JMenuItem("Admin");
		
		Window = new JMenu("Window");
		Refresh = new JMenuItem("Refresh");
		
		Edit = new JMenu("Edit");
		Account = new JMenuItem("Account");
		AddUser = new JMenuItem("Add User");
		
		ChangeRank.add(User);
		ChangeRank.add(Admin);
		Options.add(Remove);
		Options.add(ChangeRank);
		Window.add(Refresh);
		Edit.add(Account);
		Edit.add(AddUser);
		adminMenuBar.add(Options);
		adminMenuBar.add(Window);
		adminMenuBar.add(Edit);
		
		Remove.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {	
				if( table.getSelectedRow() >= 0 & !Objects.equals(dtm.getValueAt(table.getSelectedRow(), 0), user.getNick())) {
					User user2 = new User();
					user2.loadUser((String) table.getValueAt(table.getSelectedRow(), 0));
					user2.removeGroup(group.getName());
					group.removeUser(e.getActionCommand().toString());
					user2.saveUser();
					group.saveGroup();
					dtm.removeRow(table.getSelectedRow());
				}
			}
		});
		User.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if( table.getSelectedRow() >= 0 & !Objects.equals(dtm.getValueAt(table.getSelectedRow(), 0), user.getNick())) {
					User user2 = new User();
					user2.loadUser((String) table.getValueAt(table.getSelectedRow(), 0));
					group.removeAdmin(user2);
					group.saveGroup();
					table.setValueAt("Admin", table.getSelectedRow(), 2);
				}
			}
		});
		Admin.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if( table.getSelectedRow() >= 0 & !Objects.equals(dtm.getValueAt(table.getSelectedRow(), 0), user.getNick())) {
					User user2 = new User();
					user2.loadUser((String) table.getValueAt(table.getSelectedRow(), 0));
					group.addAdmin(user2);
					table.setValueAt("Admin", table.getSelectedRow(), 2);
				}
			}
		});
		Refresh.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				pFrame.setVisible(false);
				pFrame.dispose();
				PopoutGroupUserControlDialog(group);
			}
		});
		Account.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				//Edited duplicate of SettingsActionListener code
				if( table.getSelectedRow() >= 0 ) {
				select = table.getSelectedRow();
				String userS = (String) table.getValueAt(table.getSelectedRow(), 0);
				user2 = new User();
				user2.loadUser(userS);
				JFrame pFrame = new JFrame();
				JPanel panel1 = new JPanel();
				panel1.setLayout(new BoxLayout(panel1, BoxLayout.PAGE_AXIS));
				JLabel fName1 = new JLabel("First Name:");
				fName2 = new JTextField(user2.getFname());
				JButton editFName = new JButton("Commit");
				JLabel lName1 = new JLabel("Last Name:");
				lName2 = new JTextField(user2.getLname());
				JButton editLName = new JButton("Commit");
				JLabel pass1 = new JLabel("Password:");
				pass2 = new JPasswordField();
				JButton changePass = new JButton("Commit");
				
				panel1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
				fName1.setAlignmentX(Component.CENTER_ALIGNMENT);
				editFName.setAlignmentX(Component.CENTER_ALIGNMENT);
				lName1.setAlignmentX(Component.CENTER_ALIGNMENT);
				editLName.setAlignmentX(Component.CENTER_ALIGNMENT);
				fName2.setAlignmentX(Component.CENTER_ALIGNMENT);
				lName2.setAlignmentX(Component.CENTER_ALIGNMENT);
				pass1.setAlignmentX(Component.CENTER_ALIGNMENT);
				pass2.setAlignmentX(Component.CENTER_ALIGNMENT);
				changePass.setAlignmentX(Component.CENTER_ALIGNMENT);
				
				panel1.add(fName1);
				panel1.add((Box.createRigidArea(new Dimension(0, 5))));
				panel1.add(fName2);
				panel1.add((Box.createRigidArea(new Dimension(0, 5))));
				panel1.add(editFName);
				panel1.add((Box.createRigidArea(new Dimension(0, 5))));
				panel1.add(lName1);
				panel1.add((Box.createRigidArea(new Dimension(0, 5))));
				panel1.add(lName2);
				panel1.add((Box.createRigidArea(new Dimension(0, 5))));
				panel1.add(editLName);
				panel1.add((Box.createRigidArea(new Dimension(0, 5))));
				panel1.add(pass1);
				panel1.add((Box.createRigidArea(new Dimension(0, 5))));
				panel1.add(pass2);
				panel1.add((Box.createRigidArea(new Dimension(0, 5))));
				panel1.add(changePass);
				panel1.add((Box.createRigidArea(new Dimension(0, 5))));
				
				pFrame.add(panel1);
				pFrame.setVisible(true);
				pFrame.setLocationRelativeTo(null);
				pFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				pFrame.setSize(225, 300);
				editFName.addActionListener(new ActionListener(){
					
					public void actionPerformed(ActionEvent e) {
						user2.setFname(fName2.getText());
						user2.saveUser();
						if(user2.getFname() != null & user2.getLname() != null)
							table.setValueAt(user2.getFname() + " " + user2.getLname(), select, 1);
						else if(user2.getFname() != null)
							table.setValueAt(user2.getFname() + "N/A", select, 1);
						else if(user2.getLname() != null)
							table.setValueAt("N/A" + user2.getLname(), select, 1);
						else
							table.setValueAt("N/A", select, 1);
						user2.saveUser();
					}
					});
				editLName.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						user2.setLname(lName2.getText());
						user2.saveUser();
						if(user2.getFname() != null & user2.getLname() != null)
							table.setValueAt(user2.getFname() + " " + user2.getLname(), select, 1);
						else if(user2.getFname() != null)
							table.setValueAt(user2.getFname() + "N/A", select, 1);
						else if(user2.getLname() != null)
							table.setValueAt("N/A" + user2.getLname(), select, 1);
						else
							table.setValueAt("N/A", select, 1);
						user2.saveUser();
					}
					});
				changePass.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						user2.setPW_Hash(user2.hashPassword(pass2.getText()));
						user2.saveUser();
					}
					});
				}
			}
		});
		AddUser.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String input = JOptionPane.showInputDialog("Enter the username: ");
				if( input != null ) {
				User adduser = new User();
				String temp = dir + "/src/Users/" + input + ".txt";
				System.out.println(temp);
    			File f = new File(temp);
				if(f.exists()){
					adduser.loadUser(input);
					if(!group.isUser(adduser)){
						group.addUser(adduser);
						group.saveGroup();
						adduser.saveUser();
					}
					else JOptionPane.showMessageDialog(null, "User already in this group");
				}
				else JOptionPane.showMessageDialog(null,"That user does not exist");
			}
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
		
		pFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pFrame.setMinimumSize(new Dimension(300,300));
		pFrame.pack();
		pFrame.setVisible(true);
	}
}

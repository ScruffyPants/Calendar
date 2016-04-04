import javax.swing.*;
import java.io.*;
//import java.nio.file.Files;

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
	JTextField enteredYear = new JTextField();
	JTextField enteredMonth = new JTextField();
	JButton forwards = new JButton(">");
	JButton backwards = new JButton("<");
	JButton a = new JButton();
	JCheckBox pEvent = new JCheckBox();
	JTable table = new JTable();
	GridBagConstraints c = new GridBagConstraints();
	UserTable utable = new UserTable();
	DefaultTableModel dtm = utable.createUserTable();
	String d = null;
	Time time;
	User user;
	
	Body(Time t, User u){
		time = t;
		user = u;
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
					time.setYear(time.getYear()-1);
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
					time.setYear(time.getYear()+1);
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
		JMenu Calendar, Account, Info, Admin;
		JMenuItem Exit, Logout, AddEvent, Reload, UserControl, Settings, About, GetToDate, Style;
		
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
		
		Style = new JMenuItem("Style");
		Exit = new JMenuItem("Exit");
		Logout = new JMenuItem("Logout");
		AddEvent = new JMenuItem("Add Event");
		Reload = new JMenuItem("Reload");
		Settings = new JMenuItem("Settings");
		About = new JMenuItem("About");
		GetToDate = new JMenuItem("Get To Date");
		
		Info.add(About);
		Calendar.add(Exit);
		Calendar.add(Reload);
		Calendar.add(GetToDate);
		Account.add(AddEvent);
		Account.add(Style);
		Account.add(Settings);
		Account.add(Logout);
		
		Style.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				user.getStyle().setStyle();
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
		
		Reload.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				frame.dispose();
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
				JTextField fName2 = new JTextField(user.getFname());
				JButton editFName = new JButton("Edit");
				JLabel lName1 = new JLabel("Last Name:");
				JTextField lName2 = new JTextField(user.getLname());
				JButton editLName = new JButton("Edit");
				JLabel pass1 = new JLabel("Password:");
				JPasswordField pass2 = new JPasswordField();
				JButton changePass = new JButton("Edit");
				
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
		label.setForeground(user.getStyle().getForeground());
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
					if(user.getEventsByDate(time.getYear(), time.getMonth(), i).size()>0)a.setBackground(user.getStyle().getEventBackground());
					else a.setBackground(user.getStyle().getDayBackground());
					a.setHorizontalAlignment(SwingConstants.LEFT);
					a.setVerticalAlignment(SwingConstants.TOP);
					a.setBorder(null);
					a.setPreferredSize(new Dimension(90,90));
					main.add(a);
					a.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent e) {
							System.out.println("you pressed: "+e.getActionCommand());
							LinkedList<Event> events = user.getEventsByDate(time.getYear(), time.getMonth(), Integer.parseInt(e.getActionCommand()));
							PopoutEventShow(events, e.getActionCommand());
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
	
	public void PopoutEventShow(LinkedList<Event> events, String d){
		pFrame = new JFrame();
		JButton create = new JButton("Create event");
		
		for(Event a: events){
			JPanel panel = new JPanel();
			panel.setLayout(new GridBagLayout());
			
			JLabel info = new JLabel(a.getName()+" ("+Integer.toString(a.getYear())+" "+Integer.toString(a.getMonth())+" "+Integer.toString(a.getDay())+")");
			JLabel desc = new JLabel("Description: \n"+a.getDescription());
			
			info.setSize(100000, 50);
			info.setHorizontalAlignment(SwingConstants.LEFT);
			info.setVerticalAlignment(SwingConstants.BOTTOM);
			desc.setHorizontalAlignment(SwingConstants.LEFT);
			desc.setVerticalAlignment(SwingConstants.TOP);
			
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
		pFrame.setLayout(new GridLayout(0,1));
		pFrame.pack();
		pFrame.setLocationRelativeTo(null);
		pFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pFrame.setVisible(true);
	}
	
	public void PopoutEventAdd(){
		JButton submit = new JButton("Submit");
		pFrame = new JFrame();
		
		pFrame.add(new JLabel("Name: "));
		pFrame.add(name);
		
		pFrame.add(new JLabel("Year: "));
		pFrame.add(year);
		
		pFrame.add(new JLabel("Month: "));
		pFrame.add(month);
		
		pFrame.add(new JLabel("Day: "));
		pFrame.add(day);
		
		description = new JTextField();
		description.setSize(100,100);
		pFrame.add(new JLabel("Description: "));
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
		pFrame = new JFrame();
		JMenuBar adminMenuBar = new JMenuBar();

		JMenu Options, Window, ChangeRank, Edit;
		JMenuItem Ban, Verify, Student, Teacher, Admin, Refresh, Account, Events;
		utable = new UserTable();
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
		Events = new JMenuItem("Events");
		
		ChangeRank.add(Student);
		ChangeRank.add(Teacher);
		ChangeRank.add(Admin);
		Options.add(Ban);
		Options.add(Verify);
		Options.add(ChangeRank);
		Window.add(Refresh);
		Edit.add(Account);
		Edit.add(Events);
		adminMenuBar.add(Options);
		adminMenuBar.add(Window);
		adminMenuBar.add(Edit);
		
		Ban.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {	
				if( table.getSelectedRow() >= 0 & !Objects.equals(dtm.getValueAt(table.getSelectedRow(), 0), user.getNick())) {
					File toDelete = new File(dir + "\\src\\Users\\" + dtm.getValueAt(table.getSelectedRow(), 0) + ".txt"); 
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
					table.setValueAt("" + user2.getIsVerified(), table.getSelectedRow(), 2);
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
					table.setValueAt("Student", table.getSelectedRow(), 1);
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
					table.setValueAt("Teacher", table.getSelectedRow(), 1);
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
					table.setValueAt("Admin", table.getSelectedRow(), 1);
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
				String userS = (String) table.getValueAt(table.getSelectedRow(), 0);
				User user2 = new User();
				user2.loadUser(userS);
				JFrame pFrame = new JFrame();
				JPanel panel1 = new JPanel();
				panel1.setLayout(new BoxLayout(panel1, BoxLayout.PAGE_AXIS));
				JLabel fName1 = new JLabel("First Name:");
				JTextField fName2 = new JTextField(user2.getFname());
				JButton editFName = new JButton("Edit");
				JLabel lName1 = new JLabel("Last Name:");
				JTextField lName2 = new JTextField(user2.getLname());
				JButton editLName = new JButton("Edit");
				JLabel pass1 = new JLabel("Password:");
				JPasswordField pass2 = new JPasswordField();
				JButton changePass = new JButton("Edit");
				
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
					}
					});
				editLName.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						user2.setLname(lName2.getText());
						user2.saveUser();
					}
					});
				changePass.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						user.setPW_Hash(user.hashPassword(pass2.getText()));
						user2.saveUser();
					}
					});
			}
		});
		Events.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String userS = (String) table.getValueAt(table.getSelectedRow(), 0);
				User user2 = new User();
				user2.loadUser(userS);
				//Load event visualization/editing window of user2
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

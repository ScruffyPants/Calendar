import javax.swing.*;
import java.io.*;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;

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
		JMenuItem Exit, Logout, AddEvent, Reload, AddPublicEvent, UserControl, AccountInfo, About;
		
		Calendar = new JMenu("Calendar");
		Account = new JMenu("Account");
		Info = new JMenu("Info");
		
		if(user.getIsAdmin() && user.getIsVerified()) {
			Admin = new JMenu("Admin");
			menuBar.add(Admin);
			AddPublicEvent = new JMenuItem("Add Public Event");
			UserControl = new JMenuItem("User Control Panel");
			Admin.add(AddPublicEvent);
			Admin.add(UserControl);
			AddPublicEvent.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					PopoutPEventAdd();
				}
			});
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
		AccountInfo = new JMenuItem("Account Info");
		About = new JMenuItem("About");
		
		Info.add(About);
		Calendar.add(Exit);
		Calendar.add(Reload);
		Account.add(AddEvent);
		Account.add(Logout);
		Account.add(AccountInfo);
		
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
		
		AccountInfo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				pFrame = new JFrame();
				JPanel panel1 = new JPanel();
				panel1.setLayout(new BoxLayout(panel1, BoxLayout.PAGE_AXIS));
				JLabel fName = new JLabel(user.getFname());
				JLabel lName = new JLabel(user.getLname());
				JLabel nick = new JLabel(user.getNick());
				
				panel1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
				fName.setAlignmentX(Component.LEFT_ALIGNMENT);
				lName.setAlignmentX(Component.LEFT_ALIGNMENT);
				nick.setAlignmentX(Component.LEFT_ALIGNMENT);
				
				panel1.add(new JLabel("First Name:"));
				panel1.add(fName);
				panel1.add(new JLabel("Last Name:"));
				panel1.add(lName);
				panel1.add(new JLabel("Nickname:"));
				panel1.add(nick);
				
				pFrame.add(panel1);
				pFrame.setVisible(true);
				pFrame.setLocationRelativeTo(null);
				pFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				pFrame.setSize(300, 300);
			}
		});
		
		menuBar.setPreferredSize(new Dimension(300,500));
	}
	
	public void DrawCalendar(){
		JPanel main = new JPanel(new GridLayout());
		calendar = new JPanel();
		JLabel label = new JLabel(time.getYear()+" "+time.getMonthName(time.getMonth()));
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		label.setSize(100, 30);
		label.setForeground(Color.white);
		calendar.setLayout(new BoxLayout(calendar, BoxLayout.PAGE_AXIS));
		calendar.add(label, calendar);
		
		main = new JPanel(new GridLayout());
		double dim = (double)time.getDaysInMonth();
		int rows = (int)Math.ceil(dim/7);
		int cols = 7;
		System.out.println(rows+" x "+cols+" ");
		main.setLayout(new GridLayout(rows,cols,2,2));
		boolean done = false;
		int i=1;
		while(!done){
			if(i>=dim){
				JLabel a = new JLabel(" ");
				main.add(a);
				if(i%7==0){
					done = true;
					break;
				}
			}
			else{
				a = new JButton(Integer.toString(i));
				if(user.getEventsByDate(time.getYear(), time.getMonth(), i).size()>0)a.setBackground(Color.green);
				else a.setBackground(Color.white);
				a.setHorizontalAlignment(SwingConstants.LEFT);
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
			}
			i++;
		}
		main.setBackground(Color.black);
		calendar.add(main);
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
		
		description = new JTextField();
		description.setSize(100,100);
		pFrame.add(new JLabel("description: "));
		pFrame.add(description);
		
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
					bFrame.setLocationRelativeTo(null);
					bFrame.setResizable(false);
					bFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				}
			}
		});
		//pFrame.setResizable(false);
	}
	
	public void PopoutPEventAdd(){
		JFrame pFrame = new JFrame();
		JButton submit = new JButton("Submit");
		
		pFrame.add(new JLabel("Name: "));
		pFrame.add(name);
		
		pFrame.add(new JLabel("Year: "));
		pFrame.add(year);
		
		pFrame.add(new JLabel("Month: "));
		pFrame.add(month);
		
		pFrame.add(new JLabel("Day: "));
		pFrame.add(day);
		
		pFrame.add(submit);
		pFrame.setLayout(new GridLayout(0,1));
		pFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pFrame.setLocationRelativeTo(null);
		pFrame.pack();
		pFrame.setVisible(true);
		
		submit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(!name.getText().isEmpty() && !year.getText().isEmpty() && !month.getText().isEmpty() && !day.getText().isEmpty()){
					Event PEvent = new Event(name.getText(), Integer.parseInt(year.getText(),10), Integer.parseInt(month.getText(),10), Integer.parseInt(day.getText(),10), description.getText());
					user.addPEvent(PEvent);
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
		//pFrame.setResizable(false);
	}
	public void PopoutUserControlDialog() {
		JFrame pFrame = new JFrame();
		JMenuBar adminMenuBar = new JMenuBar();
		JMenu Options, Window;
		JMenuItem Ban, Verify, ChangeRank, Refresh;
		File temp = new File(dir + "\\src\\users");
		UserTable utable = new UserTable();
		JTable table = utable.createUserTable();
		JScrollPane sp = new JScrollPane(table);
		
		table.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseClicked(java.awt.event.MouseEvent e) {
		        int row = table.rowAtPoint(e.getPoint());
		        int col = table.columnAtPoint(e.getPoint());
		        if (row >= 0 && col >= 0) {
		        	ClickTracker = row;
		        	System.out.println("Clicked on the " + ClickTracker + "-th row.");
		        }
		        else
		        	ClickTracker = -1;
		    }
		});
		
		Container paneC = pFrame.getContentPane();
		JPanel pane = new JPanel();
		Options = new JMenu("Options");
		Window = new JMenu("Window");
		Refresh = new JMenuItem("Refresh");
		Ban = new JMenuItem("Ban");
		Verify = new JMenuItem("Toggle Verification");
		ChangeRank = new JMenuItem("Change Rank");
		Options.add(Ban);
		Options.add(Verify);
		Options.add(ChangeRank);
		Window.add(Refresh);
		adminMenuBar.add(Options);
		adminMenuBar.add(Window);
		Ban.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {	
				System.out.println("Ban button pressed");
				if( ClickTracker >= 0 ) {
					File toDelete = new File(dir + "\\src\\Users\\" + table.getValueAt(ClickTracker, 0)); 
					if(!toDelete.isDirectory())
						toDelete.delete();
				}
			}
		});
		Verify.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				System.out.println("Verification button pressed");
				if( ClickTracker >= 0 ) {
					String userS = (String) table.getValueAt(ClickTracker, 0);
					User user = new User();
					user.loadUser(userS);
					user.setIsVerified(!user.getIsVerified());
					user.saveUser();
				}
			}
		});
		ChangeRank.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				System.out.println("Rank-changing button pressed");
				if( ClickTracker >= 0 ) {
					String userS = (String) table.getValueAt(ClickTracker, 0);
					User user = new User();
					user.loadUser(userS);
				}
			}
		});
		Refresh.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				System.out.println("Refreshing button pressed");
				pFrame.setVisible(false);
				pFrame.dispose();
				PopoutUserControlDialog();
			}
		});
		pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));
		adminMenuBar.setAlignmentX(Component.RIGHT_ALIGNMENT);
		adminMenuBar.setMaximumSize(new Dimension(1920,30));
		pane.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		sp.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
		pane.add(adminMenuBar);
		pane.add(sp);
		pane.setMinimumSize(new Dimension(300,300));
		paneC.add(pane);
		paneC.setMinimumSize(new Dimension(300,300));
		pane.setVisible(true);
		
		pFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pFrame.setMinimumSize(new Dimension(300,300));
		pFrame.pack();
		pFrame.setVisible(true);
	}
	}

import java.io.*;
//import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Table {
	private static final long serialVersionUID = 1504199602031999L;
	private final String dir = System.getProperty("user.dir");
	
	public Table() {
	}
	
	public DefaultTableModel createUserTable() {
		
		String[] columns = {"User", "Name", "Rank", "Verified", "Events"};		
		File temp = new File(dir + "/src/Users");
		String[] users = listFilesForFolder( temp ).replaceAll(".txt",  "").split("::::::::::");
		String[][] data = new String[users.length][columns.length];
		
		for(int i = 0; i < users.length; i++) {
			User utemp = new User();
			utemp.loadUser(users[i]);
			utemp.sortEvent(utemp.getEvents());
			data[i][0] = utemp.getNick();
			
			if(utemp.getFname() != null & utemp.getLname() != null)
				data[i][1] = utemp.getFname() + " " + utemp.getLname();
			else if(utemp.getFname() != null)
				data[i][1] = utemp.getFname() + " N/A";
			else if(utemp.getLname() != null)
				data[i][1] = "N/A" + utemp.getLname();
			else
				data[i][1] = "N/A";
			
			if( utemp.getIsAdmin() )
				data[i][2] = "Admin";
			else if( utemp.getIsTeacher() )
				data[i][2] = "Teacher";
			else
				data[i][2] = "Student";
			
			data[i][3] = "" + utemp.getIsVerified();
			data[i][4] = utemp.printEvents() + " ---- " + utemp.printSchedules();
		}
		
		DefaultTableModel userTable = new DefaultTableModel(data, columns) {
			private static final long serialVersionUID = 1504199602031999L;
			@Override
		    public boolean isCellEditable(int row, int column) {
			       //all cells false
			       return false;
			}
		};
		return userTable;
	}
	
	public DefaultTableModel createGroupTable() {
		String[] columns = {"Name", "Users", "Admins", "Events"};
		File temp = new File(dir + "/src/Groups");
		String[] groups = listFilesForFolder( temp ).replaceAll(".txt",  "").split("::::::::::");
		for( int i = 0; i < groups.length; i++ ) {
			System.out.println(groups[i]);
		}
		String[][] data = new String[groups.length][columns.length];
		
		for( int i = 0; i < groups.length; i++ ) {
			Group gTemp = new Group();
			gTemp.loadGroup(groups[i]);
			//gTemp.sortEvent(gTemp.getEvents());
			
			data[i][0] = gTemp.getName();
			
			StringBuilder sb = new StringBuilder();
			for( User a : gTemp.getUsers() ) {
				sb.append(a.getNick() + " + ");
			}
			data[i][1] = sb.toString();
			sb.setLength(0);
			
			for( User a : gTemp.getAdmins() ) {
				sb.append(a.getNick() + " + ");
			}
			data[i][2] = sb.toString();
			
			data[i][3] = gTemp.printEvents();
		}

		DefaultTableModel userTable = new DefaultTableModel(data, columns) {
			private static final long serialVersionUID = 1504199602031999L;
			@Override
		    public boolean isCellEditable(int row, int column) {
			       //all cells false
			       return false;
			}
		};
		return userTable;
	}
	
	public String listFilesForFolder(final File folder) {
		StringBuilder sb = new StringBuilder();
	    for (final File fileEntry : folder.listFiles()) {
	        //if (fileEntry.isDirectory()) {
	        //    listFilesForFolder(fileEntry);
	        //} else {
	        sb.append(fileEntry.getName() + "::::::::::");
	    	}
	    return sb.toString();
	}
	
}


import java.io.*;
//import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class UserTable {
	private static final long serialVersionUID = 1504199602031999L;
	private final String dir = System.getProperty("user.dir");
	
	public UserTable() {
	}
	
	public DefaultTableModel createUserTable() {
		
		String[] columns = {"User", "Name", "Rank", "Verified", "Events"};		
		File temp = new File(dir + "/src/Users");
		String[] users = listFilesForFolder( temp ).replaceAll(".txt",  "").split(" ");
		String[][] data = new String[users.length][columns.length];
		
		for(int i = 0; i < users.length; i++) {
			User utemp = new User();
			utemp.sortEvent(utemp.getEvents());
			utemp.loadUser(users[i]);
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
			System.out.println(utemp.printEvents());
			System.out.println(utemp.printPEvents());
			System.out.println(utemp.printEvents() + " " + utemp.printPEvents());
			data[i][4] = utemp.printEvents() + " " + utemp.printPEvents();
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
	        sb.append(fileEntry.getName() + " ");
	    	}
	    return sb.toString();
	}
	
}

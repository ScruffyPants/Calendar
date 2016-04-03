
import java.io.*;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class UserTable {
	private static final long serialVersionUID = 1504199602031999L;
	private final String dir = System.getProperty("user.dir");
	
	public UserTable() {
	}
	
	public DefaultTableModel createUserTable() {
		
		String[] columns = {"User", "Rank", "Verified", "Events"};		
		File temp = new File(dir + "\\src\\Users");
		String[] users = listFilesForFolder( temp ).replaceAll(".txt",  "").split(" ");
		String[][] data = new String[users.length][columns.length];
		for(int i = 0; i < users.length; i++) {
			User utemp = new User();
			utemp.sortEvent(utemp.getEvents());
			utemp.loadUser(users[i]);
			data[i][0] = utemp.getNick();
			if( utemp.getIsAdmin() )
				data[i][1] = "Admin";
			else if( utemp.getIsTeacher() )
				data[i][1] = "Teacher";
			else
				data[i][1] = "Student";
			data[i][2] = "" + utemp.getIsVerified();
			data[i][3] = utemp.printEvents();
		}
		DefaultTableModel userTable = new DefaultTableModel(data, columns);
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

package Helper;
import java.sql.*;

public class DBConnection {
	Connection c=null;
	
	public DBConnection() {}
	
	public Connection connDb() {
		try {
			this.c = DriverManager.getConnection("jdbc:mariadb://localhost:1713/dentclinic?user=root&password=root123");
			return c;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		return c;
		
	}
	
	
}
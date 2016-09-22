package global.reusables;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//import static 

public class DBConnectionManager {
	
	private static String DB_ConnectionString2 = null;
	private static String DB_ConnectionString1= null; 
	private static String DB_DRIVERNAME = null; 
	static {
		String path=System.getProperty("user.dir")+"\\Configurations\\base.properties";
		
		DB_ConnectionString2 = GenericMethods.getPropertyValue("conString2", path);
		DB_ConnectionString1=GenericMethods.getPropertyValue("conString1", path);
		DB_DRIVERNAME=GenericMethods.getPropertyValue("driverName", path);
		
		
		
		
		
	}


	public static Connection getConnection(String relPath) throws ClassNotFoundException,
			SQLException {

		Connection connection = null;
					Class.forName(DB_DRIVERNAME);
				
					connection = DriverManager.getConnection(DB_ConnectionString1+relPath+DB_ConnectionString2
							);
					
					return connection;
	}//End of Method. 
	
	


	public static void close(ResultSet rs) {

		if (rs != null)
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	public static void close(Statement statement) {

		if (statement != null)
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	public static void close(PreparedStatement preStatement) {

		if (preStatement != null)
			try {
				preStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	public static void close(Connection connection) {

		try {
			if (connection != null)
				connection.close();
			//System.out.println("Connection closed successfully");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	

}//End of Class.

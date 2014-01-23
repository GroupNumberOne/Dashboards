package dashboard_deborah.java.code;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnectorSingleton {
	private String databaseUrl;
	private Properties props;
	private Connection databaseConn;
	private static DBConnectorSingleton uniqueInstance;
	
	private DBConnectorSingleton(String host, String port, String dbname, String user, String pw) throws SQLException {
		databaseUrl = "jdbc:postgresql://"+host+":"+port+"/"+dbname;
		props = new Properties();
		props.setProperty("user",user);
		props.setProperty("password",pw);
		//props.setProperty("ssl","true");
			
		databaseConn = DriverManager.getConnection(databaseUrl, props);
	}
	
	public Connection getDBConn() {
		return databaseConn;
	}
	
	public static DBConnectorSingleton getInstance(String host, String port, String dbname, String user, String pw) throws SQLException {
		if (uniqueInstance == null) {
			uniqueInstance = new DBConnectorSingleton(host, port, dbname, user, pw);
		}
		else {
		}
		return uniqueInstance;
		
	}
}

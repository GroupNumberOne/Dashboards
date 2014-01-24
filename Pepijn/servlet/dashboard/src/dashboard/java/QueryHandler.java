package dashboard.java;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class QueryHandler {

	private Connection databaseConnection;

	ArrayList<String> result = new ArrayList<String>();

	QueryHandler(Connection conn) {
		databaseConnection = conn;
	}

	// SQL query, SELECT "column" from "table"
	public ArrayList<String> doSelect(String query) {
		try {
			Statement st = databaseConnection.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				String str = rs.getString("return".toString());
				result.add(str);
			}

			st.close();
			rs.close();
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}

		return result;
	}
}
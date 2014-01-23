package DBConnector;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public class tsvtest {
	public static void main(String args[]) throws SQLException, IOException, ParseException {
		
		DataWriter writer = new DataWriter();
		writer.writeVacatureDonutGraphData();
		writer.writeBrushBarGraphData();
		writer.writeCVDonutGraphData();
		writer.writeSequenceGraphData();
		writer.writeToolTipBarGraphData();
		writer.writeDualBarBarGraphData();
		writer.writeShowReelGraphData();
	}
}

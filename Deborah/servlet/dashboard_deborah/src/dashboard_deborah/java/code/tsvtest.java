package dashboard_deborah.java.code;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public class tsvtest {
	public void Execute() throws SQLException, IOException, ParseException {
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

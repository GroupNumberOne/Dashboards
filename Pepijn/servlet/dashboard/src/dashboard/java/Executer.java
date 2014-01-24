package dashboard.java;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Executer {
	public void Execute() throws SQLException, IOException {
		sortableBar();
		donutGraph();
		sequencesSunBurst();
		pieChart();
		pieChart2();
		areaChart();
	}
	
	public static ArrayList<String> executeSelect(String query) throws SQLException {
		DBConnectorSingleton dbcs = DBConnectorSingleton.getInstance(
				"145.24.222.158", "5432", "INFPRJ01-56", "postgres", "GroeP1");
		
		QueryHandler qh = new QueryHandler(dbcs.getDBConn());
		ArrayList<String> output = qh.doSelect(query);
		return output;
	}
	
	public static void areaChart() throws SQLException, IOException {
		FileWriter fw = new FileWriter();
		ArrayList<String> LastCrawledNumbers = executeSelect("SELECT COUNT(lastcrawled) AS return, lastcrawled FROM urls GROUP by lastcrawled ORDER BY lastcrawled");
		ArrayList<String> LastCrawledDate = executeSelect("SELECT COUNT(lastcrawled), lastcrawled AS return FROM urls GROUP by lastcrawled ORDER BY lastcrawled");
		fw.writeAreaChartData(LastCrawledDate,LastCrawledNumbers);
	}
	
	public static void donutGraph() throws SQLException, IOException {
		FileWriter fw = new FileWriter();
		ArrayList<String> CVopleidingenData = executeSelect("SELECT COUNT(opleiding) as return, opleiding FROM cv GROUP BY opleiding");
		ArrayList<String> CVopleidingenNaam = executeSelect("SELECT COUNT(opleiding), opleiding AS return FROM cv GROUP BY opleiding");
		ArrayList<String> VACopleidingenData = executeSelect("SELECT COUNT(niveau) as return, niveau FROM vacatures GROUP BY niveau");
		ArrayList<String> VACopleidingenNaam = executeSelect("SELECT COUNT(niveau), niveau AS return FROM vacatures GROUP BY niveau");
		fw.writeDonutGraphData(CVopleidingenNaam,CVopleidingenData,VACopleidingenNaam,VACopleidingenData);
	}
	
	public static void sortableBar() throws SQLException, IOException{
		FileWriter fw = new FileWriter();
		ArrayList<String> CVwoonplaatsNaam = executeSelect("SELECT COUNT(woonplaats) AS return, woonplaats FROM cv GROUP BY woonplaats ORDER BY COUNT(woonplaats) DESC");
		ArrayList<String> CVwoonplaatsData = executeSelect("SELECT COUNT(woonplaats), woonplaats AS return FROM cv GROUP BY woonplaats ORDER BY COUNT(woonplaats) DESC");
		fw.writeSortableBarData(CVwoonplaatsNaam,CVwoonplaatsData);
	}
	
	public static void pieChart() throws SQLException, IOException{
		FileWriter fw = new FileWriter();
		ArrayList<String> rijbewijsCountList = executeSelect("SELECT COUNT(rijbewijs) as return, rijbewijs FROM cv GROUP BY rijbewijs");
		ArrayList<String> rijbewijsValuesList = executeSelect("SELECT COUNT(rijbewijs), rijbewijs as return FROM cv GROUP BY rijbewijs");
		fw.writePieData(rijbewijsValuesList,rijbewijsCountList);
	}
	
	public static void pieChart2() throws SQLException, IOException{
		FileWriter fw = new FileWriter();
		ArrayList<String> cvCount = executeSelect("SELECT COUNT(*) as return FROM cv");
		ArrayList<String> vacCount = executeSelect("SELECT COUNT(*) as return FROM vacatures");
		fw.writePieData2(cvCount,vacCount);
	}
	
	public static void sequencesSunBurst()throws SQLException, IOException{
		FileWriter fw = new FileWriter();
		// arraylisten samenvoegen
		ArrayList<String> CVcvenvacbank = executeSelect("SELECT COUNT(url) AS return FROM cv WHERE url LIKE '%cvenvacaturebank.nl%'");
		ArrayList<String> CVstarapple = executeSelect("SELECT COUNT(url) AS return FROM cv WHERE url LIKE '%starapple.nl%'");
		ArrayList<String> CVmonsterboard = executeSelect("SELECT COUNT(url) AS return FROM cv WHERE url LIKE '%monsterboard.nl%'");
		ArrayList<String> VACcvenvacbank = executeSelect("SELECT COUNT(url) AS return FROM vacatures WHERE url LIKE '%cvenvacaturebank.nl%'");
		ArrayList<String> VACstarapple = executeSelect("SELECT COUNT(url) AS return FROM vacatures WHERE url LIKE '%starapple.nl%'");
		ArrayList<String> VACmonsterboard = executeSelect("SELECT COUNT(url) AS return FROM vacatures WHERE url LIKE '%monsterboard.nl%'");
		fw.writeSequenceGraphData(CVcvenvacbank,CVstarapple,CVmonsterboard,VACcvenvacbank,VACstarapple,VACmonsterboard);
	}
}
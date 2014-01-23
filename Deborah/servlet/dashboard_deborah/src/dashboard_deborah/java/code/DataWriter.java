package dashboard_deborah.java.code;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;

public class DataWriter {
	
	public ArrayList<String> doSelect(String query) throws SQLException {
		DBConnectorSingleton dbcs = DBConnectorSingleton.getInstance("145.24.222.158", "5432", "INFPRJ01-56", "postgres", "GroeP1");

		ArrayList<String> result = new ArrayList<String>();
		
		try {
			Statement st = dbcs.getDBConn().createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				String str = rs.getString("count");
				result.add(str);
			}

			st.close();
			rs.close();
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}

		return result;
	}
	
	public void writeVacatureDonutGraphData() throws SQLException {
    try {
        File TSVFile = new File("C:/nginx/html/dashboard/deborah/data/VacatureDonutGraphData.tsv");
        FileOutputStream is = new FileOutputStream(TSVFile);
        OutputStreamWriter osw = new OutputStreamWriter(is, "UTF-8");    
        Writer w = new BufferedWriter(osw);
        w.write("xdienstverband"+"\t"+"xfunctie"+"\t"+"dienstverband"+"\t"+"functie"+"\r\n");
        
        ArrayList<String> al = new ArrayList<String>();
        String query = "SELECT COUNT(dienstverband), dienstverband FROM vacatures GROUP BY dienstverband";
        al = doSelect(query);
        
        ArrayList<String> al2 = new ArrayList<String>();
        query = "SELECT COUNT(functie) as count, functie FROM vacatures GROUP BY functie";
        al2 = doSelect(query);
        
        ArrayList<String> al3 = new ArrayList<String>();
        query = "SELECT COUNT(dienstverband) as d, dienstverband as count FROM vacatures GROUP BY dienstverband";
        al3 = doSelect(query);
        
        ArrayList<String> al4 = new ArrayList<String>();
        query = "SELECT COUNT(functie) as d, functie as count FROM vacatures GROUP BY functie";
        al4 = doSelect(query);

        for (int x = 0; 500 > x + 1; x++) {
        	String alint = "";
        	String al2int = "";
        	String al3int = "";
        	String al4int = "";
        	if (al.size() <= x) {
        		alint = "0";
        	}
        	else {
        		alint = al.get(x);
        	}
        	if (al2.size() <= x) {
        		al2int = "0";
        	}
        	else {
        		al2int = al2.get(x);
        	}
        	if (al3.size() <= x) {
        		al3int = " ";
        	}
        	else {
        		al3int = al3.get(x);
        	}
        	if (al4.size() <= x) {
        		al4int = " ";
        	}
        	else {
        		al4int = al4.get(x);
        	}
            w.write(alint + "\t" + al2int + "\t" + al3int + "\t" + al4int +  "\r\n");
        }
        
        w.close();

    } catch (IOException e) {
        System.err.println("Problem writing to the file");
    }
}
	public void writeBrushBarGraphData() throws IOException, SQLException, ParseException {      
        File CSVFile = new File("C:/nginx/html/dashboard/deborah/data/BrushBarData.csv");
        FileOutputStream is = new FileOutputStream(CSVFile);
        OutputStreamWriter osw = new OutputStreamWriter(is);    
        Writer w = new BufferedWriter(osw);
        w.write("date"+","+"price"+"\r\n");
        
        ArrayList<String> al = new ArrayList<String>();
        String query = "SELECT lastcrawled as count FROM urls WHERE lastcrawled IS NOT NULL GROUP BY lastcrawled ORDER BY lastcrawled";
        al = doSelect(query);
        
        ArrayList<String> al2 = new ArrayList<String>();
        query = "SELECT COUNT(lastcrawled) as count FROM urls WHERE lastcrawled IS NOT NULL GROUP BY lastcrawled ORDER BY lastcrawled";
        al2 = doSelect(query);
        
        for (int x = 0; al2.size() > x + 1; x++) {
        	String alint = "";
        	String al2int = "";
        	if (al.size() <= x) {
        		alint = "0";
        		System.out.println(alint);
        	}
        	else {
        		alint = al.get(x);
        	}
        	if (al2.size() <= x) {
        		alint = "0";
        	}
        	else {
        		al2int = al2.get(x);
        	}
            w.write(alint+","+al2int+"\r\n");
        }
        w.close();
	}

	public void writeCVDonutGraphData() throws SQLException {
	    try {
	        File TSVFile = new File("C:/nginx/html/dashboard/deborah/data/CVDonutGraphData.tsv");
	        FileOutputStream is = new FileOutputStream(TSVFile);
	        OutputStreamWriter osw = new OutputStreamWriter(is);    
	        Writer w = new BufferedWriter(osw);
	        w.write("xrij"+"\t"+"xberoep"+"\t"+"rij"+"\t"+"beroep"+"\r\n");
	        
	        ArrayList<String> al = new ArrayList<String>();
	        String query = "SELECT COUNT(rijbewijs) as count, rijbewijs FROM cv WHERE rijbewijs IS NOT NULL GROUP BY rijbewijs";
	        al = doSelect(query);
	        
	        ArrayList<String> al2 = new ArrayList<String>();
	        query = "SELECT COUNT(beroep) as count, beroep FROM cv GROUP BY beroep";
	        al2 = doSelect(query);
	        
	        ArrayList<String> al3 = new ArrayList<String>();
	        query = "SELECT COUNT(rijbewijs) as poep, rijbewijs as count FROM cv WHERE rijbewijs IS NOT NULL GROUP BY rijbewijs";
	        al3 = doSelect(query);
	        
	        ArrayList<String> al4 = new ArrayList<String>();
	        query = "SELECT COUNT(beroep) as poep, beroep as count FROM cv GROUP BY beroep";
	        al4 = doSelect(query);

	        for (int x = 0; 500 > x + 1; x++) {
	        	String alint = "";
	        	String al2int = "";
	        	String al3int = "";
	        	String al4int = "";
	        	if (al.size() <= x) {
	        		alint = "0";
	        	}
	        	else {
	        		alint = al.get(x);
	        	}
	        	if (al2.size() <= x) {
	        		al2int = "0";
	        	}
	        	else {
	        		al2int = al2.get(x);
	        	}
	        	if (al3.size() <= x) {
	        		al3int = " ";
	        	}
	        	else {
	        		if (al3.get(x).equals("t")) {
	        			al3int = "Wel";
	        		}
	        		else {
	        			al3int = "Niet";
	        		}
	        	}
	        	if (al4.size() <= x) {
	        		al4int = " ";
	        	}
	        	else {
	        		al4int = al4.get(x);
	        	}
	            w.write(alint + "\t" + al2int + "\t" + al3int + "\t" + al4int +  "\r\n");
	        }
	        
	        w.close();

	    } catch (IOException e) {
	        System.err.println("Problem writing to the file");
	    }
	}
	
	public void writeSequenceGraphData() throws SQLException {
	    try {
	        File CSVFile = new File("C:/nginx/html/dashboard/deborah/data/SequenceGraphData.csv");
	        FileOutputStream is = new FileOutputStream(CSVFile);
	        OutputStreamWriter osw = new OutputStreamWriter(is);    
	        Writer w = new BufferedWriter(osw);
	        
	        ArrayList<String> al = new ArrayList<String>();
	        String query = "SELECT COUNT(url) as count FROM cv WHERE lower(substring(url from 12 for 16)) SIMILAR TO 'cvenvacaturebank%'";
	        al = doSelect(query);
	        
	        ArrayList<String> al2 = new ArrayList<String>();
	        query = "SELECT COUNT(url) as count FROM cv WHERE lower(substring(url from 8 for 15)) SIMILAR TO 'cv.monsterboard%'";
	        al2 = doSelect(query);
	        
	        ArrayList<String> al3 = new ArrayList<String>();
	        query = "SELECT COUNT(url) as count FROM cv WHERE lower(substring(url from 12 for 9)) SIMILAR TO 'starapple%'";
	        al3 = doSelect(query);

	        for (int x = 0; al.size() > x; x++) {
	            w.write("cvenvacaturebank.nl-cv"+","+al.get(x) + "\r\n");
	            w.write("monsterboard.nl-cv"+","+al2.get(x) + "\r\n");
	            w.write("starapple.nl-cv"+","+al3.get(x) + "\r\n");
	        }
	        
	        query = "SELECT COUNT(url) as count FROM vacatures WHERE lower(substring(url from 12 for 16)) SIMILAR TO 'cvenvacaturebank%'";
	        al = doSelect(query);
	        
	        query = "SELECT COUNT(url) as count FROM vacatures WHERE lower(substring(url from 8 for 21)) SIMILAR TO 'vacature.monsterboard%'";
	        al2 = doSelect(query);
	        
	        query = "SELECT COUNT(url) as count FROM vacatures WHERE lower(substring(url from 12 for 9)) SIMILAR TO 'starapple%'";
	        al3 = doSelect(query);
	        
	        for (int x = 0; al.size() > x; x++) {
	            w.write("cvenvacaturebank.nl-vacature"+","+al.get(x) + "\r\n");
	            w.write("monsterboard.nl-vacature"+","+al2.get(x) + "\r\n");
	            w.write("starapple.nl-vacature"+","+al3.get(x) + "\r\n");
	        }
	        
	        w.close();

	    } catch (IOException e) {
	        System.err.println("Problem writing to the file");
	    }
	}
	
	public void writeToolTipBarGraphData() throws SQLException {
	    try {
	        File TSVFile = new File("C:/nginx/html/dashboard/deborah/data/ToolTipBarGraphData.tsv");
	        FileOutputStream is = new FileOutputStream(TSVFile);
	        OutputStreamWriter osw = new OutputStreamWriter(is);    
	        Writer w = new BufferedWriter(osw);
	        w.write("soort"+"\t"+"aantal"+"\r\n");
	        
	        ArrayList<String> al = new ArrayList<String>();
	        String query = "SELECT COUNT(id) as count FROM cv";
	        al = doSelect(query);
	        
	        ArrayList<String> al2 = new ArrayList<String>();
	        query = "SELECT COUNT(id) as count FROM vacatures";
	        al2 = doSelect(query);

	        for (int x = 0; 1 > x; x++) {
	        	String alint = "";
	        	String al2int = "";
	        	if (al.size() <= x) {
	        		alint = "0";
	        	}
	        	else {
	        		alint = al.get(x);
	        	}
	        	if (al2.size() <= x) {
	        		alint = "0";
	        	}
	        	else {
	        		al2int = al2.get(x);
	        	}
	            w.write("CV"+"\t"+alint+"\r\n");
	            w.write("VAC"+"\t"+al2int+"\r\n");
	        }
	        
	        w.close();

	    } catch (IOException e) {
	        System.err.println("Problem writing to the file");
	    }
	}
	
	public void writeDualBarBarGraphData() throws SQLException {
	    try {
	        File TSVFile = new File("C:/nginx/html/dashboard/deborah/data//DualBarGraphData.tsv");
	        FileOutputStream is = new FileOutputStream(TSVFile);
	        OutputStreamWriter osw = new OutputStreamWriter(is);    
	        Writer w = new BufferedWriter(osw);
	        w.write("opleiding"+"\t"+"cvopleiding"+"\t"+"vacniveau"+"\r\n");
	        
	        ArrayList<String> al = new ArrayList<String>();
	        String query = "SELECT COUNT(DISTINCT cv.id) as count FROM cv, vacatures WHERE opleiding = niveau GROUP BY niveau ORDER BY niveau";
	        al = doSelect(query);
	        
	        ArrayList<String> al2 = new ArrayList<String>();
	        query = "SELECT COUNT(DISTINCT vacatures.id) as count FROM cv, vacatures WHERE opleiding = niveau GROUP BY niveau ORDER BY niveau";
	        al2 = doSelect(query);
	        
	        ArrayList<String> al3 = new ArrayList<String>();
	        query = "SELECT niveau as count FROM cv, vacatures WHERE opleiding = niveau GROUP BY niveau ORDER BY niveau";
	        al3 = doSelect(query);

	        for (int x = 0; al.size() > x; x++) {
	        	String alint = "";
	        	String al2int = "";
	        	String al3int = "";
	        	if (al.size() <= x) {
	        		alint = "0";
	        	}
	        	else {
	        		alint = al.get(x);
	        	}
	        	if (al2.size() <= x) {
	        		alint = "0";
	        	}
	        	else {
	        		al2int = al2.get(x);
	        	}
	        	if (al3.size() <= x) {
	        		al3int = "Onbekend";
	        	}
	        	else {
	        		if (al3.get(x).equals("universitair")) {
	        			al3int = "uni";
	        		}
	        		else {
	        			al3int = al3.get(x);
	        		}
	        	}
	        	
	            w.write(al3int+"\t"+alint+"\t"+al2int+"\r\n");
	        }
	        
	        w.close();

	    } catch (IOException e) {
	        System.err.println("Problem writing to the file");
	    }
	}
	
	public void writeShowReelGraphData() throws IOException, SQLException, ParseException {      
        File CSVFile = new File("C:/nginx/html/dashboard/deborah/data/ShowReelData.csv");
        FileOutputStream is = new FileOutputStream(CSVFile);
        OutputStreamWriter osw = new OutputStreamWriter(is);    
        Writer w = new BufferedWriter(osw);
        w.write("symbol"+","+"date"+","+"price"+"\r\n");
        
        ArrayList<String> al = new ArrayList<String>();
        String query = "SELECT lastcrawled as count FROM urls, cv WHERE lastcrawled IS NOT NULL AND (lower(substring(fullurl from 12 for 22)) = 'starapple.nl/kandidaat' OR lower(substring(fullurl from 12 for 23)) = 'cvenvacaturebank.nl/cv/' OR lower(substring(fullurl from 8 for 18)) = 'cv.monsterboard.nl' ) AND url = fullurl GROUP BY lastcrawled ORDER BY lastcrawled";
        al = doSelect(query);
        
        ArrayList<String> al2 = new ArrayList<String>();
        query = "SELECT COUNT(lastcrawled) as count FROM urls, cv WHERE lastcrawled IS NOT NULL AND (lower(substring(fullurl from 12 for 22)) = 'starapple.nl/kandidaat' OR lower(substring(fullurl from 12 for 23)) = 'cvenvacaturebank.nl/cv/' OR lower(substring(fullurl from 8 for 18)) = 'cv.monsterboard.nl' ) AND url = fullurl GROUP BY lastcrawled ORDER BY lastcrawled";
        al2 = doSelect(query);
        
        ArrayList<String> al3 = new ArrayList<String>();
        query = "SELECT lastcrawled as count FROM urls, vacatures WHERE lastcrawled IS NOT NULL AND (lower(substring(fullurl from 12 for 21)) = 'starapple.nl/vacature' OR lower(substring(fullurl from 8 for 24)) = 'vacature.monsterboard.nl' OR lower(substring(fullurl from 12 for 29)) = 'cvenvacaturebank.nl/vacature/' ) AND url = fullurl GROUP BY lastcrawled ORDER BY lastcrawled";
        al3 = doSelect(query);
        
        ArrayList<String> al4 = new ArrayList<String>();
        query = "SELECT COUNT(lastcrawled) as count FROM urls, vacatures WHERE lastcrawled IS NOT NULL AND (lower(substring(fullurl from 12 for 21)) = 'starapple.nl/vacature' OR lower(substring(fullurl from 8 for 24)) = 'vacature.monsterboard.nl' OR lower(substring(fullurl from 12 for 29)) = 'cvenvacaturebank.nl/vacature/' ) AND url = fullurl GROUP BY lastcrawled ORDER BY lastcrawled";
        al4 = doSelect(query);
        
        ArrayList<String> al7 = new ArrayList<String>();
        query = "SELECT lastcrawled as count FROM urls, cv WHERE lastcrawled IS NOT NULL AND ((lower(substring(fullurl from 12 for 22)) = 'starapple.nl/kandidaat' OR lower(substring(fullurl from 12 for 23)) = 'cvenvacaturebank.nl/cv/' OR lower(substring(fullurl from 8 for 18)) = 'cv.monsterboard.nl' ) OR (lower(substring(fullurl from 12 for 22)) = 'starapple.nl/kandidaat' OR lower(substring(fullurl from 12 for 23)) = 'cvenvacaturebank.nl/cv/' OR (lower(substring(fullurl from 8 for 18)) = 'cv.monsterboard.nl' ))) AND url = fullurl GROUP BY lastcrawled ORDER BY lastcrawled";
        al7 = doSelect(query);
        
    	int y = 0;
    	String z = "0";    
    	boolean found = false;
        
        for (int x = 0; al7.size() > x; x++) {
        		for (y = 0; al.size() > y; y++) {
        		if (al7.get(x).equals(al.get(y))) {
        			w.write("CV"+","+al7.get(x)+","+al2.get(y)+"\r\n");
        			z = al2.get(y);
        			found = true;
        		}
        		else {
        			if (y == (al.size()-1) && found == false) {
        				w.write("CV"+","+al7.get(x)+","+z+"\r\n");
        			}
        		}
        	}
        	found = false;
        }
        
        y = 0;
        for (int x = 0; al7.size() > x; x++) {
    		for (y = 0; al3.size() > y; y++) {
    			if (al7.get(x).equals(al3.get(y))) {
    				w.write("VAC"+","+al7.get(x)+","+al4.get(y)+"\r\n");
    				z = al4.get(y);
    				found = true;
    			}
    			else {
    				if (y == (al3.size()-1) && found == false) {
    					w.write("VAC"+","+al7.get(x)+","+z+"\r\n");
    				}
    			}
    		}
    	found = false;
        }
        w.close();
	}
}

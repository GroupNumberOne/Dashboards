package dashboard.java;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

public class FileWriter {
	
	String directory = "C:/nginx/html/dashboard/pepijn";
	
	public void writePieData(ArrayList<String> list1, ArrayList<String> list2) throws IOException{
		Writer out = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(directory + "/piechart/data.csv"), "UTF-8"));
		
		out.write("index,values");
		for (Integer i = 0; i < list1.size(); i++) {
			if(list1.get(i)!=null){
				if(list1.get(i).equals("t")){
					out.write("\r\nWel rijbewijs ("+list2.get(i)+"),"+list2.get(i));
				}
				else {
					out.write("\r\nGeen rijbewijs ("+list2.get(i)+"),"+list2.get(i));
				}
			}
		}
		out.close();
	}
	
	public void writePieData2(ArrayList<String> list1, ArrayList<String> list2) throws IOException{
		Writer out = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(directory + "/piechart2/piechart2.csv"), "UTF-8"));
		
		out.write("index,values");
		out.write("\r\nAantal cv's ("+list1.get(0)+"),"+list1.get(0));
		out.write("\r\nAantal vacatures ("+list2.get(0)+"),"+list2.get(0));

		out.close();
	}
	
	public void writeSequenceGraphData(ArrayList<String> list1, ArrayList<String> list2, ArrayList<String> list3, ArrayList<String> list4, ArrayList<String> list5, ArrayList<String> list6) throws IOException{
		Writer out = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(directory + "/sequences/SequenceGraphData.csv"), "UTF-8"));

		out.write("cvenvacaturebank.nl-cv,"+list1.get(0));
		out.write("\r\nstarapple.nl-cv,"+list2.get(0));
		out.write("\r\nmonsterboard.nl-cv,"+list3.get(0));
		out.write("\r\ncvenvacaturebank.nl-vacature,"+list4.get(0));
		out.write("\r\nstarapple.nl-vacature,"+list5.get(0));
		out.write("\r\nmonsterboard.nl-vacature,"+list6.get(0));
		out.close();
	}
	
	
	public void writeAreaChartData(ArrayList<String> list1, ArrayList<String> list2) throws IOException{
		Writer out = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(directory + "/areachart/data.tsv"), "UTF-8"));
		
		out.write("date	close");
		for (Integer i = 0; i < list1.size(); i++){
			if (list1.get(i)!=null){
				out.write("\r\n"+list1.get(i)+"	"+list2.get(i));
			}
		}

		out.close();
	}
	
	public void writeDonutGraphData(ArrayList<String> list1, ArrayList<String> list2, ArrayList<String> list3, ArrayList<String> list4) throws IOException{
		Writer out = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(directory + "/donutgraph/data.tsv"), "UTF-8"));
		out.write("cvopldata	vacopldata	cvoplnaam	vacoplnaam");
		for (Integer i = 0; i < list1.size()&&i<list2.size(); i++) {
			if (i < list2.size()){
				if (!list2.get(i).equals("0")){
					out.write("\r\n"+list2.get(i) + "	");
				}
				else {
					out.write("\r\n	");
				}
			}
			else {
				out.write("\r\n	");
			}
			if (i < list4.size()){
				if (!list4.get(i).equals("0")){
					out.write(list4.get(i) + "	");
				}
				else {
					out.write("	");
				}
			}
			else {
				out.write("	");
			}
			if (i < list1.size()){
				if (list1.get(i)!=null){
					out.write(list1.get(i).trim() + "	");
				}
				else {
					out.write("	");
				}
			}
			if (i < list3.size()){
				if (list3.get(i)!=null){
					out.write(list3.get(i).trim() + "	");
				}
				else {
					out.write("	");
				}
			}
		}
		out.close();
	}
	
	public void writeSortableBarData(ArrayList<String> list1, ArrayList<String> list2) throws IOException{
		Writer out = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(directory + "/sortablebar/data.tsv"), "UTF-8"));

		out.write("letter	frequency");
		
		for (Integer i = 0; i < 10; i++){
			if (list2.get(i)!=null){
				out.write("\r\n"+list2.get(i).trim()+"	"+list1.get(i));
			}
		}
		
		out.close();
	}
}
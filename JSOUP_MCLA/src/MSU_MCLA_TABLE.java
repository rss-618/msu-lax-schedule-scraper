import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.safety.Whitelist;

public class MSU_MCLA_TABLE{
	private static Elements table_headers = new Elements();
	private static Elements table_data = new Elements();
	
	public MSU_MCLA_TABLE() throws IOException {}
	public MSU_MCLA_TABLE(String year) throws IOException {
		setYear(year);
	}
	
	public void setYear(String year) throws IOException {
		/*
		 * Puts together Missouri State MCLA URL 
		 * Grabs the raw schedule table data
		 * Seperate the table data into:
		 * 	-table_headers
		 *  -table_data = a's and span's (3:1) ratio (Every 3 a's there is a 'span' behind it)
		 * return: void
		 */
		
		//puts MCLA URL together
		String url = "http://mcla.us/team/missouri_state/" + year + "/schedule.html";
		Document doc = Jsoup.connect(url).get();
		Element ele = doc.select("table").first();
	
		//th values for the table
		table_headers = ele.select("th");
		
		//rough table data
		table_data = ele.select("td"); 
		
		//The MCLA sequence of elements go a,a,a,span
		Elements table_a = table_data.select("a");//Opponent, Location, Date
		table_a.removeAttr("href"); //gets rid of unusable tags
		Elements table_span = table_data.select("td:not(:has(a))");; //Results
		
		//Since table data is extracted and in own respective locations .clear()
		table_data.clear();
		
		//Format the table data to be in the correct order
		for(int i = 1,a = 0,span = 0;i <= table_a.size() + table_span.size(); ++i) {
			if(i % 4 == 0) {
				table_data.add(table_span.get(span++));
			}
			else {
				table_data.add(table_a.get(a++));
			}
		}
	}
	
	public void consolelog() throws IOException {
		for(Element a: table_headers) {
			System.out.println(a);
		}
		for(Element a: table_data) {
			System.out.println(a);
		}
	}
	
	public ArrayList<String> getTableHeader() throws IOException{
		/*
		 * Returns Table Headers (4 expected)
		 */
		//removes the html tags and returns raw data in an array
		ArrayList<String> th = new ArrayList<String>();
		for(Element i: table_headers) {
			String cleaned = Jsoup.clean(i.toString(), Whitelist.none());
			th.add(cleaned);
		}
		return th;
	}
	public ArrayList<String> getTableData() throws IOException{
		//removes the html tags and returns raw data in an array
		ArrayList<String> td = new ArrayList<String>();
		for(Element i: table_data) {
			String cleaned = Jsoup.clean(i.toString(), Whitelist.none());
			td.add(cleaned);
		}
		return td;
	}
	public ArrayList<ArrayList<String>> getRows() throws IOException{
		/*
		 * Organizes the table as it would be in a two-dimensional array
		 * returns arr[][];
		 */
		ArrayList<ArrayList<String>> rows = new ArrayList<ArrayList<String>>();
		ArrayList<String> th = this.getTableHeader();
		int colLen = th.size();
		rows.add(th);
		ArrayList<String> data = this.getTableData();
		for(int i = 0;i + colLen - 1< data.size(); i += colLen) {
			ArrayList<String> temp = new ArrayList<String>();
			for(int j=i; j < colLen+i; ++j) {
				temp.add(data.get(j));
			}
			rows.add(temp);
		}
		return rows;
	}
}

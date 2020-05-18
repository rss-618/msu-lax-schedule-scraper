import java.io.IOException;
import java.util.ArrayList;

public class MCLA_MISSOURISTATE_TEST {
	
	public static void test() throws IOException{
		MSU_MCLA_TABLE test1 = new MSU_MCLA_TABLE("2020");
		test1.consolelog();
		ArrayList<String> data1 = test1.getTableData(); 
		for(String i: data1) {
			System.out.println(i);
		}
		MSU_MCLA_TABLE test2 = new MSU_MCLA_TABLE();
		test2.setYear("2019");
		test2.consolelog();
		ArrayList<String> data2 = test2.getTableData(); 
		for(String i: data2) {
			System.out.println(i);
		}
		System.out.println(test2.getRows());
	}

}

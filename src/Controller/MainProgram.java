package Controller;

import java.sql.ResultSet;

import Util.DBConnector;
import Util.InputReader;

public class MainProgram {
	private static InputReader reader;
	private static DBConnector c;

	public MainProgram() {
		c = new DBConnector();
		reader = new InputReader();

		putShowToDBTest();
	}

	public void putShowToDBTest() {
		c.connect();
		System.out.println("Insert PerformanceID here");
		int PerformanceID = reader.getNumber("");
		System.out.println("PerformanceName");
		String PerformanceName = reader.getText("");
		System.out.println("Insert PerformanceDescription");
		String PerformanceDescription = reader.getText("");
		System.out.println("Insert PerformanceDuration here");
		int PerformanceDuration = reader.getNumber("");
		System.out.println("Insert PerformanceTicketPrice");
		int PerformanceTicketPrice = reader.getNumber("");
		System.out.println("Insert PerformanceDate");
		int PerformanceDate = reader.getNumber("");
		System.out.println("insert PerformanceTime");
		String PerformanceTime = reader.getText("");
		System.out.println("insert number of SeatsStall");
		int SeatsStall = reader.getNumber("");
		System.out.println("insert number of SeatsCircle");
		int SeatsCircle = reader.getNumber("");
		System.out.println("insert PerformanceLanguage");
		String PerformanceLanguage = reader.getText("");

		String sql = "INSERT INTO Performance (PerformanceID, PerformanceName, PerformanceDescription, PerformanceDuration, PerformanceTicketPrice, PerformanceDate, PerformanceTime, SeatsStall, SeatsCircle, PerformanceLanguage)"
				+ "VALUES (";
		sql = sql+PerformanceID;//PerformanceID
		sql = sql + ", ";
		sql = sql+PerformanceName;	//PerformanceName
		sql = sql + ", ";
		sql = sql+PerformanceDescription;	//PerformanceDescription
		sql = sql + ", ";
		sql = sql+PerformanceDuration; 		//PerformanceDuration
		sql = sql + ", ";
		sql = sql+PerformanceTicketPrice; //PerformanceTicketPrice
		sql = sql + ", ";
		sql = sql+PerformanceDate;		//PerformanceDate
		sql = sql + ", ";
		sql = sql+PerformanceTime;		//PerformanceTime
		sql = sql + ", ";
		sql = sql+SeatsStall; 		//SeatsStall
		sql = sql + ", ";
		sql = sql+SeatsCircle; //SeatsCircle
		sql = sql + ", ";
		sql = sql+PerformanceLanguage;//PerformanceLanguage
		sql = sql + ",)";
		ResultSet result = c.runQuery(sql);
		c.printResult(result);
		c.close();
		
	}

}

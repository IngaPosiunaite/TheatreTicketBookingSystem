package Util;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DBConnector {
	private Connection conn;

	public DBConnector() {
		conn = null;
	}

	public void connect() {
		
		try {
			Scanner s = new Scanner(new File("credentials.txt"));
			String uname = s.nextLine();
			String pwd = s.nextLine();
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/theTheatreRoyal", uname, pwd);
		} catch (IOException e) {
			System.out.println("File error.");
			e.printStackTrace();
			return;
		} catch (SQLException e) {
			System.out.println("Connection failed.");
			e.printStackTrace();
			return;
		}

		if (conn != null) {
			//System.out.println("Connection established."); //too annoying, hidden
		} else {
			System.out.println("Connection null still.");
		}
	}

	// a method to run any SQL query to run

	
	public ResultSet runQuery(String sql) {
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql,
					ResultSet.TYPE_SCROLL_SENSITIVE, // allows us to move forward and back in the ResultSet
                    ResultSet.CONCUR_UPDATABLE);
			pst.execute();
			ResultSet results = pst.getResultSet();
			if (results != null) {
				int rowcount = 0;
				if (results.last()) {
					rowcount = results.getRow();
					results.beforeFirst(); // not rs.first() because the rs.next() below will move on, missing the first
											// element
				}
			}
				
			return results;
			} catch (SQLException e) {
			System.out.println(sql + "\n failed to run.");
			e.printStackTrace();
			return null;
		}}
	
	// method to run SQL select queries
	
	public ResultSet runSqlQuery(String sqlQuery) {
		ResultSet rs = null;
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			if(sqlQuery.contains("SELECT")) {
				rs = stmt.executeQuery(sqlQuery);
			}
			else {
				stmt.execute(sqlQuery);
			}
		} catch (SQLException e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
		return rs;
	} 
	
	// get all the attributes in the listed columns and print them out.
	public void printQueryOutput(ResultSet rs){
		try {
			while(rs.next()){
				System.out.println(rs.getInt("PerformanceID") + " "
			//			+ rs.getString("PerformanceName") + " "
		//				+ rs.getString("type") + " "
		//				+ rs.getString("description") + " "
		//				+ rs.getInt("length") + " "
		//				+ rs.getString("time") + " "
		//				+ rs.getString("date") + " "
		//				+ rs.getInt("stallPrice") + " "
		//				+ rs.getInt("circlePrice") + " "
		//				+ rs.getString("language") + " "
		//				+ rs.getString("liveMusic")
						+ rs.getString("PerformanceID") + " "
						+ rs.getString("PerformanceName") + " "
						+ rs.getString("PerformanceDescription") + " "
						+ rs.getString("PerformanceDuration") + " "
						+ rs.getString("PerformanceTicketPrice") + " "
						+ rs.getString("PerformanceDate") + " "
						+ rs.getString("PerformanceTime")	 + " "
						+ rs.getString("SeatsStall")	 + " "
						+ rs.getString("SeatsCircle")	 + " "
						+ rs.getString("PerformanceLanguage")	 + " "
						
						
						);
			}
		} catch (SQLException e) {
			System.out.println("Query output failed to print properly.");
			e.printStackTrace();
		}
	}	

	
	// currently this method returns a list of the column names
	public void printResult(ResultSet rs) {
		try {
			// while there is another row
			//Retrieving the ResultSetMetadata object
		      ResultSetMetaData rsMetaData = rs.getMetaData();
		      System.out.println("List of column names in the current table: ");
		      //Retrieving the list of column names
		      int count = rsMetaData.getColumnCount();
		      for(int i = 1; i<=count; i++) {
		         System.out.println(rsMetaData.getColumnName(i));
		      }
		      for (int i = 1; i <= count; i++) {
					
					String ColumnValue = rs.getString(i);
				
					System.out.println(rsMetaData.getColumnName(i) + ColumnValue );
					;
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	// close the connection to the database. 
	public void close() {
		try {
			conn.close();
			// System.out.println("Connection closed."); //too annoying, hiden
		} catch (SQLException e) {
			System.out.println("Connection not closed.");
			e.printStackTrace();
		}
	}
}

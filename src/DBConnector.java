package theatre;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class DBConnector {

	private Connection conn;

	public DBConnector() {
		conn = null;
	}

	public void connect() {

		try {
			Scanner s = new Scanner(new File("Credentials.txt"));
			String uname = s.nextLine();
			String pwd = s.nextLine();
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Theatre", uname, pwd);
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
			System.out.println("Connection established.");
		} else {
			System.out.println("Connection null still.");
		}
	}

	public ResultSet runQuery(String sql) {
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			pst.execute();
			ResultSet results = pst.getResultSet();
			if (results != null) {
				int rowcount = 0;
				if (results.last()) {
					rowcount = results.getRow();
					results.beforeFirst();
				}
				System.out.println(sql + "\n Success.  Result set has " + rowcount + " rows");
			} else {
				System.out.println(sql + "\n Success.  No results returned");
			}
			return results;
		} catch (SQLException e) {
			System.out.println(sql + "\n failed to run.");
			e.printStackTrace();
			return null;
		}

	}

	public void printResultStart(ResultSet rs) {
		try {
			while (rs.next()) {
				System.out.print("Performance ID " + rs.getInt("performance_id") + " ");
				System.out.print("Show Name " + rs.getString("show_name") + " ");
				System.out.print("Performance Date " + rs.getDate("performance_date") + " ");
				System.out.println("Performance Time " + rs.getTime("start_time"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void printResultBrowse(ResultSet rs) {
		try {
			while (rs.next()) {
				System.out.print("Show Name " + rs.getString("show_name") + " ");
				System.out.print("Show Type " + rs.getString("show_type") + " ");
				System.out.print("Show Duration " + rs.getString("show_duration") + " ");
				System.out.print("Show Description " + rs.getString("show_description") + " ");
				System.out.print("Show Language " + rs.getString("show_language") + " ");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int printResultSetPriceDouble(ResultSet rs) {
		int price = 0;
		try {
			while (rs.next()) {
				price = rs.getInt("price");
				return price;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return price;
	}

	public int printResultStandardPrice(ResultSet rs) {
		int price = 0;
		try {
			while (rs.next()) {
				System.out.print("Show Price " + rs.getFloat("price") + " ");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return price;
	}

	public void printResultConcessionPrice(ResultSet rs) {
		try {
			while (rs.next()) {
				System.out.print("Show Price (Concession) " + rs.getFloat("price_concession") + " ");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void printResultSearch(ResultSet rs) {
		try {
			while (rs.next()) {
				System.out.print("\nShow Name " + rs.getString("show_name") + " ");
				System.out.print("Start Time " + rs.getTime("start_time") + " ");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			conn.close();
			System.out.println("Connection closed.");
		} catch (SQLException e) {
			System.out.println("Connection not closed.");
			e.printStackTrace();
		}
	}
}

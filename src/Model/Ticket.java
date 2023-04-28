package Model;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import Util.DBConnector;

public class Ticket {

	protected DBConnector db = new DBConnector();
	private String ticketNumber;
	private int TicketID;
	private String titleOfShow;
	private float price;
	private boolean isDiscount = false;
	private String reasonOfDiscount = "N/A";
	private Date dateOfShow;
	private String ShowID;
	private boolean isCircleSeat = false;
	private int bookingID;
	private String typeOfShow;
	private boolean isByPost = false;

	public Ticket(String ticketNumber, String ShowID, float price, boolean isCircle, String titleOfShow,
			String typeOfShow) throws SQLException {
		this.ticketNumber = ticketNumber;
		this.ShowID = ShowID;
		this.price = price;
		this.isCircleSeat = isCircle;
		this.titleOfShow = titleOfShow;
		this.typeOfShow = typeOfShow;
		sendTicketToDB();
		updateDetailsfromDB();

	}

	public Ticket(String ShowID) {
		this.ShowID = ShowID;
		// db.connect();

	}

	public Ticket(int TicketID) throws SQLException {
		this.TicketID = TicketID;
		// System.out.println("Ticket create with id :" + TicketID);
		getTicketDetailsByTicketID(TicketID);

	}

	public String getTicketNumber() {
		return ticketNumber;
	}

	public boolean getIsCircleSeat() {
		return isCircleSeat;
	}

	public float getPrice() {
		return price;
	}

	public void updatePrice()// for discount ticket
	{
		float disCountPrice = price * 4 / 3;
		price = disCountPrice;
	}

	public void setDiscount(boolean inputDiscount) {
		isDiscount = inputDiscount;
	}

	public void setReasonOfDiscount(String input) {
		reasonOfDiscount = input;
	}

	public String getTitleOfShow() {
		return titleOfShow;
	}

	public Date getDate() {
		return dateOfShow;
	}

	public void setBookingID(int inputID) {
		bookingID = inputID;
	}

	public void updateTicketToDB() {
		// to be done
		db.connect();
	}

	public boolean getIsDiscount() {
		return isDiscount;
	}

	public void getTicketDetailsByTicketID(int input_ticketID) throws SQLException // db.connecting
	{
		db.connect();
		int ID = input_ticketID;
		String sql = "SELECT * FROM tickets Where TicketID = " + ID + ";";
		ResultSet rs = db.runQuery(sql);
		while (rs.next()) {
			ticketNumber = rs.getString("TicketNumber");
			// TicketID = rs.getInt("TicketID");
			titleOfShow = rs.getString("TitleOfShow");
			price = rs.getFloat("price");
			isDiscount = rs.getBoolean("isDiscount");
			ShowID = rs.getString("ShowID");
			isCircleSeat = rs.getBoolean("isCircleSeat");
			typeOfShow = rs.getString("TypeOfShow");
		}
		String sql_getDate = "Select date from performance Where showID = " + ShowID + ";";
		ResultSet rs1 = db.runQuery(sql_getDate);
		while (rs1.next()) {
			dateOfShow = rs1.getDate("date");
		}

		db.close();
	}

	private void updateDetailsfromDB() throws SQLException {
		db.connect();

		String sql_getDetailsFromDB = "SELECT MAX(TicketID) FROM  thetheatreroyal.tickets";
		ResultSet rs_getID = db.runQuery(sql_getDetailsFromDB);
		while (rs_getID.next()) {
			TicketID = rs_getID.getInt("MAX(TicketID)");
		}
		db.close();
	}

	public int getTicketID() {
		return TicketID;

	}

	private void sendTicketToDB() {
		db.connect();
		System.out.println("sending ticket details to DB server...");

		// for (Ticket t : tickets) {
		String sql = "INSERT INTO tickets (TicketNumber, TitleOfShow, ShowID, TypeOfShow, isCircleSeat, price, isDiscount, isSold, BookingID)"
				+ "VALUES ('";
		sql = sql + ticketNumber;
		sql = sql + "', '";
		sql = sql + titleOfShow;
		sql = sql + "', ";
		sql = sql + ShowID;
		sql = sql + ", '";
		sql = sql + typeOfShow;
		sql = sql + "', ";
		sql = sql + isCircleSeat;
		sql = sql + ",";
		sql = sql + price;
		sql = sql + ",";
		sql = sql + false;
		sql = sql + ",";
		sql = sql + false;
		sql = sql + ",";
		int bookingID = 0; // assign 0 to the bookingID at the moment, should have some method to get the
							// correct ID
		sql = sql + bookingID;// this slot for bookingID
		sql = sql + ")";
		db.runQuery(sql);
		db.close();
	}

	public void setByPost(boolean input_isByPost) {
		isByPost = input_isByPost;
	}

	public void updateSold() throws SQLException {
		db.connect();
		int lastBookingID = 0;
		int thisBookingID = 0;
		String sql_getBookingID = "SELECT  MAX(BookingID) From tickets WHERE BookingID > 0;";
		ResultSet rs = db.runQuery(sql_getBookingID);
		while (rs.next()) {
			lastBookingID = rs.getInt("MAX(BookingID)");
		}
		String sql_getLastAvailableID = "SELECT  MIN(TicketID) From tickets Where isCircleSeat = " + isCircleSeat
				+ " AND SHOWID = '" + ShowID + "' AND isSold = false";
		ResultSet rs1 = db.runQuery(sql_getLastAvailableID);
		while (rs1.next()) {
			TicketID = rs1.getInt("MIN(TicketID)"); // overwrite the TicketID from DB
		}
		thisBookingID = lastBookingID + 1;
		// run this qury in DB to update
		String sql_AssignBookingId = "UPDATE tickets set BookingID = " + thisBookingID + " where TicketID = " + TicketID
				+ ";";
		String sql_updateIsSold = "UPDATE tickets set isSold = TRUE where  TicketID = " + TicketID + ";";
		String sql_updateByPost = "UPDATE tickets set byPost = " + isByPost + " where  TicketID = " + TicketID + ";";
		db.runQuery(sql_AssignBookingId);
		db.runQuery(sql_updateIsSold);
		db.runQuery(sql_updateByPost);

		db.close();
	}
}

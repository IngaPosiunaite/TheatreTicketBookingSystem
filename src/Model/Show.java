package Model;

import Util.DBConnector;
import Util.InputReader;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public abstract class Show {

	protected ArrayList<Ticket> tickets = new ArrayList<>();
	protected DBConnector db = new DBConnector();
	protected InputReader reader = new InputReader();
	protected String showDate; 
	protected String showTime; 
	// protected PerformanceID;
	protected String PerformanceName;
	protected String ShowID;
	protected String PerformanceDescription;
	protected int showDuration; 
	protected int SeatsCircle;
	protected int SeatsStall;
	protected boolean isMusicalAccompaniment = false;
	protected String informationOfPerformers;
	protected int PerformanceDate;// in dd-mm-yyyy format
	protected String PerformanceTime;
	protected String titleOfShow = null;
	protected String description;
	// protected PerformanceTicketPrice;
	protected float stallPrice; // without discount
	protected float circlePrice; // without discount
	protected String language = "English"; // English is the default language
	protected String typeOfShow = null;
	protected String liveMusic = null;

	public Show(String titleOfShow, String typeOfShow, int showDuration, String description, int SeatsStall,
			int SeatsCircle, float stallPrice, float circlePrice, String showDate, String showTime)
			throws SQLException {
		this.titleOfShow = titleOfShow;
		this.typeOfShow = typeOfShow;
		this.showDuration = showDuration;
		this.description = description;
		this.SeatsStall = SeatsStall;
		this.SeatsCircle = SeatsCircle;
		this.stallPrice = stallPrice;
		this.circlePrice = circlePrice;
		this.showDate = showDate;
		this.showTime = showTime;

		sendShowsToDB();
		setShowID();
		ticketCreate(SeatsCircle, SeatsStall, stallPrice, circlePrice);
	}

	// for show with liveMusic
	public Show(String titleOfShow, String typeOfShow, int showDuration, String description, int SeatsStall,
			int SeatsCircle, float stallPrice, float circlePrice, String showDate, String showTime, String liveMusic)
			throws SQLException {
		this.titleOfShow = titleOfShow;
		this.typeOfShow = typeOfShow;
		this.showDuration = showDuration;
		this.description = description;
		this.SeatsStall = SeatsStall;
		this.SeatsCircle = SeatsCircle;
		this.stallPrice = stallPrice;
		this.circlePrice = circlePrice;
		this.showDate = showDate;
		this.showTime = showTime;
		this.liveMusic = liveMusic;

		sendShowsToDB();
		setShowID();
		ticketCreate(SeatsCircle, SeatsStall, stallPrice, circlePrice);
		// sendTicketToDB();
	}

	public String getLanguage() {
		return language;
	}

	private void setShowID() {
		try {
			ShowID = getShowIDFromDB();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void sendShowsToDB() {
		db.connect();
		System.out.println("uploading show details to DB server...");

		String sql = "INSERT INTO performance (name, type, description, length, time, date, stallPrice, circlePrice, language, liveMusic, stallSeat, circleSeat)"
				+ "VALUES ('";

		sql = sql + titleOfShow; //
		sql = sql + "', '";
		sql = sql + typeOfShow; //
		sql = sql + "', '";
		sql = sql + description; //
		sql = sql + "', ";
		sql = sql + showDuration; //
		sql = sql + ", '";
		sql = sql + showTime; // time in VARCHAR
		sql = sql + "', '";

		sql = sql + (java.sql.Date.valueOf(showDate)); // date in DATE(SQL) //must be input as yyyy-MM-dd
		sql = sql + "', ";
		sql = sql + stallPrice; // stallPrice
		sql = sql + ", ";
		sql = sql + circlePrice; // stallPrice
		sql = sql + ", '";
		sql = sql + language;// PerformanceLanguage
		sql = sql + "', '";
		sql = sql + liveMusic;// PerformanceLanguage
		sql = sql + "',";
		sql = sql + SeatsStall;
		sql = sql + ",";
		sql = sql + SeatsCircle;
		sql = sql + ")";
		db.runQuery(sql);
		db.close();

	}

	void ticketCreate(int numberOfCircleSeats, int numberOfStallSeats, float stallPrice, float circlePrice)
			throws SQLException {
		for (int i = 0; i < numberOfStallSeats; i++) {

			String currentTicketNumber = "CircleSeat#" + i;
			System.out.println(currentTicketNumber);
			Ticket currentTicket = new Ticket(currentTicketNumber, ShowID, circlePrice, true, titleOfShow, typeOfShow);
			tickets.add(currentTicket);
			System.out.println(currentTicket.getTicketID());
		}
		for (int i = 0; i < numberOfCircleSeats; i++) {
			String currentTicketNumber = "StallSeat#" + i;
			Ticket currentTicket = new Ticket(currentTicketNumber, ShowID, stallPrice, false, titleOfShow, typeOfShow);
			tickets.add(currentTicket);
			System.out.println(currentTicket.getTicketID());

		}

	}

	private String getShowIDFromDB() throws SQLException {
		String IdToReturn = "";
		db.connect();
		String sql = "SELECT MAX(showID) FROM performance";
		ResultSet rs = db.runQuery(sql);
		if (rs.next()) {
			IdToReturn = rs.getString(1);
			System.out.println(IdToReturn);
		}
		db.close();
		return IdToReturn;
	}

}

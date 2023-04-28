package Model;

import java.sql.SQLException;

public class Concert extends Show {

	public Concert(String titleOfShow, String typeOfShow, int showDuration, String description, int SeatsStall,
			int SeatsCircle, float stallPrice, float circlePrice, String showDate, String showTime, String liveMusic) throws SQLException {
		super(titleOfShow, typeOfShow, showDuration, description, SeatsStall, SeatsCircle, stallPrice, circlePrice,
				showDate, showTime, liveMusic);
		language = null; // concert has no language
		liveMusic = "yes"; // concert always has live Music

		
	}

}

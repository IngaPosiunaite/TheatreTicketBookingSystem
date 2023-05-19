package Model;

import java.sql.SQLException;

public class Theatre extends Show implements Language {

	public Theatre(String titleOfShow, String typeOfShow, int showDuration, String description, int SeatsStall,
			int SeatsCircle, float stallPrice, float circlePrice, String showDate, String showTime)
			throws SQLException {
		super(titleOfShow, typeOfShow, showDuration, description, SeatsStall, SeatsCircle, stallPrice, circlePrice,
				showDate, showTime);
		typeOfShow = "Theatre";
		liveMusic = null;

	}

	@Override
	public void setLanguage(String inputLanguage) {
		language = inputLanguage;
	}

}

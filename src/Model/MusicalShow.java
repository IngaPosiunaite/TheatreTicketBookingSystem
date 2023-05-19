package Model;

import java.sql.SQLException;

public class MusicalShow extends Show implements Language, MusicalAccompaniment {

	public MusicalShow(String titleOfShow, String typeOfShow, int showDuration, String description, int SeatsStall,
			int SeatsCircle, float stallPrice, float circlePrice, String showDate, String showTime)
			throws SQLException {
		super(titleOfShow, typeOfShow, showDuration, description, SeatsStall, SeatsCircle, stallPrice, circlePrice,
				showDate, showTime);
		typeOfShow = "Music Show";

	}

	public MusicalShow(String titleOfShow, String typeOfShow, int showDuration, String description, int SeatsStall,
			int SeatsCircle, float stallPrice, float circlePrice, String showDate, String showTime, String liveMusic)
			throws SQLException {
		super(titleOfShow, typeOfShow, showDuration, description, SeatsStall, SeatsCircle, stallPrice, circlePrice,
				showDate, showTime, liveMusic);
		typeOfShow = "Music Show";

	}

	@Override
	public void showPrinciplePerformance() {

	}

	@Override
	public void setLanguage(String inputLanguage) {
		language = inputLanguage;

	}

	@Override
	public void setMusicalAccompaniment(String inputIsMusicalAccompaniment) {
		liveMusic = inputIsMusicalAccompaniment;

	}

}

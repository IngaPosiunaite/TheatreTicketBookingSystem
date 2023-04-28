package Model;

import java.sql.SQLException;

public class Opera extends Show implements Language, MusicalAccompaniment {

	public Opera(String titleOfShow, String typeOfShow, int showDuration, String description, int SeatsStall,
			int SeatsCircle, float stallPrice, float circlePrice, String showDate, String showTime) throws SQLException {
		super(titleOfShow, typeOfShow, showDuration, description, SeatsStall, SeatsCircle, stallPrice, circlePrice,
				showDate, showTime);
		typeOfShow = "Opera";
		
	}

	public Opera(String titleOfShow, String typeOfShow, int showDuration, String description, int SeatsStall,
			int SeatsCircle, float stallPrice, float circlePrice, String showDate, String showTime, String liveMusic) throws SQLException {
		super(titleOfShow, typeOfShow, showDuration, description, SeatsStall, SeatsCircle, stallPrice, circlePrice,
				showDate, showTime, liveMusic);
		typeOfShow = "Opera";
		
	}
	@Override
	public void setMusicalAccompaniment(String inputIsMusicalAccompaniment) {
		liveMusic = inputIsMusicalAccompaniment;

	}

	@Override
	public void showPrinciplePerformance() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setLanguage(String inputLanguage) {
		language = inputLanguage;

	}

}

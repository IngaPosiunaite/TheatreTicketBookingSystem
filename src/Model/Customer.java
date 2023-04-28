package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.LowerCase;

import Util.DBConnector;
import Util.InputReader;

public class Customer {
	private boolean isLogin = false;
	private String name;
	private String email;
	private String password;
	private String address;
	public DBConnector db = new DBConnector();
	private String en_CN; //encrypted card details
	public InputReader reader = new InputReader();
	private boolean loginSuccess = false;
	private int CustomerID;
	final String secretKey = "thetheatreroyal";
	private boolean isEmployee = false;

	public Customer() throws SQLException {
		// loginOrRegister();
		// printWelcome();
	}

	public void loginOrRegister() throws SQLException {
		while (loginSuccess == false) {
			System.out.println("do you have an account? y/n");
			String inputYN = (reader.getText("")).toLowerCase();
			if (("y").equals(inputYN)) // has account
			{
				login();
			} else {
				newUserRegister();
			}
		}
	}

	public void newUserRegister() throws SQLException {
		boolean setupFinish = false;
		while (setupFinish == false) {
			System.out.println("Please insert your name");
			String input_name = reader.getText("");
			setName(input_name);
			boolean isEmailTaken = true;
			String input_email = "";
			while (isEmailTaken == true) {
				System.out.println("Please insert your email, it would be your login account");
				System.out.println("If you want to login, please insert login");
				System.out.println();
				input_email = reader.getText("");
				if ((input_email.toLowerCase()).equals("login")) {
					break;
				}
				isEmailTaken = checkIfEmailTaken(input_email);
			}
			if (isEmailTaken == true) {
				System.out.println("this email was registered, please use another email");
				System.out.println();
				break;
			} else {
				setEmail(input_email);
				System.out.println("Please insert your login password");
				String input_password = reader.getText("");
				setPassword(input_password);
				System.out.println("Please insert your address");
				String input_address = reader.getText("");
				setAddress(input_address);
				System.out.println("Please insert your card Number, it will be encrypted");
				String input_cardNo = reader.getText("");
				String temp_card = input_cardNo;
				setCard(encryptoCard(temp_card));
				sendDetailToDataBase();
				setupFinish = true;
				loginSuccess = true;
				isLogin = true;
				System.out.println("Your account has been created successfully");
			}
		}
	}

	private boolean checkIfEmailTaken(String input_email) throws SQLException {
		db.connect();
		String sql = "SELECT * from customer WHERE email = '" + input_email + "';";
		ResultSet rs = db.runQuery(sql);
		if (rs.next()) { // email taken
			db.close();
			return true;
		} else {
			return false;
		}
	}

	private void printWelcome() {
		System.out.println("Welcome " + name);
	}

	public String getName() {
		return name;
	}

	public String getemail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getaddress() {
		return address;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setCard(String card) {
		en_CN = card;
	}

	public boolean getIsEmployee() {
		return isEmployee;
	}

	public boolean getIsLogin() {
		return isLogin;
	}

	public String encryptoCard(String temp_card) {
		String encryptedCard = AES.encrypt(temp_card, secretKey);
		en_CN = encryptedCard;
		return encryptedCard;
	}

	public void showDe() // testing used, to be removed
	{
		System.out.println(AES.decrypt(en_CN, secretKey));
	}

	public void login() throws SQLException {
		System.out.println("Please insert your email");
		String input_email = reader.getText("");
		System.out.println("Please insert your password");
		String input_PW = reader.getText("");
		db.connect();
		String sql = "SELECT * from customer WHERE email = '" + input_email + "' AND password = '" + input_PW + "';";

		ResultSet rs = db.runQuery(sql);
		if (rs.next()) {
			CustomerID = rs.getInt("CustomerID");
			name = rs.getString("name");
			email = rs.getString("email");
			address = rs.getString("address");
			isEmployee = rs.getBoolean("isEmployee");
			en_CN = rs.getString("card");
			loginSuccess = true;
			System.out.println("login success");
			System.out.println();
			db.close();
			isLogin = true;

		} else {
			loginSuccess = false;
			System.out.println("login information is not correct, please check again");
			System.out.println("or you can create free account");
			System.out.println();
			db.close();
		}
	}

	public void sendDetailToDataBase() {
		db.connect();
		String sql = "INSERT INTO customer (name, email, password, address, card, isEmployee)" + "VALUES ('";
		sql = sql + name;
		sql = sql + "', '";
		sql = sql + email;
		sql = sql + "','";
		sql = sql + password;
		sql = sql + "', '";
		sql = sql + address;
		sql = sql + "', '";
		sql = sql + en_CN;
		sql = sql + "',";
		sql = sql + false;
		sql = sql + ");";
		db.runQuery(sql);
		db.close();

	}

	/////////////////////////////
	// from here the code are for employee user
	public void menuSelection(boolean isEmployee) throws SQLException {
		if (isEmployee == true) {
			// menu for employee
			menuForEmployee();
		}
		// else normal user menu or back to Engine
	}

	private void menuForEmployee() throws SQLException {
		boolean thisMethodEnd = false;
		while ((thisMethodEnd == false)) {
			System.out.println("Please select function");
			System.out.println("1 - add new show");
			System.out.println("0 - exit");
			// some more function
			String employee_selection = reader.getText("");
			if ((employee_selection).equals("1")) {
				addNewShow();
			}

			// add some function selection

			if ((employee_selection).equals("0")) {
				// end this method
				thisMethodEnd = true;
			}
		}
	}
public String getCard()
{
	return en_CN;
}
public void chooseCard() {
	System.out.println("would you like to card stored in system?");
	System.out.println(" 1 - Card stored in system");
	System.out.println(" 2 - new Card only for this payment.");
	String user_input = reader.getText("");
	if ((user_input).equals("1"))
	{
		
	}
	if ((user_input).equals("2"))
	{
		System.out.println("Please insert your card number for this payment");
		//demo
		String demo = reader.getText("");
	}
	
	
}
	
	private void addNewShow() throws SQLException { // only for employee
		System.out.println("Please insert the name of show");
		String input_showName = reader.getText("");
		String input_typeOfShow = null;
		String input_Language = null;
		String input_liveMusicDescription = null;
		boolean isMusic = false;
		boolean input_correct = false;
		while (input_correct == false) {
			System.out.println("Please insert the type of show  1 / 2 / 3 / 4 ");
			System.out.println("1 - Theatre Show");
			System.out.println("2 - Opera");
			System.out.println("3 - Music Show");
			System.out.println("4 - Concert");
			String input_number = reader.getText("");
			if ((input_number).equals("1")) { // Theatre
				input_correct = true;
				input_typeOfShow = "Theatre";
				System.out.println("Please insert the language of this show");
				input_Language = reader.getText("");
			} else if ((input_number).equals("2")) { // Opera
				input_correct = true;
				input_typeOfShow = "Opera";
				System.out.println("Please insert the language of this show");
				input_Language = reader.getText("");
				System.out.println("is there Music Accompaniment for this show? y/n");
				String input_YN = (reader.getText("")).toLowerCase();
				if ((input_YN).equals("y")) {
					isMusic = true;
					System.out.println("Please insert information of live Music");
					input_liveMusicDescription = reader.getText("");
				} else if ((input_YN).equals("n")) {
					isMusic = false;
				}
			} else if ((input_number).equals("3")) { // music
				input_correct = true;
				input_typeOfShow = "Music Show";
				System.out.println("Please insert the language of this show");
				input_Language = reader.getText("");
				System.out.println("is there Music Accompaniment for this show? y/n");
				String input_YN = (reader.getText("")).toLowerCase();
				if ((input_YN).equals("y")) {
					isMusic = true;

					System.out.println("Please insert information of live Music");
					input_liveMusicDescription = reader.getText("");
				} else if ((input_YN).equals("n")) {
					isMusic = false;
				}

			} else if ((input_number).equals("4")) { // concert
				input_correct = true;
				input_typeOfShow = "Concert";

				System.out.println("Please insert information of live Music");
				input_liveMusicDescription = reader.getText("");
			} else {
				System.out.println("incorrect insert, please only insert the number");
			}
		}
		System.out.println("Please insert description of this show.");
		String input_description = reader.getText("");
		System.out.println("Please insert the Show Duration in minute");
		int input_showDuration = reader.getNumber("");
		System.out.println("Please insert the hours of Show time, if its on 14:30, input 14");
		String input_hour = reader.getText("");

		System.out.println("Please insert the minute of Show time, if its on 14:30 input 30");
		String input_minute = reader.getText("");
		String input_Time = input_hour + ":" + input_minute;
		System.out.println("Please insert the date of shows in yyyy-mm-dd format ");
		String input_date = reader.getText("");
		System.out.println("Please insert the number of Stall Seats");
		int input_stallSeats = reader.getNumber("");
		System.out.println("Please insert the price of StallSeat");
		float input_stallPrice = reader.getFloat("");
		System.out.println("Please insert the price of CircleSeat");
		float input_CirclePrice = reader.getFloat("");

		// show the use all the informaton
		System.out.println("Please check is the information correct");
		System.out.println("Show name:            " + input_showName);
		System.out.println("Type of Show:         " + input_typeOfShow);
		System.out.println("Description:          " + input_description);
		System.out.println("Show Length:          " + input_showDuration);
		System.out.println("Show time and date:   " + input_Time + ", " + input_date);
		System.out.println("Stall  Seat: " + input_stallSeats + "      " + "Price of Stall Seat:" + input_stallPrice);
		int input_CircleSeat = 200 - input_stallSeats;
		System.out.println(
				"Circle Seat: " + (200 - input_stallSeats) + "      " + "Price of Circle Seat:" + input_CirclePrice);
		if (input_Language != null) {
			System.out.println("Performance Language: " + input_Language);
		}
		System.out.print("Live Music:           ");
		if (isMusic == true) {
			System.out.println("Yes");
		} else {
			System.out.println("No");
		}
		System.out.println("");
		System.out.println("Information correct? y/n");
		String input_isCorrect = (reader.getText("")).toLowerCase();
		if ((input_isCorrect).equals("n")) {
			System.out.println("Please enter all information again");
			// end this method
		} else if ((input_isCorrect).equals("y")) {
//create show
			// create theatre
			if ((input_typeOfShow).equals("Theatre")) {
				Theatre currentTheatre = new Theatre(input_showName, input_typeOfShow, input_showDuration,
						input_description, input_stallSeats, input_CircleSeat, input_stallPrice, input_CirclePrice,
						input_date, input_Time);
			}

			// createOpera
			if ((input_typeOfShow).equals("Opera")) {
				if (isMusic == true) {
					Opera currentOpera = new Opera(input_showName, input_typeOfShow, input_showDuration,
							input_description, input_stallSeats, input_CircleSeat, input_stallPrice, input_CirclePrice,
							input_date, input_Time, input_liveMusicDescription);
				} else {
					Opera currentOpera = new Opera(input_showName, input_typeOfShow, input_showDuration,
							input_description, input_stallSeats, input_CircleSeat, input_stallPrice, input_CirclePrice,
							input_date, input_Time);
				}
			}
			// createMusical
			if ((input_typeOfShow).equals("Music Show")) {
				if (isMusic == true) {
					MusicalShow currentMusicShow = new MusicalShow(input_showName, input_typeOfShow, input_showDuration,
							input_description, input_stallSeats, input_CircleSeat, input_stallPrice, input_CirclePrice,
							input_date, input_Time, input_liveMusicDescription);
				} else {
					MusicalShow currentMusicShow = new MusicalShow(input_showName, input_typeOfShow, input_showDuration,
							input_description, input_stallSeats, input_CircleSeat, input_stallPrice, input_CirclePrice,
							input_date, input_Time);
				}
			}
			// create Concert
			if ((input_typeOfShow).equals("Concert")) {
				Concert currentConcert = new Concert(input_showName, input_typeOfShow, input_showDuration,
						input_description, input_stallSeats, input_CircleSeat, input_stallPrice, input_CirclePrice,
						input_date, input_Time, input_liveMusicDescription);

			}
		}
	}


	}
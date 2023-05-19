package Controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.Basket;
import Model.Customer;
import Util.DBConnector;
import Util.InputReader;

public class Engine {
	private ArrayList<String> searchResult = new ArrayList<>();
	private Basket currentBasket = new Basket();
	private Customer c;

	public static void main(String[] args) throws SQLException {

		Customer c = new Customer();
		Basket currentBasket = new Basket();

		// create a new connector with the database
		DBConnector db = new DBConnector();
		// connect to database
		db.connect();
		// create a new input reader
		InputReader reader = new InputReader();
		// print the welcome message
		Controller.printWelcome();
		// while the program is running
		boolean runProgram = true;
		while (runProgram) {

			if ((c.getIsEmployee()) == true) {
				System.out.println("to enter admin menu, insert admin");

			}
			System.out.println();
			System.out.println("Please select function by insert number.");
			System.out.println(" 1 - Login or create new account");
			System.out.println(" 2 - View all shows");
			System.out.println(" 3 - Search shows by date, type or ShowID");
			System.out.println(" 4 - Add ticket to basket");
			System.out.println(" 5 - Check basket");
			System.out.println(" 6 - Empty Basket");
			System.out.println(" 0 - Quit the program");

			String command = reader.getText("");

			if (command.equals("1")) { // login or create account
				c.loginOrRegister();

			} else if (command.equals("admin")) {
				c.menuSelection(c.getIsEmployee());

			}
			if (command.equals("2")) { // view all shows

				// return all of the attributes in the shows table and print them out
				String selectStmt = "SELECT * FROM thetheatreroyal.performance";
				ResultSet rs = db.runQuery(selectStmt);

				// for searching the tickets table
				// db.printQueryOutput(rs);

				ArrayList<String> searchResult = new ArrayList<>();
				String showDetailsToPrint = "";
				int ShowID = 0;
				while (rs.next()) {
					ShowID = rs.getInt("ShowID");
					showDetailsToPrint = "\n" + "Show ID : " + (rs.getString("ShowID") + "\n");
					showDetailsToPrint = showDetailsToPrint + "Show name:            " + rs.getString("name") + "\n";
					showDetailsToPrint = showDetailsToPrint + "Type of Show:         " + rs.getString("type") + "\n";
					showDetailsToPrint = showDetailsToPrint + "Description:          " + rs.getString("description")
							+ "\n";
					showDetailsToPrint = showDetailsToPrint + "Show Length:          " + rs.getInt("length")
							+ "minutes \n";
					showDetailsToPrint = showDetailsToPrint + "Show time and date:   " + rs.getString("time") + ", "
							+ rs.getString("date") + "\n";
					String sql_checkStall = "SELECT COUNT(isCircleSeat) FROM tickets WHERE isCircleSeat = false and showID =  "
							+ ShowID + " and isSold = false";
					ResultSet rs_ticket_stall = db.runQuery(sql_checkStall);
					while (rs_ticket_stall.next()) {
						showDetailsToPrint = showDetailsToPrint + "Stall seat price: " + rs.getFloat("stallPrice")
								+ "             " + "avaliable:";
						showDetailsToPrint = showDetailsToPrint + rs_ticket_stall.getString("COUNT(isCircleSeat)");
						showDetailsToPrint = showDetailsToPrint + "\n";

					}
					String sql_checkCircle = "SELECT COUNT(isCircleSeat) FROM tickets WHERE isCircleSeat = true  and showID = "
							+ ShowID + " and isSold = false";
					ResultSet rs_ticket_circle = db.runQuery(sql_checkCircle);
					while (rs_ticket_circle.next()) {
						showDetailsToPrint = showDetailsToPrint + "Circle seat price: " + rs.getFloat("circlePrice")
								+ "             " + "avaliable:";
						showDetailsToPrint = showDetailsToPrint + rs_ticket_circle.getString("COUNT(isCircleSeat)");
					}
					searchResult.add(showDetailsToPrint);
				}

					// searchResult.add(" - Show ID : "+(rs.getString("ShowID"))+" , "+ rs.getString("name")+"\n"+"Description"+rs.getString("description"));
				for (String s : searchResult) {
					System.out.println(s);
				}

			} else if (command.equals("3")) // search show by date, type or SHOWID;
			{
				System.out.println("Please insert how you like to search");
				System.out.println(" 1 - search by Date");
				System.out.println(" 2 - search by show type");
				System.out.println(" 3 - search by show ID");
				String user_Input = reader.getText("");
				if ((user_Input).equals("1")) {
					ArrayList<String> searchResult = new ArrayList<>();
					// search by date
					System.out.println("Please insert the date of shows in yyyy-mm-dd format");
					String input_date = reader.getText("");
					String sql_searchByDate = "SELECT * FROM thetheatreroyal.performance Where Date = '" + input_date
							+ "';";

					ResultSet rs = db.runQuery(sql_searchByDate);

					// for searching the tickets table
					// db.printQueryOutput(rs);

					String showDetailsToPrint = "";
					int ShowID = 0;
					while (rs.next()) {
						ShowID = rs.getInt("ShowID");
						showDetailsToPrint = "\n" + "Show ID : " + (rs.getString("ShowID") + "\n");
						showDetailsToPrint = showDetailsToPrint + "Show name:            " + rs.getString("name")
								+ "\n";
						showDetailsToPrint = showDetailsToPrint + "Type of Show:         " + rs.getString("type")
								+ "\n";
						showDetailsToPrint = showDetailsToPrint + "Description:          " + rs.getString("description")
								+ "\n";
						showDetailsToPrint = showDetailsToPrint + "Show Length:          " + rs.getInt("length")
								+ "minutes \n";
						showDetailsToPrint = showDetailsToPrint + "Show time and date:   " + rs.getString("time") + ", "
								+ rs.getString("date") + "\n";
						String sql_checkStall = "SELECT COUNT(isCircleSeat) FROM tickets WHERE isCircleSeat = false and showID =  "
								+ ShowID + " and isSold = false";
						ResultSet rs_ticket_stall = db.runQuery(sql_checkStall);
						while (rs_ticket_stall.next()) {
							showDetailsToPrint = showDetailsToPrint + "Stall seat price: " + rs.getFloat("stallPrice")
									+ "             " + "avaliable:";
							showDetailsToPrint = showDetailsToPrint + rs_ticket_stall.getString("COUNT(isCircleSeat)");
							showDetailsToPrint = showDetailsToPrint + "\n";

						}
						String sql_checkCircle = "SELECT COUNT(isCircleSeat) FROM tickets WHERE isCircleSeat = true  and showID = "
								+ ShowID + " and isSold = false";
						ResultSet rs_ticket_circle = db.runQuery(sql_checkCircle);
						while (rs_ticket_circle.next()) {
							showDetailsToPrint = showDetailsToPrint + "Circle seat price: " + rs.getFloat("circlePrice")
									+ "             " + "avaliable:";
							showDetailsToPrint = showDetailsToPrint + rs_ticket_circle.getString("COUNT(isCircleSeat)");
						}
						searchResult.add(showDetailsToPrint);
					}

					//searchResult.add(" - Show ID : "+(rs.getString("ShowID"))+" , "+ rs.getString("name")+"\n"+"Description"+rs.getString("description"));
					for (String s : searchResult) {
						System.out.println(s);
					}
					System.out.println();
				} else if ((user_Input).equals("2")) {
					// search by Show type
					ArrayList<String> searchResult = new ArrayList<>();
					String input_typeOfShow = "";
					boolean inputCorrect = false;
					while (inputCorrect == false) {
						System.out.println("Please insert the type of Show you are searching");
						System.out.println(" 1 - Theatre Show");
						System.out.println(" 2 - Opera Show");
						System.out.println(" 3 - Music Show");
						System.out.println(" 4 - Concert");
						String input_typeOfShowDigit = reader.getText("");
						if ((input_typeOfShowDigit).equals("1")) {
							input_typeOfShow = "Theatre";
							inputCorrect = true;
						} else if ((input_typeOfShowDigit).equals("2")) {
							input_typeOfShow = "Opera";
							inputCorrect = true;
						} else if ((input_typeOfShowDigit).equals("3")) {
							input_typeOfShow = "MusicalShow";
							inputCorrect = true;
						} else if ((input_typeOfShowDigit).equals("4")) {
							input_typeOfShow = "Concert";
							inputCorrect = true;
						} else {
							System.out.println("input not correct, please only insert the number.");
						}
						String sql_searchByType = "SELECT * FROM thetheatreroyal.performance Where type = '"
								+ input_typeOfShow + "';";

						ResultSet rs = db.runQuery(sql_searchByType);

						// for searching the tickets table
						// db.printQueryOutput(rs);

						searchResult = new ArrayList<>();
						String showDetailsToPrint = "";
						int ShowID = 0;
						while (rs.next()) {
							ShowID = rs.getInt("ShowID");
							showDetailsToPrint = "\n" + "Show ID : " + (rs.getString("ShowID") + "\n");
							showDetailsToPrint = showDetailsToPrint + "Show name:            " + rs.getString("name")
									+ "\n";
							showDetailsToPrint = showDetailsToPrint + "Type of Show:         " + rs.getString("type")
									+ "\n";
							showDetailsToPrint = showDetailsToPrint + "Description:          "
									+ rs.getString("description") + "\n";
							showDetailsToPrint = showDetailsToPrint + "Show Length:          " + rs.getInt("length")
									+ "minutes \n";
							showDetailsToPrint = showDetailsToPrint + "Show time and date:   " + rs.getString("time")
									+ ", " + rs.getString("date") + "\n";
							String sql_checkStall = "SELECT COUNT(isCircleSeat) FROM tickets WHERE isCircleSeat = false and showID =  "
									+ ShowID + " and isSold = false";
							ResultSet rs_ticket_stall = db.runQuery(sql_checkStall);
							while (rs_ticket_stall.next()) {
								showDetailsToPrint = showDetailsToPrint + "Stall seat price: "
										+ rs.getFloat("stallPrice") + "             " + "avaliable:";
								showDetailsToPrint = showDetailsToPrint
										+ rs_ticket_stall.getString("COUNT(isCircleSeat)");
								showDetailsToPrint = showDetailsToPrint + "\n";

							}
							String sql_checkCircle = "SELECT COUNT(isCircleSeat) FROM tickets WHERE isCircleSeat = true  and showID = "
									+ ShowID + " and isSold = false";
							ResultSet rs_ticket_circle = db.runQuery(sql_checkCircle);
							while (rs_ticket_circle.next()) {
								showDetailsToPrint = showDetailsToPrint + "Circle seat price: "
										+ rs.getFloat("circlePrice") + "             " + "avaliable:";
								showDetailsToPrint = showDetailsToPrint
										+ rs_ticket_circle.getString("COUNT(isCircleSeat)");
							}
							searchResult.add(showDetailsToPrint);
						}

						// searchResult.add(" - Show ID : "+(rs.getString("ShowID"))+" , "+ rs.getString("name")+"\n"+"Description"+rs.getString("description"));
						for (String s : searchResult) {
							System.out.println(s);
						}
					}

				} else if ((user_Input).equals("3")) {
					// search by show ID

					ArrayList<String> searchResult = new ArrayList<>();
					// search by date
					System.out.println("Please insert the ShowID");
					String input_ShowID = reader.getText("");
					String sql_searchShowID = "SELECT * FROM thetheatreroyal.performance Where showID = '"
							+ input_ShowID + "';";

					ResultSet rs = db.runQuery(sql_searchShowID);

					// for searching the tickets table
					// db.printQueryOutput(rs);

					String showDetailsToPrint = "";
					int ShowID = 0;
					while (rs.next()) {
						ShowID = rs.getInt("ShowID");
						showDetailsToPrint = "\n" + "Show ID : " + (rs.getString("ShowID") + "\n");
						showDetailsToPrint = showDetailsToPrint + "Show name:            " + rs.getString("name")
								+ "\n";
						showDetailsToPrint = showDetailsToPrint + "Type of Show:         " + rs.getString("type")
								+ "\n";
						showDetailsToPrint = showDetailsToPrint + "Description:          " + rs.getString("description")
								+ "\n";
						showDetailsToPrint = showDetailsToPrint + "Show Length:          " + rs.getInt("length")
								+ "minutes \n";
						showDetailsToPrint = showDetailsToPrint + "Show time and date:   " + rs.getString("time") + ", "
								+ rs.getString("date") + "\n";
						String sql_checkStall = "SELECT COUNT(isCircleSeat) FROM tickets WHERE isCircleSeat = false and showID =  "
								+ ShowID + " and isSold = false";
						ResultSet rs_ticket_stall = db.runQuery(sql_checkStall);
						while (rs_ticket_stall.next()) {
							showDetailsToPrint = showDetailsToPrint + "Stall seat price: " + rs.getFloat("stallPrice")
									+ "             " + "avaliable:";
							showDetailsToPrint = showDetailsToPrint + rs_ticket_stall.getString("COUNT(isCircleSeat)");
							showDetailsToPrint = showDetailsToPrint + "\n";

						}
						String sql_checkCircle = "SELECT COUNT(isCircleSeat) FROM tickets WHERE isCircleSeat = true  and showID = "
								+ ShowID + " and isSold = false";
						ResultSet rs_ticket_circle = db.runQuery(sql_checkCircle);
						while (rs_ticket_circle.next()) {
							showDetailsToPrint = showDetailsToPrint + "Circle seat price: " + rs.getFloat("circlePrice")
									+ "             " + "avaliable:";
							showDetailsToPrint = showDetailsToPrint + rs_ticket_circle.getString("COUNT(isCircleSeat)");
						}
						searchResult.add(showDetailsToPrint);
					}

//						searchResult.add(" - Show ID : "+(rs.getString("ShowID"))+" , "+ rs.getString("name")+"\n"+"Description"+rs.getString("description"));
					for (String s : searchResult) {
						System.out.println(s);
					}
					System.out.println();

					// end of search by ID

				}
			} else if (command.equals("4")) // add ticket to basket
			{

				boolean input_isDiscount;
				System.out.println("Please insert the showID ");
				String input_ShowID = reader.getText("");
				System.out.println("Please insert the type of ticket you need:");
				System.out.println(" 1 - circleSeat");
				System.out.println(" 2 - stallSeat");
				String user_input = reader.getText("");
				boolean input_isCircle;
				if ((user_input).equals("1")) {
					input_isCircle = true;
				} else {
					input_isCircle = false;
				}
				System.out.println("Please insert the how many tickets you need for this type of tickets");
				int input_numberOfTickets = reader.getNumber("");
				System.out.println("Are you eligible to concession fares? y/n");
				String inputYN = (reader.getText("")).toLowerCase();
				if ((inputYN).equals("y")) {
					input_isDiscount = true;
				} else {
					input_isDiscount = false;
				}
				currentBasket.getTicket(input_ShowID, input_isCircle, input_numberOfTickets, input_isDiscount);

			} else if (command.equals("5")) // check basket
			{
				currentBasket.printBasket();
				if (currentBasket.getTotal() > 0) {
					System.out.println("Would you like to pay now? y/n");
					String inputNY = (reader.getText("")).toLowerCase();
					if ((inputNY).equals("y")) // customer pay now
					{
						while (c.getIsLogin() == false) {

							System.out.println(
									"You have not input your information or login, we need that to process your payment");
							c.loginOrRegister();
						}
						System.out.println("Whould you like to receive your ticket by post y/n?");
						if ((currentBasket.getAnyTicketsInBasketDiscount() == true)) {
							System.out.println(" * No cost would be charged on posting concession fare tickets");
						} else {
							System.out.println(" * an addictional 1 pound would be charged for posting ticket.");
						}

						String input_YN = (reader.getText("")).toLowerCase();
						boolean isByPost = false;
						if ((input_YN).equals("y")) {
							isByPost = true;
						} else if ((input_YN).equals("n")) {
							isByPost = false;
						} else {
							System.out.println("input not correct");
						}
						currentBasket.updateAllTicketByPost(isByPost);
						if ((currentBasket.getAnyTicketsInBasketDiscount() == true)) {
							// no charges apply
						} else {
							currentBasket.setPostFee();
						}
						c.chooseCard();
						System.out.println("processing your payment...");
						currentBasket.processPayment();
						System.out.println("Completed");
						// basket set all the ticket to true to isSold and add bookingID

					} else {
						// do nothing
					}
				} else {
					// do nothing
				}

			}

			else if (command.equals("6")) // empty basket
			{
				currentBasket.emptyBasket();
				System.out.println("Your basket is now empty");
			}

			if (command.equals("Browse")) {

			}

			else if (command.equals("0")) { // Quit this program
				Controller.printBye();
				runProgram = false;
				db.close();
				// } else {
				// System.out.println("input not recognised, please check and try again");
			}

		}
	}

}

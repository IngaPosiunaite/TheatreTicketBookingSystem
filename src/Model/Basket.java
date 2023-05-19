package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Util.DBConnector;

public class Basket {
	private ArrayList<Ticket> ticketsList = new ArrayList<>();
	DBConnector db = new DBConnector();
	private float totalToPay;

	public Basket() {

	}

	public void getTicket(String ShowID, boolean isCircleSeat, int numberOfTicket, boolean isDiscount)
			throws SQLException {
		int number_of_avaliable_ticket = 0;
		db.connect();
		String sql = "SELECT COUNT(isCircleSeat) FROM tickets WHERE isCircleSeat =" + isCircleSeat + " and showID = "
				+ ShowID + " AND isSold = false";
		ResultSet rs = db.runQuery(sql);
		while (rs.next()) {
			number_of_avaliable_ticket = rs.getInt("COUNT(isCircleSeat)");
			if (number_of_avaliable_ticket >= numberOfTicket) // enough tickets for sell
			{

				// get next available tick by showID

				String sql_getTicketID = "SELECT  MIN(TicketID) From tickets Where isCircleSeat = " + isCircleSeat
						+ " AND SHOWID = '" + ShowID + "' AND isSold = false";
				ResultSet rs_checkID = db.runQuery(sql_getTicketID);
				while (rs_checkID.next()) {
					int currentTicketID = rs_checkID.getInt("MIN(TicketID)");
					for (int i = 0; i < numberOfTicket; i++) {
						Ticket currentTicket = new Ticket(currentTicketID + i);
						currentTicket.setDiscount(isDiscount);
						ticketsList.add(currentTicket);
					}
				}
			} else 
			{
				System.out.println("insufficient tickets available, please select other times or show.");
			}
		}
		db.close();
	}

	// find the available ticket, and put it into basket, when the customer pay
	// change isSold to true and assign booking ID.
	// get show id by calling the ticket ID, and input the details base on the id

	public void addTicketIntoBasket(String ShowID, boolean isCircleSeat, int numberOfTicket, boolean isDiscount)
			throws SQLException {
		db.connect();
		for (int i = 0; i < numberOfTicket; i++) {
			Ticket currentTicket = new Ticket(ShowID);
			int currentTicketID = 0;
			String sql_getTicketID = "SELECT  MIN(TicketID) From tickets Where isCircleSeat = " + isCircleSeat
					+ " AND SHOWID = '" + ShowID + "' AND isSold = false";
			ResultSet rs_checkID = db.runQuery(sql_getTicketID);
			while (rs_checkID.next()) {
				currentTicketID = rs_checkID.getInt("MIN(TicketID)");
				System.out.println(currentTicketID);
				currentTicketID = currentTicketID + i;
				System.out.println("	currentTicket ID :" + currentTicketID);
				currentTicket.getTicketDetailsByTicketID(currentTicketID);
				System.out.println(currentTicketID + "add to basket");
				ticketsList.add(currentTicket);
			}
			System.out.println(ticketsList.size());
		}
		db.close();
	}

	public void printBasket() // print out the ticket already added to basket
	{
		if (ticketsList.size() > 0) {
			int i = 0;
			for (Ticket t : ticketsList) {
				i = i + 1; // count the order of tickets.
				System.out.println("===== Item #" + i + " in basket" + "=====" + "\n");
				System.out.println("Show Title: " + t.getTitleOfShow());
				System.out.println("Date :" + t.getDate());
				if ((t.getIsCircleSeat() == true)) {
					System.out.println("Seat Type: Circle Seat");
				} else {
					System.out.println("Seat Type: Stall Seat");
				}
				if ((t.getIsDiscount()) == false) {
					System.out.println("Full Fare Ticket");
				} else {
					System.out.println("Concession Fares Ticket");
				}
				System.out.println();
			}
			float total_to_return = getTotalPrice();
			System.out.println("The total cost is :" + total_to_return);
			totalToPay = total_to_return;

		} else // no ticket
		{
			System.out.println("No ticket in basket");
		}

	}

	public float getTotalPrice() {
		float total_to_return = 0;
		for (Ticket t : ticketsList) {
			total_to_return = total_to_return + t.getPrice();
		}
		return total_to_return;
	}

	public float getTotal() {
		return totalToPay;
	}

	public void updateAllTicketByPost(boolean input_isBypost) {
		db.connect();
		for (Ticket t : ticketsList) {
			t.setByPost(input_isBypost);
		}
		db.close();
	}

	public void emptyBasket() {
		ticketsList.clear();
		totalToPay = 0;
	}

	public boolean getAnyTicketsInBasketDiscount() {
		boolean toBeReturned = false;
		for (Ticket t : ticketsList) {
			if (t.getIsDiscount() == true) {
				toBeReturned = true;
			}
		}
		return toBeReturned;
	}

	public void setPostFee() {
		totalToPay = totalToPay + 1;
	}

	public void processPayment() throws SQLException {
		for (Ticket t : ticketsList) {
			t.updateSold();
		}
		emptyBasket();
	}

}

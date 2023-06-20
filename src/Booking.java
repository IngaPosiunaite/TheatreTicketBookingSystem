package theatre;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Random;

public class Booking {

	private ArrayList<Ticket> basket;
	private Ticket ticket;
	private int numOfTickets;
	private boolean moveToBasket;
	private boolean orderConfirmed;
	private Random bookingId;
	private int performanceId;
	private ArrayList<Integer> soldTicketCollection;

	public Booking(Customer customer, Ticket ticket, int numOfTickets) {
		moveToBasket = false;
		this.ticket = new Ticket(0);
		this.numOfTickets = numOfTickets;
		bookingId = null;
		setBasket(new ArrayList<Ticket>());
		soldTicketCollection = new ArrayList<Integer>();
	}

	public ArrayList<Ticket> getBasket() {
		return basket;
	}

	public void setBasket(ArrayList<Ticket> basket) {
		this.basket = basket;
	}

	public boolean isMoveToBasket() {
		return moveToBasket;
	}

	public boolean isOrderConfirmed() {
		return orderConfirmed;
	}

	public Random getBookingId() {
		return bookingId;
	}

	public void setBookingId(Random bookingId) {
		this.bookingId = bookingId;
	}

	public int getNumOfTickets() {
		return numOfTickets;
	}

	public void setNumOfTickets(int numOfTickets) {
		this.numOfTickets = numOfTickets;
	}

	public void toggleMoveToBasket() {
		moveToBasket = !moveToBasket;
	}

	public void toggleOrderConfirmed() {
		orderConfirmed = !orderConfirmed;
	}

	public void addOrderToBasket(Ticket order) {
		toggleMoveToBasket();
		if (moveToBasket == true) {
			Ticket ticket = order;
			basket.add(ticket);
			System.out.println("Added to basket.");
		} else {
			System.out.println("Confirm first before adding to basket.");
		}
	}

	public void addTicketToSoldCollection(int quantity) {
		soldTicketCollection.add(quantity);
	}

	public ArrayList<Integer> placeBooking(Performance performance) {
		Performance p = performance;
		DBConnector connector = new DBConnector();
		Random random = new Random();

		connector.connect();
		ResultSet time = connector.runQuery("SELECT start_time from performance where start_time =" + p.getTime());
		ResultSet date = connector
				.runQuery("SELECT performance_date from performance where performance_date =" + performance.getDate());

		connector.close();
		int bookingId = random.nextInt();

		for (int i = 0; i < numOfTickets; i++) {
			connector.connect();

			String query = "INSERT INTO ticket_sold (`tickets_sold_id`, `performance_id`, `booking_id`, `seat_type`, `number_of_tickets_sold`) VALUES ("
					+ 0 + "," + performanceId + "," + bookingId + "," + ticket.getSeatType() + "," + 1 + ")";

			ResultSet rs = connector.runQuery(query);
			connector.printResultStart(rs);
			connector.close();
			soldTicketCollection.add(bookingId);
		}
		System.out.println(soldTicketCollection.size() + " tickets booked.");
		System.out.println("Booking ID: " + bookingId);

		return soldTicketCollection;
	}

	public void resetBooking() {
		moveToBasket = false;
		orderConfirmed = false;
		basket.clear();
		soldTicketCollection.clear();
	}
}

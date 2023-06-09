package theatre;

import java.util.ArrayList;

public class Ticket {
	
	private final ArrayList<Ticket> soldTickets;
	private boolean seatType;
	private boolean priceType;
	private Show show;
	private Customer customer;

	public Ticket(int performanceID) {
		soldTickets = new ArrayList<>();
		seatType = true;
		priceType = true;
	}

	public ArrayList<Ticket> getSoldTickets() {
		return soldTickets;
	}

	public boolean getSeatType() {
		return seatType;
	}

	public boolean getPriceType() {
		return priceType;
	}

	public Show getShow() {
		return show;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void toggleCircle() {
		seatType = false;
	}

	public void toggleStall() {
		seatType = true;
	}

	public void toggleConcession() {
		priceType = false;
	}

	public void toggleFullPrice() {
		priceType = true;
	}

	public void addSoldTickets(Ticket ticket) {
		soldTickets.add(ticket);
	}

	public void generateTicket(Booking booking) {
		if (soldTickets.size() < 200) {
			System.out.println("Here's your ticket:");
			printTicketDetails();
			System.out.println("Here's how many tickets you booked " + booking.getNumOfTickets());
			System.out.println("Thank you for booking. We hope you enjoy the show!");
		}
	}

	public Object getTicket() {
		return null;
	}

	public void printTicketDetails() {
		System.out.println("Here's your ticket details: ");
		System.out.println("Seat Type " + getSeatType());
		System.out.println("Price Type " + getPriceType());
	}
}

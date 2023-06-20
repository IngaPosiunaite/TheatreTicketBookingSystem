package theatre;

public class Customer {

	private Boolean infiniteBudget;
	private int ticketsBought;
	private String name;
	private String email;
	private String address;
	private Encryptor encryptor;

	public Customer(String name, String email, String address) {
		infiniteBudget = true;
		ticketsBought = 0;
		encryptor = new Encryptor();
	}

	public int getTicketsBought() {
		return ticketsBought;
	}

	public void setTicketsBought(int ticketsBought) {
		this.ticketsBought = ticketsBought;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Encryptor getEncryptor() {
		return encryptor;
	}

	public void setEncryptor(Encryptor encryptor) {
		this.encryptor = encryptor;
	}

	public Boolean getInfiniteBudget() {
		return infiniteBudget;
	}

}

package atm;

public class UserInput {
	private String name;
	private int pin;
	private long account_number;
	private String email;
	private int balance;

	public UserInput(String name, int pin, long account_number, String email, int balance) {
		this.name = name;
		this.pin = pin;
		this.account_number = account_number;
		this.email = email;
		this.balance = balance;

	}

	public String getName() {
		return name;
	}

	public int getPin() {
		return pin;
	}

	public long getAccount_number() {
		return account_number;
	}

	public String getEmail() {
		return email;
	}

	public int getBalance() {
		return balance;
	}

}

package atm;

import java.sql.*;
import java.util.Scanner;

public class database {

	static Scanner sc;

	String name;
	int pin;
	long account_number;
	String email;
	int balance;
	int withdraw;
	int deposit;

	static String url = "jdbc:mysql://localhost:3306/ATMPROJECT";
	static String username = "root";
	static String password = "1811";

	public void NewUser(UserInput data) throws SQLException {

		String name = data.getName();
		int pin = data.getPin();
		long acc = data.getAccount_number();
		String email = data.getEmail();
		int balance = data.getBalance();
		int deposit = 0;
		int withdraw = 0;

		String query = "insert into NEWUSER values(?,?,?,?,?,?,?);";
		Connection con = DriverManager.getConnection(url, username, password);
		PreparedStatement pst = con.prepareStatement(query);

		pst.setString(1, name);
		pst.setInt(2, pin);
		pst.setLong(3, acc);
		pst.setString(4, email);
		pst.setInt(5, balance);
		pst.setInt(6, deposit);
		pst.setInt(7, withdraw);

		int row1 = pst.executeUpdate();
		System.out.println("\nACCOUNT CREATED SUCCESSFULLLY...!");
	}

	public boolean userExists(int pin) throws SQLException {

		String query = "SELECT PIN FROM NEWUSER;";
		Connection con = DriverManager.getConnection(url, username, password);
		Statement st = con.createStatement();
		ResultSet rst = st.executeQuery(query);

		boolean f = false;

		loop: while (rst.next()) {
			if (pin == rst.getInt(1)) {
				f = true;
				break loop;
			}
		}
		return f;
	}

	public boolean existinguser(int pin) throws SQLException {

		String query = "SELECT PIN FROM NEWUSER;";
		Connection con = DriverManager.getConnection(url, username, password);
		Statement st = con.createStatement();
		ResultSet rst = st.executeQuery(query);

		boolean f = false;

		loop: while (rst.next()) {
			if (pin == rst.getInt(1)) {
				f = true;
				break loop;
			}
		}
		return f;
	}

	public void switchcase(int pin) throws SQLException {
		sc = new Scanner(System.in);
		loop: while (true) {
			System.out.println("\nENTER 1 -----> CHECK BALANCE");
			System.out.println("ENTER 2 -----> WITHDRAW");
			System.out.println("ENTER 3 -----> DEPOSIT MONEY");
			System.out.println("ENTER 4 -----> TAKE RESIPT");
			System.out.println("ENTER 5 -----> EXIT");
			int n = sc.nextInt();
			Connection con = DriverManager.getConnection(url, username, password);
			switch (n) {
			case 1:
				String query = "SELECT BALANCE FROM NEWUSER WHERE PIN=?;";
				PreparedStatement pst = con.prepareStatement(query);

				pst.setInt(1, pin);
				ResultSet rst = pst.executeQuery();
				if (rst.next()) {
					System.out.println("YOUR CURRENT BALANCE IS : " + rst.getInt(1));
				}
				break;
			case 2:
				System.out.print("ENTER THE WITHDRAWAL AMOUNT : ");
				withdraw = sc.nextInt();

				String query1 = "SELECT BALANCE FROM NEWUSER WHERE PIN=?;";
				PreparedStatement pst1 = con.prepareStatement(query1);

				pst1.setInt(1, pin);
				ResultSet rst1 = pst1.executeQuery();
				if (rst1.next()) {
					if (withdraw < rst1.getInt(1)) {
						balance = rst1.getInt("BALANCE") - withdraw;
						String query2 = "UPDATE NEWUSER SET balance=?,withdraw=? WHERE PIN=?;";
						PreparedStatement pst2 = con.prepareStatement(query2);
						pst2.setInt(1, balance);
						pst2.setInt(2, withdraw);
						pst2.setInt(3, pin);

						int row2 = pst2.executeUpdate();
						System.out.println("YOUR CURRENT BALANCE IS :" + balance);
					} else {
						System.err.println(
								"INSUFFICIENT BALANCE....!\n YOUR CURRENT BALANCE IS :" + rst1.getInt("BALANCE"));
					}
				}
				break;
			case 3:
				System.out.print("ENTER AMOUNT : ");
				deposit = sc.nextInt();

				String query3 = "SELECT BALANCE FROM NEWUSER WHERE PIN=?;";

				PreparedStatement pst2 = con.prepareStatement(query3);
				pst2.setInt(1, pin);
				ResultSet rst2 = pst2.executeQuery();
				rst2.next();
				balance = rst2.getInt("BALANCE") + deposit;
				String query4 = "UPDATE NEWUSER SET BALANCE=?,DEPOSIT=? WHERE PIN=?;";
				PreparedStatement pst3 = con.prepareStatement(query4);
				pst3.setInt(1, balance);
				pst3.setInt(2, deposit);
				pst3.setInt(3, pin);
				int row4 = pst3.executeUpdate();
				System.out.println("NOW YOUR BALANCE IS : " + balance);
				break;

			case 4: {
				String query5 = "SELECT BALANCE,WITHDRAW,DEPOSIT FROM NEWUSER WHERE PIN=?;";
				PreparedStatement pst4 = con.prepareStatement(query5);

				pst4.setInt(1, pin);
				ResultSet rst3 = pst4.executeQuery();
				rst3.next();
				System.out.println("------ATM MACHINE GENERATED RECEIPT------\n");
				System.out.println("   YOUR BALANCE AMOUNT IS  : " + rst3.getInt(1));
				System.out.println("   LAST WIDTHDRAWAL AMOUNT : " + rst3.getInt(2));
				System.out.println("   LAST DEPOSIT AMOUNT     : " + rst3.getInt(3));
				System.out.println("\n-----------------------------------------");
				System.out.println("             THANKS FOR COMING.             ");

			}
				break loop;
			case 5:
				break loop;

			default: {
				System.err.println("INVALID OPRATION...!");
				break;

			}
			}
		}
	}
}

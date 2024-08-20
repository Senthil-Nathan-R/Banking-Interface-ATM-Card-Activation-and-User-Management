package atm;

import java.util.Scanner;
import java.sql.*;

public class Main {
	static Scanner sc;

	static database db;

	public static void main(String[] args) throws SQLException {
		db = new database();
		sc = new Scanner(System.in);
		System.out.println("--------ATM MACHINE---------");
		loop1: while (true) {
			System.out.println("\n1.EXISTING USER \n2.ACTIVATE ATM CARD \n3.EXIT");
			System.out.print("\nENTER NUMBER 1 OR 2:");
			int num = sc.nextInt();
			switch (num) {
			case 1:
				existingUser();
				break;
			case 2:
				activateCard();
				break;
			case 3:
				break loop1;
			default:
				System.err.println("\nINVALID OPERATION...!");

			}
		}
	}

	public static void existingUser() throws SQLException {

		System.out.print("\n\nENTER A PIN NUMBER :");
		int pin = sc.nextInt();
		boolean i = db.existinguser(pin);
		if (i == true) {

			db.switchcase(pin);

		} else {
			System.err.println("INVALID USER");
			System.err.print("ENTER YOUR 4 DIGIT PIN NUMBER...!\n");
			existingUser();
		}
	}

	public static void activateCard() throws SQLException {
		System.out.print("ENTER A NAME           :");
		String name = sc.next();
		System.out.print("ENTER A PIN            :");
		int pin = sc.nextInt();
		if (String.valueOf(pin).length() == 4) {
			if (db.userExists(pin)) {
				System.err.print("THIS PIN ALREADY EXISTS..!\n\n");
				activateCard();
			} else {
				System.out.print("ENTER A ACCOUNT NUMBER :");
				long account_number = sc.nextLong();
				if (String.valueOf(account_number).length() != 10) {
					System.err.println("ENTER 10 DIGIT ACCOUNT_NUMBER TO CREATE ATM CARD...!\n\n");
					activateCard();
				} else {
					System.out.print("ENTER A EMAIL          :");
					String email = sc.next();
					System.out.print("ENTER A BALANCE        :");
					int balance = sc.nextInt();
					UserInput user = new UserInput(name, pin, account_number, email, balance);
					db.NewUser(user);
				}

			}
		} else {
			System.err.println("ENTER 4 DIGIT PIN TO CREATE ATM CARD...!\n\n");
			activateCard();
		}

	}
}
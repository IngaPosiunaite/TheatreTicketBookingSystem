package Controller;

import java.sql.ResultSet;

import Util.DBConnector;
import Util.InputReader;

public class Controller {
	private static InputReader reader;
	private static DBConnector c;
	
	
public Controller() {
	
}
	
	public static void printWelcome() {
		System.out.println("Welcome to the Theatre Royal");
        
	}
	
	public static void printBye() {
		System.out.println("Thank you. See you again soon!");
	}

	
	
}

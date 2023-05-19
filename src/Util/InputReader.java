/**
 * 
 */
package Util;

import java.util.Scanner;

public class InputReader {

	private Scanner scanner;

	/**
	 * Constructor for objects of class InputReader
	 */
	public InputReader() {
		scanner = new Scanner(System.in);
	}

	public Scanner getScanner() {
		return scanner;
	}

	public int getNumber(String prompt) {
		System.out.println(prompt);
		int number = scanner.nextInt();
		scanner.nextLine();
		return number;
	}

	public float getFloat(String prompt) {
		System.out.println(prompt);
		float number = scanner.nextFloat();
		scanner.nextLine();
		return number;
	}

	public String getText(String prompt) {
		System.out.println(prompt);
		String input = scanner.nextLine();
		return input;
	}

}

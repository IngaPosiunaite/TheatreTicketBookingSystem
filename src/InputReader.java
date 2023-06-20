package theatre;

import java.util.Scanner;

public class InputReader {

	private Scanner scanner;

	public InputReader() {
		scanner = new Scanner(System.in);
	}

	public Scanner getScanner() {
		return scanner;
	}

	public String getText(String prompt) {
		System.out.println(prompt);
		String input = scanner.nextLine();
		return input;
	}

	public int getNumber(String prompt) {
		System.out.println(prompt);
		int number = scanner.nextInt();
		scanner.nextLine();
		return number;
	}
}

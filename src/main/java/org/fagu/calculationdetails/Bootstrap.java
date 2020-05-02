package org.fagu.calculationdetails;

import java.text.ParseException;
import java.util.Scanner;


public class Bootstrap {

	public static void main(String[] args) {
		try (Scanner scanner = new Scanner(new UnclosedInputStream(System.in))) {
			while(true) {
				System.out.print(": ");
				String next = scanner.next();
				if("q".equalsIgnoreCase(next) || "quit".equalsIgnoreCase(next) || "bye".equalsIgnoreCase(next)) {
					break;
				}
				try {
					Operation operation = Operation.parse(next);
					System.out.println();
					operation.showDetails();
					System.out.println();
				} catch(ParseException e) {
					e.printStackTrace();
				}
			}
		}

	}

}

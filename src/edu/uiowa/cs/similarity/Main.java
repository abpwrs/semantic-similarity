package edu.uiowa.cs.similarity;

import java.io.*;

public class Main {
	private static void printMenu() {
		System.out.println("Supported commands:");
		System.out.println("help - Print the supported commands");
		System.out.println("quit - Quit this program");
	}

    public static void main(String[] args) throws IOException {
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

		while (true) {
			System.out.print("> ");
			String command = input.readLine();
			if (command.equals("help") || command.equals("h")) {
				printMenu();
			} else if (command.equals("quit")) {
				System.exit(0);
			} else {
				System.err.println("Unrecognized command");
			}
		}
    }
}

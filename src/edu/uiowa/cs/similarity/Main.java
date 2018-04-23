package edu.uiowa.cs.similarity;

import data.FileParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;

public class Main {
    private static void printMenu() {
        System.out.println("Supported commands:");
        System.out.println("help - Print the supported commands");
        System.out.println("quit - Quit this program");
    }

    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        FileParser parser = new FileParser();
        // this Data structure should have a Class as a wrapper around it
        HashMap<String, HashMap<String, Integer>> main_map = new HashMap<>();

        // Testing FileParser (can be commented out for submission)
        ///////////////////////////////////////////////////////////
        HashSet<HashSet<String>> test = parser.parseFile("src/data/repetition_test.txt");
        // System.out.println(test);
        ///////////////////////////////////////////////////////////

        //
        // Prototyping the conversion of HashSet<HashSet<String>> to HashMap<String,HashMap<String,Integer>>
        // for each word in each sentence - this is a double for loop
        //     iterate through each sentence - one for loop
        //         check if sentence contains the word we are adding to the main map
        //         iterate through words in sentence with goal_word - one for loop
        //             update the main map
        // O(n^4)
        System.out.println("This is going to take a bit\nLOL N^4 is fine right.......\n");
        long start = System.currentTimeMillis();
        if (main_map.isEmpty()) {
            // this will create a fresh data structure
            for (HashSet<String> sentence : test) {
                for (String word : sentence) {
                    // there is a temp for each word
                    HashMap<String, Integer> temp = new HashMap<>();
                    for (HashSet<String> sentence2 : test) {
                        if (sentence2.contains(word)) {
                            for (String word2 : sentence2) {
                                if (!word.equals(word2)) {
                                    if (temp.containsKey(word2)) {
                                        temp.put(word2, temp.get(word2) + 1);
                                    } else {
                                        temp.put(word2, 1);
                                    }
                                }
                            }
                        }
                    }
                    main_map.put(word, temp);
                }
            }
        } else {
            // We need to be able to add onto the main_map
        }
        long end = System.currentTimeMillis();
        System.out.println("\ntime to create data structure " + ((float) (end - start) / 1000f) + " seconds\n");
        System.out.println(main_map);

        while (true) {
            System.out.print("> ");
            String command = input.readLine();
            // generalize command to work for any case (Upper/Lower)
            command = command.toLowerCase();
            if (command.equals("help") || command.equals("h")) {
                printMenu();
            } else if (command.equals("quit") || command.equals("q")) {
                System.exit(0);
            } else {
                System.err.println("Unrecognized command");
            }
        }
    }
}

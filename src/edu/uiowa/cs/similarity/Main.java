package edu.uiowa.cs.similarity;

import Vectors.SemanticVector;
import data.FileParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Main {
    private static void printMenu() {
        System.out.println("Supported commands:");
        System.out.println("help - Print the supported commands");
        System.out.println("quit - Quit this program");
    }

    public static void startParsing(String filename){
        
    }

    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        // this Data structure should have a Class as a wrapper around it
        HashMap<String, HashMap<String, Integer>> main_map = new HashMap<>();

        // Testing FileParser (can be commented out for submission)
        ///////////////////////////////////////////////////////////
        ArrayList<HashSet<String>> parseResult = FileParser.parseFile("src/data/swanns_way.txt");
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
            for (HashSet<String> sentence : parseResult) {
                for (String word : sentence) {
                    SemanticVector t = new SemanticVector(word, parseResult);
                    main_map.put(word, t.getVector());
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
            String[] s_command = command.split("\\+s");
            System.out.println(s_command);
            if (s_command[0].equals("help") || s_command[0].equals("h")) {
                printMenu();
            } else if (s_command[0].equals("quit") || s_command[0].equals("q")) {
                System.exit(0);
            } else if (s_command[0].equals("index") || s_command[0].equals("i")) {
                System.exit(0);
            }
            else {
                System.err.println("Unrecognized command");
            }
        }
    }
}

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
        //TODO: Add new commands to help
        System.out.println("Supported commands:");
        System.out.println("help - Print the supported commands");
        System.out.println("quit - Quit this program");
    }

    //Run a new parse into the list of sentences, and then interpret that list into vectors for the main map.
    public static void index(String filename, ArrayList<HashSet<String>> allSentences,
                             HashMap<String, HashMap<String, Integer>> main_map) {

        System.out.println("Indexing " + filename);
        ArrayList<HashSet<String>> parseResult = FileParser.parse(filename);
        // small null pointer exception to catch
        if (parseResult != null) {
            long start = System.currentTimeMillis();
            allSentences.addAll(parseResult);
            if (main_map.isEmpty()) {
                // this will create a fresh data structure
                for (HashSet<String> sentence : parseResult) {
                    for (String word : sentence) {
                        //TODO: What if there is an existing word vector?
                        //  should we check the WordDB before generating a brand new vector? - Yes
                        //  if it exists in the WordDB, add a new parseResult to the vector? - Yes
                        // If yes, we need a vector method to update the existing "temp" hashmap with new parse.
                        // I will add this to the SemanticVector class
                        SemanticVector t = new SemanticVector(word, parseResult);
                        main_map.put(word, t.getVector());
                    }
                }
            } else {
                System.out.println("Can't index twice yet! Whoops.");
                // TODO: We need to be able to add onto the main_map
                // WordDB.update(parseResult)
            }

            long end = System.currentTimeMillis();
            System.out.println("Time to create/append to data structure " + ((float) (end - start) / 1000f) + " seconds");
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        //TODO: this Data structure should have a Class as a wrapper around it
        HashMap<String, HashMap<String, Integer>> main_map = new HashMap<>();
        HashMap<String, SemanticVector> vector_map = new HashMap<>();

        //We Need a list of all sentences collected, across all indexes.
        //We need a command to print all sentences, and we couldn't do that from the main_map,
        //so I added an in-between step to store just sentences, ready for recall from "allSentences".
        // TODO: Tell me straight up if we don't and there's a better way to do it <3
        // TODO: Ideally this will be replaced with a WordDB, but this is good
        ArrayList<HashSet<String>> allSentences = new ArrayList<HashSet<String>>();

        // Testing FileParser (can be commented out for submission)
        ///////////////////////////////////////////////////////////
        //ArrayList<HashSet<String>> test = FileParser.parse("src/data/cleanup_test.txt");
        // System.out.println(test);
        //parseResult = index("src/data/swanns_way.txt");
        // System.out.println(parseResult);

        ///////////////////////////////////////////////////////////
        //
        // Prototyping the conversion of HashSet<HashSet<String>> to HashMap<String,HashMap<String,Integer>>
        // for each word in each sentence - this is a double for loop
        //     iterate through each sentence - one for loop
        //         check if sentence contains the word we are adding to the main map
        //         iterate through words in sentence with goal_word - one for loop
        //             update the main map
        // O(n^4)
        //index("src/data/easy_sanity_test.txt", allSentences, main_map);
        //System.out.println(main_map + "\n");

        while (true) {
            System.out.print("> ");
            String command = input.readLine();
            // generalize command to work for any case (Upper/Lower)
            command = command.toLowerCase();
            String[] s_command = command.split(" ");

            /*//print given input after split for testing
            System.out.println("s_command:");
            for (String ele : s_command){
                System.out.println(ele);
            }
            System.out.print("\n");*/

            if (s_command[0].equals("help") || s_command[0].equals("h")) {
                printMenu();
            } else if (s_command[0].equals("quit") || s_command[0].equals("q")) {
                System.exit(0);
            } else if (s_command[0].equals("index") || s_command[0].equals("i")) {
                // TODO: File doesn't exist case. Try except? Don't know how to use those well. - DONE
                // I altered the FileParser so it doesn't exit
                index(s_command[1], allSentences, main_map);
            } else if (s_command[0].equals("sentences") || s_command[0].equals("s")) {
                System.out.println(allSentences);
            } else if (s_command[0].equals("num") || s_command[0].equals("n")) {
                if (s_command[1].equals("sentences")) {
                    System.out.println(allSentences.size());
                } else {
                    System.out.println("Unrecognized argument. See help for more details.");
                }
            } else {
                System.err.println("Unrecognized command");
            }
        }
    }
}

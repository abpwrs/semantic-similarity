package edu.uiowa.cs.similarity;

import DB.FileParser;
import DB.WordDB;
import Similarity.CosineSimilarity;
import Vectors.SemanticVector;
import opennlp.tools.stemmer.PorterStemmer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;

public class Main {

    private static void printMenu() {
        //TODO: Add new commands to help
        System.out.println("Supported commands:");
        System.out.println("help - Print the supported commands");
        System.out.println("quit - Quit this program");
        System.out.println("index - indexes a file (second argument) to be used as a semantic vector");
        System.out.println("sentences - prints the sentences");
        System.out.println("num - prints the number of sentences or vectors depending on the second arg");
    }

    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        //TODO: this Data structure should have a Class as a wrapper around it
        WordDB wordDB = new WordDB();
        PorterStemmer stemmer = new PorterStemmer();
        //We Need a list of all sentences collected, across all indexes.
        //We need a command to print all sentences, and we couldn't do that from the main_map,
        //so I added an in-between step to store just sentences, ready for recall from "allSentences".
        // TODO: Tell me straight up if we don't and there's a better way to do it <3
        // TODO: Ideally this will be replaced with a WordDB, but this is good
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
                if (s_command.length == 2) {
                    wordDB.index(s_command[1]);
                } else {
                    System.out.println("Incorrect Command Usage:");
                    printMenu();
                }

            } else if (s_command[0].equals("sentences") || s_command[0].equals("s")) {
                System.out.println(wordDB.getAllSentences());

            } else if (s_command[0].equals("vectors") || s_command[0].equals("v")) {
                for (SemanticVector vector : wordDB.getVectors()) {
                    System.out.println(vector.getWord());
                    System.out.println(vector.getVector());
                }

            } else if (s_command[0].equals("num") || s_command[0].equals("n")) {
                if (s_command.length == 2) {
                    if (s_command[1].equals("sentences") || s_command[1].equals("s")) {
                        System.out.println(wordDB.numSentences());
                    } else if (s_command[1].equals("vectors") || s_command[1].equals("v")) {
                        System.out.println(wordDB.numVectors());
                    } else {
                        System.out.println("Unrecognized argument. See help for more details.");
                    }
                } else {
                    System.out.println("Incorrect Command Usage:");
                    printMenu();
                }

            } else if (s_command[0].equals("topj")) {
                if (s_command.length == 3) {
                    if (wordDB.contains(stemmer.stem(s_command[1]))) {
                        stemmer.reset();
                        if (FileParser.isNumeric(s_command[2])) {
                            long start = System.currentTimeMillis();
                            ArrayList<Map.Entry<String, Double>> topj = wordDB.TopJ(stemmer.stem(s_command[1]), Integer.parseInt(s_command[2]), new CosineSimilarity());
                            long end = System.currentTimeMillis();
                            System.out.println("Time taken to calculate TopJ " + ((float) (end - start) / 1000f) + " seconds");

                            System.out.println(topj);
                        } else {
                            System.out.println("The third argument must be an integer");
                        }

                    } else {
                        System.out.println("Cannot compute TopJ similarity to " + s_command[1]);
                    }
                    stemmer.reset();
                } else {
                    System.out.println("Incorrect Command Usage:");
                    printMenu();
                }
                stemmer.reset();
            } else {
                System.err.println("Unrecognized command");
            }
        }
    }
}


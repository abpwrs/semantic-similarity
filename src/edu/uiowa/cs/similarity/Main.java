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
        //TODO: BEN: Add new commands to help
        System.out.println("Supported commands:");
        System.out.println("help - Print the supported commands");
        System.out.println("quit - Quit this program");
        System.out.println("index FILE - Read in and index  the file given by FILE");
        System.out.println("sentences - Prints the currently stored sentences");
        System.out.println("num TYPE - Prints the number of TYPE data. Ex: num sentence or num vector");
    }

    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        WordDB wordDB = new WordDB();
        PorterStemmer stemmer = new PorterStemmer();
        //TODO: BEN: Issue 10, Implementing the measure command
        //TODO: BEN: Looking into cluster class.
        //TODO: BEN: Test data, look for issue 16 bug.
        //TODO: BEN: Check on the full write up requirements.

        // Testing FileParser (can be commented out for submission)
        ///////////////////////////////////////////////////////////
        //ArrayList<HashSet<String>> test = FileParser.parse("src/data/cleanup_test.txt");

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
            command = command.toLowerCase();
            String[] s_command = command.split(" ");

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
                //TODO: BEN: End of part 3 terminal example. topj cat 6 "not enough?" why does example have "ten = 0.0"
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


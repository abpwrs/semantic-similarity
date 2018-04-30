package edu.uiowa.cs.similarity;

import DB.FileParser;
import DB.WordDB;
import Similarity.*;
import Vectors.SemanticVector;
import opennlp.tools.stemmer.PorterStemmer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;

public class Main {

    private static void printMenu() {
        //TODO: May_1: BEN: Add new commands to help
        System.out.println("Supported commands:");
        System.out.println("\nhelp - Print the supported commands");
        System.out.println("\nquit - Quit this program");
        System.out.println("\nindex FILE - Read in and index  the file given by FILE");
        System.out.println("\nsentences - Prints the currently stored sentences");
        System.out.println("\nnum TYPE - Prints the number of TYPE data.");
        System.out.println("TYPE options:\n\t(s)entences\n\t(v)ectors");
        System.out.println("\nmeasure METHOD - Changes what similarity function is used.");
        System.out.println("METHOD options:\n\tcosineSimilarity\n\tnegEuclideanDist\n\tnormNegEuclideanDist");
    }

    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        WordDB wordDB = new WordDB();
        PorterStemmer stemmer = new PorterStemmer();
        SimilarityFunction similarityFunction = new CosineSimilarity();
        //TODO: BEN: Issue 10, Implementing the measure command
        //TODO: BEN: Looking into cluster class.
        //TODO: BEN: Test data, look for issue 16 bug.
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
                if (s_command.length == 3) {
                    if (wordDB.contains(stemmer.stem(s_command[1]))) {
                        stemmer.reset();
                        if (FileParser.isNumeric(s_command[2])) {
                            long start = System.currentTimeMillis();
                            ArrayList<Map.Entry<String, Double>> topj = wordDB.TopJ(stemmer.stem(s_command[1]), Integer.parseInt(s_command[2]), similarityFunction);
                            long end = System.currentTimeMillis();
                            System.out.println("Time taken to calculate TopJ " + ((float) (end - start) / 1000f) + " seconds, using: " + similarityFunction.getMethodName());

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
            } else if (s_command[0].equals("measure")) {
                if (s_command.length == 2) {
                    switch (s_command[1]) {
                        case "cosinesimilarity":
                            similarityFunction = new CosineSimilarity();
                            System.out.println("Similarity measure changed to " + similarityFunction.getMethodName());
                            break;
                        case "negeuclideandist":
                            similarityFunction = new NegEuclideanDist();
                            System.out.println("Similarity measure changed to " + similarityFunction.getMethodName());
                            break;
                        case "normnegeuclideandist":
                            similarityFunction = new NormEuclideanDist();
                            System.out.println("Similarity measure changed to " + similarityFunction.getMethodName());
                            break;
                        default:
                            System.out.println("Invalid similarity method");
                            printMenu();
                    }
                } else {
                    System.out.println("Incorrect Command Usage:");
                    printMenu();
                }

            } else {
                System.err.println("Unrecognized command");
            }
        }
    }
}


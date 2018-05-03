package edu.uiowa.cs.similarity;

import DB.FileParser;
import DB.WordDB;
import Similarity.CosineSimilarity;
import Similarity.NegEuclideanDist;
import Similarity.NormEuclideanDist;
import Similarity.SimilarityFunction;
import Vectors.SemanticVector;
import opennlp.tools.stemmer.PorterStemmer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 *
 */
public class Main {

    /**
     *
     */
    private static void printMenu() {

        System.out.println("Supported commands:");
        System.out.println("help - Print the supported commands");
        System.out.println("quit - Quit this program");
        System.out.println("index FILE - Read in and index  the file given by FILE");
        System.out.println("sentences - Prints the currently stored sentences");
        System.out.println("vectors - Prints the currently stored vectors");
        System.out.println("num TYPE - Prints the number of TYPE data. Ex: num sentence or num vector");
        System.out.println("topj WORD COUNT - Calculates the top COUNT(int) related vectors to WORD(string)." +
                "\n                  Uses cosine by default, see \"measure\" command to change.");
        System.out.println("measure FUNCTION - Options include \"cosine\", \"euc\", or \"eucnorm\".");
        System.out.println("kmeans K ITERS - Generates K clusters iterated through ITERS times.");
    }

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        WordDB wordDB = new WordDB();
        PorterStemmer stemmer = new PorterStemmer();

        SimilarityFunction choosenFunc = new CosineSimilarity();
        //TODO: BEN: Issue 10, Implementing the measure command -- Done

        //TODO: BEN: Looking into cluster class.
        //TODO: BEN: Test data, look for issue 16 bug.
        while (true) {
            System.out.print("> ");

            // Read in and format arguments
            String command = input.readLine();
            command = command.toLowerCase();
            String[] s_command = command.split(" ");

            // Help Command
            //////////////////////////////////////////////////////////////////////////////
            if (s_command[0].equals("help") || s_command[0].equals("h")) {
                printMenu();

                // Quit Command
                //////////////////////////////////////////////////////////////////////////
            } else if (s_command[0].equals("quit") || s_command[0].equals("q")) {
                System.exit(0);

                // Index Command
                //////////////////////////////////////////////////////////////////////////
            } else if (s_command[0].equals("index") || s_command[0].equals("i")) {
                if (s_command.length == 2) {
                    wordDB.index(s_command[1]);
                } else {
                    System.out.println("Incorrect Command Usage:");
                    printMenu();
                }

                // Print Sentences Command
                //////////////////////////////////////////////////////////////////////////
            } else if (s_command[0].equals("sentences") || s_command[0].equals("s")) {
                System.out.println(wordDB.getAllSentences());

                // Print Vectors Command
                //////////////////////////////////////////////////////////////////////////
            } else if (s_command[0].equals("vectors") || s_command[0].equals("v")) {
                for (SemanticVector vector : wordDB.getVectors()) {
                    System.out.println(vector.getWord());
                    System.out.println(vector.getVector());
                }

                // Print Quantities Command
                //////////////////////////////////////////////////////////////////////////
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

                // Top-J Command
                //////////////////////////////////////////////////////////////////////////
            } else if (s_command[0].equals("topj")) {
                if (s_command.length == 3) {
                    if (wordDB.contains(stemmer.stem(s_command[1]))) {
                        stemmer.reset();
                        if (FileParser.isNumeric(s_command[2])) {
                            long start = System.currentTimeMillis();
                            ArrayList<Map.Entry<String, Double>> topj = wordDB.TopJ(stemmer.stem(s_command[1]), Integer.parseInt(s_command[2]), choosenFunc);
                            long end = System.currentTimeMillis();
                            System.out.println("Time taken to calculate TopJ " + ((float) (end - start) / 1000f) + " seconds, using: " + choosenFunc.getMethodName());

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
            } else if (s_command[0].equals("measure") || s_command[0].equals("m")) {
                if (s_command[1].equals("cosine")) {
                    choosenFunc = new CosineSimilarity();
                } else if (s_command[1].equals("euc")) {
                    choosenFunc = new NegEuclideanDist();
                } else if (s_command[1].equals("eucnorm")) {
                    choosenFunc = new NormEuclideanDist();
                } else {
                    System.out.println(s_command[1] + " is not a valid function type. See help for more details.");
                }
                System.out.println("Similarity measure is" + choosenFunc.getMethodName());
            } else if (s_command[0].equals("kmeans") || s_command[0].equals("k")) {
                //TODO: Check that k and iters is int
                System.out.println(Integer.parseInt(s_command[1]));
                System.out.println(Integer.parseInt(s_command[2]));
                HashMap<Integer, LinkedList<SemanticVector>> temp = wordDB.k_means(Integer.parseInt(s_command[1]), Integer.parseInt(s_command[2]));
                System.out.println(temp.entrySet().size());
                for (Map.Entry<Integer, LinkedList<SemanticVector>> entry : temp.entrySet()) {
                    System.out.println("Cluster: " + entry.getKey());
                    for (SemanticVector vector : entry.getValue()) {
                        System.out.print(vector.getWord() + ", ");
                    }
                    System.out.println();
                }
            } else {
                System.err.println("Unrecognized command");
            }
        }
    }
}


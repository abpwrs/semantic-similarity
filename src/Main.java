import DB.FileParser;
import DB.WordDB;
import SimilarityFunctions.CosineSimilarity;
import SimilarityFunctions.NegEuclideanDist;
import SimilarityFunctions.NormEuclideanDist;
import SimilarityFunctions.SimilarityFunction;
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
        System.out.println("representatives J - prints the final clusters using only the Top-J representatives, for " +
                "each cluster of the latest run of k-means.");
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

                // Print SimilarityVectors Command
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
                            ArrayList<Map.Entry<String, Double>> topj = wordDB.TopJ(stemmer.stem(s_command[1]), Integer.parseInt(s_command[2]), choosenFunc);
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
                // Change Measure Command
                //////////////////////////////////////////////////////////////////////////
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
                System.out.println("SimilarityFunctions measure is " + choosenFunc.getMethodName());

                // K-Means Command
                //////////////////////////////////////////////////////////////////////////
            } else if (s_command[0].equals("kmeans") || s_command[0].equals("k")) {
                if (s_command.length == 3 && FileParser.isNumeric(s_command[1]) && FileParser.isNumeric(s_command[2])) {
                    if (Integer.parseInt(s_command[2]) < wordDB.numVectors() - 1) {
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
                        System.out.println("K is to large to calculate that many clusters\nDatabase only has: " + wordDB.numVectors() + " elements");
                    }

                } else {
                    System.out.println("Incorrect command usage");
                    printMenu();
                }
            } else if (s_command[0].equals("representatives") || s_command[0].equals("r")) {
                if (s_command.length == 2 && FileParser.isNumeric(s_command[1])) {
                    wordDB.representatives(Integer.parseInt(s_command[1]));
                } else {
                    System.out.println("Incorrect command usage");
                    printMenu();
                }


            } else {
                System.err.println("Unrecognized command");
            }
        }
    }
}


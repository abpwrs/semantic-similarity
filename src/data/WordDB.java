package data;

import Vectors.GenericVector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Possible Word DataBase Class
 */
public class WordDB {
    // TODO: pick one of these two data types
    private int numSentences;
    // Each word has a vector containing it's relation to every other word
    private HashMap<String, GenericVector> words_as_vectors;

    /**
     *
     */
    WordDB() {
        this.words_as_vectors = new HashMap<>();
        this.numSentences = 0;
    }

    /**
     * @param file_data the output of FileParser.parse()
     */
    public void update(ArrayList<HashSet<String>> file_data) {
        // TODO: find a way of updating the DataBase
        // for each word in the file data we need to update the semantic vector of that class
        this.numSentences += file_data.size();


    }

    // TODO: create a HashMap to represent the similarity of each word

    /**
     * @param goal_word
     * @return
     */
    public HashMap<String, Double> similarity(String goal_word) {
        return null;
    }

    /**
     * @return
     */
    public int getNumSentences() {
        return numSentences;
    }

    /**
     * @return
     */
    public int getNumVectors() {
        return words_as_vectors.size();
    }
}

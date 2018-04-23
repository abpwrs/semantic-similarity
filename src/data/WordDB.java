package data;

import Vectors.SemanticVector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Possible Word DataBase Class
 */
public class WordDB {
    // TODO: pick one of these two data types
    // Each word has a vector containing it's relation to every other word
    private HashMap<String, SemanticVector> words_as_vectors;
    // similar to the above, but the semantic vector is just a HashMap
    private HashMap<String, HashMap<String, Integer>> words_as_maps;

    WordDB(HashMap<String, SemanticVector> words_as_vectors, HashMap<String, HashMap<String, Integer>> words_as_maps) {
        this.words_as_maps = words_as_maps;
        this.words_as_vectors = words_as_vectors;
    }

    /**
     * @param file_data the output of FileParser.parse()
     */
    public void update(ArrayList<HashSet<String>> file_data) {
        // TODO: find a way of updating the file
    }

    // TODO: create a HashMap to represent the similarity of each word
    public HashMap<String, Double> similarity(String goal_word) {
        return null;
    }
}

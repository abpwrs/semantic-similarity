package data;

import Vectors.SemanticVector;

import java.util.HashMap;

/**
 * Possible Word DataBase Class
 */
public class WordDB {
    // Each word has a vector containing it's relation to every other word
    private HashMap<String, SemanticVector> words_as_vectors;
    // similar to the above, but the semantic vector is just a HashMap
    private HashMap<String, HashMap<String, Integer>> words_as_maps;

    WordDB(HashMap<String, SemanticVector> words_as_vectors, HashMap<String, HashMap<String, Integer>> words_as_maps) {
        this.words_as_maps = words_as_maps;
        this.words_as_vectors = words_as_vectors;
    }
}

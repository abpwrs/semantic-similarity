package data;

import Similarity.SimilarityFunction;
import Vectors.SemanticVector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Possible Word DataBase Class
 */
public class WordDB {
    // TODO: pick one of these two data types
    // Each word has a vector containing it's relation to every other word
    // if we want to try different vector implementations, we only need to change Semantic Vector to be a
    // GenericVector and then we just need to make sure we have all of the methods we need
    private HashMap<String, SemanticVector> words_as_vectors;
    private ArrayList<HashSet<String>> all_sentences;

    /**
     *
     */
    public WordDB() {
        this.words_as_vectors = new HashMap<>();
        all_sentences = new ArrayList<>();
    }

    /**
     * @param filename
     */
    public void index(String filename) {
        // TODO: find a way of updating the DataBase
        // for each word in the file data we need to update the semantic vector of that class
        System.out.println("Indexing " + filename);
        ArrayList<HashSet<String>> parseResult = FileParser.parse(filename);
        // small null pointer exception to catch
        if (parseResult != null) {
            long start = System.currentTimeMillis();
            all_sentences.addAll(parseResult);
            for (HashSet<String> sentence : parseResult) {
                for (String word : sentence) {
                    if (words_as_vectors.containsKey(word)) {
                        // Case where the vector already exists, so it just needs to be update
                        words_as_vectors.get(word).update(parseResult);
                    } else {
                        // Case where a new vector needs to be created
                        SemanticVector semanticVector = new SemanticVector(word, parseResult);
                        words_as_vectors.put(word, semanticVector);
                    }
                }
            }
            long end = System.currentTimeMillis();
            System.out.println("Time to create/append to Word Database (WordDB) " + ((float) (end - start) / 1000f) + " seconds");
        }
    }

    /**
     * @return
     */
    public int numSentences() {
        return all_sentences.size();
    }

    /**
     * @return
     */
    public ArrayList<HashSet<String>> getAllSentences() {
        return all_sentences;
    }


    /**
     * @return
     */
    public int numVectors() {
        return words_as_vectors.size();
    }

    /**
     * @return
     */
    public Collection<SemanticVector> getVectors() {
        return words_as_vectors.values();
    }

    /**
     * @param word    The word we want to find words similar to
     * @param J       The number of similar words to return
     * @param simFunc A simlilarity function to base the vector relations off of
     * @return
     */
    public HashMap<String, Double> TopJ(String word, Integer J, SimilarityFunction simFunc) {
        SemanticVector base_word = words_as_vectors.get(word);
        //TODO: Isolate the elements we need to compare
        //TODO: calculate similarity for each vector
        // Double sim = simFunc.calculateSimilarity();

        return null;
    }
}

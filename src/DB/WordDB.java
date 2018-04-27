package DB;

import Similarity.SimilarityFunction;
import Vectors.SemanticVector;

import java.util.*;

/**
 * Possible Word DataBase Class
 */
public class WordDB {
    // TODO: pick one of these two data types
    // Each word has a vector containing it's relation to every other word
    // if we want to try different vector implementations, we only need to change Semantic Vector to be a
    // GenericVector and then we just need to make sure we have all of the methods we need
    private HashMap<String, SemanticVector> words_as_vectors;
    HashMap<String, Boolean> updated = new HashMap<>();
    private ArrayList<HashSet<String>> all_sentences;
    private boolean DB_exists;

    private void reset_updated_false() {
        for (String temp : updated.keySet()) {
            updated.put(temp, false);
        }
    }

    public boolean isEmpty() {
        return words_as_vectors.isEmpty();
    }

    /**
     *
     */
    public WordDB() {
        this.words_as_vectors = new HashMap<>();
        this.all_sentences = new ArrayList<>();
        this.DB_exists = false;
    }

    /**
     * @param filename
     */
    //TODO: Optimize Indexing and File Parsing
    public void index(String filename) {
        this.reset_updated_false();
        // for each word in the file data we need to updated the semantic vector of that class
        System.out.println("Indexing " + filename);
        ArrayList<HashSet<String>> parseResult = FileParser.parse(filename);
        // small null pointer exception to catch if file not found
        if (parseResult != null) {
            long start = System.currentTimeMillis();
            this.all_sentences.addAll(parseResult);
            for (HashSet<String> sentence : parseResult) {
                for (String word : sentence) {
                    if (!updated.containsKey(word) || !updated.get(word)) {
                        if (this.words_as_vectors.containsKey(word)) {
                            // Case where the vector already exists, so it just needs to be updated
                            this.words_as_vectors.get(word).update(parseResult);
                            this.updated.put(word, true);
                        } else {
                            // Case where a new vector needsi  to be created
                            SemanticVector semanticVector = new SemanticVector(word, parseResult);
                            this.words_as_vectors.put(word, semanticVector);
                            this.updated.put(word, true);
                        }
                    }
                }
            }
            long end = System.currentTimeMillis();
            System.out.println("Time taken to " + (!this.DB_exists ? "create" : "append to") + " Word Database (WordDB) " + ((float) (end - start) / 1000f) + " seconds");
            this.DB_exists = true;
        }
    }

    /**
     * @return
     */
    public int numSentences() {
        return this.all_sentences.size();
    }

    /**
     * @return
     */
    public ArrayList<HashSet<String>> getAllSentences() {
        return this.all_sentences;
    }


    /**
     * @return
     */
    public int numVectors() {
        return this.words_as_vectors.size();
    }

    public boolean contains(String check) {
        return words_as_vectors.containsKey(check);
    }

    /**
     * @return
     */
    public Collection<SemanticVector> getVectors() {
        return this.words_as_vectors.values();
    }

    /**
     * @param word    The word we want to find words similar to
     * @param J       The number of similar words to return
     * @param simFunc A simlilarity function to base the vector relations off of
     * @return
     */
    public ArrayList<Map.Entry<String, Double>> TopJ(String word, Integer J, SimilarityFunction simFunc) {
        SemanticVector base_word = words_as_vectors.get(word);
        ArrayList<Map.Entry<String, Double>> ret = new ArrayList<>();
        HashMap<String, Double> relation = new HashMap<>();
        for (Map.Entry<String, SemanticVector> elem : words_as_vectors.entrySet()) {
            if (elem.getValue().getVector().containsKey(word)) {
                relation.put(elem.getKey(), simFunc.calculateSimilarity(base_word, elem.getValue()));
            }
        }
        return simFunc.getmax(relation, J);
    }
}

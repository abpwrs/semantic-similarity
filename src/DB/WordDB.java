package DB;

import Similarity.SimilarityFunction;
import Vectors.SemanticVector;

import java.util.*;


/**
 * Possible Word DataBase Class
 */
public class WordDB implements Database {
    //TODO: BEN: Modify to work with GenericVectors... why? it only needs semantic vector.
    //TODO: BEN: Issue 12?

    // Each word has a vector containing it's relation to every other word
    // if we want to try different vector implementations, we only need to change Semantic Vector to be a
    // GenericVector and then we just need to make sure we have all of the methods we need
    private HashMap<String, SemanticVector> words_as_vectors;
    HashMap<String, Boolean> updated = new HashMap<>();
    private ArrayList<ArrayList<String>> all_sentences;
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
    //TODO: Optimize Indexing and File Parsing - Done
    @Override
    public void index(String filename) {
        this.reset_updated_false();
        // for each word in the file data we need to updated the semantic vector of that class
        System.out.println("Indexing " + filename);
        ArrayList<ArrayList<String>> parseResult = FileParser.parse(filename);
        // small null pointer exception to catch if file not found
        if (parseResult != null) {
            long start = System.currentTimeMillis();
            //?? TODO: May_1
            // Why is it faster to do this out here? We should write about why for the last question of part 4.
            //      adding the sentences here cut 20 seconds off the index time for war and peace. 82.341 --> 62.33
            this.all_sentences.addAll(parseResult);
            for (ArrayList<String> sentence : parseResult) {
                for (String word : sentence) {

                    // THIS LINE IS WHY OUR CODE RUNS QUICKLY:
                    // We only have to update each word once if we keep a boolean array of words
                    // This keeps us from double/triple/etc... counting.
                    // because this dataset is so sparse -- i.e. words are repetitive,
                    // this saves us a lot of unnecessary computation
                    ////////////////////////////////////////////////////////
                    if (!updated.containsKey(word) || !updated.get(word)) {
                        ////////////////////////////////////////////////////

                        if (this.words_as_vectors.containsKey(word)) {
                            // Case where the vector already exists, so it just needs to be updated
                            this.words_as_vectors.get(word).update(parseResult);
                        } else {
                            // Case where a new vector needs to be created
                            SemanticVector semanticVector = new SemanticVector(word, parseResult);
                            this.words_as_vectors.put(word, semanticVector);
                        }

                        this.updated.put(word, true);
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
    public ArrayList<ArrayList<String>> getAllSentences() {
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
     * @param simFunc A similarity function to base the vector relations off of
     * @return
     */
    public ArrayList<Map.Entry<String, Double>> TopJ(String word, Integer J, SimilarityFunction simFunc) {
        if (J > words_as_vectors.size() - 1) {
            System.out.println("Error: not enough elements to compute TopJ");
            return null;
        }
        SemanticVector base_word = words_as_vectors.get(word);
        //TODO: May_1 part 3, why we didn't use ArrayList
        HashMap<String, Double> relation = new HashMap<>();
        for (Map.Entry<String, SemanticVector> elem : words_as_vectors.entrySet()) {
            if (elem.getValue().getVector().containsKey(word)) {
                relation.put(elem.getKey(), simFunc.calculateSimilarity(base_word, elem.getValue()));
            }
        }

        // at this point most_related contains all of the elements that are actually  related
        ArrayList<Map.Entry<String, Double>> most_related = simFunc.getMostRelated(relation, J);

        // this fills in the rest of the values with unrelated values if not enough words are actually related
        for (Map.Entry<String, SemanticVector> elem : words_as_vectors.entrySet()) {
            if (most_related.size() >= J) {
                break;
            } else if (!relation.containsKey(elem.getKey())) {
                relation.put(elem.getKey(), simFunc.getUnrelatedValue());
                most_related.add(new AbstractMap.SimpleEntry<>(elem.getKey(), simFunc.calculateSimilarity(base_word, words_as_vectors.get(elem.getKey()))));
            }
        }
        return most_related;
    }
}

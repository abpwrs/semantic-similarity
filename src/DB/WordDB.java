package DB;

import Similarity.CosineSimilarity;
import Similarity.NegEuclideanDist;
import Similarity.SimilarityFunction;
import Vectors.SemanticVector;

import java.util.*;


/**
 * Word DataBase Class
 */
public class WordDB implements Database {
    // Each word has a vector containing it's relation to every other word
    // if we want to try different vector implementations, we only need to change Semantic Vector to be a
    // GenericVector and then we just need to make sure we have all of the methods we need
    // TODO: implement WordDB.size() method to validate k; -- may not be neccesary, we already have methods to get the number of vectors...


    // ATTRIBUTES
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Primary Data Structure to map each word to it's corresponding vector
     */
    private HashMap<String, SemanticVector> words_as_vectors;

    /**
     * the updated HashMap is used to increase the efficiency of our program
     * updated stops the indexing function from recreating the same vector multiple times
     * i.e. no double/triple indexing of words
     */
    private HashMap<String, Boolean> updated;

    /**
     * this is a data structure used
     */
    private ArrayList<ArrayList<String>> all_sentences;

    /**
     * a boolean used for diagnostic printing
     */
    private boolean DB_exists;
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////


    // CONSTRUCTOR
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Constructor instantiates each of the internal data structures
     */
    public WordDB() {
        this.words_as_vectors = new HashMap<>();
        this.all_sentences = new ArrayList<>();
        this.updated = new HashMap<>();
        this.DB_exists = false;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////


    // UTILITY FUNCTIONS (i.e. private functions)
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * utility function to reset the updated HashMap for each indexing of a new file
     */
    private void reset_updated_false() {
        for (String temp : updated.keySet()) {
            updated.put(temp, false);
        }
    }

    /**
     * @param cluster a single cluster to calculate the average of
     * @return a SemanticVector that is representative of the centroid of the cluster
     */
    private SemanticVector calculate_centroid(LinkedList<SemanticVector> cluster) {
        //SimilarityFunction similarityFunction = new NegEuclideanDist();
        //Double min_cost = Double.NEGATIVE_INFINITY;
        //SemanticVector min_word = null;
        //for (SemanticVector main : cluster) {
        //    Double word_cost = 0.0;
        //    for (SemanticVector sub : cluster) {
        //        word_cost += similarityFunction.calculateSimilarity(main, sub);
        //    }
        //}
        SemanticVector center = new SemanticVector();
        for (SemanticVector val : cluster) {
            center.update(val);
        }
        center.normalizeBy(cluster.size());
        return center;
    }

    /**
     * @return returns a random SemanticVector in the DataSet
     */
    private SemanticVector sampleWithoutReplacement() {
        Random generator = new Random();
        Object[] keys = this.words_as_vectors.keySet().toArray();
        Object randomKey = keys[generator.nextInt(keys.length)];
        String key = (String) randomKey;
        return this.words_as_vectors.get(key);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////


    // PRIMARY METHODS FOR THE PROJECT
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * @param filename the file name to be read and added to the database
     */
    @Override
    public void index(String filename) {
        this.reset_updated_false();
        // for each word in the file data we need to updated the semantic vector of that class
        System.out.println("Indexing " + filename);
        ArrayList<ArrayList<String>> parseResult = FileParser.parse(filename);
        // small null pointer exception to deal with if file is not found
        if (parseResult != null) {
            // capture start time
            long start = System.currentTimeMillis();
            // Why is it faster to do this out here? We should write about why for the last question of part 4.
            //      adding the sentences here cut 20 seconds off the index time for war and peace. 82.341 --> 62.33

            // update the sentences data structure
            this.all_sentences.addAll(parseResult);
            for (ArrayList<String> sentence : parseResult) {
                for (String word : sentence) {

                    // THIS LINE IS PART OF WHY OUR CODE RUNS QUICKLY:
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
            // capture end time
            long end = System.currentTimeMillis();
            // Diagnostic Printing
            System.out.println("Time taken to " + (!this.DB_exists ? "create" : "append to") + " Word Database (WordDB) " + ((float) (end - start) / 1000f) + " seconds");
            this.DB_exists = true;
        }
    }

    /**
     * @param word    The word we want to find words similar to
     * @param J       The number of similar words to return
     * @param simFunc A similarity function to base the vector relations off of
     * @return returns the J most related words
     */
    public ArrayList<Map.Entry<String, Double>> TopJ(String word, Integer J, SimilarityFunction simFunc) {
        if (J > this.words_as_vectors.size() - 1) {
            System.out.println("Error: not enough elements to compute TopJ");
            return null;
        }
        long start = System.currentTimeMillis();
        SemanticVector base_word = this.words_as_vectors.get(word);
        HashMap<String, Double> relation = new HashMap<>();
        for (Map.Entry<String, SemanticVector> elem : this.words_as_vectors.entrySet()) {
            if (elem.getValue().getVector().containsKey(word)) {
                relation.put(elem.getKey(), simFunc.calculateSimilarity(base_word, elem.getValue()));
            }
        }

        // at this point most_related contains all of the elements that are actually  related
        ArrayList<Map.Entry<String, Double>> most_related = simFunc.getMostRelated(relation, J);

        // this fills in the rest of the values with unrelated values if not enough words are actually related
        for (Map.Entry<String, SemanticVector> elem : this.words_as_vectors.entrySet()) {
            if (most_related.size() >= J) {
                break;
            } else if (!relation.containsKey(elem.getKey())) {
                //relation.put(elem.getKey(), simFunc.calculateSimilarity(base_word, elem.getValue()));
                relation.put(elem.getKey(), simFunc.getUnrelatedValue());
                most_related.add(new AbstractMap.SimpleEntry<>(elem.getKey(), simFunc.calculateSimilarity(base_word, words_as_vectors.get(elem.getKey()))));
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("Time taken to calculate TopJ " + ((float) (end - start) / 1000f) + " seconds, using: " + simFunc.getMethodName());
        return most_related;
    }

    /**
     * @param k     the number of clusters we want to find
     * @param iters the number of iterations/refinements we want to use to calculate our clusters
     * @return returns a HashMap of clusters
     */
    public HashMap<Integer, LinkedList<SemanticVector>> k_means(int k, int iters) {
        long start = System.currentTimeMillis();
        //TODO: BEN: Verify that there's an existing DB

        // No longer need a magnitudes HashMap
//        HashMap<String, Double> magnitudes = new HashMap<>();
//
//        for (SemanticVector temp : words_as_vectors.values()) {
//            magnitudes.put(temp.getWord(), temp.getMagnitude());
//        }

        SimilarityFunction simFunc = new NegEuclideanDist();
        //SimilarityFunction simFunc = new CosineSimilarity();

        SemanticVector means[] = new SemanticVector[k];

        // attempt that selected initial values based on which vectors had the greatest magnitudes
//        for (int i = 0; i < k; i++) {
//            String max = Collections.max(magnitudes.entrySet(), Map.Entry.comparingByValue()).getKey();
//            means[i] = words_as_vectors.get(max);
//            magnitudes.remove(max);
//
//        }


        // sample without replacement for means initial values
        for (int i = 0; i < k; i++) {
            means[i] = this.sampleWithoutReplacement();
        }

        HashMap<Integer, LinkedList<SemanticVector>> clusters = new HashMap<>();

        // K-means calculation
        for (int iter = 0; iter < iters; ++iter) {
            clusters = new HashMap<>();

            for (int i = 0; i < k; i++) {
                clusters.put(i, new LinkedList<>());
            }

            for (Map.Entry<String, SemanticVector> point : this.words_as_vectors.entrySet()) {
                // assign to clusters
                int min_means_index = 0;
                Double min_relation = Double.NEGATIVE_INFINITY;

                for (int i = 0; i < k; i++) {
                    Double temp = simFunc.calculateSimilarity(point.getValue(), means[i]);
                    if (temp > min_relation) {
                        min_relation = temp;
                        min_means_index = i;
                    }
                }

                LinkedList<SemanticVector> temp = clusters.get(min_means_index);
                temp.add(point.getValue());
                clusters.put(min_means_index, temp);
            }
            //TODO: Alex: EFFICIENCY
            for (int i = 0; i < k; i++) {
                // adjust means
                means[i] = this.calculate_centroid(clusters.get(i));

            }

            // remove the mean from the vector

        }
        long end = System.currentTimeMillis();
        System.out.println("Time taken to calculate K-Means " + ((float) (end - start) / 1000f) + " seconds, using: " + simFunc.getMethodName());


        return clusters;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////


    // OTHER USEFUL PUBLIC FUNCTIONS THAT WOULD BE STANDARD
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Resets the Database to be empty
     */
    public void RESET_DB() {
        // the clears aren't necessary, but make me feel better
        System.out.println("Are you sure you want to reset the DataBase? (y/n)");
        Scanner in = new Scanner(System.in);
        if (in.nextLine().toLowerCase().equals("y")) {
            this.words_as_vectors.clear();
            this.words_as_vectors = new HashMap<>();
            this.all_sentences.clear();
            this.all_sentences = new ArrayList<>();
            this.updated.clear();
            this.updated = new HashMap<>();
            this.DB_exists = false;
        } else {
            System.out.println("Solid choice!\nKeeping the DB as is.");
        }
    }

    /**
     * @return the size of the sentences ArrayList
     */
    public int numSentences() {
        return this.all_sentences.size();
    }

    /**
     * @return All of the sentences that have been indexed into the DB
     */
    public ArrayList<ArrayList<String>> getAllSentences() {
        return this.all_sentences;
    }


    /**
     * @return the size of the vectors HashMap
     */
    public int numVectors() {
        return this.words_as_vectors.size();
    }

    /**
     * @param check a word that may or may not exist in the DB
     * @return a boolean representing whether or not the word is in the database
     */
    public boolean contains(String check) {
        return this.words_as_vectors.containsKey(check);
    }

    /**
     * @return the semantic vectors stored in the DB
     */
    public Collection<SemanticVector> getVectors() {
        return this.words_as_vectors.values();
    }

    /**
     * @return a boolean value representing the status of the database
     */
    public boolean isEmpty() {
        return words_as_vectors.isEmpty();
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////


}
package DB;

import opennlp.tools.stemmer.PorterStemmer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Class designed to parse any file of strings
 */
public class FileParser {

    /**
     * A LinkedList of words that are too common to be of value
     */
    private static LinkedList<String> STOP_WORDS;

    /**
     * Private Constructor stops anyone from instantiating an object of this type
     */
    private FileParser() {

    }


    // Static Constructor to initialize the STOPWORDS LinkedList
    static {
        try {
            Scanner stopFile = new Scanner(new File("src/data/stopwords.txt"));
            STOP_WORDS = new LinkedList<>();
            stopFile.useDelimiter("\n");
            while (stopFile.hasNext()) {
                STOP_WORDS.add(stopFile.next());
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Cannot find stopwords.txt \n Killing Program.");
        }
    }


    /**
     * @param fileName the name of the file to be parsed
     * @return a HashSet of sentences containing unique and validated words as strings
     */
    public static ArrayList<ArrayList<String>> parse(String fileName) {
        try {
            Scanner fileScanner = new Scanner(new File(fileName));
            fileScanner.useDelimiter("\n");
            StringBuilder builder = new StringBuilder();
            ArrayList<ArrayList<String>> result = new ArrayList<>();
            // open source implementation of the porter stemming algorithm
            PorterStemmer porterStemmer = new PorterStemmer();

            // Builds one big string
            while (fileScanner.hasNext()) {
                builder.append(fileScanner.next()).append(" ");
            }
            // convert StringBuilder to String
            String words = builder.toString();
            // get rid of caps
            words = words.toLowerCase();
            // get rid of in sentence punctuation
            words = words.replaceAll("\\(|\\)|,|, |--|:|;|\"|'|' ", "");
            // split into sentences
            String[] sentences = words.split("[!?.]");
            for (String sentence : sentences) {
                ArrayList<String> temp = new ArrayList<>();
                // split each sentence into a set of words
                String[] word_arr = sentence.split("\\s");
                for (String word : word_arr) {
                    // validates that the word should be added
                    if (!STOP_WORDS.contains(word) && !word.matches("\\s") && !word.matches("") && !isNumeric(word)) {
                        // adds the stem of the word
                        temp.add(porterStemmer.stem(word));
                        // reset PorterStemmer
                        porterStemmer.reset();
                    }
                }
                // check that temp isn't empty
                if (!temp.isEmpty()) {
                    result.add(temp);
                }
            }

            return result;


        } catch (IOException e) {
            System.out.println("\nFile: " + fileName + " does not exist.\n");
        }

        return null;
    }

    public static boolean isNumeric(String x) {
        try {
            Integer.parseInt(x);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}

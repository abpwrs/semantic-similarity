package data;

import opennlp.tools.stemmer.PorterStemmer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Class designed to parse any file of strings
 */
public class FileParser {

    /**
     * A LinkedList of words that are too common to be of value
     */
    private LinkedList<String> STOPWORDS;

    /**
     * Constructor to initialise the STOPWORDS LinkedList
     */
    public FileParser() {
        try {
            Scanner stopFile = new Scanner(new File("src/data/stopwords.txt"));
            STOPWORDS = new LinkedList<>();
            stopFile.useDelimiter("\n");
            while (stopFile.hasNext()) {
                STOPWORDS.add(stopFile.next());
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
    public HashSet<HashSet<String>> parseFile(String fileName) {
        try {
            Scanner fileScanner = new Scanner(Paths.get(fileName));
            fileScanner.useDelimiter("\n");
            StringBuilder builder = new StringBuilder();
            HashSet<HashSet<String>> result = new HashSet<>();
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
                HashSet<String> temp = new HashSet<>();
                // split each sentence into a set of words
                String[] word_arr = sentence.split("\\s");
                for (String word : word_arr) {
                    // validates that the word should be added
                    if (!STOPWORDS.contains(word) && !word.matches("\\s") && !word.matches("") && !isNumeric(word)) {
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

            // diagnostic printing
            // System.out.println(result);
            return result;


        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("File: " + fileName + " does not exist. \n Killing Program.");
            System.exit(-1);
        }

        System.err.println("Unknown error occurred in FileParser");
        return null;
    }

    private boolean isNumeric(String x) {
        try {
            Integer.parseInt(x);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}

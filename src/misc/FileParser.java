package misc;

import opennlp.tools.stemmer.PorterStemmer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Scanner;

public class FileParser {

    // Private constructor stops anyone from instantiating an object of this type
    // example usage:
    // misc.FileParser.parseFile("hello.txt");
    // could return some data structure that out semantic vector can then store
    private LinkedList<String> STOPWORDS;

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

    // this is a static constructor
    // Returns a list of sentences
    public LinkedList<String> parseFile(String fileName) {
        try {
            Scanner fileScanner = new Scanner(Paths.get(fileName));
            StringBuilder builder = new StringBuilder();

            // TODO: Implement the parsing and cleaning of input
            while (fileScanner.hasNext()) {
                builder.append(fileScanner.next()).append(" ");
            }
            String words = builder.toString();
            words = words.toLowerCase();
            words = words.replaceAll(",|, |--|:|;|\"|'", "");

            System.out.println(words);
            PorterStemmer porterStemmer = new PorterStemmer();


        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("File: " + fileName + " does not exist. \n Killing Program.");
        }

        // TODO: Change Return Type
        return null;
    }
}

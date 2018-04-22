import java.io.IOException;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Scanner;

public class FileParser {

    // Private constructor stops anyone from instantiating an object of this type
    // example usage:
    // FileParser.parseFile("hello.txt");
    // could return some data structure that out semantic vector can then store
    private FileParser() {
    }

    public static LinkedList<String> parseFile(String fileName) {
        try {
            Scanner fileScanner = new Scanner(Paths.get(fileName));


            // TODO: Implement the parsing and cleaning of input

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("File: " + fileName + " does not exist. \n Killing Program.");
            System.exit(-1);
        }

        // TODO: Change Return Type
        return null;
    }
}

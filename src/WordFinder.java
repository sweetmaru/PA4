// Name: Xiaofeng Luo
// USC NetID: 6101697403
// CS 455 PA4
// Fall 2021


import java.io.FileNotFoundException;
import java.util.*;

/**
 * This is the main class to find all the scrabble words with the given rack.
 */
public class WordFinder {
   /**
    * Main method to find the scrabble words and do the error checking
    * @param args
    */
   public static void main(String[] args) {
      Scanner in = new Scanner(System.in);
      ScoreTable scoreTable = new ScoreTable();

      //get the dictionary file name
      String fileName = getFileName(args);

      try{
         //create the anagram dictionary
         AnagramDictionary anagram = new AnagramDictionary(fileName);
         System.out.println("Type . to quit.");
         //compute for each input rack
         while(in.hasNextLine()) {
            System.out.print("Rack? ");
            String input = in.nextLine();
            Rack rack = new Rack();
            Map<String, Integer> scrabble = new TreeMap<>(); //the map which stores the scrabble words and scores
            ArrayList<String> allSubsets = new ArrayList<>();
            if (input.equals(".")) {
               break;
            } else {
               //put input tiles into the rack and get all the subsets of the rack
               allSubsets = putIntoRack(rack, input);
            }

            //get the anagrams of each subset
            for (String subset : allSubsets) {
               ArrayList<String> words = anagram.getAnagramsOf(subset);
               //compute the score for each scrabble word, then store the words and scores into the scrabble map
               for (String word : words) {
                  Integer score = scoreTable.getScore(word);
                  scrabble.put(word, score);
               }
            }

            //print the two lines in the output before the scrabble words and scores
            print(scrabble, rack);

            //sort the results by scores and print the results
            ArrayList<Map.Entry<String, Integer>> result = new ArrayList<>(scrabble.entrySet());
            Collections.sort(result, new ScoreComparator());
            for (Map.Entry<String, Integer> entry : result) {
               System.out.println(entry.getValue() + ": " + entry.getKey());
            }
         }
      }
      catch(FileNotFoundException exception) {
         printError1(fileName);
      }
      catch(IllegalDictionaryException exception) {
         printError2(exception);
      }

   }


   /**
    * Get the filename of the dictionary
    *
    * @param args the program argument
    * @return the filename
    */
   private static String getFileName(String[] args){
      String filename = "";
      if(args.length > 0){
         filename = args[0];
      }
      else filename = "sowpods.txt";
      return filename;
   }

   /**
    * Put input tiles into the rack and get all the subsets of the rack
    *
    * @param rack the initial empty rack
    * @param input input
    * @return the array list of all the subsets of the rack
    */
   private static ArrayList<String> putIntoRack(Rack rack, String input){
      for(int i = 0; i < input.length(); i++){
         rack.addTile(String.valueOf(input.charAt(i)));
      }
      return rack.getAllSubsets(rack.getTiles());
   }

   /**
    * Print the two lines in the output before the scrabble words and scores
    *
    * @param scrabble the map which stores the scrabble words and scores
    * @param rack the rack
    */
   private static void print(Map<String, Integer> scrabble, Rack rack){
      System.out.println("We can make " + scrabble.size() +" words from \"" + rack.getTiles() + "\"");
      if(scrabble.size() != 0){
         System.out.println("All of the words with their scores (sorted by score):");
      }
   }

   /**
    * Print the error message for the FileNotFoundException
    *
    * @param fileName the filename of dictionary
    */
   private static void printError1(String fileName){
      System.out.println("ERROR: Dictionary file \"" + fileName + "\" does not exist.");
      System.out.println("Exiting program.");
   }

   /**
    * Print the error message for the IllegalDictionaryException
    *
    * @param exception illegal dictionary exception
    */
   private static void printError2(IllegalDictionaryException exception){
      System.out.println(exception.getMessage());
      System.out.println("Exiting program.");
   }
}


/**
 * A class implements Comparator and the compare method to decide the order of two entries of a map
 */
class ScoreComparator implements Comparator<Map.Entry<String, Integer>> {
   /**
    * compare two entries by their values
    * @param a entry a
    * @param b entry b
    * @return a negative number if a should come before b, 0 if a and b are equal, or a positive number
    * if a should come after b
    */
   public int compare(Map.Entry<String, Integer> a, Map.Entry<String, Integer> b){
      return b.getValue() - a.getValue();
   }
}
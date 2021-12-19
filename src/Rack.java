// Name: Xiaofeng Luo
// USC NetID: 6101697403
// CS 455 PA4
// Fall 2021


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
   A Rack of Scrabble tiles
 */

public class Rack {

   /**
    * Representation Invariants:
    *
    * --rack cannot be modified.
    * --rack can contain non-word part.
    * --rack is not case-sensitive.
    */

   //The string to store the rack
   private String rack;

   //Create an empty rack
   public Rack(){
      rack = "";
   }

   /**
    * Add an input letter to the rack
    *
    * @param tile input letter
    */
   public void addTile(String tile){
      rack = rack + tile;
   }

   /**
    * Get the current rack
    *
    * @return the current rack
    */
   public String getTiles(){
      return rack;
   }

   /**
    * Get all the subsets of the filtered rack
    *
    * @param rack the current rack
    * @return an array list with all the subsets
    */
   public ArrayList<String> getAllSubsets(String rack){
      String rackFiltered = filter(rack);
      String unique = getUnique(getLetterWithMulti(rackFiltered));
      int[] mult = getMult(getLetterWithMulti(rackFiltered));
      return allSubsets(unique, mult, 0);
   }

   /**
    * Get a map with unique letters in the rack and the corresponding multiplicity of each letter
    *
    * @param rackFiltered the rack with the non-word part removed
    * @return the map with unique letters and corresponding multiplicities
    */
   private Map<Character, Integer> getLetterWithMulti(String rackFiltered){
      Map<Character, Integer> letterWithMulti = new HashMap<>();
      for(int i = 0; i < rackFiltered.length(); i++){
         Character letter = rackFiltered.charAt(i);
         Integer oldValue = letterWithMulti.get(letter);
         if(oldValue == null){
            letterWithMulti.put(letter, 1);
         }
         else letterWithMulti.put(letter, oldValue + 1);
      }
      return letterWithMulti;
   }

   /**
    * Filter the non-word part of the rack
    *
    * @param rack the current rack
    * @return the filtered rack
    */
   private String filter(String rack){
      return rack.replaceAll("(\\A[^\\w]+)|([^\\w]+\\z)", "");
   }

   /**
    * Get the string of unique letters
    *
    * @param letterWithMulti the map with unique letters and corresponding multiplicities
    * @return the string of unique letters
    */
   private String getUnique(Map<Character, Integer> letterWithMulti){
      String unique = "";
      for(Map.Entry<Character, Integer> entry: letterWithMulti.entrySet()){
         unique = unique + entry.getKey();
      }

      return unique;
   }

   /**
    * Get the array of multiplicities of letters
    *
    * @param letterWithMulti the map with unique letters and corresponding multiplicities
    * @return the array of multiplicities
    */
   private int[] getMult(Map<Character, Integer> letterWithMulti){
      int[] mult = new int[letterWithMulti.size()];
      int i = 0;
      for(Map.Entry<Character, Integer> entry: letterWithMulti.entrySet()){
         mult[i] = entry.getValue();
         i++;
      }

      return mult;
   }

   /**
      Finds all subsets of the multiset starting at position k in unique and mult.
      unique and mult describe a multiset such that mult[i] is the multiplicity of the char
           unique.charAt(i).
      PRE: mult.length must be at least as big as unique.length()
           0 <= k <= unique.length()
      @param unique a string of unique letters
      @param mult the multiplicity of each letter from unique.  
      @param k the smallest index of unique and mult to consider.
      @return all subsets of the indicated multiset.  Unlike the multiset in the parameters,
      each subset is represented as a String that can have repeated characters in it.
      @author Claire Bono
    */
   private static ArrayList<String> allSubsets(String unique, int[] mult, int k) {
      ArrayList<String> allCombos = new ArrayList<>();
      
      if (k == unique.length()) {  // multiset is empty
         allCombos.add("");
         return allCombos;
      }
      
      // get all subsets of the multiset without the first unique char
      ArrayList<String> restCombos = allSubsets(unique, mult, k+1);
      
      // prepend all possible numbers of the first char (i.e., the one at position k) 
      // to the front of each string in restCombos.  Suppose that char is 'a'...
      
      String firstPart = "";          // in outer loop firstPart takes on the values: "", "a", "aa", ...
      for (int n = 0; n <= mult[k]; n++) {   
         for (int i = 0; i < restCombos.size(); i++) {  // for each of the subsets 
                                                        // we found in the recursive call
            // create and add a new string with n 'a's in front of that subset
            allCombos.add(firstPart + restCombos.get(i));  
         }
         firstPart += unique.charAt(k);  // append another instance of 'a' to the first part
      }
      
      return allCombos;
   }

   
}
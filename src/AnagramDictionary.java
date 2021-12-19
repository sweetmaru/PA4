// Name: Xiaofeng Luo
// USC NetID: 6101697403
// CS 455 PA4
// Fall 2021

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


/**
   A dictionary of all anagram sets. 
   Note: the processing is case-sensitive; so if the dictionary has all lower
   case words, you will likely want any string you test to have all lower case
   letters too, and likewise if the dictionary words are all upper case.
 */
public class AnagramDictionary {

   /**
    * Representation Invariants:
    *
    * --anagramDic has the canonical forms of words in the dictionary file as keys.
    * --anagramDic has the set of unique values for each key, i.e. there is no duplicate.
    * --inFile must scan a dictionary file which exists.
    */

   private Map<String, Set<String>> anagramDic;
   private Scanner inFile;


   /**
      Create an anagram dictionary from the list of words given in the file
      indicated by fileName.  
      @param fileName  the name of the file to read from
      @throws FileNotFoundException  if the file is not found
      @throws IllegalDictionaryException  if the dictionary has any duplicate words
    */
   public AnagramDictionary(String fileName) throws FileNotFoundException,
                                                    IllegalDictionaryException {
      anagramDic = new HashMap<>();
      inFile = new Scanner(new File(fileName));
      //create an anagram dictionary
      while(inFile.hasNext()){
         String word = inFile.next();
         //find the canonical form of the word, which is an ascending numerical order
         String form = sorted(word);
         if(anagramDic.containsKey(form)){
            if(anagramDic.get(form).contains(word)){
               throw new IllegalDictionaryException("ERROR: Illegal dictionary: dictionary file has a duplicate word: "
                     + word);
            }
            //if a word has been in the set of corresponding canonical form, throw exception
            else{
               anagramDic.get(form).add(word);
            }
         }
         else {
            anagramDic.put(form, new HashSet<>());
            anagramDic.get(form).add(word);
         }
      }

   }
   

   /**
      Get all anagrams of the given string. This method is case-sensitive.
      E.g. "CARE" and "race" would not be recognized as anagrams.
      @param string string to process
      @return a list of the anagrams of s
    */
   public ArrayList<String> getAnagramsOf(String string) {
      ArrayList<String> anagrams = new ArrayList<>();
      if(anagramDic.containsKey(sorted(string))){
         anagrams = new ArrayList<>(anagramDic.get(sorted(string)));
      }
      return anagrams;
   }


   /**
    * Get the canonical form of the word, which is an ascending numerical order
    *
    * @param string the given word
    * @return the canonical form of the word
    */
   private String sorted(String string){
      char[] letter = string.toCharArray();
      Arrays.sort(letter);
      return String.valueOf(letter);
   }

   
}

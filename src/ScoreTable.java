// Name: Xiaofeng Luo
// USC NetID: 6101697403
// CS 455 PA4
// Fall 2021

import java.lang.String;
import java.util.Locale;

/**
 * This class contains a score table for the scrabble letters.
 * And uses this table to compute the total score of a scrabble word.
 */
public class ScoreTable {
   //the score table
   private static final int[] SCORE_TABLE = {1, 3, 3, 2, 1, 4, 2,
                                            4, 1, 8, 5, 1, 3, 1,
                                            1, 3, 10, 1, 1, 1,
                                            1, 4, 4, 8, 4, 10};

   /**
    * This method computes the total sum of the scrabble word
    * by indexing the array with the char at each position of the word.
    *
    * @param word a scrabble word
    * @return the score of the given word
    */
   public int getScore(String word){
      String lowercase = word.toLowerCase(Locale.ROOT);
      int sum = 0;
      for(int i = 0; i < word.length(); i++){
         sum += SCORE_TABLE[lowercase.charAt(i) - 'a'];
      }
      return sum;
   }
}

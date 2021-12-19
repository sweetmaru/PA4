import java.util.ArrayList;

public class RackTester {
   public static void main(String[] args) {
      Rack rack1 = new Rack();
      doOneTest(rack1,"aesa");
   }

   private static void doOneTest(Rack rack, String letters){
      rack.addTile(letters);
      System.out.println("The current rack: exp:[" + letters + "] " + rack.getTiles());
      ArrayList<String> subsets = rack.getAllSubsets(rack.getTiles());
      for(String s: subsets){
         System.out.println(s);
      }
   }
}

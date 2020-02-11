import jdk.jfr.Description;

import java.io.*;
import java.util.*;


public class Main {

    public static void main(String[] args) throws IOException {

        Set<String> uniqueItems = new HashSet<>();
        //store unique items in the dataset

        List<String> buckets;
        //a collection of all items in one line

        Hashtable<String,Integer> database = new Hashtable<>();
        //a collection of all items with their support #

        //File input/output stream
        BufferedReader in = new BufferedReader(new FileReader("data/retail.txt"));
        BufferedWriter output = new BufferedWriter(new FileWriter("data/GeneratedFrequentItems.txt"));

        String currentLine; //current line scanned by BufferedReader
        while ((currentLine = in.readLine()) != null) {
            //output.write(currentLine+"\n");
            buckets = Arrays.asList(currentLine.split(" "));
            uniqueItems.addAll(buckets); //get all unique items

            Set<String> tempSet = new HashSet<>(); //remove duplicate items
            tempSet.addAll(buckets);
            for(String item: tempSet) {     //get the support of each item
                if (database.containsKey(item)) {
                    database.put(item, database.get(item) + 1);
                } else {
                    database.put(item, 1);
                }
            }
        }

        //generate 1st sequences
        Map<String,Integer> temp = minePatterns(database,uniqueItems,40);
        temp.forEach((k,v) ->{
            try {
                output.write("Key: "+ k + " Value: " + v +"\n");
            }catch (IOException e){
                e.printStackTrace();
            }

        });

//        database.forEach((k,v) -> {
//            try {
//                output.write("Key: "+ k + " Value: " + v +"\n");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });

        output.close();
        in.close();
    }


    /**
     * To generate frequent items and frequent pairs
     * @param database a collection of item name OR ID with their frequencies
     * @param uniqueItems a collection of all unique items in the dataset
     * @param support support threshold
     */
    public static Map<String,Integer> minePatterns(Map<String, Integer> database, Set<String> uniqueItems,int support){

        Hashtable<String,Integer> frequentItems = new Hashtable<>();
        database.forEach((k,v) ->{
            if(v >= support){
                frequentItems.put(k,v);
            }
        });

        return frequentItems;
    }
}

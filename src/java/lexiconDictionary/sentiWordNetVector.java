package lexiconDictionary;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Vector;

public class sentiWordNetVector {

    private static String pathToSWN = "h:/Extra/db/dataset/sentiwordNET/SentiWordNet_3.txt";
    private static HashMap _dict;

    static {
        _dict = new HashMap<String, String>();
        HashMap<String, Vector<Double>> _temp = new HashMap<String, Vector<Double>>();
        try {

            BufferedReader csv = new BufferedReader(new FileReader(pathToSWN));
            String line = "";
            BasicDBObject x = new BasicDBObject();
            BasicDBObject d = new BasicDBObject();
            BasicDBObject f = new BasicDBObject();
            BasicDBList array = new BasicDBList();
            while ((line = csv.readLine()) != null) {
                String[] data = line.split("\t");
                Double score = Double.parseDouble(data[2]) - Double.parseDouble(data[3]);
                String[] words = data[4].split(" ");
                String temp[];
                temp = words[0].split("#");
                f.put("word", temp[0]);
                array = new BasicDBList();
                for (int i = 1; i < words.length; i++) {
                    temp = words[i].split("#");
                    array.add(temp[0]);
                }
                d.put("synset", array);
                x.put("$push", d);
            }
        } catch (Exception e) {
        }
    }

    public static Double getPolarity(String word, String pos) {
        return (Double) _dict.get(word + "#" + pos);
    }
}

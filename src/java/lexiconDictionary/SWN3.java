package lexiconDictionary;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

public class SWN3 {
	private String pathToSWN ="dataset/sentiwordNET/SentiWordNet_3.txt";
	private HashMap _dict;

        public static void main (String a[]) throws UnknownHostException
{
        SWN3 s=new SWN3();
}
	public SWN3() throws UnknownHostException{
                        Mongo m=new Mongo();
                        DB db=m.getDB("opinionDB");
                        DBCollection coll=db.getCollection("sentiwordnet");
			_dict = new HashMap<String, String>();
			HashMap<String, Vector<Double>> _temp = new HashMap<String, Vector<Double>>();
			try{				
				//System.out.print("File found:");

				BufferedReader csv =  new BufferedReader(new FileReader(pathToSWN));
				String line = "";
                                BasicDBObject x=new BasicDBObject();
                                BasicDBObject d=new BasicDBObject();
                                BasicDBObject f=new BasicDBObject();
                                BasicDBList array=new BasicDBList();
				while((line = csv.readLine()) != null)
				{
					String[] data = line.split("\t");
					Double score = Double.parseDouble(data[2])-Double.parseDouble(data[3]);
					String[] words = data[4].split(" ");
                                        String temp[];
                                        temp=words[0].split("#");
                                        f.put("word",temp[0]);
                                        //System.out.println(temp[0]);
                                        array=new BasicDBList();
                                        for(int i=1;i<words.length;i++)
                                        {
                                                temp=words[i].split("#");
                                                array.add(temp[0]);
                                         }
                                        d.put("synset",array);
                                        x.put("$push",d);
                                        coll.update(f, x);
                            }
            }
                        catch(Exception e){}
    }


	public Double extract(String word, String pos) {
		return (Double)_dict.get(word + "#" + pos);
	}
}

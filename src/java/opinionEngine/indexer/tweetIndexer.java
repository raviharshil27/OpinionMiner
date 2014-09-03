/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package opinionEngine.indexer;

import com.mongodb.*;
import java.io.*;
import mongoDb.mongoConnection;
import opinionEngine.sentimentAnalyzer.lexiconBaseAnalysis;

/**
 *
 * @author Rasesh shah
 */
public class tweetIndexer {

    static Mongo mongo;
    static DB db;
    static DBCollection coll;
    static final String db_name = "opinionDB", coll_name = "results",dir_path="h:/Extra/ras/";
    lexiconBaseAnalysis lex;
    String dir_name, file_list[];
    int file_no;
    File file_pro;
    FileInputStream file;
    BasicDBObject json;
    BasicDBList json_list;

    static {
        mongo = mongoConnection.getMongo();
        db = mongo.getDB(db_name);
        coll = db.getCollection(coll_name);
    }

    public tweetIndexer(String dirname) {
        lex = new lexiconBaseAnalysis();
        this.dir_name = dirname;
        file_no = 0;
        file_pro = new File(dir_path+ dirname);
        file_list = file_pro.list();
        coll = db.getCollection(dir_name);
    }

    public void setNextFile() {
        try {
            file = new FileInputStream(dir_path + dir_name + "/" + file_list[file_no]);
            file_no++;
        } catch (FileNotFoundException ex) {
            System.out.println("opinionEngine.indexer.tweetIndexer : file not found");
        }
    }

    public void getFile() {
        String temp = "";
        int t;
        try {
            while ((t = file.read()) != -1) {
                temp += (char) (t);
            }
        } catch (IOException ex) {
            System.out.print("opinionEngine.indexer.tweetIndexer : file not able to fetch");
        }
        json = (BasicDBObject) com.mongodb.util.JSON.parse(temp);
    }

    public void getResult() {
        BasicDBObject obj;
        setNextFile();
        getFile();
        json_list = (BasicDBList) json.get("results");
        for (int i = 0; i < json_list.size(); i++) {
            System.out.println(i);
            obj = (BasicDBObject) json_list.get(i);
            obj.put("score", lex.getStmtPolarity(obj.getString("text")));
        }
        obj = new BasicDBObject("result", json_list);
        coll.insert(obj);
    }

    public static void main(String a[]) {
        tweetIndexer t = new tweetIndexer("iphone");
        t.getResult();
    }
}

package lexiconDictionary;

import com.mongodb.*;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import mongoDb.mongoConnection;

public class sentiWordNetMongo
{
    static Mongo mongo;
    static DB db;
    static DBCollection coll;
    static final String db_name="opinionDB",coll_name="sentiwordnet";
    static
    {
        try {
            mongo=new Mongo();
        } catch (UnknownHostException ex) {
            Logger.getLogger(sentiWordNetMongo.class.getName()).log(Level.SEVERE, null, ex);
        }
        db=mongo.getDB(db_name);
        coll=db.getCollection(coll_name);
    }

    //get polarity from mongo db of word using this method by providing word and its part of speech token

    public static Double getPolarity(String word,String pos)
    {
        BasicDBObject word_detail=new BasicDBObject();
        word_detail.put("word",word);
        word_detail.put("pos", pos);
        DBCursor cur=coll.find(word_detail);
        while(cur.hasNext())
        {
           BasicDBObject result=(BasicDBObject) cur.next();
           return result.getDouble("score");
        }
        return 0.0;
    }

}

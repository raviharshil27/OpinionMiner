/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mongoDb;

import com.mongodb.Mongo;
import java.net.UnknownHostException;

/**
 *
 * @author Rasesh shah
 */

public class mongoConnection
{
    static Mongo m;
    static
    {
        try {
            m = new Mongo();
        }
        catch (UnknownHostException ex)
        {
            System.out.print("mongoDb:mongoConnection fail to connect");
        }
    }

    public static Mongo getMongo()
    {
        return m;
    }
}

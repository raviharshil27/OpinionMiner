/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package opinionEngine.dataPuller;

import java.io.*;
import java.net.*;

/**
 *
 * @author Rasesh
 */

public class twitterDataPuller
{
    String object,requestString,lang,rpp,page,result_type,json,dir_path;
    int count;
    int TOTALCOUNT=15;
    URL requestURL;
    BufferedInputStream response;
    FileOutputStream file;
    public Thread thread;
    public static void main(String a[])
    {
        twitterDataPuller t=new twitterDataPuller();
        t.fetch("iphone");
    }

    public twitterDataPuller()
    {
        json="";
        lang="en";
        rpp="100";
        count=1;
        dir_path="h:/Extra/ras/";
    }

    public void fetch(String object)
    {
        this.object=object;
        boolean b= new File(dir_path+object).mkdir();
        while(count!=TOTALCOUNT)
        {
                try
                {
                    requestString="http://search.twitter.com/search.json?q="+URLEncoder.encode(object, "UTF-8")+"&page="+count+"&rpp="+rpp+"&lang="+lang  ;
                    requestURL = new URL(requestString);
                    java.net.URLConnection conn=requestURL.openConnection();
                    response=new BufferedInputStream(conn.getInputStream());

                    int temp;
                    json="";
                    while((temp=response.read())!=-1)
                    {
                        json+=(char)(temp);
                    }
                    file=new FileOutputStream(new File(dir_path+object+"/"+object+count+".json"));
                    file.write(json.getBytes());
                    file.close();
                    System.out.println("Data Puller Thread "+object+" count "+count);
                    count++;
                    page=count+"";
                }
                catch (Exception e)
                {
                    System.out.println(e.toString());
                }

     
        }
    }
}

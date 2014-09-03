/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.mongodb.*;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Set;
import javax.servlet.ServletException;

import javax.servlet.http.*;
import mongoDb.mongoConnection;
import opinionEngine.dataPuller.twitterDataPuller;
import opinionEngine.indexer.tweetIndexer;

/**
 *
 * @author Rasesh shah
 */
public class tweetfetch extends HttpServlet
{

    Mongo mongo;
    DB db;
    DBCollection coll;
    final String db_name = "opinionDB", coll_name = "results";
    String q;
    twitterDataPuller pull;
    tweetIndexer in;
    @Override
    public void init() throws ServletException
    {
        super.init();
        mongo =mongoConnection.getMongo();
        db = mongo.getDB(db_name);
        coll = db.getCollection(coll_name);
        pull=new twitterDataPuller();
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        q = request.getParameter("search_text");
        ArrayList<String> ar = new ArrayList<String>();
        Set arr=db.getCollectionNames();
        if(!arr.contains(q))
        {    
            pull=new twitterDataPuller();
            pull.fetch(q);
            in=new tweetIndexer(q);
            in.getResult();
        }
        response.setContentType("application/json");
        coll = db.getCollection(q);
        DBCursor cur = coll.find();
        DBObject obj = cur.next();
        try
        {
        String json = com.mongodb.util.JSON.serialize(obj);
        response.getWriter().write(json);
        }
        catch(Exception e)
        {
            System.out.print("not found");
        }
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}

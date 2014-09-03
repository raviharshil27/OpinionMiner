<%-- 
    Document   : error
    Created on : May 22, 2013, 6:58:43 PM
    Author     : Harshil
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%= request.getParameter("search_text") %>
        <h1>Enter proper query.....</h1>
    </body>
</html>

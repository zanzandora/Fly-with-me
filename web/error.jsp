<%-- 
    Document   : error.jsp
    Created on : Nov 24, 2024, 3:39:21 PM
    Author     : pc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
<% 
    String errorMessage = (String) session.getAttribute("errorMessage");
    String errorDetails = (String) session.getAttribute("errorDetails");
    if (errorMessage != null) {
%>
    <div class="error-message">
        <h2>Đã xảy ra lỗi:</h2>
        <p><%= errorMessage %></p>
        <pre><%= errorDetails %></pre>
    </div>
<%
    }
%>
    </body>
</html>

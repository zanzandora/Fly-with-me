<%-- 
    Document   : navbar_user
    Created on : Nov 9, 2024, 11:20:03 PM
    Author     : pc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpSession" %>
<%
     String baseURL = getServletContext().getInitParameter("baseURL");
    if (session == null) {
        session = request.getSession(false);
    }
    String hodem = (session != null && session.getAttribute("hodem") != null) ? 
                   (String) session.getAttribute("hodem") : "Lovely";
    String ten = (session != null && session.getAttribute("ten") != null) ? 
                 (String) session.getAttribute("ten") : "Guest";
    String userId = (session != null && session.getAttribute("id") != null) ? 
                 (String) session.getAttribute("id") : "Guest";
%>

<nav class="navbar">
    
    
        <div class="nav-container">
            <a href="<%= baseURL %>/app.jsp" class="logo">Plush Airlines</a>
            <ul class="nav-links">
                            
                <li>
                    <div class="user-nickname">
                        Welcome, <%= hodem %> <%= ten %>
                        <div class="user-dropdown">
                            <a href="#">Profile</a>
                            <a href="<%= baseURL %>/BookingFlightServlet">My Booking</a>
                            <a href="LogOutServlet">Logout</a>
                        </div>
                    </div>
                </li>
            </ul>
        </div>
    </nav>

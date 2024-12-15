<%-- 
    Document   : search_fly
    Created on : Nov 12, 2024, 10:04:11 PM
    Author     : pc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    String baseURL = getServletContext().getInitParameter("baseURL");
%>
<div class="search-container">
    <form class="search-form" method="POST" action="<%= baseURL %>/SearchFlightServlet">
        <div class="form-group">
            <label for="departure">Departure Airport</label>
            <input type="text" id="departure" name="NoiCatCanh" placeholder="Enter departure airport" required>
        </div>
        <div class="form-group">
            <label for="arrival">Arrival Airport</label>
            <input type="text" id="arrival" name="NoiHaCanh" placeholder="Enter arrival airport" required>
        </div>
        <div class="form-group">
            <label for="departure-time">Departure Time</label>
            <input type="datetime-local" id="departure-time" name="ThoiGianCatCanhDanhNghia" required>
        </div>
        <div class="form-group">
            <label for="arrival-time">Arrival Time</label>
            <input type="datetime-local" id="arrival-time" name="ThoiGianHaCanhDanhNghia" required>
        </div>
        
        <button type="submit" class="search-btn" >Search Flights</button>
    </form>
</div>



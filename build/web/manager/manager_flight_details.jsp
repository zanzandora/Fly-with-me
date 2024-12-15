<%-- 
    Document   : airline_flight
    Created on : Nov 20, 2024, 12:36:43 AM
    Author     : pc
--%>

<%@page import="com.flywithme.dao.CustomerDAO"%>
<%@page import="com.flywithme.model.Customer"%>
<%@page import="com.flywithme.model.Seat"%>
<%@page import="java.util.List"%>
<%@page import="com.flywithme.dao.FlightDAO"%>
<%@page import="com.flywithme.model.Flight"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String flightId = request.getParameter("id");
    Flight flight = null;
    if (flightId != null) {
        flight = FlightDAO.getFlightById(flightId);
    }
%>
<div id="flightDetails" class="flight-details">
    <h3>Flight Details - <%= flight != null ? flight.getMaChuyenBay() : ""%></h3>
    <div class="flight-info">

        <div class="flight-info-item">
            <h4>Route Information</h4>
            <p>Airline Code: <%= flight.getMaHang()%></p>
            <p>From: <%= flight.getTenSanBayCatCanh()%></p>
            <p>To: <%= flight.getTenSanBayHaCanh()%></p>
            <p>Take-off: <%= flight.getThoiGianCatCanhDanhNghia()%></p>                        
            <p>Landing: <%= flight.getThoiGianHaCanhDanhNghia()%></p>
            <p>Status: <%= flight.getStatus()%></p>
            <p>Duration: <%= flight.getDuration()%></p>
        </div>


    </div>
    
</div>


<%-- 
    Document   : search_flight_result
    Created on : Nov 12, 2024, 11:38:29 PM
    Author     : pc
--%>

<%@page import="com.flywithme.model.Flight"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    String message = (String) request.getAttribute("messageSearch");
    if (message != null) {
%>
<p style="color: red;"><%= message%></p>
<%
    }
%>
<%
    // Lấy danh sách chuyến bay từ session hoặc request
    List<Flight> flights = (List<Flight>) request.getAttribute("flights");
    if (flights != null && !flights.isEmpty()) {
        for (Flight flight : flights) {
%>
<div class="flight-card">
    <div class="flight-content">
        <div class="flight-route">
            <%= flight.getTenSanBayCatCanh()%> → <%= flight.getTenSanBayHaCanh()%>
        </div>
        <div class="flight-info-grid">
            <div class="info-item">
                <div class="info-label">Departure</div>
                <div><%= flight.getThoiGianCatCanhDanhNghia()%></div>
            </div>
            <div class="info-item">
                <div class="info-label">Arrival</div>
                <div><%= flight.getThoiGianHaCanhDanhNghia()%></div>
            </div>
            <div class="info-item">
                <div class="info-label">Flight</div>
                <div><%= flight.getMaChuyenBay()%></div>
            </div>
            <div class="info-item">
                <div class="info-label">Price</div>
                <div>$<%= flight.getGiaTien()%></div>
            </div>
            <div class="info-item">
                <div class="info-label">Status</div>
                <div><%= flight.getStatus()%></div>
            </div>
        </div>
        <c:url var="detailsUrl" value="/DetailsServlet">
            <c:param name="maChuyenBay" value="<%= flight.getMaChuyenBay()%>" />
            <c:param name="action" value="details" />
        </c:url>
        <a href="${detailsUrl}" class="view-details-btn">View Details</a>
    </div>

</div>
<%
        }
    }
%>
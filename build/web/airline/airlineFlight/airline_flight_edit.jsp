<%-- 
    Document   : airline_flight_edit
    Created on : Nov 20, 2024, 1:02:55 AM
    Author     : pc
--%>

<%@page import="java.util.List"%>
<%@page import="com.flywithme.model.Airport"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="com.flywithme.dao.FlightDAO"%>
<%@page import="com.flywithme.model.Flight"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String flightId = request.getParameter("id");

    Flight flight = null;
    if (flightId != null) {
        flight = FlightDAO.getFlightById(flightId);
    }
    out.println(flight.getTenSanBayCatCanh());
%>
<div class="flight-form-container">
    <h2 class="flight-form-title">Edit Flight</h2>
    <form action="<%= request.getContextPath()%>/AirlineFlightManagement" method="POST" class="flight-form">
        <!-- Hidden input chứa hành động -->
        <input type="hidden" name="X-Action" value="edit">
        <div class="flight-form-group">
            <label for="idFlight" class="flight-form-label">Id Flight</label>
            <input type="text" id="idFlight" name="idFlight" class="flight-form-input" value="<%= flight != null ? flight.getMaChuyenBay(): "" %>" readonly>
        </div>
        <div class="flight-form-group">
            <label for="from" class="flight-form-label">From</label>
            <select id="from" name="from" class="flight-form-input" required>
                <option value="">Select Departure Airport</option>
                <%            List<Airport> airports = (List<Airport>) session.getAttribute("airports");
                    String selectedFrom = flight != null ? flight.getTenSanBayCatCanh(): "";
                    if (airports != null && !airports.isEmpty()) {
                        for (Airport airport : airports) {
                %>
                <option value="<%= airport.getMaSanBay()%>" 
                        <%= airport.getTenSanBay().equals(selectedFrom) ? "selected" : ""%>>
                    <%= airport.getTenSanBay()%>
                </option>
                <%
                        }
                    }
                %>
            </select>
        </div>

        <div class="flight-form-group">
            <label for="to" class="flight-form-label">To</label>
            <select id="to" name="to" class="flight-form-input" required>
                <option value="">Select Departure Airport</option>
                <%           
                    String selectedTo = flight != null ? flight.getTenSanBayHaCanh(): "";
                    if (airports != null && !airports.isEmpty()) {
                        for (Airport airport : airports) {
                %>
                <option value="<%= airport.getMaSanBay()%>" 
                        <%= airport.getTenSanBay().equals(selectedTo) ? "selected" : ""%>>
                    <%= airport.getTenSanBay()%>
                </option>
                <%
                        }
                    }
                %>
            </select>
        </div>

        <div class="flight-form-group">
            <label for="takeoff" class="flight-form-label">Take-off</label>
            <input type="datetime-local" id="takeoff" name="takeoff" class="flight-form-input" 
                   value="<%= flight != null ? flight.getThoiGianCatCanhDanhNghia().toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")) : ""%>"
                   required>
        </div>

        <div class="flight-form-group">
            <label for="landing" class="flight-form-label">Landing</label>
            <input type="datetime-local" id="landing" name="landing" class="flight-form-input" 
                   value="<%= flight != null ? flight.getThoiGianHaCanhDanhNghia().toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")) : ""%>" 
                   required>
        </div>

        <div class="flight-form-group">
            <label for="status" class="flight-form-label">Status</label>
            <select id="status" name="status" class="flight-form-input" required>
                <option value="scheduled" <%= flight != null && flight.getStatus().equals("scheduled") ? "selected" : ""%>>scheduled</option>
                <option value="delayed" <%= flight != null && flight.getStatus().equals("delayed") ? "selected" : ""%>>delayed</option>
                <option value="cancelled" <%= flight != null && flight.getStatus().equals("cancelled") ? "selected" : ""%>>cancelled</option>
            </select>
        </div>



        <button type="submit" class="flight-submit-btn">Edit Flight</button>
    </form>
</div>

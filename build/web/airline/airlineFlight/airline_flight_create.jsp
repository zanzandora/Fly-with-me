<%-- 
    Document   : airline_flight_create
    Created on : Nov 20, 2024, 1:02:43 AM
    Author     : pc
--%>

<%@page import="com.flywithme.model.Airport"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div class="flight-form-container">
    <h2 class="flight-form-title">Create New Flight</h2>
    <form action="<%= request.getContextPath()%>/AirlineFlightManagement" method="POST" class="flight-form">
        <!-- Hidden input chứa hành động -->
        <input type="hidden" name="X-Action" value="create">
        <div class="flight-form-group">
            <label for="from" class="flight-form-label">From</label>
            <select id="from" name="from" class="flight-form-input" required>
                <option value="">Select Departure Airport</option>

                <%  List<Airport> airports = (List<Airport>) session.getAttribute("airports");
                    if (airports != null && !airports.isEmpty()) {
                        for (Airport airport : airports) {
                %>
                <option value="<%= airport.getMaSanBay() %>" ><%= airport.getTenSanBay()%></option>
                <% }
                    }%>
            </select>
        </div>

        <div class="flight-form-group">
            <label for="to" class="flight-form-label">To</label>
            <select id="to" name="to" class="flight-form-input" required>
                <option value="">Select Arrival Airport</option>
                <%
                    if (airports != null && !airports.isEmpty()) {
                        for (Airport airport : airports) {
                %>
                <option value="<%= airport.getMaSanBay() %>"><%= airport.getTenSanBay()%></option>
                <%}
                    }%>
            </select>
        </div>
        <div class="flight-form-group">
            <label for="takeoff" class="flight-form-label">Take-off</label>
            <input type="datetime-local" id="takeoff" name="takeoff" class="flight-form-input" required>
        </div>

        <div class="flight-form-group">
            <label for="landing" class="flight-form-label">Landing</label>
            <input type="datetime-local" id="landing" name="landing" class="flight-form-input" required>
        </div>

        <div class="flight-form-group">
            <label for="status" class="flight-form-label">Status</label>
            <select id="status" name="status" class="flight-form-input" required>
                <option value="scheduled">scheduled</option>
                <option value="delayed">delayed</option>
                <option value="cancelled">cancelled</option>
            </select>
        </div>



        <button type="submit" class="flight-submit-btn">Create Flight</button>
    </form>
</div>
            

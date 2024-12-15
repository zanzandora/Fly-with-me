<%-- 
    Document   : admin_AM_edit
    Created on : Nov 15, 2024, 7:54:52 PM
    Author     : pc
--%>

<%@page import="com.flywithme.dao.AirportDAO"%>
<%@page import="com.flywithme.model.Airport"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String airportId = request.getParameter("id");

    Airport airport = null; 
    if (airportId != null) {
        // Gọi DAO để lấy thông tin airline dựa vào airlineId
        airport = AirportDAO.getAirportById(airportId);
    }
%>
 <div class="airline-form-container">
        <h2 class="airline-form-title">Edit Airport </h2>
        <form action="<%= request.getContextPath()%>/AdminAirportManagement" method="POST" class="airline-form">
            <!-- Hidden input chứa hành động -->
            <input type="hidden" name="X-Action" value="edit">
            <div class="airline-form-group">
                <label for="airportID" class="airline-form-label">Airport Id</label>
                <input type="text" id="airportID" name="airportID" class="airline-form-input" value="<%= airport != null ? airport.getMaSanBay(): "" %>" readonly>
            </div>

            <div class="airline-form-group">
                <label for="airportPassword" class="airline-form-label">Airport Password</label>
                <input type="text" id="airportPassword" name="airportPassword" class="airline-form-input" value="<%= airport != null ? airport.getMatKhauSanBay(): "" %>" required>
            </div>
             <div class="airline-form-group">
                <label for="airportName" class="airline-form-label">Airport Name</label>
                <input type="text" id="airportName" name="airportName" class="airline-form-input" value="<%= airport != null ? airport.getTenSanBay(): "" %>" required>
            </div>
            <div class="airline-form-group">
                <label for="airportCountry" class="airline-form-label">Airport Country</label>
                <input type="text" id="airportCountry" name="airportCountry" class="airline-form-input" value="<%= airport != null ? airport.getQuocGia(): "" %>" required>
            </div>

            <button type="submit" class="airline-submit-btn">Edit Airport </button>
        </form>
    </div>
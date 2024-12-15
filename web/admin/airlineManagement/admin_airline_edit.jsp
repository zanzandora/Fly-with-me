<%-- 
    Document   : admin_airline_edit
    Created on : Nov 15, 2024, 3:06:11 PM
    Author     : pc
--%>


<%@page import="com.flywithme.dao.AirlineDAO"%>
<%@page import="com.flywithme.model.Airline"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String airlineId = request.getParameter("id");

    Airline airline = null; // Airline là class bạn sử dụng để chứa dữ liệu hãng hàng không
    if (airlineId != null) {
        // Gọi DAO để lấy thông tin airline dựa vào airlineId
        airline = AirlineDAO.getAirlineById(airlineId);
    }
%>
<div class="airline-form-container">
    <h2 class="airline-form-title">Edit Airline</h2>
    <form action="<%= request.getContextPath()%>/AdminAirlineManagement" method="POST" class="airline-form" enctype="multipart/form-data">
        <!-- Hidden input chứa hành động -->
        <input type="hidden" name="X-Action" value="edit">
        <div class="airline-form-group">
            <label for="airlineCode" class="airline-form-label">Airline Code</label>
            <input type="text" id="airlineId" name="airlineId" class="airline-form-input"  value="<%= airline != null ? airline.getMaHang(): "" %>" readonly>
        </div>

        <div class="airline-form-group">
            <label for="airlineName" class="airline-form-label">Airline Name</label>
            <input type="text" id="airlineName" name="airlineName" class="airline-form-input"  value="<%= airline != null ? airline.getTenHang(): "" %>" required>
        </div>
        <div class="airline-form-group">
            <label for="airlinePassword" class="airline-form-label">Airline Password</label>
            <input type="text" id="airlinePassword" name="airlinePassword" class="airline-form-input"  value="<%= airline != null ? airline.getPasswordHangHangKhong(): "" %>" required>
        </div>
        <div class="airline-form-group">
            <label for="country" class="airline-form-label">Country</label>
            <input type="text" id="country" name="country" class="airline-form-input"  value="<%= airline != null ? airline.getCountry(): "" %>" required>
        </div>

        <div class="airline-form-group">
            <label for="avatar" class="airline-form-label">Avatar</label>
            <input type="file" id="avatar" name="avatar" class="airline-form-input">
            <% if (airline != null && airline.getAvatar() != null) { %>
                <p>Current Avatar: <img src="<%= request.getContextPath() %>/assets/img/avarta_airline/<%= airline.getAvatar() %>" alt="Avatar" width="100"></p>
            <% } %>
        </div>

        <button type="submit" class="airline-submit-btn">Edit Airline</button>
    </form>
</div>

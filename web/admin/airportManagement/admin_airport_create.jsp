<%-- 
    Document   : admin_AM_create
    Created on : Nov 15, 2024, 7:54:43 PM
    Author     : pc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="airline-form-container">
    <h2 class="airline-form-title">Create New Airport </h2>
    <form action="<%= request.getContextPath()%>/AdminAirportManagement" method="POST" class="airline-form">
        <!-- Hidden input chứa hành động -->
            <input type="hidden" name="X-Action" value="create">
        <div class="airline-form-group">
            <label for="airportPassword" class="airline-form-label">Airport Password</label>
            <input type="text" id="airportPassword" name="airportPassword" class="airline-form-input" required>
        </div>
        <div class="airline-form-group">
            <label for="airportName" class="airline-form-label">Airport Name</label>
            <input type="text" id="airportName" name="airportName" class="airline-form-input" required>
        </div>
        <div class="airline-form-group">
            <label for="airportCountry" class="airline-form-label">Airport Country</label>
            <input type="text" id="airportCountry" name="airportCountry" class="airline-form-input" required>
        </div>
        <button type="submit" class="airline-submit-btn">Create Airport </button>
    </form>
</div>
<% String message = (String) request.getAttribute("message"); %>
<% if (message != null) {%>
<script>
    alert("<%= message%>");
</script>
<% }%>

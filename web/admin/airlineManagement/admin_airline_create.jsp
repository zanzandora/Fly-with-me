<%-- 
    Document   : admin_airline_create
    Created on : Nov 15, 2024, 2:18:58 PM
    Author     : pc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
 <div class="airline-form-container">
        <h2 class="airline-form-title">Create New Airline</h2>
        <form action="<%= request.getContextPath() %>/AdminAirlineManagement" method="POST" class="airline-form" enctype="multipart/form-data">
            <!-- Hidden input chứa hành động -->
            <input type="hidden" name="X-Action" value="create">
            <div class="airline-form-group">
                <label for="airlineCode" class="airline-form-label">Airline Code</label>
                <input type="text" id="airlineCode" name="airlineId" class="airline-form-input" required>
            </div>
            <div class="airline-form-group">
                <label for="airlineName" class="airline-form-label">Airline Name</label>
                <input type="text" id="airlineName" name="airlineName" class="airline-form-input" required>
            </div>
             <div class="airline-form-group">
                <label for="airlinePassword" class="airline-form-label">Airline Password</label>
                <input type="text" id="airlinePassword" name="airlinePassword" class="airline-form-input" required>
            </div>
            <div class="airline-form-group">
                <label for="country" class="airline-form-label">Country</label>
                <input type="text" id="country" name="country" class="airline-form-input" required>
            </div>

            <div class="airline-form-group">
                <label for="avatar" class="airline-form-label">Avatar</label>
                <input type="file" id="avatar" name="avatar" class="airline-form-input" accept="image/*" required>
            </div>

            <button type="submit" class="airline-submit-btn">Create Airline</button>
        </form>
    </div>

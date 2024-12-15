<%-- 
    Document   : admin_customer_create
    Created on : Nov 15, 2024, 11:22:46 PM
    Author     : pc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
 <div class="airline-form-container">
        <h2 class="airline-form-title">Create New Customer</h2>
       
        <form action="<%= request.getContextPath() %>/AdminCustomerManagement" method="POST" class="airline-form">
            <!-- Hidden input chứa hành động -->
            <input type="hidden" name="X-Action" value="create">
            <div class="airline-form-group">
                <label for="firstNameCustomer" class="airline-form-label">First Name</label>
                <input type="text" id="firstName" name="firstNameCustomer" class="airline-form-input" required>
            </div>
            <div class="airline-form-group">
                <label for="lastNameCustomer" class="airline-form-label">Last Name</label>
                <input type="text" id="lastName" name="lastNameCustomer" class="airline-form-input" required>
            </div>
            <div class="airline-form-group">
                <label for="emailCustomer" class="airline-form-label">Email</label>
                <input type="email" id="email" name="emailCustomer" class="airline-form-input" required>
            </div>
            <div class="airline-form-group">
                <label for="passwordCustomer" class="airline-form-label">Customer Password</label>
                <input type="text" id="passwordCustomer" name="passwordCustomer" class="airline-form-input" required>
            </div>
            <div class="airline-form-group">
                <label for="birthdayCustomer" class="airline-form-label">Birthday</label>
                <input type="date" id="birthday" name="birthdayCustomer" class="airline-form-input" required>
            </div>
            <button type="submit" class="airline-submit-btn">Create Customer</button>
        </form>
    </div>

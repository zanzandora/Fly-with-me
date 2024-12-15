<%-- 
    Document   : admin_customer_edit
    Created on : Nov 15, 2024, 11:23:05 PM
    Author     : pc
--%>

<%@page import="com.flywithme.dao.CustomerDAO"%>
<%@page import="com.flywithme.model.Customer"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String customerId = request.getParameter("id");

    Customer customer = null; // Airline là class bạn sử dụng để chứa dữ liệu hãng hàng không
    if (customerId != null) {
        // Gọi DAO để lấy thông tin airline dựa vào airlineId
        customer = CustomerDAO.getCustomerById(customerId);
    }
%>
<div class="airline-form-container">
    <h2 class="airline-form-title">Edit Customer</h2>
    <form action="<%= request.getContextPath() %>/AdminCustomerManagement" method="POST" class="airline-form">
        <!-- Hidden input chứa hành động -->
        <input type="hidden" name="X-Action" value="edit">
        <div class="airline-form-group">
            <label for="idCustomer" class="airline-form-label">Customer Id</label>
            <input type="text" id="idCustomer" name="idCustomer" class="airline-form-input" value="<%= customer != null ? customer.getSoDinhDanh(): "" %>" readonly>
        </div>
        <div class="airline-form-group">
            <label for="firstNameCustomer" class="airline-form-label">First Name</label>
            <input type="text" id="firstNameCustomer" name="firstNameCustomer" class="airline-form-input" value="<%= customer != null ? customer.getTen(): "" %>" required>
        </div>
        <div class="airline-form-group">
            <label for="lastNameCustomer" class="airline-form-label">Last Name</label>
            <input type="text" id="lastNameCustomer" name="lastNameCustomer" class="airline-form-input" value="<%= customer != null ? customer.getHoDem(): "" %>" required>
        </div>
        <div class="airline-form-group">
            <label for="emailCustomer" class="airline-form-label">Email</label>
            <input type="email" id="emailCustomer" name="emailCustomer" class="airline-form-input" value="<%= customer != null ? customer.getEmail(): "" %>" required>
        </div>
        <div class="airline-form-group">
            <label for="passwordCustomer" class="airline-form-label">Customer Password</label>
            <input type="text" id="passwordCustomer" name="passwordCustomer" class="airline-form-input" value="<%= customer != null ? customer.getMatKhauKhach(): "" %>" required>
        </div>
        <div class="airline-form-group">
            <label for="birthdayCustomer" class="airline-form-label">Birthday</label>
            <input type="date" id="birthdayCustomer" name="birthdayCustomer" class="airline-form-input" value="<%= customer != null ? customer.getNgayThangNamSinh(): "" %>" required>
        </div>



        <button type="submit" class="airline-submit-btn">Edit Customer</button>
    </form>
</div>

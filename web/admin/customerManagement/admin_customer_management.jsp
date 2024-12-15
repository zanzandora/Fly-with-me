<%-- 
    Document   : admin_customer_management
    Created on : Nov 15, 2024, 2:52:49 PM
    Author     : pc
--%>

<%@page import="com.flywithme.model.Customer"%>
<%@page import="java.util.List"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="card" id="Customer-Management">
    <h3>Customer Management</h3>
    <button class="create-btn create-customer">Create New Customer</button>
    <table>
        <thead>
            <tr>
                <th>Customer ID</th>
                <th>Last Name</th>
                <th>First Name</th>
                <th>Email</th>                            
                <th>Password</th>
                <th>Day of Birth</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <%  List<Customer> customers = (List<Customer>) session.getAttribute("customers");
                if (customers != null && !customers.isEmpty()) {
                    for (Customer cus : customers) {
            %>
            <tr>
                <td><%= cus.getSoDinhDanh()%></td>
                <td><%= cus.getHoDem()%></td>
                <td><%= cus.getTen()%></td>
                <td><%= cus.getEmail()%></td>
                <td><%= cus.getMatKhauKhach()%></td>                
                <td><%= cus.getNgayThangNamSinh()%></td>
                <td class="action-buttons">
                    <a href="<c:url value='admin_page.jsp' />?action=editCustomer&id=<%= cus.getSoDinhDanh()%>" class="edit-btn edit-customer">Edit</a>
                    <a href="<c:url value='/AdminCustomerManagement' />?X-Action=delete&id=<%= cus.getSoDinhDanh()%>" 
                        onclick="return confirmDelete()" 
                        class="delete-btn delete-customer">Delete</a>
                </td>
            </tr>
            <% }
                            }%>
        </tbody>
    </table>
</div>
        
<script>

    const createCustomerBtn = document.querySelector('.create-customer');
    createCustomerBtn.addEventListener('click', function () {
        // Thay đổi URL để xử lý với JSP và chỉ hiển thị phần create
        window.location.href = "admin_page.jsp?action=createCustomer";
    });
     function confirmDelete() {
        return confirm("Are you sure you want to delete this Customer?");
    }

</script>

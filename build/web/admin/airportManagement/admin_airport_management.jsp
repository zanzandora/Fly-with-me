<%-- 
    Document   : admin_AM_management
    Created on : Nov 15, 2024, 2:51:29 PM
    Author     : pc
--%>

<%@page import="com.flywithme.model.Airport"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String baseURL = getServletContext().getInitParameter("baseURL");


%>
<div class="card" id="Airport-Management">
    <h3>Airport Management</h3>
    <button class="create-btn create-airport">Create New Airport</button>
    <table>
        <thead>
            <tr>
                <th>Airport Id</th>
                <th>Airport Name</th>
                <th>Airport Password</th>
                <th>Country</th>                
                <th>Action</th>


            </tr>
        </thead>
        <tbody>
            <%  List<Airport> airports = (List<Airport>) session.getAttribute("airports");
                if (airports != null && !airports.isEmpty()) {
                    for (Airport ap : airports) {
            %> 
            <tr>
                <td><%= ap.getMaSanBay()%></td>
                <td><%= ap.getTenSanBay()%></td>                 
                <td><%= ap.getMatKhauSanBay()%></td> 
                <td><%= ap.getQuocGia()%></td>

                <td class="action-buttons">
                    <a href="<c:url value='admin_page.jsp' />?action=editAirport&id=<%= ap.getMaSanBay()%>" class="edit-btn edit-airport">Edit</a>
                    <a href="<c:url value='/AdminAirportManagement' />?X-Action=delete&id=<%= ap.getMaSanBay()%>" 
                       onclick="return confirmDelete()" 
                        class="delete-btn delete-airport">Delete</a>
                </td>
            </tr>
            <% }
                }%>
        </tbody>
    </table>
</div>


<script>

    const createAirportBtn = document.querySelector('.create-airport');

            createAirportBtn.addEventListener('click', function () {
                // Thay đổi URL để xử lý với JSP và chỉ hiển thị phần create
                window.location.href = "admin_page.jsp?action=createAirport";
            });
            function confirmDelete() {
        return confirm("Are you sure you want to delete this airline?");
    }


</script>
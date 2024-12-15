<%-- 
    Document   : admin_airline_management
    Created on : Nov 15, 2024, 1:29:01 PM
    Author     : pc
--%>

<%@page import="java.util.List"%>
<%@page import="com.flywithme.model.Airline"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String baseURL = getServletContext().getInitParameter("baseURL");
%>
<div class="card" id="Airline-Management">

    <h3>Airline Management</h3>
    <button class="create-btn create-airline"  >Create New Airline</button>


    <table>
        <thead>
            <tr>
                <th>Airline Code</th>
                <th>Airline Name</th>                
                <th>Airline Password</th>
                <th>Country</th>
                <th>Avatar</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <%  List<Airline> airlines = (List<Airline>) session.getAttribute("airlines");
                if (airlines != null && !airlines.isEmpty()) {
                    for (Airline airline : airlines) {
            %>  
            <tr>
                <td><%= airline.getMaHang()%></td>
                <td><%= airline.getTenHang()%></td>
                <td><%= airline.getPasswordHangHangKhong()%></td>
                <td><%= airline.getCountry()%></td>
                <td><img src="<%= baseURL%>/assets/img/avarta_airline/<%= airline.getAvatar()%>" alt="Avatar" style="width:50px;"></td>
                <td class="action-buttons">
                    <a href="<c:url value='admin_page.jsp' />?action=editAirline&id=<%= airline.getMaHang()%>" class="edit-btn edit-airline">Edit</a>
                    <a href="<c:url value='/AdminAirlineManagement' />?X-Action=delete&id=<%= airline.getMaHang()%>" 
                       onclick="return confirmDelete()" 
                       class="delete-btn delete-airline">Delete</a>
                </td>
            </tr>
            <% }
                }%>
        </tbody>
    </table>
</div>
<script>
    const createAirlineBtn = document.querySelector('.create-airline');
    createAirlineBtn.addEventListener('click', function () {
        // Thay đổi URL để xử lý với JSP và chỉ hiển thị phần create
        window.location.href = "admin_page.jsp?action=createAirline";
    });
    function confirmDelete() {
        return confirm("Are you sure you want to delete this airline?");
    }
</script>

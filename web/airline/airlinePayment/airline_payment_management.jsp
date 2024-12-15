<%-- 
    Document   : airline_payment_management
    Created on : Nov 20, 2024, 1:08:20 AM
    Author     : pc
--%>

<%@page import="com.flywithme.model.Booking"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="card">
    <h3>Payment Management</h3>
    <table>
        <thead>
            <tr>
                <th>Booking ID</th>
                <th>Flight</th>
                <th>Customer</th>                
                <th>Seats</th>

                <th>Amount Paid</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <%
            List<Booking> bookings = (List<Booking>) session.getAttribute("bookings");
            if (bookings != null) {
                for (Booking booking : bookings) {
        %>
            <tr>
                <td><%= booking.getMaDatCho() %></td>
                <td><%= booking.getMaChuyenBay()%></td>
                <td><%= booking.getTenKhachHang()%></td>               
                <td><%= booking.getMaGhe()%></td>
                <td><%= booking.getSoTienDaThanhToan()%>$</td>
                <td class="action-links">
                    <a href="<c:url value='/AirlinePaymentManagement' />?action=detailsPayment&id=<%= booking.getMaDatCho()%>" 
                       class="details-link">Details</a>
                </td>
            </tr>
             <%
                }
            } else {
                out.println("<tr><td colspan='6'>Không có dữ liệu.</td></tr>");
            }
        %>
        </tbody>
    </table>
</div>

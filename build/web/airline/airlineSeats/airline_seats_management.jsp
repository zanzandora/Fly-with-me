<%-- 
    Document   : airline_seats_management
    Created on : Nov 20, 2024, 1:07:08 AM
    Author     : pc
--%>

<%@page import="com.flywithme.dao.CustomerDAO"%>
<%@page import="com.flywithme.model.Customer"%>
<%@page import="com.flywithme.model.Seat"%>
<%@page import="com.flywithme.model.Flight"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%      String baseURL = getServletContext().getInitParameter("baseURL");%>
<%
    String maChuyenBay = (session != null && session.getAttribute("maChuyenBay") != null)
            ? (String) session.getAttribute("maChuyenBay") : "Choose Flights";
%>
<div class="container">
    <div class="seat-management">
        <h2>Seat Management - Flight No. <span id="flightNameDisplay"><%= maChuyenBay%></span></h2>

        <div class="controls">

            <select class="flight-select" id="flightSelect">
                <option value="">Select Flight</option>
                <%  List<Flight> flights = (List<Flight>) session.getAttribute("flights");
                    if (flights != null && !flights.isEmpty()) {
                        for (Flight flight : flights) {
                %>
                <option value="<%= flight.getMaChuyenBay()%>"><%= flight.getMaChuyenBay()%></option>
                <% }
                    }%>
            </select>
        </div>

        <div class="card" id="FlightInfor">
            <table>
                <thead>
                    <tr>
                        <th>Seat No</th>
                        <th>Status</th>
                        <th>Passenger</th>
                                                <th>Seat Class</th>
                        <th>Paid Total</th>
                        <th>Booking ID</th>                        
                        <th>Action</th>

                    </tr>
                </thead>
                <tbody>
                    <%

                        List<Seat> seats = (List<Seat>) session.getAttribute("seats");
                        if (seats != null) {
                            for (Seat seat : seats) {
                                String sodinhdanh = seat.getKhachHang();
                                Customer customer = CustomerDAO.getCustomerById(sodinhdanh);
                                if (customer != null) {
                                    String fullName = customer.getHoDem() + " " + customer.getTen();
                                    seat.setKhachHang(fullName);
                                }
                    %>
                    <tr>
                        <td><%= seat.getMaGhe()%></td>
                        <td><%= seat.getTinhTrang()%></td>
                        <td><%= seat.getKhachHang() != null ? seat.getKhachHang() : "N/A"%></td>
                        <td><%= seat.getHangGhe()%></td>                                                
                        <td><%= seat.getSoTienDaThanhToan() != 0 ? seat.getSoTienDaThanhToan() : 0%>$</td>
                        <td><%= seat.getMaDatCho() != null ? seat.getMaDatCho() : "N/A"%></td>
                        <td class="action-links">
                            <% if ("Available".equals(seat.getTinhTrang())) {%>
                            <a href="<c:url value='/AirlineSeatsManagement' />?X-Action=edit&status=lockSeat&id=<%= seat.getMaGhe()%>" class="delete-link">Locked</a>   
                            <% } else if ("Locked".equals(seat.getTinhTrang())) {%>
                            <a href="<c:url value='/AirlineSeatsManagement' />?X-Action=edit&status=unlockSeat&id=<%= seat.getMaGhe()%>" class="add-link">Unlocked</a>
                            <% } %>
                        </td>
                    </tr>
                    <%
                        }
                    } else {
                    %>
                    <tr>
                        <td colspan="4">No seats available for this flight.</td>
                    </tr>
                    <% }%>
                </tbody>
            </table>
        </div>
    </div>
</div>

<div class="notification" id="notification"></div>

<script>
    document.addEventListener('DOMContentLoaded', function () {
        const flightSelect = document.getElementById('flightSelect');

        flightSelect.addEventListener('change', function () {
            const flightNumber = flightSelect.value;
            if (flightNumber) {
                // Chuyển hướng đến Servlet với tham số maChuyenBay
                window.location.href = '<%= baseURL%>/AirlineSeatsManagement?maChuyenBay=' + flightNumber;
            }
        });

    });


</script>

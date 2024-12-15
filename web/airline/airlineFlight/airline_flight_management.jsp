<%-- 
    Document   : airline_flight_management
    Created on : Nov 20, 2024, 1:03:38 AM
    Author     : pc
--%>

<%@page import="com.flywithme.model.Flight"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String tenHang = (session != null && session.getAttribute("tenHang") != null)
            ? (String) session.getAttribute("tenHang") : "Guest";
%>
<div class="card" id="FlightInfor">
    <h3>Flight Management - <%= tenHang%></h3> 
    <a href="airline_page.jsp?action=createFlight" class="add-link">Add New Flight</a>
    <table>
        <thead>
            <tr>
                <th>Flight No.</th>
                <th>From</th>
                <th>To</th>
                <th>Take-off</th>
                <th>Landing</th>                
                <th>Pricing</th>
                <th>Status</th>
                <th>Approval</th>                
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <%  List<Flight> flights = (List<Flight>) session.getAttribute("flights");
                if (flights != null && !flights.isEmpty()) {
                    for (Flight flight : flights) {
            %>

            <tr class="flight-row" data-flight="VA123">
                <td><%= flight.getMaChuyenBay()%></td>
                <td><%= flight.getTenSanBayCatCanh()%></td>                
                <td><%= flight.getTenSanBayHaCanh()%></td> 
                <td><%= flight.getThoiGianCatCanhDanhNghia()%></td>
                <td><%= flight.getThoiGianHaCanhDanhNghia()%></td>                
                <td><%= flight.getGiaTien()%>$</td>       
                <td><%= flight.getStatus()%></td>
                <td><%= flight.getTinhTrangChuyenBay()%></td>
                <td class="action-links">
                    <a href="<c:url value='airline_page.jsp' />?action=editFlight&id=<%= flight.getMaChuyenBay()%>" class="edit-link">Edit</a>
                    <a href="<c:url value='/AirlineFlightManagement' />?X-Action=delete&id=<%= flight.getMaChuyenBay()%>"
                       class="delete-link" 
                       onclick="return confirmDelete()" >Cancel</a>
                    <a href="<c:url value='airline_page.jsp' />?action=detailsFlight&id=<%= flight.getMaChuyenBay()%>" 
                       class="details-link">Details</a>
                </td>
            </tr>
            <% }
                            }%>

        </tbody>
    </table>
</div>
<script>
     function confirmDelete() {
        return confirm("Are you sure you want to delete this airline?");
    }
</script>

<%-- 
    Document   : airline_flight_management
    Created on : Nov 20, 2024, 1:03:38 AM
    Author     : pc
--%>

<%@page import="com.flywithme.model.Flight"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="card" id="FlightInfor">
    <h3>Flight Management </h3> 
    <table>
        <thead>
            <tr>
                <th>Flight No.</th>
                <th>From</th>
                <th>To</th>
                <th>Take-off</th>
                <th>Landing</th>                
                <th>Status</th>
                <th>Airline</th>
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
                <td><%= flight.getStatus()%></td>                
                <td><%= flight.getMaHang()%></td>

                <td><%= flight.getTinhTrangChuyenBay()%></td>
                <td class="action-links">
                    <%
                        if ("Authorized".equals(flight.getTinhTrangChuyenBay())) {
                    %>
                    <!-- Nút Revoke nếu đã phê duyệt -->
                    <form action="<%= request.getContextPath()%>/ManagerFlightManagement" method="POST" >
                        <input type="hidden" name="maChuyenBay" value="<%= flight.getMaChuyenBay()%>">
                        <input type="hidden" name="X-Action" value="edit">
                        <input type="hidden" name="action" value="revoke">

                        <button type="submit" class="btn btn-cancel">Revoke</button>
                    </form>
                    <%
                    } else if ("No Authorized".equals(flight.getTinhTrangChuyenBay())) {
                    %>
                    <!-- Nút Approval nếu chưa phê duyệt -->
                    <form action="<%= request.getContextPath()%>/ManagerFlightManagement" method="POST" >
                        <input type="hidden" name="maChuyenBay" value="<%= flight.getMaChuyenBay()%>">
                        <input type="hidden" name="X-Action" value="edit">
                        <input type="hidden" name="action" value="approve">

                        <button type="submit" class="btn btn-update">Approve</button>
                    </form>
                    <%
                        }
                    %>


                    <a href="<c:url value='manager_page.jsp' />?action=detailsFlight&id=<%= flight.getMaChuyenBay()%>" 
                       class="details-link">Details</a>
                </td>
            </tr>
            <% }
                } else {

                    out.println("<tr><td colspan='6'>Không có dữ liệu.</td></tr>");

                }%>

        </tbody>
    </table>
</div>
<script>

</script>

<%@page import="com.flywithme.model.Seat"%>
<%@page import="java.util.List"%>
<%@page import="java.util.List"%>
<%@page import="com.flywithme.dao.FlightDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.flywithme.model.Flight"%>
<%@page import="java.sql.Timestamp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Chi tiết chuyến bay</title>
        <link rel="stylesheet" type="text/css" href="./assets/css/style.css">
    </head>
    <body>

        <!-- Navbar user -->
        <jsp:include page="./navbar_user.jsp" />

        <!-- Main content -->
        <div class="flight-details">
            <%
                Flight flight = (Flight) request.getAttribute("flight");
                if (flight != null) {
            %>
            <div id="flightDetails" class="card" style="height: 100%">
                <h3>Flight Details - <%= flight != null ? flight.getMaChuyenBay() : ""%></h3>
                <div class="flight-info">
                    <div class="flight-info-item">
                        <h4>Flight Information</h4>
                        <p>Airline Code: <%= flight.getMaHang()%></p>
                        <p>From: <%= flight.getTenSanBayCatCanh()%></p>
                        <p>To: <%= flight.getTenSanBayHaCanh()%></p>
                        <p>Take-off: <%= flight.getThoiGianCatCanhDanhNghia()%></p>                        
                        <p>Landing: <%= flight.getThoiGianHaCanhDanhNghia()%></p>
                        <% if ("cancelled".equals(flight.getStatus())) {%>
                        <p style="color:red;">Status: <%= flight.getStatus()%></p>
                        <% } else {%>
                        <p>Status: <%= flight.getStatus()%></p>
                        <% }%>
                        <p>Duration: <%= flight.getDuration()%></p>
                        <p>Paid: <%= flight.getGiaTien()%>$</p>
                    </div>
                </div>
            </div>
            <% } else { %>
            <p>Không tìm thấy thông tin chuyến bay.</p>
            <% }%>


            <!-- Seats  -->
            <div class="card" id="FlightInfor">
                <table>
                    <thead>
                        <tr>
                            <th>Seat No</th>
                            <th>Status</th>
                            <th>Seat Class</th>
                            <th>Price</th>
                            <th>Booking ID</th>                        

                        </tr>
                    </thead>
                    <tbody>
                        <%

                            List<Seat> seats = (List<Seat>) request.getAttribute("seats");
                            if (seats != null) {
                                for (Seat seat : seats) {
                        %>
                        <tr>
                            <td><%= seat.getMaGhe()%></td>
                            <td><%= seat.getTinhTrang()%></td>
                            <td><%= seat.getHangGhe()%></td>
                            <td><%= seat.getGiaGhe()%></td>                                                
                            <td><%= seat.getMaDatCho() != null ? seat.getMaDatCho() : "N/A"%></td>
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
            <c:url var="bookingUrl" value="/DetailsServlet">
                <c:param name="maChuyenBay" value="<%= flight.getMaChuyenBay()%>" />
                <c:param name="action" value="booking" />
            </c:url>

            <% if ("cancelled".equals(flight.getStatus())) {%>
            <a class="book-now-btn--unavailable">Booking Now !</a>

            <% } else {%>
            <a href="${bookingUrl}" class="book-now-btn">Booking Now !</a>

            <% }%>


        </div>

    </body>
</html>

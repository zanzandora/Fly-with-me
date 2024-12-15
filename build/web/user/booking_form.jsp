<%@page import="com.flywithme.model.Seat"%>
<%@page import="java.util.List"%>
<%@page import="java.util.List"%>
<%@page import="com.flywithme.dao.FlightDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.flywithme.model.Flight"%>
<%@page import="java.sql.Timestamp"%>
<%@ page import="javax.servlet.http.HttpSession" %>
<% String baseURL = getServletContext().getInitParameter("baseURL");%>

<%
    if (session == null) {
        session = request.getSession(false);
    }
    String hodem = (session != null && session.getAttribute("hodem") != null)
            ? (String) session.getAttribute("hodem") : "Lovely";
    String ten = (session != null && session.getAttribute("ten") != null)
            ? (String) session.getAttribute("ten") : "Guest";
    String email = (session != null && session.getAttribute("email") != null)
            ? (String) session.getAttribute("email") : "Guest";
    String id = (session != null && session.getAttribute("id") != null)
            ? (String) session.getAttribute("id") : "Guest";
    String birth = (session != null && session.getAttribute("birth") != null)
            ? (String) session.getAttribute("birth") : "Guest";

%>
<%    Flight flight = (Flight) request.getAttribute("flight");
    if (flight != null) {
%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Chi tiết chuyến bay</title>
        <link rel="stylesheet" type="text/css" href="<%= baseURL%>/assets/css/style.css">
    </head>
    <body>

        <!-- Navbar user -->
        <jsp:include page="./navbar_user.jsp" />

        <!-- Main content -->
        <div class="flight-details">
            <div class="booking-form">
                <h2>Booking Form</h2>

                <form action="<%= baseURL%>/BookingFlightServlet" method="post">
                    <!-- Hidden input chứa hành động -->
                    <input type="hidden" name="maChuyenBay" value="<%= flight.getMaChuyenBay()%>">
                    <input type="text" name="id" placeholder="id" value="<%= id%>" readonly>
                    <input type="text" name="name" placeholder="Name" value="<%= hodem + " " + ten%>" readonly>
                    <input type="email" name="email" placeholder="Email" value="<%= email%>" readonly>
                    <input type="date" name="birth" placeholder="birth" value="<%= birth%>" readonly>
                    <input id="seat-info" type="text" name="seat-info" placeholder="Choose Your Seat" readonly>

                    <button type="submit">Book Flight</button>
                </form>
            </div>
            <% } %>

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
                            
                            <td style="text-align: center;">
                                <% if ("Booked".equals(seat.getTinhTrang()) || "Locked".equals(seat.getTinhTrang())) { %>
                                <span style="color: red;"><%= seat.getMaGhe()%></span>
                                <% } else {%>
                                <button class="btn-seats" type="button" onclick="updateSeatInfo('<%= seat.getMaGhe()%>', '<%= seat.getHangGhe()%>', '<%= seat.getGiaGhe()%>$')">
                                    <p class="text-seat"><%= seat.getMaGhe()%></p>
                                </button>
                                <% }%>
                            </td>
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


        </div>
        <script>
            // Function to handle seat selection
            function updateSeatInfo(seatNo, seatClass, seatPaid) {
                // Display the selected seat number and seat class in the "Your Seat" section
                document.getElementById("seat-info").value = seatNo + "-" + seatClass + "-" + seatPaid;
            }
        </script>

    </body>
</html>

<%-- 
    Document   : confirm_book
    Created on : Nov 27, 2024, 1:42:04 PM
    Author     : pc
--%>

<%@page import="com.flywithme.model.BookingDetails"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% String baseURL = getServletContext().getInitParameter("baseURL");%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Chi tiết Book</title>
        <link rel="stylesheet" type="text/css" href="<%= baseURL%>/assets/css/style.css">
    </head>
    <body>
        <jsp:include page="./navbar_user.jsp" />

        <div class="booking-ticket-container mt-5">
            <h2>Booking Ticket Details</h2>
            <div class="booking-tickets">
                <div class="row height-80vh">
                    <!-- Book Ticket 1 -->
                    <%
                        // Lấy danh sách bookingDetails từ request
                        List<BookingDetails> bookingDetails = (List<BookingDetails>) request.getAttribute("bookingDetails");
                        int i = 1;
                        if (bookingDetails != null && !bookingDetails.isEmpty()) {
                            // Duyệt qua từng bookingDetail và hiển thị
                            for (BookingDetails booking : bookingDetails) {
                    %>
                    <div class="col-md-3">
                        <div class="booking-ticket bg-light p-4 rounded shadow mb-4">
                            <h3>Book Ticket <%= i%></h3>
                            <div class="booking-ticket-group">
                                <label for="book-id-1">Book ID:</label>
                                <span id="book-id-1"><%= booking.getMaDatCho()%></span>
                            </div>
                            <div class="booking-ticket-group">
                                <label for="flight-id-1">Flight ID:</label>
                                <span id="flight-id-1"><%= booking.getMaChuyenBay()%></span>
                            </div>
                            <div class="booking-ticket-group">
                                <label for="seat-id-1">Seat ID:</label>
                                <span id="seat-id-1"><%= booking.getMaGhe()%></span>
                            </div>
                            <div class="booking-ticket-group">
                                <label for="customer-id-1">Customer ID:</label>
                                <span id="customer-id-1"><%= booking.getSoDinhDanh()%></span>
                            </div>
                            <div class="booking-ticket-group">
                                <label for="customer-id-1">Customer Name:</label>
                                <span id="customer-id-1"><%= booking.getHoTenKhachHang()%></span>
                            </div>
                            <div class="booking-ticket-group">
                                <label for="customer-id-1">From:</label>
                                <span id="customer-id-1"><%= booking.getSanBayDi()%> ( <%= booking.getQuocGiaDi()%> )</span>
                            </div><div class="booking-ticket-group">
                                <label for="customer-id-1">To:</label>
                                <span id="customer-id-1"><%= booking.getQuocGiaDen()%> ( <%= booking.getQuocGiaDen()%> ) </span>
                            </div>
                            <div class="booking-ticket-group">
                                <label for="amount-paid-1">Amount Paid:</label>
                                <span id="amount-paid-1"><%= String.format("%.2f", booking.getSoTienDaThanhToan())%>$</span>
                            </div>
                            <div class="booking-ticket-group">
                                <label for="flight-status-1">Flight Status:</label>
                                <span id="flight-status-1"><%= booking.getStatus()%></span>
                            </div>
                            <div class="booking-ticket-group">
                                <label for="departure-time-1">Departure Time:</label>
                                <span id="departure-time-1"><%= booking.getThoiGianKhoiHanh()%></span>
                            </div>
                            <div class="booking-ticket-group">
                                <label for="arrival-time-1">Arrival Time:</label>
                                <span id="arrival-time-1"><%= booking.getThoiGianHaCanh()%></span>
                            </div>
                            <div class="booking-ticket-group">
                                <div class="booking-ticket-group">
                                    <form method="POST" action="<%= baseURL%>/CanCelBookingServlet">
                                        <input type="hidden" name="maDatCho" value="<%= booking.getMaDatCho()%>">
                                        <input type="hidden" name="maGhe" value="<%= booking.getMaGhe()%>">
                                        <input type="hidden" name="maChuyenBay" value="<%= booking.getMaChuyenBay()%>">
                                        <input type="hidden" name="userId" value="<%= booking.getSoDinhDanh()%>">

                                        <button onclick="return confirm('Do you want cancel this ticket ?')" type="submit" class="btn-cancel-ticket"><span>Cancel Booking</span></button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                    <%
                                i++;
                            }
                        }else{
                    %>
                    <img src="<%= baseURL%>/assets/img/null_listed.png" alt="Null Listed"/>
                    <% } %>
                </div>
            </div>
        </div>
    </body>
</html>

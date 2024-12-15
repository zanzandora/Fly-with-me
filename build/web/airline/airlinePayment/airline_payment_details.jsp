<%-- 
    Document   : airline_payment_details
    Created on : Nov 24, 2024, 10:30:58 PM
    Author     : pc
--%>

<%@page import="com.flywithme.model.BookingDetails"%>
<%@page import="java.util.List"%>
<%@page import="com.flywithme.dao.BookingDAO"%>
<%@page import="com.flywithme.model.Booking"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div id="flightDetails" class="flight-details">
    <%
        List<BookingDetails> bookingDetails = (List<BookingDetails>) session.getAttribute("bookingDetails");
        if (bookingDetails == null) {
            out.println("<h1>Không có thông tin đặt chỗ.</h1>");
        } else {
    %>
    <%
        for (BookingDetails detail : bookingDetails) {
    %>
    <h3>Booking Details - <%= detail != null ? detail.getMaDatCho() : ""%></h3>
    <div class="flight-info">
        <div class="flight-info-item">
            <h4>Customer Information</h4>
            <p>Customer Code: <%= detail.getSoDinhDanh()%></p>
            <p>Customer Name: <%= detail.getHoTenKhachHang()%></p>            
            <p>Customer Email: <%= detail.getEmail()%></p>                        
            <p>Customer Birth: <%= detail.getNgayThangNamSinh()%></p>     
            <p>Amount Paid: <%= detail.getSoTienDaThanhToan()%></p>                                    

        </div>
        <div class="flight-info-item">
            <h4>Flight Information</h4>
            <p>Flight Code: <%= detail.getMaChuyenBay()%></p>
            <p>From: <%= detail.getSanBayDi()%> ( <span><%= detail.getQuocGiaDi()%></span> ) </p>            
            <p>To: <%= detail.getSanBayDen()%> ( <span><%= detail.getQuocGiaDen()%></span> ) </p>                        
            <p>Take-off: <%= detail.getThoiGianKhoiHanh()%></p>            
            <p>Landing: <%= detail.getThoiGianHaCanh()%></p>            
            <p>Approval: <%= detail.getTinhTrangChuyenBay()%></p>                        
            <p>Status: <%= detail.getStatus()%></p>                        
        </div>

        <% }
            }%>
    </div>
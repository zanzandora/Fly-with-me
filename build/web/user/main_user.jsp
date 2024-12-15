<%-- 
    Document   : main_user
    Created on : Nov 9, 2024, 11:41:00 PM
    Author     : pc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div class="hero-section">
    <div class="hero-content">
        <h1>Book Your Flight</h1>
    </div>
</div>
<!--    SEARCH-->
<jsp:include page="./search_flight.jsp" />
<!--FLY AD-->
<div class="flight-info">
    <jsp:include page="./search_flight_result.jsp" />
</div>

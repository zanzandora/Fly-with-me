<%-- 
    Document   : airline_page
    Created on : Nov 14, 2024, 10:39:34 PM
    Author     : pc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpSession" %>
<%
    String baseURL = getServletContext().getInitParameter("baseURL");
    if (session == null) {
        session = request.getSession(false);
    }
    String avatar = (session != null && session.getAttribute("avatar") != null)
            ? (String) session.getAttribute("avatar") : "";
    String maHang = (session != null && session.getAttribute("maHang") != null)
            ? (String) session.getAttribute("maHang") : "Guest";
    String tenHang = (session != null && session.getAttribute("tenHang") != null)
            ? (String) session.getAttribute("tenHang") : "Guest";
%>

<%
    String FlightEndpoint = request.getContextPath() + "/AirlineFlightManagement";
    String BookingEndpoint = request.getContextPath() + "/AirlinePaymentManagement";
    String SeatsEndpoit = request.getContextPath() + "/AirlineSeatsManagement";


%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Plush Airlines - Flight Management</title>
        <link rel="stylesheet" href="<%= baseURL%>/assets/css/airline.css"/>
        <style>

        </style>
    </head>
    <body>
        <div class="admin-container">
            <div class="sidebar">
                <div class="airline-info">
                    <div class="airline-logo">
                        <img src="<%= baseURL%>/assets/img/avarta_airline/<%= avatar%>" alt="Airline Logo">
                    </div>
                    <h2><%= tenHang%></h2>
                    <p>Airline ID: <%= maHang%></p>
                </div>
                <div class="sidebar-menu">
                    <a href="airline_page.jsp"><i class="fas fa-plane"></i> Flights</a>
                    <a href="airline_page.jsp?action=managementPrice"><i class="fas fa-dollar-sign"></i> Pricing</a>
                    <a href="airline_page.jsp?action=managementSeats"><i class="fas fa-chair"></i> Seat Management</a>
                    <a href="airline_page.jsp"><i class="fas fa-money-check"></i> Payments</a>
                    <a href="<%= baseURL%>/LogOutServlet"><i class="fas fa-sign-out-alt"></i> Logout</a>
                </div>
            </div>

            <div class="main-content">

                <%
                    String action = request.getParameter("action");
                    String pageToInclude = "./airline_total_data.jsp"; // Giá trị mặc định

                    if (action != null) {
                        switch (action) {
                            case "createFlight":
                                pageToInclude = "./airlineFlight/airline_flight_create.jsp";
                                break;
                            case "editFlight":
                                pageToInclude = "./airlineFlight/airline_flight_edit.jsp";
                                break;
                            case "detailsFlight":
                                pageToInclude = "./airlineFlight/airline_flight_details.jsp";
                                break;
                            case "managementPrice":
                                pageToInclude = "./airlinePrice/airline_price_management.jsp";
                                break;
                            case "detailsPayment":
                                pageToInclude = "./airlinePayment/airline_payment_details.jsp";
                                break;
                            case "managementSeats":
                                pageToInclude = "./airlineSeats/airline_seats_management.jsp";
                                break;
                            case "editSeat":
                                pageToInclude = "./airlineSeats/airline_seat_edit.jsp";
                                break;
                        }
                    }
                %>
                <jsp:include page="<%= pageToInclude%>" />

                <% if (action == null) { %>
                <jsp:include page="./airlineFlight/airline_flight_management.jsp" />
                <jsp:include page="./airlinePayment/airline_payment_management.jsp" />

                <% }%>




            </div>
        </div>
        <script>
            document.addEventListener('DOMContentLoaded', function () {

                const seats = document.querySelectorAll('.seat');
                seats.forEach(seat => {
                    seat.addEventListener('click', function () {
                        alert('Seat management functionality to be implemented');
                    });
                });
            });


            window.onload = function () {
                const fetchData = (url, responseType = "text") => {
                    return fetch(url)
                            .then(response => {
                                console.log(`Response status from ${url}:`, response.status);
                                if (!response.ok) {
                                    throw new Error(`HTTP error, status = ${response.status}`);
                                }
                                return responseType === "json" ? response.json() : response.text();
                            })
                            .catch(error => {
                                console.error(`Error loading data from ${url}:`, error);
                                throw error;
                            });
                };

                // Fetch admin total data
                fetchData('<%= baseURL%>/AirlineTotalDataServlet', "json")
                        .then(data => {
                            document.getElementById('Revenue').innerText = data.revenue;
                            document.getElementById('PendingApprovals').innerText = data.pendingApprovals;
                        });

                // Fetch data for airlines, AMs, and customers
                const endpoints = [
                    "<%= FlightEndpoint%>",
                    "<%= BookingEndpoint%>"
                ];

                endpoints.forEach(endpoint => {
                    fetchData(endpoint)
                            .then(data => {
                                console.log(`Data loaded successfully from ${endpoint}.`);
                            });
                });
            };


        </script>
        <%
            String errorMessage = (String) session.getAttribute("errorMessage");
            if (errorMessage != null) {
        %>
        <script>
            alert("<%= errorMessage%>");
        </script>
        <%
                session.removeAttribute("errorMessage");
            }
        %>

    </body>
</html>

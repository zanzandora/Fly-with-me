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
    String maSanbay = (session != null && session.getAttribute("maSanBay") != null)
            ? (String) session.getAttribute("maSanBay") : "Guest";
    String tenSanbay = (session != null && session.getAttribute("tenSanBay") != null)
            ? (String) session.getAttribute("tenSanBay") : "Guest";
%>

<%
    String FlightEndpoint = request.getContextPath() + "/ManagerFlightManagement";
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
                    <h2><%= tenSanbay%></h2>
                    <p>Airport ID: <%= maSanbay%></p>
                </div>
                <div class="sidebar-menu">
                    <a href="manager_page.jsp"><i class="fas fa-plane"></i> Flights</a>
                    <a href="<%= baseURL%>/LogOutServlet"><i class="fas fa-sign-out-alt"></i> Logout</a>
                </div>  
            </div>

            <div class="main-content">

                <%
                    String action = request.getParameter("action");
                    String pageToInclude = "./manager_total_data.jsp"; 

                    if (action != null) {
                        switch (action) {
                            case "approvalFlight":
                                pageToInclude = "./manager_approvalFlight.jsp";
                                break;
                            case "detailsFlight":
                                pageToInclude = "./manager_flight_details.jsp";
                                break;
                        }
                    }
                %>
                <jsp:include page="<%= pageToInclude%>" />

                <% if (action == null) { %>
                <jsp:include page="./manager_flight.jsp" />

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
                                console.log(`Response status from `+url+`:`, response.status);
                                if (!response.ok) {
                                    throw new Error(`HTTP error, status = `+response.status);
                                }
                                return responseType === "json" ? response.json() : response.text();
                            })
                            .catch(error => {
                                console.error(`Error loading data from ${url}:`, error);
                                throw error;
                            });
                };

                // Fetch admin total data
                fetchData('<%= baseURL%>/ManagerTotalDataServlet', "json")
                        .then(data => {
                            document.getElementById('Approved').innerText = data.approved;
                            document.getElementById('PendingApprovals').innerText = data.pendingApprovals;
                        });

                // Fetch data for airlines, AMs, and customers
                const endpoints = [
                    "<%= FlightEndpoint%>"
                ];

                endpoints.forEach(endpoint => {
                    fetchData(endpoint)
                            .then(data => {
                                console.log(`Data loaded successfully from `+endpoint);
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

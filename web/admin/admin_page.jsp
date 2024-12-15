<%-- 
    Document   : admin_page
    Created on : Nov 9, 2024, 10:46:56 AM
    Author     : pc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%      String baseURL = getServletContext().getInitParameter("baseURL");
    String airlineEndpoint = request.getContextPath() + "/AdminAirlineManagement";
    String airportEndpoint = request.getContextPath() + "/AdminAirportManagement";
    String customerEndpoint = request.getContextPath() + "/AdminCustomerManagement";
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin page</title>
        <link rel="stylesheet" href="<%= baseURL%>/assets/css/admin.css"/>
    </head>
    <body>
        <div class="admin-container">
            <div class="sidebar">
                <h2>Admin Dashboard</h2>
                <div class="sidebar-menu">
                    <a href="#Airline-Management"><i class="fas fa-plane"></i> Airlines</a>
                    <a href="#Airport-Management"><i class="fas fa-building"></i> Airports</a>
                    <a href="#Customer-Management"><i class="fas fa-users"></i> Customers</a>
                    <a href="<%= baseURL%>/LogOutServlet"><i class="fas fa-sign-out-alt"></i> Logout</a>
                </div>
            </div>

            <div class="main-content">
                <%
                    String action = request.getParameter("action");
                    String pageToInclude = "./admin_total_data.jsp"; // Giá trị mặc định

                    if (action != null) {
                        switch (action) {
                            case "createAirline":
                                pageToInclude = "./airlineManagement/admin_airline_create.jsp";
                                break;
                            case "editAirline":
                                pageToInclude = "./airlineManagement/admin_airline_edit.jsp";
                                break;
                            case "createAirport":
                                pageToInclude = "./airportManagement/admin_airport_create.jsp";
                                break;
                            case "editAirport":
                                pageToInclude = "./airportManagement/admin_airport_edit.jsp";
                                break;
                            case "createCustomer":
                                pageToInclude = "./customerManagement/admin_customer_create.jsp";
                                break;
                            case "editCustomer":
                                pageToInclude = "./customerManagement/admin_customer_edit.jsp";
                                break;
                        }
                    }
                %>
                <jsp:include page="<%= pageToInclude%>" />

                <% if (action == null) { %>
                <jsp:include page="./airlineManagement/admin_airline_management.jsp" />
                <jsp:include page="./airportManagement/admin_airport_management.jsp" />
                <jsp:include page="./customerManagement/admin_customer_management.jsp" />
                <% }%>
            </div>



            <script>
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
                    fetchData('<%= baseURL%>/AdminTotalDataServlet', "json")
                            .then(data => {
                                document.getElementById('totalAirlines').innerText = data.totalAirlines;
                                document.getElementById('totalAirports').innerText = data.totalAirports;
                                document.getElementById('totalCustomers').innerText = data.totalCustomers;
                            });

                    // Fetch data for airlines, AMs, and customers
                    const endpoints = [
                        "<%= airlineEndpoint%>",
                        "<%= airportEndpoint%>",
                        "<%= customerEndpoint%>"
                    ];

                    endpoints.forEach(endpoint => {
                        fetchData(endpoint)
                                .then(data => {
                                    console.log(`Data loaded successfully from ${endpoint}.`);
                                });
                    });
                };
                
            </script>

    </body>
</html>

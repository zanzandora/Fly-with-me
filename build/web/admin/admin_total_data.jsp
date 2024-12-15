<%-- 
    Document   : admin_total_data
    Created on : Nov 15, 2024, 12:18:16 AM
    Author     : pc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String baseURL = getServletContext().getInitParameter("baseURL");
%>

<div class="dashboard-cards">
                <div class="card">
                    <h3>Total Airlines</h3>
                    <p><span id="totalAirlines"> 0 </span> Active Airlines</p>
                </div> 
                <div class="card">
                    <h3>Total Airports</h3>
                    <p><span id="totalAirports"> 0 </span> Airports</p>
                </div>
                <div class="card">
                    <h3>Total Customers</h3>
                    <p><span id="totalCustomers"> 0 </span> Registered Users</p>
                </div>
            </div>

        
<%-- 
    Document   : airline_login
    Created on : Nov 13, 2024, 11:43:00 PM
    Author     : pc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String baseURL = getServletContext().getInitParameter("baseURL");

    String errorMessage = request.getParameter("error");

%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Airline Login</title>
        <link rel="stylesheet"  type="text/css" href="<%= baseURL%>/assets/css/sign_in_up.css">

    </head>
    <body>
        <div class="container">
            <div class="heading">Airline </div>
            <form class="form" action="<%= baseURL%>/AirlineLoginServlet" method="POST">
                <input
                    placeholder="Code Airline"
                    name="codeAirline"
                    type="text"
                    class="input"
                    required=""
                    />
                <input
                    placeholder="Password"
                    name="passwordAirline"
                    type="password"
                    class="input"
                    required=""
                    />
                <span class="forgot-password"><a href="#">Forgot Password ?</a></span>
                <input value="Sign In" type="submit" class="login-button" />
            </form>


            <p><a href="<%= baseURL%>/signIn.jsp">Back</a> </p>
        </div>
        <%
            if (errorMessage != null) {
        %>
        <script>
            alert("<%= errorMessage%>");
        </script>
        <% }%>

    </body>
</html>



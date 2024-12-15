<%-- 
    Document   : manager_login
    Created on : Nov 13, 2024, 11:35:15 PM
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
        <title>Manager Login</title>
        <link rel="stylesheet"  type="text/css" href="<%= baseURL%>/assets/css/sign_in_up.css">

    </head>
    <body>
        <div class="container">
            <div class="heading">Manager Airport</div>
            <form class="form" action="<%= baseURL%>/ManagerLoginServlet" method="POST">
                <input
                    placeholder="Code"
                    name="codeManager"
                    type="text"
                    class="input"
                    required=""
                    />
                <input
                    placeholder="Password"
                    name="passwordManager"
                    type="password"
                    class="input"
                    required=""
                    />
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


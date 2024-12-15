/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.flywithme.controller;

import com.flywithme.model.DBconnect;
import com.flywithme.utils.SessionUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author pc
 */
@WebServlet(name = "ManagerLoginServlet", urlPatterns = {"/ManagerLoginServlet"})
public class ManagerLoginServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ManagerLoginServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ManagerLoginServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         String codeManager = request.getParameter("codeManager");
        String passwordManager = request.getParameter("passwordManager");

        Connection conn = null;
        try {
            conn = DBconnect.getConnection();
            String sql = "SELECT * FROM sanbay WHERE MaSanBay = ? AND MatKhauSanBay= ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, codeManager);
            statement.setString(2, passwordManager);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Đăng nhập thành công
                String maSanBay = resultSet.getString("MaSanBay");
                String tenSanBay = resultSet.getString("TenSanBay");

                HttpSession session = request.getSession();
                session.setAttribute("maSanBay", maSanBay);
                session.setAttribute("tenSanBay", tenSanBay);
                try {
                    SessionUtils.loadFlightsWithoutAirlineCodeToSession(session);
                    SessionUtils.loadSeatsToSession(session);

                } catch (SQLException e) {
                    e.printStackTrace();
                    session.setAttribute("errorMessage", "Error loading flights: " + e.getMessage());
                }

                response.sendRedirect("manager/manager_page.jsp");
            } else {
                // Đăng nhập thất bại
                response.sendRedirect("manager/manager_login.jsp?error=Invalid email or password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

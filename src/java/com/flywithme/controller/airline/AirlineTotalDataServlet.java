/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.flywithme.controller.airline;

import com.flywithme.model.DBconnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@WebServlet(name = "AirlineTotalDataServlet", urlPatterns = {"/AirlineTotalDataServlet"})
public class AirlineTotalDataServlet extends HttpServlet {

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
            out.println("<title>Servlet AirlineTotalDataServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AirlineTotalDataServlet at " + request.getContextPath() + "</h1>");
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
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(false);
        String maHang = (session != null) ? (String) session.getAttribute("maHang") : null;

        if (maHang == null || maHang.isEmpty()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User is not logged in.");
            return;
        }
        try (Connection conn = DBconnect.getConnection()) {
            double revenue = calculateRevenueToday(conn, maHang);

            int pendingApprovals = calculatePendingApprovals(conn, maHang);
            int approved = calculateApproved(conn, maHang);

            // Đặt dữ liệu vào response dưới dạng JSON
            PrintWriter out = response.getWriter();
            String jsonResponse = "{\"revenue\": " + revenue
                    + ", \"pendingApprovals\": " + pendingApprovals
                    + ", \"approved\":" + approved + "}";
            out.print(jsonResponse);
            out.flush();

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching dashboard data.");
        }

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
        processRequest(request, response);
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

    private double calculateRevenueToday(Connection conn, String mahang) throws SQLException {
        String sql = "SELECT SUM(GiaTien) AS revenue "
                + "FROM chuyenbay "
                + "WHERE MaHang = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, mahang);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("revenue");
            }
        }
        return 0;
    }

    private int calculatePendingApprovals(Connection conn, String mahang) throws SQLException {
        String sql = "SELECT COUNT(*) AS pending_count FROM chuyenbay WHERE TinhTrangChuyenBay = 'No Authorized' AND MaHang = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, mahang);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("pending_count");
            }
        }
        return 0;
    }

    private int calculateApproved(Connection conn, String mahang) throws SQLException {
        String sql = "SELECT COUNT(*) AS approved FROM chuyenbay WHERE TinhTrangChuyenBay = 'Authorized' AND MaHang = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, mahang);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("approved");
            }
        }
        return 0;
    }

}

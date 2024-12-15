/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.flywithme.controller.admin;

import com.flywithme.model.DBconnect;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
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
@WebServlet(name = "AdminTotalDataServlet", urlPatterns = {"/AdminTotalDataServlet"})
public class AdminTotalDataServlet extends HttpServlet {

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
            out.println("<title>Servlet AdminTotalDataServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AdminTotalDataServlet at " + request.getContextPath() + "</h1>");
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
        
        int totalAirlines = 0;
        int totalAirports = 0;
        int totalCustomers = 0;
        Connection conn = null;

        try {
            conn = DBconnect.getConnection();

            // Tính tổng Airlines
            String sqlAirlines = "SELECT COUNT(*) FROM hanghangkhong";
            PreparedStatement stmtAirlines = conn.prepareStatement(sqlAirlines);
            ResultSet rsAirlines = stmtAirlines.executeQuery();
            if (rsAirlines.next()) {
                totalAirlines = rsAirlines.getInt(1);
            }

            // Tính tổng Airports
            String sqlAirports = "SELECT COUNT(*) FROM sanbay";
            PreparedStatement stmtAirports = conn.prepareStatement(sqlAirports);
            ResultSet rsAirports = stmtAirports.executeQuery();
            if (rsAirports.next()) {
                totalAirports = rsAirports.getInt(1);
            }

            // Tính tổng Customers
            String sqlCustomers = "SELECT COUNT(*) FROM khachhang";
            PreparedStatement stmtCustomers = conn.prepareStatement(sqlCustomers);
            ResultSet rsCustomers = stmtCustomers.executeQuery();
            if (rsCustomers.next()) {
                totalCustomers = rsCustomers.getInt(1);
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Đặt dữ liệu vào response dưới dạng JSON
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        String jsonResponse = "{\"totalAirlines\": " + totalAirlines + 
                              ", \"totalAirports\": " + totalAirports + 
                              ", \"totalCustomers\": " + totalCustomers + "}";
        out.print(jsonResponse); //Ghi chuỗi JSON vào luồng 
        out.flush(); //Đẩy dữ liệu từ bộ đệm ra client, đảm bảo rằng phản hồi được gửi đi ngay lập tức.

    
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

}

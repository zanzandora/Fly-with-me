/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.flywithme.controller.user;

import com.flywithme.dao.FlightDAO;
import com.flywithme.model.Flight;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
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
@WebServlet(name = "SearchFlightServlet", urlPatterns = {"/SearchFlightServlet"})
public class SearchFlightServlet extends HttpServlet {

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
            out.println("<title>Servlet SearchFlightServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SearchFlightServlet at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession();

        String noiCatCanh = request.getParameter("NoiCatCanh");
        String noiHaCanh = request.getParameter("NoiHaCanh");
        String thoiGianCatCanh = request.getParameter("ThoiGianCatCanhDanhNghia");
        String thoiGianHaCanh = request.getParameter("ThoiGianHaCanhDanhNghia");
        String maChuyenBay = request.getParameter("MaChuyenBay");

        // Gọi DAO để lấy danh sách chuyến bay theo tiêu chí
        FlightDAO flightDAO = new FlightDAO();
        List<Flight> flights;
        try {
            flights = flightDAO.searchFlights(noiCatCanh, noiHaCanh, thoiGianCatCanh, thoiGianHaCanh, maChuyenBay);
            if (flights.isEmpty()) {
                // Nếu không có kết quả, thêm thông báo
                request.setAttribute("messageSearch", "No flights were found matching your search criteria ...");
            } else {
                // Nếu có kết quả, thêm danh sách chuyến bay
                request.setAttribute("flights", flights);
            }
            request.getRequestDispatcher("/app.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error while searching flights: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
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

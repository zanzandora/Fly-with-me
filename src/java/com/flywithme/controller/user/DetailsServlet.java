/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.flywithme.controller.user;

import com.flywithme.dao.FlightDAO;
import com.flywithme.dao.SeatDAO;
import com.flywithme.model.Flight;
import com.flywithme.model.Seat;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
@WebServlet(name = "DetailsServlet", urlPatterns = {"/DetailsServlet"})
public class DetailsServlet extends HttpServlet {

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
            out.println("<title>Servlet DetailsServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DetailsServlet at " + request.getContextPath() + "</h1>");
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
        SeatDAO seatDAO = new SeatDAO();
        String maChuyenBay = request.getParameter("maChuyenBay");
        String action = request.getParameter("action");

        if (maChuyenBay != null && !maChuyenBay.isEmpty()) {
            try {
                Flight flight = null;
                List<Seat> seats = null;
                flight = FlightDAO.getFlightById(maChuyenBay);
                seats = seatDAO.getSeatsForFlight(maChuyenBay);
                if (flight != null) {
                    request.setAttribute("flight", flight);
                    request.setAttribute("seats", seats);
                    // Chuyển tiếp đến trang chi tiết chuyến bay
                    if ("details".equals(action) && maChuyenBay != null) {
                        request.getRequestDispatcher("/user/flight_details.jsp").forward(request, response);

                    } else {
                        request.getRequestDispatcher("/user/booking_form.jsp").forward(request, response);
                    }
                } else {
                    request.setAttribute("errorMessage", "Chuyến bay không tồn tại");
                    request.getRequestDispatcher("/error.jsp").forward(request, response);
                }
            } catch (SQLException ex) {
                Logger.getLogger(DetailsServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            // Nếu không có mã chuyến bay, chuyển hướng về trang lỗi
            request.setAttribute("errorMessage", "Mã chuyến bay không hợp lệ");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
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

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.flywithme.controller.manager;

import com.flywithme.controller.airline.AirlineFlightManagement;
import com.flywithme.dao.AirportDAO;
import com.flywithme.dao.FlightDAO;
import com.flywithme.model.Airport;
import com.flywithme.model.Flight;
import com.flywithme.utils.SessionUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 *
 * @author pc
 */
@WebServlet(name = "ManagerFlightManagement", urlPatterns = {"/ManagerFlightManagement"})
public class ManagerFlightManagement extends HttpServlet {

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
            out.println("<title>Servlet ManagerFlightManagement</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ManagerFlightManagement at " + request.getContextPath() + "</h1>");
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
        try {
            HttpSession session = request.getSession();

            List<Airport> airports = (List<Airport>) session.getAttribute("airports");

            // Nếu chưa có, lấy danh sách từ cơ sở dữ liệu và lưu vào session
            if (airports == null) {
                AirportDAO airportDAO = new AirportDAO();
                try {
                    airports = airportDAO.getAllAirports(); // Lấy danh sách sân bay từ database
                    session.setAttribute("airports", airports); // Lưu vào session
                } catch (SQLException e) {
                    e.printStackTrace();
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Cannot load airports.");
                    return;
                }
            }

            String action = request.getParameter("X-Action");
            if (action == null) {
                displayFlight(request, response);
                return;
            }
            switch (action) {
                case "edit" ->
                    approvedFlight(request, response);
                default ->
                    displayFlight(request, response);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ManagerFlightManagement.class.getName()).log(Level.SEVERE, null, ex);
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
        doGet(request, response);
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

    private void displayFlight(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        HttpSession session = request.getSession();

        if (session == null) {
            session = request.getSession(false);
        }

        String maSanBay = (String) session.getAttribute("maSanBay");
        if (maSanBay == null || maSanBay.isEmpty()) {
            session.setAttribute("errorMessage", "No airport logged in.");
            response.sendRedirect(request.getContextPath() + "/manager/manager_flight_details.jsp");
            return;
        }
        // Khởi tạo đối tượng DAO
        FlightDAO flightDAO = new FlightDAO();
        List<Flight> flights = null;
        // Lấy danh sách chuyến bay từ DAO
        SessionUtils.loadFlightsWithoutAirlineCodeToSession(session);
        flights = flightDAO.getAllFlightsWithoutAirlineCode(maSanBay);
        session.setAttribute("flights", flights);
        response.sendRedirect(request.getContextPath() + "/manager/manager_page.jsp");
    }

    private void approvedFlight(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        String maChuyenBay = request.getParameter("maChuyenBay");
        String action = request.getParameter("action");

        if (maChuyenBay == null || maChuyenBay.isEmpty()) {
            session.setAttribute("errorMessage", "Invalid flight ID.");
            response.sendRedirect(request.getContextPath() + "/error.jsp");
            return;
        }
        FlightDAO flightDAO = new FlightDAO();

        try {
            boolean isUpdated = false;

            // Xác định hành động
            if ("approve".equals(action)) {
                isUpdated = flightDAO.updateFlightStatus(maChuyenBay, "Authorized");
            } else if ("revoke".equals(action)) {
                isUpdated = flightDAO.updateFlightStatus(maChuyenBay, "No Authorized");
            }

            if (isUpdated) {
                // Cập nhật danh sách chuyến bay trong session
                SessionUtils.loadFlightsWithoutAirlineCodeToSession(session);
            } else {
                session.setAttribute("errorMessage", "Failed to update flight status for " + maChuyenBay + ".");
            }

            response.sendRedirect(request.getContextPath() + "/manager/manager_page.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
            session.setAttribute("errorMessage", "Error while updating flight: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/error.jsp");
        }
    }

}

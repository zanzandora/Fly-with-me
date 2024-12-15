/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.flywithme.controller.airline;

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
@WebServlet(name = "AirlineFlightManagement", urlPatterns = {"/AirlineFlightManagement"})
public class AirlineFlightManagement extends HttpServlet {

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
            out.println("<title>Servlet AirlineFlightManagement</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AirlineFlightManagement at " + request.getContextPath() + "</h1>");
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

        try {
            String action = request.getParameter("X-Action");
            if (action == null) {
                displayFlight(request, response);
                return;
            }
            switch (action) {
                case "create" ->
                    createFlight(request, response);
                case "edit" ->
                    editFlight(request, response);
                case "delete" ->
                    deleteFlight(request, response);
                default ->
                    displayFlight(request, response);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AirlineFlightManagement.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(AirlineFlightManagement.class.getName()).log(Level.SEVERE, null, ex);
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

    private void displayFlight(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        HttpSession session = request.getSession();

        if (session == null) {
            session = request.getSession(false);
        }

        String maHang = (String) session.getAttribute("maHang");
        if (maHang == null || maHang.isEmpty()) {
            session.setAttribute("errorMessage", "No airline logged in.");
            response.sendRedirect(request.getContextPath() + "/airline/airline_page.jsp");
            return;
        }
        // Khởi tạo đối tượng DAO
        FlightDAO flightDAO = new FlightDAO();
        List<Flight> flights = null;
        try {
            // Lấy danh sách chuyến bay từ DAO
            SessionUtils.loadFlightsToSession(session);

            flights = flightDAO.getAllFlights(maHang);

        } catch (SQLException e) {
            // Xử lý lỗi nếu có
            e.printStackTrace();

            session.setAttribute("errorMessage", "Database error: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/airline/airline_page.jsp");
        }
        session.setAttribute("flights", flights);
        response.sendRedirect(request.getContextPath() + "/airline/airline_page.jsp");
    }

    private void createFlight(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, ParseException {

        HttpSession session = request.getSession();
//        Kiểm tra MaHang có null không
        if (session == null) {
            session = request.getSession(false);
        }

        String maHang = (String) session.getAttribute("maHang");
        if (maHang == null || maHang.isEmpty()) {
            session.setAttribute("errorMessage", "No airline logged in.");
            response.sendRedirect(request.getContextPath() + "/airline/airline_page.jsp");
            return;
        }

        String maChuyenBay = "FL" + UUID.randomUUID().toString().substring(0, 5); // Tạo mã chuyến bay
        String noiCatCanh = request.getParameter("from");
        String noiHaCanh = request.getParameter("to");
        String thoiGianCatCanhStr = request.getParameter("takeoff");
        String thoiGianHaCanhStr = request.getParameter("landing");
        String status = request.getParameter("status");

// Chuyển từ String thành LocalDateTime
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime thoiGianCatCanh = LocalDateTime.parse(thoiGianCatCanhStr, formatter);
        LocalDateTime thoiGianHaCanh = LocalDateTime.parse(thoiGianHaCanhStr, formatter);

// Chuyển từ LocalDateTime thành Timestamp
        Timestamp thoiGianCatCanhTimestamp = Timestamp.valueOf(thoiGianCatCanh);
        Timestamp thoiGianHaCanhTimestamp = Timestamp.valueOf(thoiGianHaCanh);
        FlightDAO flightDAO = new FlightDAO();

        try {
            boolean isCreated = flightDAO.createFlight(maChuyenBay, maHang, noiCatCanh, noiHaCanh, thoiGianCatCanhTimestamp, thoiGianHaCanhTimestamp, status);
            if (isCreated) {

                // Thêm hãng hàng không vào session
                Flight newFlight = new Flight(maChuyenBay, maHang, noiCatCanh, noiHaCanh, thoiGianCatCanhTimestamp, thoiGianHaCanhTimestamp, "No Authorized", status);
                List<Flight> flights = (List<Flight>) session.getAttribute("flights");
                if (flights == null) {
                    flights = new ArrayList<>();
                }
                flights.add(newFlight);  // Thêm customer mới vào danh sách hiện tại
                session.setAttribute("flights", flights);  // Cập nhật session
                SessionUtils.loadFlightsToSession(session); // load lại session để update bảng mới
                response.sendRedirect(request.getContextPath() + "/airline/airline_page.jsp");

            } else {
                session.setAttribute("errorMessage", "Failed to create flight.");

            }
        } catch (SQLException e) {
            e.printStackTrace();
            session.setAttribute("errorMessage", "Database error: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/airline/airline_page.jsp");

        }
    }

    private void editFlight(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();

        String machuyenbay = request.getParameter("idFlight");
        String from = request.getParameter("from");
        String to = request.getParameter("to");
        String takeoffStr = request.getParameter("takeoff");
        String landingStr = request.getParameter("landing");
        String status = request.getParameter("status");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime takeoffTime = LocalDateTime.parse(takeoffStr, formatter);
        LocalDateTime landingTime = LocalDateTime.parse(landingStr, formatter);

        // Chuyển từ LocalDateTime thành Timestamp
        Timestamp thoiGianCatCanhTimestamp = Timestamp.valueOf(takeoffTime);
        Timestamp thoiGianHaCanhTimestamp = Timestamp.valueOf(landingTime);
        FlightDAO flightDAO = new FlightDAO();

        String message;

        try {

            // Gọi phương thức DAO để cập nhật hãng hàng không
            boolean isUpdated = flightDAO.updateFlight(machuyenbay, from, to, thoiGianCatCanhTimestamp, thoiGianHaCanhTimestamp, status);
            if (isUpdated) {
                message = "Customer updated successfully!";

                // Thêm hãng hàng không vào session
                List<Flight> flights = (List<Flight>) session.getAttribute("flights");
                flights = flightDAO.updateAndGetCustomersFromSession(machuyenbay, from, to, thoiGianCatCanhTimestamp, thoiGianHaCanhTimestamp, status, session);  // Thêm customer mới vào danh sách hiện tại  // Thêm customer đã cập nhật vào danh sách hiện tại
                session.setAttribute("flights", flights);  // Cập nhật session
                SessionUtils.loadFlightsToSession(session);
                // Chuyển hướng về trang admin_page.jsp
                response.sendRedirect(request.getContextPath() + "/airline/airline_page.jsp");
            } else {
               session.setAttribute("errorMessage", "Failed to edit flight.");
            }
        } catch (SQLException e) {
             e.printStackTrace();
            session.setAttribute("errorMessage", "Database error: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/airline/airline_page.jsp");
        }
    }

    private void deleteFlight(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        FlightDAO flightDAO = new FlightDAO();
        HttpSession session = request.getSession();

        String message;
        try {
            boolean isDeleted = flightDAO.deleteFlight(id);
            if (isDeleted) {
                message = "Customers deleted successfully!";

                // Cập nhật lại danh sách airlines trong session
                List<Flight> flights = (List<Flight>) session.getAttribute("flights");
                if (flights != null) {
                    flights.removeIf(flight -> flight.getMaChuyenBay().equals(id));
                    session.setAttribute("flights", flights);
                }
            } else {
                message = "Failed to delete customer!";

            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "An error occurred: " + e.getMessage();
        }
        session.setAttribute("message", message);
        response.sendRedirect(request.getContextPath() + "/airline/airline_page.jsp");
    }

}

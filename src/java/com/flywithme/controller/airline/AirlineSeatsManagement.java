/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.flywithme.controller.airline;

import com.flywithme.dao.SeatDAO;
import com.flywithme.model.Seat;
import com.flywithme.utils.SessionUtils;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@WebServlet(name = "AirlineSeatsManagement", urlPatterns = {"/AirlineSeatsManagement"})
public class AirlineSeatsManagement extends HttpServlet {

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
            out.println("<title>Servlet AirlineSeatsManagement</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AirlineSeatsManagement at " + request.getContextPath() + "</h1>");
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

        List<Seat> seats = (List<Seat>) session.getAttribute("seats");
        String maChuyenBay = request.getParameter("maChuyenBay");

        // Nếu chưa có, lấy danh sách từ cơ sở dữ liệu và lưu vào session
        if (seats == null) {
            SeatDAO seatDAO = new SeatDAO(); 
            try {
                seats = seatDAO.getSeatsForFlight(maChuyenBay); // Lấy danh sách sân bay từ database
            } catch (SQLException e) {
                session.setAttribute("errorMessage", e.getMessage());  
                session.setAttribute("errorDetails", e.toString());
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
            session.setAttribute("seats", seats); // Lưu vào session
        }

        String action = request.getParameter("X-Action");

        if (action == null) {
            displaySeat(request, response);
            return;
        }
        switch (action) {

            case "edit" ->
                editSeat(request, response);

            default ->
                displaySeat(request, response);
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

    private void displaySeat(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String maChuyenBay = request.getParameter("maChuyenBay");

        SeatDAO seatDAO = new SeatDAO();

        try {
            List<Seat> seats = seatDAO.getSeatsForFlight(maChuyenBay);
            session.setAttribute("maChuyenBay", maChuyenBay);
            session.setAttribute("seats", seats);
            response.sendRedirect(request.getContextPath() + "/airline/airline_page.jsp?action=managementSeats");
        } catch (SQLException e) {
            session.setAttribute("errorMessage", e.getMessage());
            session.setAttribute("errorDetails", e.toString());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    private void editSeat(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String status = request.getParameter("status");
        String maChuyenBay = (String) session.getAttribute("maChuyenBay");
        if (maChuyenBay == null || maChuyenBay.isEmpty()) {
            session.setAttribute("errorMessage", "No maChuyenbay session in.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);

            return;
        }

        String maGhe = request.getParameter("id");

        SeatDAO seatDAO = new SeatDAO();
        try {
            boolean isBooked = seatDAO.isSeatBooked(maGhe, maChuyenBay);

            if ("lockSeat".equals(status)) {
                if (isBooked) {
                    // Nếu ghế đã booked, không làm gì cả
                    session.setAttribute("errorMessage", "Cannot lock a booked seat.");
                    request.getRequestDispatcher("/error.jsp").forward(request, response);
                    return;
                }
                if (!seatDAO.isSeatExist(maGhe, maChuyenBay)) {
                    seatDAO.addSeatToStatusTable(maChuyenBay, maGhe, "Locked");
                    SessionUtils.loadSeatsToSession(session);

                } else {
                    seatDAO.updateSeatStatus(maGhe, "Locked");
                    SessionUtils.loadSeatsToSession(session);

                }
            } else if ("unlockSeat".equals(status)) {
                if (isBooked) {
                    // Nếu ghế đã booked, không làm gì cả
                    session.setAttribute("errorMessage", "Cannot unlock a booked seat.");
                    request.getRequestDispatcher("/error.jsp").forward(request, response);
                    return;
                }
                if (!seatDAO.isSeatExist(maGhe, maChuyenBay)) {
                    seatDAO.addSeatToStatusTable(maChuyenBay, maGhe, "Available");
                    SessionUtils.loadSeatsToSession(session);

                } else {
                    seatDAO.updateSeatStatus(maGhe, "Available");
                    SessionUtils.loadSeatsToSession(session);

                }
            }
            response.sendRedirect(request.getContextPath() + "/airline/airline_page.jsp?action=managementSeats");

        } catch (SQLException e) {
            e.printStackTrace();
            session.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

}

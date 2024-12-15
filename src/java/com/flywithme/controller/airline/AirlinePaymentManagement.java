/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.flywithme.controller.airline;

import com.flywithme.dao.BookingDAO;
import com.flywithme.model.Booking;
import com.flywithme.model.BookingDetails;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
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
@WebServlet(name = "AirlinePaymentManagement", urlPatterns = {"/AirlinePaymentManagement"})
public class AirlinePaymentManagement extends HttpServlet {

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
            out.println("<title>Servlet AirlinePaymentManagement</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AirlinePaymentManagement at " + request.getContextPath() + "</h1>");
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

        String action = request.getParameter("action");

        if ("detailsPayment".equals(action)) {
            detailsPayment(request, response);
        } else {
            displayPayment(request, response);
        };

    }

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

    private void displayPayment(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
String maHang = (String) session.getAttribute("maHang");
        if (maHang == null || maHang.isEmpty()) {
            session.setAttribute("errorMessage", "No airline logged in.");
            response.sendRedirect(request.getContextPath() + "/airline/airline_page.jsp");
            return;
        }
        // Truy vấn thông tin đặt chỗ từ cơ sở dữ liệu
        List<Booking> bookings = null;
        try {
            BookingDAO bookingDAO = new BookingDAO();
            bookings = bookingDAO.getAllBookings(maHang);
            // Lưu danh sách đặt chỗ vào session
            session.setAttribute("bookings", bookings);
            response.sendRedirect(request.getContextPath() + "/airline/airline_page.jsp");
        } catch (SQLException e) {
            e.printStackTrace();  // In lỗi ra console
            session.setAttribute("errorMessage", e.getMessage());  // Lưu lỗi vào request
            session.setAttribute("errorDetails", e.toString());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
// Chuyển hướng tới trang errorPage.jsp
        }

    }

    private void detailsPayment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String id = request.getParameter("id");

        // Truy vấn chi tiết thanh toán từ cơ sở dữ liệu
        List<BookingDetails> bookingDetails = null;
        try {
            BookingDAO bookingDAO = new BookingDAO();
            bookingDetails = bookingDAO.getBookingDetails(id); // Truy vấn chi tiết thanh toán từ DB
            session.setAttribute("bookingDetails", bookingDetails); // Lưu kết quả vào session
        } catch (SQLException e) {
            e.printStackTrace(); // Log lỗi
            session.setAttribute("errorMessage", e.getMessage()); // Lưu lỗi vào session
            request.getRequestDispatcher("/error.jsp").forward(request, response); // Chuyển hướng tới trang lỗi
            return; // Kết thúc phương thức để tránh lỗi thêm
        }

        // Chuyển tiếp đến trang JSP hiển thị chi tiết thanh toán
        response.sendRedirect(request.getContextPath() + "/airline/airline_page.jsp?action=detailsPayment&id="+id);

    }

}

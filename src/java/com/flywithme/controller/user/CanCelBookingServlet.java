/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.flywithme.controller.user;

import com.flywithme.dao.BookingDAO;
import com.flywithme.dao.SeatDAO;
import com.flywithme.model.BookingDetails;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author pc
 */
@WebServlet(name = "CanCelBookingServlet", urlPatterns = {"/CanCelBookingServlet"})
public class CanCelBookingServlet extends HttpServlet {

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
            out.println("<title>Servlet CanCelBookingServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CanCelBookingServlet at " + request.getContextPath() + "</h1>");
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
         String maDatCho = request.getParameter("maDatCho");
        String maGhe = request.getParameter("maGhe");
        String maChuyenBay = request.getParameter("maChuyenBay");
        String userId = request.getParameter("userId");
        
        BookingDAO bookingDAO = new BookingDAO();
        SeatDAO seatDAO = new SeatDAO();
            System.out.println("Step 1: Bắt đầu try catch quá trình cancel booking ticket");

        try {

            boolean isBookingCancelled = bookingDAO.cancelBooking(maDatCho);


            boolean isSeatUpdated = seatDAO.updateSeatStatusWhenCancel(maGhe, maChuyenBay, "Available");

            if (isBookingCancelled && isSeatUpdated) {
                 List<BookingDetails> bookingDetails = bookingDAO.getBookingDetailsByCus(userId); 

                // Đặt bookingDetails vào request
                request.setAttribute("bookingDetails", bookingDetails);
                request.getRequestDispatcher("/user/my_booking.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Failed to cancel booking. Please try again.");
                request.getRequestDispatcher("error_page.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Error canceling booking: " + e.getMessage());
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

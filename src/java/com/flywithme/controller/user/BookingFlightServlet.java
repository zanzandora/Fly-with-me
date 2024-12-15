/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.flywithme.controller.user;

import com.flywithme.dao.BookingDAO;
import com.flywithme.dao.FlightDAO;
import com.flywithme.dao.SeatDAO;
import com.flywithme.model.Booking;
import com.flywithme.model.BookingDetails;
import com.flywithme.model.Seat;
import com.flywithme.model.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
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
@WebServlet(name = "BookingFlightServlet", urlPatterns = {"/BookingFlightServlet"})
public class BookingFlightServlet extends HttpServlet {

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
            out.println("<title>Servlet BookingFlightServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet BookingFlightServlet at " + request.getContextPath() + "</h1>");
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
String userId = (String) session.getAttribute("id"); 
            BookingDAO bookingDAO = new BookingDAO();
            
            List<BookingDetails> bookingDetails = bookingDAO.getBookingDetailsByCus(userId);  // Get từ database
            
            if (bookingDetails != null) {
                request.setAttribute("bookingDetails", bookingDetails);
            } else {
                request.setAttribute("bookingDetails", new ArrayList<Booking>());
            }
            
            request.getRequestDispatcher("/user/my_booking.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(BookingFlightServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        SeatDAO seatDAO = new SeatDAO();
        BookingDAO bookingDAO = new BookingDAO();
        FlightDAO flightDAO = new FlightDAO();

        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String birth = request.getParameter("birth");
        String selectedSeat = request.getParameter("seat-info");
        String flightCode = request.getParameter("maChuyenBay");    

        // Tách chuỗi theo dấu "-".
        String[] seatDetails = selectedSeat.split("-");

        // Kiểm tra nếu dữ liệu không hợp lệ
        if (seatDetails.length != 3) {
            // Thông báo lỗi nếu dữ liệu không đủ
            request.setAttribute("errorMessage", "Invalid seat information.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        String seatNo = seatDetails[0]; // Mã ghế
        String seatClass = seatDetails[1]; // Lớp ghế
        double seatPrice = Double.parseDouble(seatDetails[2].replace("$", ""));  // Giá ghế (loại bỏ dấu $ nếu có)

        try {

            Seat seat = SeatDAO.getSeatStatusByMaGhe(seatNo,flightCode);

            if (seat != null && "Booked".equals(seat.getTinhTrang())) {
                request.setAttribute("errorMessage", "Seat " + seatNo + " is already booked.");
                request.getRequestDispatcher("error.jsp").forward(request, response);
                return;
            }

            // Kiểm tra nếu ghế chưa có trạng thái, thêm vào bảng tinhtrangghe
            if (!seatDAO.isSeatExist(seatNo, flightCode)) {
                seatDAO.addSeatToStatusTable(flightCode, seatNo, "Available");
            }

            seatDAO.updateSeatStatus(seatNo, "Booked");

            double flightPrice = flightDAO.getFlightPrice(flightCode);

            double totalPrice = seatPrice + flightPrice;

            String bookingCode = "B" + UUID.randomUUID().toString().substring(0, 5);
            Booking booking = new Booking();
            booking.setMaDatCho(bookingCode);
            booking.setMaChuyenBay(flightCode);
            booking.setMaGhe(seatNo);
            booking.setSoDinhDanh(id);
            booking.setSoTienDaThanhToan(totalPrice);
            System.out.println("Step 5: Adding booking record.");
            bookingDAO.addBookingRecord(booking);

            request.setAttribute("booking", booking);
            request.setAttribute("userId", id); 
            doGet(request,response);

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error processing your booking. Please try again later.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

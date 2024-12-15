/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.flywithme.utils;

import com.flywithme.dao.BookingDAO;
import com.flywithme.dao.FlightDAO;
import com.flywithme.dao.SeatDAO;
import com.flywithme.model.Booking;
import com.flywithme.model.Flight;
import com.flywithme.model.Seat;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.http.HttpSession;

/**
 *
 * @author pc
 */
public class SessionUtils {

    public static void loadFlightsToSession(HttpSession session) throws SQLException {
        String maHang = (session != null && session.getAttribute("maHang") != null)
                ? (String) session.getAttribute("maHang") : "Guest";
        try {
            FlightDAO flightDAO = new FlightDAO();
            List<Flight> flights = flightDAO.getAllFlights(maHang);
            session.setAttribute("flights", flights);
        } catch (SQLException e) {
            e.printStackTrace();
            session.setAttribute("errorMessage", "Error loading flights: " + e.getMessage());
        }
    }
    public static void loadSeatsToSession(HttpSession session) throws SQLException {
        String maChuyenBay = (session != null && session.getAttribute("maChuyenBay") != null)
                ? (String) session.getAttribute("maChuyenBay") : "Guest";
        try {
            SeatDAO seatDAO = new SeatDAO();
            List<Seat> seats = seatDAO.getSeatsForFlight(maChuyenBay);
            session.setAttribute("seats", seats);
        } catch (SQLException e) {
            e.printStackTrace();
            session.setAttribute("errorMessage", "Error loading flights: " + e.getMessage());
        }
    }
    public static void loadFlightsWithoutAirlineCodeToSession(HttpSession session) throws SQLException {
        String maSanBay = (session != null && session.getAttribute("maSanBay") != null)
                ? (String) session.getAttribute("maSanBay") : "Guest";
        try {
            FlightDAO flightDAO = new FlightDAO();
            List<Flight> flights = flightDAO.getAllFlightsWithoutAirlineCode(maSanBay);
            session.setAttribute("flights", flights);
        } catch (SQLException e) {
            e.printStackTrace();
            session.setAttribute("errorMessage", "Error loading flights: " + e.getMessage());
        }
    }
    public static void loadPaymentToSession(HttpSession session) throws SQLException {
        String maHang = (session != null && session.getAttribute("maHang") != null)
                ? (String) session.getAttribute("maHang") : "Guest";
        try {
            BookingDAO bookingDAO = new BookingDAO();
            List<Booking> bookings = bookingDAO.getAllBookings(maHang);
            session.setAttribute("bookings", bookings);
        } catch (SQLException e) {
            e.printStackTrace();
            session.setAttribute("errorMessage", "Error loading flights: " + e.getMessage());
        }
    }
}

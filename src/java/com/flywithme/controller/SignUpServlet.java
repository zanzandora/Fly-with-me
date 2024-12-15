/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.flywithme.controller;

import com.flywithme.model.DBconnect;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 *
 * @author pc
 */
@WebServlet(name = "SignUpServlet", urlPatterns = {"/SignUpServlet"})
public class SignUpServlet extends HttpServlet {

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
            out.println("<title>Servlet SignOutServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SignOutServlet at " + request.getContextPath() + "</h1>");
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
         // Lấy các thông tin từ form
         
        String hodem = request.getParameter("lastName");
        String ten = request.getParameter("firstName");
        String email = request.getParameter("email");
        String birthday = request.getParameter("birthday");
        String password = request.getParameter("password");
        String repassword = request.getParameter("rePassword");
        
        String sodinhdanh = "KH" + UUID.randomUUID().toString().substring(0, 5);

        // Kiểm tra xác nhận mật khẩu
        if (!password.equals(repassword)) {
            request.setAttribute("errorMessage", "Mật khẩu và xác nhận mật khẩu không khớp!");
            request.getRequestDispatcher("signUp.jsp").forward(request, response);
            return;
        }

        Connection conn = null;
        // Kết nối đến cơ sở dữ liệu và thêm user mới
        try {
            conn = DBconnect.getConnection();
            String sql = "INSERT INTO khachhang (SoDinhDanh,HoDem, Ten, Email, MatKhauKhach,NgayThangNamSinh) VALUES (?, ?, ?, ?, ? ,?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, sodinhdanh);
            statement.setString(2, hodem);
            statement.setString(3, ten);
            statement.setString(4, email);
            statement.setString(5, password);
            statement.setString(6, birthday);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                response.sendRedirect("signIn.jsp");
            } else {
                request.setAttribute("errorMessage", "Đăng ký thất bại, vui lòng thử lại!");
                request.getRequestDispatcher("signUp.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi cơ sở dữ liệu: " + e.getMessage());
            request.getRequestDispatcher("signUp.jsp").forward(request, response);
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

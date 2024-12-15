/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.flywithme.controller.admin;

import com.flywithme.dao.AirlineDAO;
import com.flywithme.model.Airline;
import com.flywithme.model.DBconnect;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author pc
 */
@WebServlet(name = "AdminAirlineManagement", urlPatterns = {"/AdminAirlineManagement"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50 // 50MB
)
public class AdminAirlineManagement extends HttpServlet {

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
            out.println("<title>Servlet AdminAirlineManagement</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AdminAirlineManagement at " + request.getContextPath() + "</h1>");
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
            String action = request.getParameter("X-Action");
            if (action == null) {
                displayAirlines(request, response);
                return;
            }
            switch (action) {
                case "create" ->
                    createAirline(request, response);
                case "edit" ->
                    editAirline(request, response);
                case "delete" ->
                    deleteAirline(request, response);
                default ->
                    displayAirlines(request, response);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminAirlineManagement.class.getName()).log(Level.SEVERE, null, ex);
        }

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

    private void createAirline(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();

        String maHang = request.getParameter("airlineId");
        String tenHang = request.getParameter("airlineName");
        String password = request.getParameter("airlinePassword");
        String country = request.getParameter("country");

        // Thư mục lưu file avatar
        String uploadPath = getServletContext().getRealPath("") + "assets/img/avarta_airline";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir(); // Tạo thư mục nếu chưa tồn tại
        }

        // Lấy file avatar
        Part avatarPart = request.getPart("avatar");

        // Khởi tạo đối tượng AirlineDAO
        AirlineDAO airlineDAO = new AirlineDAO();
        String message;

        try {
            // Gọi phương thức DAO để thêm hãng hàng không
            boolean isCreated = airlineDAO.createAirline(maHang, tenHang, password, country, avatarPart, uploadPath);
            if (isCreated) {
                message = "Airline created successfully!";

                // Thêm hãng hàng không vào session
                Airline newAirline = new Airline(maHang, tenHang, password, country, getFileName(avatarPart));
                List<Airline> airlines = (List<Airline>) session.getAttribute("airlines");
                if (airlines == null) {
                    airlines = new ArrayList<>();
                }
                airlines.add(newAirline);  // Thêm airline mới vào danh sách hiện tại
                session.setAttribute("airlines", airlines);  // Cập nhật session

                response.sendRedirect(request.getContextPath() + "/admin/admin_page.jsp");
            } else {
                message = "Failed to create airline!";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            message = "An error occurred: " + ex.getMessage();
        }

        session.setAttribute("message", message);

    }

    private String getFileName(Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf("=") + 2, content.length() - 1);
            }
        }
        return null;
    }

    private void editAirline(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();

        String maHang = request.getParameter("airlineId");
        String tenHang = request.getParameter("airlineName");
        String password = request.getParameter("airlinePassword");
        String country = request.getParameter("country");

        // Thư mục lưu file avatar
        String uploadPath = getServletContext().getRealPath("") + "assets/img/avarta_airline";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir(); // Tạo thư mục nếu chưa tồn tại
        }

        // Lấy file avatar
        Part avatarPart = request.getPart("avatar");

        // Khởi tạo đối tượng AirlineDAO
        AirlineDAO airlineDAO = new AirlineDAO();
        String message;

        try {
            // Lấy avatar cũ từ cơ sở dữ liệu 
            String currentAvatar = airlineDAO.getCurrentAvatar(maHang);  // Lấy ảnh hiện tại

            // Gọi phương thức DAO để cập nhật hãng hàng không
            boolean isUpdated = airlineDAO.updateAirline(maHang, tenHang, password, country, avatarPart, uploadPath, currentAvatar);
            if (isUpdated) {
                message = "Airline updated successfully!";

                // Thêm hãng hàng không vào session
                Airline updatedAirline = new Airline(maHang, tenHang, password, country, currentAvatar);
                List<Airline> airlines = (List<Airline>) session.getAttribute("airlines");
                airlines = airlineDAO.updateAndGetAirlinesFromSession(maHang, tenHang, password, country, avatarPart, uploadPath, airlines);
                session.setAttribute("airlines", airlines);  // Cập nhật session

                // Chuyển hướng về trang admin_page.jsp
                response.sendRedirect(request.getContextPath() + "/admin/admin_page.jsp");
            } else {
                message = "Failed to update airline!";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            message = "An error occurred: " + ex.getMessage();
        }

        session.setAttribute("message", message);
    }

    private String saveAvatar(Part avatarPart) throws IOException {
        // Xác định thư mục lưu trữ ảnh
        String uploadDir = getServletContext().getRealPath("/assets/img/avarta_airline");

        // Tạo thư mục nếu chưa tồn tại
        File uploadDirFile = new File(uploadDir);
        if (!uploadDirFile.exists()) {
            uploadDirFile.mkdirs();
        }

        // Lấy tên file từ header Content-Disposition
        String fileName = Paths.get(avatarPart.getSubmittedFileName()).getFileName().toString();

        // Tạo đường dẫn đầy đủ để lưu file
        String filePath = uploadDir + File.separator + fileName;

        // Lưu file vào thư mục đã chỉ định
        try (InputStream inputStream = avatarPart.getInputStream(); OutputStream outputStream = new FileOutputStream(filePath)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }

        return fileName; // Trả về tên file để lưu vào cơ sở dữ liệu
    }

    private void deleteAirline(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String maHang = request.getParameter("id");
        AirlineDAO airlineDAO = new AirlineDAO();
        HttpSession session = request.getSession();

        String message;
        try {
            boolean isDeleted = airlineDAO.deleteAirline(maHang);
            if (isDeleted) {
                message = "Airline deleted successfully!";

                // Cập nhật lại danh sách airlines trong session
                List<Airline> airlines = (List<Airline>) session.getAttribute("airlines");
                if (airlines != null) {
                    airlines.removeIf(airline -> airline.getMaHang().equals(maHang));
                    session.setAttribute("airlines", airlines);
                }
            } else {
                message = "Failed to delete airline!";
                
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "An error occurred: " + e.getMessage();
        }
        session.setAttribute("message", message);
        response.sendRedirect(request.getContextPath() + "/admin/admin_page.jsp");
    }

    private void displayAirlines(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {

        // Khởi tạo đối tượng DAO
        AirlineDAO airlineDAO = new AirlineDAO();
        List<Airline> airlines = null;
        HttpSession session = request.getSession();
        try {
            // Lấy danh sách hãng hàng không từ DAO
            airlines = airlineDAO.getAllAirlines();
        } catch (SQLException e) {
            // Xử lý lỗi nếu có
            e.printStackTrace();
        }
        session.setAttribute("airlines", airlines);
        response.sendRedirect(request.getContextPath() + "/admin/admin_page.jsp");
    }

}

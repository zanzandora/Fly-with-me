/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.flywithme.controller.admin;

import com.flywithme.dao.CustomerDAO;
import com.flywithme.model.Customer;
import com.flywithme.model.DBconnect;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
@WebServlet(name = "AdminCustomerManagement", urlPatterns = {"/AdminCustomerManagement"})
public class AdminCustomerManagement extends HttpServlet {

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
            out.println("<title>Servlet AdminCustomerManagement</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AdminCustomerManagement at " + request.getContextPath() + "</h1>");
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
                displayCustomer(request, response);
                return;
            }
            switch (action) {
                case "create" ->
                    createCustomer(request, response);
                case "edit" ->
                    editCustomer(request, response);
                case "delete" ->
                    deleteCustomer(request, response);
                default ->
                    displayCustomer(request, response);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminCustomerManagement.class.getName()).log(Level.SEVERE, null, ex);
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

    private void displayCustomer(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        // Khởi tạo đối tượng DAO
        CustomerDAO customerDAO = new CustomerDAO();
        List<Customer> customers = null;
        HttpSession session = request.getSession();
        try {
            // Lấy danh sách khách hàng từ DAO
            customers = customerDAO.getAllCustomers();
        } catch (SQLException e) {
            // Xử lý lỗi nếu có
            e.printStackTrace();
        }
        session.setAttribute("customers", customers);
        response.sendRedirect(request.getContextPath() + "/admin/admin_page.jsp");
    }

    private void createCustomer(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        
        String sodinhdanh = "KH" + UUID.randomUUID().toString().substring(0, 5);
        String firstName = request.getParameter("firstNameCustomer");
        String lastName = request.getParameter("lastNameCustomer");
        String email = request.getParameter("emailCustomer");
        String password = request.getParameter("passwordCustomer");
        String birthday = request.getParameter("birthdayCustomer");
        
        // Khởi tạo đối tượng CustomerDAO
        CustomerDAO customerDAO = new CustomerDAO();
        String message;

        try {
            // Gọi phương thức DAO để thêm khách hàng
            boolean isCreated = customerDAO.createCustomer(sodinhdanh, lastName, firstName, email, password, birthday );
            if (isCreated) {
                message = "Customer created successfully!";

                // Thêm hãng hàng không vào session
                Customer newCustomer = new Customer(sodinhdanh, lastName, firstName, email, password, birthday);
                List<Customer> customers = (List<Customer>) session.getAttribute("customers");
                if (customers == null) {
                    customers = new ArrayList<>();
                }
                customers.add(newCustomer);  // Thêm customer mới vào danh sách hiện tại
                session.setAttribute("customers", customers);  // Cập nhật session

                response.sendRedirect(request.getContextPath() + "/admin/admin_page.jsp");
            } else {
                message = "Failed to create customers!";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            message = "An error occurred: " + ex.getMessage();
        }

        session.setAttribute("message", message);
    }

    private void editCustomer(HttpServletRequest request, HttpServletResponse response) {
         HttpSession session = request.getSession();

        String id = request.getParameter("idCustomer");
        String firstName = request.getParameter("firstNameCustomer");
        String lastName = request.getParameter("lastNameCustomer");
        String email = request.getParameter("emailCustomer");
        String password = request.getParameter("passwordCustomer");
        String birthday = request.getParameter("birthdayCustomer");

        

        // Khởi tạo đối tượng AirlineDAO
        CustomerDAO customerDAO = new CustomerDAO();
        String message;

        try {
            

            // Gọi phương thức DAO để cập nhật hãng hàng không
            boolean isUpdated = customerDAO.updateCustomer(id, lastName, firstName, email, password, birthday);
            if (isUpdated) {
                message = "Customer updated successfully!";

                // Thêm hãng hàng không vào session
                Customer updatedCustomer = new Customer(id, lastName, firstName, email, password, birthday);
                List<Customer> customers = (List<Customer>) session.getAttribute("customers");
                customers = customerDAO.updateAndGetCustomersFromSession(id, lastName, firstName, email, password, birthday, customers);  // Thêm customer mới vào danh sách hiện tại  // Thêm customer đã cập nhật vào danh sách hiện tại
                session.setAttribute("customers", customers);  // Cập nhật session

                // Chuyển hướng về trang admin_page.jsp
                response.sendRedirect(request.getContextPath() + "/admin/admin_page.jsp");
            } else {
                message = "Failed to update airline!";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            message = "An error occurred: " + ex.getMessage();
        }
    }

    private void deleteCustomer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        CustomerDAO customerDAO = new CustomerDAO();
        HttpSession session = request.getSession();

        String message;
        try {
            boolean isDeleted = customerDAO.deleteCustomer(id);
            if (isDeleted) {
                message = "Customers deleted successfully!";

                // Cập nhật lại danh sách airlines trong session
                List<Customer> customers = (List<Customer>) session.getAttribute("customers");
                if (customers != null) {
                    customers.removeIf(customer -> customer.getSoDinhDanh().equals(id));
                    session.setAttribute("customers", customers);
                }
            } else {
                message = "Failed to delete customer!";
                
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "An error occurred: " + e.getMessage();
        }
        session.setAttribute("message", message);
        response.sendRedirect(request.getContextPath() + "/admin/admin_page.jsp");
    }

}

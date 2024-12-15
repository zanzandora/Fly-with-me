/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.flywithme.dao;

import com.flywithme.model.Customer;
import com.flywithme.model.DBconnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pc
 */
public class CustomerDAO {
    // Hàm lấy danh sách các hãng hàng không
    public List<Customer> getAllCustomers() throws SQLException {
        List<Customer> customers = new ArrayList<>();
        
        // Kết nối cơ sở dữ liệu
        try (Connection conn = DBconnect.getConnection()) {
            String sql = "SELECT * FROM khachhang";  // Truy vấn lấy tất cả dữ liệu từ bảng
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();  // Thực hiện truy vấn

            // Duyệt qua kết quả và thêm vào danh sách
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setSoDinhDanh(rs.getString("SoDinhDanh"));
                customer.setHoDem(rs.getString("HoDem"));
                customer.setTen(rs.getString("Ten"));
                customer.setEmail(rs.getString("Email"));
                customer.setMatKhauKhach(rs.getString("MatKhauKhach"));                
                customer.setNgayThangNamSinh(rs.getString("NgayThangNamSinh"));
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return customers;
    }
     // Lấy thông tin Customer theo ID
    public static Customer getCustomerById(String id) {
        Customer customer = null;
        String query = "SELECT * FROM khachhang WHERE SoDinhDanh = ?";
        try (Connection conn = DBconnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                customer = new Customer();
                customer.setSoDinhDanh(rs.getString("SoDinhDanh"));
                customer.setHoDem(rs.getString("HoDem"));
                customer.setTen(rs.getString("Ten"));
                customer.setEmail(rs.getString("Email"));
                customer.setMatKhauKhach(rs.getString("MatKhauKhach"));
                customer.setNgayThangNamSinh(rs.getString("NgayThangNamSinh"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }
    
    // Hàm thêm Khách hàng vào cơ sở dữ liệu
    public boolean createCustomer(String soDinhDanh, String hoDem, String ten, String email, String matKhauKhach, String ngayThangNamSinh) {


        try (Connection conn = DBconnect.getConnection()) {
            String sql = "INSERT INTO khachhang (SoDinhDanh, HoDem, Ten, Email, MatKhauKhach, NgayThangNamSinh) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, soDinhDanh);
            stmt.setString(2, hoDem);
            stmt.setString(3, ten);
            stmt.setString(4, email);
            stmt.setString(5, matKhauKhach);
            stmt.setString(6, ngayThangNamSinh);
            // Thực thi truy vấn
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                return true;  // Thành công
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;  // Thất bại
    }
    // Cập nhật thông tin Customer
    public boolean updateCustomer(String soDinhDanh, String hoDem, String ten, String email, String matKhauKhach, String ngayThangNamSinh) {
         
        try (Connection conn = DBconnect.getConnection()) {
            String sql = "UPDATE khachhang SET HoDem = ?, Ten = ?, Email = ?, MatKhauKhach = ?, NgayThangNamSinh = ? WHERE SoDinhDanh = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, hoDem);
            stmt.setString(2, ten);
            stmt.setString(3, email);
            stmt.setString(4, matKhauKhach);  
            stmt.setString(5, ngayThangNamSinh);
            stmt.setString(6, soDinhDanh);
            // Thực thi truy vấn
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                return true;  // Thành công
            }
        } catch (SQLException e) {
            e.printStackTrace();
            
        }
        return false;
    }
    //Cập nhật dữ liệu trong session
    public List<Customer> updateAndGetCustomersFromSession(String soDinhDanh, String hoDem, String ten, String email, String matKhauKhach, String ngayThangNamSinh, List<Customer> sessionCustomer) throws SQLException {
        if (sessionCustomer != null) {
            for (Customer customer : sessionCustomer) {
                if (customer.getSoDinhDanh().equals(soDinhDanh)) {
                    customer.setHoDem(hoDem);
                    customer.setTen(ten);
                    customer.setEmail(email);                    
                    customer.setMatKhauKhach(matKhauKhach);
                    customer.setNgayThangNamSinh(ngayThangNamSinh);
                    break;
                }
            }
        } else {
            sessionCustomer = getAllCustomers();
        }
        return sessionCustomer;
    }
     // Phương thức xóa một hãng hàng không
    public boolean deleteCustomer(String soDinhDanh) {
    boolean isDeleted = false;
    try (Connection conn = DBconnect.getConnection()) {
        conn.setAutoCommit(false); 

        // Bước 1: Cập nhật trạng thái ghế về Available
        String updateSeatsSql = "UPDATE tinhtrangghe SET TinhTrangGhe = 'Available' "
                              + "WHERE MaGhe IN (SELECT MaGhe FROM datcho WHERE SoDinhDanh = ?)";
        try (PreparedStatement updateSeatsStmt = conn.prepareStatement(updateSeatsSql)) {
            updateSeatsStmt.setString(1, soDinhDanh);
            updateSeatsStmt.executeUpdate();
        }

        // Bước 2: Xóa khách hàng
        String deleteCustomerSql = "DELETE FROM khachhang WHERE SoDinhDanh = ?";
        try (PreparedStatement deleteCustomerStmt = conn.prepareStatement(deleteCustomerSql)) {
            deleteCustomerStmt.setString(1, soDinhDanh);
            int rowsAffected = deleteCustomerStmt.executeUpdate();
            isDeleted = rowsAffected > 0;
        }

        conn.commit(); // Commit transaction
    } catch (SQLException e) {
        e.printStackTrace();
        
    }
    return isDeleted;
}
}

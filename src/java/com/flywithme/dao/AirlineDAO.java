/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.flywithme.dao;

import com.flywithme.model.Airline;
import com.flywithme.model.DBconnect;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;


/**
 *
 * @author pc
 */
public class AirlineDAO {
    // Hàm lấy danh sách các hãng hàng không
    public List<Airline> getAllAirlines() throws SQLException {
        List<Airline> airlines = new ArrayList<>();
        
        // Kết nối cơ sở dữ liệu
        try (Connection conn = DBconnect.getConnection()) {
            String sql = "SELECT * FROM hanghangkhong";  // Truy vấn lấy tất cả dữ liệu từ bảng
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();  // Thực hiện truy vấn

            // Duyệt qua kết quả và thêm vào danh sách
            while (rs.next()) {
                Airline airline = new Airline();
                airline.setMaHang(rs.getString("MaHang"));
                airline.setTenHang(rs.getString("TenHang"));
                airline.setPasswordHangHangKhong(rs.getString("PasswordHangHangKhong"));
                airline.setCountry(rs.getString("Country"));
                airline.setAvatar(rs.getString("Avatar"));
                airlines.add(airline);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return airlines;
    }
     // Lấy thông tin Airline theo ID
    public static Airline getAirlineById(String id) {
        Airline airline = null;
        String query = "SELECT * FROM hanghangkhong WHERE MaHang = ?";
        try (Connection conn = DBconnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                airline = new Airline();
                airline.setMaHang(rs.getString("MaHang"));
                airline.setTenHang(rs.getString("TenHang"));
                airline.setPasswordHangHangKhong(rs.getString("PasswordHangHangKhong"));
                airline.setCountry(rs.getString("Country"));
                airline.setAvatar(rs.getString("Avatar"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return airline;
    }
    
    // Hàm thêm hãng hàng không vào cơ sở dữ liệu
    public boolean createAirline(String maHang, String tenHang, String password, String country, Part avatarPart, String uploadPath) {
        String fileName = getFileName(avatarPart);  // Lấy tên file avatar
        String avatarPath = uploadPath + File.separator + fileName;  // Đường dẫn lưu avatar

        try (Connection conn = DBconnect.getConnection()) {
            String sql = "INSERT INTO hanghangkhong (MaHang, TenHang, PasswordHangHangKhong, Country, Avatar) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, maHang);
            stmt.setString(2, tenHang);
            stmt.setString(3, password);
            stmt.setString(4, country);
            stmt.setString(5, fileName);

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
    // Cập nhật thông tin Airline
    public boolean updateAirline(String maHang, String tenHang, String password, String country, Part avatarPart, String uploadPath, String currentAvatar) {
         String fileName = currentAvatar; // Mặc định dùng ảnh cũ
        String avatarPath = uploadPath + File.separator + fileName;  // Đường dẫn lưu avatar

        // Nếu người dùng chọn ảnh mới, thì thay đổi ảnh
        if (avatarPart != null && avatarPart.getSize() > 0) {
            fileName = getFileName(avatarPart);  // Lấy tên file avatar mới
            avatarPath = uploadPath + File.separator + fileName;  // Cập nhật đường dẫn file mới
        }

        try (Connection conn = DBconnect.getConnection()) {
            String sql = "UPDATE hanghangkhong SET TenHang = ?, PasswordHangHangKhong = ?, Country = ?, Avatar = ? WHERE MaHang = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, tenHang);
            stmt.setString(2, password);
            stmt.setString(3, country);
            stmt.setString(4, fileName);  // Dùng tên file mới hoặc file cũ
            stmt.setString(5, maHang);

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
    public List<Airline> updateAndGetAirlinesFromSession(
        String maHang, 
        String tenHang, 
        String password, 
        String country, 
        Part avatarPart, 
        String uploadPath, 
        List<Airline> sessionAirline
) throws Exception {
    

    // Lấy avatar cũ từ cơ sở dữ liệu
    String currentAvatar = getCurrentAvatar(maHang);

    // Cập nhật thông tin hãng hàng không trong cơ sở dữ liệu
    boolean isUpdated = updateAirline(maHang, tenHang, password, country, avatarPart, uploadPath, currentAvatar);

    if (isUpdated) {
        // Nếu session có airlines, cập nhật trong session
        if (sessionAirline != null) {
            for (Airline airline : sessionAirline) {
                if (airline.getMaHang().equals(maHang)) {
                    airline.setTenHang(tenHang);
                    airline.setPasswordHangHangKhong(password);
                    airline.setCountry(country);

                    // Cập nhật avatar mới (nếu có)
                    if (avatarPart != null && avatarPart.getSize() > 0) {
                        airline.setAvatar(currentAvatar);
                    }
                    break;
                }
            }
        } else {
            // Nếu không có trong session, tải lại danh sách từ cơ sở dữ liệu
            sessionAirline = getAllAirlines();
        }
    } else {
        return null; // Trả về null nếu cập nhật thất bại
    }

    return sessionAirline;
}
     // Phương thức xóa một hãng hàng không
    public boolean deleteAirline(String maHang) {
        boolean isDeleted = false;
        try (Connection conn = DBconnect.getConnection()) {
            String sql = "DELETE FROM hanghangkhong WHERE MaHang = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, maHang);

            int rowsAffected = stmt.executeUpdate();
            isDeleted = rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isDeleted;
    }
    // Hàm lấy tên file từ Part (file upload)
    private String getFileName(Part part) {
        String header = part.getHeader("content-disposition");
        for (String cd : header.split(";")) {
            if (cd.trim().startsWith("filename")) {
                return cd.substring(cd.indexOf('=') + 2, cd.length() - 1);
            }
        }
        return null;
    }
    // Hàm lấy avatar hiện tại của hãng hàng không từ cơ sở dữ liệu
    public String getCurrentAvatar(String maHang) {
        String avatar = null;

        try (Connection conn = DBconnect.getConnection()) {
            String sql = "SELECT Avatar FROM hanghangkhong WHERE MaHang = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, maHang);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                avatar = rs.getString("Avatar");  // Lấy tên avatar từ cơ sở dữ liệu
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return avatar;  // Trả về tên file ảnh hoặc null nếu không có
    }
}

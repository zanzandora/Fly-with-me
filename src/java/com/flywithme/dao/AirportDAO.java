/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.flywithme.dao;

import com.flywithme.model.Airport;
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
public class AirportDAO {

    // Hàm lấy danh sách các hãng hàng không
    public List<Airport> getAllAirports() throws SQLException {
        List<Airport> airports = new ArrayList<>();

        // Kết nối cơ sở dữ liệu
        try (Connection conn = DBconnect.getConnection()) {
            String sql = "SELECT * FROM sanbay";  // Truy vấn lấy tất cả dữ liệu từ bảng
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();  // Thực hiện truy vấn

            // Duyệt qua kết quả và thêm vào danh sách
            while (rs.next()) {
                Airport airport = new Airport();
                airport.setMaSanBay(rs.getString("MaSanBay"));
                airport.setMatKhauSanBay(rs.getString("MatKhauSanBay"));
                airport.setTenSanBay(rs.getString("TenSanbay"));
                airport.setQuocGia(rs.getString("QuocGia"));
                airports.add(airport);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return airports;
    }
    // Lấy thông tin Airport theo ID

    public static Airport getAirportById(String id) {
        Airport airport = null;
        String query = "SELECT * FROM sanbay WHERE MaSanBay = ?";
        try (Connection conn = DBconnect.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                airport = new Airport();
                airport.setMaSanBay(rs.getString("MaSanBay"));
                airport.setMatKhauSanBay(rs.getString("MatKhauSanBay"));
                airport.setTenSanBay(rs.getString("TenSanbay"));
                airport.setQuocGia(rs.getString("QuocGia"));;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return airport;
    }

    // Hàm thêm Khách hàng vào cơ sở dữ liệu
    public boolean createAirport(String maSanbay, String matKhauSanBay, String tenSanbay, String quocGia) {

        try (Connection conn = DBconnect.getConnection()) {
            String sql = "INSERT INTO sanbay (MaSanBay, MatKhauSanBay, TenSanbay, QuocGia) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, maSanbay);
            stmt.setString(2, matKhauSanBay);
            stmt.setString(3, tenSanbay);
            stmt.setString(4, quocGia);
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

    // Cập nhật thông tin Airport
    public boolean updateAirport(String maSanbay, String matKhauSanBay, String tenSanbay, String quocGia) {

        try (Connection conn = DBconnect.getConnection()) {
            String sql = "UPDATE sanbay SET MatKhauSanBay = ?, TenSanbay = ?, QuocGia = ? WHERE MaSanBay = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, matKhauSanBay);
            stmt.setString(2, tenSanbay);
            stmt.setString(3, quocGia);
            stmt.setString(4, maSanbay);
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
    public List<Airport> updateAndGetAirportsFromSession(String masanbay, String matkhausanbay, String tensanbay, String quocsgia, List<Airport> sessionAirports) throws SQLException {
        if (sessionAirports != null) {
            for (Airport airport : sessionAirports) {
                if (airport.getMaSanBay().equals(masanbay)) {
                    airport.setMatKhauSanBay(matkhausanbay);
                    airport.setTenSanBay(tensanbay);
                    airport.setQuocGia(quocsgia);
                    break;
                }
            }
        } else {
            sessionAirports = getAllAirports();
        }
        return sessionAirports;
    }
    // Phương thức xóa một hãng hàng không

    public boolean deleteAirport(String maSanbay) {
        boolean isDeleted = false;
        try (Connection conn = DBconnect.getConnection()) {
            String sql = "DELETE FROM sanbay WHERE MaSanBay = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, maSanbay);

            int rowsAffected = stmt.executeUpdate();
            isDeleted = rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isDeleted;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.flywithme.dao;

import com.flywithme.model.DBconnect;
import com.flywithme.model.Flight;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;
import java.time.Duration;
import javax.servlet.http.HttpSession;

/**
 *
 * @author pc
 */
public class FlightDAO {

    public List<Flight> getAllFlights(String maHang) throws SQLException {
        List<Flight> flights = new ArrayList<>();

        try (Connection conn = DBconnect.getConnection()) {
            String sql = "SELECT cb.MaChuyenBay, cb.Status, cb.TinhTrangChuyenBay, cb.MaHang, "
                    + "       sb1.TenSanBay AS TenNoiCatCanh, "
                    + "       sb2.TenSanBay AS TenNoiHaCanh, "
                    + "       cb.ThoiGianCatCanhDanhNghia, cb.ThoiGianHaCanhDanhNghia, "
                    + "       cb.GiaTien "
                    + "FROM chuyenbay cb "
                    + "JOIN sanbay sb1 ON cb.NoiCatCanh = sb1.MaSanBay "
                    + "JOIN sanbay sb2 ON cb.NoiHaCanh = sb2.MaSanBay "
                    + "WHERE MaHang = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, maHang); // Bổ sung giá trị maHang vào query
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    Flight flight = new Flight();
                    flight.setMaChuyenBay(rs.getString("MaChuyenBay"));
                    flight.setTenSanBayCatCanh(rs.getString("TenNoiCatCanh"));
                    flight.setTenSanBayHaCanh(rs.getString("TenNoiHaCanh"));
                    flight.setThoiGianCatCanhDanhNghia(rs.getTimestamp("ThoiGianCatCanhDanhNghia"));
                    flight.setThoiGianHaCanhDanhNghia(rs.getTimestamp("ThoiGianHaCanhDanhNghia"));
                    flight.setGiaTien(rs.getDouble("GiaTien"));
                    flight.setStatus(rs.getString("Status"));
                    flight.setTinhTrangChuyenBay(rs.getString("TinhTrangChuyenBay"));
                    flights.add(flight);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return flights;
    }

    public List<Flight> getAllFlightsWithoutAirlineCode(String maSanBay) throws SQLException {
        List<Flight> flights = new ArrayList<>();

        try (Connection conn = DBconnect.getConnection()) {
            String sql = "SELECT cb.MaChuyenBay, cb.Status, cb.TinhTrangChuyenBay, cb.MaHang, "
                    + "       sb1.TenSanBay AS TenNoiCatCanh, "
                    + "       sb2.TenSanBay AS TenNoiHaCanh, "
                    + "       cb.ThoiGianCatCanhDanhNghia, cb.ThoiGianHaCanhDanhNghia, "
                    + "       cb.GiaTien "
                    + "FROM chuyenbay cb "
                    + "JOIN sanbay sb1 ON cb.NoiCatCanh = sb1.MaSanBay "
                    + "JOIN sanbay sb2 ON cb.NoiHaCanh = sb2.MaSanBay "
                    + "WHERE NoicatCanh = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, maSanBay); // Bổ sung giá trị maHang vào query

                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    Flight flight = new Flight();
                    flight.setMaHang(rs.getString("MaHang"));
                    flight.setMaChuyenBay(rs.getString("MaChuyenBay"));
                    flight.setTenSanBayCatCanh(rs.getString("TenNoiCatCanh"));
                    flight.setTenSanBayHaCanh(rs.getString("TenNoiHaCanh"));
                    flight.setThoiGianCatCanhDanhNghia(rs.getTimestamp("ThoiGianCatCanhDanhNghia"));
                    flight.setThoiGianHaCanhDanhNghia(rs.getTimestamp("ThoiGianHaCanhDanhNghia"));
                    flight.setGiaTien(rs.getDouble("GiaTien"));
                    flight.setStatus(rs.getString("Status"));
                    flight.setTinhTrangChuyenBay(rs.getString("TinhTrangChuyenBay"));
                    flights.add(flight);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return flights;
    }

    public boolean createFlight(String maChuyenBay, String maHang, String noiCatCanh, String noiHaCanh, Timestamp thoiGianCatCanhDanhNghia, Timestamp thoiGianHaCanhDanhNghia, String status) throws SQLException {
        String sql = "INSERT INTO chuyenbay (MaChuyenBay, MaHang , NoiCatCanh , NoiHaCanh , ThoiGianCatCanhDanhNghia, ThoiGianHaCanhDanhNghia,GiaTien, TinhTrangChuyenBay, Status) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBconnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (thoiGianCatCanhDanhNghia == null || thoiGianHaCanhDanhNghia == null) {
                throw new IllegalArgumentException("Thời gian cất cánh và hạ cánh không được để trống.");
            }
            long durationInMinutes = Duration.between(thoiGianCatCanhDanhNghia.toLocalDateTime(), thoiGianHaCanhDanhNghia.toLocalDateTime()).toMinutes();
            double price = getPriceForDuration(durationInMinutes, conn);

            stmt.setString(1, maChuyenBay);
            stmt.setString(2, maHang);
            stmt.setString(3, noiCatCanh);
            stmt.setString(4, noiHaCanh);
            stmt.setTimestamp(5, thoiGianCatCanhDanhNghia);
            stmt.setTimestamp(6, thoiGianHaCanhDanhNghia);
            stmt.setDouble(7, price);

            stmt.setString(8, "No Authorized");
            stmt.setString(9, status);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                return true;  // Thành công
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error executing createFlight: " + e.getMessage(), e);
        }

        return false;
    }
    // Lấy thông tin Flight theo ID

    public static Flight getFlightById(String id) {
        Flight flight = null;
        String query = "SELECT cb.MaChuyenBay, cb.Status, cb.TinhTrangChuyenBay,cb.MaHang, cb.GiaTien, "
                + "sb1.TenSanBay AS TenNoiCatCanh, sb2.TenSanBay AS TenNoiHaCanh, "
                + "cb.ThoiGianCatCanhDanhNghia, cb.ThoiGianHaCanhDanhNghia "
                + "FROM chuyenbay cb "
                + "JOIN sanbay sb1 ON cb.NoiCatCanh = sb1.MaSanBay "
                + "JOIN sanbay sb2 ON cb.NoiHaCanh = sb2.MaSanBay "
                + "WHERE cb.MaChuyenBay = ?";
        try (Connection conn = DBconnect.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                flight = new Flight();
                flight.setMaChuyenBay(rs.getString("MaChuyenBay"));
                flight.setMaHang(rs.getString("MaHang"));
                flight.setTenSanBayCatCanh(rs.getString("TenNoiCatCanh"));
                flight.setTenSanBayHaCanh(rs.getString("TenNoiHaCanh"));
                flight.setThoiGianCatCanhDanhNghia(rs.getTimestamp("ThoiGianCatCanhDanhNghia"));
                flight.setThoiGianHaCanhDanhNghia(rs.getTimestamp("ThoiGianHaCanhDanhNghia"));
                flight.setTinhTrangChuyenBay(rs.getString("TinhTrangChuyenBay"));
                flight.setStatus(rs.getString("Status"));
                flight.setGiaTien(rs.getDouble("GiaTien"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flight;
    }

    public double getFlightPrice(String maChuyenBay) throws SQLException {
        // Truy vấn giá chuyến bay từ bảng chuyenbay
        String query = "SELECT GiaTien FROM chuyenbay WHERE MaChuyenBay = ?";
        try (Connection conn = DBconnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, maChuyenBay);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("GiaTien");
            } else {
                throw new SQLException("Flight code not found: " + maChuyenBay);
            }
        }
    }
    // Cập nhật thông tin Flight

    public boolean updateFlight(String maChuyenBay, String noiCatCanh, String noiHaCanh, Timestamp thoiGianCatCanhDanhNghia, Timestamp thoiGianHaCanhDanhNghia, String status) {

        try (Connection conn = DBconnect.getConnection()) {
            String sql = "UPDATE chuyenbay SET NoiCatCanh = ?, NoiHaCanh = ?, ThoiGianCatCanhDanhNghia = ?, ThoiGianHaCanhDanhNghia = ?, Status = ?, GiaTien = ? WHERE MaChuyenBay = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            long durationInMinutes = Duration.between(thoiGianCatCanhDanhNghia.toLocalDateTime(), thoiGianHaCanhDanhNghia.toLocalDateTime()).toMinutes();
            double price = getPriceForDuration(durationInMinutes, conn);

            stmt.setString(1, noiCatCanh);
            stmt.setString(2, noiHaCanh);
            stmt.setTimestamp(3, thoiGianCatCanhDanhNghia);
            stmt.setTimestamp(4, thoiGianHaCanhDanhNghia);
            stmt.setString(5, status);
            stmt.setDouble(6, price);

            stmt.setString(7, maChuyenBay);
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

    public List<Flight> updateAndGetCustomersFromSession(String maChuyenBay, String noiCatCanh, String noiHaCanh, Timestamp thoiGianCatCanhDanhNghia, Timestamp thoiGianHaCanhDanhNghia, String status, HttpSession session) throws SQLException {
        // Lấy mã hãng hàng không đã đăng nhập từ session
        String maHang = (session != null && session.getAttribute("maHang") != null)
                ? (String) session.getAttribute("maHang") : "";
        List<Flight> sessionFlight = (List<Flight>) session.getAttribute("flights");
        if (sessionFlight != null) {
            for (Flight flight : sessionFlight) {
                if (flight.getMaChuyenBay().equals(maChuyenBay)) {
                    flight.setTenSanBayCatCanh(noiCatCanh);
                    flight.setTenSanBayHaCanh(noiHaCanh);
                    flight.setThoiGianCatCanhDanhNghia(thoiGianCatCanhDanhNghia);
                    flight.setThoiGianHaCanhDanhNghia(thoiGianHaCanhDanhNghia);
                    flight.setStatus(status);
                    break;
                }
            }
        } else {
            sessionFlight = getAllFlights(maHang);
        }
        return sessionFlight;
    }

    //phương thức tính giá dựa trên thời lượng chuyến bay
    public static void updateFlightPrice(Flight flight) {
        String priceQuery = "SELECT GiaTien FROM pricing WHERE ? BETWEEN min_duration AND max_duration";
        String updateQuery = "UPDATE chuyenbay SET GiaTien = ? WHERE MaChuyenBay = ?";

        try (Connection conn = DBconnect.getConnection(); PreparedStatement pricePs = conn.prepareStatement(priceQuery)) {

            // Lấy duration của chuyến bay (phút)
            long duration = flight.getDurationInMinutes();

            if (duration > 0) {
                pricePs.setLong(1, duration);
                ResultSet priceRs = pricePs.executeQuery();

                if (priceRs.next()) {
                    double price = priceRs.getDouble("GiaTien");

                    // Cập nhật giá vào bảng chuyenbay
                    try (PreparedStatement updatePs = conn.prepareStatement(updateQuery)) {
                        updatePs.setDouble(1, price);
                        updatePs.setString(2, flight.getMaChuyenBay());
                        updatePs.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Phương thức xóa một chuyến bay

    public boolean deleteFlight(String maChuyenBay) {
        boolean isDeleted = false;
        try (Connection conn = DBconnect.getConnection()) {
            String sql = "DELETE FROM chuyenbay WHERE MaChuyenbay = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, maChuyenBay);

            int rowsAffected = stmt.executeUpdate();
            isDeleted = rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isDeleted;
    }

    private double getPriceForDuration(long durationInMinutes, Connection conn) throws SQLException {
        String query = "SELECT GiaTien FROM pricing WHERE ? >= min_duration AND (max_duration = -1 OR ? <= max_duration)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setLong(1, durationInMinutes);
            ps.setLong(2, durationInMinutes);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble("GiaTien");
            }
        }
        // Nếu không tìm thấy giá phù hợp, bạn có thể trả về giá mặc định hoặc báo lỗi
        throw new SQLException("Không tìm thấy giá phù hợp cho thời lượng chuyến bay: " + durationInMinutes);
    }

    public boolean updateFlightStatus(String maChuyenBay, String newStatus) throws SQLException {
        String updateStatusSql = "UPDATE chuyenbay SET TinhTrangChuyenBay = ? WHERE MaChuyenBay = ?";
        try (Connection conn = DBconnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(updateStatusSql)) {

            stmt.setString(1, newStatus);
            stmt.setString(2, maChuyenBay);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0; // Trả về true nếu có hàng được cập nhật
        }
    }

    public List<Flight> searchFlights(String noiCatCanh, String noiHaCanh, String thoiGianCatCanh, String thoiGianHaCanh, String maChuyenBay) throws SQLException {
        List<Flight> flights = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT cb.MaChuyenBay, sb1.TenSanBay AS TenSanBayCatCanh, sb2.TenSanBay AS TenSanBayHaCanh, "
                + "cb.ThoiGianCatCanhDanhNghia, cb.ThoiGianHaCanhDanhNghia, cb.GiaTien, cb.Status "
                + "FROM chuyenbay cb "
                + "JOIN sanbay sb1 ON cb.NoiCatCanh = sb1.MaSanBay "
                + "JOIN sanbay sb2 ON cb.NoiHaCanh = sb2.MaSanBay "
                + "WHERE cb.TinhTrangChuyenBay = 'Authorized' "
        );

        if (noiCatCanh != null && !noiCatCanh.isEmpty()) {
            sql.append(" AND sb1.TenSanBay LIKE ?");
        }
        if (noiHaCanh != null && !noiHaCanh.isEmpty()) {
            sql.append(" AND sb2.TenSanBay LIKE ?");
        }
        if (thoiGianCatCanh != null && !thoiGianCatCanh.isEmpty()) {
            sql.append(" AND ThoiGianCatCanhDanhNghia >= ?");
        }
        if (thoiGianHaCanh != null && !thoiGianHaCanh.isEmpty()) {
            sql.append(" AND ThoiGianHaCanhDanhNghia <= ?");
        }
        if (maChuyenBay != null && !maChuyenBay.isEmpty()) {
            sql.append(" AND MaChuyenBay LIKE ?");
        }

        try (Connection conn = DBconnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            int index = 1;
            if (noiCatCanh != null && !noiCatCanh.isEmpty()) {
                stmt.setString(index++, "%" + noiCatCanh + "%");
            }
            if (noiHaCanh != null && !noiHaCanh.isEmpty()) {
                stmt.setString(index++, "%" + noiHaCanh + "%");
            }
            if (thoiGianCatCanh != null && !thoiGianCatCanh.isEmpty()) {
                stmt.setString(index++, thoiGianCatCanh);
            }
            if (thoiGianHaCanh != null && !thoiGianHaCanh.isEmpty()) {
                stmt.setString(index++, thoiGianHaCanh);
            }
            if (maChuyenBay != null && !maChuyenBay.isEmpty()) {
                stmt.setString(index++, "%" + maChuyenBay + "%");
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Flight flight = new Flight();
                    flight.setMaChuyenBay(rs.getString("MaChuyenBay"));
                    flight.setTenSanBayCatCanh(rs.getString("TenSanBayCatCanh"));
                    flight.setTenSanBayHaCanh(rs.getString("TenSanBayHaCanh"));
//                    flight.setTenSanBayCatCanh(rs.getString("NoiCatCanh"));
//                    flight.setTenSanBayHaCanh(rs.getString("NoiHaCanh"));
                    flight.setThoiGianCatCanhDanhNghia(rs.getTimestamp("ThoiGianCatCanhDanhNghia"));
                    flight.setThoiGianHaCanhDanhNghia(rs.getTimestamp("ThoiGianHaCanhDanhNghia"));
                    flight.setGiaTien(rs.getDouble("GiaTien"));
                    flight.setStatus(rs.getString("Status"));

                    flights.add(flight);
                }
            }
        }
        return flights;
    }

}

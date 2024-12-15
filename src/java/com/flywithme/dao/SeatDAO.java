package com.flywithme.dao;

import com.flywithme.model.DBconnect;
import com.flywithme.model.Seat;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;

public class SeatDAO {

    public List<Seat> getSeatsForFlight(String maChuyenBay) throws SQLException {
        List<Seat> seats = new ArrayList<>();
        String sql = "SELECT gm.MaGhe, gm.GiaGhe, gm.HangGhe, "
                + " CASE "
                + "        WHEN d.MaDatCho IS NOT NULL THEN 'Booked' "
                + "        ELSE COALESCE(ttg.TinhTrangGhe, 'Available') "
                + " END AS TinhTrang, "
                + "d.MaDatCho AS BookingId, d.SoDinhDanh AS Passenger, d.SoTienDaThanhToan "
                + "FROM ghengoi gm "
                + "LEFT JOIN tinhtrangghe ttg ON gm.MaGhe = ttg.MaGhe AND ttg.MaChuyenBay = ? "
                + "LEFT JOIN datcho d ON gm.MaGhe = d.MaGhe AND d.MaChuyenBay = ? "
                + "ORDER BY gm.HangGhe, gm.MaGhe";
        try (Connection conn = DBconnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maChuyenBay);
            stmt.setString(2, maChuyenBay);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Seat seat = new Seat();
                seat.setMaGhe(rs.getString("MaGhe"));
                seat.setTinhTrang(rs.getString("TinhTrang"));
                seat.setKhachHang(rs.getString("Passenger"));
                seat.setMaDatCho(rs.getString("BookingId"));
                seat.setGiaGhe(rs.getDouble("GiaGhe"));
                seat.setHangGhe(rs.getString("HangGhe"));
                seat.setSoTienDaThanhToan(rs.getDouble("SoTienDaThanhToan"));

                seats.add(seat);
            }
        }
        return seats;
    }

    // Lấy thông tin tình trạng ghế theo MaGhe
    public static Seat getSeatStatusByMaGhe(String maGhe, String maChuyenBay) throws SQLException {
        Seat seat = null;

        // Câu lệnh SQL lấy tình trạng ghế từ bảng tinhtrangghe
        String sql = "SELECT gm.MaGhe, "
                + "COALESCE(ttg.TinhTrangGhe, 'available') AS TinhTrang "
                + "FROM ghengoi gm "
                + "LEFT JOIN tinhtrangghe ttg ON gm.MaGhe = ttg.MaGhe AND ttg.MaChuyenBay = ? "
                + "WHERE gm.MaGhe = ?";

        try (Connection conn = DBconnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maChuyenBay);  // Gán giá trị cho tham số MaGhe
            stmt.setString(2, maGhe);  // Gán giá trị cho tham số MaGhe

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // Khởi tạo đối tượng Seat và gán các giá trị từ kết quả truy vấn
                seat = new Seat();
                seat.setMaGhe(rs.getString("MaGhe"));
                seat.setTinhTrang(rs.getString("TinhTrang"));

                // Thêm đối tượng Seat vào danh sách
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Ghi log nếu có lỗi
            throw new SQLException("Error checking seat existence: " + e.getMessage(), e);
        }

        return seat;
    }

    public void updateSeatStatus(String maGhe, String newStatus) throws SQLException {
        String checkStatusSql = "SELECT TinhTrangGhe FROM tinhtrangghe WHERE MaGhe = ?";
        try (Connection conn = DBconnect.getConnection(); PreparedStatement checkStmt = conn.prepareStatement(checkStatusSql)) {
            checkStmt.setString(1, maGhe);

            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                String currentStatus = rs.getString("TinhTrangGhe");
                // Nếu ghế có trạng thái 'Booked', không thực hiện cập nhật
                if ("Booked".equals(currentStatus)) {
                    return;
                }
            }

            // Nếu không phải 'Booked', thực hiện cập nhật trạng thái
            String updateSql = "UPDATE tinhtrangghe SET TinhTrangGhe = ? WHERE MaGhe = ?";
            try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                updateStmt.setString(1, newStatus);
                updateStmt.setString(2, maGhe);
                updateStmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Ghi log nếu có lỗi
            throw new SQLException("Error checking seat existence: " + e.getMessage(), e);
        }
    }

    public boolean updateSeatStatusWhenCancel(String maGhe, String maChuyenBay, String newStatus) throws SQLException {
        String sql = "UPDATE tinhtrangghe SET TinhTrangGhe = ? WHERE MaGhe = ? AND MaChuyenBay = ?";
        try (Connection conn = DBconnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newStatus);
            stmt.setString(2, maGhe);
            stmt.setString(3, maChuyenBay);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error checking seat existence: " + e.getMessage(), e);
        }
    }

    public boolean isSeatExist(String maGhe, String maChuyenBay) throws SQLException {
        String sql = "SELECT COUNT(*) FROM tinhtrangghe WHERE MaGhe = ? AND MaChuyenBay = ?";
        try (Connection conn = DBconnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maGhe);
            stmt.setString(2, maChuyenBay);

            try (ResultSet rs = stmt.executeQuery()) { // Đảm bảo ResultSet được đóng
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Ghi log nếu có lỗi
            throw new SQLException("Error checking seat existence: " + e.getMessage(), e);
        }
        return false;
    }

    public void addSeatToStatusTable(String maChuyenbay, String maGhe, String status) throws SQLException {
        String checkStatusSql = "SELECT TinhTrangGhe FROM tinhtrangghe WHERE MaGhe = ? AND MaChuyenBay = ?";
        try (Connection conn = DBconnect.getConnection(); PreparedStatement checkStmt = conn.prepareStatement(checkStatusSql)) {
            checkStmt.setString(1, maGhe);
            checkStmt.setString(2, maChuyenbay);

            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                String currentStatus = rs.getString("TinhTrangGhe");
                
                // Nếu trạng thái là 'Booked', không thực hiện thay đổi
                if ("Booked".equals(currentStatus)) {
                    return;
                }
            }

            // Nếu ghế chưa có trạng thái hoặc không phải 'Booked', thực hiện thêm
            String sql = "INSERT INTO tinhtrangghe (MaGhe, TinhTrangGhe, MaChuyenBay) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, maGhe);
                stmt.setString(2, status);
                stmt.setString(3, maChuyenbay);
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace(); // Ghi log nếu có lỗi
                throw new SQLException("Error checking seat existence: " + e.getMessage(), e);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Ghi log nếu có lỗi
            throw new SQLException("Error checking seat existence: " + e.getMessage(), e);
        }
    }

    public boolean isSeatBooked(String maGhe, String maChuyenBay) throws SQLException {
        String checkStatusSql = "SELECT TinhTrangGhe FROM tinhtrangghe WHERE MaGhe = ? AND MaChuyenBay = ?";
        try (Connection conn = DBconnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(checkStatusSql)) {
            stmt.setString(1, maGhe);
            stmt.setString(2, maChuyenBay);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String currentStatus = rs.getString("TinhTrangGhe");
                    return "Booked".equalsIgnoreCase(currentStatus); // So khớp không phân biệt hoa/thường
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Ghi log nếu có lỗi
            throw new SQLException("Error checking if seat is booked: " + e.getMessage(), e);
        }
        return false;
    }

}

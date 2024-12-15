package com.flywithme.dao;

import com.flywithme.model.Booking;
import com.flywithme.model.BookingDetails;
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
public class BookingDAO {

    public List<Booking> getAllBookings(String maHang) throws SQLException {

        List<Booking> bookings = new ArrayList<>();
        try (Connection conn = DBconnect.getConnection()) {
            String sql = "SELECT d.MaDatCho, d.MaChuyenBay, d.MaGhe, d.SoDinhDanh, d.SoTienDaThanhToan, "
                    + "CONCAT(k.HoDem, ' ', k.Ten) AS TenKhachHang "
                    + "FROM datcho d "
                    + "JOIN khachhang k ON d.SoDinhDanh = k.SoDinhDanh "
                    + "JOIN chuyenbay c ON d.MaChuyenBay = c.MaChuyenBay " // Thêm JOIN với bảng chuyenbay
                    + "WHERE c.MaHang = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, maHang);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Booking booking = new Booking();
                booking.setMaDatCho(rs.getString("MaDatCho"));
                booking.setSoDinhDanh(rs.getString("SoDinhDanh"));
                booking.setMaChuyenBay(rs.getString("MaChuyenBay"));
                booking.setMaGhe(rs.getString("MaGhe"));
                booking.setSoTienDaThanhToan(rs.getDouble("SoTienDaThanhToan"));
                booking.setTenKhachHang(rs.getString("TenKhachHang"));
                bookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // In ra lỗi chi tiết
            throw e;
        }
        return bookings;
    }

    public static Booking getBookingById(String id) {
        Booking booking = null;
        String query = "SELECT * FROM datcho WHERE MaDatCho = ?";
        try (Connection conn = DBconnect.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                booking = new Booking();
                booking.setMaDatCho(rs.getString("MaDatCho"));
                booking.setSoDinhDanh(rs.getString("SoDinhDanh"));
                booking.setMaChuyenBay(rs.getString("MaChuyenBay"));
                booking.setMaGhe(rs.getString("MaGhe"));
                booking.setSoTienDaThanhToan(rs.getDouble("SoTienDaThanhToan"));
                booking.setTenKhachHang(rs.getString("TenKhachHang"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return booking;
    }

    public List<BookingDetails> getBookingDetailsByCus(String id) throws SQLException {
        List<BookingDetails> details = new ArrayList<>();
        try (Connection conn = DBconnect.getConnection()) {
            String sql = "SELECT b.MaDatCho, b.MaGhe, b.SoTienDaThanhToan, "
                    + "c.MaChuyenBay, c.ThoiGianCatCanhDanhNghia, c.ThoiGianHaCanhDanhNghia, c.TinhTrangChuyenBay, c.Status, "
                    + "sbDi.TenSanBay AS SanBayDi, sbDen.TenSanBay AS SanBayDen,sbDi.QuocGia AS QuocGiaDi,sbDen.QuocGia AS QuocGiaDen, "
                    + "k.HoDem, k.Ten, k.SoDinhDanh, k.Email, k.NgayThangNamSinh "
                    + "FROM datcho b "
                    + "JOIN chuyenbay c ON b.MaChuyenBay = c.MaChuyenBay "
                    + "JOIN sanbay sbDi ON c.NoiCatCanh = sbDi.MaSanBay "
                    + "JOIN sanbay sbDen ON c.NoiHaCanh = sbDen.MaSanBay "
                    + "JOIN khachhang k ON b.SoDinhDanh = k.SoDinhDanh "
                                        + "WHERE b.SoDinhDanh = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, id);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                BookingDetails detail = new BookingDetails();
                detail.setMaDatCho(rs.getString("MaDatCho"));
                detail.setMaGhe(rs.getString("MaGhe"));
                detail.setSoTienDaThanhToan(rs.getDouble("SoTienDaThanhToan"));

                detail.setMaChuyenBay(rs.getString("MaChuyenBay"));
                detail.setThoiGianKhoiHanh(rs.getTimestamp("ThoiGianCatCanhDanhNghia"));
                detail.setThoiGianHaCanh(rs.getTimestamp("ThoiGianHaCanhDanhNghia"));
                detail.setTinhTrangChuyenBay(rs.getString("TinhTrangChuyenBay"));
                detail.setStatus(rs.getString("Status"));

                detail.setSanBayDi(rs.getString("SanBayDi"));
                detail.setQuocGiaDi(rs.getString("QuocGiaDi"));
                detail.setSanBayDen(rs.getString("SanBayDen"));
                detail.setQuocGiaDen(rs.getString("QuocGiaDen"));

                detail.setHoTenKhachHang(rs.getString("HoDem") + " " + rs.getString("Ten"));
                detail.setSoDinhDanh(rs.getString("SoDinhDanh"));
                detail.setEmail(rs.getString("Email"));
                detail.setNgayThangNamSinh(rs.getString("NgayThangNamSinh"));

                details.add(detail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return details;
    }
    public List<BookingDetails> getBookingDetails(String id) throws SQLException {
        List<BookingDetails> details = new ArrayList<>();
        try (Connection conn = DBconnect.getConnection()) {
            String sql = "SELECT b.MaDatCho, b.MaGhe, b.SoTienDaThanhToan, "
                    + "c.MaChuyenBay, c.ThoiGianCatCanhDanhNghia, c.ThoiGianHaCanhDanhNghia, c.TinhTrangChuyenBay, c.Status, "
                    + "sbDi.TenSanBay AS SanBayDi, sbDen.TenSanBay AS SanBayDen,sbDi.QuocGia AS QuocGiaDi,sbDen.QuocGia AS QuocGiaDen, "
                    + "k.HoDem, k.Ten, k.SoDinhDanh, k.Email, k.NgayThangNamSinh "
                    + "FROM datcho b "
                    + "JOIN chuyenbay c ON b.MaChuyenBay = c.MaChuyenBay "
                    + "JOIN sanbay sbDi ON c.NoiCatCanh = sbDi.MaSanBay "
                    + "JOIN sanbay sbDen ON c.NoiHaCanh = sbDen.MaSanBay "
                    + "JOIN khachhang k ON b.SoDinhDanh = k.SoDinhDanh "
                    + "WHERE b.MaDatCho = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, id);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                BookingDetails detail = new BookingDetails();
                detail.setMaDatCho(rs.getString("MaDatCho"));
                detail.setMaGhe(rs.getString("MaGhe"));
                detail.setSoTienDaThanhToan(rs.getDouble("SoTienDaThanhToan"));

                detail.setMaChuyenBay(rs.getString("MaChuyenBay"));
                detail.setThoiGianKhoiHanh(rs.getTimestamp("ThoiGianCatCanhDanhNghia"));
                detail.setThoiGianHaCanh(rs.getTimestamp("ThoiGianHaCanhDanhNghia"));
                detail.setTinhTrangChuyenBay(rs.getString("TinhTrangChuyenBay"));
                detail.setStatus(rs.getString("Status"));

                detail.setSanBayDi(rs.getString("SanBayDi"));
                detail.setQuocGiaDi(rs.getString("QuocGiaDi"));
                detail.setSanBayDen(rs.getString("SanBayDen"));
                detail.setQuocGiaDen(rs.getString("QuocGiaDen"));

                detail.setHoTenKhachHang(rs.getString("HoDem") + " " + rs.getString("Ten"));
                detail.setSoDinhDanh(rs.getString("SoDinhDanh"));
                detail.setEmail(rs.getString("Email"));
                detail.setNgayThangNamSinh(rs.getString("NgayThangNamSinh"));

                details.add(detail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return details;
    }

    public void addBookingRecord(Booking booking) throws SQLException {
        String sql = "INSERT INTO datcho (MaDatCho, MaChuyenBay, MaGhe, SoDinhDanh, SoTienDaThanhToan) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBconnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, booking.getMaDatCho());  
            stmt.setString(2, booking.getMaChuyenBay());   
            stmt.setString(3, booking.getMaGhe());      
            stmt.setString(4, booking.getSoDinhDanh());       
            stmt.setDouble(5, booking.getSoTienDaThanhToan());   

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); 
            throw e;  
        }
    }
    public boolean cancelBooking(String maDatCho) throws SQLException {
    String sql = "DELETE FROM datcho WHERE MaDatCho = ?";
    try (Connection conn = DBconnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, maDatCho);
        return stmt.executeUpdate() > 0;
    }
}

}

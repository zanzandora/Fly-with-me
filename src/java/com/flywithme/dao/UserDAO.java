/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.flywithme.dao;

import com.flywithme.model.User;
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
public class UserDAO {

    public User login(String email, String password) throws SQLException {
        String sql = "SELECT SoDinhDanh, HoDem, Ten, Email, NgayThanhNamSinh FROM khachhang WHERE Email = ? AND MatKhauKhach = ?";
        User user = null;

        try (Connection conn = DBconnect.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String soDinhDanh = rs.getString("SoDinhDanh");
                    String hoDem = rs.getString("HoDem");
                    String ten = rs.getString("Ten");
                    String hoTen = hoDem + " " + ten; // Ghép họ và tên
                    String ngaySinh = rs.getString("NgayThanhNamSinh");

                    // Tạo đối tượng User với thông tin đầy đủ
                    user = new User(soDinhDanh, hoTen, email, ngaySinh);
                }
            }
        }
        return user;
    }

}


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.flywithme.model;

/**
 *
 * @author pc
 */
public class Airline {

     private String MaHang;
    private String TenHang;
    private String PasswordHangHangKhong	;
    private String Country;
    private String Avatar;
    
    public Airline(String MaHang, String TenHang, String PasswordHangHangKhong, String Country, String Avatar) {
        this.MaHang = MaHang;
        this.TenHang = TenHang;
        this.PasswordHangHangKhong = PasswordHangHangKhong;
        this.Country = Country;
        this.Avatar = Avatar;
    }

    public Airline() {
         
    }

    public String getMaHang() {
        return MaHang;
    }

    public void setMaHang(String MaHang) {
        this.MaHang = MaHang;
    }

    public String getTenHang() {
        return TenHang;
    }

    public void setTenHang(String TenHang) {
        this.TenHang = TenHang;
    }

    public String getPasswordHangHangKhong() {
        return PasswordHangHangKhong;
    }

    public void setPasswordHangHangKhong(String PasswordHangHangKhong) {
        this.PasswordHangHangKhong = PasswordHangHangKhong;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String Country) {
        this.Country = Country;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String Avatar) {
        this.Avatar = Avatar;
    }
    

    
}

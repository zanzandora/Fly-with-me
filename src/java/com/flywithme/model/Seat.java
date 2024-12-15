/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.flywithme.model;

/**
 *
 * @author pc
 */
public class Seat {
    private String maGhe;
    private String tinhTrang;
    private double giaGhe;       
    private String soDinhDanh;
    private double soTienDaThanhToan;
    private String khachHang;
    private String maDatCho;
    private String hangGhe;  

    public Seat(String maGhe, String tinhTrang, double giaGhe, String soDinhDanh, String khachHang, String maDatCho, String hangGhe) {
        this.maGhe = maGhe;
        this.tinhTrang = tinhTrang;
        this.giaGhe = giaGhe;
        this.soDinhDanh = soDinhDanh;
        this.khachHang = khachHang;
        this.maDatCho = maDatCho;
        this.hangGhe = hangGhe;
    }

  

    public Seat() {
       
    }

    public String getMaGhe() {
        return maGhe;
    }

    public void setMaGhe(String maGhe) {
        this.maGhe = maGhe;
    }



    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public double getGiaGhe() {
        return giaGhe;
    }

    public void setGiaGhe(double giaGhe) {
        this.giaGhe = giaGhe;
    }

    public String getHangGhe() {
        return hangGhe;
    }

    public void setHangGhe(String hangGhe) {
        this.hangGhe = hangGhe;
    }

    public String getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(String khachHang) {
        this.khachHang = khachHang;
    }

    public String getMaDatCho() {
        return maDatCho;
    }

    public void setMaDatCho(String maDatCho) {
        this.maDatCho = maDatCho;
    }

    public String getSoDinhDanh() {
        return soDinhDanh;
    }

    public void setSoDinhDanh(String soDinhDanh) {
        this.soDinhDanh = soDinhDanh;
    }

    public double getSoTienDaThanhToan() {
        return soTienDaThanhToan;
    }

    public void setSoTienDaThanhToan(double soTienDaThanhToan) {
        this.soTienDaThanhToan = soTienDaThanhToan;
    }
    
    
}

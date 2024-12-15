/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.flywithme.model;

/**
 *
 * @author pc
 */
public class Booking {
    private String maDatCho;
    private String maChuyenBay;
    private String maGhe;
    private String soDinhDanh;
    private double soTienDaThanhToan;
    private String tenKhachHang; 

    public Booking(){}
    
    public Booking(String maDatCho, String maChuyenBay, String maGhe, String soDinhDanh, double soTienDaThanhToan) {
        this.maDatCho = maDatCho;
        this.maChuyenBay = maChuyenBay;
        this.maGhe = maGhe;
        this.soDinhDanh = soDinhDanh;
        this.soTienDaThanhToan = soTienDaThanhToan;
    }

    public String getMaDatCho() {
        return maDatCho;
    }

    public void setMaDatCho(String maDatCho) {
        this.maDatCho = maDatCho;
    }

    public String getMaChuyenBay() {
        return maChuyenBay;
    }

    public void setMaChuyenBay(String maChuyenBay) {
        this.maChuyenBay = maChuyenBay;
    }

    public String getMaGhe() {
        return maGhe;
    }

    public void setMaGhe(String maGhe) {
        this.maGhe = maGhe;
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

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }
    
}

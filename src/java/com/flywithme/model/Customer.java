/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.flywithme.model;

/**
 *
 * @author pc
 */
public class Customer {
    
    private String SoDinhDanh;
    private String HoDem;
    private String Ten;    
    private String Email;
    private String MatKhauKhach;
    private String NgayThangNamSinh;

    public Customer(String SoDinhDanh, String HoDem, String Ten, String Email, String MatKhauKhach, String NgayThangNamSinh) {
        this.SoDinhDanh = SoDinhDanh;
        this.HoDem = HoDem;
        this.Ten = Ten;
        this.Email = Email;
        this.MatKhauKhach = MatKhauKhach;
        this.NgayThangNamSinh = NgayThangNamSinh;
    }

    public Customer() {
        
    }

    public String getSoDinhDanh() {
        return SoDinhDanh;
    }

    public void setSoDinhDanh(String SoDinhDanh) {
        this.SoDinhDanh = SoDinhDanh;
    }

    public String getHoDem() {
        return HoDem;
    }

    public void setHoDem(String HoDem) {
        this.HoDem = HoDem;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String Ten) {
        this.Ten = Ten;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getMatKhauKhach() {
        return MatKhauKhach;
    }

    public void setMatKhauKhach(String MatKhauKhach) {
        this.MatKhauKhach = MatKhauKhach;
    }

    public String getNgayThangNamSinh() {
        return NgayThangNamSinh;
    }

    public void setNgayThangNamSinh(String NgayThangNamSinh) {
        this.NgayThangNamSinh = NgayThangNamSinh;
    }
    

}

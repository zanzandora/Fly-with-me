/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.flywithme.model;

/**
 *
 * @author pc
 */
public class Airport {

    private String MaSanBay;
    private String MatKhauSanBay;
    private String TenSanBay;
    private String QuocGia;
    
    public Airport(){}
    
    public Airport(String MaSanBay, String MatKhauSanBay, String TenSanBay,String QuocGia) {
        this.MaSanBay = MaSanBay;
        this.MatKhauSanBay = MatKhauSanBay;
        this.TenSanBay = TenSanBay;        
        this.QuocGia = QuocGia;

    }

    public String getMaSanBay() {
        return MaSanBay;
    }

    public void setMaSanBay(String MaSanBay) {
        this.MaSanBay = MaSanBay;
    }

    public String getMatKhauSanBay() {
        return MatKhauSanBay;
    }

    public void setMatKhauSanBay(String MatKhauSanBay) {
        this.MatKhauSanBay = MatKhauSanBay;
    }

    public String getTenSanBay() {
        return TenSanBay;
    }

    public void setTenSanBay(String TenSanBay) {
        this.TenSanBay = TenSanBay;
    }

    public String getQuocGia() {
        return QuocGia;
    }

    public void setQuocGia(String QuocGia) {
        this.QuocGia = QuocGia;
    }
    
   
}

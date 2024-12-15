/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.flywithme.model;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 *
 * @author pc
 */
public class Flight {
    private String MaChuyenBay;
    private String MaHang ;    
    private String TenSanBayCatCanh;  
    private String TenSanBayHaCanh; 
    private Timestamp ThoiGianCatCanhDanhNghia;
    private Timestamp ThoiGianHaCanhDanhNghia;  
    private double GiaTien  ;
    private String TinhTrangChuyenBay;    
    private String Status;


    public Flight(){}
    
    public Flight(String MaChuyenBay, String MaHang,String TenSanBayCatCanh,String TenSanBayHaCanh, Timestamp ThoiGianCatCanhDanhNghia, Timestamp ThoiGianHaCanhDanhNghia,String TinhTrangChuyenBay, String Status) {
        this.MaChuyenBay = MaChuyenBay;
        this.MaHang = MaHang;
        this.TenSanBayCatCanh = TenSanBayCatCanh;
        this.TenSanBayHaCanh = TenSanBayHaCanh;
        this.ThoiGianCatCanhDanhNghia = ThoiGianCatCanhDanhNghia;
        this.ThoiGianHaCanhDanhNghia = ThoiGianHaCanhDanhNghia;        
        this.TinhTrangChuyenBay = TinhTrangChuyenBay;

        this.Status = Status;

    }

    public String getMaChuyenBay() {
        return MaChuyenBay;
    }

    public void setMaChuyenBay(String MaChuyenBay) {
        this.MaChuyenBay = MaChuyenBay;
    }

    public String getMaHang() {
        return MaHang;
    }

    public void setMaHang(String MaHang) {
        this.MaHang = MaHang;
    }
    public Timestamp getThoiGianCatCanhDanhNghia() {
        return ThoiGianCatCanhDanhNghia;
    }

    public void setThoiGianCatCanhDanhNghia(Timestamp ThoiGianCatCanhDanhNghia) {
        this.ThoiGianCatCanhDanhNghia = ThoiGianCatCanhDanhNghia;
    }

    public Timestamp getThoiGianHaCanhDanhNghia() {
        return ThoiGianHaCanhDanhNghia;
    }

    public void setThoiGianHaCanhDanhNghia(Timestamp ThoiGianHaCanhDanhNghia) {
        this.ThoiGianHaCanhDanhNghia = ThoiGianHaCanhDanhNghia;
    }
    public String getTinhTrangChuyenBay() {
        return TinhTrangChuyenBay;
    }

    public void setTinhTrangChuyenBay(String TinhTrangChuyenBay) {
        this.TinhTrangChuyenBay = TinhTrangChuyenBay;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getTenSanBayCatCanh() {
        return TenSanBayCatCanh;
    }

    public void setTenSanBayCatCanh(String TenSanBayCatCanh) {
        this.TenSanBayCatCanh = TenSanBayCatCanh;
    }

    public String getTenSanBayHaCanh() {
        return TenSanBayHaCanh;
    }

    public void setTenSanBayHaCanh(String TenSanBayHaCanh) {
        this.TenSanBayHaCanh = TenSanBayHaCanh;
    }

    public double getGiaTien() {
        return GiaTien;
    }

    public void setGiaTien(double GiaTien) {
        this.GiaTien = GiaTien;
    }
    
    public String getDuration() {
        if (this.ThoiGianCatCanhDanhNghia != null && this.ThoiGianHaCanhDanhNghia != null) {
            LocalDateTime takeoff = this.ThoiGianCatCanhDanhNghia.toLocalDateTime();
            LocalDateTime landing = this.ThoiGianHaCanhDanhNghia.toLocalDateTime();
            Duration duration = Duration.between(takeoff, landing);
            long hours = duration.toHours();
            long minutes = duration.toMinutes() % 60;
            return String.format("%d hours %d minutes", hours, minutes);
        }
        return "Invalid time data";
    }
    public long getDurationInMinutes() {
    if (this.ThoiGianCatCanhDanhNghia != null && this.ThoiGianHaCanhDanhNghia != null) {
        LocalDateTime takeoff = this.ThoiGianCatCanhDanhNghia.toLocalDateTime();
        LocalDateTime landing = this.ThoiGianHaCanhDanhNghia.toLocalDateTime();
        Duration duration = Duration.between(takeoff, landing);
        return duration.toMinutes();
    }
    return -1;
}
}

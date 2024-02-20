/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Class;

import java.io.Serializable;

/**
 *
 * @author Nguyen Dinh Dung
 */
public class SanPhamClass implements Serializable{

    public String MaLK;
    public String TenLK;
    public String LoaiLK;
    public float Gia;
    public int SoLuong;
    public int BaoHanh;
    public String NSX;
    public String Hinh;
    public String NguoiThem;
    public String TinhTrang;

    public SanPhamClass() {
    }

    public SanPhamClass(String MaLK, String TenLK, String LoaiLK, float Gia, int SoLuong, int BaoHanh, String NSX, String Hinh, String NguoiThem, String TinhTrang) {
        this.MaLK = MaLK;
        this.TenLK = TenLK;
        this.LoaiLK = LoaiLK;
        this.Gia = Gia;
        this.SoLuong = SoLuong;
        this.BaoHanh = BaoHanh;
        this.NSX = NSX;
        this.Hinh = Hinh;
        this.NguoiThem = NguoiThem;
        this.TinhTrang = TinhTrang;
    }

    public String getMaLK() {
        return MaLK;
    }

    public void setMaLK(String MaLK) {
        this.MaLK = MaLK;
    }

    public String getTenLK() {
        return TenLK;
    }

    public void setTenLK(String TenLK) {
        this.TenLK = TenLK;
    }

    public String getLoaiLK() {
        return LoaiLK;
    }

    public void setLoaiLK(String LoaiLK) {
        this.LoaiLK = LoaiLK;
    }

    public float getGia() {
        return Gia;
    }

    public void setGia(float Gia) {
        this.Gia = Gia;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int SoLuong) {
        this.SoLuong = SoLuong;
    }

    public int getBaoHanh() {
        return BaoHanh;
    }

    public void setBaoHanh(int BaoHanh) {
        this.BaoHanh = BaoHanh;
    }

    public String getNSX() {
        return NSX;
    }

    public void setNSX(String NSX) {
        this.NSX = NSX;
    }

    public String getHinh() {
        return Hinh;
    }

    public void setHinh(String Hinh) {
        this.Hinh = Hinh;
    }

    public String getNguoiThem() {
        return NguoiThem;
    }

    public void setNguoiThem(String NguoiThem) {
        this.NguoiThem = NguoiThem;
    }

    public String getTinhTrang() {
        return TinhTrang;
    }

    public void setTinhTrang(String TinhTrang) {
        this.TinhTrang = TinhTrang;
    }
}

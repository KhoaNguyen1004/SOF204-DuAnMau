/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Class;

import Controller.DBConnection;
import java.awt.image.SampleModel;
import java.util.List;
import java.util.ArrayList;
import java.sql.*;

/**
 *
 * @author Nguyen Dinh Dung
 */
public class SanPhamDAO {

    public static List<SanPhamClass> listSP = new ArrayList<>();

    public SanPhamClass getSP_byMASP(String MaLK) throws SQLException {
        Connection conn = null;
        PreparedStatement sttm = null;
        ResultSet rs = null;
        SanPhamClass sp = new SanPhamClass();
        try {
            String sSQL = "use Nhom2_DuAnMau; select * from SanPham where MaLK = ?";
            conn = DBConnection.getConnection();
            sttm = conn.prepareStatement(sSQL);
            sttm.setString(1, MaLK);
            rs = sttm.executeQuery();
            while (rs.next()) {
                sp.setMaLK((rs.getString(1)));
                sp.setTenLK((rs.getString(2)));
                sp.setLoaiLK(rs.getString(3));
                sp.setGia(rs.getFloat(4));
                sp.setSoLuong(rs.getInt(5));
                sp.setBaoHanh(rs.getInt(6));
                sp.setNSX(rs.getString(7));
                sp.setHinh(rs.getString(8));
                sp.setNguoiThem(rs.getString(9));
                sp.setTinhTrang(rs.getString(10));
                return sp;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                conn.close();
                rs.close();
                sttm.close();
            } catch (Exception e) {

            }
        }
        return null;
    }

    public List<SanPhamClass> getAll_SanPham() {
        List<SanPhamClass> listSP = new ArrayList<>();
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            String sSQL = "select * from SanPham";
            conn = DBConnection.getConnection();
            st = conn.createStatement();
            rs = st.executeQuery(sSQL);
            while (rs.next()) {
                SanPhamClass sp = new SanPhamClass();
                sp.setMaLK((rs.getString(1)));
                sp.setTenLK((rs.getString(2)));
                sp.setLoaiLK(rs.getString(3));
                sp.setGia(rs.getFloat(4));
                sp.setSoLuong(rs.getInt(5));
                sp.setBaoHanh(rs.getInt(6));
                sp.setNSX(rs.getString(7));
                sp.setHinh(rs.getString(8));
                sp.setNguoiThem(rs.getString(9));
                sp.setTinhTrang(rs.getString(10));
                listSP.add(sp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                conn.close();
                st.close();
            } catch (Exception e) {

            }
        }
        return listSP;
    }

    public int updateSP(SanPhamClass sp) throws SQLException {
        Connection conn = null;
        PreparedStatement sttm = null;
        try {
            String sSQL = "update SanPham set TenLK=?,LoaiLK=?, Gia=? ,SoLuong=? , BaoHanh=? ,NSX=?, Hinh=?, NguoiThem=? ,TinhTrang=? where MaLK=?";
            conn = DBConnection.getConnection();
            sttm = conn.prepareStatement(sSQL);
            sttm.setString(10, sp.getMaLK());
            sttm.setString(1, sp.getTenLK());
            sttm.setString(2, sp.getLoaiLK());
            sttm.setString(3, String.valueOf(sp.getGia()));
            sttm.setString(4, String.valueOf(sp.getSoLuong()));
            sttm.setString(5, String.valueOf(sp.getBaoHanh()));
            sttm.setString(6, sp.getNSX());
            sttm.setString(7, sp.getHinh());
            sttm.setString(8, sp.getNguoiThem());
            sttm.setString(9, (String) sp.getTinhTrang());
            if (sttm.executeUpdate() > 0) {
                System.out.println("Updated");
                return 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
                sttm.close();
            } catch (Exception e) {

            }
        }
        return -1;
    }

    public int addSP(SanPhamClass sp) throws SQLException {
        Connection conn = null;
        PreparedStatement sttm = null;
        try {
            String sSQL = "insert into SanPham (MaLK , TenLK , LoaiLK , Gia , SoLuong , BaoHanh , NSX , Hinh , NguoiThem , TinhTrang) values (?,?,?,?,?,?,?,?,?,?)";
            conn = DBConnection.getConnection();
            sttm = conn.prepareStatement(sSQL);
            sttm.setString(1, sp.getMaLK());
            sttm.setString(2, sp.getTenLK());
            sttm.setString(3, sp.getLoaiLK());
            sttm.setString(4, String.valueOf(sp.getGia()));
            sttm.setString(5, String.valueOf(sp.getSoLuong()));
            sttm.setString(6, String.valueOf(sp.getBaoHanh()));
            sttm.setString(7, sp.getNSX());
            sttm.setString(8, sp.getHinh());
            sttm.setString(9, sp.getNguoiThem());
            sttm.setString(10, (String) sp.getTinhTrang());
           
            if (sttm.executeUpdate() > 0) {
                System.out.println("Added");
                return 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
                sttm.close();
            } catch (Exception e) {

            }
        }
        return -1;
    }

}

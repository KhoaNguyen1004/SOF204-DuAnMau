/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Class;


import Controller.DBConnection;
import java.util.List;
import java.util.ArrayList;
import java.sql.*;

public class NhanVienDAO {

    public static List<NhanVienClass> listSV = new ArrayList<>();

    public NhanVienClass getNV_byMANV(String MaNV) throws SQLException {
        Connection conn = null;
        PreparedStatement sttm = null;
        ResultSet rs = null;
        NhanVienClass nv = new NhanVienClass();
        try {
            String sSQL = "use Nhom2_DuAnMau; select * from NhanVien where MaNV = ?";
            conn = DBConnection.getConnection();
            sttm = conn.prepareStatement(sSQL);
            sttm.setString(1, MaNV);
            rs = sttm.executeQuery();
            while (rs.next()) {
                nv.setMaNV((rs.getString(1)));
                nv.setTenNV((rs.getString(2)));
                nv.setGioiTinh(rs.getString(3));
                nv.setSDT(rs.getString(4));
                nv.setEmail(rs.getString(5));
                nv.setDiaChi(rs.getString(6));
                nv.setHinh(rs.getString(7));
                nv.setNamSinh(rs.getInt(8));
                nv.setChucVu(rs.getString(9));
                nv.setTinhtrang(rs.getString(10));
                return nv;
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

    public List<NhanVienClass> getAll_NhanVien() {
        List<NhanVienClass> listNV = new ArrayList<>();
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            String sSQL = "select * from NhanVien";
            conn = DBConnection.getConnection();
            st = conn.createStatement();
            rs = st.executeQuery(sSQL);
            while (rs.next()) {
                NhanVienClass nv = new NhanVienClass();
                nv.setMaNV((rs.getString(1)));
                nv.setTenNV((rs.getString(2)));
                nv.setGioiTinh(rs.getString(3));
                nv.setSDT(rs.getString(4));
                nv.setEmail(rs.getString(5));
                nv.setDiaChi(rs.getString(6));
                nv.setHinh(rs.getString(7));
                nv.setNamSinh(rs.getInt(8));
                nv.setChucVu(rs.getString(9));
                nv.setTinhtrang(rs.getString(10));
                listNV.add(nv);
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
        return listNV;
    }

    public int updateNV(NhanVienClass nv) throws SQLException {
        Connection conn = null;
        PreparedStatement sttm = null;
        try {
            String sSQL = "update NhanVien set TenNV=?,GioiTinh=?,SDT=? ,Email=? , DiaChi=? ,Hinh=?, NamSinh=?, ChucVu=? ,TinhTrang=? where MaNV=?";
            conn = DBConnection.getConnection();
            sttm = conn.prepareStatement(sSQL);
            sttm.setString(10, nv.getMaNV());
            sttm.setString(1, nv.getTenNV());
            sttm.setString(2, nv.IsGioiTinh());
            sttm.setString(3, nv.getSDT());
            sttm.setString(4, nv.getEmail());
            sttm.setString(5, nv.getDiaChi());
            sttm.setString(6, nv.getHinh());
            sttm.setString(7, String.valueOf(nv.getNamSinh()));
            // sttm.setInt(7, Integer.parseInt(nv.getNamSinh()));
            sttm.setString(8, nv.getChucVu());
            sttm.setString(9, (String) nv.getTinhtrang());

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

    public int addNV(NhanVienClass nv) throws SQLException {
        Connection conn = null;
        PreparedStatement sttm = null;
        try {
            String sSQL = "insert into NhanVien (MaNV,TenNV,GioiTinh, SDT,Email, DiaChi, Hinh,NamSinh,ChucVu,TinhTrang) values (?,?,?,?,?,?,?,?,?,?)";
            conn = DBConnection.getConnection();
            sttm = conn.prepareStatement(sSQL);
            sttm.setString(1, nv.getMaNV());
            sttm.setString(2, nv.getTenNV());
            sttm.setString(3, nv.IsGioiTinh());
            sttm.setString(4, nv.getSDT());
            sttm.setString(5, nv.getEmail());
            sttm.setString(6, nv.getDiaChi());
            sttm.setString(7, nv.getHinh());
            sttm.setString(8, String.valueOf(nv.getNamSinh()));
            sttm.setString(9, nv.getChucVu());
            sttm.setString(10, (String) nv.getTinhtrang());
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

    
     public List<NhanVienClass> FindNhanVienByName(String name){
        ResultSet rs = null;
        Statement sttm = null;
        Connection conn=null;
        List<NhanVienClass> ls = new ArrayList<>();
        try{
            String sSQL = "select * from nhanvien where manv like N'%"+name+"%' or tennv like N'%"+name+"%'";
            conn = DBConnection.getConnection();
            sttm = conn.createStatement();
            rs = sttm.executeQuery(sSQL);
            while(rs.next()){
                NhanVienClass nv = new NhanVienClass();
                nv.setMaNV(rs.getString(1));
                nv.setTenNV(rs.getString(2));
                nv.setGioiTinh(rs.getString(3));
                nv.setSDT(rs.getString(4));
                nv.setEmail(rs.getString(5));
                nv.setDiaChi(rs.getString(6));
                nv.setNamSinh(rs.getInt(7));
                nv.setChucVu(rs.getString(8));
                nv.setTinhtrang(rs.getString(9));
                ls.add(nv);
            }
        }catch(Exception e){
            System.out.println("Error"+e.toString());
        }
        finally{
            try{
                rs.close();
                sttm.close();
                conn.close();
            }catch(Exception e){
            }
        }
        return ls;
    }
       
}

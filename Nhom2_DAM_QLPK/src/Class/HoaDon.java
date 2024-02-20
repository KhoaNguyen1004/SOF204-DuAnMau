/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Class;

import Controller.DBConnection;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nguyen Dung
 */
public class HoaDon {

    Connection con = null;
    Statement st = null;
    ResultSet rs = null;
    PreparedStatement ps = null;

    public int getMaxRow() {
        int row = 0;
        try {
            con = DBConnection.getConnection();
            st = con.createStatement();
            rs = st.executeQuery("select max(mahd) from hoadon");
            while (rs.next()) {
                row = rs.getInt(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(HoaDon.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row + 1;
    }

    public void addHDCT(int mahd, String tenlk, String malk, int soluong, float donGia) {

        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("insert into hdct values (?,?,?,?,?)");
            ps.setInt(1, mahd);
            ps.setString(2, tenlk);
            ps.setString(3, malk);
            ps.setInt(4, soluong);
            ps.setFloat(5, donGia);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(HoaDon.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public int getSoluong(String masp) {
        int sl = 0;
        try {
            con = DBConnection.getConnection();
            st = con.createStatement();
            rs = st.executeQuery("select soluong from sanpham where malk='" + masp + "'");
            if (rs.next()) {
                sl = rs.getInt(1);
            }
        } catch (Exception e) {
        }
        return sl;
    }

    public void updateSoluong(String masp, int sl) {
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("update sanpham set soluong=? where malk=?");
            ps.setInt(1, sl);
            ps.setString(2, masp);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

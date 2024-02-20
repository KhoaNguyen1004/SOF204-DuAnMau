package UI.QuanLy;

import Controller.Chuyenmanhinh;
import Controller.DBConnection;
import Controller.DanhMuc;
import UI.Login.FormDoiPass;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainJFrames extends javax.swing.JFrame {

    Connection con = null;
    PreparedStatement ps = null;
    Statement st = null;
    ResultSet rs = null;

    public MainJFrames() {
        initComponents();
        setLocationRelativeTo(this);

        Chuyenmanhinh control = new Chuyenmanhinh(jpnView);
        control.setView(jpnTrangChu, jlbTrangChu);
        List<DanhMuc> listItem = new ArrayList<>();
        listItem.add(new DanhMuc("TrangChu", jpnTrangChu, jlbTrangChu));
        listItem.add(new DanhMuc("QLNV", jpnNhanVien, jlbNhanVien));
        listItem.add(new DanhMuc("QLSP", jpnSanPham, jlbSanPham));
        listItem.add(new DanhMuc("QLHD", jpnHoaDon, jlbHoaDon));
        listItem.add(new DanhMuc("ThongKe", jpnThongKe, jlbThongKe));
        //listItem.add(new DanhMuc("TaiKhoan", jpnTaiKhoan1, jlbTaiKhoan));
        listItem.add(new DanhMuc("Thoat", jpnThoat, jlbThoat));
        control.setEvent(listItem);
    }

    public void getten(String name) {
        lblTenNV.setText(name);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpnRoot = new javax.swing.JPanel();
        jpnHotBar = new javax.swing.JPanel();
        lblTenNV = new javax.swing.JLabel();
        Exit1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        lblDoiMK = new javax.swing.JLabel();
        jpnMenu = new javax.swing.JPanel();
        jpnTrangChu = new javax.swing.JPanel();
        jlbTrangChu = new javax.swing.JLabel();
        jpnNhanVien = new javax.swing.JPanel();
        jlbNhanVien = new javax.swing.JLabel();
        jpnSanPham = new javax.swing.JPanel();
        jlbSanPham = new javax.swing.JLabel();
        jpnHoaDon = new javax.swing.JPanel();
        jlbHoaDon = new javax.swing.JLabel();
        jpnThongKe = new javax.swing.JPanel();
        jlbThongKe = new javax.swing.JLabel();
        jpnThoat = new javax.swing.JPanel();
        jlbThoat = new javax.swing.JLabel();
        jpnView = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(1200, 700));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jpnRoot.setBackground(new java.awt.Color(255, 255, 255));
        jpnRoot.setPreferredSize(new java.awt.Dimension(1200, 700));
        jpnRoot.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jpnHotBar.setBackground(new java.awt.Color(95, 157, 247));
        jpnHotBar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        jpnHotBar.setPreferredSize(new java.awt.Dimension(1200, 40));

        lblTenNV.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTenNV.setForeground(new java.awt.Color(255, 247, 233));
        lblTenNV.setText("Nguyễn Ngọc Minh Thuận");
        lblTenNV.setPreferredSize(new java.awt.Dimension(190, 20));

        Exit1.setBackground(new java.awt.Color(255, 102, 102));
        Exit1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        Exit1.setForeground(new java.awt.Color(255, 247, 233));
        Exit1.setText("X");
        Exit1.setBorder(null);
        Exit1.setBorderPainted(false);
        Exit1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Exit1.setFocusPainted(false);
        Exit1.setFocusable(false);
        Exit1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Exit1ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 247, 233));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("_");
        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel1.setPreferredSize(new java.awt.Dimension(40, 40));
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        lblDoiMK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/door-key.png"))); // NOI18N
        lblDoiMK.setText("jLabel2");
        lblDoiMK.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblDoiMK.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblDoiMKMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jpnHotBarLayout = new javax.swing.GroupLayout(jpnHotBar);
        jpnHotBar.setLayout(jpnHotBarLayout);
        jpnHotBarLayout.setHorizontalGroup(
            jpnHotBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnHotBarLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(lblTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblDoiMK, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(846, 846, 846)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(Exit1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jpnHotBarLayout.setVerticalGroup(
            jpnHotBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnHotBarLayout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(jpnHotBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDoiMK, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(Exit1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jpnRoot.add(jpnHotBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jpnMenu.setBackground(new java.awt.Color(95, 157, 247));
        jpnMenu.setPreferredSize(new java.awt.Dimension(70, 660));
        jpnMenu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jpnTrangChu.setBackground(new java.awt.Color(95, 157, 247));

        jlbTrangChu.setBackground(new java.awt.Color(95, 157, 247));
        jlbTrangChu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbTrangChu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/home (7).png"))); // NOI18N

        javax.swing.GroupLayout jpnTrangChuLayout = new javax.swing.GroupLayout(jpnTrangChu);
        jpnTrangChu.setLayout(jpnTrangChuLayout);
        jpnTrangChuLayout.setHorizontalGroup(
            jpnTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jlbTrangChu, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jpnTrangChuLayout.setVerticalGroup(
            jpnTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jlbTrangChu, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jpnMenu.add(jpnTrangChu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jpnNhanVien.setBackground(new java.awt.Color(95, 157, 247));

        jlbNhanVien.setBackground(new java.awt.Color(95, 157, 247));
        jlbNhanVien.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbNhanVien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/add-user.png"))); // NOI18N

        javax.swing.GroupLayout jpnNhanVienLayout = new javax.swing.GroupLayout(jpnNhanVien);
        jpnNhanVien.setLayout(jpnNhanVienLayout);
        jpnNhanVienLayout.setHorizontalGroup(
            jpnNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jlbNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
        );
        jpnNhanVienLayout.setVerticalGroup(
            jpnNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jlbNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
        );

        jpnMenu.add(jpnNhanVien, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, -1, -1));

        jpnSanPham.setBackground(new java.awt.Color(95, 157, 247));

        jlbSanPham.setBackground(new java.awt.Color(95, 157, 247));
        jlbSanPham.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbSanPham.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/add-product.png"))); // NOI18N
        jlbSanPham.setPreferredSize(new java.awt.Dimension(70, 70));

        javax.swing.GroupLayout jpnSanPhamLayout = new javax.swing.GroupLayout(jpnSanPham);
        jpnSanPham.setLayout(jpnSanPhamLayout);
        jpnSanPhamLayout.setHorizontalGroup(
            jpnSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jlbSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jpnSanPhamLayout.setVerticalGroup(
            jpnSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jlbSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jpnMenu.add(jpnSanPham, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, -1, -1));

        jpnHoaDon.setBackground(new java.awt.Color(95, 157, 247));

        jlbHoaDon.setBackground(new java.awt.Color(95, 157, 247));
        jlbHoaDon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/bill.png"))); // NOI18N

        javax.swing.GroupLayout jpnHoaDonLayout = new javax.swing.GroupLayout(jpnHoaDon);
        jpnHoaDon.setLayout(jpnHoaDonLayout);
        jpnHoaDonLayout.setHorizontalGroup(
            jpnHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jlbHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
        );
        jpnHoaDonLayout.setVerticalGroup(
            jpnHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jlbHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
        );

        jpnMenu.add(jpnHoaDon, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 210, -1, -1));

        jpnThongKe.setBackground(new java.awt.Color(95, 157, 247));

        jlbThongKe.setBackground(new java.awt.Color(95, 157, 247));
        jlbThongKe.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbThongKe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/line-chart.png"))); // NOI18N

        javax.swing.GroupLayout jpnThongKeLayout = new javax.swing.GroupLayout(jpnThongKe);
        jpnThongKe.setLayout(jpnThongKeLayout);
        jpnThongKeLayout.setHorizontalGroup(
            jpnThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jlbThongKe, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
        );
        jpnThongKeLayout.setVerticalGroup(
            jpnThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jlbThongKe, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
        );

        jpnMenu.add(jpnThongKe, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 280, -1, -1));

        jpnThoat.setBackground(new java.awt.Color(95, 157, 247));

        jlbThoat.setBackground(new java.awt.Color(95, 157, 247));
        jlbThoat.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlbThoat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/logout (1).png"))); // NOI18N
        jlbThoat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlbThoatMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jpnThoatLayout = new javax.swing.GroupLayout(jpnThoat);
        jpnThoat.setLayout(jpnThoatLayout);
        jpnThoatLayout.setHorizontalGroup(
            jpnThoatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnThoatLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jlbThoat, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jpnThoatLayout.setVerticalGroup(
            jpnThoatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnThoatLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jlbThoat, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jpnMenu.add(jpnThoat, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 590, -1, -1));

        jpnRoot.add(jpnMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, -1, -1));

        jpnView.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jpnViewLayout = new javax.swing.GroupLayout(jpnView);
        jpnView.setLayout(jpnViewLayout);
        jpnViewLayout.setHorizontalGroup(
            jpnViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1130, Short.MAX_VALUE)
        );
        jpnViewLayout.setVerticalGroup(
            jpnViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 660, Short.MAX_VALUE)
        );

        jpnRoot.add(jpnView, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 40, 1130, 660));

        getContentPane().add(jpnRoot, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1500, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Exit1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Exit1ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_Exit1ActionPerformed

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        // TODO add your handling code here:
        setExtendedState(ICONIFIED);
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jlbThoatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbThoatMouseClicked
        // TODO add your handling code here:
        new UI.Login.Login().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jlbThoatMouseClicked

    private void lblDoiMKMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDoiMKMouseClicked
        try {
            // TODO add your handling code here:
            
            String manv = "";
            FormDoiPass fdp = new FormDoiPass(this, true);
            con = DBConnection.getConnection();
            st = con.createStatement();
            rs = st.executeQuery("select manv from nhanvien where tennv=N'" + lblTenNV.getText() + "'");
            if (rs.next()) {
                manv = rs.getString(1);
            }
            fdp.getMaNV(manv);
            fdp.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(MainJFrames.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_lblDoiMKMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Exit1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jlbHoaDon;
    private javax.swing.JLabel jlbNhanVien;
    private javax.swing.JLabel jlbSanPham;
    private javax.swing.JLabel jlbThoat;
    private javax.swing.JLabel jlbThongKe;
    private javax.swing.JLabel jlbTrangChu;
    private javax.swing.JPanel jpnHoaDon;
    private javax.swing.JPanel jpnHotBar;
    private javax.swing.JPanel jpnMenu;
    private javax.swing.JPanel jpnNhanVien;
    private javax.swing.JPanel jpnRoot;
    private javax.swing.JPanel jpnSanPham;
    private javax.swing.JPanel jpnThoat;
    private javax.swing.JPanel jpnThongKe;
    private javax.swing.JPanel jpnTrangChu;
    private javax.swing.JPanel jpnView;
    private javax.swing.JLabel lblDoiMK;
    public static javax.swing.JLabel lblTenNV;
    // End of variables declaration//GEN-END:variables
}

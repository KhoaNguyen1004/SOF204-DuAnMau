package UI.NhanVien;

import Controller.DBConnection;
import UI.QuanLy.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

public class SanPhamPanel3 extends javax.swing.JPanel {

    Connection con = null;
    PreparedStatement ps = null;
    Statement st = null;
    ResultSet rs = null;
    Vector data = null;
    DefaultTableModel model = null;

    public SanPhamPanel3() {
        initComponents();
        fillSanPham();
        EventTxTFind();
    }

    void fillSanPham() {
        try {
            model = (DefaultTableModel) tblSanPham.getModel();
            model.setRowCount(0);
            con = DBConnection.getConnection();
            st = con.createStatement();
            rs = st.executeQuery("select * from sanpham");
            while (rs.next()) {
                data = new Vector();
                data.add(rs.getString(2));
                data.add(rs.getString(3));
                data.add(rs.getInt(5));
                float gia = rs.getFloat(4);
                String fm = new DecimalFormat("#,###").format(gia);
                data.add(fm);
                data.add(rs.getString(10));
                model.addRow(data);
            }
            tblSanPham.setModel(model);
        } catch (SQLException ex) {
            Logger.getLogger(SanPhamPanel3.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void fillToForm() {
        try {
            int index = tblSanPham.getSelectedRow();
            String tenLK = (String) tblSanPham.getValueAt(index, 0);
            con = DBConnection.getConnection();
            st = con.createStatement();
            rs = st.executeQuery("select * from sanpham where tenlk=N'" + tenLK + "'");
            if (rs.next()) {
                txtMaLK.setText(rs.getString(1));
                txtTenLk.setText(rs.getString(2));
                txtLoaiLK.setText(rs.getString(3));
                float gia = rs.getFloat(4);
                String fm = new DecimalFormat("#,###").format(gia);
                txtDonGia.setText(fm);
                txtSoluong.setText(String.valueOf(rs.getInt(5)));
                txtNXS.setText(rs.getString(7));
                txtBaohanh.setText(String.valueOf(rs.getInt(6)));
                txtTrangThai.setText(rs.getString(10));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SanPhamPanel3.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void EventTxTFind() {
        txtTimKiem.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void removeUpdate(DocumentEvent e) {
                if (txtTimKiem.getText().trim().length() == 0) {
                    fillSanPham();
                }
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
    }

    void FindByName(String find) {
        try {
            DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
            model.setRowCount(0);
            con = DBConnection.getConnection();
            st = con.createStatement();
            String query = "select * "
                    + "from SanPham where MaLK like '%" + find + "%' or TenLK like N'%" + find + "%'";
            rs = st.executeQuery(query);
            while (rs.next()) {
                data = new Vector();
                data.add(rs.getNString("TenLK"));
                data.add(rs.getString("LoaiLK"));
                data.add(rs.getInt("SoLuong"));
                data.add(rs.getFloat("Gia"));
                data.add(rs.getNString("TinhTrang"));
                model.addRow(data);
            }
            tblSanPham.setModel(model);
        } catch (Exception e) {
            Logger.getLogger(TrangChuPanel.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jpnRoot = new javax.swing.JPanel();
        jpnDSSanPham = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        txtTimKiem = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        btnTimKiem = new javax.swing.JButton();
        jpnTTSanPham = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtMaLK = new javax.swing.JTextField();
        lblHinh = new javax.swing.JLabel();
        txtTenLk = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtLoaiLK = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtSoluong = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtDonGia = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtBaohanh = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtNXS = new javax.swing.JTextField();
        txtTrangThai = new javax.swing.JTextField();

        jpnRoot.setBackground(new java.awt.Color(255, 255, 255));
        jpnRoot.setPreferredSize(new java.awt.Dimension(1130, 660));

        jpnDSSanPham.setBackground(new java.awt.Color(255, 255, 255));
        jpnDSSanPham.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18), new java.awt.Color(23, 70, 162))); // NOI18N

        jScrollPane2.setHorizontalScrollBar(null);

        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên LK", "Loại LK", "Số lượng", "Giá", "Tình trạng"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblSanPham);
        if (tblSanPham.getColumnModel().getColumnCount() > 0) {
            tblSanPham.getColumnModel().getColumn(0).setPreferredWidth(130);
            tblSanPham.getColumnModel().getColumn(1).setPreferredWidth(30);
            tblSanPham.getColumnModel().getColumn(2).setPreferredWidth(10);
            tblSanPham.getColumnModel().getColumn(3).setPreferredWidth(50);
            tblSanPham.getColumnModel().getColumn(4).setPreferredWidth(50);
        }

        txtTimKiem.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Tìm kiếm");

        btnTimKiem.setBackground(new java.awt.Color(95, 157, 247));
        btnTimKiem.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnTimKiem.setForeground(new java.awt.Color(255, 247, 233));
        btnTimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/search (5).png"))); // NOI18N
        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.setBorderPainted(false);
        btnTimKiem.setFocusPainted(false);
        btnTimKiem.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnTimKiem.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpnDSSanPhamLayout = new javax.swing.GroupLayout(jpnDSSanPham);
        jpnDSSanPham.setLayout(jpnDSSanPhamLayout);
        jpnDSSanPhamLayout.setHorizontalGroup(
            jpnDSSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnDSSanPhamLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 556, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jpnDSSanPhamLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnTimKiem)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpnDSSanPhamLayout.setVerticalGroup(
            jpnDSSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnDSSanPhamLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnDSSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(btnTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 531, Short.MAX_VALUE)
                .addContainerGap())
        );

        jpnTTSanPham.setBackground(new java.awt.Color(255, 255, 255));
        jpnTTSanPham.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18), new java.awt.Color(23, 70, 162))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Mã linh kiện:");

        txtMaLK.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtMaLK.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtMaLK.setEnabled(false);
        txtMaLK.setPreferredSize(new java.awt.Dimension(200, 30));

        lblHinh.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblHinh.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHinh.setText("Hình");
        lblHinh.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lblHinh.setPreferredSize(new java.awt.Dimension(128, 128));

        txtTenLk.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtTenLk.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtTenLk.setEnabled(false);
        txtTenLk.setPreferredSize(new java.awt.Dimension(200, 30));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Tên linh kiện:");

        txtLoaiLK.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtLoaiLK.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtLoaiLK.setEnabled(false);
        txtLoaiLK.setPreferredSize(new java.awt.Dimension(200, 30));
        txtLoaiLK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLoaiLKActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Số lượng:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Loại linh kiện:");

        txtSoluong.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtSoluong.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtSoluong.setEnabled(false);
        txtSoluong.setPreferredSize(new java.awt.Dimension(200, 30));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtDonGia.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtDonGia.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtDonGia.setEnabled(false);
        txtDonGia.setPreferredSize(new java.awt.Dimension(200, 30));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Đơn giá:");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("Trạng thái:");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setText("Bảo hành:");

        txtBaohanh.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtBaohanh.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtBaohanh.setEnabled(false);
        txtBaohanh.setPreferredSize(new java.awt.Dimension(200, 30));

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel14.setText("Nhà sản xuất:");

        txtNXS.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtNXS.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtNXS.setEnabled(false);
        txtNXS.setPreferredSize(new java.awt.Dimension(200, 30));

        txtTrangThai.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtTrangThai.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtTrangThai.setEnabled(false);
        txtTrangThai.setPreferredSize(new java.awt.Dimension(200, 30));

        javax.swing.GroupLayout jpnTTSanPhamLayout = new javax.swing.GroupLayout(jpnTTSanPham);
        jpnTTSanPham.setLayout(jpnTTSanPhamLayout);
        jpnTTSanPhamLayout.setHorizontalGroup(
            jpnTTSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnTTSanPhamLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel7)
                .addGap(472, 472, 472))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnTTSanPhamLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnTTSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnTTSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jpnTTSanPhamLayout.createSequentialGroup()
                            .addComponent(jLabel6)
                            .addGap(24, 24, 24))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnTTSanPhamLayout.createSequentialGroup()
                            .addComponent(jLabel4)
                            .addGap(29, 29, 29)))
                    .addGroup(jpnTTSanPhamLayout.createSequentialGroup()
                        .addGroup(jpnTTSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel5)
                            .addComponent(jLabel8)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14)
                            .addComponent(jLabel11))
                        .addGap(27, 27, 27)))
                .addGroup(jpnTTSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNXS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBaohanh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSoluong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jpnTTSanPhamLayout.createSequentialGroup()
                        .addGroup(jpnTTSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMaLK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTenLk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtLoaiLK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblHinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jpnTTSanPhamLayout.setVerticalGroup(
            jpnTTSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnTTSanPhamLayout.createSequentialGroup()
                .addGroup(jpnTTSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnTTSanPhamLayout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addGroup(jpnTTSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtMaLK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(jpnTTSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTenLk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(20, 20, 20)
                        .addGroup(jpnTTSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtLoaiLK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)))
                    .addGroup(jpnTTSanPhamLayout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(lblHinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20)
                .addGroup(jpnTTSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSoluong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(20, 20, 20)
                .addGroup(jpnTTSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jpnTTSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtBaohanh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jpnTTSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtNXS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jpnTTSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addGap(80, 80, 80)
                .addComponent(jLabel7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jpnRootLayout = new javax.swing.GroupLayout(jpnRoot);
        jpnRoot.setLayout(jpnRootLayout);
        jpnRootLayout.setHorizontalGroup(
            jpnRootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnRootLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpnDSSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jpnTTSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpnRootLayout.setVerticalGroup(
            jpnRootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnRootLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnRootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jpnDSSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpnTTSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpnRoot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jpnRoot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtLoaiLKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLoaiLKActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLoaiLKActionPerformed

    private void tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseClicked
        // TODO add your handling code here:
        fillToForm();
    }//GEN-LAST:event_tblSanPhamMouseClicked

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        // TODO add your handling code here:
        FindByName(txtTimKiem.getText());
    }//GEN-LAST:event_btnTimKiemActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnTimKiem;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel jpnDSSanPham;
    private javax.swing.JPanel jpnRoot;
    private javax.swing.JPanel jpnTTSanPham;
    private javax.swing.JLabel lblHinh;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTextField txtBaohanh;
    private javax.swing.JTextField txtDonGia;
    private javax.swing.JTextField txtLoaiLK;
    private javax.swing.JTextField txtMaLK;
    private javax.swing.JTextField txtNXS;
    private javax.swing.JTextField txtSoluong;
    private javax.swing.JTextField txtTenLk;
    private javax.swing.JTextField txtTimKiem;
    private javax.swing.JTextField txtTrangThai;
    // End of variables declaration//GEN-END:variables
}

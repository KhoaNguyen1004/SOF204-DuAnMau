package UI.QuanLy;

import Controller.DBConnection;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class HoaDonPanel extends javax.swing.JPanel {

    Vector data = null;
    PreparedStatement ps = null;
    Connection con = null;
    ResultSet rs = null;
    Statement st = null;
    //String header[] = {"Mã hóa đơn", "Mã người bán", "Ngày tạo hóa đơn", "Tổng tiền"};
    //String header2[] = {"Mã hóa đơn", "Mã sản phẩm", "Tên sản phẩm", "Đơn giá", "Số lượng","Tổng tiền"};
    DefaultTableModel mode = null;
    DefaultTableModel mode2 = null;

    public HoaDonPanel() {
        initComponents();
        fillHD();
        tableDecor();
    }

    void tableDecor() {
        tblDSHoaDon.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
        tblDSHoaDon.getTableHeader().setBackground(new Color(23, 70, 162));
        tblDSHoaDon.getTableHeader().setForeground(Color.DARK_GRAY);
        tblDSHoaDon.setRowHeight(30);

        tblHDCT.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 12));
        tblHDCT.getTableHeader().setBackground(new Color(23, 70, 162));
        tblHDCT.getTableHeader().setForeground(Color.DARK_GRAY);
        tblHDCT.setRowHeight(30);
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        //hdc.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);

    }

    void fillHD() {
        try {
            mode = (DefaultTableModel) tblDSHoaDon.getModel();
            mode.setRowCount(0);
            con = DBConnection.getConnection();
            st = con.createStatement();
            rs = st.executeQuery("select * from hoadon");
            while (rs.next()) {
                data = new Vector();
                data.add(rs.getInt("mahd"));
                data.add(rs.getString("nguoiban"));
                data.add(rs.getDate("ngaytaohd"));
                data.add(rs.getInt("makh"));
                float tt=rs.getFloat("tongtien");
                String fms = new DecimalFormat("#,###").format(tt);
                data.add(fms);
                mode.addRow(data);
            }
            tblDSHoaDon.setModel(mode);
        } catch (SQLException ex) {
            Logger.getLogger(HoaDonPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void fillHDCT() {
        try {
            mode2 = (DefaultTableModel) tblHDCT.getModel();
            int mahd = (int) tblDSHoaDon.getValueAt(tblDSHoaDon.getSelectedRow(), 0);
            mode2.setRowCount(0);
            con = DBConnection.getConnection();
            ps = con.prepareStatement("select * from hdct where mahd=?");
            ps.setInt(1, mahd);
            rs = ps.executeQuery();
            while (rs.next()) {
                data = new Vector();
                data.add(rs.getInt("mahd"));
                data.add(rs.getString("malk"));
                data.add(rs.getString("tenlk"));
                data.add(rs.getInt("soluong"));
                String fms = new DecimalFormat("#,###").format(rs.getFloat("dongialk"));
                data.add(fms);
                mode2.addRow(data);
            }
            tblHDCT.setModel(mode2);
        } catch (SQLException ex) {
            Logger.getLogger(HoaDonPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpnRoot = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDSHoaDon = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblHDCT = new javax.swing.JTable();

        jpnRoot.setBackground(new java.awt.Color(255, 255, 255));
        jpnRoot.setPreferredSize(new java.awt.Dimension(1130, 660));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách hóa đơn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18), new java.awt.Color(23, 70, 162))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Tìm kiếm");

        txtTimKiem.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        btnTimKiem.setBackground(new java.awt.Color(95, 157, 247));
        btnTimKiem.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnTimKiem.setForeground(new java.awt.Color(255, 243, 233));
        btnTimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/search (5).png"))); // NOI18N
        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.setBorderPainted(false);
        btnTimKiem.setFocusPainted(false);
        btnTimKiem.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnTimKiem.setPreferredSize(new java.awt.Dimension(100, 35));

        tblDSHoaDon.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblDSHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã HĐ", "Người bán", "Ngày tạo HĐ", "Mã KH", "Tổng tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDSHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDSHoaDonMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDSHoaDon);
        if (tblDSHoaDon.getColumnModel().getColumnCount() > 0) {
            tblDSHoaDon.getColumnModel().getColumn(0).setPreferredWidth(70);
            tblDSHoaDon.getColumnModel().getColumn(0).setMaxWidth(70);
            tblDSHoaDon.getColumnModel().getColumn(3).setPreferredWidth(70);
            tblDSHoaDon.getColumnModel().getColumn(3).setMaxWidth(70);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 493, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Hóa đơn chi tiết", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18), new java.awt.Color(23, 70, 162))); // NOI18N

        tblHDCT.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblHDCT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã HĐ", "Mã LK", "Tên LK", "Số lượng", "Đơn giá"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblHDCT);
        if (tblHDCT.getColumnModel().getColumnCount() > 0) {
            tblHDCT.getColumnModel().getColumn(0).setPreferredWidth(60);
            tblHDCT.getColumnModel().getColumn(0).setMaxWidth(60);
            tblHDCT.getColumnModel().getColumn(1).setPreferredWidth(70);
            tblHDCT.getColumnModel().getColumn(1).setMaxWidth(70);
            tblHDCT.getColumnModel().getColumn(3).setPreferredWidth(70);
            tblHDCT.getColumnModel().getColumn(3).setMaxWidth(70);
            tblHDCT.getColumnModel().getColumn(4).setPreferredWidth(130);
            tblHDCT.getColumnModel().getColumn(4).setMaxWidth(130);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 523, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );

        javax.swing.GroupLayout jpnRootLayout = new javax.swing.GroupLayout(jpnRoot);
        jpnRoot.setLayout(jpnRootLayout);
        jpnRootLayout.setHorizontalGroup(
            jpnRootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnRootLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpnRootLayout.setVerticalGroup(
            jpnRootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnRootLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnRootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jpnRoot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jpnRoot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblDSHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDSHoaDonMouseClicked
        // TODO add your handling code here:
        fillHDCT();
    }//GEN-LAST:event_tblDSHoaDonMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel jpnRoot;
    private javax.swing.JTable tblDSHoaDon;
    private javax.swing.JTable tblHDCT;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}

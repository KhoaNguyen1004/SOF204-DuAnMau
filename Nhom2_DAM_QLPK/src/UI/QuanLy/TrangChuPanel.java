package UI.QuanLy;

import Controller.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

public class TrangChuPanel extends javax.swing.JPanel {

    Connection con = null;
    PreparedStatement ps = null;
    Statement st = null;
    ResultSet rs = null;
    Vector data = null;

    public TrangChuPanel() {
        initComponents();
        DSHomNay();
        DSHomQua();
        top5SP();
    }
    
    
    
    void DSHomNay(){
        try {
            con=DBConnection.getConnection();
            st=con.createStatement();
            rs=st.executeQuery("select sum(tongtien) from hoadon where day(NgayTaoHD) = day(GETDATE()) and month(ngaytaohd)=month(getdate()) and year(ngaytaohd)=year(getdate());");
            if(rs.next()){
                float num=rs.getFloat(1);
                String fm=new DecimalFormat("#,###").format(num);
                lblDSHomNay.setText(fm);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TrangChuPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void DSHomQua(){
        try {
            con=DBConnection.getConnection();
            st=con.createStatement();
            rs=st.executeQuery("select sum(tongtien) from hoadon where day(NgayTaoHD) = day(GETDATE()-1) and month(ngaytaohd)=month(getdate()) and year(ngaytaohd)=year(getdate());");
            if(rs.next()){
                float num=rs.getFloat(1);
                String fm=new DecimalFormat("#,###").format(num);
                lblDSHomQua.setText(fm);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TrangChuPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void top5SP(){
        try {
            DefaultTableModel model=(DefaultTableModel) tblTopSP.getModel();
            model.setRowCount(0);
            con=DBConnection.getConnection();
            st=con.createStatement();
            rs=st.executeQuery("select  top 5 tenlk,sum(soluong),dongialk,sum(soluong)*dongialk from HDCT group by TenLK,DonGiaLK");
            while(rs.next()){
                data=new Vector();
                data.add(rs.getString(1));
                data.add(rs.getInt(2));
                float num=rs.getFloat(3);
                String fm=new DecimalFormat("#,###").format(num);
                data.add(fm);
                float tongtien=rs.getFloat(4);
                String fms=new DecimalFormat("#,###").format(tongtien);
                data.add(fm);
                model.addRow(data);
            }
            tblTopSP.setModel(model);
        } catch (SQLException ex) {
            Logger.getLogger(TrangChuPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpnRoot = new javax.swing.JPanel();
        jpnDoanhSo = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblDSHomNay = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblDSHomQua = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jpnTopSP = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTopSP = new javax.swing.JTable();

        jpnRoot.setBackground(new java.awt.Color(255, 255, 255));
        jpnRoot.setPreferredSize(new java.awt.Dimension(1130, 660));

        jpnDoanhSo.setBackground(new java.awt.Color(255, 255, 255));
        jpnDoanhSo.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Doanh số bán hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18), new java.awt.Color(23, 70, 162))); // NOI18N
        jpnDoanhSo.setPreferredSize(new java.awt.Dimension(500, 300));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Hôm nay:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Hôm qua:");

        lblDSHomNay.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblDSHomNay.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDSHomNay.setText("0");
        lblDSHomNay.setPreferredSize(new java.awt.Dimension(300, 36));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("VNĐ");

        lblDSHomQua.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblDSHomQua.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDSHomQua.setText("0");
        lblDSHomQua.setPreferredSize(new java.awt.Dimension(300, 36));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("VNĐ");

        javax.swing.GroupLayout jpnDoanhSoLayout = new javax.swing.GroupLayout(jpnDoanhSo);
        jpnDoanhSo.setLayout(jpnDoanhSoLayout);
        jpnDoanhSoLayout.setHorizontalGroup(
            jpnDoanhSoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnDoanhSoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnDoanhSoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnDoanhSoLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(22, 22, 22)
                        .addComponent(lblDSHomQua, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel6))
                    .addGroup(jpnDoanhSoLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(24, 24, 24)
                        .addComponent(lblDSHomNay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4)))
                .addContainerGap())
        );
        jpnDoanhSoLayout.setVerticalGroup(
            jpnDoanhSoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnDoanhSoLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jpnDoanhSoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDSHomNay, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44)
                .addGroup(jpnDoanhSoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDSHomQua, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(50, Short.MAX_VALUE))
        );

        jpnTopSP.setBackground(new java.awt.Color(255, 255, 255));
        jpnTopSP.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Sản phẩm bán chạy", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18), new java.awt.Color(23, 70, 162))); // NOI18N

        tblTopSP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên SP", "Số lượng", "Đơn giá", "Tổng giá"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblTopSP);

        javax.swing.GroupLayout jpnTopSPLayout = new javax.swing.GroupLayout(jpnTopSP);
        jpnTopSP.setLayout(jpnTopSPLayout);
        jpnTopSPLayout.setHorizontalGroup(
            jpnTopSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnTopSPLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 783, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpnTopSPLayout.setVerticalGroup(
            jpnTopSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnTopSPLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jpnRootLayout = new javax.swing.GroupLayout(jpnRoot);
        jpnRoot.setLayout(jpnRootLayout);
        jpnRootLayout.setHorizontalGroup(
            jpnRootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnRootLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnRootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jpnTopSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jpnDoanhSo, javax.swing.GroupLayout.PREFERRED_SIZE, 487, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(299, Short.MAX_VALUE))
        );
        jpnRootLayout.setVerticalGroup(
            jpnRootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnRootLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpnDoanhSo, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jpnTopSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel jpnDoanhSo;
    private javax.swing.JPanel jpnRoot;
    private javax.swing.JPanel jpnTopSP;
    private javax.swing.JLabel lblDSHomNay;
    private javax.swing.JLabel lblDSHomQua;
    private javax.swing.JTable tblTopSP;
    // End of variables declaration//GEN-END:variables
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package UI.QuanLy;

import Controller.DBConnection;
import com.toedter.calendar.JTextFieldDateEditor;

import java.io.BufferedOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import javax.swing.JFileChooser;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class ThongKePanel extends javax.swing.JPanel {

    Connection con = null;
    PreparedStatement ps = null;
    Statement st = null;
    ResultSet rs = null;
    Vector data = null;

    public ThongKePanel() {
        initComponents();
        JTextFieldDateEditor editor = (JTextFieldDateEditor) jdcFromDate.getDateEditor();
        editor.setEditable(false);
        JTextFieldDateEditor editor1 = (JTextFieldDateEditor) jdcToDate.getDateEditor();
        editor1.setEditable(false);
    }

    void ThongKeHD(String fromDate, String toDate) {
        try {
            DefaultTableModel model = (DefaultTableModel) tblThongKeHD.getModel();
            model.setRowCount(0);
            con = DBConnection.getConnection();
            st = con.createStatement();
            String query = "  select HoaDon.MaHD,NgayTaoHD,TenNV,MaKH,sum(DonGiaLK*SoLuong)\n"
                    + "  from HoaDon inner join HDCT on HoaDon.MaHD = HDCT.MaHD\n"
                    + "  inner join NhanVien nv on nv.MaNV = HoaDon.NguoiBan\n"
                    + "  where NgayTaoHD between '" + fromDate + "' and '" + toDate + "'"
                    + "  group by HoaDon.MaHD, NgayTaoHD, TenNV, MaKH";
            rs = st.executeQuery(query);
            while (rs.next()) {
                data = new Vector();
                data.add(rs.getInt(1));
                data.add(rs.getDate(2));
                data.add(rs.getNString(3));
                data.add(rs.getInt(4));
                float gia = rs.getFloat(5);
                String fm = new DecimalFormat("#,###").format(gia);
                data.add(fm);
                model.addRow(data);
            }
            tblThongKeHD.setModel(model);
        } catch (Exception e) {
            Logger.getLogger(TrangChuPanel.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    void ThongKeSP(String fromDate, String toDate) {
        try {
            DefaultTableModel model = (DefaultTableModel) tblThongKeSP.getModel();
            model.setRowCount(0);
            con = DBConnection.getConnection();
            st = con.createStatement();
            String query = "select TenLK, sum(SoLuong), DonGiaLK, DonGiaLK*sum(SoLuong)\n"
                    + "  from HoaDon hd inner join HDCT on hd.MaHD = HDCT.MaHD\n"
                    + "  where NgayTaoHD between '" + fromDate + "' and '" + toDate + "'"
                    + "  group by TenLK, DonGiaLK";
            rs = st.executeQuery(query);
            while (rs.next()) {
                data = new Vector();
                data.add(rs.getNString(1));
                data.add(rs.getInt(2));
                float gia = rs.getFloat(3);
                String fmgia = new DecimalFormat("#,###").format(gia);
                data.add(fmgia);
                float tongtien = rs.getFloat(4);
                String fm = new DecimalFormat("#,###").format(tongtien);
                data.add(fm);
                model.addRow(data);
            }
            tblThongKeSP.setModel(model);
        } catch (Exception e) {
            Logger.getLogger(TrangChuPanel.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    void TongHD(String fromDate, String toDate) {
        try {
            con = DBConnection.getConnection();
            st = con.createStatement();
            String query = "select COUNT(DISTINCT HDCT.MaHD)\n"
                    + "  from HoaDon inner join HDCT on HoaDon.MaHD = HDCT.MaHD\n"
                    + "  where NgayTaoHD between '" + fromDate + "' and '" + toDate + "'";
            rs = st.executeQuery(query);
            if (rs.next()) {
                lblTongHD.setText(String.valueOf(rs.getInt(1)));
            }
        } catch (Exception e) {
            Logger.getLogger(TrangChuPanel.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    void TongTienHD() {
        float tong = 0;
        float tien = 0;
        DefaultTableModel model = (DefaultTableModel) tblThongKeHD.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            tien = Float.parseFloat(model.getValueAt(i, 4).toString().replaceAll("[^0-9]", ""));
            tong += tien;
        }
        String fm = new DecimalFormat("#,###").format(tong);
        lblTongTien.setText(fm);
    }

    void TongSP() {
        int tong = 0;
        DefaultTableModel model = (DefaultTableModel) tblThongKeSP.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            int sl = (int) (model.getValueAt(i, 1));
            tong += sl;
        }
        lblTongSL.setText(String.valueOf(tong));
    }

    void InTK() {
        FileOutputStream out = null;
        BufferedOutputStream bos = null;
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFRow row, r = null;
        Cell cell, c = null;
        try {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setDialogTitle("Save as");
            DefaultTableModel model1 = (DefaultTableModel) tblThongKeHD.getModel();
            DefaultTableModel model2 = (DefaultTableModel) tblThongKeSP.getModel();
            int opt = jFileChooser.showSaveDialog(this);

            if (opt == JFileChooser.APPROVE_OPTION) {
                XSSFSheet sheet = workbook.createSheet("HoaDon");
                row = sheet.createRow(0);
                cell = row.createCell(0, CellType.STRING);
                cell.setCellValue("Mã HĐ");
                cell = row.createCell(1, CellType.STRING);
                cell.setCellValue("Ngày Bán");
                cell = row.createCell(2, CellType.STRING);
                cell.setCellValue("Tên NV");
                cell = row.createCell(3, CellType.STRING);
                cell.setCellValue("Mã KH");
                cell = row.createCell(4, CellType.STRING);
                cell.setCellValue("Tổng tiền");

                for (int i = 0; i < model1.getRowCount(); i++) {
                    row = sheet.createRow(i + 1);
                    for (int j = 0; j < model1.getColumnCount(); j++) {
                        cell = row.createCell(j);
                        cell.setCellValue(model1.getValueAt(i, j).toString());
                    }
                }

                XSSFSheet sheet2 = workbook.createSheet("SanPham");
                r = sheet2.createRow(0);
                c = r.createCell(0, CellType.STRING);
                c.setCellValue("Tên LK");
                c = r.createCell(1, CellType.STRING);
                c.setCellValue("Tổng SL");
                c = r.createCell(2, CellType.STRING);
                c.setCellValue("Đơn giá");
                c = r.createCell(3, CellType.STRING);
                c.setCellValue("Thành tiền");

                for (int i = 0; i < model2.getRowCount(); i++) {
                    r = sheet2.createRow(i + 1);
                    for (int j = 0; j < model2.getColumnCount(); j++) {
                        c = r.createCell(j);
                        c.setCellValue(model2.getValueAt(i, j).toString());
                    }
                }

                out = new FileOutputStream(jFileChooser.getSelectedFile() + ".xlsx");
                bos = new BufferedOutputStream(out);
                workbook.write(bos);
                JOptionPane.showMessageDialog(this, "Thành công!");
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException io) {
            System.out.println(io);
        } finally {
            try {
                if (bos != null) {
                    bos.close();
                }
                if (out != null) {
                    out.close();
                }
                if (workbook != null) {
                    workbook.close();
                }
            } catch (Exception e) {
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpnRoot = new javax.swing.JPanel();
        jpnThongKe = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        btnThongKe = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblThongKeHD = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblThongKeSP = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblTongHD = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblTongTien = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblTongSL = new javax.swing.JLabel();
        btnPrint = new javax.swing.JButton();
        jdcFromDate = new com.toedter.calendar.JDateChooser();
        jdcToDate = new com.toedter.calendar.JDateChooser();

        setPreferredSize(new java.awt.Dimension(1130, 660));

        jpnRoot.setBackground(new java.awt.Color(255, 255, 255));
        jpnRoot.setPreferredSize(new java.awt.Dimension(1130, 660));

        jpnThongKe.setBackground(new java.awt.Color(255, 255, 255));
        jpnThongKe.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thống kê", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18), new java.awt.Color(23, 70, 162))); // NOI18N
        jpnThongKe.setFocusable(false);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Từ:");

        btnThongKe.setBackground(new java.awt.Color(95, 157, 247));
        btnThongKe.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnThongKe.setForeground(new java.awt.Color(255, 247, 233));
        btnThongKe.setText("Thống kê");
        btnThongKe.setBorderPainted(false);
        btnThongKe.setFocusPainted(false);
        btnThongKe.setMinimumSize(new java.awt.Dimension(95, 25));
        btnThongKe.setPreferredSize(new java.awt.Dimension(110, 28));
        btnThongKe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThongKeActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Theo Hóa Đơn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(23, 70, 162))); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(500, 420));

        tblThongKeHD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã HĐ", "Ngày Bán", "Người Bán", "Mã Khách", "Tổng tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblThongKeHD);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 518, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Theo Sản Phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(23, 70, 162))); // NOI18N
        jPanel2.setPreferredSize(new java.awt.Dimension(500, 420));

        tblThongKeSP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên LK", "Tổng SL", "Đơn giá", "Thành tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblThongKeSP);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 464, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Đến:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Tổng số hóa đơn:");

        lblTongHD.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTongHD.setForeground(new java.awt.Color(0, 51, 153));
        lblTongHD.setText("0");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Tổng số tiền:");

        lblTongTien.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTongTien.setForeground(new java.awt.Color(255, 51, 51));
        lblTongTien.setText("0");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 51, 51));
        jLabel7.setText("VND");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Tổng số sản phẩm đã bán ra:");

        lblTongSL.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTongSL.setForeground(new java.awt.Color(102, 0, 255));
        lblTongSL.setText("0");

        btnPrint.setBackground(new java.awt.Color(255, 115, 29));
        btnPrint.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnPrint.setForeground(new java.awt.Color(255, 247, 233));
        btnPrint.setText("In Thống kê");
        btnPrint.setBorderPainted(false);
        btnPrint.setFocusPainted(false);
        btnPrint.setPreferredSize(new java.awt.Dimension(100, 30));
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });

        jdcFromDate.setBackground(new java.awt.Color(255, 255, 255));

        jdcToDate.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jpnThongKeLayout = new javax.swing.GroupLayout(jpnThongKe);
        jpnThongKe.setLayout(jpnThongKeLayout);
        jpnThongKeLayout.setHorizontalGroup(
            jpnThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnThongKeLayout.createSequentialGroup()
                .addGroup(jpnThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnThongKeLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 554, Short.MAX_VALUE)
                        .addGap(16, 16, 16)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpnThongKeLayout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jdcFromDate, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jdcToDate, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnThongKe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpnThongKeLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(jpnThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(jpnThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTongTien)
                            .addComponent(lblTongHD))
                        .addGap(111, 111, 111)
                        .addGroup(jpnThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpnThongKeLayout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblTongSL))
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)))
                .addContainerGap())
        );
        jpnThongKeLayout.setVerticalGroup(
            jpnThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnThongKeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnThongKe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jdcFromDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jdcToDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jpnThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 451, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 451, Short.MAX_VALUE))
                .addGroup(jpnThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnThongKeLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jpnThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(lblTongHD)
                            .addComponent(jLabel4)
                            .addComponent(lblTongSL))
                        .addGap(18, 18, 18)
                        .addGroup(jpnThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(lblTongTien)
                            .addComponent(jLabel7))
                        .addContainerGap(24, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnThongKeLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26))))
        );

        javax.swing.GroupLayout jpnRootLayout = new javax.swing.GroupLayout(jpnRoot);
        jpnRoot.setLayout(jpnRootLayout);
        jpnRootLayout.setHorizontalGroup(
            jpnRootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnRootLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpnThongKe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpnRootLayout.setVerticalGroup(
            jpnRootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnRootLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpnThongKe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    private void btnThongKeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThongKeActionPerformed
        Date today = new Date();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        String hnay = s.format(today);

        if (jdcFromDate.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Chưa chọn ngày bắt đầu(Ngày kết thúc để trống = hôm nay)");
        } else {
            if (jdcFromDate.getDate().after(today)) {
                JOptionPane.showMessageDialog(this, "Chọn sai ngày bắt đầu");
            } else if (jdcToDate.getDate() == null) {
                String ngay1 = s.format(jdcFromDate.getDate());
                ThongKeHD(ngay1, hnay);
                ThongKeSP(ngay1, hnay);
                TongHD(ngay1, hnay);
                TongTienHD();
                TongSP();
            } else if (jdcFromDate.getDate().after(jdcToDate.getDate())) {
                JOptionPane.showMessageDialog(this, "Chọn ngày bắt đầu trước ngày kết thúc");
            } else {
                String ngay1 = s.format(jdcFromDate.getDate());
                String ngay2 = s.format(jdcToDate.getDate());
                ThongKeHD(ngay1, ngay2);
                ThongKeSP(ngay1, ngay2);
                TongHD(ngay1, ngay2);
                TongTienHD();
                TongSP();
            }
        }
    }//GEN-LAST:event_btnThongKeActionPerformed

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
        DefaultTableModel model=(DefaultTableModel) tblThongKeHD.getModel();
        
        if (model.getRowCount() ==0) {
            JOptionPane.showMessageDialog(this, "Chưa có dữ liệu để in");
        } else {
            InTK();
        }
    }//GEN-LAST:event_btnPrintActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPrint;
    private javax.swing.JButton btnThongKe;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private com.toedter.calendar.JDateChooser jdcFromDate;
    private com.toedter.calendar.JDateChooser jdcToDate;
    private javax.swing.JPanel jpnRoot;
    private javax.swing.JPanel jpnThongKe;
    private javax.swing.JLabel lblTongHD;
    private javax.swing.JLabel lblTongSL;
    private javax.swing.JLabel lblTongTien;
    private javax.swing.JTable tblThongKeHD;
    private javax.swing.JTable tblThongKeSP;
    // End of variables declaration//GEN-END:variables
}

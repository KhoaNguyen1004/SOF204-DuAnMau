package UI.NhanVien;

import Class.HoaDon;
import Class.SanPhamClass;
import Controller.DBConnection;

import java.awt.Color;
import java.awt.Font;
import java.awt.print.PrinterException;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

public class BanHangPanel extends javax.swing.JPanel {

    HoaDon hd = new HoaDon();
    Connection con = null;
    PreparedStatement ps = null;
    Statement st = null;
    ResultSet rs = null;
    DefaultTableModel model = null;
    List<SanPhamClass> listSP = new ArrayList<>();
    List<SanPhamClass> listGioHang = new ArrayList<>();

    public BanHangPanel() {
        initComponents();
        tableDecor();
        loadLoaiLKComboBox();
        getThanhTien();
        EventGetKHName();
        btnLamMoi.setEnabled(false);
        btnThemSP.setEnabled(false);
        btnSuaSP.setEnabled(false);
        btnXoa.setEnabled(false);
        btnHuyHD.setEnabled(false);
        btnThanhToan.setEnabled(false);
        txtSL.setEnabled(false);
        cboLoaiLk.setEnabled(false);
        cboTenLK.setEnabled(false);
    }

    void tableDecor() {
        tblGioHang.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
        tblGioHang.getTableHeader().setBackground(new Color(23, 70, 162));
        tblGioHang.getTableHeader().setForeground(Color.DARK_GRAY);
        tblGioHang.setRowHeight(30);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tblGioHang.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        tblGioHang.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);

    }

    void themSPvaoGioHang() {
        try {
            SanPhamClass sp = new SanPhamClass();
            HoaDon hd = new HoaDon();
            String malk = null;
            sp.LoaiLK = cboLoaiLk.getSelectedItem().toString();
            sp.TenLK = cboTenLK.getSelectedItem().toString();
            con = DBConnection.getConnection();
            st = con.createStatement();
            rs = st.executeQuery("select malk from sanpham where loailk=N'" + cboLoaiLk.getSelectedItem().toString()
                    + "' and tenlk=N'" + cboTenLK.getSelectedItem().toString() + "'");
            if (rs.next()) {
                malk = rs.getString("malk");
            }
            int soluong = hd.getSoluong(malk);
            int soluongMoi = Integer.parseInt(txtSL.getText());
            if (soluongMoi > soluong) {
                JOptionPane.showMessageDialog(this, "Không đủ hàng trong kho!");
                return;
            } else {
                if (listGioHang.isEmpty()) {
                    sp.MaLK = malk;
                    String replacegia = txtGia1.getText().replaceAll("[^0-9]", "").trim();
                    sp.Gia = Float.parseFloat(replacegia);
                    sp.SoLuong = Integer.parseInt(txtSL.getText());
                    sp.BaoHanh = Integer.parseInt(txtBaoHanh.getText());
                    listGioHang.add(sp);
                    JOptionPane.showMessageDialog(this, "Thêm thành công!");
                    fillTableSP();
                    tinhTongTien();
                    clearForm();

                } else {
                    for (int i = 0; i < listGioHang.size(); i++) {
                        if (cboTenLK.getSelectedItem().equals(listGioHang.get(i).TenLK)) {
                            if (soluongMoi > soluong - listGioHang.get(i).SoLuong) {
                                JOptionPane.showMessageDialog(this, "Không đủ hàng trong kho!");
                                return;
                            } else {
                                listGioHang.get(i).SoLuong += Integer.parseInt(txtSL.getText());
                                JOptionPane.showMessageDialog(this, "Thêm thành công!");
                                fillTableSP();
                                tinhTongTien();
                                clearForm();
                                return;
                            }
                        }
                    }
                    sp.MaLK = malk;
                    String replacegia = txtGia1.getText().replaceAll("[^0-9]", "").trim();
                    sp.Gia = Float.parseFloat(replacegia);
                    sp.SoLuong = Integer.parseInt(txtSL.getText());
                    sp.BaoHanh = Integer.parseInt(txtBaoHanh.getText());
                    listGioHang.add(sp);
                    JOptionPane.showMessageDialog(this, "Thêm thành công!");
                    fillTableSP();
                    tinhTongTien();
                    clearForm();
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(BanHangPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void updateSPtrongGioHang() {
        int index = tblGioHang.getSelectedRow();
        if (index > -1) {
            SanPhamClass sp = listGioHang.get(index);
            if (listGioHang.get(index).getSoLuong() == Integer.parseInt(txtSL.getText())) {
                JOptionPane.showMessageDialog(null, "Giá trị không đổi!");
                return;
            } else {
                sp.SoLuong = Integer.parseInt(txtSL.getText());
                if (listGioHang.get(index).getSoLuong() == 0) {
                    listGioHang.remove(index);
                }
                JOptionPane.showMessageDialog(this, "Đã cập nhật!");
            }
            fillTableSP();
            tinhTongTien();
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm muốn sửa!");
        }

    }

    void deleteSPtrongGioHang() {
        int index = tblGioHang.getSelectedRow();
        listGioHang.remove(index);
        JOptionPane.showMessageDialog(null, "Xóa sản phẩm thành công!");
        fillTableSP();
        tinhTongTien();

    }

    void loadLoaiLKComboBox() {

        try {
            con = DBConnection.getConnection();
            st = con.createStatement();
            rs = st.executeQuery("select loailk from sanpham group by loailk");
            while (rs.next()) {
                cboLoaiLk.addItem(rs.getString(1));
            }

        } catch (SQLException ex) {
            Logger.getLogger(BanHangPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    void getTenLKfromLoaiLK() {
        try {

            String loai = cboLoaiLk.getSelectedItem().toString();
            con = DBConnection.getConnection();
            st = con.createStatement();
            rs = st.executeQuery("select tenlk from sanpham where loailk=N'" + loai + "'");
            DefaultComboBoxModel tenmodel = new DefaultComboBoxModel();
            while (rs.next()) {
                String tenlk = rs.getString("tenlk");
                tenmodel.addElement(tenlk);
            }
            cboTenLK.setModel(tenmodel);
            rs.close();
            st.close();
            con.close();

        } catch (SQLException ex) {
            Logger.getLogger(BanHangPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    void getItemfromTenLK() {
        try {
            con = DBConnection.getConnection();
            st = con.createStatement();
            String loailk = cboLoaiLk.getSelectedItem().toString();
            String tenlk = cboTenLK.getSelectedItem().toString();
            rs = st.executeQuery("select * from sanpham where loailk=N'" + loailk + "' and tenlk=N'" + tenlk + "'");
            while (rs.next()) {
                txtBaoHanh.setText(String.valueOf(rs.getInt("baohanh")));
                float gia = rs.getFloat("gia");
                String fm = new DecimalFormat("#,###").format(gia);
                txtGia1.setText(fm);
            }
            rs.close();
            st.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(BanHangPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void getThanhTien() {

        txtSL.getDocument().addDocumentListener(new DocumentListener() {
            String pattern = "[0-9]+";

            @Override
            public void insertUpdate(DocumentEvent e) {

                if (txtSL.getText().trim().length() == 0) {
                    txtThanhTien.setText("");
                } else {
                    String replacegia = "";
                    if (txtSL.getText().matches(pattern)) {
                        replacegia = txtGia1.getText().replaceAll("[^0-9]", "").trim();
                        float tt = Float.parseFloat(replacegia) * Integer.parseInt(txtSL.getText());
                        String fm = new DecimalFormat("#,###").format(tt);
                        txtThanhTien.setText(String.valueOf(fm));
                    } else {
                        txtThanhTien.setText("");
                    }
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {

                if (txtSL.getText().trim().length() == 0 || !txtSL.getText().matches(pattern)) {
                    txtThanhTien.setText("");
                } else {
                    String replacegia = txtGia1.getText().replaceAll("[^0-9]", "").trim();
                    float tt = Float.parseFloat(replacegia) * Integer.parseInt(txtSL.getText());
                    String fm = new DecimalFormat("#,###").format(tt);
                    txtThanhTien.setText(String.valueOf(fm));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if (txtSL.getText().trim().length() == 0 || !txtSL.getText().equals(pattern)) {
                    txtThanhTien.setText("");
                } else {
                    String replacegia = txtGia1.getText().replaceAll("[^0-9]", "").trim();
                    float tt = Float.parseFloat(replacegia) * Integer.parseInt(txtSL.getText());
                    String fm = new DecimalFormat("#,###").format(tt);
                    txtThanhTien.setText(String.valueOf(fm));
                }
            }

        });
    }

    void clearForm() {
        cboLoaiLk.setSelectedIndex(0);
        //cboTenLK.setSelectedIndex(0);
        txtBaoHanh.setText("");
        txtThanhTien.setText("");
        txtSL.setText("");
        txtGia1.setText("");
        tblGioHang.clearSelection();
        cboLoaiLk.setEnabled(true);
        cboTenLK.setEnabled(true);
        txtSL.setEnabled(false);
    }

    void clearAll() {
        lblMaHD.setText("");
        lblMaNguoiBan.setText("");
        lblNgayTaoHD.setText("");
        lblThanhTien.setText("0");
        lblTongTien.setText("0");
        txtKHNumber.setText("");
        lblNameKH.setText("");
        listGioHang.clear();
        fillTableSP();
    }

    void fillTableSP() {
        DefaultTableModel model = (DefaultTableModel) tblGioHang.getModel();
        model.setRowCount(0);
        //Duyệt list bổ sung vào table
        for (SanPhamClass sp : listGioHang) {
            Object[] row = new Object[]{sp.getMaLK(), sp.getTenLK(), sp.getBaoHanh(),
                sp.getSoLuong(), sp.getGia(), sp.getSoLuong() * sp.getGia()};
            model.addRow(row);
        }

    }

    void fillGioHangToForm(int index) {
        cboLoaiLk.setSelectedItem(listGioHang.get(index).getLoaiLK());
        cboTenLK.setSelectedItem(listGioHang.get(index).getTenLK());
        txtBaoHanh.setText(String.valueOf(listGioHang.get(index).getBaoHanh()));
        txtGia1.setText(String.valueOf(listGioHang.get(index).getGia()));
        txtSL.setText(String.valueOf(listGioHang.get(index).getSoLuong()));

    }

    void addHoaDon() {
        try {
            String makh = "";
            con = DBConnection.getConnection();
            st = con.createStatement();
            rs = st.executeQuery("select makh from khachhang where sdt='" + txtKHNumber.getText() + "'");
            if (rs.next()) {
                makh = rs.getString(1);
            }
            ps = con.prepareStatement("insert into hoadon values(?,?,?,?)");
            ps.setString(2, lblMaNguoiBan.getText());
            ps.setDate(1, java.sql.Date.valueOf(lblNgayTaoHD.getText()));
            String replacenum = lblThanhTien.getText().replaceAll("[^0-9]", "").trim();
            ps.setFloat(3, Float.parseFloat(replacenum));
            ps.setString(4, makh);
            ps.executeUpdate();
            ps.close();
            con.close();
            fillTableSP();
        } catch (SQLException ex) {
            Logger.getLogger(BanHangPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    void tinhTongTien() {
        float tt = 0;
        model = (DefaultTableModel) tblGioHang.getModel();
        if (model.getColumnCount() > 0) {
            for (int i = 0; i < model.getRowCount(); i++) {
                tt += Float.parseFloat(model.getValueAt(i, 4).toString()) * Integer.parseInt(model.getValueAt(i, 3).toString());
            }
            String fms = new DecimalFormat("#,###").format(tt);
            lblTongTien.setText(fms);
            float thanhtien = 0;
            thanhtien = tt - (tt * Integer.parseInt(lblGiamGia.getText()) / 100);
            String fm = new DecimalFormat("#,###").format(thanhtien);
            lblThanhTien.setText(fm);
        }
    }

    void addHDCT() {
        model = (DefaultTableModel) tblGioHang.getModel();
        if (model.getRowCount() > 0) {
            int mahd = Integer.parseInt(lblMaHD.getText());
            for (int i = 0; i < model.getRowCount(); i++) {
                String masp = model.getValueAt(i, 0).toString();
                String tensp = model.getValueAt(i, 1).toString();
                float dongia = Float.parseFloat(model.getValueAt(i, 4).toString());
                int sl = Integer.parseInt(model.getValueAt(i, 3).toString());
                hd.addHDCT(mahd, tensp, masp, sl, dongia);
                int newSL = hd.getSoluong(masp) - sl;
                System.out.println(newSL);
                hd.updateSoluong(masp, newSL);
                fillTableSP();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Giỏ hàng trống!");
            return;
        }
    }

    void getKhachHang() {
        try {
            con = DBConnection.getConnection();
            st = con.createStatement();
            String sdt = txtKHNumber.getText();
            rs = st.executeQuery("select hoten,rankkh from khachhang kh,rankkhachhang rkh where kh.makh=rkh.makh and SDT='" + sdt + "'");
            if (rs.next()) {
                lblRank.setText(rs.getString(2));
                lblNameKH.setText(rs.getString(1));
            }

        } catch (SQLException ex) {
            Logger.getLogger(BanHangPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void EventGetKHName() {
        txtKHNumber.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                lblNameKH.setText("Không tìm thấy");
                getKhachHang();
                if (lblRank.getText().equals("Đồng")) {
                    lblGiamGia.setText("0");
                } else if (lblRank.getText().equals("Bạc")) {
                    lblGiamGia.setText("5");
                } else if (lblRank.getText().equals("Vàng")) {
                    lblGiamGia.setText("7");
                } else if (lblRank.getText().equals("Bạch kim")) {
                    lblGiamGia.setText("10");
                } else if (lblRank.getText().equals("Kim cương")) {
                    lblGiamGia.setText("12");
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                getKhachHang();
                lblNameKH.setText("Không tìm thấy");
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                lblNameKH.setText("Không tìm thấy");
                getKhachHang();

            }

        });
    }

    boolean validatesSP() {
        String checkNum = "[1-9]+";
        if (txtBaoHanh.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm!");
            return false;
        } else {
            if (!txtSL.getText().matches(checkNum)) {
                JOptionPane.showMessageDialog(this, "Giá trị số lượng không hợp lệ");
                return false;
            }
        }
        return true;
    }

    boolean validateHD() {
        if (listGioHang.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Giỏ hàng trống!");
            return false;
        }
        if (lblNameKH.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập thông tin khách hàng!");
            txtKHNumber.requestFocus();
            txtKHNumber.setBackground(Color.yellow);
            return false;
        }
        if (lblNameKH.getText() == "Không tìm thấy") {
            JOptionPane.showMessageDialog(this, "Vui lòng thêm mới thông tin khách hàng!");
            btnAddKH.requestFocus();
            return false;
        }
        return true;
    }

    void printHD() {
        try {
            String tongtien = lblTongTien.getText();
            MessageFormat header = new MessageFormat("Receipt: " + lblMaHD.getText() + "          Tổng hóa đơn:" + tongtien + "VNĐ");
            MessageFormat footer = new MessageFormat("Page{0,number,integer}");
            tblGioHang.print(JTable.PrintMode.FIT_WIDTH, header, footer);
        } catch (PrinterException ex) {
            Logger.getLogger(BanHangPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();
        jpnRoot = new javax.swing.JPanel();
        jpnThongTinSanPham = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtThanhTien = new javax.swing.JTextField();
        txtSL = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtBaoHanh = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        btnLamMoi = new javax.swing.JButton();
        btnThemSP = new javax.swing.JButton();
        btnSuaSP = new javax.swing.JButton();
        cboLoaiLk = new javax.swing.JComboBox<>();
        cboTenLK = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        txtGia1 = new javax.swing.JTextField();
        btnXoa = new javax.swing.JButton();
        jpnGioHang = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblGioHang = new javax.swing.JTable();
        jpnThanhToan = new javax.swing.JPanel();
        lblMaNguoiBan = new javax.swing.JLabel();
        lblMaHD = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        lblTongTien = new javax.swing.JLabel();
        btnTaoHD = new javax.swing.JButton();
        btnHuyHD = new javax.swing.JButton();
        btnThanhToan = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lblNgayTaoHD = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtKHNumber = new javax.swing.JTextField();
        lblNameKH = new javax.swing.JLabel();
        btnAddKH = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        lblThanhTien = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        lblGiamGia = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        lblRank = new javax.swing.JLabel();

        jScrollPane1.setViewportView(jEditorPane1);

        setPreferredSize(new java.awt.Dimension(1130, 660));

        jpnRoot.setBackground(new java.awt.Color(255, 255, 255));
        jpnRoot.setPreferredSize(new java.awt.Dimension(1130, 660));

        jpnThongTinSanPham.setBackground(new java.awt.Color(255, 255, 255));
        jpnThongTinSanPham.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18), new java.awt.Color(23, 70, 162))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Loại linh kiện:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Tên linh kiện:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Giá linh kiện:");

        txtThanhTien.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtThanhTien.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtThanhTien.setEnabled(false);
        txtThanhTien.setPreferredSize(new java.awt.Dimension(180, 30));

        txtSL.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtSL.setPreferredSize(new java.awt.Dimension(180, 30));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Số lượng:");

        txtBaoHanh.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtBaoHanh.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtBaoHanh.setEnabled(false);
        txtBaoHanh.setPreferredSize(new java.awt.Dimension(180, 30));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Bảo hành (Tháng):");

        btnLamMoi.setBackground(new java.awt.Color(95, 157, 247));
        btnLamMoi.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnLamMoi.setForeground(new java.awt.Color(255, 247, 233));
        btnLamMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/clear-format.png"))); // NOI18N
        btnLamMoi.setText("Làm mới");
        btnLamMoi.setBorderPainted(false);
        btnLamMoi.setFocusPainted(false);
        btnLamMoi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnLamMoi.setPreferredSize(new java.awt.Dimension(180, 35));
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        btnThemSP.setBackground(new java.awt.Color(95, 157, 247));
        btnThemSP.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnThemSP.setForeground(new java.awt.Color(255, 247, 233));
        btnThemSP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/plus (1).png"))); // NOI18N
        btnThemSP.setText("Thêm");
        btnThemSP.setBorderPainted(false);
        btnThemSP.setFocusPainted(false);
        btnThemSP.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnThemSP.setPreferredSize(new java.awt.Dimension(110, 35));
        btnThemSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemSPActionPerformed(evt);
            }
        });

        btnSuaSP.setBackground(new java.awt.Color(255, 115, 29));
        btnSuaSP.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnSuaSP.setForeground(new java.awt.Color(255, 247, 233));
        btnSuaSP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/pen.png"))); // NOI18N
        btnSuaSP.setText("Sửa");
        btnSuaSP.setBorderPainted(false);
        btnSuaSP.setFocusPainted(false);
        btnSuaSP.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnSuaSP.setPreferredSize(new java.awt.Dimension(110, 35));
        btnSuaSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaSPActionPerformed(evt);
            }
        });

        cboLoaiLk.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cboLoaiLk.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chọn loại" }));
        cboLoaiLk.setPreferredSize(new java.awt.Dimension(180, 30));
        cboLoaiLk.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboLoaiLkItemStateChanged(evt);
            }
        });
        cboLoaiLk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboLoaiLkActionPerformed(evt);
            }
        });

        cboTenLK.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cboTenLK.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chọn Tên" }));
        cboTenLK.setPreferredSize(new java.awt.Dimension(180, 30));
        cboTenLK.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboTenLKItemStateChanged(evt);
            }
        });
        cboTenLK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTenLKActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Thành tiền:");

        txtGia1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtGia1.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtGia1.setEnabled(false);
        txtGia1.setPreferredSize(new java.awt.Dimension(180, 30));

        btnXoa.setBackground(new java.awt.Color(255, 115, 29));
        btnXoa.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnXoa.setForeground(new java.awt.Color(255, 247, 233));
        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/pen.png"))); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.setBorderPainted(false);
        btnXoa.setFocusPainted(false);
        btnXoa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnXoa.setPreferredSize(new java.awt.Dimension(110, 35));
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpnThongTinSanPhamLayout = new javax.swing.GroupLayout(jpnThongTinSanPham);
        jpnThongTinSanPham.setLayout(jpnThongTinSanPhamLayout);
        jpnThongTinSanPhamLayout.setHorizontalGroup(
            jpnThongTinSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnThongTinSanPhamLayout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(jpnThongTinSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jpnThongTinSanPhamLayout.createSequentialGroup()
                        .addGroup(jpnThongTinSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpnThongTinSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSL, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jpnThongTinSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(cboLoaiLk, 0, 130, Short.MAX_VALUE)
                                .addComponent(txtBaoHanh, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)))
                        .addGap(60, 60, 60)
                        .addGroup(jpnThongTinSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpnThongTinSanPhamLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cboTenLK, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jpnThongTinSanPhamLayout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtGia1, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jpnThongTinSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnThongTinSanPhamLayout.createSequentialGroup()
                                    .addGap(94, 94, 94)
                                    .addComponent(txtThanhTien, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jLabel7))))
                    .addGroup(jpnThongTinSanPhamLayout.createSequentialGroup()
                        .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnThemSP, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSuaSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70)))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jpnThongTinSanPhamLayout.setVerticalGroup(
            jpnThongTinSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnThongTinSanPhamLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jpnThongTinSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(cboLoaiLk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboTenLK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jpnThongTinSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBaoHanh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel4)
                    .addComponent(txtGia1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jpnThongTinSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7)
                    .addComponent(txtThanhTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(jpnThongTinSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThemSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSuaSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jpnGioHang.setBackground(new java.awt.Color(255, 255, 255));
        jpnGioHang.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Giỏ hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18), new java.awt.Color(23, 70, 162))); // NOI18N
        jpnGioHang.setPreferredSize(new java.awt.Dimension(733, 300));

        tblGioHang.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblGioHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã LK", "Tên LK", "Bảo hành", "Số lượng", "Đơn giá", "Tổng tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblGioHang.setPreferredSize(new java.awt.Dimension(285, 300));
        tblGioHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblGioHangMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblGioHang);
        if (tblGioHang.getColumnModel().getColumnCount() > 0) {
            tblGioHang.getColumnModel().getColumn(0).setPreferredWidth(30);
            tblGioHang.getColumnModel().getColumn(1).setPreferredWidth(150);
            tblGioHang.getColumnModel().getColumn(2).setPreferredWidth(30);
            tblGioHang.getColumnModel().getColumn(3).setPreferredWidth(30);
        }

        javax.swing.GroupLayout jpnGioHangLayout = new javax.swing.GroupLayout(jpnGioHang);
        jpnGioHang.setLayout(jpnGioHangLayout);
        jpnGioHangLayout.setHorizontalGroup(
            jpnGioHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
        );
        jpnGioHangLayout.setVerticalGroup(
            jpnGioHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        jpnThanhToan.setBackground(new java.awt.Color(255, 255, 255));
        jpnThanhToan.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thanh toán", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18), new java.awt.Color(23, 70, 162))); // NOI18N
        jpnThanhToan.setPreferredSize(new java.awt.Dimension(376, 578));

        lblMaNguoiBan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblMaNguoiBan.setForeground(new java.awt.Color(255, 115, 29));
        lblMaNguoiBan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMaNguoiBan.setPreferredSize(new java.awt.Dimension(150, 25));

        lblMaHD.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblMaHD.setForeground(new java.awt.Color(255, 115, 29));
        lblMaHD.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMaHD.setPreferredSize(new java.awt.Dimension(150, 25));

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel14.setText("Tổng tiền:");

        lblTongTien.setBackground(new java.awt.Color(255, 115, 29));
        lblTongTien.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTongTien.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTongTien.setText("0");
        lblTongTien.setPreferredSize(new java.awt.Dimension(150, 25));

        btnTaoHD.setBackground(new java.awt.Color(95, 157, 247));
        btnTaoHD.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnTaoHD.setForeground(new java.awt.Color(255, 247, 233));
        btnTaoHD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/plus (1).png"))); // NOI18N
        btnTaoHD.setText("Tạo Hóa Đơn");
        btnTaoHD.setBorderPainted(false);
        btnTaoHD.setFocusPainted(false);
        btnTaoHD.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnTaoHD.setPreferredSize(new java.awt.Dimension(160, 35));
        btnTaoHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaoHDActionPerformed(evt);
            }
        });

        btnHuyHD.setBackground(new java.awt.Color(255, 115, 29));
        btnHuyHD.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnHuyHD.setForeground(new java.awt.Color(255, 247, 233));
        btnHuyHD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/delete.png"))); // NOI18N
        btnHuyHD.setText("Hủy Hóa Đơn");
        btnHuyHD.setBorderPainted(false);
        btnHuyHD.setFocusPainted(false);
        btnHuyHD.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnHuyHD.setPreferredSize(new java.awt.Dimension(160, 35));
        btnHuyHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyHDActionPerformed(evt);
            }
        });

        btnThanhToan.setBackground(new java.awt.Color(23, 70, 162));
        btnThanhToan.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btnThanhToan.setForeground(new java.awt.Color(255, 247, 233));
        btnThanhToan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/ecommerce (1).png"))); // NOI18N
        btnThanhToan.setText("Thanh Toán");
        btnThanhToan.setBorderPainted(false);
        btnThanhToan.setFocusPainted(false);
        btnThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThanhToanActionPerformed(evt);
            }
        });

        jLabel16.setBackground(new java.awt.Color(255, 115, 29));
        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel16.setText("VNĐ");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("Mã người bán:");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("Mã hóa đơn:");

        lblNgayTaoHD.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblNgayTaoHD.setForeground(new java.awt.Color(255, 115, 29));
        lblNgayTaoHD.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNgayTaoHD.setPreferredSize(new java.awt.Dimension(150, 25));

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("Ngày bán:");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setText("Khách hàng:");

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel15.setText("Tên KH:");

        txtKHNumber.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtKHNumber.setPreferredSize(new java.awt.Dimension(7, 30));

        lblNameKH.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblNameKH.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNameKH.setPreferredSize(new java.awt.Dimension(150, 25));

        btnAddKH.setBackground(new java.awt.Color(23, 70, 162));
        btnAddKH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/plus (1).png"))); // NOI18N
        btnAddKH.setBorderPainted(false);
        btnAddKH.setFocusPainted(false);
        btnAddKH.setPreferredSize(new java.awt.Dimension(30, 30));
        btnAddKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddKHActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel17.setText("Giảm giá (%):");

        jLabel18.setBackground(new java.awt.Color(255, 115, 29));
        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel18.setText("%");

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel19.setText("Thành tiền:");

        lblThanhTien.setBackground(new java.awt.Color(255, 115, 29));
        lblThanhTien.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblThanhTien.setForeground(new java.awt.Color(255, 115, 29));
        lblThanhTien.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblThanhTien.setText("0");
        lblThanhTien.setPreferredSize(new java.awt.Dimension(150, 25));

        jLabel20.setBackground(new java.awt.Color(255, 115, 29));
        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 115, 29));
        jLabel20.setText("VNĐ");

        lblGiamGia.setBackground(new java.awt.Color(255, 115, 29));
        lblGiamGia.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblGiamGia.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblGiamGia.setText("0");
        lblGiamGia.setPreferredSize(new java.awt.Dimension(150, 25));

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel21.setText("Hạng KH:");

        lblRank.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblRank.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblRank.setPreferredSize(new java.awt.Dimension(150, 25));

        javax.swing.GroupLayout jpnThanhToanLayout = new javax.swing.GroupLayout(jpnThanhToan);
        jpnThanhToan.setLayout(jpnThanhToanLayout);
        jpnThanhToanLayout.setHorizontalGroup(
            jpnThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnThanhToanLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jpnThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jpnThanhToanLayout.createSequentialGroup()
                        .addComponent(btnTaoHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnHuyHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(jpnThanhToanLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jpnThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnThanhToanLayout.createSequentialGroup()
                        .addGroup(jpnThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12)
                            .addComponent(jLabel15)
                            .addComponent(jLabel21))
                        .addGap(20, 20, 20)
                        .addGroup(jpnThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblMaHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblMaNguoiBan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblNgayTaoHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtKHNumber, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblNameKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblRank, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpnThanhToanLayout.createSequentialGroup()
                        .addGroup(jpnThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17)
                            .addComponent(jLabel14)
                            .addComponent(jLabel19))
                        .addGap(18, 18, 18)
                        .addGroup(jpnThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblThanhTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblGiamGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpnThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel20)
                            .addComponent(jLabel16)
                            .addComponent(jLabel18))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpnThanhToanLayout.setVerticalGroup(
            jpnThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnThanhToanLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jpnThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMaNguoiBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jpnThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jpnThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblNgayTaoHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(16, 16, 16)
                .addGroup(jpnThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtKHNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnAddKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jpnThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNameKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jpnThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblRank, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(97, 97, 97)
                .addGroup(jpnThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addGap(18, 18, 18)
                .addGroup(jpnThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel18)
                    .addComponent(lblGiamGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jpnThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jpnThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblThanhTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel20)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpnThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnHuyHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTaoHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43))
        );

        javax.swing.GroupLayout jpnRootLayout = new javax.swing.GroupLayout(jpnRoot);
        jpnRoot.setLayout(jpnRootLayout);
        jpnRootLayout.setHorizontalGroup(
            jpnRootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnRootLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnRootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jpnThongTinSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpnGioHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpnThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2))
        );
        jpnRootLayout.setVerticalGroup(
            jpnRootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnRootLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnRootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jpnThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 634, Short.MAX_VALUE)
                    .addGroup(jpnRootLayout.createSequentialGroup()
                        .addComponent(jpnThongTinSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jpnGioHang, javax.swing.GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE)))
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
            .addComponent(jpnRoot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        getAccessibleContext().setAccessibleName("");
        getAccessibleContext().setAccessibleDescription("");
    }// </editor-fold>//GEN-END:initComponents

    private void btnTaoHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaoHDActionPerformed
        // TODO add your handling code here:
        try {
            con = DBConnection.getConnection();
            String tennv = MainJFramesNV.lblTenNv.getText();
            ps = con.prepareStatement("select userid from acclogin,nhanvien where userid=manv and tennv=N'" + tennv + "'");
            rs = ps.executeQuery();
            if (rs.next()) {
                lblMaNguoiBan.setText(rs.getString(1));
            }
        } catch (Exception e) {
        }
        lblMaHD.setText(String.valueOf(hd.getMaxRow()));
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formatDate = sdf.format(date);
        lblNgayTaoHD.setText(formatDate);
        btnLamMoi.setEnabled(true);
        btnThemSP.setEnabled(true);
        btnSuaSP.setEnabled(true);
        btnXoa.setEnabled(true);
        btnHuyHD.setEnabled(true);
        btnTaoHD.setEnabled(false);
        btnThanhToan.setEnabled(true);
        cboLoaiLk.setEnabled(true);
        cboTenLK.setEnabled(true);
    }//GEN-LAST:event_btnTaoHDActionPerformed

    private void btnHuyHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyHDActionPerformed
        // TODO add your handling code here:
        lblMaHD.setText("");
        lblMaNguoiBan.setText("");
        lblNgayTaoHD.setText("");
        btnLamMoi.setEnabled(false);
        btnThemSP.setEnabled(false);
        btnSuaSP.setEnabled(false);
        btnXoa.setEnabled(false);
        btnHuyHD.setEnabled(false);
        btnTaoHD.setEnabled(true);
        btnThanhToan.setEnabled(false);
        cboLoaiLk.setEnabled(false);
        cboTenLK.setEnabled(false);
        txtSL.setEnabled(false);
        lblNameKH.setText("");
        txtKHNumber.setText("");
        lblThanhTien.setText("0");
        lblTongTien.setText("0");
        listGioHang.clear();
        clearForm();
        fillTableSP();
    }//GEN-LAST:event_btnHuyHDActionPerformed

    private void cboTenLKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTenLKActionPerformed
        // TODO add your handling code here:
        txtGia1.setText("");
        txtBaoHanh.setText("");
        txtSL.setText("");
        txtSL.setEnabled(true);
        getItemfromTenLK();
    }//GEN-LAST:event_cboTenLKActionPerformed

    private void cboLoaiLkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboLoaiLkActionPerformed
        // TODO add your handling code here:
        txtGia1.setText("");
        txtBaoHanh.setText("");
        txtSL.setText("");
        getTenLKfromLoaiLK();
    }//GEN-LAST:event_cboLoaiLkActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        // TODO add your handling code here:
        clearForm();
        txtSL.setEnabled(false);
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void btnAddKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddKHActionPerformed
        // TODO add your handling code here:
        KhachHang kh = new KhachHang();
        kh.setVisible(true);
    }//GEN-LAST:event_btnAddKHActionPerformed

    private void btnThemSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemSPActionPerformed
        // TODO add your handling code here:
        if (validatesSP()) {
            themSPvaoGioHang();
        }
    }//GEN-LAST:event_btnThemSPActionPerformed

    private void tblGioHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblGioHangMouseClicked
        // TODO add your handling code here:
        int index = tblGioHang.getSelectedRow();
        if (index < 0) {
            cboLoaiLk.setEnabled(true);
            cboTenLK.setEnabled(true);
        } else {
            fillGioHangToForm(index);
            cboLoaiLk.setEnabled(false);
            cboTenLK.setEnabled(false);
        }


    }//GEN-LAST:event_tblGioHangMouseClicked

    private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhToanActionPerformed
//        int kq = JOptionPane.showConfirmDialog(this, "Bạn có muốn in hóa đơn", "In hóa đơn", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
//        if (kq == JOptionPane.YES_OPTION) {
//            try {
//                // Class.forName("jdbc:sqlserver://localhost:1433;databaseName=Nhom2_DuAnMau;"+ "encrypt=true;trustServerCertificate=true;);
//                // Class.forName("jdbc:sqlserver://localhost:1433");
//                //con = DBConnection.getConnection();
//                con = DBConnection.getConnection();
//                JasperDesign jdesign = JRXmlLoader.load("C:\\Users\\Nguyen Dung\\OneDrive\\Máy tính\\Nhom2-DAM\\Nhom2_DAM_QLPK\\src\\report\\report1.jrxml");
//                String query = "select * from SanPham,khachhang,hoadon,HDCT,nhanvien";
//                JRDesignQuery updateQuery = new JRDesignQuery();
//                updateQuery.setText(query);
//
//                jdesign.setQuery(updateQuery);
//
//                JasperReport jreport = JasperCompileManager.compileReport(jdesign);
//                JasperPrint jprint = JasperFillManager.fillReport(jreport, null, con);
//                JasperExportManager.exportReportToPdfFile(jprint, "C:\\Users\\Nguyen Dung\\OneDrive\\Máy tính\\Nhom2-DAM\\Nhom2_DAM_QLPK\\src\\report\\report1.pdf");
//                //JasperViewer.viewReport(jprint);
//
//            } catch (JRException ex) {
//                Logger.getLogger(BanHangPanel.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (SQLException ex) {
//                Logger.getLogger(BanHangPanel.class.getName()).log(Level.SEVERE, null, ex);
//            }
//
//        } else {
//            addHoaDon();
//            addHDCT();
//        }
//        if (validateHD()) {
//            addHoaDon();
//            addHDCT();
//            clearAll();
//            btnLamMoi.setEnabled(false);
//            btnThemSP.setEnabled(false);
//            btnSuaSP.setEnabled(false);
//            btnXoa.setEnabled(false);
//            btnHuyHD.setEnabled(false);
//            btnTaoHD.setEnabled(true);
//            btnThanhToan.setEnabled(false);
//        }
        if (validateHD()) {
            JOptionPane.showMessageDialog(this, "Thanh toán thành công!");
            int kq = JOptionPane.showConfirmDialog(this, "Bạn có muốn in hóa đơn", "In hóa đơn", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (kq == JOptionPane.YES_OPTION) {
                printHD();
                addHoaDon();
                addHDCT();
                clearAll();
                btnLamMoi.setEnabled(false);
                btnThemSP.setEnabled(false);
                btnSuaSP.setEnabled(false);
                btnXoa.setEnabled(false);
                btnHuyHD.setEnabled(false);
                btnTaoHD.setEnabled(true);
                btnThanhToan.setEnabled(false);
            } else {
                addHoaDon();
                addHDCT();
                clearAll();
                btnLamMoi.setEnabled(false);
                btnThemSP.setEnabled(false);
                btnSuaSP.setEnabled(false);
                btnXoa.setEnabled(false);
                btnHuyHD.setEnabled(false);
                btnTaoHD.setEnabled(true);
                btnThanhToan.setEnabled(false);
            }
        }
    }//GEN-LAST:event_btnThanhToanActionPerformed

    private void btnSuaSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaSPActionPerformed
        // TODO add your handling code here:
        if (validatesSP()) {
            updateSPtrongGioHang();
        }
    }//GEN-LAST:event_btnSuaSPActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        deleteSPtrongGioHang();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void cboLoaiLkItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboLoaiLkItemStateChanged
        // TODO add your handling code here:

    }//GEN-LAST:event_cboLoaiLkItemStateChanged

    private void cboTenLKItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboTenLKItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cboTenLKItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddKH;
    private javax.swing.JButton btnHuyHD;
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnSuaSP;
    private javax.swing.JButton btnTaoHD;
    private javax.swing.JButton btnThanhToan;
    private javax.swing.JButton btnThemSP;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cboLoaiLk;
    private javax.swing.JComboBox<String> cboTenLK;
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel jpnGioHang;
    private javax.swing.JPanel jpnRoot;
    private javax.swing.JPanel jpnThanhToan;
    private javax.swing.JPanel jpnThongTinSanPham;
    private javax.swing.JLabel lblGiamGia;
    private javax.swing.JLabel lblMaHD;
    private javax.swing.JLabel lblMaNguoiBan;
    private javax.swing.JLabel lblNameKH;
    private javax.swing.JLabel lblNgayTaoHD;
    private javax.swing.JLabel lblRank;
    private javax.swing.JLabel lblThanhTien;
    private javax.swing.JLabel lblTongTien;
    private javax.swing.JTable tblGioHang;
    private javax.swing.JTextField txtBaoHanh;
    private javax.swing.JTextField txtGia1;
    public static javax.swing.JTextField txtKHNumber;
    private javax.swing.JTextField txtSL;
    private javax.swing.JTextField txtThanhTien;
    // End of variables declaration//GEN-END:variables

}

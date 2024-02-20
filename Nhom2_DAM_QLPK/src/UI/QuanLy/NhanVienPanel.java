package UI.QuanLy;

import Class.NhanVienClass;
import Controller.DBConnection;
import UI.Login.FormSighUpFrame;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class NhanVienPanel extends javax.swing.JPanel {

    Connection con = null;
    PreparedStatement ps = null;
    Statement st = null;
    ResultSet rs = null;
    DefaultTableModel model = null;
    String tenHinhAnh = null;
    private int index = -1;
    String strFind = "";
    Vector data = null;
    List<NhanVienClass> listNV = new ArrayList<>();

    public NhanVienPanel() {
        initComponents();
        loadData();
        tableDecor();
        btnTaiKhoanDN.setEnabled(false);
    }

    void tableDecor() {
        tblNhanVien.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
        tblNhanVien.getTableHeader().setBackground(new Color(23, 70, 162));
        tblNhanVien.getTableHeader().setForeground(Color.DARK_GRAY);
        tblNhanVien.setRowHeight(30);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tblNhanVien.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);

    }

    void loadData() {
        try {
            con = DBConnection.getConnection();
            st = con.createStatement();
            listNV.clear();
            rs = st.executeQuery("select * from nhanvien");
            while (rs.next()) {
                NhanVienClass nv = new NhanVienClass();
                nv.setMaNV(rs.getString("manv"));
                nv.setTenNV(rs.getString("tennv"));
                nv.setGioiTinh(rs.getString("gioitinh"));
                nv.setSDT(rs.getString("sdt"));
                nv.setEmail(rs.getString("email"));
                nv.setDiaChi(rs.getString("diachi"));
                nv.setNamSinh(rs.getInt("namsinh"));
                nv.setChucVu(rs.getString("Chucvu"));
                nv.setTinhtrang(rs.getString("tinhtrang"));
                nv.setHinh(rs.getString("hinh"));
                listNV.add(nv);
            }
            model = (DefaultTableModel) tblNhanVien.getModel();
            model.setRowCount(0);
            for (NhanVienClass nv : listNV) {
                Object[] row = new Object[]{
                    nv.getMaNV(), nv.getTenNV(), nv.IsGioiTinh(), nv.getChucVu(), nv.getTinhtrang()
                };
                model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void themNV() {
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("insert into nhanvien values(?,?,?,?,?,?,?,?,?,?)");
            ps.setString(1, txtMaNV.getText());
            ps.setString(2, txtTenNV.getText());
            ps.setString(3, (String) cboGioiTinh.getSelectedItem());
            ps.setString(4, txtSDT.getText());
            ps.setString(5, txtEmail.getText());
            ps.setString(6, txtDiaChi.getText());
            ps.setString(7, lblHinh.getText());
            ps.setString(8, txtNamSinh.getText());
            ps.setString(9, (String) cboChucVu.getSelectedItem());
            ps.setString(10, (String) cboTrangThai.getSelectedItem());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void suaNV() {
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("update nhanvien set tennv=?,gioitinh=?,sdt=?,email=?,diachi=?,hinh=?,namsinh=?,chucvu=?,tinhtrang=? where manv=?");
            ps.setString(10, txtMaNV.getText());
            ps.setString(1, txtTenNV.getText());
            ps.setString(2, (String) cboGioiTinh.getSelectedItem());
            ps.setString(3, txtSDT.getText());
            ps.setString(4, txtEmail.getText());
            ps.setString(5, txtDiaChi.getText());
            ps.setString(6, lblHinh.getText());
            ps.setString(7, txtNamSinh.getText());
            ps.setString(8, (String) cboChucVu.getSelectedItem());
            ps.setString(9, (String) cboTrangThai.getSelectedItem());
            ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(NhanVienPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void clearForm() {
        txtMaNV.setText("");
        txtTenNV.setText("");
        txtNamSinh.setText("");
        txtSDT.setText("");
        txtEmail.setText("");
        txtDiaChi.setText("");
        cboChucVu.setSelectedIndex(0);
        cboGioiTinh.setSelectedIndex(0);
        cboGioiTinh.setSelectedIndex(0);
        lblHinh.setText("Hình");
        lblHinh.setIcon(null);
        btnTaiKhoanDN.setEnabled(false);
        txtMaNV.setEnabled(true);
        loadData();
        txtTimKiem.setText("");
    }

    void loadPic(String img) {
        ImageIcon imgI = new ImageIcon("src\\Image\\" + img);
        Image im = imgI.getImage();
        ImageIcon icon = new ImageIcon(im.getScaledInstance(lblHinh.getWidth(), lblHinh.getHeight(), im.SCALE_SMOOTH));
        lblHinh.setIcon(icon);
    }

    void fillToForm() {
        try {
            String manv = "";
            int index = tblNhanVien.getSelectedRow();
            model = (DefaultTableModel) tblNhanVien.getModel();
            manv = (String) model.getValueAt(index, 0);
            con = DBConnection.getConnection();
            st = con.createStatement();
            rs = st.executeQuery("select * from nhanvien where manv='" + manv + "'");
            if (rs.next()) {
                txtMaNV.setText(rs.getString("manv"));
                txtTenNV.setText(rs.getString("tennv"));
                txtNamSinh.setText(String.valueOf(rs.getInt("namsinh")));
                txtSDT.setText(rs.getString("sdt"));
                txtEmail.setText(rs.getString("email"));
                txtDiaChi.setText(rs.getString("diachi"));
                cboGioiTinh.setSelectedItem(rs.getString("Gioitinh"));
                cboTrangThai.setSelectedItem(rs.getString("tinhtrang"));
                cboChucVu.setSelectedItem(rs.getString("chucvu"));
                loadPic(rs.getString("hinh"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public boolean validateNV() {
        //Mã NV
        if (txtMaNV.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(this, "Chưa nhập mã NV");
            txtMaNV.setBackground(Color.yellow);
            txtMaNV.requestFocus();
            return false;
        } else {
            Pattern pMaNV = Pattern.compile("PS[0-9]{5}");
            Matcher mMaNV = pMaNV.matcher(txtMaNV.getText());
            if (!mMaNV.matches()) {
                JOptionPane.showMessageDialog(this, "Mã NV nhập sai định dạng");
                txtMaNV.requestFocus();
                txtMaNV.setBackground(Color.pink);
                return false;
            } else {

                //Check trùng
                for (NhanVienClass nv : listNV) {
                    if (txtMaNV.getText().equals(nv.getMaNV())) {
                        JOptionPane.showMessageDialog(this, "Đã tồn tại mã NV này");
                        txtMaNV.requestFocus();
                        txtMaNV.setBackground(Color.pink);
                        return false;
                    }
                }
                txtMaNV.setBackground(Color.white);
            }
        }

        //Họ Tên NV
        if (txtTenNV.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(this, "Chưa nhập họ tên");
            txtTenNV.requestFocus();
            txtTenNV.setBackground(Color.yellow);
            return false;
        } else {
            //Check định dạng
            Pattern pTenNV = Pattern.compile("(?:\\P{M}\\p{M}*)+", Pattern.UNICODE_CASE);
            Matcher mTenNV = pTenNV.matcher(txtTenNV.getText());
            if (!mTenNV.matches()) {
                JOptionPane.showMessageDialog(this, "Họ tên sai định dạng");
                txtTenNV.setBackground(Color.PINK);
                txtTenNV.requestFocus();
                return false;
            } else {
                txtTenNV.setBackground(Color.WHITE);
            }
        }

//Năm sinh 
        if (txtNamSinh.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(this, "Chưa nhập năm sinh");
            txtNamSinh.requestFocus();
            txtNamSinh.setBackground(Color.yellow);
            return false;
        } else {
            //Check định dạng
            try {
                Integer.parseInt(txtNamSinh.getText());
                txtNamSinh.setBackground(Color.white);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Nhập sai định dạng ");
                txtNamSinh.requestFocus();
                txtNamSinh.setBackground(Color.pink);
                return false;
            }

            Pattern pDoB = Pattern.compile("19\\d{2}|20\\d{2}");
            Matcher mDoB = pDoB.matcher(txtNamSinh.getText());
            if (!mDoB.matches()) {
                txtNamSinh.requestFocus();
                txtNamSinh.setBackground(Color.pink);
                JOptionPane.showMessageDialog(this, "Năm sinh không đúng");
                return false;
            } else {
                txtNamSinh.setBackground(Color.white);
            }

            if (Integer.parseInt(txtNamSinh.getText()) < 1963 || Integer.parseInt(txtNamSinh.getText()) > 2005) {
                txtNamSinh.requestFocus();
                txtNamSinh.setBackground(Color.pink);
                JOptionPane.showMessageDialog(this, "Không nằm trong độ tuổi lao động");
                return false;
            }
        }

//Check SĐT
        if (txtSDT.getText().trim().length() == 0) {
            txtSDT.setBackground(Color.YELLOW);
            JOptionPane.showMessageDialog(this, "Chưa nhập SĐT");
            txtSDT.requestFocus();
            return false;
        } else {
            //Check định dạng
            try {
                Integer.parseInt(txtSDT.getText());
                txtSDT.setBackground(Color.white);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Nhập sai định dạng ");
                txtSDT.requestFocus();
                txtSDT.setBackground(Color.pink);
                return false;
            }
            Pattern pSDT = Pattern.compile("0[0-9]{9}");
            Matcher mSDT = pSDT.matcher(txtSDT.getText());
            if (!mSDT.matches()) {
                txtSDT.requestFocus();
                txtSDT.setBackground(Color.pink);
                JOptionPane.showMessageDialog(this, "Số điện thoại không đúng");
                return false;
            } else {
                txtSDT.setBackground(Color.white);
            }
        }

        //Check Email
        if (txtEmail.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(this, "Chưa nhập Email");
            txtEmail.requestFocus();
            txtEmail.setBackground(Color.yellow);
            return false;
        } else {
            //Check định dạng
            Pattern pEmail = Pattern.compile("^(.+)@(.+)$");
            Matcher mEmail = pEmail.matcher(txtEmail.getText());
            if (!mEmail.matches()) {
                JOptionPane.showMessageDialog(this, "Email sai định dạng");
                txtEmail.setBackground(Color.PINK);
                txtEmail.requestFocus();
                return false;
            } else {
                txtEmail.setBackground(Color.white);
            }
        }

        //Check địa chỉ
        if (txtDiaChi.getText().trim().length() == 0) {
            txtDiaChi.setBackground(Color.YELLOW);
            JOptionPane.showMessageDialog(this, "Chưa nhập địa chỉ");
            txtDiaChi.requestFocus();
            return false;
        } else {
            txtDiaChi.setBackground(Color.white);
        }
//
        return true;
    }

    void EventTxTFind() {
        txtTimKiem.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void removeUpdate(DocumentEvent e) {
                if (txtTimKiem.getText().trim().length() == 0) {
                    loadData();
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
            DefaultTableModel model = (DefaultTableModel) tblNhanVien.getModel();
            model.setRowCount(0);
            con = DBConnection.getConnection();
            st = con.createStatement();
            listNV.clear();
            String query = "select * "
                    + "from nhanvien where manv like '%" + find + "%' or tennv like N'%" + find + "%'";
            rs = st.executeQuery(query);
            while (rs.next()) {
                NhanVienClass nv = new NhanVienClass();
                nv.setMaNV(rs.getString("manv"));
                nv.setTenNV(rs.getString("tennv"));
                nv.setGioiTinh(rs.getString("gioitinh"));
                nv.setSDT(rs.getString("sdt"));
                nv.setEmail(rs.getString("email"));
                nv.setDiaChi(rs.getString("diachi"));
                nv.setNamSinh(rs.getInt("namsinh"));
                nv.setChucVu(rs.getString("Chucvu"));
                nv.setTinhtrang(rs.getString("tinhtrang"));
                nv.setHinh(rs.getString("hinh"));
                listNV.add(nv);
            }
            model = (DefaultTableModel) tblNhanVien.getModel();
            model.setRowCount(0);
            for (NhanVienClass nv : listNV) {
                Object[] row = new Object[]{
                    nv.getMaNV(), nv.getTenNV(), nv.IsGioiTinh(), nv.getChucVu(), nv.getTinhtrang()
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            Logger.getLogger(TrangChuPanel.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jpnRoot = new javax.swing.JPanel();
        jpnDSNhanVien = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblNhanVien = new javax.swing.JTable();
        txtTimKiem = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        btnTimKiem = new javax.swing.JButton();
        jpnTTNhanVien = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtMaNV = new javax.swing.JTextField();
        lblHinh = new javax.swing.JLabel();
        txtTenNV = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtNamSinh = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtSDT = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDiaChi = new javax.swing.JTextArea();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        cboTrangThai = new javax.swing.JComboBox<>();
        btnLamMoi = new javax.swing.JButton();
        btnTaiKhoanDN = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        cboChucVu = new javax.swing.JComboBox<>();
        cboGioiTinh = new javax.swing.JComboBox<>();

        jpnRoot.setBackground(new java.awt.Color(255, 255, 255));
        jpnRoot.setPreferredSize(new java.awt.Dimension(1130, 660));

        jpnDSNhanVien.setBackground(new java.awt.Color(255, 255, 255));
        jpnDSNhanVien.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách nhân viên", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18), new java.awt.Color(23, 70, 162))); // NOI18N

        jScrollPane2.setHorizontalScrollBar(null);

        tblNhanVien.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã NV", "Tên NV", "Giới tính", "Chức vụ", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNhanVienMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblNhanVien);
        if (tblNhanVien.getColumnModel().getColumnCount() > 0) {
            tblNhanVien.getColumnModel().getColumn(0).setPreferredWidth(70);
            tblNhanVien.getColumnModel().getColumn(0).setMaxWidth(70);
            tblNhanVien.getColumnModel().getColumn(2).setPreferredWidth(80);
            tblNhanVien.getColumnModel().getColumn(2).setMaxWidth(80);
            tblNhanVien.getColumnModel().getColumn(3).setPreferredWidth(80);
            tblNhanVien.getColumnModel().getColumn(3).setMaxWidth(80);
            tblNhanVien.getColumnModel().getColumn(4).setPreferredWidth(90);
            tblNhanVien.getColumnModel().getColumn(4).setMaxWidth(90);
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

        javax.swing.GroupLayout jpnDSNhanVienLayout = new javax.swing.GroupLayout(jpnDSNhanVien);
        jpnDSNhanVien.setLayout(jpnDSNhanVienLayout);
        jpnDSNhanVienLayout.setHorizontalGroup(
            jpnDSNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnDSNhanVienLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 556, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jpnDSNhanVienLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnTimKiem)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpnDSNhanVienLayout.setVerticalGroup(
            jpnDSNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnDSNhanVienLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnDSNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(btnTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 518, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );

        jpnTTNhanVien.setBackground(new java.awt.Color(255, 255, 255));
        jpnTTNhanVien.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin nhân viên", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18), new java.awt.Color(23, 70, 162))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Mã nhân viên:");

        txtMaNV.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtMaNV.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtMaNV.setPreferredSize(new java.awt.Dimension(200, 30));

        lblHinh.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblHinh.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHinh.setText("Hình");
        lblHinh.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lblHinh.setPreferredSize(new java.awt.Dimension(128, 128));
        lblHinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHinhMouseClicked(evt);
            }
        });

        txtTenNV.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtTenNV.setPreferredSize(new java.awt.Dimension(200, 30));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Tên nhân viên:");

        txtNamSinh.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtNamSinh.setPreferredSize(new java.awt.Dimension(200, 30));
        txtNamSinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNamSinhActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Số điện thoại:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Năm sinh:");

        txtSDT.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtSDT.setPreferredSize(new java.awt.Dimension(200, 30));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtEmail.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtEmail.setPreferredSize(new java.awt.Dimension(200, 30));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Email:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("Giới tính:");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("Địa chỉ:");

        txtDiaChi.setColumns(20);
        txtDiaChi.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtDiaChi.setRows(5);
        jScrollPane1.setViewportView(txtDiaChi);

        btnThem.setBackground(new java.awt.Color(95, 157, 247));
        btnThem.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnThem.setForeground(new java.awt.Color(255, 247, 233));
        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/plus (1).png"))); // NOI18N
        btnThem.setText("Thêm mới");
        btnThem.setBorderPainted(false);
        btnThem.setFocusPainted(false);
        btnThem.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnThem.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnThem.setInheritsPopupMenu(true);
        btnThem.setPreferredSize(new java.awt.Dimension(140, 35));
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setBackground(new java.awt.Color(255, 115, 29));
        btnSua.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnSua.setForeground(new java.awt.Color(255, 247, 233));
        btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/pen.png"))); // NOI18N
        btnSua.setText("Sửa");
        btnSua.setBorderPainted(false);
        btnSua.setFocusPainted(false);
        btnSua.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnSua.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnSua.setMinimumSize(new java.awt.Dimension(155, 35));
        btnSua.setPreferredSize(new java.awt.Dimension(100, 35));
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("Trạng thái:");

        cboTrangThai.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cboTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Còn làm", "Nghỉ Việc" }));
        cboTrangThai.setPreferredSize(new java.awt.Dimension(155, 30));

        btnLamMoi.setBackground(new java.awt.Color(95, 157, 247));
        btnLamMoi.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnLamMoi.setForeground(new java.awt.Color(255, 247, 233));
        btnLamMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/clear-format.png"))); // NOI18N
        btnLamMoi.setText("Làm mới");
        btnLamMoi.setBorderPainted(false);
        btnLamMoi.setFocusPainted(false);
        btnLamMoi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnLamMoi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnLamMoi.setMinimumSize(new java.awt.Dimension(155, 35));
        btnLamMoi.setPreferredSize(new java.awt.Dimension(100, 35));
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        btnTaiKhoanDN.setBackground(new java.awt.Color(23, 70, 162));
        btnTaiKhoanDN.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnTaiKhoanDN.setForeground(new java.awt.Color(255, 247, 233));
        btnTaiKhoanDN.setText("Tài khoản đăng nhập");
        btnTaiKhoanDN.setBorderPainted(false);
        btnTaiKhoanDN.setFocusPainted(false);
        btnTaiKhoanDN.setPreferredSize(new java.awt.Dimension(200, 30));
        btnTaiKhoanDN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaiKhoanDNActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setText("Chức vụ:");

        cboChucVu.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cboChucVu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Quản lý", "Nhân viên" }));
        cboChucVu.setPreferredSize(new java.awt.Dimension(155, 30));

        cboGioiTinh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nam", "Nữ", "Khác" }));
        cboGioiTinh.setPreferredSize(new java.awt.Dimension(200, 30));

        javax.swing.GroupLayout jpnTTNhanVienLayout = new javax.swing.GroupLayout(jpnTTNhanVien);
        jpnTTNhanVien.setLayout(jpnTTNhanVienLayout);
        jpnTTNhanVienLayout.setHorizontalGroup(
            jpnTTNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnTTNhanVienLayout.createSequentialGroup()
                .addGroup(jpnTTNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnTTNhanVienLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(jpnTTNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4))
                        .addGap(14, 14, 14)
                        .addGroup(jpnTTNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNamSinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addComponent(lblHinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpnTTNhanVienLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jLabel12)
                        .addGap(52, 52, 52)
                        .addComponent(cboChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpnTTNhanVienLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jLabel11)
                        .addGap(39, 39, 39)
                        .addComponent(cboTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpnTTNhanVienLayout.createSequentialGroup()
                        .addGap(137, 137, 137)
                        .addComponent(btnTaiKhoanDN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpnTTNhanVienLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpnTTNhanVienLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(jpnTTNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpnTTNhanVienLayout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(21, 21, 21)
                                .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jpnTTNhanVienLayout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(71, 71, 71)
                                .addGroup(jpnTTNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cboGioiTinh, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(jpnTTNhanVienLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel7)
                        .addGap(16, 16, 16)
                        .addGroup(jpnTTNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addGroup(jpnTTNhanVienLayout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addGap(61, 61, 61)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(7, 7, 7))
        );
        jpnTTNhanVienLayout.setVerticalGroup(
            jpnTTNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnTTNhanVienLayout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jpnTTNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnTTNhanVienLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel2)
                        .addGap(31, 31, 31)
                        .addComponent(jLabel4))
                    .addGroup(jpnTTNhanVienLayout.createSequentialGroup()
                        .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jpnTTNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNamSinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)))
                    .addComponent(lblHinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jpnTTNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jpnTTNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jpnTTNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(cboGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(jpnTTNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnTTNhanVienLayout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(jLabel7))
                    .addComponent(jLabel10)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jpnTTNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnTTNhanVienLayout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jLabel12))
                    .addComponent(cboChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jpnTTNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnTTNhanVienLayout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jLabel11))
                    .addComponent(cboTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnTaiKhoanDN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jpnTTNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jpnRootLayout = new javax.swing.GroupLayout(jpnRoot);
        jpnRoot.setLayout(jpnRootLayout);
        jpnRootLayout.setHorizontalGroup(
            jpnRootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnRootLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpnDSNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jpnTTNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpnRootLayout.setVerticalGroup(
            jpnRootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnRootLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnRootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jpnDSNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpnTTNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void txtNamSinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNamSinhActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNamSinhActionPerformed

    private void tblNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhanVienMouseClicked
        // TODO add your handling code here:
        fillToForm();
        btnTaiKhoanDN.setEnabled(true);
        txtMaNV.setEnabled(false);
    }//GEN-LAST:event_tblNhanVienMouseClicked

    private void btnTaiKhoanDNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaiKhoanDNActionPerformed
        try {
            String id = txtMaNV.getText();
            DefaultTableModel model = (DefaultTableModel) tblNhanVien.getModel();
            int index = tblNhanVien.getSelectedRow();
            String manv = (String) model.getValueAt(index, 0);
            con = DBConnection.getConnection();
            st = con.createStatement();
            rs = st.executeQuery("select * from acclogin,nhanvien where manv=userid and manv='" + manv + "'");

            String username = "";
            String password = "";
            if (rs.next()) {
                username = rs.getString(1);
                password = rs.getString(2);
            }
            if (username.isBlank()) {
                int kq = JOptionPane.showConfirmDialog(this, "Nhân viên chưa có tài khoản! Tạo mới?", "Tài khoản đăng nhập", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (kq == JOptionPane.YES_OPTION) {
                    String manv2 = txtMaNV.getText();

                    FormSighUpFrame sighUpFrame = new FormSighUpFrame();
                    sighUpFrame.setVisible(true);
                    sighUpFrame.getManv(manv2);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Tên đăng nhập: " + username + "\n Mật Khẩu: " + password);

            }
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnTaiKhoanDNActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        // TODO add your handling code here:
        clearForm();
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // // TODO add your handling code here:
        if (validateNV()) {
            themNV();
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        if (validateNV()) {
            suaNV();
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void lblHinhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHinhMouseClicked
        // TODO add your handling code here:
        lblHinh.setText("");
        lblHinh.setIcon(null);
        JFileChooser jfile = new JFileChooser("src");
        int result = jfile.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                lblHinh.setText("" + jfile.getSelectedFile().getName());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e);
            }
        }
    }//GEN-LAST:event_lblHinhMouseClicked

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        // TODO add your handling code here:
        FindByName(txtTimKiem.getText());
    }//GEN-LAST:event_btnTimKiemActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnTaiKhoanDN;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboChucVu;
    private javax.swing.JComboBox<String> cboGioiTinh;
    private javax.swing.JComboBox<String> cboTrangThai;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel jpnDSNhanVien;
    private javax.swing.JPanel jpnRoot;
    private javax.swing.JPanel jpnTTNhanVien;
    private javax.swing.JLabel lblHinh;
    private javax.swing.JTable tblNhanVien;
    private javax.swing.JTextArea txtDiaChi;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JTextField txtNamSinh;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtTenNV;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}

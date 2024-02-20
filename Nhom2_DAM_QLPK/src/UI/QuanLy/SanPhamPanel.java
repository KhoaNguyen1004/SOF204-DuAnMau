package UI.QuanLy;

import Class.SanPhamClass;
import Controller.DBConnection;
import UI.NhanVien.MainJFramesNV;
import UI.QuanLy.MainJFrames;
import UI.QuanLy.NhanVienPanel;
import UI.QuanLy.TrangChuPanel;
import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class SanPhamPanel extends javax.swing.JPanel {

    String Url;
    List<SanPhamClass> listSP = new ArrayList<>();
    private int index = -1;
    Connection con = null;
    PreparedStatement ps = null;
    Statement st = null;
    ResultSet rs = null;
    String tenHinhAnh = null;
    Vector data = null;
    DefaultTableModel model = null;

    public SanPhamPanel() {
        initComponents();
        loadData();
        EventTxTFind();
        btnSuaSP.setEnabled(false);
    }
    
    String getNguoiThem() {
        String manvs = "";
        try {
            con = DBConnection.getConnection();
            String tennv = MainJFrames.lblTenNV.getText();
            ps = con.prepareStatement("select manv from acclogin,nhanvien where userid=manv and tennv=N'" + tennv + "'");
            rs = ps.executeQuery();

            if (rs.next()) {
                manvs = (rs.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return manvs;
    }
    
    void loadData() {
        try {
            con = DBConnection.getConnection();
            st = con.createStatement();
            listSP.clear();
            rs = st.executeQuery("select * from sanpham");
            while (rs.next()) {
                SanPhamClass sp = new SanPhamClass();
                sp.setMaLK(rs.getString("malk"));
                sp.setTenLK(rs.getString("tenlk"));
                sp.setLoaiLK(rs.getString("loailk"));
                sp.setGia(rs.getFloat("gia"));
                sp.setSoLuong(rs.getInt("soluong"));
                sp.setBaoHanh(rs.getInt("baohanh"));
                sp.setNSX(rs.getString("nsx"));
                sp.setHinh(rs.getString("hinh"));
                sp.setNguoiThem(rs.getString("nguoithem"));
                sp.setTinhTrang(rs.getString("tinhtrang"));
                listSP.add(sp);
            }
            model = (DefaultTableModel) tblDanhSachSP.getModel();
            model.setRowCount(0);
            for (SanPhamClass sp : listSP) {
                Object[] row = new Object[]{
                    sp.getMaLK(), sp.getTenLK(), sp.getLoaiLK(), sp.getGia(), sp.getSoLuong(), sp.getBaoHanh(),
                    sp.getNSX(), sp.getTinhTrang()
                };
                model.addRow(row);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    void loadPic(String img) {
        ImageIcon imgI = new ImageIcon("src\\Image\\" + img);
        Image im = imgI.getImage();
        ImageIcon icon = new ImageIcon(im.getScaledInstance(lblHinh.getWidth(), lblHinh.getHeight(), im.SCALE_SMOOTH));
        lblHinh.setIcon(icon);
    }

    void fillToForm() {
        try {
            String masp = "";
            int index = tblDanhSachSP.getSelectedRow();
            model = (DefaultTableModel) tblDanhSachSP.getModel();
            masp = (String) model.getValueAt(index, 0);
            con = DBConnection.getConnection();
            st = con.createStatement();
            rs = st.executeQuery("select * from sanpham where malk='" + masp + "'");
            if (rs.next()) {
                txtMaLK.setText(rs.getString("malk"));
                txtTenLK.setText(rs.getString("tenlk"));
                txtLLK.setText(rs.getString("loailk"));
                txtGia.setText(String.valueOf(rs.getFloat("gia")));
                txtSL.setText(String.valueOf(rs.getInt("soluong")));
                txtBaoHanh.setText(String.valueOf(rs.getInt("baohanh")));
                txtNSX.setText(rs.getString("nsx"));
                loadPic(rs.getString("hinh"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    void addSP() {
        try {
            String manguoithem = getNguoiThem();
            con = DBConnection.getConnection();
            ps = con.prepareStatement("insert into sanpham values(?,?,?,?,?,?,?,?,?,?)");
            ps.setString(1, txtMaLK.getText());
            ps.setString(2, txtTenLK.getText());
            ps.setString(3, txtLLK.getText());
            ps.setFloat(4, Float.parseFloat(txtGia.getText()));
            ps.setInt(5, Integer.parseInt(txtSL.getText()));
            ps.setInt(6, Integer.parseInt(txtBaoHanh.getText()));
            ps.setString(7, txtNSX.getText());
            ps.setString(8, lblHinh.getText());
            ps.setString(9, manguoithem);
            ps.setString(10, (String) cboTinhTrang.getSelectedItem());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    void updateSP() {
        try {
            String manguoithem = getNguoiThem();
            con = DBConnection.getConnection();
            ps = con.prepareStatement("update SanPham set TenLK=?, LoaiLK=?, Gia=?, SoLuong=?, BaoHanh=?, NSX=?,Hinh=?, NguoiThem=?, TinhTrang=? where MaLK=?");
            ps.setString(10, txtMaLK.getText());
            ps.setString(1, txtTenLK.getText());
            ps.setString(2, txtLLK.getText());
            ps.setFloat(3, Float.parseFloat(txtGia.getText()));
            ps.setInt(4, Integer.parseInt(txtSL.getText()));
            ps.setInt(5, Integer.parseInt(txtBaoHanh.getText()));
            ps.setString(6, txtNSX.getText());
            ps.setString(7, lblHinh.getText());
            ps.setString(8, manguoithem);
            ps.setString(9, (String) cboTinhTrang.getSelectedItem());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void clearFormSP() {
        txtMaLK.setText("");
        txtTenLK.setText("");
        txtLLK.setText("");
        txtSL.setText("");
        txtGia.setText("");
        txtBaoHanh.setText("");
        txtNSX.setText("");
        lblHinh.setText("hinh anh");
        lblHinh.setIcon(null);
        tenHinhAnh = null;
        cboTinhTrang.setSelectedItem("Còn Hàng");
        txtMaLK.setEditable(true);
        tblDanhSachSP.clearSelection();
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
            DefaultTableModel model = (DefaultTableModel) tblDanhSachSP.getModel();
            model.setRowCount(0);
            con = DBConnection.getConnection();
            st = con.createStatement();
            String query = "select * from SanPham where MaLK like '%" + find + "%' or TenLK like N'%" + find + "%'";
            rs = st.executeQuery(query);
            while (rs.next()) {
                data = new Vector();
                data.add(rs.getString("malk"));
                data.add(rs.getNString("TenLK"));
                data.add(rs.getString("LoaiLK"));
                data.add(rs.getFloat("Gia"));
                data.add(rs.getInt("SoLuong"));
                data.add(rs.getInt("Baohanh"));
                data.add(rs.getString("nsx"));
                data.add(rs.getNString("TinhTrang"));
                model.addRow(data);
            }
            tblDanhSachSP.setModel(model);
        } catch (Exception e) {
            Logger.getLogger(TrangChuPanel.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public boolean validateSP() {
        //Mã LK
        if (txtMaLK.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(this, "Chưa nhập mã LK");
            txtMaLK.setBackground(Color.yellow);
            txtMaLK.requestFocus();
            return false;
        } else {
            //Check trùng
            for (SanPhamClass sp : listSP) {
                if (txtMaLK.getText().equals(sp.getMaLK())) {
                    JOptionPane.showMessageDialog(this, "Đã tồn tại mã LK này");
                    txtMaLK.requestFocus();
                    txtMaLK.setBackground(Color.pink);
                    return false;
                }
            }
            txtMaLK.setBackground(Color.white);
        }

        //Tên LK
        if (txtTenLK.getText().length() == 0) {
            JOptionPane.showMessageDialog(this, "Chưa nhập tên LK");
            txtTenLK.setBackground(Color.yellow);
            txtTenLK.requestFocus();
            return false;
        } else {
            //Check định dạng
            Pattern pTenLK = Pattern.compile("(?:\\P{M}\\p{M}*)+", Pattern.UNICODE_CASE);
            Matcher mTenLK = pTenLK.matcher(txtTenLK.getText());
            if (!mTenLK.matches()) {
                JOptionPane.showMessageDialog(this, "Tên LK sai định dạng");
                txtTenLK.setBackground(Color.PINK);
                txtTenLK.requestFocus();
                return false;
            } else {
                txtTenLK.setBackground(Color.WHITE);
            }
        }

        //Loại LK
        if (txtLLK.getText().length() == 0) {
            JOptionPane.showMessageDialog(this, "Chưa nhập loại LK");
            txtLLK.setBackground(Color.yellow);
            txtLLK.requestFocus();
            return false;
        } else {
            //Check định dạng
            Pattern pLoaiLK = Pattern.compile("(?:\\P{M}\\p{M}*)+", Pattern.UNICODE_CASE);
            Matcher mLoaiLK = pLoaiLK.matcher(txtLLK.getText());
            if (!mLoaiLK.matches()) {
                JOptionPane.showMessageDialog(this, "Loại LK sai định dạng");
                txtLLK.setBackground(Color.PINK);
                txtLLK.requestFocus();
                return false;
            } else {
                txtLLK.setBackground(Color.WHITE);
            }
        }

        //Đơn giá
        if (txtGia.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(this, "Chưa nhập đơn giá LK");
            txtGia.requestFocus();
            txtGia.setBackground(Color.yellow);
            return false;
        } else {
            try {
                Integer.parseInt(txtGia.getText());
                txtGia.setBackground(Color.white);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Nhập sai định dạng ");
                txtGia.requestFocus();
                txtGia.setBackground(Color.pink);
                return false;
            }
        }

        //Số lượng
        if (txtSL.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(this, "Chưa nhập số lượng LK");
            txtSL.requestFocus();
            txtSL.setBackground(Color.yellow);
            return false;
        } else {
            try {
                Integer.parseInt(txtSL.getText());
                txtSL.setBackground(Color.white);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Nhập sai định dạng");
                txtSL.requestFocus();
                txtSL.setBackground(Color.pink);
                return false;
            }
        }

        //Bảo hành
        if (txtBaoHanh.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(this, "Chưa nhập thời gian bảo hành");
            txtBaoHanh.requestFocus();
            txtBaoHanh.setBackground(Color.yellow);
            return false;
        } else {
            try {
                Integer.parseInt(txtBaoHanh.getText());
                txtBaoHanh.setBackground(Color.white);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Nhập sai định dạng");
                txtBaoHanh.requestFocus();
                txtBaoHanh.setBackground(Color.pink);
                return false;
            }
        }

        //NSX
        if (txtNSX.getText().length() == 0) {
            JOptionPane.showMessageDialog(this, "Chưa nhập tên NSX");
            txtNSX.setBackground(Color.yellow);
            txtNSX.requestFocus();
            return false;
        } else {
            //Check định dạng
            Pattern pTenLK = Pattern.compile("(?:\\P{M}\\p{M}*)+", Pattern.UNICODE_CASE);
            Matcher mTenLK = pTenLK.matcher(txtTenLK.getText());
            if (!mTenLK.matches()) {
                JOptionPane.showMessageDialog(this, "Tên NSX sai định dạng");
                txtNSX.setBackground(Color.PINK);
                txtNSX.requestFocus();
                return false;
            } else {
                txtNSX.setBackground(Color.WHITE);
            }
        }
        return true;
    }
    
    public boolean validateUPDATE() {
        //Tên LK
        if (txtTenLK.getText().length() == 0) {
            JOptionPane.showMessageDialog(this, "Chưa nhập tên LK");
            txtTenLK.setBackground(Color.yellow);
            txtTenLK.requestFocus();
            return false;
        } else {
            //Check định dạng
            Pattern pTenLK = Pattern.compile("(?:\\P{M}\\p{M}*)+", Pattern.UNICODE_CASE);
            Matcher mTenLK = pTenLK.matcher(txtTenLK.getText());
            if (!mTenLK.matches()) {
                JOptionPane.showMessageDialog(this, "Tên LK sai định dạng");
                txtTenLK.setBackground(Color.PINK);
                txtTenLK.requestFocus();
                return false;
            } else {
                txtTenLK.setBackground(Color.WHITE);
            }
        }

        //Loại LK
        if (txtLLK.getText().length() == 0) {
            JOptionPane.showMessageDialog(this, "Chưa nhập loại LK");
            txtLLK.setBackground(Color.yellow);
            txtLLK.requestFocus();
            return false;
        } else {
            //Check định dạng
            Pattern pLoaiLK = Pattern.compile("(?:\\P{M}\\p{M}*)+", Pattern.UNICODE_CASE);
            Matcher mLoaiLK = pLoaiLK.matcher(txtLLK.getText());
            if (!mLoaiLK.matches()) {
                JOptionPane.showMessageDialog(this, "Loại LK sai định dạng");
                txtLLK.setBackground(Color.PINK);
                txtLLK.requestFocus();
                return false;
            } else {
                txtLLK.setBackground(Color.WHITE);
            }
        }

        //Đơn giá
        if (txtGia.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(this, "Chưa nhập đơn giá LK");
            txtGia.requestFocus();
            txtGia.setBackground(Color.yellow);
            return false;
        } else {
            try {
                Integer.parseInt(txtGia.getText());
                txtGia.setBackground(Color.white);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Nhập sai định dạng ");
                txtGia.requestFocus();
                txtGia.setBackground(Color.pink);
                return false;
            }
        }

        //Số lượng
        if (txtSL.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(this, "Chưa nhập số lượng LK");
            txtSL.requestFocus();
            txtSL.setBackground(Color.yellow);
            return false;
        } else {
            try {
                Integer.parseInt(txtSL.getText());
                txtSL.setBackground(Color.white);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Nhập sai định dạng");
                txtSL.requestFocus();
                txtSL.setBackground(Color.pink);
                return false;
            }
        }

        //Bảo hành
        if (txtBaoHanh.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(this, "Chưa nhập thời gian bảo hành");
            txtBaoHanh.requestFocus();
            txtBaoHanh.setBackground(Color.yellow);
            return false;
        } else {
            try {
                Integer.parseInt(txtBaoHanh.getText());
                txtBaoHanh.setBackground(Color.white);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Nhập sai định dạng");
                txtBaoHanh.requestFocus();
                txtBaoHanh.setBackground(Color.pink);
                return false;
            }
        }

        //NSX
        if (txtNSX.getText().length() == 0) {
            JOptionPane.showMessageDialog(this, "Chưa nhập tên NSX");
            txtNSX.setBackground(Color.yellow);
            txtNSX.requestFocus();
            return false;
        } else {
            //Check định dạng
            Pattern pTenLK = Pattern.compile("(?:\\P{M}\\p{M}*)+", Pattern.UNICODE_CASE);
            Matcher mTenLK = pTenLK.matcher(txtTenLK.getText());
            if (!mTenLK.matches()) {
                JOptionPane.showMessageDialog(this, "Tên NSX sai định dạng");
                txtNSX.setBackground(Color.PINK);
                txtNSX.requestFocus();
                return false;
            } else {
                txtNSX.setBackground(Color.WHITE);
            }
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpnRoot = new javax.swing.JPanel();
        jpnThongTinSanPham = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtMaLK = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtTenLK = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtLLK = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtGia = new javax.swing.JTextField();
        txtSL = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtBaoHanh = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        lblHinh = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cboTinhTrang = new javax.swing.JComboBox<>();
        btnLamMoi = new javax.swing.JButton();
        btnThemSP = new javax.swing.JButton();
        btnSuaSP = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        txtNSX = new javax.swing.JTextField();
        jpnDSSanPham = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDanhSachSP = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();

        jpnRoot.setBackground(new java.awt.Color(255, 255, 255));
        jpnRoot.setPreferredSize(new java.awt.Dimension(1130, 660));

        jpnThongTinSanPham.setBackground(new java.awt.Color(255, 255, 255));
        jpnThongTinSanPham.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18), new java.awt.Color(23, 70, 162))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Mã linh kiện:");

        txtMaLK.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtMaLK.setPreferredSize(new java.awt.Dimension(180, 30));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Tên linh kiện:");

        txtTenLK.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtTenLK.setPreferredSize(new java.awt.Dimension(180, 30));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Loại linh kiện:");

        txtLLK.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtLLK.setPreferredSize(new java.awt.Dimension(180, 30));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Giá linh kiện:");

        txtGia.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtGia.setPreferredSize(new java.awt.Dimension(180, 30));

        txtSL.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtSL.setPreferredSize(new java.awt.Dimension(180, 30));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Số lượng:");

        txtBaoHanh.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtBaoHanh.setPreferredSize(new java.awt.Dimension(180, 30));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Bảo hành:");

        lblHinh.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHinh.setText("Hình");
        lblHinh.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lblHinh.setPreferredSize(new java.awt.Dimension(128, 128));
        lblHinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHinhMouseClicked(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Tình trạng:");

        cboTinhTrang.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cboTinhTrang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Còn hàng", "Hết hàng", "Ngưng bán" }));
        cboTinhTrang.setPreferredSize(new java.awt.Dimension(70, 30));

        btnLamMoi.setBackground(new java.awt.Color(95, 157, 247));
        btnLamMoi.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
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
        btnThemSP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/plus (1).png"))); // NOI18N
        btnThemSP.setText("Thêm sản phẩm");
        btnThemSP.setBorderPainted(false);
        btnThemSP.setFocusPainted(false);
        btnThemSP.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnThemSP.setPreferredSize(new java.awt.Dimension(180, 35));
        btnThemSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemSPActionPerformed(evt);
            }
        });

        btnSuaSP.setBackground(new java.awt.Color(255, 115, 29));
        btnSuaSP.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnSuaSP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/pen.png"))); // NOI18N
        btnSuaSP.setText("Sửa sản phẩm");
        btnSuaSP.setBorderPainted(false);
        btnSuaSP.setFocusPainted(false);
        btnSuaSP.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnSuaSP.setPreferredSize(new java.awt.Dimension(180, 35));
        btnSuaSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaSPActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("Nhà sản xuất:");

        txtNSX.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtNSX.setPreferredSize(new java.awt.Dimension(73, 30));

        javax.swing.GroupLayout jpnThongTinSanPhamLayout = new javax.swing.GroupLayout(jpnThongTinSanPham);
        jpnThongTinSanPham.setLayout(jpnThongTinSanPhamLayout);
        jpnThongTinSanPhamLayout.setHorizontalGroup(
            jpnThongTinSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnThongTinSanPhamLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnThongTinSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnThongTinSanPhamLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(btnThemSP, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addComponent(btnSuaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpnThongTinSanPhamLayout.createSequentialGroup()
                        .addGroup(jpnThongTinSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jpnThongTinSanPhamLayout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtMaLK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jpnThongTinSanPhamLayout.createSequentialGroup()
                                .addGroup(jpnThongTinSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jpnThongTinSanPhamLayout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnThongTinSanPhamLayout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addGap(18, 18, 18)))
                                .addGroup(jpnThongTinSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cboTinhTrang, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addGroup(jpnThongTinSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jpnThongTinSanPhamLayout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtTenLK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtLLK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jpnThongTinSanPhamLayout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtSL, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtBaoHanh, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtNSX, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(40, 40, 40)
                        .addComponent(lblHinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 46, Short.MAX_VALUE))
        );
        jpnThongTinSanPhamLayout.setVerticalGroup(
            jpnThongTinSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnThongTinSanPhamLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnThongTinSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnThongTinSanPhamLayout.createSequentialGroup()
                        .addGroup(jpnThongTinSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtMaLK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(txtTenLK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(txtLLK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(40, 40, 40)
                        .addGroup(jpnThongTinSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtBaoHanh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(txtSL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(txtGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel9)
                            .addComponent(txtNSX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lblHinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jpnThongTinSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnThongTinSanPhamLayout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addGroup(jpnThongTinSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnThemSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSuaSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jpnThongTinSanPhamLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(jpnThongTinSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboTinhTrang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jpnDSSanPham.setBackground(new java.awt.Color(255, 255, 255));
        jpnDSSanPham.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18), new java.awt.Color(20, 70, 162))); // NOI18N

        tblDanhSachSP.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblDanhSachSP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã linh kiện", "Tên linh kiện", "Loại linh kiện", "Giá", "Số lượng", "Bảo hành", "Nhà sản xuất", "Tình trạng"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDanhSachSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDanhSachSPMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDanhSachSP);

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel10.setText("Tìm kiếm:");

        txtTimKiem.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtTimKiem.setPreferredSize(new java.awt.Dimension(300, 28));

        btnTimKiem.setBackground(new java.awt.Color(95, 157, 247));
        btnTimKiem.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnTimKiem.setForeground(new java.awt.Color(255, 243, 233));
        btnTimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/search (5).png"))); // NOI18N
        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.setBorderPainted(false);
        btnTimKiem.setFocusPainted(false);
        btnTimKiem.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnTimKiem.setPreferredSize(new java.awt.Dimension(100, 35));
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
                .addGroup(jpnDSSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jpnDSSanPhamLayout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jpnDSSanPhamLayout.setVerticalGroup(
            jpnDSSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnDSSanPhamLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnDSSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jpnRootLayout = new javax.swing.GroupLayout(jpnRoot);
        jpnRoot.setLayout(jpnRootLayout);
        jpnRootLayout.setHorizontalGroup(
            jpnRootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnRootLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnRootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jpnDSSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpnThongTinSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jpnRootLayout.setVerticalGroup(
            jpnRootLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnRootLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpnThongTinSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpnDSSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
    }// </editor-fold>//GEN-END:initComponents

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        // TODO add your handling code here:
        clearFormSP();
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void btnThemSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemSPActionPerformed
        // TODO add your handling code here:
        if (validateSP()) {
            addSP();
            JOptionPane.showMessageDialog(this, "Thêm thành công!");
            loadData();
            clearFormSP();
        }
    }//GEN-LAST:event_btnThemSPActionPerformed

    private void btnSuaSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaSPActionPerformed
        // TODO add your handling code here:
        if (validateUPDATE()) {
            updateSP();
            JOptionPane.showMessageDialog(this, "Sửa thành công!");
            loadData();
            clearFormSP();
        }
    }//GEN-LAST:event_btnSuaSPActionPerformed

    private void tblDanhSachSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDanhSachSPMouseClicked
        // TODO add your handling code here:
        txtMaLK.setEnabled(false);
        btnSuaSP.setEnabled(true);
        fillToForm();
    }//GEN-LAST:event_tblDanhSachSPMouseClicked

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
    private javax.swing.JButton btnSuaSP;
    private javax.swing.JButton btnThemSP;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JComboBox<String> cboTinhTrang;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel jpnDSSanPham;
    private javax.swing.JPanel jpnRoot;
    private javax.swing.JPanel jpnThongTinSanPham;
    private javax.swing.JLabel lblHinh;
    private javax.swing.JTable tblDanhSachSP;
    private javax.swing.JTextField txtBaoHanh;
    private javax.swing.JTextField txtGia;
    private javax.swing.JTextField txtLLK;
    private javax.swing.JTextField txtMaLK;
    private javax.swing.JTextField txtNSX;
    private javax.swing.JTextField txtSL;
    private javax.swing.JTextField txtTenLK;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}

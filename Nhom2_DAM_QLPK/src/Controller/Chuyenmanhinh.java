package Controller;

import UI.NhanVien.BanHangPanel;
import UI.NhanVien.KhachHangPanel;
import UI.NhanVien.SanPhamPanel3;
import UI.QuanLy.HoaDonPanel;
import UI.QuanLy.NhanVienPanel;
import UI.QuanLy.SanPhamPanel;
import UI.QuanLy.ThongKePanel;
import UI.QuanLy.TrangChuPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Chuyenmanhinh {

    private JPanel root;
    private String kindSelected = "";
    private List<DanhMuc> listItem = null;

    public Chuyenmanhinh(JPanel jpnRoot) {
        this.root = jpnRoot;
    }
    
    //Hàm show trang đầu tiên xuất hiện khi mới truy cập vào ứng dụng
    public void setView(JPanel jpnItem, JLabel jlbItem) {
        kindSelected = "TrangChu";
        jpnItem.setBackground(new Color(23, 70, 162));
        jlbItem.setBackground(new Color(23, 70, 162));

        root.removeAll();
        root.setLayout(new BorderLayout());
        root.add(new TrangChuPanel());
        root.validate();
        root.repaint();
    }
    
    //----------------------------Hàm tạo event để xử lý sự kiện MouseListener ( thao tác chuyển đổi menu)
    //--Dùng cho Giao diện quản lý
    public void setEvent(List<DanhMuc> listItem) {
        this.listItem = listItem;
        for (DanhMuc item : listItem) {
            item.getJlb().addMouseListener(new LabelEvent(item.getKind(), item.getJpn(), item.getJlb()));
        }
    }

    class LabelEvent implements MouseListener {

        private JPanel node;
        private String kind;
        private JPanel jpnItem;
        private JLabel jlbItem;

        public LabelEvent(String kind, JPanel jpnItem, JLabel jlbItem) {
            this.kind = kind;
            this.jpnItem = jpnItem;
            this.jlbItem = jlbItem;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            switch (kind) {
                case "TrangChu":
                    node = new TrangChuPanel();
                    break;
                case "QLNV":
                    node = new NhanVienPanel();
                    break;
                case "QLSP":
                    node = new SanPhamPanel();
                    break;
                case "QLHD":
                    node = new HoaDonPanel();
                    break;
                case "ThongKe":
                    node = new ThongKePanel();
                    break;
                default:
                    node = new TrangChuPanel();
            }
            root.removeAll();
            root.setLayout(new BorderLayout());
            root.add(node);
            root.validate();
            root.repaint();
            changeBackGroundColor(kind);
        }

        @Override
        public void mousePressed(MouseEvent e) {
            kindSelected = kind;
            jpnItem.setBackground(new Color(23, 70, 162));
            jlbItem.setBackground(new Color(23, 70, 162));
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            jpnItem.setBackground(new Color(23, 70, 162));
            jlbItem.setBackground(new Color(23, 70, 162));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (!kindSelected.equalsIgnoreCase(kind)) {
                jpnItem.setBackground(new Color(95, 157, 247));
                jlbItem.setBackground(new Color(95, 157, 247));
            }
        }

    }

    //---Dùng cho giao diện Nhân viên
    public void setEventNV(List<DanhMuc> listItem) {
        this.listItem = listItem;
        for (DanhMuc item : listItem) {
            item.getJlb().addMouseListener(new LabelEventNV(item.getKind(), item.getJpn(), item.getJlb()));
        }
    }

    class LabelEventNV implements MouseListener {

        private JPanel node;
        private String kind;
        private JPanel jpnItem;
        private JLabel jlbItem;

        public LabelEventNV(String kind, JPanel jpnItem, JLabel jlbItem) {
            this.kind = kind;
            this.jpnItem = jpnItem;
            this.jlbItem = jlbItem;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            switch (kind) {
                case "TrangChu":
                    node = new TrangChuPanel();
                    break;
                case "BanHang":
                    node = new BanHangPanel();
                    break;
                case "KhachHang":
                    node = new KhachHangPanel();
                    break;
                case "SanPham":
                    node =new SanPhamPanel3();
                    break;
                default:
                    node = new TrangChuPanel();
            }
            root.removeAll();
            root.setLayout(new BorderLayout());
            root.add(node);
            root.validate();
            root.repaint();
            changeBackGroundColor(kind);
        }

        @Override
        public void mousePressed(MouseEvent e) {
            kindSelected = kind;
            jpnItem.setBackground(new Color(23, 70, 162));
            jlbItem.setBackground(new Color(23, 70, 162));
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            jpnItem.setBackground(new Color(23, 70, 162));
            jlbItem.setBackground(new Color(23, 70, 162));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (!kindSelected.equalsIgnoreCase(kind)) {
                jpnItem.setBackground(new Color(95, 157, 247));
                jlbItem.setBackground(new Color(95, 157, 247));
            }
        }

    }
    
    
    private void changeBackGroundColor(String kind) {
        for (DanhMuc item : listItem) {
            if (item.getKind().equalsIgnoreCase(kind)) {
                item.getJpn().setBackground(new Color(23, 70, 162));
                item.getJlb().setBackground(new Color(23, 70, 162));
            } else {
                item.getJlb().setBackground(new Color(95, 157, 247));
                item.getJpn().setBackground(new Color(95, 157, 247));
            }
        }
    }
}

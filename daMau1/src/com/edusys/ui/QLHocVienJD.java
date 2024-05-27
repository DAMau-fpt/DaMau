/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.ui;

import com.edusys.dao.ChuyenDeDAO;
import com.edusys.dao.HocVienDAO;
import com.edusys.dao.KhoaHocDAO;
import com.edusys.dao.NguoiHocDAO;
import com.edusys.model.ChuyenDe;
import com.edusys.model.HocVien;
import com.edusys.model.KhoaHoc;
import com.edusys.model.NguoiHoc;
import com.edusys.utils.XAuth;
import com.edusys.utils.XDate;
import com.edusys.utils.XDialog;
import com.edusys.utils.XExcel;
import com.formdev.flatlaf.FlatLightLaf;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author duyplus
 */
public class QLHocVienJD extends javax.swing.JDialog {

    ChuyenDeDAO cddao = new ChuyenDeDAO();
    KhoaHocDAO khdao = new KhoaHocDAO();
    NguoiHocDAO nhdao = new NguoiHocDAO();
    HocVienDAO hvdao = new HocVienDAO();

    /**
     * Creates new form QLHocVienJDialog
     *
     * @param parent
     * @param modal
     */
    public QLHocVienJD(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
    }

    private void init() {
        setLocationRelativeTo(null);
        this.fillComboBoxCD();

        DefaultTableCellRenderer render = new DefaultTableCellRenderer();
        render.setHorizontalAlignment(javax.swing.JLabel.CENTER);

        TableColumnModel hv = tblHocVien.getColumnModel();
        hv.getColumn(0).setCellRenderer(render);
        hv.getColumn(1).setCellRenderer(render);
        hv.getColumn(2).setCellRenderer(render);
        hv.getColumn(4).setCellRenderer(render);

        TableColumnModel nh = tblNguoiHoc.getColumnModel();
        nh.getColumn(0).setMaxWidth(100);
        nh.getColumn(2).setMaxWidth(100);

        nh.getColumn(0).setCellRenderer(render);
        nh.getColumn(2).setCellRenderer(render);
        nh.getColumn(3).setCellRenderer(render);
        nh.getColumn(4).setCellRenderer(render);
    }

    @SuppressWarnings("unchecked")
    private void fillComboBoxCD() {
        try {
            DefaultComboBoxModel model = (DefaultComboBoxModel) cboChuyenDe.getModel();
            model.removeAllElements();
            List<ChuyenDe> list = cddao.selectAll();
            for (ChuyenDe cd : list) {
                model.addElement(cd);
            }
            this.fillComboBoxKH();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void fillComboBoxKH() {
        try {
            DefaultComboBoxModel model = (DefaultComboBoxModel) cboKhoaHoc.getModel();
            model.removeAllElements();
            ChuyenDe cd = (ChuyenDe) cboChuyenDe.getSelectedItem();
            if (cd != null) {
                List<KhoaHoc> list = khdao.selectByChuyenDe(cd.getMaCD());
                for (KhoaHoc kh : list) {
                    model.addElement(kh);
                }
                this.fillTableHV();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fillTableHV() {
        DefaultTableModel model = (DefaultTableModel) tblHocVien.getModel();
        model.setRowCount(0);
        try {
            KhoaHoc kh = (KhoaHoc) cboKhoaHoc.getSelectedItem();
            if (kh != null) {
                List<HocVien> list = hvdao.selectByKhoahoc(kh.getMaKH());
                for (int i = 0; i < list.size(); i++) {
                    HocVien hv = list.get(i);
                    String hoten = nhdao.selectById(hv.getMaNH()).getHoTen();
                    //model.addRow(new Object[] {i + 1, hv.getMaHV(), hv.getMaNH(), hoten, hv.getDiem()});
                    Object[] row = {i + 1, hv.getMaHV(), hv.getMaNH(), hoten, hv.getDiem()};
                    double diem = hv.getDiem();
                    if (rdoTatCa.isSelected()) { //chọn tất cả thì add tất cả bản ghi vào 
                        model.addRow(row);
                    } else if (rdoDaNhap.isSelected() && diem >= 1) { //chọn đã nhập thì chỉ add bản ghi điểm 1-10
                        model.addRow(row);
                    } else if (rdoChuaNhap.isSelected() && diem == 0) { //chọn chưa nhập thì chỉ nhập bản ghi điểm 0
                        model.addRow(row);
                    }
                }
                this.fillTableNH();
            }
        } catch (Exception e) {
            XDialog.alert(this, "Lỗi truy vấn dữ liệu học viên!");
            e.printStackTrace();
        }
    }

    private void fillTableNH() {
        DefaultTableModel model = (DefaultTableModel) tblNguoiHoc.getModel();
        model.setRowCount(0);
        try {
            KhoaHoc kh = (KhoaHoc) cboKhoaHoc.getSelectedItem();
            String keyword = txtSearch.getText();
            List<NguoiHoc> list = nhdao.selectByKeyword(kh.getMaKH(), keyword);
            for (NguoiHoc nh : list) {
                model.addRow(new Object[]{
                    nh.getMaNH(),
                    nh.getHoTen(),
                    nh.isGioiTinh() ? "Nam" : "Nữ",
                    XDate.toString(nh.getNgaySinh()),
                    nh.getDienThoai(),
                    nh.getEmail()
                });
            }
        } catch (Exception e) {
            XDialog.alert(this, "Lỗi truy vấn dữ liệu người học!");
            e.printStackTrace();
        }
    }

    private void insert() {
        KhoaHoc kh = (KhoaHoc) cboKhoaHoc.getSelectedItem();
        int[] rows = tblNguoiHoc.getSelectedRows();
        for (int row : rows) {
            String manh = (String) tblNguoiHoc.getValueAt(row, 0);
            HocVien hv = new HocVien();
            hv.setMaKH(kh.getMaKH());
            hv.setDiem(0);
            hv.setMaNH(manh);
            hvdao.insert(hv);
        }
        this.fillTableHV();
        this.tabs.setSelectedIndex(0);
    }

    private void delete() {
        if (!XAuth.isManager()) {
            XDialog.alert(this, "Chỉ trưởng phòng mới được phép xóa học viên\nbạn chỉ có quyền thêm học viên và điểm!");
        } else {
            int[] rows = tblHocVien.getSelectedRows();
            if (rows.length > 0 && XDialog.confirm(this, "Bạn có thực sự muốn xóa các học viên đã chọn không?")) {
                for (int row : rows) {
                    int mahv = (Integer) tblHocVien.getValueAt(row, 1);
                    hvdao.delete(mahv);
                }
                this.fillTableHV();
            }
        }
    }

    private void update() {
        int n = tblHocVien.getRowCount();
        for (int i = 0; i < n; i++) {
            int mahv = (Integer) tblHocVien.getValueAt(i, 1);
            double diem = (Double) tblHocVien.getValueAt(i, 4);
            HocVien hv = hvdao.selectById(mahv);
            hv.setDiem(diem);
            hvdao.update(hv);
        }
        XDialog.alert(this, "Cập nhật điểm cho học viên thành công!");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgrNhap = new javax.swing.ButtonGroup();
        pnlTitle = new javax.swing.JPanel();
        lblIcon = new javax.swing.JLabel();
        lblQLHV = new javax.swing.JLabel();
        lblHocVien = new javax.swing.JLabel();
        lblExit = new javax.swing.JLabel();
        jpnlHocVien = new javax.swing.JPanel();
        pnlCombobox = new javax.swing.JPanel();
        pnlChuyenDe = new javax.swing.JPanel();
        cboChuyenDe = new javax.swing.JComboBox<>();
        pnlKhoaHoc = new javax.swing.JPanel();
        cboKhoaHoc = new javax.swing.JComboBox<>();
        tabs = new javax.swing.JTabbedPane();
        pnlHocVien = new javax.swing.JPanel();
        jspHocVien = new javax.swing.JScrollPane();
        tblHocVien = new javax.swing.JTable();
        pnlButton = new javax.swing.JPanel();
        pnlNhap = new javax.swing.JPanel();
        rdoTatCa = new javax.swing.JRadioButton();
        rdoDaNhap = new javax.swing.JRadioButton();
        rdoChuaNhap = new javax.swing.JRadioButton();
        btnExport = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        pnlNguoiHoc = new javax.swing.JPanel();
        jspList = new javax.swing.JScrollPane();
        tblNguoiHoc = new javax.swing.JTable();
        pnlSearch = new javax.swing.JPanel();
        txtSearch = new javax.swing.JTextField();
        lblSearch = new javax.swing.JLabel();
        pnlButton1 = new javax.swing.JPanel();
        btnInsert = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        pnlTitle.setBackground(new java.awt.Color(228, 141, 120));

        lblIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/icons8-customer.png"))); // NOI18N

        lblQLHV.setFont(new java.awt.Font("Be Vietnam Pro", 1, 36)); // NOI18N
        lblQLHV.setForeground(new java.awt.Color(255, 255, 255));
        lblQLHV.setText("LapTrinhCity - EduSys");

        lblHocVien.setFont(new java.awt.Font("Be Vietnam Pro", 1, 18)); // NOI18N
        lblHocVien.setForeground(new java.awt.Color(255, 255, 255));
        lblHocVien.setText("Quản Lý Học Viên");

        lblExit.setFont(new java.awt.Font("Be Vietnam Pro", 1, 24)); // NOI18N
        lblExit.setForeground(new java.awt.Color(255, 255, 255));
        lblExit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblExit.setText("x");
        lblExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblExitMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnlTitleLayout = new javax.swing.GroupLayout(pnlTitle);
        pnlTitle.setLayout(pnlTitleLayout);
        pnlTitleLayout.setHorizontalGroup(
            pnlTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTitleLayout.createSequentialGroup()
                .addGap(92, 92, 92)
                .addComponent(lblIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(pnlTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlTitleLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(pnlTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblHocVien)
                            .addComponent(lblQLHV))
                        .addContainerGap(445, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTitleLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblExit, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        pnlTitleLayout.setVerticalGroup(
            pnlTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTitleLayout.createSequentialGroup()
                .addGroup(pnlTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlTitleLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(pnlTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlTitleLayout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(lblQLHV, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblHocVien))
                            .addComponent(lblIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lblExit))
                .addGap(25, 25, 25))
        );

        jpnlHocVien.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(228, 141, 120), 2));
        jpnlHocVien.setPreferredSize(new java.awt.Dimension(1050, 622));
        jpnlHocVien.setRequestFocusEnabled(false);

        pnlCombobox.setBackground(new java.awt.Color(228, 246, 250));
        pnlCombobox.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 0, 0, 0));
        pnlCombobox.setLayout(new java.awt.GridLayout(1, 0));

        pnlChuyenDe.setBackground(new java.awt.Color(228, 246, 250));
        pnlChuyenDe.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Chuyên Đề", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Be Vietnam Pro", 0, 16), new java.awt.Color(255, 0, 0))); // NOI18N
        pnlChuyenDe.setPreferredSize(new java.awt.Dimension(41, 65));

        cboChuyenDe.setFont(new java.awt.Font("Be Vietnam Pro", 0, 14)); // NOI18N
        cboChuyenDe.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cboChuyenDe.setPreferredSize(new java.awt.Dimension(31, 35));
        cboChuyenDe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboChuyenDeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlChuyenDeLayout = new javax.swing.GroupLayout(pnlChuyenDe);
        pnlChuyenDe.setLayout(pnlChuyenDeLayout);
        pnlChuyenDeLayout.setHorizontalGroup(
            pnlChuyenDeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlChuyenDeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cboChuyenDe, 0, 501, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlChuyenDeLayout.setVerticalGroup(
            pnlChuyenDeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlChuyenDeLayout.createSequentialGroup()
                .addComponent(cboChuyenDe, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pnlCombobox.add(pnlChuyenDe);

        pnlKhoaHoc.setBackground(new java.awt.Color(228, 246, 250));
        pnlKhoaHoc.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Khoá Học", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Be Vietnam Pro", 0, 16), new java.awt.Color(255, 0, 0))); // NOI18N

        cboKhoaHoc.setFont(new java.awt.Font("Be Vietnam Pro", 0, 14)); // NOI18N
        cboKhoaHoc.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cboKhoaHoc.setPreferredSize(new java.awt.Dimension(31, 35));
        cboKhoaHoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboKhoaHocActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlKhoaHocLayout = new javax.swing.GroupLayout(pnlKhoaHoc);
        pnlKhoaHoc.setLayout(pnlKhoaHocLayout);
        pnlKhoaHocLayout.setHorizontalGroup(
            pnlKhoaHocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlKhoaHocLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cboKhoaHoc, 0, 501, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlKhoaHocLayout.setVerticalGroup(
            pnlKhoaHocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlKhoaHocLayout.createSequentialGroup()
                .addComponent(cboKhoaHoc, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 7, Short.MAX_VALUE))
        );

        pnlCombobox.add(pnlKhoaHoc);

        tabs.setFont(new java.awt.Font("Be Vietnam Pro", 0, 15)); // NOI18N

        pnlHocVien.setBackground(new java.awt.Color(228, 246, 250));
        pnlHocVien.setLayout(new java.awt.BorderLayout());

        tblHocVien.setFont(new java.awt.Font("Be Vietnam Pro", 0, 14)); // NOI18N
        tblHocVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "TT", "MÃ HV", "MÃ NH", "HỌ TÊN", "ĐIỂM"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Object.class, java.lang.String.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHocVien.setRowHeight(30);
        tblHocVien.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jspHocVien.setViewportView(tblHocVien);

        pnlHocVien.add(jspHocVien, java.awt.BorderLayout.CENTER);

        pnlButton.setBackground(new java.awt.Color(228, 246, 250));
        pnlButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        pnlButton.setLayout(new javax.swing.BoxLayout(pnlButton, javax.swing.BoxLayout.LINE_AXIS));

        pnlNhap.setBackground(new java.awt.Color(228, 246, 250));

        bgrNhap.add(rdoTatCa);
        rdoTatCa.setFont(new java.awt.Font("Be Vietnam Pro", 0, 15)); // NOI18N
        rdoTatCa.setSelected(true);
        rdoTatCa.setText("Tất cả");
        rdoTatCa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoTatCaActionPerformed(evt);
            }
        });

        bgrNhap.add(rdoDaNhap);
        rdoDaNhap.setFont(new java.awt.Font("Be Vietnam Pro", 0, 15)); // NOI18N
        rdoDaNhap.setText("Đã nhập điểm");
        rdoDaNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoDaNhapActionPerformed(evt);
            }
        });

        bgrNhap.add(rdoChuaNhap);
        rdoChuaNhap.setFont(new java.awt.Font("Be Vietnam Pro", 0, 15)); // NOI18N
        rdoChuaNhap.setText("Chưa nhập điểm");
        rdoChuaNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoChuaNhapActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlNhapLayout = new javax.swing.GroupLayout(pnlNhap);
        pnlNhap.setLayout(pnlNhapLayout);
        pnlNhapLayout.setHorizontalGroup(
            pnlNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNhapLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rdoTatCa)
                .addGap(18, 18, 18)
                .addComponent(rdoDaNhap)
                .addGap(18, 18, 18)
                .addComponent(rdoChuaNhap)
                .addContainerGap(290, Short.MAX_VALUE))
        );
        pnlNhapLayout.setVerticalGroup(
            pnlNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNhapLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(pnlNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoChuaNhap)
                    .addComponent(rdoDaNhap)
                    .addComponent(rdoTatCa, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(9, Short.MAX_VALUE))
        );

        pnlButton.add(pnlNhap);

        btnExport.setBackground(new java.awt.Color(0, 95, 95));
        btnExport.setFont(new java.awt.Font("Be Vietnam Pro", 1, 16)); // NOI18N
        btnExport.setForeground(new java.awt.Color(255, 255, 255));
        btnExport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/icons8-delete.png"))); // NOI18N
        btnExport.setText("Xuất");
        btnExport.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnExport.setMargin(new java.awt.Insets(7, 14, 7, 14));
        btnExport.setPreferredSize(new java.awt.Dimension(110, 45));
        btnExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportActionPerformed(evt);
            }
        });
        pnlButton.add(btnExport);

        btnUpdate.setBackground(new java.awt.Color(0, 95, 95));
        btnUpdate.setFont(new java.awt.Font("Be Vietnam Pro", 1, 16)); // NOI18N
        btnUpdate.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/icons8-update.png"))); // NOI18N
        btnUpdate.setText("Cập nhật");
        btnUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnUpdate.setMargin(new java.awt.Insets(7, 14, 7, 14));
        btnUpdate.setPreferredSize(new java.awt.Dimension(150, 45));
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });
        pnlButton.add(btnUpdate);

        btnDelete.setBackground(new java.awt.Color(0, 95, 95));
        btnDelete.setFont(new java.awt.Font("Be Vietnam Pro", 1, 16)); // NOI18N
        btnDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/icons8-delete.png"))); // NOI18N
        btnDelete.setText("Xoá");
        btnDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnDelete.setMargin(new java.awt.Insets(7, 14, 7, 14));
        btnDelete.setPreferredSize(new java.awt.Dimension(110, 45));
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        pnlButton.add(btnDelete);

        pnlHocVien.add(pnlButton, java.awt.BorderLayout.PAGE_END);

        tabs.addTab("HỌC VIÊN", new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/icons8-hocvien-45px.png")), pnlHocVien); // NOI18N

        pnlNguoiHoc.setBackground(new java.awt.Color(228, 246, 250));
        pnlNguoiHoc.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        pnlNguoiHoc.setLayout(new java.awt.BorderLayout());

        tblNguoiHoc.setFont(new java.awt.Font("Be Vietnam Pro", 0, 14)); // NOI18N
        tblNguoiHoc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "MÃ NH", "HỌ VÀ TÊN", "GIỚI TÍNH", "NGÀY SINH", "ĐIỆN THOẠI", "EMAIL"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNguoiHoc.setToolTipText("");
        tblNguoiHoc.setRowHeight(30);
        tblNguoiHoc.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jspList.setViewportView(tblNguoiHoc);

        pnlNguoiHoc.add(jspList, java.awt.BorderLayout.CENTER);

        pnlSearch.setBackground(new java.awt.Color(228, 246, 250));
        pnlSearch.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        pnlSearch.setLayout(new java.awt.BorderLayout());

        txtSearch.setFont(new java.awt.Font("Be Vietnam Pro", 0, 14)); // NOI18N
        txtSearch.setPreferredSize(new java.awt.Dimension(7, 35));
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });
        pnlSearch.add(txtSearch, java.awt.BorderLayout.CENTER);

        lblSearch.setFont(new java.awt.Font("Be Vietnam Pro", 0, 16)); // NOI18N
        lblSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/icons8-searchmore.png"))); // NOI18N
        lblSearch.setText("Tìm kiếm:");
        lblSearch.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 10));
        pnlSearch.add(lblSearch, java.awt.BorderLayout.LINE_START);

        pnlNguoiHoc.add(pnlSearch, java.awt.BorderLayout.PAGE_START);

        pnlButton1.setBackground(new java.awt.Color(228, 246, 250));
        pnlButton1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        btnInsert.setBackground(new java.awt.Color(0, 95, 95));
        btnInsert.setFont(new java.awt.Font("Be Vietnam Pro", 1, 16)); // NOI18N
        btnInsert.setForeground(new java.awt.Color(255, 255, 255));
        btnInsert.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/icons8-add-to-collection.png"))); // NOI18N
        btnInsert.setText("Thêm vào khóa học");
        btnInsert.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnInsert.setMargin(new java.awt.Insets(5, 14, 5, 14));
        btnInsert.setPreferredSize(new java.awt.Dimension(235, 45));
        btnInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertActionPerformed(evt);
            }
        });
        pnlButton1.add(btnInsert);

        pnlNguoiHoc.add(pnlButton1, java.awt.BorderLayout.PAGE_END);

        tabs.addTab("NGƯỜI HỌC", new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/icons8-nguoihoc-45px.png")), pnlNguoiHoc); // NOI18N

        javax.swing.GroupLayout jpnlHocVienLayout = new javax.swing.GroupLayout(jpnlHocVien);
        jpnlHocVien.setLayout(jpnlHocVienLayout);
        jpnlHocVienLayout.setHorizontalGroup(
            jpnlHocVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jpnlHocVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(tabs)
                .addComponent(pnlCombobox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpnlHocVienLayout.setVerticalGroup(
            jpnlHocVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 620, Short.MAX_VALUE)
            .addGroup(jpnlHocVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jpnlHocVienLayout.createSequentialGroup()
                    .addComponent(pnlCombobox, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(tabs, javax.swing.GroupLayout.PREFERRED_SIZE, 534, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jpnlHocVien, javax.swing.GroupLayout.DEFAULT_SIZE, 1076, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jpnlHocVien, javax.swing.GroupLayout.DEFAULT_SIZE, 624, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblExitMouseClicked
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_lblExitMouseClicked

    private void cboChuyenDeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboChuyenDeActionPerformed
        // TODO add your handling code here:
        this.fillComboBoxKH();
    }//GEN-LAST:event_cboChuyenDeActionPerformed

    private void cboKhoaHocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboKhoaHocActionPerformed
        // TODO add your handling code here:
        this.fillTableHV();
    }//GEN-LAST:event_cboKhoaHocActionPerformed

    private void rdoTatCaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoTatCaActionPerformed
        // TODO add your handling code here:
        this.fillTableHV();
    }//GEN-LAST:event_rdoTatCaActionPerformed

    private void rdoDaNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoDaNhapActionPerformed
        // TODO add your handling code here:
        this.fillTableHV();
    }//GEN-LAST:event_rdoDaNhapActionPerformed

    private void rdoChuaNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoChuaNhapActionPerformed
        // TODO add your handling code here:
        this.fillTableHV();
    }//GEN-LAST:event_rdoChuaNhapActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        this.update();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        // TODO add your handling code here:
        this.fillTableNH();
    }//GEN-LAST:event_txtSearchKeyReleased

    private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
        // TODO add your handling code here:
        this.insert();
    }//GEN-LAST:event_btnInsertActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        this.delete();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportActionPerformed
        // TODO add your handling code here:
        String x = String.valueOf(cboKhoaHoc.getSelectedItem());
        XExcel.writeToExcel(tblHocVien, x);
    }//GEN-LAST:event_btnExportActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrNhap;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnExport;
    private javax.swing.JButton btnInsert;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cboChuyenDe;
    private javax.swing.JComboBox<String> cboKhoaHoc;
    private javax.swing.JPanel jpnlHocVien;
    private javax.swing.JScrollPane jspHocVien;
    private javax.swing.JScrollPane jspList;
    private javax.swing.JLabel lblExit;
    private javax.swing.JLabel lblHocVien;
    private javax.swing.JLabel lblIcon;
    private javax.swing.JLabel lblQLHV;
    private javax.swing.JLabel lblSearch;
    private javax.swing.JPanel pnlButton;
    private javax.swing.JPanel pnlButton1;
    private javax.swing.JPanel pnlChuyenDe;
    private javax.swing.JPanel pnlCombobox;
    private javax.swing.JPanel pnlHocVien;
    private javax.swing.JPanel pnlKhoaHoc;
    private javax.swing.JPanel pnlNguoiHoc;
    private javax.swing.JPanel pnlNhap;
    private javax.swing.JPanel pnlSearch;
    private javax.swing.JPanel pnlTitle;
    private javax.swing.JRadioButton rdoChuaNhap;
    private javax.swing.JRadioButton rdoDaNhap;
    private javax.swing.JRadioButton rdoTatCa;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblHocVien;
    private javax.swing.JTable tblNguoiHoc;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}

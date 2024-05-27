/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.ui;

import com.edusys.dao.ChuyenDeDAO;
import com.edusys.dao.KhoaHocDAO;
import com.edusys.model.ChuyenDe;
import com.edusys.model.KhoaHoc;
import com.edusys.utils.XAuth;
import com.edusys.utils.XDate;
import com.edusys.utils.XDialog;
import com.edusys.utils.XExcel;
import com.edusys.utils.XValidate;
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
public class QLKhoaHocJD extends javax.swing.JDialog {

    KhoaHocDAO khdao = new KhoaHocDAO();
    ChuyenDeDAO cddao = new ChuyenDeDAO();
    int row = -1;

    /**
     * Creates new form QLKhoaHocJDialog
     *
     * @param parent
     * @param modal
     */
    public QLKhoaHocJD(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
    }

    private void init() {
        setLocationRelativeTo(null);
        this.fillComboBoxCD();

        DefaultTableCellRenderer render = new DefaultTableCellRenderer();
        render.setHorizontalAlignment(javax.swing.JLabel.CENTER);

        TableColumnModel kh = tblKhoaHoc.getColumnModel();
        kh.getColumn(0).setCellRenderer(render);
        kh.getColumn(1).setCellRenderer(render);
        kh.getColumn(2).setCellRenderer(render);
        kh.getColumn(3).setCellRenderer(render);
        kh.getColumn(4).setCellRenderer(render);
        kh.getColumn(5).setCellRenderer(render);
    }

    private void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblKhoaHoc.getModel();
        model.setRowCount(0);
        try {
            ChuyenDe cd = (ChuyenDe) cboChuyenDe.getSelectedItem();
            List<KhoaHoc> list = khdao.selectByChuyenDe(cd.getMaCD());
            for (KhoaHoc kh : list) {
                Object[] row = {
                    kh.getMaKH(),
                    kh.getThoiLuong(),
                    kh.getHocPhi(),
                    XDate.toString(kh.getNgayKG()),
                    kh.getMaNV(),
                    XDate.toString(kh.getNgayTao())
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            XDialog.alert(this, "Lỗi truy vấn dữ liệu khoá học!");
            e.printStackTrace();
        }
    }

    private void insert() {
        KhoaHoc model = getForm();
        model.setNgayTao(XDate.now());
        try {
            khdao.insert(model);
            this.fillTable();
            this.clearForm();
            XDialog.alert(this, "Thêm khoá học mới thành công!");
        } catch (Exception e) {
            XDialog.alert(this, "Thêm khoá học mới thất bại!");
            e.printStackTrace();
        }
    }

    private void update() {
        KhoaHoc model = getForm();
        try {
            khdao.update(model);
            this.fillTable();
            XDialog.alert(this, "Cập nhật khoá học thành công!");
        } catch (Exception e) {
            XDialog.alert(this, "Cập nhật khoá học thất bại!");
            e.printStackTrace();
        }
    }

    private void delete() {
        if (!XAuth.isManager()) {
            XDialog.alert(this, "Chỉ trưởng phòng mới được phép xóa khoá học!");
        } else {
            if (XDialog.confirm(this, "Bạn có thực sự muốn xóa khóa học này không?")) {
                Integer makh = Integer.valueOf(cboChuyenDe.getToolTipText());
                try {
                    khdao.delete(makh);
                    this.fillTable();
                    this.clearForm();
                    XDialog.alert(this, "Xóa khoá học thành công!");
                } catch (Exception e) {
                    XDialog.alert(this, "Xóa khoá học thất bại!");
                    e.printStackTrace();
                }
            }
        }
    }

    private void clearForm() {
        ChuyenDe cd = (ChuyenDe) cboChuyenDe.getSelectedItem();
        KhoaHoc kh = new KhoaHoc();
        kh.setMaCD(cd.getMaCD());
        kh.setMaNV(XAuth.user.getMaNV());
        kh.setNgayKG(XDate.addDays(30));
        kh.setNgayTao(XDate.now());
        this.setForm(kh);
    }

    private void edit() {
        ChuyenDe cd = (ChuyenDe) cboChuyenDe.getSelectedItem();
        Integer makh = (Integer) tblKhoaHoc.getValueAt(row, 0);
        KhoaHoc kh = khdao.selectById(makh);
        this.setForm(kh);
        this.updateStatus();
        tabs.setSelectedIndex(0);
        txtTenCD.setText(cd.getTenCD());
    }

    private void setForm(KhoaHoc kh) {
        cboChuyenDe.setToolTipText(String.valueOf(kh.getMaKH()));
        cboChuyenDe.setSelectedItem(cddao.selectById(kh.getMaCD()));
        txtNgayKG.setText(XDate.toString(kh.getNgayKG()));
        txtHocPhi.setText(String.valueOf(kh.getHocPhi()));
        txtThoiLuong.setText(String.valueOf(kh.getThoiLuong()));
        txtNguoiTao.setText(kh.getMaNV());
        txtNgayTao.setText(XDate.toString(kh.getNgayTao()));
        txtGhiChu.setText(kh.getGhiChu());
    }

    private KhoaHoc getForm() {
        ChuyenDe cd = (ChuyenDe) cboChuyenDe.getSelectedItem();
        KhoaHoc kh = new KhoaHoc();
        kh.setMaCD(cd.getMaCD());
        kh.setNgayKG(XDate.toDate(txtNgayKG.getText()));
        kh.setHocPhi(Double.valueOf(txtHocPhi.getText()));
        kh.setThoiLuong(Integer.valueOf(txtThoiLuong.getText()));
        kh.setGhiChu(txtGhiChu.getText());
        kh.setMaNV(XAuth.user.getMaNV());
        kh.setNgayTao(XDate.toDate(txtNgayTao.getText()));
        kh.setMaKH(Integer.valueOf(cboChuyenDe.getToolTipText()));
        return kh;
    }

    private void first() {
        this.row = 0;
        this.edit();
    }

    private void prev() {
        if (this.row > 0) {
            this.row--;
            this.edit();
        }
    }

    private void next() {
        if (this.row < tblKhoaHoc.getRowCount() - 1) {
            this.row++;
            this.edit();
        }
    }

    private void last() {
        this.row = tblKhoaHoc.getRowCount() - 1;
        this.edit();
    }

    private void updateStatus() {
        boolean edit = (this.row >= 0);
        boolean first = (this.row == 0);
        boolean last = (this.row == tblKhoaHoc.getRowCount() - 1);
        // Trạng thái form
        txtNguoiTao.setEditable(!edit);
        btnInsert.setEnabled(!edit);
        btnUpdate.setEnabled(edit);
        btnDelete.setEnabled(edit);

        // Trạng thái điều hướng
        btnFirst.setEnabled(edit && !first);
        btnPrev.setEnabled(edit && !first);
        btnNext.setEnabled(edit && !last);
        btnLast.setEnabled(edit && !last);
    }

    @SuppressWarnings("unchecked")
    private void fillComboBoxCD() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboChuyenDe.getModel();
        model.removeAllElements();
        List<ChuyenDe> list = cddao.selectAll();
        for (ChuyenDe cd : list) {
            model.addElement(cd);
        }
    }

    private void selectCD() {
        ChuyenDe cd = (ChuyenDe) cboChuyenDe.getSelectedItem();
        txtThoiLuong.setText(String.valueOf(cd.getThoiLuong()));
        txtHocPhi.setText(String.valueOf(cd.getHocPhi()));
        txtTenCD.setText(cd.getTenCD());
        txtGhiChu.setText(cd.getTenCD());

        this.fillTable();
        this.row = -1;
        this.updateStatus();
        tabs.setSelectedIndex(1);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileChooser = new javax.swing.JFileChooser();
        pnlTitle = new javax.swing.JPanel();
        lblIcon = new javax.swing.JLabel();
        lblQLHV = new javax.swing.JLabel();
        lblKhoaHoc = new javax.swing.JLabel();
        lblExit = new javax.swing.JLabel();
        jpnlKhoaHoc = new javax.swing.JPanel();
        pnlChuyenDe = new javax.swing.JPanel();
        cboChuyenDe = new javax.swing.JComboBox<>();
        tabs = new javax.swing.JTabbedPane();
        pnlEdit = new javax.swing.JPanel();
        pnlForm = new javax.swing.JPanel();
        lblTenCD = new javax.swing.JLabel();
        txtTenCD = new javax.swing.JTextField();
        lblHocPhi = new javax.swing.JLabel();
        txtHocPhi = new javax.swing.JTextField();
        lblNguoiTao = new javax.swing.JLabel();
        txtNguoiTao = new javax.swing.JTextField();
        lblNgayKG = new javax.swing.JLabel();
        txtNgayKG = new javax.swing.JTextField();
        lblThoiLuong = new javax.swing.JLabel();
        txtThoiLuong = new javax.swing.JTextField();
        lblNgayTao = new javax.swing.JLabel();
        txtNgayTao = new javax.swing.JTextField();
        lblGhiChu = new javax.swing.JLabel();
        jspGhiChu = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        pnlButton = new javax.swing.JPanel();
        btnInsert = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnExport = new javax.swing.JButton();
        pnlControl = new javax.swing.JPanel();
        btnFirst = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        pnlList = new javax.swing.JPanel();
        jspKhoaHoc = new javax.swing.JScrollPane();
        tblKhoaHoc = new javax.swing.JTable();

        fileChooser.setDialogTitle("Chọn logo chuyên đề");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        pnlTitle.setBackground(new java.awt.Color(228, 141, 120));

        lblIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/icons8-customer.png"))); // NOI18N

        lblQLHV.setFont(new java.awt.Font("Be Vietnam Pro", 1, 36)); // NOI18N
        lblQLHV.setForeground(new java.awt.Color(255, 255, 255));
        lblQLHV.setText("LapTrinhCity - EduSys");

        lblKhoaHoc.setFont(new java.awt.Font("Be Vietnam Pro", 1, 18)); // NOI18N
        lblKhoaHoc.setForeground(new java.awt.Color(255, 255, 255));
        lblKhoaHoc.setText("Quản Lý Khoá Học");

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
                            .addComponent(lblKhoaHoc)
                            .addComponent(lblQLHV))
                        .addContainerGap(449, Short.MAX_VALUE))
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
                                .addComponent(lblKhoaHoc))
                            .addComponent(lblIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lblExit))
                .addGap(25, 25, 25))
        );

        jpnlKhoaHoc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(228, 141, 120), 2));

        pnlChuyenDe.setBackground(new java.awt.Color(228, 246, 250));
        pnlChuyenDe.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Chuyên Đề", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Be Vietnam Pro", 0, 16), new java.awt.Color(255, 0, 0))); // NOI18N
        pnlChuyenDe.setPreferredSize(new java.awt.Dimension(41, 70));

        cboChuyenDe.setFont(new java.awt.Font("Be Vietnam Pro", 0, 14)); // NOI18N
        cboChuyenDe.setPreferredSize(new java.awt.Dimension(31, 30));
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
                .addComponent(cboChuyenDe, 0, 1028, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlChuyenDeLayout.setVerticalGroup(
            pnlChuyenDeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cboChuyenDe, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        tabs.setFont(new java.awt.Font("Be Vietnam Pro", 0, 15)); // NOI18N

        pnlEdit.setFont(new java.awt.Font("Be Vietnam Pro", 0, 14)); // NOI18N

        lblTenCD.setFont(new java.awt.Font("Be Vietnam Pro", 0, 15)); // NOI18N
        lblTenCD.setText("Chuyên đề");

        txtTenCD.setEditable(false);
        txtTenCD.setFont(new java.awt.Font("Be Vietnam Pro", 0, 14)); // NOI18N
        txtTenCD.setForeground(new java.awt.Color(255, 51, 0));
        txtTenCD.setEnabled(false);
        txtTenCD.setPreferredSize(new java.awt.Dimension(7, 30));

        lblHocPhi.setFont(new java.awt.Font("Be Vietnam Pro", 0, 15)); // NOI18N
        lblHocPhi.setText("Học phí");

        txtHocPhi.setEditable(false);
        txtHocPhi.setFont(new java.awt.Font("Be Vietnam Pro", 0, 14)); // NOI18N
        txtHocPhi.setEnabled(false);
        txtHocPhi.setPreferredSize(new java.awt.Dimension(7, 30));

        lblNguoiTao.setFont(new java.awt.Font("Be Vietnam Pro", 0, 15)); // NOI18N
        lblNguoiTao.setText("Người tạo");

        txtNguoiTao.setEditable(false);
        txtNguoiTao.setFont(new java.awt.Font("Be Vietnam Pro", 0, 14)); // NOI18N
        txtNguoiTao.setEnabled(false);
        txtNguoiTao.setPreferredSize(new java.awt.Dimension(7, 30));

        lblNgayKG.setFont(new java.awt.Font("Be Vietnam Pro", 0, 15)); // NOI18N
        lblNgayKG.setText("Ngày khai giảng (dd/mm/yyyy)");

        txtNgayKG.setFont(new java.awt.Font("Be Vietnam Pro", 0, 14)); // NOI18N
        txtNgayKG.setToolTipText("Định dạng dd/MM/yyyy");
        txtNgayKG.setPreferredSize(new java.awt.Dimension(7, 30));

        lblThoiLuong.setFont(new java.awt.Font("Be Vietnam Pro", 0, 15)); // NOI18N
        lblThoiLuong.setText("Thời lượng (giờ)");

        txtThoiLuong.setEditable(false);
        txtThoiLuong.setFont(new java.awt.Font("Be Vietnam Pro", 0, 14)); // NOI18N
        txtThoiLuong.setEnabled(false);
        txtThoiLuong.setPreferredSize(new java.awt.Dimension(7, 30));

        lblNgayTao.setFont(new java.awt.Font("Be Vietnam Pro", 0, 15)); // NOI18N
        lblNgayTao.setText("Ngày tạo (dd/mm/yyyy)");

        txtNgayTao.setEditable(false);
        txtNgayTao.setFont(new java.awt.Font("Be Vietnam Pro", 0, 14)); // NOI18N
        txtNgayTao.setEnabled(false);
        txtNgayTao.setPreferredSize(new java.awt.Dimension(7, 30));

        javax.swing.GroupLayout pnlFormLayout = new javax.swing.GroupLayout(pnlForm);
        pnlForm.setLayout(pnlFormLayout);
        pnlFormLayout.setHorizontalGroup(
            pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFormLayout.createSequentialGroup()
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblTenCD)
                    .addComponent(lblHocPhi, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNguoiTao)
                    .addComponent(txtTenCD, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
                    .addComponent(txtHocPhi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNguoiTao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 78, Short.MAX_VALUE)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblNgayKG)
                    .addComponent(txtNgayKG, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
                    .addComponent(lblThoiLuong)
                    .addComponent(txtThoiLuong, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblNgayTao)
                    .addComponent(txtNgayTao, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)))
        );
        pnlFormLayout.setVerticalGroup(
            pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFormLayout.createSequentialGroup()
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTenCD)
                    .addComponent(lblNgayKG))
                .addGap(5, 5, 5)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNgayKG, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTenCD, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblHocPhi)
                    .addComponent(lblThoiLuong))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtHocPhi, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtThoiLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblNgayTao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblNguoiTao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNguoiTao, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNgayTao, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        lblGhiChu.setFont(new java.awt.Font("Be Vietnam Pro", 0, 15)); // NOI18N
        lblGhiChu.setText("Ghi chú");

        txtGhiChu.setColumns(20);
        txtGhiChu.setFont(new java.awt.Font("Be Vietnam Pro", 0, 14)); // NOI18N
        txtGhiChu.setRows(5);
        txtGhiChu.setMargin(new java.awt.Insets(4, 4, 4, 4));
        jspGhiChu.setViewportView(txtGhiChu);

        jPanel1.setBackground(new java.awt.Color(228, 246, 250));

        btnInsert.setBackground(new java.awt.Color(0, 95, 95));
        btnInsert.setFont(new java.awt.Font("Be Vietnam Pro", 1, 16)); // NOI18N
        btnInsert.setForeground(new java.awt.Color(255, 255, 255));
        btnInsert.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/icons8-add-to-collection.png"))); // NOI18N
        btnInsert.setText("Thêm");
        btnInsert.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnInsert.setPreferredSize(new java.awt.Dimension(115, 45));
        btnInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertActionPerformed(evt);
            }
        });
        pnlButton.add(btnInsert);

        btnUpdate.setBackground(new java.awt.Color(0, 95, 95));
        btnUpdate.setFont(new java.awt.Font("Be Vietnam Pro", 1, 16)); // NOI18N
        btnUpdate.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/icons8-update.png"))); // NOI18N
        btnUpdate.setText("Sửa");
        btnUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnUpdate.setPreferredSize(new java.awt.Dimension(110, 45));
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
        btnDelete.setText("Xóa");
        btnDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnDelete.setPreferredSize(new java.awt.Dimension(110, 45));
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        pnlButton.add(btnDelete);

        btnClear.setBackground(new java.awt.Color(0, 95, 95));
        btnClear.setFont(new java.awt.Font("Be Vietnam Pro", 1, 16)); // NOI18N
        btnClear.setForeground(new java.awt.Color(255, 255, 255));
        btnClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/icons8-add.png"))); // NOI18N
        btnClear.setText("Mới");
        btnClear.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnClear.setPreferredSize(new java.awt.Dimension(110, 45));
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });
        pnlButton.add(btnClear);

        btnExport.setBackground(new java.awt.Color(0, 95, 95));
        btnExport.setFont(new java.awt.Font("Be Vietnam Pro", 1, 16)); // NOI18N
        btnExport.setForeground(new java.awt.Color(255, 255, 255));
        btnExport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/icons8-export-excel.png"))); // NOI18N
        btnExport.setText("Xuất");
        btnExport.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnExport.setPreferredSize(new java.awt.Dimension(110, 45));
        btnExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportActionPerformed(evt);
            }
        });
        pnlButton.add(btnExport);

        btnFirst.setBackground(new java.awt.Color(51, 51, 255));
        btnFirst.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/icon-first.png"))); // NOI18N
        btnFirst.setPreferredSize(new java.awt.Dimension(60, 45));
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });
        pnlControl.add(btnFirst);

        btnPrev.setBackground(new java.awt.Color(51, 51, 255));
        btnPrev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/icon-prev.png"))); // NOI18N
        btnPrev.setPreferredSize(new java.awt.Dimension(60, 45));
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });
        pnlControl.add(btnPrev);

        btnNext.setBackground(new java.awt.Color(255, 153, 0));
        btnNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/icon-next.png"))); // NOI18N
        btnNext.setPreferredSize(new java.awt.Dimension(60, 45));
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });
        pnlControl.add(btnNext);

        btnLast.setBackground(new java.awt.Color(255, 153, 0));
        btnLast.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/icon-last.png"))); // NOI18N
        btnLast.setPreferredSize(new java.awt.Dimension(60, 45));
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });
        pnlControl.add(btnLast);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pnlControl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnlControl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlEditLayout = new javax.swing.GroupLayout(pnlEdit);
        pnlEdit.setLayout(pnlEditLayout);
        pnlEditLayout.setHorizontalGroup(
            pnlEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEditLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jspGhiChu)
                    .addComponent(pnlForm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlEditLayout.createSequentialGroup()
                        .addComponent(lblGhiChu)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlEditLayout.setVerticalGroup(
            pnlEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlEditLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblGhiChu)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jspGhiChu, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        tabs.addTab("CẬP NHẬT", new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/icons8-edit-profile.png")), pnlEdit); // NOI18N

        pnlList.setBackground(new java.awt.Color(228, 246, 250));
        pnlList.setFont(new java.awt.Font("Be Vietnam Pro", 0, 14)); // NOI18N

        tblKhoaHoc.setFont(new java.awt.Font("Be Vietnam Pro", 0, 14)); // NOI18N
        tblKhoaHoc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "MÃ KH", "THỜI LƯỢNG", "HỌC PHÍ", "KHAI GIẢNG", "NGƯỜI TẠO", "NGÀY TẠO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblKhoaHoc.setToolTipText("");
        tblKhoaHoc.setRowHeight(30);
        tblKhoaHoc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKhoaHocMouseClicked(evt);
            }
        });
        jspKhoaHoc.setViewportView(tblKhoaHoc);

        javax.swing.GroupLayout pnlListLayout = new javax.swing.GroupLayout(pnlList);
        pnlList.setLayout(pnlListLayout);
        pnlListLayout.setHorizontalGroup(
            pnlListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspKhoaHoc, javax.swing.GroupLayout.DEFAULT_SIZE, 1050, Short.MAX_VALUE)
        );
        pnlListLayout.setVerticalGroup(
            pnlListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspKhoaHoc, javax.swing.GroupLayout.DEFAULT_SIZE, 457, Short.MAX_VALUE)
        );

        tabs.addTab("DANH SÁCH", new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/icons8-bulleted-list.png")), pnlList); // NOI18N

        javax.swing.GroupLayout jpnlKhoaHocLayout = new javax.swing.GroupLayout(jpnlKhoaHoc);
        jpnlKhoaHoc.setLayout(jpnlKhoaHocLayout);
        jpnlKhoaHocLayout.setHorizontalGroup(
            jpnlKhoaHocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlChuyenDe, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1076, Short.MAX_VALUE)
            .addGroup(jpnlKhoaHocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(tabs, javax.swing.GroupLayout.Alignment.TRAILING))
        );
        jpnlKhoaHocLayout.setVerticalGroup(
            jpnlKhoaHocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlKhoaHocLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlChuyenDe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 517, Short.MAX_VALUE))
            .addGroup(jpnlKhoaHocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnlKhoaHocLayout.createSequentialGroup()
                    .addGap(0, 80, Short.MAX_VALUE)
                    .addComponent(tabs, javax.swing.GroupLayout.PREFERRED_SIZE, 513, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jpnlKhoaHoc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jpnlKhoaHoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblExitMouseClicked
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_lblExitMouseClicked

    private void cboChuyenDeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboChuyenDeActionPerformed
        // TODO add your handling code here:
        this.selectCD();
    }//GEN-LAST:event_cboChuyenDeActionPerformed

    private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
        // TODO add your handling code here:
        if (XValidate.checkNullText(txtNgayKG)) {
            if (XValidate.checkDate(txtNgayKG)) {
                if (XValidate.check5Ngay(txtNgayKG, txtNgayTao)) {
                    this.insert();
                }
            }
        }
    }//GEN-LAST:event_btnInsertActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        if (XValidate.checkNullText(txtNgayKG)) {
            if (XValidate.checkDate(txtNgayKG)) {
                if (XValidate.check5Ngay(txtNgayKG, txtNgayTao)) {
                    this.update();
                }
            }
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        this.delete();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        // TODO add your handling code here:
        this.clearForm();
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        // TODO add your handling code here:
        this.first();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        // TODO add your handling code here:
        this.prev();
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        // TODO add your handling code here:
        this.next();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        // TODO add your handling code here:
        this.last();
    }//GEN-LAST:event_btnLastActionPerformed

    private void tblKhoaHocMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKhoaHocMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            this.row = tblKhoaHoc.getSelectedRow();
            this.edit();
        }
    }//GEN-LAST:event_tblKhoaHocMouseClicked

    private void btnExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportActionPerformed
        // TODO add your handling code here:
        String x = String.valueOf(cboChuyenDe.getSelectedItem());
        XExcel.writeToExcel(tblKhoaHoc, x);
    }//GEN-LAST:event_btnExportActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnExport;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnInsert;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cboChuyenDe;
    private javax.swing.JFileChooser fileChooser;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jpnlKhoaHoc;
    private javax.swing.JScrollPane jspGhiChu;
    private javax.swing.JScrollPane jspKhoaHoc;
    private javax.swing.JLabel lblExit;
    private javax.swing.JLabel lblGhiChu;
    private javax.swing.JLabel lblHocPhi;
    private javax.swing.JLabel lblIcon;
    private javax.swing.JLabel lblKhoaHoc;
    private javax.swing.JLabel lblNgayKG;
    private javax.swing.JLabel lblNgayTao;
    private javax.swing.JLabel lblNguoiTao;
    private javax.swing.JLabel lblQLHV;
    private javax.swing.JLabel lblTenCD;
    private javax.swing.JLabel lblThoiLuong;
    private javax.swing.JPanel pnlButton;
    private javax.swing.JPanel pnlChuyenDe;
    private javax.swing.JPanel pnlControl;
    private javax.swing.JPanel pnlEdit;
    private javax.swing.JPanel pnlForm;
    private javax.swing.JPanel pnlList;
    private javax.swing.JPanel pnlTitle;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblKhoaHoc;
    private javax.swing.JTextArea txtGhiChu;
    private javax.swing.JTextField txtHocPhi;
    private javax.swing.JTextField txtNgayKG;
    private javax.swing.JTextField txtNgayTao;
    private javax.swing.JTextField txtNguoiTao;
    private javax.swing.JTextField txtTenCD;
    private javax.swing.JTextField txtThoiLuong;
    // End of variables declaration//GEN-END:variables
}

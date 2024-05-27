/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.ui;

import com.edusys.dao.NguoiHocDAO;
import com.edusys.model.KhoaHoc;
import com.edusys.model.NguoiHoc;
import com.edusys.utils.XAuth;
import com.edusys.utils.XDate;
import com.edusys.utils.XDialog;
import com.edusys.utils.XExcel;
import com.edusys.utils.XImage;
import com.edusys.utils.XValidate;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author duyplus
 */
public class QLNguoiHocJD extends javax.swing.JDialog {

    NguoiHocDAO nhdao = new NguoiHocDAO();
    int row = -1;

    /**
     * Creates new form QLNguoiHocJDialog
     *
     * @param parent
     * @param modal
     */
    public QLNguoiHocJD(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
    }

    private void init() {
        setLocationRelativeTo(null);
        this.fillTable();
        this.row = -1;
        this.updateStatus();

        DefaultTableCellRenderer render = new DefaultTableCellRenderer();
        render.setHorizontalAlignment(javax.swing.JLabel.CENTER);

        TableColumnModel kh = tblNguoiHoc.getColumnModel();
        kh.getColumn(0).setMaxWidth(100);
        kh.getColumn(1).setMinWidth(170);
        kh.getColumn(2).setMaxWidth(100);
        kh.getColumn(5).setMinWidth(100);
        kh.getColumn(6).setMaxWidth(100);
        kh.getColumn(8).setMinWidth(10);

        kh.getColumn(0).setCellRenderer(render);
        kh.getColumn(2).setCellRenderer(render);
        kh.getColumn(3).setCellRenderer(render);
        kh.getColumn(4).setCellRenderer(render);
        kh.getColumn(5).setCellRenderer(render);
        kh.getColumn(6).setCellRenderer(render);
        kh.getColumn(7).setCellRenderer(render);
        kh.getColumn(8).setCellRenderer(render);
    }

    private void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblNguoiHoc.getModel();
        model.setRowCount(0);
        try {
            KhoaHoc kh = new KhoaHoc();
            String keyword = txtSearch.getText();
            List<NguoiHoc> list = nhdao.selectByKeyword(kh.getMaKH(), keyword);
            for (NguoiHoc nh : list) {
                Object[] row = {
                    nh.getMaNH(),
                    nh.getHoTen(),
                    nh.isGioiTinh() ? "Nam" : "Nữ",
                    XDate.toString(nh.getNgaySinh()),
                    nh.getDienThoai(),
                    nh.getEmail(),
                    nh.getMaNV(),
                    XDate.toString(nh.getNgayDK()),
                    nh.getHinh()
                };
                model.addRow(row);
            }
            lblTongso.setText("(" + model.getRowCount() + " người học)");
        } catch (Exception e) {
            XDialog.alert(this, "Lỗi truy vấn dữ liệu người học!");
            e.printStackTrace();
        }
    }

    private void insert() {
        NguoiHoc model = getForm();
        try {
            nhdao.insert(model);
            this.fillTable();
            this.clearForm();
            XDialog.alert(this, "Thêm người học mới thành công!");
        } catch (Exception e) {
            XDialog.alert(this, "Thêm người học mới thất bại!");
            e.printStackTrace();
        }
    }

    private void update() {
        NguoiHoc model = getForm();
        try {
            nhdao.update(model);
            this.fillTable();
            XDialog.alert(this, "Cập nhật người học thành công!");
        } catch (Exception e) {
            XDialog.alert(this, "Cập nhật người học thất bại!");
            e.printStackTrace();
        }
    }

    private void delete() {
        if (!XAuth.isManager()) {
            XDialog.alert(this, "Chỉ trưởng phòng mới được phép xóa người học!");
        } else if (XDialog.confirm(this, "Bạn có thực sự muốn xóa người học này không?")) {
            String manh = txtMaNH.getText();
            try {
                nhdao.delete(manh);
                this.fillTable();
                this.clearForm();
                XDialog.alert(this, "Xóa người học thành công!");
            } catch (Exception e) {
                XDialog.alert(this, "Xóa người học thất bại!");
                e.printStackTrace();
            }
        }
    }

    private void clearForm() {
        this.setForm(new NguoiHoc());
        this.row = -1;
        this.updateStatus();
    }

    private void edit() {
        String manh = (String) tblNguoiHoc.getValueAt(row, 0);
        NguoiHoc nh = nhdao.selectById(manh);
        this.setForm(nh);
        this.updateStatus();
        tabs.setSelectedIndex(0);
        this.fillTable();
    }

    private void search() {
        this.fillTable();
        this.clearForm();
        this.row = -1;
        this.updateStatus();
    }

    private void setForm(NguoiHoc nh) {
        txtMaNH.setText(nh.getMaNH());
        txtHoTen.setText(nh.getHoTen());
        cboGioiTinh.setSelectedIndex(nh.isGioiTinh() ? 0 : 1);
        txtNgaySinh.setText(XDate.toString(nh.getNgaySinh()));
        txtDienThoai.setText(nh.getDienThoai());
        txtEmail.setText(nh.getEmail());
        txtGhiChu.setText(nh.getGhiChu());
        lblHinh.setIcon(XImage.readIconNH("NoImage.png"));
        if (nh.getHinh() != null) {
            lblHinh.setToolTipText(nh.getHinh());
            lblHinh.setIcon(XImage.readIconNH(nh.getHinh()));
        }
    }

    private NguoiHoc getForm() {
        NguoiHoc nh = new NguoiHoc();
        nh.setMaNH(txtMaNH.getText());
        nh.setHoTen(txtHoTen.getText());
        nh.setGioiTinh(cboGioiTinh.getSelectedIndex() == 0);
        nh.setNgaySinh(XDate.toDate(txtNgaySinh.getText()));
        nh.setDienThoai(txtDienThoai.getText());
        nh.setEmail(txtEmail.getText());
        nh.setGhiChu(txtGhiChu.getText());
        nh.setMaNV(XAuth.user.getMaNV());
        nh.setNgayDK(XDate.now());
        nh.setHinh(lblHinh.getToolTipText());
        return nh;
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
        if (this.row < tblNguoiHoc.getRowCount() - 1) {
            this.row++;
            this.edit();
        }
    }

    private void last() {
        this.row = tblNguoiHoc.getRowCount() - 1;
        this.edit();
    }

    private void updateStatus() {
        boolean edit = (this.row >= 0);
        boolean first = (this.row == 0);
        boolean last = (this.row == tblNguoiHoc.getRowCount() - 1);
        // Trạng thái form
        txtMaNH.setEditable(!edit);
        btnInsert.setEnabled(!edit);
        btnUpdate.setEnabled(edit);
        btnDelete.setEnabled(edit);

        // Trạng thái điều hướng
        btnFirst.setEnabled(edit && !first);
        btnPrev.setEnabled(edit && !first);
        btnNext.setEnabled(edit && !last);
        btnLast.setEnabled(edit && !last);
    }

    private void selectIcon() {
        JFileChooser fc = new JFileChooser("logos\\avatars");
        FileFilter filter = new FileNameExtensionFilter("Image Files", "gif", "jpeg", "jpg", "png");
        fc.setFileFilter(filter);
        fc.setMultiSelectionEnabled(false);
        int kq = fc.showOpenDialog(fc);
        if (kq == JFileChooser.APPROVE_OPTION) {
            java.io.File file = fc.getSelectedFile();
            XImage.saveIconNH(file); // lưu hình vào thư mục logos
            ImageIcon icon = XImage.readIconNH(file.getName()); // đọc hình từ logos
            lblHinh.setIcon(icon);
            lblHinh.setToolTipText(file.getName()); // giữ tên hình trong tooltip
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

        fileChooser = new javax.swing.JFileChooser();
        pnlTitle = new javax.swing.JPanel();
        lblIcon = new javax.swing.JLabel();
        lblQLHV = new javax.swing.JLabel();
        lblNguoiHoc = new javax.swing.JLabel();
        lblTongso = new javax.swing.JLabel();
        lblExit = new javax.swing.JLabel();
        tabs = new javax.swing.JTabbedPane();
        pnlEdit = new javax.swing.JPanel();
        lblImage = new javax.swing.JLabel();
        lblHinh = new javax.swing.JLabel();
        lblMaNH = new javax.swing.JLabel();
        txtMaNH = new javax.swing.JTextField();
        lblHoTen = new javax.swing.JLabel();
        txtHoTen = new javax.swing.JTextField();
        lblGioiTinh = new javax.swing.JLabel();
        cboGioiTinh = new javax.swing.JComboBox<>();
        lblDienThoai = new javax.swing.JLabel();
        txtDienThoai = new javax.swing.JTextField();
        lblNgaySinh = new javax.swing.JLabel();
        txtNgaySinh = new javax.swing.JTextField();
        lblEmail = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        lblGhiChu = new javax.swing.JLabel();
        jspGhiChu = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        pnlButton = new javax.swing.JPanel();
        btnInsert = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnExport = new javax.swing.JButton();
        pnlButton1 = new javax.swing.JPanel();
        btnFirst = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        pnlList = new javax.swing.JPanel();
        pnlSearch = new javax.swing.JPanel();
        txtSearch = new javax.swing.JTextField();
        lblSearch = new javax.swing.JLabel();
        jspTable = new javax.swing.JScrollPane();
        tblNguoiHoc = new javax.swing.JTable();

        fileChooser.setDialogTitle("Chọn logo chuyên đề");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        pnlTitle.setBackground(new java.awt.Color(228, 141, 120));

        lblIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/icons8-customer.png"))); // NOI18N

        lblQLHV.setFont(new java.awt.Font("Be Vietnam Pro", 1, 36)); // NOI18N
        lblQLHV.setForeground(new java.awt.Color(255, 255, 255));
        lblQLHV.setText("LapTrinhCity - EduSys");

        lblNguoiHoc.setFont(new java.awt.Font("Be Vietnam Pro", 1, 18)); // NOI18N
        lblNguoiHoc.setForeground(new java.awt.Color(255, 255, 255));
        lblNguoiHoc.setText("Quản Lý Người Học");

        lblTongso.setFont(new java.awt.Font("Be Vietnam Pro", 0, 16)); // NOI18N
        lblTongso.setForeground(new java.awt.Color(255, 255, 255));

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
                            .addGroup(pnlTitleLayout.createSequentialGroup()
                                .addComponent(lblNguoiHoc)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblTongso, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(pnlTitleLayout.createSequentialGroup()
                                .addComponent(lblQLHV)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTitleLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblExit, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        pnlTitleLayout.setVerticalGroup(
            pnlTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTitleLayout.createSequentialGroup()
                .addGroup(pnlTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblExit)
                    .addGroup(pnlTitleLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(pnlTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlTitleLayout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(lblQLHV, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(19, 19, 19)
                                .addGroup(pnlTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblNguoiHoc)
                                    .addComponent(lblTongso, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addGap(25, 25, 25))
        );

        tabs.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(228, 141, 120), 2));
        tabs.setFont(new java.awt.Font("Be Vietnam Pro", 0, 15)); // NOI18N

        pnlEdit.setFont(new java.awt.Font("Be Vietnam Pro", 0, 14)); // NOI18N

        lblImage.setFont(new java.awt.Font("Be Vietnam Pro", 0, 15)); // NOI18N
        lblImage.setText("Hình ảnh");

        lblHinh.setBackground(new java.awt.Color(255, 255, 255));
        lblHinh.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHinh.setToolTipText("Hình ảnh");
        lblHinh.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 153, 255), 1, true));
        lblHinh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblHinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHinhMouseClicked(evt);
            }
        });

        lblMaNH.setFont(new java.awt.Font("Be Vietnam Pro", 0, 15)); // NOI18N
        lblMaNH.setText("Mã người học");

        txtMaNH.setFont(new java.awt.Font("Be Vietnam Pro", 0, 14)); // NOI18N
        txtMaNH.setToolTipText("Mã người học");

        lblHoTen.setFont(new java.awt.Font("Be Vietnam Pro", 0, 15)); // NOI18N
        lblHoTen.setText("Họ và tên");

        txtHoTen.setFont(new java.awt.Font("Be Vietnam Pro", 0, 14)); // NOI18N
        txtHoTen.setToolTipText("Họ và tên");

        lblGioiTinh.setFont(new java.awt.Font("Be Vietnam Pro", 0, 15)); // NOI18N
        lblGioiTinh.setText("Giới tính");

        cboGioiTinh.setFont(new java.awt.Font("Be Vietnam Pro", 0, 14)); // NOI18N
        cboGioiTinh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nam", "Nữ" }));
        cboGioiTinh.setPreferredSize(new java.awt.Dimension(64, 30));

        lblDienThoai.setFont(new java.awt.Font("Be Vietnam Pro", 0, 15)); // NOI18N
        lblDienThoai.setText("Điện thoại");

        txtDienThoai.setFont(new java.awt.Font("Be Vietnam Pro", 0, 14)); // NOI18N
        txtDienThoai.setToolTipText("Số điện thoại");

        lblNgaySinh.setFont(new java.awt.Font("Be Vietnam Pro", 0, 15)); // NOI18N
        lblNgaySinh.setText("Ngày sinh (dd/mm/yyyy)");

        txtNgaySinh.setFont(new java.awt.Font("Be Vietnam Pro", 0, 14)); // NOI18N
        txtNgaySinh.setToolTipText("Định dạng dd/MM/yyyy");

        lblEmail.setFont(new java.awt.Font("Be Vietnam Pro", 0, 15)); // NOI18N
        lblEmail.setText("Địa chỉ email");

        txtEmail.setFont(new java.awt.Font("Be Vietnam Pro", 0, 14)); // NOI18N
        txtEmail.setToolTipText("Địa chỉ email");

        lblGhiChu.setFont(new java.awt.Font("Be Vietnam Pro", 0, 15)); // NOI18N
        lblGhiChu.setText("Ghi chú");

        txtGhiChu.setColumns(20);
        txtGhiChu.setFont(new java.awt.Font("Be Vietnam Pro", 0, 14)); // NOI18N
        txtGhiChu.setRows(5);
        txtGhiChu.setMargin(new java.awt.Insets(4, 4, 4, 4));
        jspGhiChu.setViewportView(txtGhiChu);

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
        pnlButton1.add(btnFirst);

        btnPrev.setBackground(new java.awt.Color(51, 51, 255));
        btnPrev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/icon-prev.png"))); // NOI18N
        btnPrev.setPreferredSize(new java.awt.Dimension(60, 45));
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });
        pnlButton1.add(btnPrev);

        btnNext.setBackground(new java.awt.Color(255, 153, 0));
        btnNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/icon-next.png"))); // NOI18N
        btnNext.setPreferredSize(new java.awt.Dimension(60, 45));
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });
        pnlButton1.add(btnNext);

        btnLast.setBackground(new java.awt.Color(255, 153, 0));
        btnLast.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/icon-last.png"))); // NOI18N
        btnLast.setPreferredSize(new java.awt.Dimension(60, 45));
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });
        pnlButton1.add(btnLast);

        javax.swing.GroupLayout pnlEditLayout = new javax.swing.GroupLayout(pnlEdit);
        pnlEdit.setLayout(pnlEditLayout);
        pnlEditLayout.setHorizontalGroup(
            pnlEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEditLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(pnlEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jspGhiChu)
                    .addGroup(pnlEditLayout.createSequentialGroup()
                        .addGroup(pnlEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblImage)
                            .addComponent(lblHinh, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMaNH)
                            .addComponent(txtHoTen, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnlEditLayout.createSequentialGroup()
                                .addGroup(pnlEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblMaNH)
                                    .addComponent(lblHoTen))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlEditLayout.createSequentialGroup()
                                .addGroup(pnlEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlEditLayout.createSequentialGroup()
                                        .addComponent(txtDienThoai)
                                        .addGap(46, 46, 46))
                                    .addGroup(pnlEditLayout.createSequentialGroup()
                                        .addGroup(pnlEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblGioiTinh)
                                            .addComponent(cboGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblDienThoai))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(pnlEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblEmail)
                                    .addGroup(pnlEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
                                        .addComponent(txtNgaySinh)
                                        .addComponent(lblNgaySinh))))))
                    .addGroup(pnlEditLayout.createSequentialGroup()
                        .addComponent(lblGhiChu)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlEditLayout.createSequentialGroup()
                        .addComponent(pnlButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pnlButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnlEditLayout.setVerticalGroup(
            pnlEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlEditLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(pnlEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMaNH)
                    .addComponent(lblImage))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlEditLayout.createSequentialGroup()
                        .addComponent(txtMaNH, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblHoTen)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblGioiTinh)
                            .addComponent(lblNgaySinh))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblDienThoai)
                            .addComponent(lblEmail))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lblHinh, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblGhiChu)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jspGhiChu, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 4, Short.MAX_VALUE))
        );

        tabs.addTab("CẬP NHẬT", new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/icons8-edit-profile.png")), pnlEdit); // NOI18N

        pnlList.setBackground(new java.awt.Color(228, 246, 250));
        pnlList.setFont(new java.awt.Font("Be Vietnam Pro", 0, 14)); // NOI18N

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

        tblNguoiHoc.setFont(new java.awt.Font("Be Vietnam Pro", 0, 14)); // NOI18N
        tblNguoiHoc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "MÃ NH", "HỌ VÀ TÊN", "GIỚI TÍNH", "NGÀY SINH", "ĐIỆN THOẠI", "EMAIL", "MÃ NV", "NGÀY ĐK", "HÌNH"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNguoiHoc.setToolTipText("Người học");
        tblNguoiHoc.setRowHeight(30);
        tblNguoiHoc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNguoiHocMouseClicked(evt);
            }
        });
        jspTable.setViewportView(tblNguoiHoc);

        javax.swing.GroupLayout pnlListLayout = new javax.swing.GroupLayout(pnlList);
        pnlList.setLayout(pnlListLayout);
        pnlListLayout.setHorizontalGroup(
            pnlListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlListLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(pnlListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jspTable, javax.swing.GroupLayout.DEFAULT_SIZE, 1150, Short.MAX_VALUE)
                    .addComponent(pnlSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnlListLayout.setVerticalGroup(
            pnlListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlListLayout.createSequentialGroup()
                .addComponent(pnlSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jspTable, javax.swing.GroupLayout.PREFERRED_SIZE, 497, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        tabs.addTab("DANH SÁCH", new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/icons8-bulleted-list.png")), pnlList); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(tabs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(pnlTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(tabs, javax.swing.GroupLayout.PREFERRED_SIZE, 601, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblHinhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHinhMouseClicked
        // TODO add your handling code here:
        this.selectIcon();
    }//GEN-LAST:event_lblHinhMouseClicked

    private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
        // TODO add your handling code here:
        if (XValidate.checkNullText(txtMaNH)
                && XValidate.checkNullText(txtHoTen)
                && XValidate.checkNullText(txtNgaySinh)
                && XValidate.checkNullText(txtDienThoai)
                && XValidate.checkNullText(txtEmail)
                && XValidate.checkNullHinh(lblHinh)) {
            if (XValidate.checkMaNH(txtMaNH)
                    && XValidate.checkName(txtHoTen)
                    && XValidate.checkDate(txtNgaySinh)
                    && XValidate.checkSDT(txtDienThoai)
                    && XValidate.checkEmail(txtEmail)) {
                if (XValidate.checkTrungNH(txtMaNH)) {
                    if (XValidate.check17Nam(txtNgaySinh)) {
                        this.insert();
                    }
                }
            }
        }
    }//GEN-LAST:event_btnInsertActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        if (XValidate.checkNullText(txtHoTen)
                && XValidate.checkNullText(txtNgaySinh)
                && XValidate.checkNullText(txtDienThoai)
                && XValidate.checkNullText(txtEmail)
                && XValidate.checkNullHinh(lblHinh)) {
            if (XValidate.checkName(txtHoTen)
                    && XValidate.checkDate(txtNgaySinh)
                    && XValidate.checkSDT(txtDienThoai)
                    && XValidate.checkEmail(txtEmail)) {
                if (XValidate.check17Nam(txtNgaySinh)) {
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

    private void tblNguoiHocMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNguoiHocMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            this.row = tblNguoiHoc.getSelectedRow();
            this.edit();
        }
    }//GEN-LAST:event_tblNguoiHocMouseClicked

    private void lblExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblExitMouseClicked
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_lblExitMouseClicked

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        // TODO add your handling code here:
        this.fillTable();
    }//GEN-LAST:event_txtSearchKeyReleased

    private void btnExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportActionPerformed
        // TODO add your handling code here:
        XExcel.writeToExcel(tblNguoiHoc, tblNguoiHoc.getToolTipText());
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
    private javax.swing.JComboBox<String> cboGioiTinh;
    private javax.swing.JFileChooser fileChooser;
    private javax.swing.JScrollPane jspGhiChu;
    private javax.swing.JScrollPane jspTable;
    private javax.swing.JLabel lblDienThoai;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblExit;
    private javax.swing.JLabel lblGhiChu;
    private javax.swing.JLabel lblGioiTinh;
    private javax.swing.JLabel lblHinh;
    private javax.swing.JLabel lblHoTen;
    private javax.swing.JLabel lblIcon;
    private javax.swing.JLabel lblImage;
    private javax.swing.JLabel lblMaNH;
    private javax.swing.JLabel lblNgaySinh;
    private javax.swing.JLabel lblNguoiHoc;
    private javax.swing.JLabel lblQLHV;
    private javax.swing.JLabel lblSearch;
    private javax.swing.JLabel lblTongso;
    private javax.swing.JPanel pnlButton;
    private javax.swing.JPanel pnlButton1;
    private javax.swing.JPanel pnlEdit;
    private javax.swing.JPanel pnlList;
    private javax.swing.JPanel pnlSearch;
    private javax.swing.JPanel pnlTitle;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblNguoiHoc;
    private javax.swing.JTextField txtDienThoai;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextArea txtGhiChu;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JTextField txtMaNH;
    private javax.swing.JTextField txtNgaySinh;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.ui;

import com.edusys.dao.NhanVienDAO;
import com.edusys.model.NhanVien;
import com.edusys.utils.XAuth;
import com.edusys.utils.XDialog;
import com.edusys.utils.XValidate;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author duyplus
 */
public class QLNhanVienJD extends javax.swing.JDialog {
    
    NhanVienDAO nvdao = new NhanVienDAO();
    int row = -1;

    /**
     * Creates new form QLNhanVienJDialog
     *
     * @param parent
     * @param modal
     */
    public QLNhanVienJD(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
    }
    
    private void init() {
        setLocationRelativeTo(null);
        this.fillTable(); // đổ dữ liệu nhân viên vào bảng
        this.updateStatus(); // cập nhật trạng thái form
    }
    
    private void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblNhanVien.getModel();
        model.setRowCount(0);
        try {
            List<NhanVien> list = nvdao.selectAll();
            for (NhanVien nv : list) {
                Object[] row = {
                    nv.getMaNV(),
                    //XAuth.user.getVaiTro() ? nv.getMatKhau() : hidePass(nv.getMatKhau()),
                    hidePass(nv.getMatKhau()),
                    nv.getHoTen(),
                    nv.getEmail(),
                    nv.isVaiTro() ? "Trưởng phòng" : "Nhân viên"
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            XDialog.alert(this, "Lỗi truy vấn dữ liệu nhân viên!");
            e.printStackTrace();
        }
    }
    
    private void insert() {
        NhanVien nv = this.getForm();
        String mk2 = new String(txtXacNhanMK.getPassword());
        if (!mk2.equals(nv.getMatKhau())) {
            XDialog.alert(this, "Xác nhận mật khẩu không đúng!");
        } else {
            try {
                nvdao.insert(nv); // thêm mới
                this.fillTable(); // đỗ lại bảng
                this.clearForm(); // xóa trắng form
                XDialog.alert(this, "Thêm nhân viên mới thành công!");
            } catch (Exception e) {
                XDialog.alert(this, "Thêm nhân viên mới thất bại!");
                e.printStackTrace();
            }
        }
    }
    
    private void update() {
        NhanVien nv = this.getForm();
        String mk2 = new String(txtXacNhanMK.getPassword());
        if (!mk2.equals(nv.getMatKhau())) {
            XDialog.alert(this, "Xác nhận mật khẩu không đúng!");
        } else {
            try {
                nvdao.update(nv); // cập nhật
                this.fillTable(); // đổ lại bảng
                XDialog.alert(this, "Cập nhật nhân viên thành công!");
            } catch (Exception e) {
                XDialog.alert(this, "Cập nhật nhân viên thất bại!");
                e.printStackTrace();
            }
        }
    }
    
    private void delete() {
        if (!XAuth.isManager()) {
            XDialog.alert(this, "Chỉ trưởng phòng mới được phép xóa nhân viên!");
        } else {
            String manv = txtMaNV.getText();
            if (manv.equals(XAuth.user.getMaNV())) {
                XDialog.alert(this, "Bạn không được phép xóa chính mình!");
            } else if (XDialog.confirm(this, "Bạn có thực sự muốn xóa nhân viên này không?")) {
                try {
                    nvdao.delete(manv);
                    this.fillTable();
                    this.clearForm();
                    XDialog.alert(this, "Xóa nhân viên thành công!");
                } catch (Exception e) {
                    XDialog.alert(this, "Xóa nhân viên thất bại!");
                    e.printStackTrace();
                }
            }
        }
    }
    
    private void clearForm() {
        NhanVien nv = new NhanVien();
        this.setForm(nv);
        this.row = -1;
        this.updateStatus();
    }
    
    private void edit() {
        String manv = (String) tblNhanVien.getValueAt(this.row, 0);
        NhanVien nv = nvdao.selectById(manv);
        this.setForm(nv);
        tabs.setSelectedIndex(0);
        this.updateStatus();
    }
    
    private void setForm(NhanVien nv) {
        txtMaNV.setText(nv.getMaNV());
        txtHoTen.setText(nv.getHoTen());
        txtEmail.setText(nv.getEmail());
        txtMatKhau.setText(nv.getMatKhau());
        txtXacNhanMK.setText(nv.getMatKhau());
        cboVaiTro.setSelectedIndex(nv.isVaiTro() ? 0 : 1);
    }
    
    private NhanVien getForm() {
        NhanVien nv = new NhanVien();
        nv.setMaNV(txtMaNV.getText());
        nv.setHoTen(txtHoTen.getText());
        nv.setEmail(txtEmail.getText());
        nv.setMatKhau(new String(txtMatKhau.getPassword()));
        nv.setVaiTro(cboVaiTro.getSelectedIndex() == 0);
        return nv;
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
        if (this.row < tblNhanVien.getRowCount() - 1) {
            this.row++;
            this.edit();
        }
    }
    
    private void last() {
        this.row = tblNhanVien.getRowCount() - 1;
        this.edit();
    }
    
    private void updateStatus() {
        boolean edit = (this.row >= 0);
        boolean first = (this.row == 0);
        boolean last = (this.row == tblNhanVien.getRowCount() - 1);
        // Trạng thái form
        txtMaNV.setEditable(!edit);
        btnInsert.setEnabled(!edit);
        btnUpdate.setEnabled(edit);
        btnDelete.setEnabled(edit);

        // Trạng thái điều hướng
        btnFirst.setEnabled(edit && !first);
        btnPrev.setEnabled(edit && !first);
        btnNext.setEnabled(edit && !last);
        btnLast.setEnabled(edit && !last);
    }

    //Chỉ trưởng phòng mới đc xem mật khẩu người khác
    private String hidePass(String pass) {
        String hide = "";
        for (int i = 0; i < pass.length(); i++) {
            hide += "*";
        }
        return hide;
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
        lblNhanVien = new javax.swing.JLabel();
        lblExit = new javax.swing.JLabel();
        tabs = new javax.swing.JTabbedPane();
        pnlEdit = new javax.swing.JPanel();
        lblMaNV = new javax.swing.JLabel();
        txtMaNV = new javax.swing.JTextField();
        lblHoTen = new javax.swing.JLabel();
        txtHoTen = new javax.swing.JTextField();
        lblEmail = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        lblMatKhau = new javax.swing.JLabel();
        txtMatKhau = new javax.swing.JPasswordField();
        lblXacNhanMK = new javax.swing.JLabel();
        txtXacNhanMK = new javax.swing.JPasswordField();
        lblVaiTro = new javax.swing.JLabel();
        cboVaiTro = new javax.swing.JComboBox<>();
        btnInsert = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnFirst = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        pnlList = new javax.swing.JPanel();
        jspTable = new javax.swing.JScrollPane();
        tblNhanVien = new javax.swing.JTable();

        fileChooser.setDialogTitle("Chọn logo chuyên đề");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        pnlTitle.setBackground(new java.awt.Color(228, 141, 120));

        lblIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/icons8-customer.png"))); // NOI18N

        lblQLHV.setFont(new java.awt.Font("Be Vietnam Pro", 1, 36)); // NOI18N
        lblQLHV.setForeground(new java.awt.Color(255, 255, 255));
        lblQLHV.setText("LapTrinhCity - EduSys");

        lblNhanVien.setFont(new java.awt.Font("Be Vietnam Pro", 1, 18)); // NOI18N
        lblNhanVien.setForeground(new java.awt.Color(255, 255, 255));
        lblNhanVien.setText("Quản Lý Nhân Viên");

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
                            .addComponent(lblNhanVien)
                            .addComponent(lblQLHV))
                        .addContainerGap())
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
                                .addComponent(lblNhanVien))
                            .addComponent(lblIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lblExit))
                .addGap(25, 25, 25))
        );

        tabs.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(228, 141, 120), 2));
        tabs.setFont(new java.awt.Font("Be Vietnam Pro", 0, 15)); // NOI18N

        lblMaNV.setFont(new java.awt.Font("Be Vietnam Pro", 0, 15)); // NOI18N
        lblMaNV.setText("Mã nhân viên:");

        txtMaNV.setFont(new java.awt.Font("Be Vietnam Pro", 0, 14)); // NOI18N
        txtMaNV.setToolTipText("Mã nhân viên");

        lblHoTen.setFont(new java.awt.Font("Be Vietnam Pro", 0, 15)); // NOI18N
        lblHoTen.setText("Họ và tên:");

        txtHoTen.setFont(new java.awt.Font("Be Vietnam Pro", 0, 14)); // NOI18N
        txtHoTen.setToolTipText("Họ và tên");

        lblEmail.setFont(new java.awt.Font("Be Vietnam Pro", 0, 15)); // NOI18N
        lblEmail.setText("Email:");

        txtEmail.setFont(new java.awt.Font("Be Vietnam Pro", 0, 14)); // NOI18N
        txtEmail.setToolTipText("Họ và tên");

        lblMatKhau.setFont(new java.awt.Font("Be Vietnam Pro", 0, 15)); // NOI18N
        lblMatKhau.setText("Mật khẩu:");

        txtMatKhau.setFont(new java.awt.Font("Be Vietnam Pro", 0, 14)); // NOI18N
        txtMatKhau.setToolTipText("Mật khẩu");

        lblXacNhanMK.setFont(new java.awt.Font("Be Vietnam Pro", 0, 15)); // NOI18N
        lblXacNhanMK.setText("Xác nhận mật khẩu:");

        txtXacNhanMK.setFont(new java.awt.Font("Be Vietnam Pro", 0, 14)); // NOI18N
        txtXacNhanMK.setToolTipText("Xác nhận mật khẩu");

        lblVaiTro.setFont(new java.awt.Font("Be Vietnam Pro", 0, 15)); // NOI18N
        lblVaiTro.setText("Vai trò:");

        cboVaiTro.setFont(new java.awt.Font("Be Vietnam Pro", 0, 14)); // NOI18N
        cboVaiTro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Trưởng phòng", "Nhân viên" }));

        btnInsert.setBackground(new java.awt.Color(0, 95, 95));
        btnInsert.setFont(new java.awt.Font("Be Vietnam Pro", 1, 16)); // NOI18N
        btnInsert.setForeground(new java.awt.Color(255, 255, 255));
        btnInsert.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/icons8-add-to-collection.png"))); // NOI18N
        btnInsert.setText("Thêm");
        btnInsert.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertActionPerformed(evt);
            }
        });

        btnUpdate.setBackground(new java.awt.Color(0, 95, 95));
        btnUpdate.setFont(new java.awt.Font("Be Vietnam Pro", 1, 16)); // NOI18N
        btnUpdate.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/icons8-update.png"))); // NOI18N
        btnUpdate.setText("Sửa");
        btnUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setBackground(new java.awt.Color(0, 95, 95));
        btnDelete.setFont(new java.awt.Font("Be Vietnam Pro", 1, 16)); // NOI18N
        btnDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/icons8-delete.png"))); // NOI18N
        btnDelete.setText("Xóa");
        btnDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnClear.setBackground(new java.awt.Color(0, 95, 95));
        btnClear.setFont(new java.awt.Font("Be Vietnam Pro", 1, 16)); // NOI18N
        btnClear.setForeground(new java.awt.Color(255, 255, 255));
        btnClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/icons8-add.png"))); // NOI18N
        btnClear.setText("Mới");
        btnClear.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        btnFirst.setBackground(new java.awt.Color(51, 51, 255));
        btnFirst.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/icon-first.png"))); // NOI18N
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        btnPrev.setBackground(new java.awt.Color(51, 51, 255));
        btnPrev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/icon-prev.png"))); // NOI18N
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });

        btnNext.setBackground(new java.awt.Color(255, 153, 0));
        btnNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/icon-next.png"))); // NOI18N
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnLast.setBackground(new java.awt.Color(255, 153, 0));
        btnLast.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/icon-last.png"))); // NOI18N
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlEditLayout = new javax.swing.GroupLayout(pnlEdit);
        pnlEdit.setLayout(pnlEditLayout);
        pnlEditLayout.setHorizontalGroup(
            pnlEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEditLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlEditLayout.createSequentialGroup()
                        .addComponent(btnInsert)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnUpdate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDelete)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnClear)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 199, Short.MAX_VALUE)
                        .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlEditLayout.createSequentialGroup()
                        .addGroup(pnlEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblMaNV)
                            .addComponent(lblHoTen)
                            .addComponent(lblEmail)
                            .addComponent(txtMaNV, javax.swing.GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)
                            .addComponent(txtHoTen)
                            .addComponent(txtEmail))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnlEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblVaiTro)
                            .addComponent(txtXacNhanMK, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblMatKhau)
                            .addComponent(txtMatKhau, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblXacNhanMK)
                            .addComponent(cboVaiTro, 0, 425, Short.MAX_VALUE))))
                .addContainerGap())
        );
        pnlEditLayout.setVerticalGroup(
            pnlEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlEditLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(pnlEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlEditLayout.createSequentialGroup()
                        .addComponent(lblMaNV)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(lblHoTen)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblEmail)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39))
                    .addGroup(pnlEditLayout.createSequentialGroup()
                        .addComponent(lblMatKhau)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblXacNhanMK)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtXacNhanMK, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblVaiTro)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboVaiTro, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(pnlEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnInsert, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(201, 201, 201))
        );

        tabs.addTab("CẬP NHẬT", new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/icons8-edit-profile.png")), pnlEdit); // NOI18N

        pnlList.setBackground(new java.awt.Color(228, 246, 250));

        tblNhanVien.setFont(new java.awt.Font("Be Vietnam Pro", 0, 14)); // NOI18N
        tblNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "MÃ NV", "MẬT KHẨU", "HỌ VÀ TÊN", "EMAIL", "VAI TRÒ"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNhanVien.setRowHeight(30);
        tblNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNhanVienMouseClicked(evt);
            }
        });
        jspTable.setViewportView(tblNhanVien);

        javax.swing.GroupLayout pnlListLayout = new javax.swing.GroupLayout(pnlList);
        pnlList.setLayout(pnlListLayout);
        pnlListLayout.setHorizontalGroup(
            pnlListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspTable, javax.swing.GroupLayout.DEFAULT_SIZE, 896, Short.MAX_VALUE)
        );
        pnlListLayout.setVerticalGroup(
            pnlListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlListLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jspTable, javax.swing.GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE))
        );

        tabs.addTab("DANH SÁCH", new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/icons8-bulleted-list.png")), pnlList); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tabs)
                    .addComponent(pnlTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(pnlTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(tabs, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
        // TODO add your handling code here:
        if (XValidate.checkNullText(txtMaNV)
                && XValidate.checkNullPass(txtMatKhau)
                && XValidate.checkNullPass(txtXacNhanMK)
                && XValidate.checkNullText(txtHoTen)
                && XValidate.checkEmail(txtEmail)) {
            if (XValidate.checkMaNV(txtMaNV)
                    && XValidate.checkPassNV(txtMatKhau)
                    && XValidate.checkName(txtHoTen)) {
                if (XValidate.checkTrungNV(txtMaNV)) {
                    this.insert();
                }
            }
        }
    }//GEN-LAST:event_btnInsertActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        if (XValidate.checkNullPass(txtMatKhau)
                && XValidate.checkNullPass(txtXacNhanMK)
                && XValidate.checkNullText(txtHoTen)
                && XValidate.checkEmail(txtEmail)) {
            if (XValidate.checkPassNV(txtMatKhau)
                    && XValidate.checkName(txtHoTen)) {
                this.update();
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

    private void tblNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhanVienMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            this.row = tblNhanVien.getSelectedRow();
            this.edit();
        }
    }//GEN-LAST:event_tblNhanVienMouseClicked

    private void lblExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblExitMouseClicked
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_lblExitMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnInsert;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cboVaiTro;
    private javax.swing.JFileChooser fileChooser;
    private javax.swing.JScrollPane jspTable;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblExit;
    private javax.swing.JLabel lblHoTen;
    private javax.swing.JLabel lblIcon;
    private javax.swing.JLabel lblMaNV;
    private javax.swing.JLabel lblMatKhau;
    private javax.swing.JLabel lblNhanVien;
    private javax.swing.JLabel lblQLHV;
    private javax.swing.JLabel lblVaiTro;
    private javax.swing.JLabel lblXacNhanMK;
    private javax.swing.JPanel pnlEdit;
    private javax.swing.JPanel pnlList;
    private javax.swing.JPanel pnlTitle;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblNhanVien;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JPasswordField txtMatKhau;
    private javax.swing.JPasswordField txtXacNhanMK;
    // End of variables declaration//GEN-END:variables
}

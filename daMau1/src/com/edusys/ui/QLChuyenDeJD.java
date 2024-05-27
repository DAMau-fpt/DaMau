/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.ui;

import com.edusys.dao.ChuyenDeDAO;
import com.edusys.model.ChuyenDe;
import com.edusys.utils.XAuth;
import com.edusys.utils.XDialog;
import com.edusys.utils.XExcel;
import com.edusys.utils.XImage;
import com.edusys.utils.XValidate;
import com.formdev.flatlaf.FlatLightLaf;
import java.io.File;
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
public class QLChuyenDeJD extends javax.swing.JDialog {

    int row = -1;
    ChuyenDeDAO cddao = new ChuyenDeDAO();

    /**
     * Creates new form QLChuyenDeJDialog
     *
     * @param parent
     * @param modal
     */
    public QLChuyenDeJD(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
    }

    private void init() {
        setLocationRelativeTo(null);
        this.fillTable(); // đổ dữ liệu chuyên đề vào bảng
        this.row = -1;
        this.updateStatus(); // cập nhật trạng thái form

        DefaultTableCellRenderer render = new DefaultTableCellRenderer();
        render.setHorizontalAlignment(javax.swing.JLabel.CENTER);

        TableColumnModel kh = tblChuyenDe.getColumnModel();
        kh.getColumn(0).setMaxWidth(100);
        kh.getColumn(1).setMinWidth(250);
        kh.getColumn(2).setMinWidth(20);
        kh.getColumn(3).setMinWidth(20);
        kh.getColumn(4).setMinWidth(20);

        kh.getColumn(0).setCellRenderer(render);
        kh.getColumn(2).setCellRenderer(render);
        kh.getColumn(3).setCellRenderer(render);
    }

    private void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblChuyenDe.getModel();
        model.setRowCount(0);
        try {
            List<ChuyenDe> list = cddao.selectAll();
            for (ChuyenDe cd : list) {
                Object[] row = {
                    cd.getMaCD(),
                    cd.getTenCD(),
                    cd.getHocPhi(),
                    cd.getThoiLuong(),
                    cd.getHinh()
                };
                model.addRow(row);
            }
            lblTongso.setText("(" + model.getRowCount() + " chuyên đề)");
        } catch (Exception e) {
            XDialog.alert(this, "Lỗi truy vấn dữ liệu chuyên đề!");
            e.printStackTrace();
        }
    }

    private void insert() {
        ChuyenDe model = getForm();
        try {
            cddao.insert(model);
            this.fillTable();
            this.clearForm();
            XDialog.alert(this, "Thêm chuyên đề mới thành công!");
        } catch (Exception e) {
            XDialog.alert(this, "Thêm chuyên đề mới thất bại!");
            e.printStackTrace();
        }

    }

    private void update() {
        ChuyenDe model = getForm();
        try {
            cddao.update(model);
            this.fillTable();
            XDialog.alert(this, "Cập nhật chuyên đề thành công!");
        } catch (Exception e) {
            XDialog.alert(this, "Cập nhật chuyên đề thất bại!");
            e.printStackTrace();
        }
    }

    private void delete() {
        if (!XAuth.isManager()) {
            XDialog.alert(this, "Chỉ trưởng phòng mới được phép xóa chuyên đề!");
        } else {
            if (XDialog.confirm(this, "Bạn có thực sự muốn xóa chuyên đề này không?")) {
                String macd = txtMaCD.getText();
                try {
                    cddao.delete(macd);
                    this.fillTable();
                    this.clearForm();
                    XDialog.alert(this, "Xóa chuyên đề thành công!");
                } catch (Exception e) {
                    XDialog.alert(this, "Xóa chuyên đề thất bại!");
                    e.printStackTrace();
                }
            }
        }
    }

    private void clearForm() {
        this.setForm(new ChuyenDe());
        this.row = -1;
        this.updateStatus();
    }

    private void edit() {
        String macd = (String) tblChuyenDe.getValueAt(this.row, 0);
        ChuyenDe cd = cddao.selectById(macd);
        this.setForm(cd);
        this.updateStatus();
        tabs.setSelectedIndex(0);
    }

    private void setForm(ChuyenDe cd) {
        txtMaCD.setText(cd.getMaCD());
        txtTenCD.setText(cd.getTenCD());
        txtThoiLuong.setText(String.valueOf(cd.getThoiLuong()));
        txtHocPhi.setText(String.valueOf(cd.getHocPhi()));
        txtMota.setText(cd.getMoTa());
        lblHinh.setIcon(XImage.readIconCD("NoImage.png"));
        if (cd.getHinh() != null) {
            lblHinh.setToolTipText(cd.getHinh());
            lblHinh.setIcon(XImage.readIconCD(cd.getHinh()));
        }
    }

    private ChuyenDe getForm() {
        ChuyenDe cd = new ChuyenDe();
        cd.setMaCD(txtMaCD.getText());
        cd.setTenCD(txtTenCD.getText());
        cd.setThoiLuong(Integer.valueOf(txtThoiLuong.getText()));
        cd.setHocPhi(Double.valueOf(txtHocPhi.getText()));
        cd.setMoTa(txtMota.getText());
        cd.setHinh(lblHinh.getToolTipText());
        return cd;
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
        if (this.row < tblChuyenDe.getRowCount() - 1) {
            this.row++;
            this.edit();
        }
    }

    private void last() {
        this.row = tblChuyenDe.getRowCount() - 1;
        this.edit();
    }

    private void updateStatus() {
        boolean edit = (this.row >= 0);
        boolean first = (this.row == 0);
        boolean last = (this.row == tblChuyenDe.getRowCount() - 1);
        // Trạng thái form
        txtMaCD.setEditable(!edit);
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
        JFileChooser fc = new JFileChooser("logos");
        FileFilter filter = new FileNameExtensionFilter("Image Files", "gif", "jpeg", "jpg", "png");
        fc.setFileFilter(filter);
        fc.setMultiSelectionEnabled(false);
        int kq = fc.showOpenDialog(fc);
        if (kq == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            XImage.saveIconCD(file); // lưu hình vào thư mục logos
            ImageIcon icon = XImage.readIconCD(file.getName()); // đọc hình từ logos
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

        pnlTitle = new javax.swing.JPanel();
        lblIcon = new javax.swing.JLabel();
        lblQLHV = new javax.swing.JLabel();
        lblChuyenDe = new javax.swing.JLabel();
        lblTongso = new javax.swing.JLabel();
        lblExit = new javax.swing.JLabel();
        tabs = new javax.swing.JTabbedPane();
        pnlEdit = new javax.swing.JPanel();
        lblImage = new javax.swing.JLabel();
        lblHinh = new javax.swing.JLabel();
        lblMaCD = new javax.swing.JLabel();
        txtMaCD = new javax.swing.JTextField();
        lblTenCD = new javax.swing.JLabel();
        txtTenCD = new javax.swing.JTextField();
        lblThoiLuong = new javax.swing.JLabel();
        txtThoiLuong = new javax.swing.JTextField();
        lblHocPhi = new javax.swing.JLabel();
        txtHocPhi = new javax.swing.JTextField();
        lblMoTa = new javax.swing.JLabel();
        jspMoTa = new javax.swing.JScrollPane();
        txtMota = new javax.swing.JTextArea();
        pnlbutton = new javax.swing.JPanel();
        btnInsert = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnFirst = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        btnExport = new javax.swing.JButton();
        pnlList = new javax.swing.JPanel();
        jspList = new javax.swing.JScrollPane();
        tblChuyenDe = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        pnlTitle.setBackground(new java.awt.Color(228, 141, 120));

        lblIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/icons8-customer.png"))); // NOI18N

        lblQLHV.setFont(new java.awt.Font("Be Vietnam Pro", 1, 36)); // NOI18N
        lblQLHV.setForeground(new java.awt.Color(255, 255, 255));
        lblQLHV.setText("LapTrinhCity - EduSys");

        lblChuyenDe.setFont(new java.awt.Font("Be Vietnam Pro", 1, 18)); // NOI18N
        lblChuyenDe.setForeground(new java.awt.Color(255, 255, 255));
        lblChuyenDe.setText("Quản Lý Chuyên Đề");

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
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTitleLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblExit, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlTitleLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(pnlTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlTitleLayout.createSequentialGroup()
                                .addComponent(lblQLHV)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(pnlTitleLayout.createSequentialGroup()
                                .addComponent(lblChuyenDe)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblTongso, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
        );
        pnlTitleLayout.setVerticalGroup(
            pnlTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTitleLayout.createSequentialGroup()
                .addGroup(pnlTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlTitleLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(pnlTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlTitleLayout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(lblQLHV, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(pnlTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblChuyenDe)
                                    .addComponent(lblTongso, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addComponent(lblExit))
                .addGap(25, 25, 25))
        );

        tabs.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(228, 141, 120), 2));
        tabs.setFont(new java.awt.Font("Be Vietnam Pro", 0, 15)); // NOI18N

        pnlEdit.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 0, 0, 0));
        pnlEdit.setFont(new java.awt.Font("Be Vietnam Pro", 0, 12)); // NOI18N

        lblImage.setFont(new java.awt.Font("Be Vietnam Pro", 0, 15)); // NOI18N
        lblImage.setText("Hình ảnh");

        lblHinh.setBackground(new java.awt.Color(255, 255, 255));
        lblHinh.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHinh.setToolTipText("Hình ảnh");
        lblHinh.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 153, 255), 1, true));
        lblHinh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblHinh.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblHinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHinhMouseClicked(evt);
            }
        });

        lblMaCD.setFont(new java.awt.Font("Be Vietnam Pro", 0, 15)); // NOI18N
        lblMaCD.setText("Mã chuyên đề");

        txtMaCD.setFont(new java.awt.Font("Be Vietnam Pro", 0, 14)); // NOI18N
        txtMaCD.setToolTipText("Mã chuyên đề");

        lblTenCD.setFont(new java.awt.Font("Be Vietnam Pro", 0, 15)); // NOI18N
        lblTenCD.setText("Tên chuyên đề");

        txtTenCD.setFont(new java.awt.Font("Be Vietnam Pro", 0, 14)); // NOI18N
        txtTenCD.setToolTipText("Tên chuyên đề");

        lblThoiLuong.setFont(new java.awt.Font("Be Vietnam Pro", 0, 15)); // NOI18N
        lblThoiLuong.setText("Thời lượng (giờ)");

        txtThoiLuong.setFont(new java.awt.Font("Be Vietnam Pro", 0, 14)); // NOI18N
        txtThoiLuong.setToolTipText("Thời lượng");

        lblHocPhi.setFont(new java.awt.Font("Be Vietnam Pro", 0, 15)); // NOI18N
        lblHocPhi.setText("Học phí");

        txtHocPhi.setFont(new java.awt.Font("Be Vietnam Pro", 0, 14)); // NOI18N
        txtHocPhi.setToolTipText("Học phí");

        lblMoTa.setFont(new java.awt.Font("Be Vietnam Pro", 0, 15)); // NOI18N
        lblMoTa.setText("Mô tả chuyên đề");

        txtMota.setColumns(20);
        txtMota.setFont(new java.awt.Font("Be Vietnam Pro", 0, 14)); // NOI18N
        txtMota.setRows(5);
        txtMota.setToolTipText("Mô tả chuyên đề");
        txtMota.setMargin(new java.awt.Insets(4, 4, 4, 4));
        jspMoTa.setViewportView(txtMota);

        pnlbutton.setBackground(new java.awt.Color(228, 246, 250));

        btnInsert.setBackground(new java.awt.Color(0, 95, 95));
        btnInsert.setFont(new java.awt.Font("Be Vietnam Pro", 1, 16)); // NOI18N
        btnInsert.setForeground(new java.awt.Color(0, 255, 255));
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

        btnExport.setBackground(new java.awt.Color(0, 95, 95));
        btnExport.setFont(new java.awt.Font("Be Vietnam Pro", 1, 16)); // NOI18N
        btnExport.setForeground(new java.awt.Color(255, 255, 255));
        btnExport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/icons8-export-excel.png"))); // NOI18N
        btnExport.setText("Xuất");
        btnExport.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlbuttonLayout = new javax.swing.GroupLayout(pnlbutton);
        pnlbutton.setLayout(pnlbuttonLayout);
        pnlbuttonLayout.setHorizontalGroup(
            pnlbuttonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlbuttonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnInsert)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUpdate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDelete)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnClear)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExport, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlbuttonLayout.setVerticalGroup(
            pnlbuttonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlbuttonLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlbuttonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnInsert, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExport, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlEditLayout = new javax.swing.GroupLayout(pnlEdit);
        pnlEdit.setLayout(pnlEditLayout);
        pnlEditLayout.setHorizontalGroup(
            pnlEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlEditLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jspMoTa)
                    .addGroup(pnlEditLayout.createSequentialGroup()
                        .addGroup(pnlEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblImage)
                            .addComponent(lblHinh, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMaCD, javax.swing.GroupLayout.DEFAULT_SIZE, 802, Short.MAX_VALUE)
                            .addComponent(txtThoiLuong)
                            .addComponent(txtTenCD)
                            .addComponent(txtHocPhi)
                            .addGroup(pnlEditLayout.createSequentialGroup()
                                .addGroup(pnlEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblHocPhi, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblThoiLuong)
                                    .addComponent(lblTenCD)
                                    .addComponent(lblMaCD))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(pnlEditLayout.createSequentialGroup()
                        .addComponent(lblMoTa)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addComponent(pnlbutton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlEditLayout.setVerticalGroup(
            pnlEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlEditLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(pnlEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMaCD)
                    .addComponent(lblImage))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlEditLayout.createSequentialGroup()
                        .addComponent(txtMaCD, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblTenCD)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTenCD, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblThoiLuong)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtThoiLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblHocPhi)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtHocPhi, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblHinh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblMoTa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jspMoTa, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnlbutton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        tabs.addTab("CẬP NHẬT", new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/icons8-edit-profile.png")), pnlEdit); // NOI18N

        pnlList.setBackground(new java.awt.Color(228, 246, 250));
        pnlList.setLayout(new java.awt.BorderLayout());

        tblChuyenDe.setFont(new java.awt.Font("Be Vietnam Pro", 0, 14)); // NOI18N
        tblChuyenDe.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "MÃ CD", "TÊN CD", "HỌC PHÍ", "THỜI LƯỢNG", "HÌNH"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblChuyenDe.setToolTipText("Chuyên đề");
        tblChuyenDe.setGridColor(new java.awt.Color(255, 0, 0));
        tblChuyenDe.setRowHeight(30);
        tblChuyenDe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblChuyenDeMouseClicked(evt);
            }
        });
        jspList.setViewportView(tblChuyenDe);

        pnlList.add(jspList, java.awt.BorderLayout.CENTER);

        tabs.addTab("DANH SÁCH", new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/icons8-bulleted-list.png")), pnlList); // NOI18N

        tabs.setSelectedIndex(1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabs)
            .addComponent(pnlTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(pnlTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(tabs))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblHinhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHinhMouseClicked
        // TODO add your handling code here:
        this.selectIcon();
    }//GEN-LAST:event_lblHinhMouseClicked

    private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
        // TODO add your handling code here:
        if (XValidate.checkNullText(txtMaCD)
                && XValidate.checkNullText(txtTenCD)
                && XValidate.checkNullText(txtThoiLuong)
                && XValidate.checkNullText(txtHocPhi)
                && XValidate.checkNullText(txtMota)
                && XValidate.checkNullHinh(lblHinh)) {
            if (XValidate.checkMaCD(txtMaCD)
                    && XValidate.checkTenCD(txtTenCD)
                    && XValidate.checkThoiLuong(txtThoiLuong)
                    && XValidate.checkHocPhi(txtHocPhi)
                    && XValidate.checkMoTaCD(txtMota)) {
                if (XValidate.checkTrungCD(txtMaCD)) {
                    this.insert();
                }
            }
        }
    }//GEN-LAST:event_btnInsertActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        if (XValidate.checkNullText(txtMaCD)
                && XValidate.checkNullText(txtTenCD)
                && XValidate.checkNullText(txtThoiLuong)
                && XValidate.checkNullText(txtHocPhi)
                && XValidate.checkNullText(txtMota)
                && XValidate.checkNullHinh(lblHinh)) {
            if (XValidate.checkMaCD(txtMaCD)
                    && XValidate.checkTenCD(txtTenCD)
                    && XValidate.checkThoiLuong(txtThoiLuong)
                    && XValidate.checkHocPhi(txtHocPhi)
                    && XValidate.checkMoTaCD(txtMota)) {
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

    private void tblChuyenDeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblChuyenDeMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            this.row = tblChuyenDe.getSelectedRow();
            this.edit();
        }
    }//GEN-LAST:event_tblChuyenDeMouseClicked

    private void lblExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblExitMouseClicked
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_lblExitMouseClicked

    private void btnExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportActionPerformed
        // TODO add your handling code here:
        XExcel.writeToExcel(tblChuyenDe, tblChuyenDe.getToolTipText());
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
    private javax.swing.JScrollPane jspList;
    private javax.swing.JScrollPane jspMoTa;
    private javax.swing.JLabel lblChuyenDe;
    private javax.swing.JLabel lblExit;
    private javax.swing.JLabel lblHinh;
    private javax.swing.JLabel lblHocPhi;
    private javax.swing.JLabel lblIcon;
    private javax.swing.JLabel lblImage;
    private javax.swing.JLabel lblMaCD;
    private javax.swing.JLabel lblMoTa;
    private javax.swing.JLabel lblQLHV;
    private javax.swing.JLabel lblTenCD;
    private javax.swing.JLabel lblThoiLuong;
    private javax.swing.JLabel lblTongso;
    private javax.swing.JPanel pnlEdit;
    private javax.swing.JPanel pnlList;
    private javax.swing.JPanel pnlTitle;
    private javax.swing.JPanel pnlbutton;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblChuyenDe;
    private javax.swing.JTextField txtHocPhi;
    private javax.swing.JTextField txtMaCD;
    private javax.swing.JTextArea txtMota;
    private javax.swing.JTextField txtTenCD;
    private javax.swing.JTextField txtThoiLuong;
    // End of variables declaration//GEN-END:variables
}

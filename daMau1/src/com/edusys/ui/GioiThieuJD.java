package com.edusys.ui;

import javax.swing.JDialog;

/**
 *
 * @author duyplus
 */
public class GioiThieuJD extends JDialog {

    /**
     * Creates new form GioiThieuJDialog
     *
     * @param parent
     * @param modal
     */
    public GioiThieuJD(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        this.pack();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlGioiThieu = new javax.swing.JPanel();
        lblLogo = new javax.swing.JLabel();
        pnlText = new javax.swing.JPanel();
        txtGioiThieu = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setUndecorated(true);

        pnlGioiThieu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(228, 141, 120), 2));
        pnlGioiThieu.setLayout(new java.awt.BorderLayout());

        lblLogo.setBackground(new java.awt.Color(228, 246, 250));
        lblLogo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/edusys/icons/logogt.png"))); // NOI18N
        lblLogo.setMaximumSize(new java.awt.Dimension(667, 390));
        lblLogo.setMinimumSize(new java.awt.Dimension(667, 390));
        lblLogo.setOpaque(true);
        lblLogo.setPreferredSize(new java.awt.Dimension(667, 390));
        lblLogo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblLogoMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                lblLogoMouseReleased(evt);
            }
        });
        pnlGioiThieu.add(lblLogo, java.awt.BorderLayout.PAGE_START);

        txtGioiThieu.setEditable(false);
        txtGioiThieu.setBackground(new java.awt.Color(238, 101, 68));
        txtGioiThieu.setFont(new java.awt.Font("Be Vietnam Pro", 0, 16)); // NOI18N
        txtGioiThieu.setForeground(new java.awt.Color(255, 255, 255));
        txtGioiThieu.setText("EduSys là dự án mẫu. Mục tiêu chính là huấn luyện sinh viên qui trình thực hiện dự án.\n\nMục tiêu của dự án này là để rèn luyện kỹ năng IO (CDIO) tức không yêu cầu sinh viên phải thu thập phân tích mà chỉ  thực hiện và vận hành một phần mềm chuẩn bị cho các dự án sau này. Các kỹ năng CD (trong CDIO) sẽ được huấn luyện ở dự án 1 và dự án 2.\n\nYêu cầu về môi trường:\n1. Hệ điều hành bất kỳ\n2. JDK 1.8 trở lên\n3. SQL Server 2008 trở lên");
        txtGioiThieu.setMargin(new java.awt.Insets(25, 13, 10, 5));
        txtGioiThieu.setPreferredSize(new java.awt.Dimension(512, 175));
        txtGioiThieu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtGioiThieuMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                txtGioiThieuMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout pnlTextLayout = new javax.swing.GroupLayout(pnlText);
        pnlText.setLayout(pnlTextLayout);
        pnlTextLayout.setHorizontalGroup(
            pnlTextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTextLayout.createSequentialGroup()
                .addComponent(txtGioiThieu, javax.swing.GroupLayout.PREFERRED_SIZE, 900, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnlTextLayout.setVerticalGroup(
            pnlTextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTextLayout.createSequentialGroup()
                .addComponent(txtGioiThieu, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pnlGioiThieu.add(pnlText, java.awt.BorderLayout.CENTER);

        getContentPane().add(pnlGioiThieu, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblLogoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLogoMouseClicked
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_lblLogoMouseClicked

    private void txtGioiThieuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtGioiThieuMouseClicked
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_txtGioiThieuMouseClicked

    private void lblLogoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLogoMouseReleased
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_lblLogoMouseReleased

    private void txtGioiThieuMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtGioiThieuMouseReleased
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_txtGioiThieuMouseReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblLogo;
    private javax.swing.JPanel pnlGioiThieu;
    private javax.swing.JPanel pnlText;
    private javax.swing.JTextPane txtGioiThieu;
    // End of variables declaration//GEN-END:variables
}
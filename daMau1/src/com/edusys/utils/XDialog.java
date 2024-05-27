package com.edusys.utils;

import java.awt.Component;
import javax.swing.JOptionPane;

/**
 *
 * @author duyplus
 */
public class XDialog {

    /*
     * Hiển thị thông báo cho người dùng
     */
    public static void alert(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Hệ thống quản lý đào tạo",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /*
     * Hiển thị thông báo và yêu cầu người dùng xác nhận
     */
    public static boolean confirm(Component parent, String message) {
        int result = JOptionPane.showConfirmDialog(parent, message, "Hệ thống quản lý đào tạo",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return result == JOptionPane.YES_OPTION;
    }

    /*
     * Hiển thị thông báo yêu cầu nhập dữ liệu
     */
    public static String prompt(Component parent, String message) {
        return JOptionPane.showInputDialog(parent, message, "Hệ thống quản lý đào tạo",
                JOptionPane.INFORMATION_MESSAGE);
    }
}

package com.edusys.utils;

import com.edusys.dao.ChuyenDeDAO;
import com.edusys.dao.NguoiHocDAO;
import com.edusys.dao.NhanVienDAO;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author duyplus
 */
public class XValidate {

    public static ChuyenDeDAO cddao = new ChuyenDeDAO();
    public static NguoiHocDAO nhdao = new NguoiHocDAO();
    public static NhanVienDAO nvdao = new NhanVienDAO();

    /*
     * Xếp loại học viên theo điểm
     */
    public static String getRank(double diem) {
        String xepLoai = "Xuất sắc";
        if (diem < 0) {
            xepLoai = "Chưa nhập";
        } else if (diem < 3) {
            xepLoai = "Kém";
        } else if (diem < 5) {
            xepLoai = "Yếu";
        } else if (diem < 6.5) {
            xepLoai = "Trung bình";
        } else if (diem < 7.5) {
            xepLoai = "Khá";
        } else if (diem < 9) {
            xepLoai = "Giỏi";
        }
        return xepLoai;
    }

    //--------------- CHUYÊN ĐỀ ---------------//
    /*
     * Kiểm thử mã Chuyên Đề
     */
    public static boolean checkMaCD(JTextField txt) {
        String id = txt.getText();
        String rgx = "[a-zA-Z0-9]{5}$";
        if (id.matches(rgx)) {
            return true;
        } else {
            XDialog.alert(txt.getRootPane(), txt.getToolTipText() + " phải có đúng 5 kí tự\n(chữ hoa, thường không dấu hoặc số).");
            return false;
        }
    }

    /*
     * Kiểm tra trùng mã Chuyên Đề
     */
    public static boolean checkTrungCD(JTextField txt) {
        if (cddao.selectById(txt.getText()) == null) {
            return true;
        } else {
            XDialog.alert(txt.getRootPane(), "Mã chuyên đề '" + txt.getText() + "' đã tồn tại.");
            return false;
        }
    }

    /*
     * Kiểm thử tên Chuyên Đề
     */
    public static boolean checkTenCD(JTextField txt) {
        String id = txt.getText();
        String rgx = ".{3,50}";
        if (id.matches(rgx)) {
            return true;
        } else {
            XDialog.alert(txt.getRootPane(), txt.getToolTipText() + " phải có từ 3-50 kí tự.");
            return false;
        }
    }

    /*
     * Kiểm thử mô tả Chuyên Đề
     */
    public static boolean checkMoTaCD(JTextArea txt) {
        String id = txt.getText();
        String rgx = ".{3,255}";
        if (id.matches(rgx)) {
            return true;
        } else {
            XDialog.alert(txt.getRootPane(), txt.getToolTipText() + " phải có từ 3-255 kí tự.");
            return false;
        }
    }

    /*
     * Kiểm thử thời lượng Chuyên Đề
     */
    public static boolean checkThoiLuong(JTextField txt) {
        try {
            int hour = Integer.parseInt(txt.getText());
            if (hour >= 0) {
                return true;
            } else {
                XDialog.alert(txt.getRootPane(), txt.getToolTipText() + " phải lớn hơn bằng 0.");
                return false;
            }
        } catch (NumberFormatException e) {
            XDialog.alert(txt.getRootPane(), txt.getToolTipText() + " phải là số nguyên.");
            return false;
        }
    }

    /*
     * Kiểm thử học phí Chuyên Đề
     */
    public static boolean checkHocPhi(JTextField txt) {
        try {
            float hp = Float.parseFloat(txt.getText());
            if (hp >= 0) {
                return true;
            } else {
                XDialog.alert(txt.getRootPane(), txt.getToolTipText() + " phải là lớn hơn bằng 0.");
                return false;
            }
        } catch (NumberFormatException e) {
            XDialog.alert(txt.getRootPane(), txt.getToolTipText() + " phải là số thực.");
            return false;
        }
    }

    //--------------- NGƯỜI HỌC ---------------//
    /*
     * Kiểm thử mã Người Học
     */
    public static boolean checkMaNH(JTextField txt) {
        String id = txt.getText();
        String rgx = "[a-zA-Z0-9]{7}";
        if (id.matches(rgx)) {
            return true;
        } else {
            XDialog.alert(txt.getRootPane(), txt.getToolTipText() + " phải có đúng 7 kí tự\n(chữ hoa, thường không dấu hoặc số).");
            return false;
        }
    }

    /*
     * Kiêm tra trùng mã Người Học
     */
    public static boolean checkTrungNH(JTextField txt) {
        if (nhdao.selectById(txt.getText()) == null) {
            return true;
        } else {
            XDialog.alert(txt.getRootPane(), "Mã người học '" + txt.getText() + "' đã tồn tại.");
            return false;
        }
    }

    /*
     * Kiểm thử số điện thoại
     */
    public static boolean checkSDT(JTextField txt) {
        String id = txt.getText();
        String rgx = "(086|096|097|098|032|033|034|035|036|037|038|039|089|090|093|070|079|077|078|076|088|091|094|083|084|085|081|082|092|056|058|099|059)[0-9]{7}";
        if (id.matches(rgx)) {
            return true;
        } else {
            XDialog.alert(txt.getRootPane(), txt.getToolTipText() + " phải gồm 10 số\nĐúng đầu số của các nhà mạng Viettel, Vinaphone, Mobifone,..");
            return false;
        }
    }

    /*
     * Kiểm tra ngày sinh Người Học
     */
    public static boolean check17Nam(JTextField txt) {
        Date date = XDate.toDate(txt.getText());
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(date);
        c2.setTime(XDate.now());
        long a = (c2.getTime().getTime() - c1.getTime().getTime()) / (24 * 3600 * 1000);
        if (a >= 6210) {
            return true;
        } else {
            XDialog.alert(txt.getRootPane(), txt.getToolTipText() + " phải cách đây ít nhất 17 tuổi.");
            return false;
        }
    }

    //--------------- NHÂN VIÊN ---------------//
    /*
     * Kiểm thử mã Nhân Viên
     */
    public static boolean checkMaNV(JTextField txt) {
        String id = txt.getText();
        String rgx = "[a-zA-Z0-9]{3,15}";
        if (id.matches(rgx)) {
            return true;
        } else {
            XDialog.alert(txt.getRootPane(), txt.getToolTipText() + " phải có từ 3-15 kí tự\n(chữ hoa, thường không dấu hoặc số).");
            return false;
        }
    }

    /*
     * Kiểm tra trùng mã Nhân Viên
     */
    public static boolean checkTrungNV(JTextField txt) {
        if (nvdao.selectById(txt.getText()) == null) {
            return true;
        } else {
            XDialog.alert(txt.getRootPane(), "Mã '" + txt.getText() + "' đã tồn tại.");
            return false;
        }
    }

    /*
     * Kiểm thử mật khẩu Nhân Viên
     */
    public static boolean checkPassNV(JPasswordField txt) {
        if (txt.getPassword().length > 2 && txt.getPassword().length < 17) {
            return true;
        } else {
            XDialog.alert(txt.getRootPane(), txt.getToolTipText() + " phải có từ 3-16 kí tự.");
            return false;
        }
    }

    /*
     * Kiểm thử ngày tháng năm
     */
    public static boolean isValidDate(String date) {
        if (date == null) {
            return false;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        if (date.trim().length() != sdf.toPattern().length()) {
            return false;
        }
        sdf.setLenient(false);
        try {
            sdf.parse(date.trim());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /*
     * Kiểm thử ngày tháng năm
     */
    public static boolean checkDate(JTextField txt) {
        String date = txt.getText();
        if (isValidDate(date)) {
            return true;
        } else {
            XDialog.alert(txt.getRootPane(), "Không đúng định dạng dd/MM/yyyy.");
            return false;
        }
    }

    /*
     * Kiểm thử tên Nhân Viên, Người Học
     */
    public static boolean checkName(JTextField txt) {
        String id = txt.getText();
        String rgx = "^[A-Za-zÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚÝàáâãèéêìíòóôõùúýĂăĐđĨĩŨũƠơƯưẠ-ỹ ]{3,25}$";
        if (id.matches(rgx)) {
            return true;
        } else {
            XDialog.alert(txt.getRootPane(), txt.getToolTipText() + " phải là tên có dấu hoặc không và từ 3-25 kí tự.");
            return false;
        }
    }

    /*
     * Kiêm tra ngày tạo Khoá Học
     */
    public static boolean check5Ngay(JTextField txt, JTextField txt2) {
        Date date = XDate.toDate(txt.getText());
        Date date2 = XDate.toDate(txt2.getText());
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(date);
        c2.setTime(date2);
        long a = (c1.getTime().getTime() - c2.getTime().getTime()) / (24 * 3600 * 1000);
        if (a >= 5) {
            return true;
        } else {
            XDialog.alert(txt.getRootPane(), "Ngày khai giảng phải cách ngày tạo ít nhất 5 ngày.");
            return false;
        }
    }

    /*
     * Kiểm thử Email
     */
    public static boolean checkEmail(JTextField txt) {
        String id = txt.getText();
        String rgx = "^[a-zA-Z][a-zA-Z0-9_\\.]{5,32}@[a-zA-Z0-9]{2,}(\\.[a-zA-Z0-9]{2,5}){1,2}$";
        if (id.matches(rgx)) {
            return true;
        } else {
            XDialog.alert(txt.getRootPane(), txt.getToolTipText() + " không đúng định dạng (ký tự có dấu)"
                    + "\nhoặc bị giới hạn kí tự cho phép.");
            return false;
        }
    }

    /*
     * Kiểm thử nhập rỗng TextField
     */
    public static boolean checkNullText(JTextField txt) {
        if (txt.getText().trim().length() > 0) {
            return true;
        } else {
            XDialog.alert(txt.getRootPane(), txt.getToolTipText() + " không được để trống.");
            return false;
        }
    }

    /*
     * Kiểm thử nhập rỗng TextArea
     */
    public static boolean checkNullText(JTextArea txt) {
        if (txt.getText().trim().length() > 0) {
            return true;
        } else {
            XDialog.alert(txt.getRootPane(), txt.getToolTipText() + " không được để trống.");
            return false;
        }
    }

    /*
     * Kiểm tra mật khẩu rỗng
     */
    public static boolean checkNullPass(JPasswordField txt) {
        if (txt.getPassword().length > 0) {
            return true;
        } else {
            XDialog.alert(txt.getRootPane(), txt.getToolTipText() + " không được để trống.");
            return false;
        }
    }

    /*
     * Kiểm tra ảnh chưa được chọn
     */
    public static boolean checkNullHinh(JLabel lbl) {
        if (lbl.getToolTipText() != null) {
            return true;
        } else {
            XDialog.alert(lbl.getRootPane(), lbl.getToolTipText() + " không được để trống.");
            return false;
        }
    }
}

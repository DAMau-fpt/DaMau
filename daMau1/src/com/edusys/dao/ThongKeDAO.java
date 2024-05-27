/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.dao;

import com.edusys.utils.XJDBC;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author duyplus
 */
public class ThongKeDAO {

    public static ResultSet rs = null; // Trả về kết quả truy vấn
    public static String BANGDIEM_SQL = "{CALL sp_BangDiem (?)}";
    public static String LUONGNGUOIHOC_SQL = "{CALL sp_ThongKeNguoiHoc}";
    public static String DIEMCHUYENDE_SQL = "{CALL sp_ThongKeDiem}";
    public static String DOANHTHU_SQL = "{CALL sp_ThongKeDoanhThu (?)}";

    private List<Object[]> getListOfArray(String sql, String[] cols, Object... args) {
        try {
            List<Object[]> list = new ArrayList<>();
            rs = XJDBC.query(sql, args);
            while (rs.next()) {
                Object[] vals = new Object[cols.length];
                for (int i = 0; i < cols.length; i++) {
                    vals[i] = rs.getObject(cols[i]);
                }
                list.add(vals);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Object[]> getBangDiem(Integer makh) {
        String[] cols = {"MaNH", "HoTen", "Diem"};
        return this.getListOfArray(BANGDIEM_SQL, cols, makh);
    }

    public List<Object[]> getLuongNguoiHoc() {
        String[] cols = {"Nam", "SoLuong", "DauTien", "CuoiCung"};
        return this.getListOfArray(LUONGNGUOIHOC_SQL, cols);
    }

    public List<Object[]> getDiemChuyenDe() {
        String[] cols = {"ChuyenDe", "SoHV", "ThapNhat", "CaoNhat", "TrungBinh"};
        return this.getListOfArray(DIEMCHUYENDE_SQL, cols);
    }

    public List<Object[]> getDoanhThu(int nam) {
        String[] cols = {"ChuyenDe", "SoKH", "SoHV", "DoanhThu", "ThapNhat", "CaoNhat", "TrungBinh"};
        return this.getListOfArray(DOANHTHU_SQL, cols, nam);
    }
}

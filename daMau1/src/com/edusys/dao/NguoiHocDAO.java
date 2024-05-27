/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.dao;

import com.edusys.utils.XJDBC;
import com.edusys.model.NguoiHoc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author duyplus
 */
public class NguoiHocDAO extends EduSysDAO<NguoiHoc, String> {

    public static ResultSet rs = null; // Trả về kết quả truy vấn
    public static String INSERT_SQL = "INSERT INTO NguoiHoc(MaNH,HoTen,NgaySinh,GioiTinh,DienThoai,Email,GhiChu,MaNV,NgayDK,Hinh) "
            + "VALUES (?,?,?,?,?,?,?,?,?,?)";
    public static String UPDATE_SQL = "UPDATE NguoiHoc SET HoTen=?,NgaySinh=?,GioiTinh=?,DienThoai=?,Email=?,GhiChu=?,MaNV=?,Hinh=? "
            + "WHERE MaNH=?";
    public static String DELETE_SQL = "DELETE FROM NguoiHoc WHERE MaNH=?";
    public static String SELECT_ALL_SQL = "SELECT * FROM NguoiHoc";
    public static String SELECT_BY_ID_SQL = "SELECT * FROM NguoiHoc WHERE MaNH=?";
    public static String SELECT_BY_KEYWORD_SQL = "SELECT * FROM NguoiHoc WHERE (HoTen LIKE ? "
            + "OR MaNH LIKE ? OR DienThoai LIKE ?) AND MaNH NOT IN (SELECT MaNH FROM HocVien WHERE MaKH=?)";

    @Override
    public void insert(NguoiHoc entity) {
        XJDBC.update(INSERT_SQL,
                entity.getMaNH(),
                entity.getHoTen(),
                entity.getNgaySinh(),
                entity.isGioiTinh(),
                entity.getDienThoai(),
                entity.getEmail(),
                entity.getGhiChu(),
                entity.getMaNV(),
                entity.getNgayDK(),
                entity.getHinh());
    }

    @Override
    public void update(NguoiHoc entity) {
        XJDBC.update(UPDATE_SQL,
                entity.getHoTen(),
                entity.getNgaySinh(),
                entity.isGioiTinh(),
                entity.getDienThoai(),
                entity.getEmail(),
                entity.getGhiChu(),
                entity.getMaNV(),
                entity.getHinh(),
                entity.getMaNH());
    }

    @Override
    public void delete(String key) {
        XJDBC.update(DELETE_SQL, key);
    }

    @Override
    public List<NguoiHoc> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public NguoiHoc selectById(String key) {
        List<NguoiHoc> list = selectBySql(SELECT_BY_ID_SQL, key);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    protected List<NguoiHoc> selectBySql(String sql, Object... args) {
        List<NguoiHoc> list = new ArrayList<>();
        try {
            try {
                rs = XJDBC.query(sql, args);
                while (rs.next()) {
                    NguoiHoc entity = new NguoiHoc();
                    entity.setMaNH(rs.getString("MaNH"));
                    entity.setHoTen(rs.getString("HoTen"));
                    entity.setNgaySinh(rs.getDate("NgaySinh"));
                    entity.setGioiTinh(rs.getBoolean("GioiTinh"));
                    entity.setDienThoai(rs.getString("DienThoai"));
                    entity.setEmail(rs.getString("Email"));
                    entity.setGhiChu(rs.getString("GhiChu"));
                    entity.setMaNV(rs.getString("MaNV"));
                    entity.setNgayDK(rs.getDate("NgayDK"));
                    entity.setHinh(rs.getString("Hinh"));
                    list.add(entity);
                }
            } finally {
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return list;
    }

    public List<NguoiHoc> selectByKeyword(int makh, String key) {
        return this.selectBySql(SELECT_BY_KEYWORD_SQL, "%" + key + "%", "%" + key + "%", "%" + key + "%", makh);
    }
}

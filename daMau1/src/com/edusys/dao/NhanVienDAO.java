/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.dao;

import com.edusys.utils.XJDBC;
import com.edusys.model.NhanVien;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author duyplus
 */
public class NhanVienDAO extends EduSysDAO<NhanVien, String> {

    public static ResultSet rs = null; // Trả về kết quả truy vấn
    public static String INSERT_SQL = "INSERT INTO NhanVien (MaNV,MatKhau,HoTen, Email,VaiTro) VALUES (?,?,?,?,?)";
    public static String UPDATE_SQL = "UPDATE NhanVien SET MatKhau=?,HoTen=?,Email=?,VaiTro=? WHERE MaNV=?";
    public static String DELETE_SQL = "DELETE FROM NhanVien WHERE MaNV=?";
    public static String SELECT_ALL_SQL = "SELECT * FROM NhanVien";
    public static String SELECT_BY_ID_SQL = "SELECT * FROM NhanVien WHERE MaNV=?";

    @Override
    public void insert(NhanVien entity) {
        XJDBC.update(INSERT_SQL,
                entity.getMaNV(),
                entity.getMatKhau(),
                entity.getHoTen(),
                entity.getEmail(),
                entity.isVaiTro());
    }

    @Override
    public void update(NhanVien entity) {
        XJDBC.update(UPDATE_SQL,
                entity.getMatKhau(),
                entity.getHoTen(),
                entity.getEmail(),
                entity.isVaiTro(),
                entity.getMaNV());
    }

    @Override
    public void delete(String key) {
        XJDBC.update(DELETE_SQL, key);
    }

    @Override
    public List<NhanVien> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public NhanVien selectById(String key) {
        List<NhanVien> list = selectBySql(SELECT_BY_ID_SQL, key);
        return !list.isEmpty() ? list.get(0) : null;
    }

    @Override
    protected List<NhanVien> selectBySql(String sql, Object... args) {
        List<NhanVien> list = new ArrayList<>();
        try {
            rs = XJDBC.query(sql, args);
            while (rs.next()) {
                NhanVien entity = new NhanVien();
                entity.setMaNV(rs.getString("MaNV"));
                entity.setMatKhau(rs.getString("MatKhau"));
                entity.setHoTen(rs.getString("HoTen"));
                entity.setEmail(rs.getString("Email"));
                entity.setVaiTro(rs.getBoolean("VaiTro"));
                list.add(entity);
            }
            return list;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.dao;

import com.edusys.utils.XJDBC;
import com.edusys.model.HocVien;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author duyplus
 */
public class HocVienDAO extends EduSysDAO<HocVien, Integer> {

    public static ResultSet rs = null; // Trả về kết quả truy vấn
    public static String INSERT_SQL = "INSERT INTO HocVien(MaKH,MaNH,Diem) VALUES(?,?,?)";
    public static String UPDATE_SQL = "UPDATE HocVien SET MaKH=?,MaNH=?,Diem=? WHERE MaHV=?";
    public static String DELETE_SQL = "DELETE FROM HocVien WHERE MaHV=?";
    public static String SELECT_ALL_SQL = "SELECT * FROM HocVien";
    public static String SELECT_BY_ID_SQL = "SELECT * FROM HocVien WHERE MaHV=?";
    public static String SELECT_BY_KH_SQL = "SELECT * FROM HocVien WHERE MaKH=?";

    @Override
    public void insert(HocVien entity) {
        XJDBC.update(INSERT_SQL,
                entity.getMaKH(),
                entity.getMaNH(),
                entity.getDiem());
    }

    @Override
    public void update(HocVien entity) {
        XJDBC.update(UPDATE_SQL,
                entity.getMaKH(),
                entity.getMaNH(),
                entity.getDiem(),
                entity.getMaHV());
    }

    @Override
    public void delete(Integer key) {
        XJDBC.update(DELETE_SQL, key);
    }

    @Override
    public List<HocVien> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public HocVien selectById(Integer key) {
        List<HocVien> list = selectBySql(SELECT_BY_ID_SQL, key);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    protected List<HocVien> selectBySql(String sql, Object... args) {
        List<HocVien> list = new ArrayList<>();
        try {
            try {
                rs = XJDBC.query(sql, args);
                while (rs.next()) {
                    HocVien entity = new HocVien();
                    entity.setMaHV(rs.getInt("MaHV"));
                    entity.setMaKH(rs.getInt("MaKH"));
                    entity.setMaNH(rs.getString("MaNH"));
                    entity.setDiem(rs.getDouble("Diem"));
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

    public List<HocVien> selectByKhoahoc(int makh) {
        return this.selectBySql(SELECT_BY_KH_SQL, makh);
    }
}

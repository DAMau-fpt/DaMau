/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.dao;

import com.edusys.utils.XJDBC;
import com.edusys.model.KhoaHoc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author duyplus
 */
public class KhoaHocDAO extends EduSysDAO<KhoaHoc, Integer> {

    public static ResultSet rs = null; // Trả về kết quả truy vấn
    public static String INSERT_SQL = "INSERT INTO KhoaHoc (MaCD,HocPhi,ThoiLuong,NgayKG,GhiChu,MaNV,NgayTao)"
            + " VALUES (?,?,?,?,?,?,?)";
    public static String UPDATE_SQL = "UPDATE KhoaHoc SET MaCD=?,HocPhi=?,ThoiLuong=?,NgayKG=?,GhiChu=?,MaNV=?"
            + " WHERE MaKH=?";
    public static String DELETE_SQL = "DELETE FROM KhoaHoc WHERE MaKH=?";
    public static String SELECT_ALL_SQL = "SELECT * FROM KhoaHoc";
    public static String SELECT_BY_ID_SQL = "SELECT * FROM KhoaHoc WHERE MaKH=?";
    public static String SELECT_BY_CD_SQL = "SELECT * FROM KhoaHoc WHERE MaCD=?";
    public static String SELECT_YEARS_SQL = "SELECT DISTINCT year(NgayKG) Year FROM KhoaHoc ORDER BY Year DESC";

    @Override
    public void insert(KhoaHoc entity) {
        XJDBC.update(INSERT_SQL,
                entity.getMaCD(),
                entity.getHocPhi(),
                entity.getThoiLuong(),
                entity.getNgayKG(),
                entity.getGhiChu(),
                entity.getMaNV(),
                entity.getNgayTao());
    }

    @Override
    public void update(KhoaHoc entity) {
        XJDBC.update(UPDATE_SQL,
                entity.getMaCD(),
                entity.getHocPhi(),
                entity.getThoiLuong(),
                entity.getNgayKG(),
                entity.getGhiChu(),
                entity.getMaNV(),
                entity.getMaKH());
    }

    @Override
    public void delete(Integer key) {
        XJDBC.update(DELETE_SQL, key);
    }

    @Override
    public List<KhoaHoc> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public KhoaHoc selectById(Integer key) {
        List<KhoaHoc> list = selectBySql(SELECT_BY_ID_SQL, key);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    protected List<KhoaHoc> selectBySql(String sql, Object... args) {
        List<KhoaHoc> list = new ArrayList<>();
        try {
            try {
                rs = XJDBC.query(sql, args);
                while (rs.next()) {
                    KhoaHoc entity = new KhoaHoc();
                    entity.setMaKH(rs.getInt("MaKH"));
                    entity.setHocPhi(rs.getDouble("HocPhi"));
                    entity.setThoiLuong(rs.getInt("ThoiLuong"));
                    entity.setNgayKG(rs.getDate("NgayKG"));
                    entity.setGhiChu(rs.getString("GhiChu"));
                    entity.setMaNV(rs.getString("MaNV"));
                    entity.setNgayTao(rs.getDate("NgayTao"));
                    entity.setMaCD(rs.getString("MaCD"));
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

    public List<KhoaHoc> selectByChuyenDe(String macd) {
        return this.selectBySql(SELECT_BY_CD_SQL, macd);
    }

    public List<Integer> selectByNam() {
        List<Integer> list = new ArrayList<>();
        try {
            ResultSet rs = XJDBC.query(SELECT_YEARS_SQL);
            while (rs.next()) {
                list.add(rs.getInt(1));
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}

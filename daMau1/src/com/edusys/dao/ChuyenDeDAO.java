/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.dao;

import com.edusys.utils.XJDBC;
import com.edusys.model.ChuyenDe;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author duyplus
 */
public class ChuyenDeDAO extends EduSysDAO<ChuyenDe, String> {

    public static ResultSet rs = null; // Trả về kết quả truy vấn
    public static String INSERT_SQL = "INSERT INTO ChuyenDe (MaCD,TenCD,HocPhi,ThoiLuong,Hinh,MoTa) VALUES (?,?,?,?,?,?)";
    public static String UPDATE_SQL = "UPDATE ChuyenDe SET TenCD=?,HocPhi=?,ThoiLuong=?,Hinh=?,MoTa=? WHERE MaCD=?";
    public static String DELETE_SQL = "DELETE FROM ChuyenDe WHERE MaCD=?";
    public static String SELECT_ALL_SQL = "SELECT * FROM ChuyenDe";
    public static String SELECT_BY_ID_SQL = "SELECT * FROM ChuyenDe WHERE MaCD=?";

    @Override
    public void insert(ChuyenDe entity) {
        XJDBC.update(INSERT_SQL,
                entity.getMaCD(),
                entity.getTenCD(),
                entity.getHocPhi(),
                entity.getThoiLuong(),
                entity.getHinh(),
                entity.getMoTa());
    }

    @Override
    public void update(ChuyenDe entity) {
        XJDBC.update(UPDATE_SQL,
                entity.getTenCD(),
                entity.getHocPhi(),
                entity.getThoiLuong(),
                entity.getHinh(),
                entity.getMoTa(),
                entity.getMaCD());
    }

    @Override
    public void delete(String key) {
        XJDBC.update(DELETE_SQL, key);
    }

    @Override
    public List<ChuyenDe> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public ChuyenDe selectById(String key) {
        List<ChuyenDe> list = selectBySql(SELECT_BY_ID_SQL, key);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    protected List<ChuyenDe> selectBySql(String sql, Object... args) {
        List<ChuyenDe> list = new ArrayList<>();
        try {
            try {
                rs = XJDBC.query(sql, args);
                while (rs.next()) {
                    ChuyenDe entity = new ChuyenDe();
                    entity.setMaCD(rs.getString("MaCD"));
                    entity.setHinh(rs.getString("Hinh"));
                    entity.setHocPhi(rs.getDouble("HocPhi"));
                    entity.setMoTa(rs.getString("MoTa"));
                    entity.setTenCD(rs.getString("TenCD"));
                    entity.setThoiLuong(rs.getInt("ThoiLuong"));
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
}

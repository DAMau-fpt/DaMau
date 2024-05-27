/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package com.edusys.testing;

import com.edusys.utils.XJDBC;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tien
 */
public class TestJdbcHelper {

    public static void inDuLieu(ResultSet rs, ResultSetMetaData rsMeta, int col) {
        try {
            while (rs.next()) {
                for (int i = 1; i <= col; i++) {
                    System.out.print(rs.getObject(i) + " ");
                }
                System.out.println("\n------------------------------------");

            }
        } catch (SQLException ex) {
            Logger.getLogger(TestJdbcHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        ResultSet rs = null;
        ResultSetMetaData rsMeta = null;
        int col = 0;
        String UPDATE_SQL = "UPDATE NguoiHoc SET DienThoai = ? WHERE MaNH=?";
        String INSERT_SQL = "INSERT INTO NguoiHoc(MaNH,HoTen,NgaySinh,GioiTinh,DienThoai,Email,GhiChu,MaNV,NgayDK,Hinh) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?)";
        String DELETE_SQL = "DELETE FROM NguoiHoc WHERE MaNH=?";
        String SELECT_ALL_SQL = "SELECT * FROM NguoiHoc";
        String SELECT_BY_ID_SQL = "SELECT * FROM NguoiHoc WHERE MaNH=?";
        try {
            //Them NguoiHoc
            System.out.println("Them NguoiHoc");
            XJDBC.update(INSERT_SQL, "001", "A", new Date(), true, "0123456789", "a@gmail.com", "khong co", "001", new Date(), "NoImage.png");
            XJDBC.update(INSERT_SQL, "002", "B", new Date(), true, "0123456788", "b@gmail.com", "khong co", "001", new Date(), "NoImage.png");
            XJDBC.update(INSERT_SQL, "003", "C", new Date(), true, "0123456787", "c@gmail.com", "khong co", "001", new Date(), "NoImage.png");
            XJDBC.update(INSERT_SQL, "004", "D", new Date(), true, "0123456786", "d@gmail.com", "khong co", "001", new Date(), "NoImage.png");
            XJDBC.update(INSERT_SQL, "005", "E", new Date(), true, "0123456785", "e@gmail.com", "khong co", "001", new Date(), "NoImage.png");
            //Sua thong tin
            System.out.println("----------\nSua thong tin");
            XJDBC.update(UPDATE_SQL, "0123456777", "001");
            //Xoa theo ma
            System.out.println("----------\nXoa theo ma");
            XJDBC.update(DELETE_SQL, "005");
            //truy van theo ma
            System.out.println("Truy van theo ma");
            rs = XJDBC.query(SELECT_BY_ID_SQL, "001");
            rsMeta = rs.getMetaData();
            col = rsMeta.getColumnCount();
            inDuLieu(rs, rsMeta, col);
            System.out.println("Truy van tat ca");
            
            //truy van tat ca
            rs = XJDBC.query(SELECT_ALL_SQL);
            rsMeta = rs.getMetaData();
            col = rsMeta.getColumnCount();
            inDuLieu(rs, rsMeta, col);
            
            //Goi proc
            System.out.println("Thong ke nguoi hoc");
            rs = XJDBC.query("{CALL sp_ThongKeNguoiHoc}");
            rsMeta = rs.getMetaData();
            col = rsMeta.getColumnCount();
            inDuLieu(rs, rsMeta, col);
            
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

}

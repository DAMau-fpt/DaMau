package com.edusys.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author duyplus
 */
public class XJDBC {

    public static Connection conn = null; // Kết nối với sql
    public static PreparedStatement ps = null; // Câu lệnh SQL được biên dịch trước
    public static ResultSet rs = null; // Trả về kết quả truy vấn

    private static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static String dburl = "jdbc:sqlserver://26.68.248.135\\DAFPL:1433;databaseName=Polypro;encrypt = false;";
    private static String dbuser = "sa";
    private static String dbpass = "123456";

    /*
     * Nạp driver
     */
    static {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    /*
     * Xây dựng PreparedStatement
     *
     */
    public static PreparedStatement getStmt(String sql, Object... args) {
        try {
            conn = DriverManager.getConnection(dburl, dbuser, dbpass);
            if (sql.trim().startsWith("{")) {
                ps = conn.prepareCall(sql);
            } else {
                ps = conn.prepareStatement(sql);
            }
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            return ps;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /*
     * Thực hiện câu lệnh SQL thao tác (INSERT, UPDATE, DELETE) hoặc thủ tục lưu thao tác dữ liệu
     *
     */
    public static void update(String sql, Object... args) {
        try {
            ps = XJDBC.getStmt(sql, args);
            try {
                ps.executeUpdate();
            } finally {
                ps.getConnection().close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /*
     * Thực hiện câu lệnh SQL truy vấn (SELECT) hoặc thủ tục lưu truy vấn dữ liệu
     */
    public static ResultSet query(String sql, Object... args) {
        try {
            ps = XJDBC.getStmt(sql, args);
            return ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
}

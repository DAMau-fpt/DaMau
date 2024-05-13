/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConnectDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Tien
 */
public class ConnectDB {
    public static String user = "sa";
    public static String pass = "123456";
    public static String diverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    public static String url = "jdbc:sqlserver://26.68.248.135\\DAFPL:1433;databaseName=Polypro;encrypt = false;";
    public static Connection connection;
    public static Connection getConnection(){
        try{
            Class.forName(diverName);
            connection = DriverManager.getConnection(url, user, pass);
        }catch(ClassNotFoundException | SQLException e){
            System.out.println(e);
        }

        return connection;
    }
    //test connect
    public static void main(String[] args) {
        Connection con = ConnectDB.getConnection();
        String s = con == null ?  "that bai" : "thanh cong";
        System.out.println(s);
    }
}

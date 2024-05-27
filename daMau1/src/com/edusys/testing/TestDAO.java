/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package com.edusys.testing;

import com.edusys.dao.NguoiHocDAO;
import com.edusys.dao.ThongKeDAO;
import com.edusys.model.NguoiHoc;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Tien
 */
public class TestDAO {
    public static void main(String[] args) {
        //them du lieu
        NguoiHocDAO dao = new NguoiHocDAO();
        ThongKeDAO tkdao = new ThongKeDAO();
        for(int i=1 ; i <=5 ; i++){
            NguoiHoc nh = new NguoiHoc("00" + (5 + i), (5 + i) + "", "01234567" + (5 + i) + (5 + i), "test" + (5 + i) + "@gmail.com", "day la du lieu test", "001", new Date());
            dao.insert(nh);
        }
        //sua du lieu
        NguoiHoc update = new NguoiHoc();
        update.setMaNH("006");
        update.setHoTen("006");
        update.setNgaySinh(new Date());
        update.setGioiTinh(true);
        update.setDienThoai("0123456700");
        update.setEmail("");
        update.setGhiChu("day la doi tuong duoc update");
        update.setMaNV("001");
        update.setNgayDK(new Date());
        update.setHinh("");
        dao.update(update);
        //xoa du lieu
        dao.delete("006");
        //truy van theo ma
        System.out.println(dao.selectById("001"));
        //truy van tat ca
        System.out.println(dao.selectAll());
        //goi proc
        List<Object[]> luongNguoiHoc = tkdao.getLuongNguoiHoc();
        System.out.println("Luong Nguoi Hoc:");
        for (Object[] row : luongNguoiHoc) {
            System.out.println("Nam: " + row[0] + ", SoLuong: " + row[1] + ", DauTien: " + row[2] + ", CuoiCung: " + row[3]);
        }
    }
}

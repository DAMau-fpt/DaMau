/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package com.edusys.utils;

import java.awt.HeadlessException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author duyplus
 */
public class XExcel {

    public static void writeToExcel(JTable table, String title) {
        XSSFWorkbook wb = null;
        FileOutputStream fos = null;
        try {
            wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet(title);

            JFileChooser fc = new JFileChooser("D:\\");
            fc.setDialogTitle("Save as...");
            FileNameExtensionFilter fnef = new FileNameExtensionFilter("Files", "xls", "xlsx", "xlsm");
            fc.setFileFilter(fnef);
            int chooser = fc.showSaveDialog(null);
            if (chooser == JFileChooser.APPROVE_OPTION) {
                TableModel tm = table.getModel();
                TableColumnModel tcm = table.getTableHeader().getColumnModel();
                XSSFRow rowtitle = sheet.createRow((short) 0);
                for (int i = 0; i < tcm.getColumnCount(); i++) {
                    // Set font header
                    XSSFFont font = wb.createFont();
                    font.setFontHeightInPoints((short) 12);
                    font.setColor(IndexedColors.RED.getIndex());
                    font.setBold(true);
                    // Set style header
                    XSSFCellStyle cs = wb.createCellStyle();
                    cs.setAlignment(HorizontalAlignment.CENTER);
                    cs.setVerticalAlignment(VerticalAlignment.CENTER);
                    cs.setFont(font);
                    // Create header cell
                    XSSFCell cell = rowtitle.createCell((short) i);
                    cell.setCellValue(tcm.getColumn(i).getHeaderValue().toString());
                    cell.setCellStyle(cs);
                }
                for (int i = 0; i < tm.getRowCount(); i++) {
                    XSSFFont font1 = wb.createFont();
                    // Set font body
                    font1.setFontHeightInPoints((short) 10);
                    // Set style
                    XSSFCellStyle cs1 = wb.createCellStyle();
                    cs1.setFont(font1);
                    XSSFRow row = sheet.createRow((short) i+1);
                    for (int j = 0; j < tm.getColumnCount(); j++) {
                        // Create body cell
                        XSSFCell cell = row.createCell((short) j);
                        cell.setCellValue(Objects.toString(tm.getValueAt(i, j), ""));
                        cell.setCellStyle(cs1);
                        // Set fit column
                        for (int k = 0; k < row.getLastCellNum(); k++) {
                            wb.getSheetAt(0).autoSizeColumn(k);
                        }
                    }
                }
                fos = new FileOutputStream(fc.getSelectedFile() + ".xlsx");
                wb.write(fos);
                XDialog.alert(null, "Xuất dữ liệu thành công!");
                fos.close();
            }
        } catch (HeadlessException | IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (wb != null) {
                    wb.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}

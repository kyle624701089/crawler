package com.kyle.crawler.utils;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.CellFormat;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Excels2OneUtil {
    private final static Log logger = LogFactory.getLog(Excels2OneUtil.class);
    public static void mergeExcel(String outputFileName, List<String> inputFileNameArray) {
        /*if (inputFileNameArray.size() == 1) {
            logger.info("至少需要两个文件才能合并，请验证！！！");
            return;
        }*/
        try {
            WritableWorkbook outputExcel = Workbook.createWorkbook(new File(outputFileName));
            int index = 0;
            for (String fileName : inputFileNameArray) {
                // 创建excel文件的工作簿对象book
                Workbook inputExcel = Workbook.getWorkbook(new FileInputStream(fileName));
                // 获取excel文件工作簿的工作表数量sheets
                Sheet[] sheets = inputExcel.getSheets();
//                ExecutorService executorService = Executors.newFixedThreadPool(50);
//                new ThreadPoolExecutor(sheets.length,)
                for (Sheet sheet : sheets) {
                    String sheetName = sheet.getName();
                    WritableSheet writableSheet = outputExcel.createSheet(sheetName, index);
                    System.out.println(sheetName);
                    copy(sheet, writableSheet);
                    index++;
                }
            }
            /** **********将以上缓存中的内容写到EXCEL文件中******** */
            outputExcel.write();
            /** *********关闭文件************* */
            outputExcel.close();
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            logger.error(sw.toString());
        }
    }

    private static void copy(Sheet formSheet, WritableSheet toWritableSheet) {
        // 行数
        int rows = formSheet.getRows();
        // 列数
        int columns = formSheet.getColumns();
        for (int rowIndex = 0; rowIndex < rows; rowIndex++) {
            for (int columnIndex = 0; columnIndex < columns; columnIndex++) {
                // 获取当前工作表.row_index,column_index单元格的cell对象
                Cell cell = formSheet.getCell(columnIndex, rowIndex);
                try {
                    CellFormat wcf = new WritableCellFormat();
                    if (cell.getCellFormat() != null) {
                        wcf = cell.getCellFormat();
                    }
                    toWritableSheet.addCell(new Label(columnIndex, rowIndex, cell.getContents(), wcf));
                } catch (Exception e) {
                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    e.printStackTrace(pw);
                    logger.error(sw.toString());
                }
            }
        }
    }
}

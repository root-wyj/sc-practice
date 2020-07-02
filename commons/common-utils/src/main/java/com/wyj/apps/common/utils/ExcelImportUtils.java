package com.wyj.apps.common.utils;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wuyingjie <wuyingjie@kuaishou.com>
 * Created on 2020-02-23
 */

@Slf4j
public class ExcelImportUtils {

    /**
     * 前缀
     */
    private static final String SUFFIX_2003 = ".xls";
    private static final String SUFFIX_2007 = ".xlsx";

    public static Workbook createWorkbook(InputStream file, String fileName) {
        if (file == null) {
            throw new RuntimeException("上传文件不能为空");
        }
        if (StringUtils.isBlank(fileName)) {
            throw new RuntimeException("文件名不能为空");
        }
        Workbook workbook = null;
        //初始化输入流
        try {
            if (fileName.endsWith(SUFFIX_2003)) {
                workbook = new HSSFWorkbook(file);
            } else if (fileName.endsWith(SUFFIX_2007)) {
                workbook = new XSSFWorkbook(file);
            }
            return workbook;
        } catch (Exception e) {
            log.error("解析Excel格式错误:", e);
            throw new RuntimeException("格式错误");
        }
    }

    /**
     * 解析Excel。<br>
     * 1. 默认去读workbook 第一页。<br>
     * 2. 默认 有header.<br>
     * 3. 会去空格 String.trim() <br/>
     * 4. 会过滤空行 <br/>
     */
    public static <T> List<T> readXls(InputStream file, String fileName, Class<T> clz)
            throws IllegalAccessException, InstantiationException {
        return readXls(file, fileName, clz, DateUtil.FORMAT_YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 解析Excel。<br>
     * 1. 默认去读workbook 第一页。<br>
     * 2. 默认 有header.<br>
     * 3. 会去空格 String.trim() <br/>
     * 4. 会过滤空行 <br/>
     */
    public static <T> List<T> readXls(InputStream file, String fileName, Class<T> clz, String dateFormatStr)
            throws IllegalAccessException, InstantiationException {
        List<T> result = new ArrayList<>();
        Workbook workbook = createWorkbook(file, fileName);
        Field[] fields = clz.getDeclaredFields();
        Sheet sheet = workbook.getSheetAt(0);
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            T oneData = clz.newInstance();
            Row row = sheet.getRow(i);
            if (row == null) {
                continue;
            }

            boolean isEmptyRow = true;

            for (Field field : fields) {
                field.setAccessible(true);
                ExcelCol excelCol = field.getAnnotation(ExcelCol.class);
                if (excelCol == null) {
                    continue;
                }
                int colIndex = excelCol.colIndex();
                Cell cell = row.getCell(colIndex);
                if (cell == null) {
                    continue;
                }
                String cellValue = getCellFormatValue(cell).trim();

                if (isEmptyRow && !StringUtils.isEmpty(cellValue)) {
                    isEmptyRow = false;
                }

                if (field.getType() == String.class) {
                    field.set(oneData, cellValue);
                } else if (field.getType() == BigDecimal.class) {
                    if (StringUtils.isBlank(cellValue)) {
                        continue;
                    }
                    field.set(oneData, new BigDecimal(cellValue));
                } else if (field.getType() == Date.class) {
                    Date vTmp = cellValue.isEmpty() ? null : DateUtil.parseDate(cellValue, dateFormatStr);
                    field.set(oneData, vTmp);
                } else if (field.getType() == Long.class) {
                    field.set(oneData, Long.valueOf(cellValue));
                } else if (field.getType() == Integer.class) {
                    field.set(oneData, Integer.valueOf(cellValue));
                }
            }

            if (!isEmptyRow) {
                result.add(oneData);
            }
        }
        return result;
    }

    private static String getCellFormatValue(Cell cell) {
        String value = "";
        if (cell != null) {
            switch (cell.getCellType()) {
                case HSSFCell.CELL_TYPE_STRING: // 字符串
                    value = cell.getStringCellValue();
                    break;
                case HSSFCell.CELL_TYPE_NUMERIC: // 数字
                    //value = cell.getNumericCellValue() + "";
                    DecimalFormat df = new DecimalFormat("#.###");
                    value = df.format(cell.getNumericCellValue());
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        Date date = cell.getDateCellValue();
                        if (date != null) {
                            value = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
                        } else {
                            value = "";
                        }
                    }
                    break;
                case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
                    value = cell.getBooleanCellValue() + "";
                    break;
                case HSSFCell.CELL_TYPE_FORMULA: // 公式
                    value = cell.getCellFormula() + "";
                    break;
                case HSSFCell.CELL_TYPE_BLANK: // 空值
                    break;
                case HSSFCell.CELL_TYPE_ERROR: // 故障
                    value = "非法字符";
                    break;
                default:
                    value = "未知类型";
                    break;
            }
        }
        return value;
    }


    public static class Test {
        // checkImportList = ExcelImportUtils.readXls(file, AssetCheckImportExcelModel.class);
        @ExcelCol(colIndex = 0)
        private String name;
    }
}

package com.wyj.apps.common.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExcelUtil {

    public static void buildExcel(Workbook workbook, List<List<?>> list, List<Class<?>> clazz, List<String> names) {
        for (int i = 0; i < list.size(); i++) {
            buildExcel(workbook, list.get(i), clazz.get(i));
            workbook.setSheetName(i, names.get(i));
        }
    }

    /**
     * 对list数据源将其里面的数据导入到excel表单
     */
    public static void buildExcel(Workbook workbook, List<?> list, Class<?> clazz) {

        Map<String, Field> fields = getMappedFiled(clazz, null);
        List<Field> fields1 = getAllExcelAnnotFields(clazz, null);

        Sheet sheet = workbook.createSheet(); // 产生工作表对象
        Row row;
        Cell cell; // 产生单元格
        CellStyle style = workbook.createCellStyle();
        row = sheet.createRow(0); // 产生一行
        // 写入各个字段的列头名称
        for (Map.Entry<String, Field> entry : fields.entrySet()) {

            Field field = entry.getValue();
            ExcelCol attr = field.getAnnotation(ExcelCol.class);
            int col = attr.colIndex(); // 获得列号
            if (attr.showTitle()) {
                cell = row.createCell(col); // 创建列
                cell.setCellType(CellType.STRING); // 设置列中写入内容为String类型
                cell.setCellValue(attr.name()); // 写入列名
                cell.setCellStyle(style);
            }
        }

        int startNo = 0;
        int endNo = list.size();
        // 写入各条记录,每条记录对应excel表中的一行
        for (int i = startNo; i < endNo; i++) {
            row = sheet.createRow(i + 1 - startNo);
            Object vo = list.get(i); // 得到导出对象.
            // 写入每个单元格
            for (Map.Entry<String, Field> entry : fields.entrySet()) {
                Field field = entry.getValue(); // 获得field.
                field.setAccessible(true); // 设置实体类私有属性可访问
                ExcelCol attr = field.getAnnotation(ExcelCol.class); //每个属性的注解值
                try {
                    // 根据ExcelVOAttribute中设置情况决定是否导出,有些情况需要保持为空,希望用户填写这一列.
                    cell = row.createCell(attr.colIndex()); // 创建cell
                    cell.setCellType(CellType.STRING);
                    if (attr.showContent()) {

                        if (attr.combo().length > 0) {
                            ExcelCol.OPER operation = attr.operation();
                            String[] combos = attr.combo();
                            switch (operation) {
                                case ADD:
                                    break;
                                case APPEND:
                                    StringBuilder sb = new StringBuilder();
                                    String oper = combos[combos.length - 1];
                                    for (int j = 0; j < combos.length - 1; j++) {
                                        Field f = fields1.get(Integer.parseInt(combos[j]));
                                        f.setAccessible(true);
                                        sb = sb.append(fields1.get(Integer.parseInt(combos[j])).get(vo) == null ? "" :
                                                       fields1.get(Integer.parseInt(combos[j])).get(vo)).append(oper);
                                    }
                                    cell.setCellValue(
                                            String.valueOf(sb.substring(0, sb.length() - 1))); // 如果数据存在就填入,不存在填入空格.
                                    break;
                                case REPLACE:
                                    // 取单个号的值，下一个值为要替换的值
                                    for (int j = 0; j < combos.length - 1; j += 2) {
                                        Field f = fields1.get(attr.colIndex());
                                        f.setAccessible(true);
                                        if (String.valueOf(fields1.get(attr.colIndex()).get(vo)).equals(combos[j])) {
                                            cell.setCellValue(combos[j + 1] == null ? "" : combos[j + 1]);
                                        }
                                    }
                                    break;
                                case MULTIPLY:
                                    BigDecimal res = BigDecimal.ONE;
                                    for (String a : combos) {
                                        Field f = fields1.get(Integer.parseInt(a));
                                        f.setAccessible(true);
                                        res = res.multiply((BigDecimal) fields1.get(Integer.parseInt(a)).get(vo));
                                    }
                                    cell.setCellValue(String.valueOf(res)); // 如果数据存在就填入,不存在填入空格.
                                    break;
                                default:
                                    break;
                            }

                        } else {
                            cell.setCellValue(field.get(vo) == null ? ""
                                                                    : String
                                                      .valueOf(field.get(vo))); // 如果数据存在就填入,不存在填入空格.
                        }
                    }
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 解决的 excel 65535 行的问题
     */
    //    public void exportMaxExcel(List<T> list, String fileName,
    //            HttpServletResponse response) throws IOException {
    //
    //        Workbook workbook = new XSSFWorkbook();// 产生工作薄对象
    //        int size = list.size();
    //        int max = size / 65535;
    //        if (size % 65535 != 0) {
    //            max ++;
    //        }
    //        for (int i = 0; i <= max; i ++) {
    //            int start = i * 65535;
    //            if (start > size) {
    //                break;
    //            }
    //            int end;
    //            if ((i + 1) * 65535 >= size) {
    //                end = size;
    //            } else {
    //                end = (i + 1) * 65535;
    //            }
    //            buildExcel(workbook, list.subList(start, end), clazz);
    //        }
    //        exportExcel(response, fileName, workbook);
    //    }

    /**
     * 得到实体类所有通过注解映射了数据表的字段
     */
    private static Map<String, Field> getMappedFiled(Class clazz, HashMap<String, Field> fields) {
        if (fields == null) {
            fields = new LinkedHashMap<>();
        }

        Field[] allFields = clazz.getDeclaredFields(); // 得到所有定义字段
        // 得到所有field并存放到一个list中.
        for (Field field : allFields) {
            if (field.isAnnotationPresent(ExcelCol.class)) {
                // fields.add(field);
                fields.put(field.getName(), field);
            }
        }
        if (clazz.getSuperclass() != null
                && !clazz.getSuperclass().equals(Object.class)) {
            getMappedFiled(clazz.getSuperclass(), fields);
        }
        return fields;
    }

    private static List<Field> getAllExcelAnnotFields(Class clazz, List<Field> fields) {
        if (fields == null) {
            fields = new ArrayList<>();
        }

        Field[] allFields = clazz.getDeclaredFields(); // 得到所有定义字段
        // 得到所有field并存放到一个list中.
        for (Field field : allFields) {
            if (field.isAnnotationPresent(ExcelCol.class)) {
                fields.add(field);
            }
        }
        if (clazz.getSuperclass() != null
                && !clazz.getSuperclass().equals(Object.class)) {
            getAllExcelAnnotFields(clazz.getSuperclass(), fields);
        }
        return fields;
    }

    public static void exportExcel(HttpServletResponse response, String fileName, Workbook workbook)
            throws IOException {
        OutputStream out = null;
        try {
            out = response.getOutputStream();
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename="
                    .concat(String.valueOf(URLEncoder.encode(fileName + ".xlsx", "UTF-8"))));
            workbook.write(out);
        } catch (IOException e) {
            System.err.println("outputStream error" + e.getMessage());
            e.printStackTrace();
        } finally {
            out.close();
        }
    }

    public static void main(String[] args) throws IOException {

        Workbook workbook = new XSSFWorkbook();
        String excelName = "验收记录表";
        List<Test> list = new ArrayList<>();
        ExcelUtil.buildExcel(workbook, Collections.singletonList(list),
                Collections.singletonList(Test.class),
                Collections.singletonList(excelName));
        HttpServletResponse response = new HttpServletResponseWrapper(null);
        ExcelUtil.exportExcel(response, excelName, workbook);
    }

    @Data
    private static class Test {
        @ExcelCol(colIndex = 0, name = "name")
        private String name;
        @ExcelCol(colIndex = 1, name = "age")
        private String age;
    }
}
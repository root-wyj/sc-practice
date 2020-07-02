package com.wyj.apps.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wuyingjie <wuyingjie@kuaishou.com>
 * Created on 2020-04-22
 */
@Slf4j
public class ZipUtils {

    public static void zip(List<InputStream> inputStreamList, List<String> fileNameList, ZipOutputStream zipOutStream) {
        for (int i = 0; i < inputStreamList.size(); i++) {
            zip(inputStreamList.get(i), zipOutStream, fileNameList.get(i));
        }
    }

    public static void zip(InputStream in, ZipOutputStream out, String fileName) {
        byte[] buffer = new byte[1024];
        int len;
        try {
            out.putNextEntry(new ZipEntry(fileName));
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
        } catch (IOException e) {
            log.error("zip error, cause: {}", e.getMessage(), e);
            throw new RuntimeException("zip error");
        } finally {
            try {
                out.flush();
                out.closeEntry();
                in.close();
            } catch (IOException e) {
                log.error("close stream error, cause: {}", e.getMessage(), e);
            }
        }
    }

    public static void zipExcel(List<Workbook> xlsBook, List<String> fileNameList, ZipOutputStream zipOutStream) {
        for (int i = 0; i < xlsBook.size(); i++) {
            try {
                zipOutStream.putNextEntry(new ZipEntry(fileNameList.get(i)));
                xlsBook.get(i).write(zipOutStream);
            } catch (IOException e) {
                log.error("zip excel error, cause: {}", e.getMessage(), e);
                throw new RuntimeException("zip excel error");
            } finally {
                try {
                    zipOutStream.flush();
                    zipOutStream.closeEntry();
                } catch (IOException e) {
                    log.error("close stream error, cause: {}", e.getMessage(), e);
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        String zipName = "zip.zip";
        HttpServletResponse response = new HttpServletResponseWrapper(null);
        response.setContentType("APPLICATION/OCTET-STREAM");
        response.setHeader("Content-Disposition", "attachment; filename=" + zipName);
        ZipOutputStream out = new ZipOutputStream(response.getOutputStream());

        Workbook workbookCanteenAid = new XSSFWorkbook();
        List<ZipUtils> list = new ArrayList<>();
        ExcelUtil.buildExcel(workbookCanteenAid, list, ZipUtils.class);
        ZipUtils.zipExcel(Collections.singletonList(workbookCanteenAid),
                Collections.singletonList("excel_filename"), out);
        out.close();
    }
}

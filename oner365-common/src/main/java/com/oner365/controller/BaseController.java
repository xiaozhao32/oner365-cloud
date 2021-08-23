package com.oner365.controller;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;

import com.oner365.util.DataUtils;
import com.oner365.util.excel.ExportExcelUtils;

/**
 * Controller 父类
 * @author zhaoyong
 *
 */
public class BaseController {

    protected static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);

    @Value("${file.path}")
    protected String filePath;

    @Value("${file.excel.suffix}")
    protected String fileSuffix;

    /**
     * 导出excel
     * @param fileName 文件名称
     * @param titleKeys 字段
     * @param columnNames 参数
     * @param content 内容
     * @return ResponseEntity<byte[]>
     */
    protected <T> ResponseEntity<byte[]> exportExcel(String fileName, String[] titleKeys, String[] columnNames, List<T> content) {
        String name = fileName + "." + fileSuffix;
        ExportExcelUtils.exportExcel(name, titleKeys, columnNames, content, filePath, fileSuffix);
        File file = new File(filePath + File.separator + name);
        return DataUtils.download(file, name);
    }

}

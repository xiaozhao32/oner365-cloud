package com.oner365.controller;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.oner365.common.config.properties.DefaultFileProperties;
import com.oner365.util.DataUtils;
import com.oner365.util.excel.ExportExcelUtils;

/**
 * Controller 父类
 * @author zhaoyong
 *
 */
public class BaseController {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected DefaultFileProperties fileProperties;

    /**
     * 导出excel
     * @param fileName 文件名称
     * @param titleKeys 字段
     * @param columnNames 参数
     * @param content 内容
     * @return ResponseEntity<byte[]>
     */
    protected <T> ResponseEntity<byte[]> exportExcel(String fileName, String[] titleKeys, String[] columnNames, List<T> content) {
        String name = fileName + "." +fileProperties.getExcelSuffix();
        ExportExcelUtils.exportExcel(name, titleKeys, columnNames, content, fileProperties.getDownload(), fileProperties.getExcelSuffix());
        File file = DataUtils.getFile(fileProperties.getDownload(), name);
        return DataUtils.download(file, name);
    }

}

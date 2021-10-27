package com.oner365.dfs.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.tobato.fastdfs.domain.fdfs.FileInfo;
import com.oner365.common.ResponseResult;
import com.oner365.common.constants.PublicConstants;
import com.oner365.common.enums.ResultEnum;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.controller.BaseController;
import com.oner365.dfs.client.FastdfsClient;
import com.oner365.dfs.entity.FastdfsFile;
import com.oner365.dfs.service.IFastdfsFileService;
import com.oner365.util.DataUtils;

/**
 * 文件上传
 * @author zhaoyong
 */
@RestController
@RequestMapping("/fdfs")
public class FastdfsFileController extends BaseController {

    @Autowired
    private FastdfsClient fastdfsClient;
    
    @Autowired
    private IFastdfsFileService fastdfsFileService;

    /**
     * 查询列表
     *
     * @param data 查询参数
     * @return Page<FastdfsFile>
     */
    @PostMapping("/list")
    public Page<FastdfsFile> list(@RequestBody QueryCriteriaBean data) {
        return fastdfsFileService.pageList(data);
    }
    
    /**
     * 文件上传
     * 
     * @param file MultipartFile
     * @return ResponseResult<String>
     */
    @PostMapping("/uploadMultipartFile")
    public ResponseResult<String> uploadMultipartFile(@RequestBody MultipartFile file) {
        String url = fastdfsClient.uploadFile(file);
        return ResponseResult.success(url);
    }

    /**
     * 文件上传 File 类型
     * 
     * @param file File
     * @return ResponseResult<String>
     */
    @PostMapping("/uploadFile")
    public ResponseResult<String> uploadFile(@RequestBody File file) {
        String url = fastdfsClient.uploadFile(file);
        return ResponseResult.success(url);
    }

    /**
     * 文件下载
     * 
     * @param fileUrl  url 开头从组名开始
     * @param filename 文件名称
     * @param response HttpServletResponse
     */
    @GetMapping("/download")
    public void download(@RequestParam("fileUrl") String fileUrl, String filename, 
            HttpServletResponse response) {
        byte[] data = fastdfsClient.download(fileUrl);
        if (DataUtils.isEmpty(filename)) {
            filename = StringUtils.substringAfterLast(fileUrl, PublicConstants.DELIMITER);
        }

        try {
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment;filename=" + URLEncoder.encode(filename, StandardCharsets.UTF_8.name()));
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("download UnsupportedEncodingException:", e);
        }

        // 写出
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            IOUtils.write(data, outputStream);
        } catch (IOException e) {
            LOGGER.error("download IOException:", e);
        }

    }

    /**
     * 文件流下载
     * 
     * @param fileUrl url 开头从组名开始
     * @return byte[]
     */
    @GetMapping("/downloadFile")
    public byte[] downloadFile(@RequestParam("fileUrl") String fileUrl) {
        return fastdfsClient.download(fileUrl);
    }

    /**
     * 删除文件
     * 
     * @param ids 文件地址
     * @return String
     */
    @DeleteMapping("/delete")
    public String delete(@RequestBody String... ids) {
        if (ids != null) {
            for (String id : ids) {
                fastdfsClient.deleteFile(id);
            }
        }
        return ResultEnum.SUCCESS.getName();
    }

    /**
     * 获取文件
     * 
     * @param groupName 组名称
     * @param path      地址
     * @return FileInfo
     */
    @GetMapping("/getFile")
    public FileInfo getFile(@RequestParam("groupName") String groupName, @RequestParam("path") String path) {
        return fastdfsClient.getFile(groupName, path);
    }

}

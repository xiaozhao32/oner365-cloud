package com.oner365.dfs.client;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.oner365.common.constants.PublicConstants;
import com.oner365.dfs.entity.FastdfsFile;
import com.oner365.dfs.service.IFastdfsFileService;
import com.oner365.util.DataUtils;
import com.github.tobato.fastdfs.domain.conn.FdfsWebServer;
import com.github.tobato.fastdfs.domain.fdfs.FileInfo;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.exception.FdfsUnsupportStorePathException;
import com.github.tobato.fastdfs.service.FastFileStorageClient;

/**
 * 文件上传服务
 * @author zhaoyong
 */
@Component
public class FastdfsClient {

    private final Logger logger = LoggerFactory.getLogger(FastdfsClient.class);

    @Value("${fdfs.storage.path}")
    private String path;

    @Value("${fdfs.ip}")
    private String ip;

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @Autowired
    private FdfsWebServer fdfsWebServer;

    @Autowired
    private IFastdfsFileService fastdfsFileService;

    /**
     * 上传文件
     *
     * @param file 文件对象
     * @return 文件访问地址
     */
    public String uploadFile(MultipartFile file) {
        try (InputStream in = file.getInputStream()) {
            StorePath storePath = fastFileStorageClient.uploadFile(in, file.getSize(),
                    getExtName(file.getOriginalFilename(), file.getContentType()), null);
            String url = getResAccessUrl(storePath);
            saveFastdfsFile(url, file.getOriginalFilename(), file.getSize());
            return url;
        } catch (IOException e) {
            logger.error("upload MultipartFile IOException:", e);
        }
        return null;
    }

    /**
     * 上传文件
     *
     * @param file 文件对象
     * @return 文件访问地址
     */
    public String uploadFile(File file) {
        try (FileInputStream inputStream = new FileInputStream(file)) {
            StorePath storePath = fastFileStorageClient.uploadFile(inputStream, file.length(),
                    getExtName(file.getName(), null), null);
            String url = getResAccessUrl(storePath);
            saveFastdfsFile(url, file.getName(), file.length());
            return url;
        } catch (IOException e) {
            logger.error("upload File IOException:", e);
        }
        return null;
    }

    private void saveFastdfsFile(String url, String fileName, long fileSize) {
        // save
        FastdfsFile fastdfsFile = new FastdfsFile();
        fastdfsFile.setFastdfsUrl("http://" + ip);
        fastdfsFile.setId(StringUtils.replace(url, fastdfsFile.getFastdfsUrl() + PublicConstants.DELIMITER, ""));
        fastdfsFile.setCreateTime(new Date());
        fastdfsFile.setDirectory(false);
        fastdfsFile.setFilePath(url);
        fastdfsFile.setFileName(StringUtils.substringAfterLast(url, PublicConstants.DELIMITER));
        fastdfsFile.setDisplayName(fileName);
        fastdfsFile.setFileSuffix(DataUtils.getExtension(fileName));
        fastdfsFile.setSize(DataUtils.convertFileSize(fileSize));
        fastdfsFileService.save(fastdfsFile);
    }

    /**
     * 获取扩展名
     * @param fileName 文件名称
     * @param contentType contentType
     * @return String
     */
    private String getExtName(String fileName, String contentType) {
        String result = FilenameUtils.getExtension(fileName);
        if ("".equals(result)) {
            return StringUtils.substringAfterLast(contentType, PublicConstants.DELIMITER);
        }
        return result;
    }

    /**
     * 将一段字符串生成一个文件上传
     *
     * @param content       文件内容
     * @param fileExtension 扩展名
     * @return String
     */
    public String uploadFile(String content, String fileExtension) {
        byte[] buff = content.getBytes(Charset.forName(StandardCharsets.UTF_8.name()));
        try (ByteArrayInputStream stream = new ByteArrayInputStream(buff)) {
            StorePath storePath = fastFileStorageClient.uploadFile(stream, buff.length, fileExtension, null);
            return getResAccessUrl(storePath);
        } catch (IOException e) {
            logger.error("upload File IOException:", e);
        }
        return null;
    }

    /**
     *  封装完整URL地址
     * @param storePath 文件地址
     * @return String
     */
    private String getResAccessUrl(StorePath storePath) {
        return fdfsWebServer.getWebServerUrl() + PublicConstants.DELIMITER + storePath.getFullPath();
    }

    /**
     * 下载文件
     *
     * @param fileUrl 文件url
     * @return byte[]
     */
    public byte[] download(String fileUrl) {
        String group = fileUrl.substring(0, fileUrl.indexOf(PublicConstants.DELIMITER));
        String downloadPath = fileUrl.substring(fileUrl.indexOf(PublicConstants.DELIMITER) + 1);
        return fastFileStorageClient.downloadFile(group, downloadPath, new DownloadByteArray());
    }

    /**
     * 删除文件
     *
     * @param fileUrl 文件访问地址
     */
    public void deleteFile(String fileUrl) {
        if (DataUtils.isEmpty(fileUrl)) {
            return;
        }
        try {
            StorePath storePath = StorePath.parseFromUrl(fileUrl);
            fastFileStorageClient.deleteFile(storePath.getGroup(), storePath.getPath());
            fastdfsFileService.deleteById(fileUrl);
        } catch (FdfsUnsupportStorePathException e) {
            logger.error("delete File Fdfs not support StorePathException:", e);
        }
    }

    /**
     * 获取文件
     * @param groupName 组名称
     * @param path 地址
     * @return FileInfo
     */
    public FileInfo getFile(String groupName, String path) {
        return fastFileStorageClient.queryFileInfo(groupName, path);
    }

}

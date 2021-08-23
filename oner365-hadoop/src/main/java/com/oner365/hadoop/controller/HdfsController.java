package com.oner365.hadoop.controller;

import java.util.List;
import java.util.Map;

import org.apache.hadoop.fs.BlockLocation;
import org.apache.hadoop.fs.FileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.oner365.hadoop.config.ResponseData;
import com.oner365.hadoop.entity.User;
import com.oner365.hadoop.service.HdfsService;
import com.google.common.collect.Lists;

/**
 * Hadoop控制器
 * @author zhaoyong
 */
@RestController
@RequestMapping("/hadoop/hdfs")
public class HdfsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HdfsController.class);

    /**
     * 创建文件夹
     *
     * @param path 文件夹
     * @return ResponseData
     */
    @GetMapping("/mkdir")
    public ResponseData<Boolean> mkdir(@RequestParam("path") String path) {
        boolean result = false;
        try (FileSystem fs = HdfsService.getFileSystem()) {
            result = HdfsService.mkdir(fs, path);
        } catch (Exception e) {
            LOGGER.error("mkdir error:", e);
        }
        return new ResponseData<>(result);
    }

    /**
     * 读取HDFS目录信息
     *
     * @param path 文件
     * @return ResponseData
     */
    @GetMapping("/readPathInfo")
    public ResponseData<List<Map<String, Object>>> readPathInfo(@RequestParam("path") String path) {
        List<Map<String, Object>> list = Lists.newArrayList();
        try (FileSystem fs = HdfsService.getFileSystem()) {
            list = HdfsService.readPathInfo(fs, path);
        } catch (Exception e) {
            LOGGER.error("readPathInfo error:", e);
        }
        return new ResponseData<>(list);
    }

    /**
     * 获取HDFS文件在集群中的位置
     *
     * @param path 文件
     * @return ResponseData
     */
    @GetMapping("/getFileBlockLocations")
    public ResponseData<BlockLocation[]> getFileBlockLocations(@RequestParam("path") String path) {
        try (FileSystem fs = HdfsService.getFileSystem()) {
            BlockLocation[] result = HdfsService.getFileBlockLocations(fs, path);
            return new ResponseData<>(result);
        } catch (Exception e) {
            LOGGER.error("getFileBlockLocations error:", e);
        }
        return new ResponseData<>();
    }

    /**
     * 创建文件
     *
     * @param path 文件
     * @param file 文件
     * @return ResponseData
     */
    @PostMapping("/createFile")
    public ResponseData<String> createFile(@RequestParam("path") String path, @RequestParam("file") MultipartFile file) {
        try (FileSystem fs = HdfsService.getFileSystem()) {
            HdfsService.createFile(fs, path, file);
        } catch (Exception e) {
            LOGGER.error("createFile error:", e);
        }
        return new ResponseData<>();
    }

    /**
     * 读取HDFS文件内容
     *
     * @param path 文件
     * @return ResponseData
     */
    @GetMapping("/readFile")
    public ResponseData<String> readFile(@RequestParam("path") String path) {
        String result = null;
        try (FileSystem fs = HdfsService.getFileSystem()) {
            result = HdfsService.readFile(fs, path);
        } catch (Exception e) {
            LOGGER.error("readFile error:", e);
        }
        return new ResponseData<>(result);
    }

    /**
     * 读取HDFS文件转换成Byte类型
     *
     * @param path 文件
     * @return ResponseData
     */
    @GetMapping("/openFileToBytes")
    public ResponseData<byte[]> openFileToBytes(@RequestParam("path") String path) {
        byte[] result = null;
        try (FileSystem fs = HdfsService.getFileSystem()) {
            result = HdfsService.openFileToBytes(fs, path);
        } catch (Exception e) {
            LOGGER.error("openFileToBytes error:", e);
        }
        return new ResponseData<>(result);
    }

    /**
     * 读取HDFS文件装换成User对象
     *
     * @param path 文件
     * @return ResponseData
     */
    @GetMapping("/openFileToUser")
    public ResponseData<User> openFileToUser(@RequestParam("path") String path) {
        User result = null;
        try (FileSystem fs = HdfsService.getFileSystem()) {
            result = HdfsService.openFileToObject(fs, path, User.class);
        } catch (Exception e) {
            LOGGER.error("openFileToUser error:", e);
        }
        return new ResponseData<>(result);
    }

    /**
     * 读取文件列表
     *
     * @param path 文件
     * @return ResponseData
     */
    @GetMapping("/listFile")
    public ResponseData<List<Map<String, String>>> listFile(@RequestParam("path") String path) {
        List<Map<String, String>> result = Lists.newArrayList();
        try (FileSystem fs = HdfsService.getFileSystem()) {
            result = HdfsService.listFile(fs, path);
        } catch (Exception e) {
            LOGGER.error("listFile error:", e);
        }
        return new ResponseData<>(result);
    }

    /**
     * 重命名文件
     *
     * @param oldName 源文件
     * @param newName 重命名文件
     * @return ResponseData
     */
    @GetMapping("/renameFile")
    public ResponseData<Boolean> renameFile(@RequestParam("oldName") String oldName, @RequestParam("newName") String newName) {
        boolean result = false;
        try (FileSystem fs = HdfsService.getFileSystem()) {
            result = HdfsService.renameFile(fs, oldName, newName);
        } catch (Exception e) {
            LOGGER.error("renameFile error:", e);
        }
        return new ResponseData<>(result);
    }

    /**
     * 删除文件
     *
     * @param path 文件
     * @return ResponseData
     */
    @DeleteMapping("/deleteFile")
    public ResponseData<Boolean> deleteFile(@RequestParam("path") String path) {
        boolean result = false;
        try (FileSystem fs = HdfsService.getFileSystem()) {
            result = HdfsService.deleteFile(fs, path);
        } catch (Exception e) {
            LOGGER.error("deleteFile error:", e);
        }
        return new ResponseData<>(result);
    }

    /**
     * 上传文件
     *
     * @param path 文件
     * @param uploadPath 上传目录
     * @return ResponseData
     */
    @PostMapping("/uploadFile")
    public ResponseData<String> uploadFile(@RequestParam("path") String path, @RequestParam("uploadPath") String uploadPath) {
        try (FileSystem fs = HdfsService.getFileSystem()) {
            HdfsService.uploadFile(fs, path, uploadPath);
        } catch (Exception e) {
            LOGGER.error("uploadFile error:", e);
        }
        return new ResponseData<>();
    }

    /**
     * 下载文件
     *
     * @param path 文件
     * @param downloadPath 下载目录
     * @return ResponseData
     */
    @PostMapping("/downloadFile")
    public ResponseData<String> downloadFile(@RequestParam("path") String path,
            @RequestParam("downloadPath") String downloadPath) {
        try (FileSystem fs = HdfsService.getFileSystem()) {
            HdfsService.downloadFile(fs, path, downloadPath);
        } catch (Exception e) {
            LOGGER.error("downloadFile error:", e);
        }
        return new ResponseData<>();
    }

    /**
     * HDFS文件复制
     *
     * @param sourcePath 源文件
     * @param targetPath 目标文件
     * @return ResponseData
     */
    @GetMapping("/copyFile")
    public ResponseData<String> copyFile(@RequestParam("sourcePath") String sourcePath,
            @RequestParam("targetPath") String targetPath) {
        try (FileSystem fs = HdfsService.getFileSystem()) {
            HdfsService.copyFile(fs, sourcePath, targetPath);
        } catch (Exception e) {
            LOGGER.error("copyFile error:", e);
        }
        return new ResponseData<>();
    }

    /**
     * 查看文件是否已存在
     *
     * @param path 文件
     * @return ResponseData
     */
    @GetMapping("/existFile")
    public ResponseData<Boolean> existFile(@RequestParam("path") String path) {
        boolean result = false;
        try (FileSystem fs = HdfsService.getFileSystem()) {
            result = HdfsService.existFile(fs, path);
        } catch (Exception e) {
            LOGGER.error("existFile error:", e);
        }
        return new ResponseData<>(result);
    }
}

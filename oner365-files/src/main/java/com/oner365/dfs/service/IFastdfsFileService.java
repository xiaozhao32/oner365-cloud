package com.oner365.dfs.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.alibaba.fastjson.JSONObject;
import com.oner365.dfs.entity.FastdfsFile;

/**
 * 文件接口
 * @author zhaoyong
 */
public interface IFastdfsFileService {

    /**
     * 查询文件列表
     *
     * @param paramJson 参数
     * @return Page
     */
    Page<FastdfsFile> pageList(JSONObject paramJson);

    /**
     * 查询列表
     * 
     * @param paramJson 参数
     * @return List
     */
    List<FastdfsFile> findList(JSONObject paramJson);

    /**
     * 查询文件详情
     *
     * @param id 编号
     * @return FastdfsFile
     */
    FastdfsFile getById(String id);

    /**
     * 保存文件
     *
     * @param entity 文件对象
     * @return FastdfsFile
     */
    FastdfsFile save(FastdfsFile entity);

    /**
     * 删除文件
     *
     * @param id 编号
     * @return int
     */
    int deleteById(String id);

}

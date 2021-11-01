package com.oner365.files.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.files.entity.SysFileStorage;

/**
 * 文件接口
 * @author zhaoyong
 */
public interface IFileStorageService {

    /**
     * 查询文件列表
     *
     * @param data 查询参数
     * @return Page
     */
    Page<SysFileStorage> pageList(QueryCriteriaBean data);

    /**
     * 查询列表
     * 
     * @param data 查询参数
     * @return List
     */
    List<SysFileStorage> findList(QueryCriteriaBean data);

    /**
     * 查询文件详情
     *
     * @param id 编号
     * @return SysFileStorage
     */
    SysFileStorage getById(String id);

    /**
     * 保存文件
     *
     * @param entity 文件对象
     * @return SysFileStorage
     */
    SysFileStorage save(SysFileStorage entity);

    /**
     * 删除文件
     *
     * @param id 编号
     * @return int
     */
    int deleteById(String id);

}

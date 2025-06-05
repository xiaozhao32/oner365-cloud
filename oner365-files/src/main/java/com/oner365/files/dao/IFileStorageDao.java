package com.oner365.files.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.oner365.files.entity.SysFileStorage;

/**
 * 文件接口
 *
 * @author zhaoyong
 */
public interface IFileStorageDao
        extends JpaRepository<SysFileStorage, String>, JpaSpecificationExecutor<SysFileStorage> {

}

package com.oner365.neo4j.service;

import com.oner365.neo4j.entity.ParentNode;

/**
 * 节点接口
 *
 * @author zhaoyong
 *
 */
public interface ParentService {

    /**
     * 保存
     * @param entity 节点对象
     * @return ParentNode
     */
    ParentNode save(ParentNode entity);

    /**
     * 删除
     * @param id 节点id
     * @return boolean
     */
    boolean delete(Long id);

}

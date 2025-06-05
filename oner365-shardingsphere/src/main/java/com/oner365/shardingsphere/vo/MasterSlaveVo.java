package com.oner365.shardingsphere.vo;

import java.io.Serializable;

/**
 * 主从测试
 *
 * @author zhaoyong
 */
public class MasterSlaveVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 订单id
     */
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

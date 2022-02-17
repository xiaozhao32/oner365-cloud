package com.oner365.swagger.dto;

import java.io.Serializable;

/**
 * 缓存信息
 *
 * @author zhaoyong
 */
public class CacheJedisInfoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /** name */
    private String name;

    /** index */
    private Integer index;

    /** db size */
    private Long size;

    public CacheJedisInfoDto() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }
}

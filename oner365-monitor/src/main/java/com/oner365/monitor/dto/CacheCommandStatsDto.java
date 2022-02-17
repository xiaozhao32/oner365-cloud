package com.oner365.monitor.dto;

import java.io.Serializable;

/**
 * 缓存信息
 *
 * @author zhaoyong
 */
public class CacheCommandStatsDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 名称 */
    private String name;

    /** 值 */
    private String value;

    public CacheCommandStatsDto() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

package com.oner365.monitor.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Properties;

/**
 * 缓存信息
 *
 * @author zhaoyong
 */
public class CacheInfoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 缓存信息
     */
    private Properties info;

    /**
     * 长度
     */
    private Long dbSize;

    /**
     * command stats
     */
    private List<CacheCommandStatsDto> commandStats;

    /**
     * 构造方法
     */
    public CacheInfoDto() {
        super();
    }

    public Properties getInfo() {
        return info;
    }

    public void setInfo(Properties info) {
        this.info = info;
    }

    public Long getDbSize() {
        return dbSize;
    }

    public void setDbSize(Long dbSize) {
        this.dbSize = dbSize;
    }

    public List<CacheCommandStatsDto> getCommandStats() {
        return commandStats;
    }

    public void setCommandStats(List<CacheCommandStatsDto> commandStats) {
        this.commandStats = commandStats;
    }

}

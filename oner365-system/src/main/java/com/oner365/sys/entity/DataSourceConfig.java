package com.oner365.sys.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/** 
 * 数据源配置
 * @author zhaoyong
 */
@Entity
@Table(name = "nt_data_source_config")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class DataSourceConfig implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "generator")
    @GenericGenerator(name = "generator", strategy = "uuid")
    private String id;
    
    /**
     * 连接名
     */
    @Column(name = "connect_name")
    private String connectName;
    
    /**
     * 数据库类型：mysql、oracle
     */
    @Column(name = "db_type")
    private String dbType;
    
    /**
     * native
     */
    @Column(name = "ds_type")
    private String dsType;
    
    /**
     * 数据库连接地址
     */
    @Column(name = "ip_address")
    private String ip;
    
    /**
     * 驱动名称
     */
    @Column(name = "driver_name")
    private String driverName;
    
    /**
     * 地址
     */
    @Column(name = "url")
    private String url;
    
    /**
     * 数据库名
     */
    @Column(name = "db_name")
    private String dbName;
    
    /**
     * 端口
     */
    @Column(name = "port")
    private int port;
    
    /**
     * 用户名
     */
    @Column(name = "user_name")
    private String userName;
    
    /**
     * 密码
     */
    @Column(name = "password")
    private String password;
    
    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private LocalDateTime updateTime;
    
    /**
     * Constructor
     */
    public DataSourceConfig() {
        super();
    }
    
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }
    /**
     * @return the connectName
     */
    public String getConnectName() {
        return connectName;
    }
    /**
     * @param connectName the connectName to set
     */
    public void setConnectName(String connectName) {
        this.connectName = connectName;
    }
    /**
     * @return the dbType
     */
    public String getDbType() {
        return dbType;
    }
    /**
     * @param dbType the dbType to set
     */
    public void setDbType(String dbType) {
        this.dbType = dbType;
    }
    /**
     * @return the dsType
     */
    public String getDsType() {
        return dsType;
    }
    /**
     * @param dsType the dsType to set
     */
    public void setDsType(String dsType) {
        this.dsType = dsType;
    }
    /**
     * @return the ip
     */
    public String getIp() {
        return ip;
    }
    /**
     * @param ip the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }
    /**
     * @return the dbName
     */
    public String getDbName() {
        return dbName;
    }
    /**
     * @param dbName the dbName to set
     */
    public void setDbName(String dbName) {
        this.dbName = dbName;
    }
    /**
     * @return the port
     */
    public int getPort() {
        return port;
    }
    /**
     * @param port the port to set
     */
    public void setPort(int port) {
        this.port = port;
    }
    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }
    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }
    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * @return the createTime
     */
    public LocalDateTime getCreateTime() {
        return createTime;
    }
    /**
     * @param createTime the createTime to set
     */
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
    /**
     * @return the updateTime
     */
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }
    /**
     * @param updateTime the updateTime to set
     */
    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
    /**
     * @return the driverName
     */
    public String getDriverName() {
        return driverName;
    }
    /**
     * @param driverName the driverName to set
     */
    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }
    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }
    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }


}

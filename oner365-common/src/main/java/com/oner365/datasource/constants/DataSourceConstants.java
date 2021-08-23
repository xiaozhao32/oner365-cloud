package com.oner365.datasource.constants;

/**
 * 动态数据源 参数名称
 * @author zhaoyong
 *
 */
public final class DataSourceConstants {
    
    public enum DataSourceType {
        /** 默认类型 */
        PRIMARY
    }
    
    public static final String ID = "id";
    
    /** 数据源类型 */
    public static final String DS_TYPE_DB = "db";
    public static final String DS_TYPE_CACHE = "cache";
    
    /** 动态切换数据源参数类型 */
    public static final String DYNAMIC_DATA_TYPE = "dynamicDataType_";
    
    public static final String DS_TYPE = "dsType";
    
    /** 数据库类型 */
    public static final String DB_TYPE_MYSQL = "mysql";
    public static final String DB_TYPE_ORACLE = "oracle";
    
    /** 驱动类型 */
    public static final String DRIVER_NAME_MYSQL = "com.mysql.cj.jdbc.Driver";
    public static final String DRIVER_NAME_ORACLE = "oracle.jdbc.driver.OracleDriver";
    public static final String DRIVER_NAME_POSTGRESQL = "org.postgresql.Driver";
    public static final String DRIVER_NAME_DB2 = "com.ibm.db2.jcc.DB2Driver";
    public static final String DRIVER_NAME_SQLSERVER = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
    
    /**
     * Generate constructor
     */
    private DataSourceConstants() {
        
    }
    
}

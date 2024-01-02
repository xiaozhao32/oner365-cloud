package com.oner365.data.datasource.constants;

/**
 * 动态数据源 参数名称
 * 
 * @author zhaoyong
 *
 */
public final class DataSourceConstants {

  public static final String PRIMARY = "primary";

  public static final String ID = "id";

  /** 数据源类型 */
  public static final String DS_TYPE_DB = "db";
  public static final String DS_TYPE_CACHE = "cache";

  public static final String DS_TYPE = "dsType";

  /** 数据库类型 */
  public static final String DB_TYPE_MYSQL = "mysql";
  public static final String DB_TYPE_ORACLE = "oracle";
  public static final String DB_TYPE_POSTGRES = "postgres";
  public static final String DB_TYPE_DB2 = "db2";
  public static final String DB_TYPE_DM = "dm";
  
  /** 数据源cache */
  public static final String CACHE_MAP = "sourceMap";

  /** 驱动类型 */
  public static final String DRIVER_NAME_MYSQL = "com.mysql.cj.jdbc.Driver";
  public static final String DRIVER_NAME_ORACLE = "oracle.jdbc.driver.OracleDriver";
  public static final String DRIVER_NAME_POSTGRESQL = "org.postgresql.Driver";
  public static final String DRIVER_NAME_DB2 = "com.ibm.db2.jcc.DB2Driver";
  public static final String DRIVER_NAME_SQLSERVER = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
  public static final String DRIVER_NAME_DM = "dm.jdbc.driver.DmDriver";

  /**
   * Generate constructor
   */
  private DataSourceConstants() {

  }

}

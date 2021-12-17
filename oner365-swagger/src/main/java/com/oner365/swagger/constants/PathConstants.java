package com.oner365.swagger.constants;

/**
 * 常量 - 地址
 * 
 * @author zhaoyong
 * 
 */
public class PathConstants {

  /** system */
  public static final String FEIGN_CLIENT_SYSTEM = "oner365-system";
  
  /** system context */
  public static final String CONTEXT_SYSTEM_AUTH_ID = "ISystemAuthClient";
  public static final String CONTEXT_SYSTEM_DATASOURCE_CONFIG_ID = "ISystemDataSourceConfigClient";
  
  /** 用户认证 */
  public static final String REQUEST_SYSTEM_AUTH_LOGIN = "/auth/login";
  public static final String REQUEST_SYSTEM_AUTH_CAPTCHA_IMAGE = "/auth/captchaImage";
  public static final String REQUEST_SYSTEM_AUTH_MENU = "/auth/menu/{menuType}";
  public static final String REQUEST_SYSTEM_AUTH_MENU_OPERATION = "/auth/menu/operation/{menuId}";
  public static final String REQUEST_SYSTEM_AUTH_LOGOUT = "/auth/logout";
  
  /** 数据源 */
  public static final String REQUEST_SYSTEM_DATASOURCE_LIST = "/datasource/list";
  public static final String REQUEST_SYSTEM_DATASOURCE_GET_ID = "/datasource/get/{id}";
  public static final String REQUEST_SYSTEM_DATASOURCE_GET_CONNECT_NAME = "/datasource/getConnectName";
  public static final String REQUEST_SYSTEM_DATASOURCE_SAVE = "/datasource/save";
  public static final String REQUEST_SYSTEM_DATASOURCE_DELETE = "/datasource/delete";
  
  /** files */
  public static final String FEIGN_CLIENT_FILES = "oner365-files";
  /** files context */
  public static final String CONTEXT_FILES_STORAGE_ID = "IFilesStorageClient";
  
  /** 文件存储信息 */
  public static final String REQUEST_FILES_STORAGE_LIST = "/fdfs/list";
  public static final String REQUEST_FILES_STORAGE_UPLOAD = "/fdfs/uploadMultipartFile";
  public static final String REQUEST_FILES_STORAGE_DOWNLOAD = "/fdfs/downloadFile";
  
  /** monitor */
  public static final String FEIGN_CLIENT_MONITOR = "oner365-monitor";
  
  /** monitor context */
  public static final String CONTEXT_MONITOR_CACHE_ID = "IMonitorCacheClient";
  
  /** 缓存 */
  public static final String REQUEST_MONITOR_CACHE_INDEX = "/cache/index";
  public static final String REQUEST_MONITOR_CACHE_LIST = "/cache/cacheList";
  public static final String REQUEST_MONITOR_CACHE_CLEAN = "/cache/clean";

  private PathConstants() {
  }

}

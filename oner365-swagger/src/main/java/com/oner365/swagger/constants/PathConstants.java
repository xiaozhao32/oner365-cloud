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
  
  /** system path */
  public static final String REQUEST_SYSTEM_AUTH_LOGIN = "/auth/login";
  public static final String REQUEST_SYSTEM_AUTH_CAPTCHA_IMAGE = "/auth/captchaImage";
  public static final String REQUEST_SYSTEM_AUTH_MENU = "/auth/menu/{menuType}";
  public static final String REQUEST_SYSTEM_AUTH_MENU_OPERATION = "/auth/menu/operation/{menuId}";
  public static final String REQUEST_SYSTEM_AUTH_LOGOUT = "/auth/logout";
  
  /** files */
  public static final String FEIGN_CLIENT_FILES = "oner365-files";
  /** files context */
  public static final String CONTEXT_FILES_STORAGE_ID = "IFilesStorageClient";
  
  /** monitor */
  public static final String FEIGN_CLIENT_MONITOR = "oner365-monitor";
  
  /** monitor context */
  public static final String CONTEXT_MONITOR_CACHE_ID = "IMonitorCacheClient";

  private PathConstants() {
  }

}

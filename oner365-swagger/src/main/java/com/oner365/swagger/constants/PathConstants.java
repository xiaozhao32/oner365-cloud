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
  
  /** elasticsearch */
  public static final String FEIGN_CLIENT_ELASTICSEARCH = "oner365-elasticsearch";
  /** elasticsearch context */
  public static final String CONTEXT_ELASTICSEARCH_INFO_ID = "IElasticsearchInfoClient";
  public static final String CONTEXT_ELASTICSEARCH_SAMPLE_GENE_ID = "IElasticsearchSampleGeneClient";
  
  /** Elasticsearch信息 */
  public static final String REQUEST_ELASTICSEARCH_INFO_INDEX = "/info/index";
  /** 基因信息 */
  public static final String REQUEST_ELASTICSEARCH_SAMPLE_GENE_LIST = "/sampleGene/list";
  public static final String REQUEST_ELASTICSEARCH_SAMPLE_GENE_GET_ID = "/sampleGene/get/{id}";
  public static final String REQUEST_ELASTICSEARCH_SAMPLE_GENE_SAVE = "/sampleGene/save";
  public static final String REQUEST_ELASTICSEARCH_SAMPLE_GENE_DELETE = "/sampleGene/delete";
  
  /** monitor */
  public static final String FEIGN_CLIENT_MONITOR = "oner365-monitor";
  /** monitor context */
  public static final String CONTEXT_MONITOR_CACHE_ID = "IMonitorCacheClient";
  public static final String CONTEXT_MONITOR_RABBITMQ_ID = "IMonitorRabbitmqClient";
  public static final String CONTEXT_MONITOR_SERVER_ID = "IMonitorServerClient";
  
  /** 缓存 */
  public static final String REQUEST_MONITOR_CACHE_INDEX = "/cache/index";
  public static final String REQUEST_MONITOR_CACHE_LIST = "/cache/cacheList";
  public static final String REQUEST_MONITOR_CACHE_CLEAN = "/cache/clean";
  /** 队列 */
  public static final String REQUEST_MONITOR_RABBITMQ_INDEX = "/rabbitmq/index";
  public static final String REQUEST_MONITOR_RABBITMQ_LIST = "/rabbitmq/list/{type}";
  public static final String REQUEST_MONITOR_RABBITMQ_DELETE = "/rabbitmq/delete/{type}/{name}";
  /** 服务器 */
  public static final String REQUEST_MONITOR_SERVER_INDEX = "/server/index";

  private PathConstants() {
  }

}

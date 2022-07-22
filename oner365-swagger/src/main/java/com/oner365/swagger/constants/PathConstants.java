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
  public static final String CONTEXT_SYSTEM_DICT_ID = "ISystemDictClient";
  public static final String CONTEXT_SYSTEM_JOB_ID = "ISystemJobClient";
  public static final String CONTEXT_SYSTEM_LOG_ID = "ISystemLogClient";
  public static final String CONTEXT_SYSTEM_MENU_ID = "ISystemMenuClient";
  public static final String CONTEXT_SYSTEM_MENU_OPERATION_ID = "ISystemMenuOperationClient";
  public static final String CONTEXT_SYSTEM_MENU_TYPE_ID = "ISystemMenuTypeClient";
  public static final String CONTEXT_SYSTEM_MESSAGE_ID = "ISystemMessageClient";
  public static final String CONTEXT_SYSTEM_ORG_ID = "ISystemOrgClient";
  public static final String CONTEXT_SYSTEM_ROLE_ID = "ISystemRoleClient";
  public static final String CONTEXT_SYSTEM_USER_ID = "ISystemUserClient";
  
  /** 用户认证 */
  public static final String REQUEST_SYSTEM_AUTH_LOGIN = "/auth/login";
  public static final String REQUEST_SYSTEM_AUTH_CAPTCHA_IMAGE = "/auth/captcha";
  public static final String REQUEST_SYSTEM_AUTH_MENU = "/auth/menu";
  public static final String REQUEST_SYSTEM_AUTH_MENU_OPERATION = "/auth/menu/operation/{menuId}";
  public static final String REQUEST_SYSTEM_AUTH_LOGOUT = "/auth/logout";
  
  /** 数据源 */
  public static final String REQUEST_SYSTEM_DATASOURCE_LIST = "/datasource/list";
  public static final String REQUEST_SYSTEM_DATASOURCE_GET_ID = "/datasource/get/{id}";
  public static final String REQUEST_SYSTEM_DATASOURCE_GET_CONNECT_NAME = "/datasource/name";
  public static final String REQUEST_SYSTEM_DATASOURCE_SAVE = "/datasource/save";
  public static final String REQUEST_SYSTEM_DATASOURCE_DELETE = "/datasource/delete";
  
  /** 字典信息 */
  public static final String REQUEST_SYSTEM_DICT_TYPE_LIST = "/dict/type/list";
  public static final String REQUEST_SYSTEM_DICT_TYPE_CODES_LIST = "/dict/type/codes/list";
  public static final String REQUEST_SYSTEM_DICT_TYPE_GET_ID = "/dict/type/get/{id}";
  public static final String REQUEST_SYSTEM_DICT_TYPE_CHECK = "/dict/type/check";
  public static final String REQUEST_SYSTEM_DICT_TYPE_STATUS = "/dict/type/status/{id}";
  public static final String REQUEST_SYSTEM_DICT_TYPE_SAVE = "/dict/type/save";
  public static final String REQUEST_SYSTEM_DICT_TYPE_DELETE = "/dict/type/delete";
  public static final String REQUEST_SYSTEM_DICT_TYPE_EXPORT = "/dict/type/export";
  
  public static final String REQUEST_SYSTEM_DICT_ITEM_LIST = "/dict/item/list";
  public static final String REQUEST_SYSTEM_DICT_ITEM_GET_ID = "/dict/item/get/{id}";
  public static final String REQUEST_SYSTEM_DICT_ITEM_CHECK = "/dict/item/check";
  public static final String REQUEST_SYSTEM_DICT_ITEM_STATUS = "/dict/item/status/{id}";
  public static final String REQUEST_SYSTEM_DICT_ITEM_SAVE = "/dict/item/save";
  public static final String REQUEST_SYSTEM_DICT_ITEM_DELETE = "/dict/item/delete";
  public static final String REQUEST_SYSTEM_DICT_ITEM_EXPORT = "/dict/item/export";
  public static final String REQUEST_SYSTEM_DICT_ITEM_TYPE = "/dict/item/type/{typeId}";
  public static final String REQUEST_SYSTEM_DICT_ITEM_TYPE_IDS = "/dict/item/type/ids";
  
  /** 职位信息 */
  public static final String REQUEST_SYSTEM_JOB_LIST = "/job/list";
  public static final String REQUEST_SYSTEM_JOB_GET_ID = "/job/get/{id}";
  public static final String REQUEST_SYSTEM_JOB_STATUS = "/job/status/{id}";
  public static final String REQUEST_SYSTEM_JOB_SAVE = "/job/save";
  public static final String REQUEST_SYSTEM_JOB_DELETE = "/job/delete";
  public static final String REQUEST_SYSTEM_JOB_EXPORT = "/job/export";
  
  /** 日志信息 */
  public static final String REQUEST_SYSTEM_LOG_LIST = "/log/list";
  public static final String REQUEST_SYSTEM_LOG_GET_ID = "/log/get/{id}";
  public static final String REQUEST_SYSTEM_LOG_SAVE = "/log/save";
  public static final String REQUEST_SYSTEM_LOG_DELETE = "/log/delete";
  public static final String REQUEST_SYSTEM_LOG_DAYS_DELETE = "/log/days/delete";
  public static final String REQUEST_SYSTEM_LOG_EXPORT = "/log/export";
  
  /** 菜单 */
  public static final String REQUEST_SYSTEM_MENU_LIST = "/menus/list";
  public static final String REQUEST_SYSTEM_MENU_GET_ID = "/menus/get/{id}";
  public static final String REQUEST_SYSTEM_MENU_STATUS = "/menus/status/{id}";
  public static final String REQUEST_SYSTEM_MENU_TREE = "/menus/tree";
  public static final String REQUEST_SYSTEM_MENU_ROLE = "/menus/role/{roleId}";
  public static final String REQUEST_SYSTEM_MENU_SAVE = "/menus/save";
  public static final String REQUEST_SYSTEM_MENU_DELETE = "/menus/delete";
  
  /** 菜单操作 */
  public static final String REQUEST_SYSTEM_MENU_OPERATION_LIST = "/menu/operation/list";
  public static final String REQUEST_SYSTEM_MENU_OPERATION_GET_ID = "/menu/operation/get/{id}";
  public static final String REQUEST_SYSTEM_MENU_OPERATION_STATUS = "/menus/operation/status/{id}";
  public static final String REQUEST_SYSTEM_MENU_OPERATION_CHECK = "/menu/operation/check";
  public static final String REQUEST_SYSTEM_MENU_OPERATION_SAVE = "/menu/operation/save";
  public static final String REQUEST_SYSTEM_MENU_OPERATION_DELETE = "/menu/operation/delete";
  public static final String REQUEST_SYSTEM_MENU_OPERATION_EXPORT = "/menu/operation/export";
  
  /** 菜单类型 */
  public static final String REQUEST_SYSTEM_MENU_TYPE_LIST = "/menu/type/list";
  public static final String REQUEST_SYSTEM_MENU_TYPE_ALL = "/menu/type/all";
  public static final String REQUEST_SYSTEM_MENU_TYPE_GET_ID = "/menu/type/get/{id}";
  public static final String REQUEST_SYSTEM_MENU_TYPE_STATUS = "/menus/type/status/{id}";
  public static final String REQUEST_SYSTEM_MENU_TYPE_CHECK = "/menu/type/check";
  public static final String REQUEST_SYSTEM_MENU_TYPE_SAVE = "/menu/type/save";
  public static final String REQUEST_SYSTEM_MENU_TYPE_DELETE = "/menu/type/delete";
  
  /** 消息通知 */
  public static final String REQUEST_SYSTEM_MESSAGE_REFRESH = "/message/refresh";
  public static final String REQUEST_SYSTEM_MESSAGE_LIST = "/message/list";
  public static final String REQUEST_SYSTEM_MESSAGE_GET_ID = "/message/get/{id}";
  public static final String REQUEST_SYSTEM_MESSAGE_STATUS = "/message/status/{id}";
  public static final String REQUEST_SYSTEM_MESSAGE_SAVE = "/message/save";
  public static final String REQUEST_SYSTEM_MESSAGE_DELETE = "/message/delete";
  public static final String REQUEST_SYSTEM_MESSAGE_EXPORT = "/message/export";
  
  /** 机构管理 */
  public static final String REQUEST_SYSTEM_ORG_LIST = "/org/list";
  public static final String REQUEST_SYSTEM_ORG_GET_ID = "/org/get/{id}";
  public static final String REQUEST_SYSTEM_ORG_CONNECTION = "/org/connection/{ds}";
  public static final String REQUEST_SYSTEM_ORG_CONNECTION_CHECK = "/org/connection/check/{id}";
  public static final String REQUEST_SYSTEM_ORG_PARENT = "/org/parent";
  public static final String REQUEST_SYSTEM_ORG_CHECK = "/org/check";
  public static final String REQUEST_SYSTEM_ORG_TREE = "/org/tree";
  public static final String REQUEST_SYSTEM_ORG_USER = "/org/user/{userId}";
  public static final String REQUEST_SYSTEM_ORG_STATUS = "/status/{id}";
  public static final String REQUEST_SYSTEM_ORG_SAVE = "/org/save";
  public static final String REQUEST_SYSTEM_ORG_DELETE = "/org/delete";
  
  /** 角色管理 */
  public static final String REQUEST_SYSTEM_ROLE_LIST = "/role/list";
  public static final String REQUEST_SYSTEM_ROLE_GET_ID = "/role/get/{id}";
  public static final String REQUEST_SYSTEM_ROLE_STATUS = "/role/status/{id}";
  public static final String REQUEST_SYSTEM_ROLE_CHECK = "/role/check";
  public static final String REQUEST_SYSTEM_ROLE_SAVE = "/role/save";
  public static final String REQUEST_SYSTEM_ROLE_DELETE = "/role/delete";
  public static final String REQUEST_SYSTEM_ROLE_EXPORT = "/role/export";
  
  /** 用户管理 */
  public static final String REQUEST_SYSTEM_USER_LIST = "/user/list";
  public static final String REQUEST_SYSTEM_USER_GET_ID = "/user/get/{id}";
  public static final String REQUEST_SYSTEM_USER_PROFILE = "/user/profile";
  public static final String REQUEST_SYSTEM_USER_AVATAR = "/user/avatar";
  public static final String REQUEST_SYSTEM_USER_PROFILE_UPDATE = "/user/update/profile";
  public static final String REQUEST_SYSTEM_USER_CHECK = "/user/check";
  public static final String REQUEST_SYSTEM_USER_RESET = "/user/reset";
  public static final String REQUEST_SYSTEM_USER_PASSWORD_UPDATE = "/user/update/password";
  public static final String REQUEST_SYSTEM_USER_STATUS = "/user/status/{id}";
  public static final String REQUEST_SYSTEM_USER_SAVE = "/user/save";
  public static final String REQUEST_SYSTEM_USER_DELETE = "/user/delete";
  public static final String REQUEST_SYSTEM_USER_EXPORT = "/user/export";
  
  /** gateway */
  public static final String FEIGN_CLIENT_GATEWAY = "oner365-gateway";
  /** gateway context */
  public static final String CONTEXT_GATEWAY_ROUTE_ID = "IGatewayRouteClient";
  
  /** 网关路由管理 */
  public static final String REQUEST_GATEWAY_ROUTE_LIST = "/route/list";
  public static final String REQUEST_GATEWAY_ROUTE_GET_ID = "/route/get/{id}";
  public static final String REQUEST_GATEWAY_ROUTE_ADD = "/route/add";
  public static final String REQUEST_GATEWAY_ROUTE_REFRESH = "/route/refresh";
  public static final String REQUEST_GATEWAY_ROUTE_UPDATE = "/route/update";
  public static final String REQUEST_GATEWAY_ROUTE_STATUS = "/route/status/{id}/{status}";
  public static final String REQUEST_GATEWAY_ROUTE_DELETE = "/route/delete";
  
  /** files */
  public static final String FEIGN_CLIENT_FILES = "oner365-files";
  /** files context */
  public static final String CONTEXT_FILES_STORAGE_ID = "IFilesStorageClient";
  
  /** 文件存储信息 */
  public static final String REQUEST_FILES_STORAGE_LIST = "/storage/list";
  public static final String REQUEST_FILES_STORAGE_UPLOAD = "/storage/upload";
  public static final String REQUEST_FILES_STORAGE_DOWNLOAD = "/storage/download";
  public static final String REQUEST_FILES_STORAGE_DOWNLOAD_BYTE = "/storage/byte/download";
  public static final String REQUEST_FILES_STORAGE_DELETE = "/storage/delete";
  public static final String REQUEST_FILES_STORAGE_NAME = "/storage/name";
  
  /** elasticsearch */
  public static final String FEIGN_CLIENT_ELASTICSEARCH = "oner365-elasticsearch";
  /** elasticsearch context */
  public static final String CONTEXT_ELASTICSEARCH_INFO_ID = "IElasticsearchInfoClient";
  public static final String CONTEXT_ELASTICSEARCH_SAMPLE_GENE_ID = "IElasticsearchSampleGeneClient";
  public static final String CONTEXT_ELASTICSEARCH_SAMPLE_LOCATION_ID = "IElasticsearchSampleLocationClient";
  public static final String CONTEXT_ELASTICSEARCH_APPLICATION_LOG_ID = "IElasticsearchApplicationLogClient";
  
  /** Elasticsearch信息 */
  public static final String REQUEST_ELASTICSEARCH_INFO_INDEX = "/info/index";
  /** 基因信息 */
  public static final String REQUEST_ELASTICSEARCH_SAMPLE_GENE_LIST = "/sample/gene/list";
  public static final String REQUEST_ELASTICSEARCH_SAMPLE_GENE_GET_ID = "/sample/gene/get/{id}";
  public static final String REQUEST_ELASTICSEARCH_SAMPLE_GENE_SAVE = "/sample/gene/save";
  public static final String REQUEST_ELASTICSEARCH_SAMPLE_GENE_DELETE = "/sample/gene/delete";
  /** 坐标信息 */
  public static final String REQUEST_ELASTICSEARCH_SAMPLE_LOCATION_LIST = "/sample/location/list";
  public static final String REQUEST_ELASTICSEARCH_SAMPLE_LOCATION_GET_ID = "/sample/location/get/{id}";
  public static final String REQUEST_ELASTICSEARCH_SAMPLE_LOCATION_SAVE = "/sample/location/save";
  public static final String REQUEST_ELASTICSEARCH_SAMPLE_LOCATION_DELETE = "/sample/location/delete";
  /** 应用日志 */
  public static final String REQUEST_ELASTICSEARCH_APPLICATION_LOG_LIST = "/application/log/list";
  public static final String REQUEST_ELASTICSEARCH_APPLICATION_LOG_GET_ID = "/application/log/get/{id}";
  public static final String REQUEST_ELASTICSEARCH_APPLICATION_LOG_DELETE = "/application/log/delete";
  
  /** monitor */
  public static final String FEIGN_CLIENT_MONITOR = "oner365-monitor";
  /** monitor context */
  public static final String CONTEXT_MONITOR_CACHE_ID = "IMonitorCacheClient";
  public static final String CONTEXT_MONITOR_RABBITMQ_ID = "IMonitorRabbitmqClient";
  public static final String CONTEXT_MONITOR_SERVER_ID = "IMonitorServerClient";
  
  /** 缓存 */
  public static final String REQUEST_MONITOR_CACHE_INDEX = "/cache/index";
  public static final String REQUEST_MONITOR_CACHE_LIST = "/cache/list";
  public static final String REQUEST_MONITOR_CACHE_CLEAN = "/cache/clean";
  /** 队列 */
  public static final String REQUEST_MONITOR_RABBITMQ_INDEX = "/rabbitmq/index";
  public static final String REQUEST_MONITOR_RABBITMQ_LIST = "/rabbitmq/list/{type}";
  public static final String REQUEST_MONITOR_RABBITMQ_DELETE = "/rabbitmq/delete/{type}/{name}";
  /** 服务器 */
  public static final String REQUEST_MONITOR_SERVER_INDEX = "/server/index";
  
  /** generator */
  public static final String FEIGN_CLIENT_GENERATOR = "oner365-generator";
  /** generator context */
  public static final String CONTEXT_GENERATOR_TOOLS_ID = "IGeneratorToolsClient";
  /** generator tools */
  public static final String REQUEST_GENERATOR_TOOLS_LIST = "/gen/list";
  public static final String REQUEST_GENERATOR_TOOLS_INFO = "/gen/{tableId}";
  public static final String REQUEST_GENERATOR_TOOLS_DB_LIST = "/gen/db/list";
  public static final String REQUEST_GENERATOR_TOOLS_COLUMN = "/gen/column/{tableId}";
  public static final String REQUEST_GENERATOR_TOOLS_IMPORT = "/gen/import";
  public static final String REQUEST_GENERATOR_TOOLS_SAVE = "/gen";
  
  /** hadoop */
  public static final String FEIGN_CLIENT_HADOOP = "oner365-hadoop";
  /** hadoop context */
  public static final String CONTEXT_HADOOP_HDFS_ID = "IHadoopHdfsClient";
  /** hadoop hdfs */
  public static final String REQUEST_HADOOP_HDFS_MKDIR = "/hdfs/mkdir";
  public static final String REQUEST_HADOOP_HDFS_INFO = "/hdfs/info";
  public static final String REQUEST_HADOOP_HDFS_LOCATIONS = "/hdfs/locations";
  public static final String REQUEST_HADOOP_HDFS_CREATE = "/hdfs/create";
  public static final String REQUEST_HADOOP_HDFS_READ = "/hdfs/read";
  public static final String REQUEST_HADOOP_HDFS_BYTES = "/hdfs/bytes";
  public static final String REQUEST_HADOOP_HDFS_USER = "/hdfs/user";
  public static final String REQUEST_HADOOP_HDFS_LIST = "/hdfs/list";
  public static final String REQUEST_HADOOP_HDFS_RENAME = "/hdfs/rename";
  public static final String REQUEST_HADOOP_HDFS_DELETE = "/hdfs/delete";
  public static final String REQUEST_HADOOP_HDFS_UPLOAD = "/hdfs/upload";
  public static final String REQUEST_HADOOP_HDFS_DOWNLOAD = "/hdfs/download";
  public static final String REQUEST_HADOOP_HDFS_COPY = "/hdfs/copy";
  public static final String REQUEST_HADOOP_HDFS_EXIST = "/hdfs/exist";
  
  /** kafka */
  public static final String FEIGN_CLIENT_KAFKA = "oner365-kafka";
  /** kafka context */
  public static final String CONTEXT_KAFKA_CONSUMER_ID = "IKafkaConsumerClient";
  /** kafka消息 */
  public static final String REQUEST_KAFKA_CONSUMER_MESSAGE_SEND = "/message/send";
  
  /** rocketmq */
  public static final String FEIGN_CLIENT_ROCKETMQ = "oner365-rocketmq";
  /** rocketmq context */
  public static final String CONTEXT_ROCKETMQ_CONSUMER_ID = "IRocketmqConsumerClient";
  /** rocketmq消息 */
  public static final String REQUEST_ROCKETMQ_CONSUMER_MESSAGE_SEND = "/message/send";
  
  /** pulsar */
  public static final String FEIGN_CLIENT_PULSAR = "oner365-pulsar";
  /** pulsar context */
  public static final String CONTEXT_PULSAR_CONSUMER_ID = "IPulsarConsumerClient";
  /** pulsar消息 */
  public static final String REQUEST_PULSAR_CONSUMER_MESSAGE_SEND = "/message/send";
  
  /** dubbo */
  public static final String FEIGN_CLIENT_DUBBO_CONSUMER = "oner365-dubbo-consumer";
  /** dubbo context */
  public static final String CONTEXT_DUBBO_CONSUMER_ID = "IDubboConsumerClient";
  /** dubbo消息 */
  public static final String REQUEST_DUBBO_CONSUMER_MESSAGE_SEND = "/message/send";

  private PathConstants() {
  }

}

package com.oner365.data.commons.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * 公共常量
 *
 * @author zhaoyong
 */
public class PublicConstants {

  /**
   * 公共名称
   */
  public static final String NAME = "oner365";

  /**
   * 创建线程数
   */
  public static final int THREAD_NUMBER = 10;

  /**
   * 过期时间
   */
  public static final int EXPIRE_TIME = 60 * 12;
  
  /** 字节长度 */
  public static final int BYTE_SIZE = 1024;

  /**
   * 分页长度
   */
  public static final int PAGE_SIZE = 15;

  /** 查询参数 count */
  public static final String PARAM_COUNT = "count";
  /** 查询参数 list */
  public static final String PARAM_LIST = "list";
  /** 查询状态 status */
  public static final String PARAM_STATUS = "status";
  /** 文件类型 fileStorage */
  public static final String PARAM_FILE_STORAGE = "fileStorage";
  /** 分页对象 */
  public static final String PARAM_PAGE = "pageInfo";

  /** 返回编码 */
  public static final String CODE = "code";
  public static final String STATUS = "status";
  public static final String PATH = "path";
  public static final String METHOD = "method";
  public static final String MSG = "msg";
  public static final String MESSAGE = "message";
  public static final String RESULT = "result";

  /** 默认值 */
  public static final String DEFAULT_VALUE = "1";
  
  /** 雪花算法机器id */
  public static final long MACHINE_ID = 1L;
  public static final long DATA_CENTER_ID = 1L;

  /** 缓存id */
  public static final String KEY_ID = "#id";
  /** 缓存键名称 */
  public static final String KEY_GENERATOR = "keyGenerator";

  /**
   * 队列类型
   */
  public static final String SCHEDULE_CONCURRENT = "1";

  /**
   * 队列名称
   */
  public static final String QUEUE_NAME = "scheduleTask";

  /** 队列键值 */
  public static final String MQ_KEY = "key";
  public static final String MQ_FANOUT = "fanout";
  public static final String MQ_DIRECT = "direct";
  
  /** 文件存储 */
  public static final String FILE_STORAGE = "file.storage";
  
  /** 队列类型方式 */
  public static final String QUEUE_TYPE = "queue.type";

  /** 分隔符 */
  public static final String DELIMITER = "/";
  /** 冒号 */
  public static final String COLON = ":";
  
  /** uuid */
  public static final String UUID = "org.hibernate.id.UUIDGenerator";
  
  /** http */
  public static final String FILE_HTTP = "http://";
  
  /**
   * 枚举集合
   */
  public static final Map<String, String> initEnumMap = new HashMap<>();
  
  /**
   * 队列锁时间
   */
  public static final Integer QUEUE_LOCK_TIME_SECOND = 10;
  
  /**
   * Constructor
   */
  protected PublicConstants() {
    super();
  }
}

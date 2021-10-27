package com.oner365.common.constants;

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
  /** 分页对象 */
  public static final String PARAM_PAGE = "pageInfo";

  /** 返回编码 */
  public static final String CODE = "code";
  /** 返回消息 */
  public static final String MSG = "msg";

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

  /** 分隔符 */
  public static final String DELIMITER = "/";

  /**
   * Constructor
   */
  public PublicConstants() {
    super();
  }
}

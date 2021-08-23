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
   * 状态码
   */
  public static final String STATUS_YES = "1";
  public static final String STATUS_NO = "0";

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

  /**
   * 参数 count
   */
  public static final String PARAM_COUNT = "count";

  /**
   * 参数 list
   */
  public static final String PARAM_LIST = "list";

  public static final String PARAM_STATUS = "status";

  /**
   * 分页对象
   */
  public static final String PARAM_PAGE = "pageInfo";

  public static final String ERROR_PARAM_NOTNULL = "Param is error, %s is not allow null.";

  public static final int SUCCESS_CODE = 1;
  public static final int ERROR_CODE = 0;

  public static final String FAILURE = "failure";

  public static final String SUCCESS = "success";

  public static final String CODE = "code";

  public static final String MSG = "msg";

  public static final String DEFAULT_VALUE = "1";

  public static final String KEY_ID = "#id";
  public static final String KEY_GENERATOR = "keyGenerator";

  /**
   * 队列类型
   */
  public static final String SCHEDULE_CONCURRENT = "1";

  /**
   * 队列名称
   */
  public static final String QUEUE_NAME = "scheduleTask";

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

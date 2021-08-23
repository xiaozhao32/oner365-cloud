package com.oner365.monitor.constants;

/**
 * 任务调度通用常量
 *
 * @author zhaoyong
 */
public class ScheduleConstants {

    public static final String TASK_CLASS_NAME = "TASK_CLASS_NAME";

    /** 执行目标key */
    public static final String TASK_PROPERTIES = "TASK_PROPERTIES";

    /** 默认 */
    public static final String MISFIRE_DEFAULT = "0";

    /** 立即触发执行 */
    public static final String MISFIRE_IGNORE_MISFIRES = "1";

    /** 触发一次执行 */
    public static final String MISFIRE_FIRE_AND_PROCEED = "2";

    /** 不触发立即执行 */
    public static final String MISFIRE_DO_NOTHING = "3";

    /**
     * 任务服务名称
     */
    public static final String SCHEDULE_SERVER_NAME = "oner365-monitor";

    public enum Status {
        /**
         * 正常
         */
        NORMAL("1"),
        /**
         * 暂停
         */
        PAUSE("0");

        private String value;

        Status(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    private ScheduleConstants() {
        super();
    }

}

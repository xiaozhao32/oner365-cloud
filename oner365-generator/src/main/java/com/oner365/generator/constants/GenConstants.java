package com.oner365.generator.constants;

/**
 * 代码生成通用常量
 *
 * @author zhaoyong
 */
public class GenConstants {

    /** 单表（增删改查） */
    public static final String TPL_CRUD = "crud";

    /** 树表（增删改查） */
    public static final String TPL_TREE = "tree";

    /** 树编码字段 */
    public static final String TREE_CODE = "treeCode";

    /** 树父编码字段 */
    public static final String TREE_PARENT_CODE = "treeParentCode";

    /** 树名称字段 */
    public static final String TREE_NAME = "treeName";

    /** 上级菜单ID字段 */
    public static final String PARENT_MENU_ID = "parentMenuId";

    /** 上级菜单名称字段 */
    public static final String PARENT_MENU_NAME = "parentMenuName";

    public static final String PARAM_ID = "id";

    public static final String PARAM_CREATE_USER = "create_user";

    public static final String PARAM_CREATE_TIME = "create_time";

    public static final String PARAM_UPDATE_USER = "update_user";

    public static final String PARAM_UPDATE_TIME = "update_time";

    public static final String PARAM_REMARK = "remark";

    /** 文本框 */
    public static final String HTML_INPUT = "input";

    /** 文本域 */
    public static final String HTML_TEXTAREA = "textarea";

    /** 下拉框 */
    public static final String HTML_SELECT = "select";

    /** 单选框 */
    public static final String HTML_RADIO = "radio";

    /** 复选框 */
    public static final String HTML_CHECKBOX = "checkbox";

    /** 日期控件 */
    public static final String HTML_DATE = "date";

    /** 时间控件 */
    public static final String HTML_DATE_TIME = "datetime";

    /** 上传控件 */
    public static final String HTML_UPLOAD_IMAGE = "uploadImage";

    /** 富文本控件 */
    public static final String HTML_EDITOR = "editor";

    /** 字符串类型 */
    public static final String TYPE_STRING = "String";

    /** 整型 */
    public static final String TYPE_INTEGER = "Integer";

    /** 长整型 */
    public static final String TYPE_LONG = "Long";

    /** 浮点型 */
    public static final String TYPE_DOUBLE = "Double";

    /** 高精度计算类型 */
    public static final String TYPE_BIG_DECIMAL = "BigDecimal";

    /** 日期类型 */
    public static final String TYPE_DATE = "LocalDate";

    /** 时间类型 */
    public static final String TYPE_DATE_TIME = "LocalDateTime";

    /** 枚举状态类型 */
    public static final String TYPE_ENUM_STATUS = "StatusEnum";

    /** json类型 */
    public static final String TYPE_JSON = "json";

    /** 模糊查询 */
    public static final String QUERY_LIKE = "LIKE";

    /** 需要 */
    public static final String REQUIRE = "1";

    /** 构造方法 */
    private GenConstants() {
        super();
    }

}

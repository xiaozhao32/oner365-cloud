package com.oner365.generator.util;

import java.util.Arrays;

import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;

import com.oner365.generator.config.GenConfig;
import com.oner365.generator.constants.GenConstants;
import com.oner365.generator.entity.GenTable;
import com.oner365.generator.entity.GenTableColumn;
import com.oner365.util.DataUtils;

/**
 * 代码生成器 工具类
 *
 * @author zhaoyong
 */
public class GenUtils {

    /** 数据库字符串类型 */
    protected static final String[] COLUMNTYPE_STR = { "char", "varchar", "nvarchar", "varchar2", "tinytext", "text",
            "mediumtext", "longtext" };

    /** 数据库时间类型 */
    protected static final String[] COLUMNTYPE_DATE_TIME = { GenConstants.HTML_DATE_TIME, "time", "timestamp" };

    /** 数据库日期类型 */
    protected static final String[] COLUMNTYPE_DATE = { GenConstants.HTML_DATE };

    /** 数据库数字类型 */
    protected static final String[] COLUMNTYPE_NUMBER = { "tinyint", "smallint", "mediumint", "int", "number",
            "integer", "bit", "bigint", "float", "double", "decimal" };

    /** 页面不需要编辑字段 */
    protected static final String[] COLUMNNAME_NOT_EDIT = { "id", GenConstants.PARAM_CREATE_USER,
            GenConstants.PARAM_CREATE_TIME };

    /** 页面不需要显示的列表字段 */
    protected static final String[] COLUMNNAME_NOT_LIST = { "id", GenConstants.PARAM_CREATE_USER,
            GenConstants.PARAM_CREATE_TIME, "update_user", "update_time" };

    /** 页面不需要查询字段 */
    protected static final String[] COLUMNNAME_NOT_QUERY = { "id", GenConstants.PARAM_CREATE_USER,
            GenConstants.PARAM_CREATE_TIME, "update_user", "update_time", "remark" };

    private static final int POS_2 = 2;
    private static final int POS_10 = 10;

    public static final String PARAM_NAME = "name";
    public static final String PARAM_STATUS = "status";
    public static final String PARAM_TYPE = "type";
    public static final String PARAM_IMAGE = "image";
    public static final String PARAM_CONTENT = "content";

    public static final String C_LEFT = "(";
    public static final String C_RIGHT = ")";

    /**
     * 构造方法
     */
    private GenUtils() {

    }

    /**
     * 初始化表信息
     */
    public static void initTable(GenConfig genConfig, GenTable genTable, String operName) {
        genTable.setClassName(convertClassName(genTable.getTableName(), genConfig.getTablePrefix(), genConfig.getAutoRemovePre()));
        genTable.setPackageName(genConfig.getPackageName());
        genTable.setModuleName(getModuleName(genConfig.getPackageName()));
        genTable.setBusinessName(getBusinessName(genTable.getTableName()));
        genTable.setFunctionName(replaceText(genTable.getTableComment()));
        genTable.setFunctionAuthor(genConfig.getAuthor());
        genTable.setCreateBy(operName);
    }

    /**
     * 初始化列属性字段
     */
    public static void initColumnField(GenTableColumn column, GenTable table) {
        String dataType = getDbType(column.getColumnType());
        String columnName = column.getColumnName();
        column.setTableId(table.getTableId());
        column.setCreateBy(table.getCreateBy());
        // 设置java字段名
        column.setJavaField(DataUtils.coderName(columnName));

        if (arraysContains(COLUMNTYPE_STR, dataType)) {
            column.setJavaType(GenConstants.TYPE_STRING);
            // 字符串长度超过500设置为文本域
            Integer columnLength = getColumnLength(column.getColumnType());
            String htmlType = columnLength >= 500 ? GenConstants.HTML_TEXTAREA : GenConstants.HTML_INPUT;
            column.setHtmlType(htmlType);
        } else if (arraysContains(COLUMNTYPE_DATE, dataType)) {
            column.setJavaType(GenConstants.TYPE_DATE);
            column.setHtmlType(GenConstants.HTML_DATE);
        } else if (arraysContains(COLUMNTYPE_DATE_TIME, dataType)) {
            column.setJavaType(GenConstants.TYPE_DATE_TIME);
            column.setHtmlType(GenConstants.HTML_DATE_TIME);
        } else if (arraysContains(COLUMNTYPE_NUMBER, dataType)) {
            column.setHtmlType(GenConstants.HTML_INPUT);

            // 如果是浮点型 统一用BigDecimal
            String[] str = StringUtils.split(StringUtils.substringBetween(column.getColumnType(), "(", ")"), ",");
            if (str != null && str.length == POS_2 && Integer.parseInt(str[1]) > 0) {
                column.setJavaType(GenConstants.TYPE_BIG_DECIMAL);
            }
            // 如果是整形
            else if (str != null && str.length == 1 && Integer.parseInt(str[0]) <= POS_10) {
                column.setJavaType(GenConstants.TYPE_INTEGER);
            }
            // 长整形
            else {
                column.setJavaType(GenConstants.TYPE_LONG);
            }
        }

        // 插入字段（默认所有字段都需要插入）
        column.setIsInsert(GenConstants.REQUIRE);

        // 编辑字段
        if (!arraysContains(COLUMNNAME_NOT_EDIT, columnName) && !column.isPk()) {
            column.setIsEdit(GenConstants.REQUIRE);
        }
        // 列表字段
        if (!arraysContains(COLUMNNAME_NOT_LIST, columnName) && !column.isPk()) {
            column.setIsList(GenConstants.REQUIRE);
        }
        // 查询字段
        if (!arraysContains(COLUMNNAME_NOT_QUERY, columnName) && !column.isPk()) {
            column.setIsQuery(GenConstants.REQUIRE);
        }

        // 查询字段类型
        if (StringUtils.endsWithIgnoreCase(columnName, PARAM_NAME)) {
            column.setQueryType(GenConstants.QUERY_LIKE);
        }
        // 状态字段设置单选框
        if (StringUtils.endsWithIgnoreCase(columnName, PARAM_STATUS)) {
            column.setHtmlType(GenConstants.HTML_RADIO);
        }
        // 类型&性别字段设置下拉框
        else if (StringUtils.endsWithIgnoreCase(columnName, PARAM_TYPE)
                || StringUtils.endsWithIgnoreCase(columnName, "sex")) {
            column.setHtmlType(GenConstants.HTML_SELECT);
        }
        // 文件字段设置上传控件
        else if (StringUtils.endsWithIgnoreCase(columnName, PARAM_IMAGE)) {
            column.setHtmlType(GenConstants.HTML_UPLOAD_IMAGE);
        }
        // 内容字段设置富文本控件
        else if (StringUtils.endsWithIgnoreCase(columnName, PARAM_CONTENT)) {
            column.setHtmlType(GenConstants.HTML_EDITOR);
        }
    }

    /**
     * 校验数组是否包含指定值
     *
     * @param arr         数组
     * @param targetValue 值
     * @return 是否包含
     */
    public static boolean arraysContains(String[] arr, String targetValue) {
        return Arrays.asList(arr).contains(targetValue);
    }

    /**
     * 获取模块名
     *
     * @param packageName 包名
     * @return 模块名
     */
    public static String getModuleName(String packageName) {
        int lastIndex = packageName.lastIndexOf('.');
        int nameLength = packageName.length();
        return StringUtils.substring(packageName, lastIndex + 1, nameLength);
    }

    /**
     * 获取业务名
     *
     * @param tableName 表名
     * @return 业务名
     */
    public static String getBusinessName(String tableName) {
        int lastIndex = tableName.lastIndexOf('_');
        int nameLength = tableName.length();
        return StringUtils.substring(tableName, lastIndex + 1, nameLength);
    }

    /**
     * 表名转换成Java类名
     *
     * @param tableName 表名称
     * @return 类名
     */
    public static String convertClassName(String tableName, String tablePrefix, boolean autoRemovePre) {
        if (autoRemovePre && !DataUtils.isEmpty(tablePrefix)) {
            String[] searchList = StringUtils.split(tablePrefix, ",");
            tableName = replaceFirst(tableName, searchList);
        }
        return DataUtils.builderName(tableName);
    }

    /**
     * 批量替换前缀
     *
     * @param replacement 替换值
     * @param searchList   替换列表
     * @return String
     */
    public static String replaceFirst(String replacement, String[] searchList) {
        String text = replacement;
        for (String searchString : searchList) {
            if (replacement.startsWith(searchString)) {
                text = replacement.replaceFirst(searchString, "");
                break;
            }
        }
        return text;
    }

    /**
     * 关键字替换
     *
     * @param text 需要被替换的名字
     * @return 替换后的名字
     */
    public static String replaceText(String text) {
        return RegExUtils.replaceAll(text, "(?:表|Oner365)", "");
    }

    /**
     * 获取数据库类型字段
     *
     * @param columnType 列类型
     * @return 截取后的列类型
     */
    public static String getDbType(String columnType) {
        if (StringUtils.indexOf(columnType, C_LEFT) > 0) {
            return StringUtils.substringBefore(columnType, C_LEFT);
        } else {
            return columnType;
        }
    }

    /**
     * 获取字段长度
     *
     * @param columnType 列类型
     * @return 截取后的列类型
     */
    public static Integer getColumnLength(String columnType) {
        if (StringUtils.indexOf(columnType, C_LEFT) > 0) {
            String length = StringUtils.substringBetween(columnType, C_LEFT, C_RIGHT);
            return Integer.valueOf(length);
        } else {
            return 0;
        }
    }
}

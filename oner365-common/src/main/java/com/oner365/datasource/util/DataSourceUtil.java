package com.oner365.datasource.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oner365.common.constants.PublicConstants;
import com.oner365.datasource.constants.DataSourceConstants;
import com.oner365.util.ClassesUtil;
import com.oner365.util.DataUtils;

/**
 * 数据源工具类
 *
 * @author zhaoyong
 *
 */
public class DataSourceUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceUtil.class);

    /**
     * Generate constructor
     */
    private DataSourceUtil() {
    }

    /**
     * 查询数据库结构
     *
     * @param driverClassName
     *            驱动名称
     * @param url
     *            地址
     * @param userName
     *            数据库名称
     * @return String
     */
    public static String queryTableSchema(String driverClassName, String url, String userName) {
        String tableSchema = StringUtils.substringAfterLast(url, PublicConstants.DELIMITER);
        tableSchema = StringUtils.substringBefore(tableSchema, "?");

        StringBuilder queryString = new StringBuilder();
        /* 数据库类型 */
        if (DataSourceConstants.DRIVER_NAME_MYSQL.equals(driverClassName)) {
            // MySQL
            queryString.append(" SELECT table_name, substring_index(TABLE_COMMENT, ';','1') as table_comment ");
            queryString.append(" FROM INFORMATION_SCHEMA.TABLES ");
            queryString.append(" WHERE TABLE_SCHEMA='").append(tableSchema).append("' ");
        } else if (DataSourceConstants.DRIVER_NAME_ORACLE.equals(driverClassName)) {
            // Oracle
            queryString.append(" select table_name, comments as table_comment from user_tab_comments");
        } else if (DataSourceConstants.DRIVER_NAME_POSTGRESQL.equals(driverClassName)) {
            // postgreSQL
            queryString.append(" select tablename as table_name from pg_tables where schemaname='public' ");
        } else if (DataSourceConstants.DRIVER_NAME_DB2.equals(driverClassName)) {
            // DB2
            queryString.append(" select TABNAME as table_name,REMARKS as table_comment from SYSCAT.TABLES where TABSCHEMA = '").append(userName.toUpperCase()).append("' and TYPE  = 'T' ");
        } else if (DataSourceConstants.DRIVER_NAME_SQLSERVER.equals(driverClassName)) {
            // SQLServer
            queryString.append(" select name as table_name from sysobjects where xtype='U'");
        } else {
            LOGGER.error("{} Not found driver!", driverClassName);
        }
        return queryString.toString();
    }

    /**
     * 查询表结构
     *
     * @param driverClassName
     *            驱动名称
     * @param url
     *            地址
     * @param userName
     *            数据库名称
     * @param tableName
     *            表名称
     * @return String
     */
    public static String queryColumnSchema(String driverClassName, String url, String userName, String tableName) {
        String tableSchema = StringUtils.substringAfterLast(url, PublicConstants.DELIMITER);
        tableSchema = StringUtils.substringBefore(tableSchema, "?");

        StringBuilder queryString = new StringBuilder();
        /* 数据库类型 */
        if (DataSourceConstants.DRIVER_NAME_MYSQL.equals(driverClassName)) {
            // MySQL
            queryString.append(
                    " select a.TABLE_NAME as table_name,substring_index(a.TABLE_COMMENT, ';','1') as table_comment, ");
            queryString.append(
                    " b.COLUMN_NAME as column_name ,b.COLUMN_COMMENT as column_comment,b.COLUMN_TYPE as column_type,b.CHARACTER_MAXIMUM_LENGTH as data_length,b.COLUMN_DEFAULT as column_default,b.IS_NULLABLE as is_nullable,b.EXTRA as extra ");
            queryString.append(" from INFORMATION_SCHEMA.TABLES a, INFORMATION_SCHEMA.COLUMNS b ");
            queryString.append(" where a.TABLE_NAME=b.TABLE_NAME ");
            queryString.append(" and a.TABLE_SCHEMA=b.TABLE_SCHEMA ");
            queryString.append(" and b.TABLE_SCHEMA='").append(tableSchema).append("' ");
            queryString.append(" and b.TABLE_NAME = '").append(tableName).append("' ");
        } else if (DataSourceConstants.DRIVER_NAME_ORACLE.equals(driverClassName)) {
            // Oracle
            queryString.append(
                    " select a.TABLE_NAME as table_name, c.COMMENTS as table_comment, a.COLUMN_NAME as column_name, b.COMMENTS as column_comment, a.DATA_TYPE as column_type, a.DATA_LENGTH as data_length, a.DATA_SCALE as column_default, a.NULLABLE as is_nullable ");
            queryString.append("  from USER_TAB_COLUMNS a, USER_COL_COMMENTS b,USER_TAB_COMMENTS c ");
            queryString.append(
                    " where a.TABLE_NAME = b.TABLE_NAME and a.TABLE_NAME=c.TABLE_NAME and a.COLUMN_NAME = b.COLUMN_NAME ");
            queryString.append("    and a.table_name = upper('").append(tableName).append("') ");
        } else if (DataSourceConstants.DRIVER_NAME_POSTGRESQL.equals(driverClassName)) {
            // PostgreSQL
            queryString.append(
                    " SELECT col.table_name as table_name,col.column_name as column_name,des.description as column_comment,col.data_type as column_type,col.character_maximum_length as data_length,col.column_default as column_default,col.is_nullable as is_nullable ");
            queryString.append(" FROM information_schema.columns col ");
            queryString.append(" LEFT JOIN pg_description des ON col.table_name::regclass = des.objoid ");
            queryString.append(" AND col.ordinal_position = des.objsubid ");
            queryString.append(" WHERE table_schema = 'public' and table_name = '").append(tableName).append("' ");
            queryString.append(" ORDER BY ordinal_position ");
        } else if (DataSourceConstants.DRIVER_NAME_DB2.equals(driverClassName)) {
            // DB2
            queryString.append(
                    " select col.TABNAME as table_name, t.REMARKS as table_comment, col.COLNAME as column_name,col.REMARKS as column_comment,col.TYPENAME as column_type,col.LENGTH as data_length,col.DEFAULT as column_default,col.NULLS as is_nullable ");
            queryString.append(" from SYSCAT.COLUMNS col, SYSCAT.TABLES t ");
            queryString.append(" where col.TABNAME = t.TABNAME ");
            queryString.append(" and col.TABSCHEMA = '").append(userName.toUpperCase()).append("' ");
            queryString.append(" and col.TABNAME='").append(tableName.toUpperCase()).append("'");
        } else if (DataSourceConstants.DRIVER_NAME_SQLSERVER.equals(driverClassName)) {
            // SQLServer
            queryString.append(
                    " select o.name as table_name, c.name as column_name, c.length as data_length, c.isnullable as is_nullable ");
            queryString.append(" from sysobjects o, syscolumns c where o.id=c.id and o.name='").append(tableName).append("'");
        } else {
            LOGGER.error("{} Not found driver!", driverClassName);
        }
        return queryString.toString();
    }

    /**
     * 判断连接
     *
     * @param driverName 驱动名称
     * @param url 地址
     * @param userName 账号
     * @param password 密码
     * @return boolean
     */
    public static boolean isConnection(String driverName, String url, String userName, String password) {
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            LOGGER.error("Class Not found:", e);
        }
        try (Connection con = DriverManager.getConnection(url, userName, password);
                Statement stat = con.createStatement()) {
            if (stat != null) {
                return true;
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException:", e);
        }
        return false;
    }

    /**
     * 获取连接
     *
     * @param driverName 驱动名称
     * @param url 地址
     * @param userName 账号
     * @param password 密码
     * @return Connection
     */
    public static Connection getConnection(String driverName, String url, String userName, String password) {
        try {
            Class.forName(driverName);
            return DriverManager.getConnection(url, userName, password);
        } catch (Exception e) {
            LOGGER.error("getConnection:", e);
        }
        return null;
    }

    /**
     * 根据动态数据源和SQL，返回Result结果集
     *
     * @param sql
     *            SQL
     * @return List<Map<String, String>>
     */
    public static List<Map<String, String>> execute(Connection con, String sql) {
        final String[] key = new String[] { "create ", "drop ", "alter ", "insert ", "update ", "delete ", "call ",
                "CREATE ", "DROP ", "ALTER ", "INSERT ", "UPDATE ", "DELETE ", "CALL " };

        if (sql == null) {
            return Collections.emptyList();
        }

        List<Map<String, String>> resultList = new ArrayList<>();

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            // 判断是否是执行语句
            boolean isExecute = false;
            for (String s : key) {
                if (StringUtils.startsWith(sql, s)) {
                    isExecute = true;
                    break;
                }
            }

            if (isExecute) {
                execute(con, ps, resultList);
            } else {
                execute(ps, resultList);
            }

        } catch (Exception e) {
            Map<String, String> map = new HashMap<>();
            map.put("Error:", e.getMessage());
            resultList.add(map);
            LOGGER.error("execute error:", e);
        }
        return resultList;

    }

    /**
     * 执行sql
     *
     * @param con Connection
     * @param ps PreparedStatement
     * @param resultList 结果
     * @throws SQLException 异常
     */
    private static void execute(Connection con, PreparedStatement ps, List<Map<String, String>> resultList)
            throws SQLException {
        // SQL执行
        con.setAutoCommit(false);
        int result = ps.executeUpdate();
        con.commit();

        // 返回结果
        Map<String, String> map = new HashMap<>();
        map.put("result", String.valueOf(result));
        resultList.add(map);
    }

    /**
     * 查询sql
     *
     * @param ps PreparedStatement
     * @param resultList 结果
     * @throws SQLException 异常
     */
    private static void execute(PreparedStatement ps, List<Map<String, String>> resultList) throws SQLException {
        try (ResultSet rs = ps.executeQuery()) {
            // 返回结果
            ResultSetMetaData md = rs.getMetaData();
            int column = md.getColumnCount();
            while (rs.next()) {
                Map<String, String> map = new HashMap<>();
                for (int i = 1; i <= column; i++) {
                    map.put(md.getColumnName(i).toLowerCase(), rs.getString(i));
                }
                resultList.add(map);
            }
        }
    }

    /**
     * 关闭连接
     *
     * @param o 对象
     */
    public static void close(Object o) {
        try {
            if (o instanceof ResultSet) {
                ((ResultSet) o).close();
            } else if (o instanceof CallableStatement) {
                ((CallableStatement) o).close();
            } else if (o instanceof PreparedStatement) {
                ((PreparedStatement) o).close();
            } else if (o instanceof Statement) {
                ((Statement) o).close();
            } else if (o instanceof Connection) {
                ((Connection) o).close();
            }
        } catch (SQLException e) {
            LOGGER.error("close error:", e);
        }
    }

    /**
     * List<Bean>对象转换成insert sql语句
     *
     * @param list 对象
     * @param database 数据源
     * @return String
     */
    public static <T> String insertSqlList(List<T> list, String database) {
        StringBuilder result = new StringBuilder();
        if (!DataUtils.isEmpty(list)) {
            for (T object : list) {
                result.append(insertSql(object, database));
            }
        }
        return result.toString();
    }

    /**
     * Bean对象转换成insert sql语句
     *
     * @param bean 对象
     * @param database 数据源
     * @return String
     */
    public static <T> String insertSql(T bean, String database) {
        StringBuilder result = new StringBuilder();
        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();
        result.append("insert into ");

        Class<?> clazz = bean.getClass();
        Table table = clazz.getAnnotation(Table.class);
        if (table == null) {
            return StringUtils.EMPTY;
        }
        Method[] getters = ClassesUtil.getGetters(clazz);
        for (Method m : getters) {
            String property = ClassesUtil.getProperty(m);
            if (property == null || "class".equals(property)) {
                continue;
            }

            Field field = FieldUtils.getField(clazz, property, true);
            Column column = field.getAnnotation(Column.class);
            String columnName;
            if (column != null) {
                columnName = column.name();
            } else {
                JoinColumn joinColumn = field.getAnnotation(JoinColumn.class);
                if (joinColumn != null) {
                    columnName = joinColumn.name();
                } else {
                    columnName = field.getName();
                }
            }
            columns.append(columnName).append(",");

            Class<?> retType = m.getReturnType();

            Object val = null;
            try {
                val = field.get(bean);
            } catch (Exception e) {
                LOGGER.error("ArgumentException:", e);
            }
            getSql(retType, val, values, database);

        }
        String col = columns.toString();
        col = col.substring(0, col.length() - 1);
        String val = values.toString();
        val = val.substring(0, val.length() - 1);

        result.append(table.name()).append(" (").append(col).append(") values(").append(val).append(");");
        return result.toString() + "\n";
    }

    /**
     * 获取SQL
     *
     * @param retType 类
     * @param val 字段
     * @param values 值
     * @param database 数据源
     */
    private static void getSql(Class<?> retType, Object val, StringBuilder values, String database) {
        if (val == null) {
            values.append(",");
        } else {
            if (ClassesUtil.isPrimitive(retType)) {
                // 基本类型
                values.append("'").append(val).append("'").append(",");
            } else if (ClassesUtil.isDate(retType)) {
                // 时间
                if (DataSourceConstants.DRIVER_NAME_ORACLE.equals(database)) {
                    values.append("TO_TIMESTAMP('").append(val).append("', 'YYYY-MM-DD HH24:MI:SS:FF6')").append(",");
                } else {
                    values.append("'").append(val).append("'").append(",");
                }
            } else {
                getSql(val, values);
            }
        }
    }

    /**
     *
     * @param val 字段
     * @param values 值
     */
    private static void getSql(Object val, StringBuilder values) {
        // 对象类型获取id ->未排除集合类型
        Class<?> clazzBean = val.getClass();
        Method[] gettersBean = ClassesUtil.getGetters(clazzBean);
        for (Method mBean : gettersBean) {
            String propertyBean = ClassesUtil.getProperty(mBean);
            if (propertyBean == null || "class".equals(propertyBean)) {
                continue;
            }
            Field fieldBean = FieldUtils.getField(clazzBean, propertyBean, true);
            if (DataSourceConstants.ID.equals(propertyBean)) {
                try {
                    Object valBean = fieldBean.get(val);
                    values.append("'").append(valBean).append("'").append(",");
                } catch (Exception e) {
                    LOGGER.error("getSQL error:", e);
                }
            }
        }
    }


}

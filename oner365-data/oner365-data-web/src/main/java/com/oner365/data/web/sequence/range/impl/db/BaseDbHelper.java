package com.oner365.data.web.sequence.range.impl.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oner365.data.web.sequence.exception.SeqException;

/**
 * sequence db helper
 *
 * @author zhaoyong
 */
abstract class BaseDbHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseDbHelper.class);

    private static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS #tableName(id bigint(20) NOT NULL AUTO_INCREMENT,value bigint(20) NOT NULL,name varchar(32) NOT NULL,gmt_create DATETIME NOT NULL,gmt_modified DATETIME NOT NULL,PRIMARY KEY (`id`),UNIQUE uk_name (`name`))";

    private static final String SQL_INSERT_RANGE = "INSERT IGNORE INTO #tableName(name,value,gmt_create,gmt_modified) VALUE(?,?,?,?)";

    private static final String SQL_UPDATE_RANGE = "UPDATE #tableName SET value=?,gmt_modified=? WHERE name=? AND value=?";

    private static final String SQL_SELECT_RANGE = "SELECT value FROM #tableName WHERE name=?";

    private static final String SQL_TABLE_NAME = "#tableName";

    private static final Long MAX_VALUE = 9223372036754775807L;

    private BaseDbHelper() {

    }

    static void createTable(DataSource dataSource, String tableName) {
        try (Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(SQL_CREATE_TABLE.replace(SQL_TABLE_NAME, tableName));
        }
        catch (SQLException e) {
            throw new SeqException(e);
        }
    }

    private static void insertRange(DataSource dataSource, String tableName, String name, long stepStart) {
        try (Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(SQL_INSERT_RANGE.replace(SQL_TABLE_NAME, tableName))) {
            stmt.setString(1, name);
            stmt.setLong(2, stepStart);
            stmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            stmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            throw new SeqException(e);
        }
    }

    static boolean updateRange(DataSource dataSource, String tableName, Long newValue, Long oldValue, String name) {
        try (Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE_RANGE.replace(SQL_TABLE_NAME, tableName))) {
            stmt.setLong(1, newValue);
            stmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            stmt.setString(3, name);
            stmt.setLong(4, oldValue);
            int affectedRows = stmt.executeUpdate();
            return (affectedRows > 0);
        }
        catch (SQLException e) {
            throw new SeqException(e);
        }
    }

    static Long selectRange(DataSource dataSource, String tableName, String name, long stepStart) {
        ResultSet rs = null;
        try (Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_RANGE.replace(SQL_TABLE_NAME, tableName))) {
            stmt.setString(1, name);
            rs = stmt.executeQuery();
            if (!rs.next()) {
                insertRange(dataSource, tableName, name, stepStart);
                return null;
            }
            long oldValue = rs.getLong(1);
            if (oldValue < 0L) {
                String msg = "Sequence value cannot be less than zero, value = " + oldValue
                        + ", please check table sequence" + tableName;
                throw new SeqException(msg);
            }
            if (oldValue > MAX_VALUE) {
                String msg = "Sequence value overflow, value = " + oldValue + ", please check table sequence"
                        + tableName;
                throw new SeqException(msg);
            }
            return oldValue;
        }
        catch (SQLException e) {
            throw new SeqException(e);
        }
        finally {
            closeQuietly(rs);
        }
    }

    private static void closeQuietly(AutoCloseable closeable) {
        if (null != closeable) {
            try {
                closeable.close();
            }
            catch (Exception e) {
                LOGGER.error("close error:", e);
            }
        }
    }

}

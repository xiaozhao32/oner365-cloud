package com.oner365.data.commons.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Blob 工具类
 * 
 * @author zhaoyong
 *
 */
public class BlobUtils implements Blob {

    private static final Logger LOGGER = LoggerFactory.getLogger(BlobUtils.class);

    private byte[] blobBytes = new byte[0];

    private int blobLength = 0;

    /**
     * 构造函数
     */
    public BlobUtils() {
    }

    /**
     * 构造函数，以byte[]构建blob
     * 
     * @param bytes 字节
     */
    public BlobUtils(byte[] bytes) {
        init(bytes);
    }

    /**
     * 构造函数，以blob重新构建blob
     * 
     * @param blob blob
     */
    public BlobUtils(Blob blob) {
        init(blobToBytes(blob));
    }

    /**
     * 初始化byte[]
     * 
     * @param bytes 字节
     */
    private void init(byte[] bytes) {
        blobBytes = bytes;
        blobLength = blobBytes.length;
    }

    /**
     * 将blob转为byte[]
     * 
     * @param blob blob
     * @return byte[]
     */
    private byte[] blobToBytes(Blob blob) {
        try (InputStream inputStream = blob.getBinaryStream();
            BufferedInputStream is = new BufferedInputStream(inputStream)) {
            byte[] bytes = new byte[(int) blob.length()];
            int len = bytes.length;
            int offset = 0;
            int read;
            while (offset < len && (read = is.read(bytes, offset, len - offset)) >= 0) {
                offset += read;
            }
            return bytes;
        } catch (Exception e) {
            LOGGER.error("blobToBytes error:", e);
        }
        return new byte[0];
    }

    /**
     * 获得blob中数据实际长度
     * 
     * @return long
     */
    @Override
    public long length() {
        return blobBytes.length;
    }

    /**
     * 返回指定长度的byte[]
     * 
     * @param pos pos
     * @param len len
     * @return byte[]
     * @throws SQLException 异常
     */
    @Override
    public byte[] getBytes(long pos, int len) throws SQLException {
        if (pos == 0 && len == length()) {
            return blobBytes;
        }
        try {
            byte[] newBytes = new byte[len];
            System.arraycopy(blobBytes, (int) pos, newBytes, 0, len);
            return newBytes;
        } catch (Exception e) {
            throw new SQLException("Inoperable scope of this array");
        }
    }

    /**
     * 返回InputStream
     * 
     * @return InputStream
     */
    @Override
    public InputStream getBinaryStream() {
        return new ByteArrayInputStream(blobBytes);
    }

    /**
     * 获取此byte[]中start的字节位置
     * 
     * @param pattern byte[]
     * @param start long
     * @return long
     * @throws SQLException 异常
     */
    @Override
    public long position(byte[] pattern, long start) throws SQLException {
        start--;
        if (start < 0) {
            throw new SQLException("start < 0");
        }
        if (start >= blobLength) {
            throw new SQLException("start >= max length");
        }
        if (pattern == null) {
            throw new SQLException("pattern == null");
        }
        if (pattern.length == 0 || blobLength == 0 || pattern.length > blobLength) {
            return -1;
        }
        
        return positions(pattern, start);
    }
    
    private long positions(byte[] pattern, long start) {
      int limit = blobLength - pattern.length;
      for (int i = (int) start; i <= limit; i++) {
          int p;
          for (p = 0; p < pattern.length && blobBytes[i + p] == pattern[p]; p++) {
              if (p == pattern.length) {
                  return i + 1L;
              }
          }
      }
      return -1;
    }

    /**
     * 获取指定的blob中start的字节位置
     * 
     * @param pattern Blog
     * @param start long
     * @return long
     * @throws SQLException 异常
     */
    @Override
    public long position(Blob pattern, long start) throws SQLException {
        return position(blobToBytes(pattern), start);
    }

    /**
     * 不支持操作异常抛出
     * 
     */
    void nonsupport() {
        throw new UnsupportedOperationException("This method is not supported！");
    }

    /**
     * 释放Blob对象资源
     *
     */
    @Override
    public void free() {
        blobBytes = new byte[0];
        blobLength = 0;
    }

    /**
     * 返回指定长度部分的InputStream，并返回InputStream
     * 
     * @param pos pos
     * @param len len
     * @return InputStream
     * @throws SQLException 异常
     */
    @Override
    public InputStream getBinaryStream(long pos, long len) throws SQLException {
        return new ByteArrayInputStream(getBytes(pos, (int) len));
    }

    /**
     * 以指定指定长度将二进制流写入OutputStream，并返回OutputStream
     * 
     * @param pos long
     * @return OutputStream
     * @throws SQLException 异常
     */
    @Override
    public OutputStream setBinaryStream(long pos) throws SQLException {
        // 暂不支持
        nonsupport();
        pos--;
        if (pos < 0) {
            throw new SQLException("pos < 0");
        }
        if (pos > blobLength) {
            throw new SQLException("pos > length");
        }
        // 将byte[]转为ByteArrayInputStream

        byte[] bytes;
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(blobBytes);
                ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            bytes = new byte[inputStream.available()];
            int read;
            while ((read = inputStream.read(bytes)) >= 0) {
                os.write(bytes, 0, read);
            }
            // 返回OutputStream
            return os;
        } catch (IOException e) {
            LOGGER.error("setBinaryStream error:", e);
        }
        return null;
    }

    /**
     * 设定byte[]
     * 
     * @param pos pos
     * @param bytes bytes
     * @param offset offset
     * @param size size
     * @param isCopy copy
     * @return int
     * @throws SQLException 异常
     */
    public int setBytes(long pos, byte[] bytes, int offset, int size, boolean isCopy) throws SQLException {
        // 暂不支持
        nonsupport();
        pos--;
        if (pos < 0) {
            throw new SQLException("pos < 0");
        }
        if (pos > blobLength) {
            throw new SQLException("pos > max length");
        }
        if (bytes == null) {
            throw new SQLException("bytes == null");
        }
        if (offset < 0 || offset > bytes.length) {
            throw new SQLException("offset < 0 || offset > bytes.length");
        }
        if (size < 0 || pos + size > Integer.MAX_VALUE || offset + size > bytes.length) {
            throw new SQLException();
        }
        // 当copy数据时
        if (isCopy) {
            blobBytes = new byte[size];
            System.arraycopy(bytes, offset, blobBytes, 0, size);
        } else { // 否则直接替换对象
            blobBytes = bytes;
        }
        return blobBytes.length;
    }

    /**
     * 设定指定开始位置byte[]
     * 
     * @param pos pos
     * @param bytes bytes
     * @return int
     * @throws SQLException 异常
     */
    @Override
    public int setBytes(long pos, byte[] bytes) throws SQLException {
        // 暂不支持
        nonsupport();
        return setBytes(pos, bytes, 0, bytes.length, true);
    }

    /**
     * 设定byte[]
     * 
     * @param pos pos
     * @param bytes bytes
     * @param offset offset
     * @param len len
     * @return int
     * @throws SQLException 异常
     */
    @Override
    public int setBytes(long pos, byte[] bytes, int offset, int len) throws SQLException {
        // 暂不支持
        nonsupport();
        return setBytes(pos, bytes, offset, len, true);
    }

    /**
     * 截取相应部分数据
     * 
     * @param len len
     * @throws SQLException 异常
     */
    @Override
    public void truncate(long len) throws SQLException {
        if (len < 0) {
            throw new SQLException("len < 0");
        }
        if (len > blobLength) {
            throw new SQLException("len > max length");
        }
        blobLength = (int) len;
    }
}

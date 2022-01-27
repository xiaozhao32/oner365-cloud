package com.oner365.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oner365.common.constants.PublicConstants;

/**
 * 解压缩工具类
 * 
 * @author zhaoyong
 */
public class ZipUtil {

  private static final Logger LOGGER = LoggerFactory.getLogger(ZipUtil.class);

  /**
   * Generate constructor
   */
  private static final int SIZE = 1024;

  /**
   * Generate constructor
   */
  private ZipUtil() {
  }

  /**
   * zip压缩
   * 
   * @param file   文件
   * @param dest   目标路径
   * @param filter 过滤
   */
  public static void zip(File file, String dest, List<String> filter) {
    try (FileOutputStream outputStream = new FileOutputStream(dest);
        ZipOutputStream out = new ZipOutputStream(outputStream)) {
      zip(out, file, "", filter);
    } catch (IOException e) {
      LOGGER.error("Error zip:", e);
    }
  }

  /**
   * zip压缩
   * 
   * @param files  多个文件
   * @param dest   目标路径
   * @param filter 过滤
   */
  public static void zip(List<File> files, String dest, List<String> filter) {
    try (FileOutputStream outputStream = new FileOutputStream(dest);
        ZipOutputStream out = new ZipOutputStream(outputStream)) {
      files.forEach(file -> zip(out, file, "", filter));
    } catch (IOException e) {
      LOGGER.error("Error zip:", e);
    }
  }

  /**
   * zip压缩
   * 
   * @param out      输出流
   * @param srcFile  文件
   * @param baseName 位置
   * @param filter   过滤
   */
  private static void zip(ZipOutputStream out, File srcFile, String baseName, List<String> filter) {
    if (srcFile.exists()) {
      String base = baseName;
      if (srcFile.isDirectory()) {
        base = base.length() == 0 ? "" : base + PublicConstants.DELIMITER;
        zipBaseDirectory(out, srcFile, filter, base);
      } else {
        zipFilter(out, srcFile, base, filter);
      }
    }
  }

  private static void zipFilter(ZipOutputStream out, File srcFile, String base, List<String> filter) {
    if (filter != null) {
      if (isExist(base, filter)) {
        base = base.length() == 0 ? srcFile.getName() : base;
        zipBaseFile(out, srcFile, base);
      }
    } else {
      base = base.length() == 0 ? srcFile.getName() : base;
      zipBaseFile(out, srcFile, base);
    }
  }

  /**
   * 压缩目录
   * 
   * @param out     输出流
   * @param srcFile 文件
   * @param filter  过滤
   * @param base    位置
   */
  private static void zipBaseDirectory(ZipOutputStream out, File srcFile, List<String> filter, String base) {
    try {
      File[] files = srcFile.listFiles();
      if (filter != null) {
        if (!srcFile.isDirectory() && isExist(base, filter)) {
          out.putNextEntry(new ZipEntry(base));
        }
      } else {
        if (!srcFile.isDirectory()) {
          out.putNextEntry(new ZipEntry(base));
        }
      }
      Arrays.stream(files).forEach(file -> zip(out, file, base + file.getName(), filter));
    } catch (IOException e) {
      LOGGER.error("Error zipBaseDirectory:", e);
    }
  }

  /**
   * 压缩文件
   * 
   * @param out     输出流
   * @param srcFile 文件
   * @param base    位置
   */
  private static void zipBaseFile(ZipOutputStream out, File srcFile, String base) {
    final String comment = "zip comment";
    try (FileInputStream in = new FileInputStream(srcFile)) {
      ZipEntry zipEntry = new ZipEntry(base);
      zipEntry.setComment(comment);
      out.putNextEntry(zipEntry);
      int length;
      byte[] b = new byte[SIZE];
      while ((length = in.read(b, 0, SIZE)) != -1) {
        out.write(b, 0, length);
      }
    } catch (IOException e) {
      LOGGER.error("Error zipBaseFile:", e);
    }
  }

  /**
   * 判断文件是否存在
   * 
   * @param base 位置
   * @param list 文件
   * @return boolean
   */
  private static boolean isExist(String base, List<String> list) {
    if (!DataUtils.isEmpty(list)) {
      return list.stream().anyMatch(base::contains);
    }
    return false;
  }

  /**
   * 解压缩
   * 
   * @param srcFile    文件
   * @param dest       输出路径
   * @param deleteFile 是否删除
   */
  public static void unZip(String srcFile, String dest, boolean deleteFile) {
    File file = DataUtils.getFile(srcFile);
    if (file.exists()) {
      try (ZipFile zipFile = new ZipFile(file)) {
        Enumeration<?> e = zipFile.getEntries();
        while (e.hasMoreElements()) {
          unzipFile(zipFile, e, dest);
        }
        if (deleteFile) {
          file.deleteOnExit();
        }
      } catch (IOException e) {
        LOGGER.error("Error unZip:", e);
      }
    }
  }

  private static void unzipFile(ZipFile zipFile, Enumeration<?> e, String dest) throws IOException {
    ZipEntry zipEntry = (ZipEntry) e.nextElement();
    if (zipEntry.isDirectory()) {
      String name = zipEntry.getName();
      name = name.substring(0, name.length() - 1);
      File f = DataUtils.getFile(dest + name);
      f.mkdirs();
    } else {
      String fileName = dest + zipEntry.getName();
      DataUtils.createFile(fileName, null);

      try (InputStream is = zipFile.getInputStream(zipEntry);
          FileOutputStream fos = DataUtils.getFileOutputStream(fileName)) {
        int length;
        byte[] b = new byte[SIZE];
        while ((length = is.read(b, 0, SIZE)) != -1) {
          fos.write(b, 0, length);
        }
      }
    }
  }

  /**
   * 获取压缩文件注释
   * 
   * @param srcFile 文件
   * @return String
   */
  public static String getZipComment(String srcFile) {
    String comment = "";
    try (ZipFile zipFile = new ZipFile(srcFile)) {
      Enumeration<?> e = zipFile.getEntries();
      while (e.hasMoreElements()) {
        ZipEntry zipEntry = (ZipEntry) e.nextElement();
        comment = zipEntry.getComment();
        if (!DataUtils.isEmpty(comment) && !"null".equals(comment)) {
          break;
        }
      }
    } catch (Exception e) {
      LOGGER.error("Error getZipComment:", e);
    }

    return comment;
  }

  /**
   * 从zip中获取指定文件
   * 
   * @param zipFilePath       文件
   * @param specifiesFileName 名称
   * @return String
   */
  public static String getZipSpecifiedFileString(String zipFilePath, String specifiesFileName) {
    StringBuilder str = new StringBuilder();

    try (ZipFile zipFile = new ZipFile(zipFilePath)) {
      Enumeration<?> enu = zipFile.getEntries();
      while (enu.hasMoreElements()) {
        ZipEntry entry = (ZipEntry) enu.nextElement();
        if (entry.getName().equals(specifiesFileName) && !entry.isDirectory()) {
          try (InputStream is = zipFile.getInputStream(entry);
              InputStreamReader isr = new InputStreamReader(is, Charset.defaultCharset());
              BufferedReader in = new BufferedReader(isr)) {
            String len;
            while ((len = in.readLine()) != null) {
              str.append(len);
            }
            break;
          }
        }
      }
      return str.toString();
    } catch (IOException e) {
      LOGGER.error("Error getZipSpecifiedFileString:", e);
    }
    return null;
  }

  /**
   * 从zip中获取指定文件
   * 
   * @param zipFilePath       文件
   * @param specifiesFileName 名称
   * @return InputStream
   */
  public static InputStream getZipSpecifiedFileInputStream(String zipFilePath, String specifiesFileName) {
    try (ZipFile zipFile = new ZipFile(zipFilePath)) {
      Enumeration<?> enu = zipFile.getEntries();
      InputStream is = null;
      while (enu.hasMoreElements()) {
        ZipEntry entry = (ZipEntry) enu.nextElement();
        if (entry.getName().equals(specifiesFileName) && !entry.isDirectory()) {
          is = zipFile.getInputStream(entry);
          break;
        }
      }
      return is;
    } catch (IOException e) {
      LOGGER.error("Error getZipSpecifiedFileInputStream:", e);
    }
    return null;
  }
}

package com.oner365.deploy.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oner365.deploy.entity.DeployEntity;
import com.oner365.deploy.entity.DeployServer;
import com.oner365.deploy.entity.ServerEntity;

import ch.ethz.ssh2.Connection;

/**
 * 部署工具类
 * 
 * @author zhaoyong
 */
public class DeployMethod {

  private static final Logger LOGGER = LoggerFactory.getLogger(DeployMethod.class);
  
  private static final String FILE_LIB = "lib";
  private static final String FILE_TARGET = "target";
  private static final String FILE_RESOURCES = "resources";
  /** 分隔符 */
  private static final String DELIMITER = "/";

  private DeployMethod() {
  }

  /**
   * 停止服务
   * 
   * @param con          连接对象
   * @param targetServer 目标服务
   */
  public static void stop(Connection con, String targetServer) {
    // kill tomcat
    String cmd = "kill -9 `cat " + targetServer + ".pid 2>/dev/null` 2>/dev/null || true";
    LOGGER.info("> {}", cmd);
    List<String> cmdList = new ArrayList<>(2);
    cmdList.add(cmd);
    List<List<String>> execList = DeployUtils.execCommand(con, cmdList);
    for (List<String> list : execList) {
      for (String s : list) {
        LOGGER.info("> {}", s);
      }
    }
  }

  /**
   * 启动服务
   * 
   * @param con          连接对象
   * @param targetServer 目标服务
   */
  public static void start(Connection con, String targetServer) {
    // tomcat启动找不到java_home,需要设置 ln -s /opt/jdk1.6.0_32/bin/java /bin/java
    String cmd = targetServer + "/bin/startup.sh";
    LOGGER.info("> {}", cmd);
    List<String> cmdList = new ArrayList<>(2);
    cmdList.add(cmd);
    List<List<String>> execList = DeployUtils.execCommand(con, cmdList);
    for (List<String> list : execList) {
      for (String s : list) {
        LOGGER.info("> {}", s);
      }
    }
  }

  /**
   * 上传文件到目标服务器
   */
  public static void deploy(DeployServer server, String localFile, String targetPath) {
    // upload
    String a1 = "scp -P " + server.getPort() + " -r " + localFile + " " + server.getUsername() + "@" + server.getIp()
        + ":" + targetPath;
    LOGGER.info("> {}", a1);
    DeployUtils.execExecute(a1);
  }

  /**
   * 执行命令
   * 
   * @param con      连接对象
   * @param commands 命令
   */
  public static void execCommands(Connection con, List<String> commands) {

    List<List<String>> execList = DeployUtils.execCommand(con, commands);
    for (List<String> list : execList) {
      for (String s : list) {
        LOGGER.info("> {}", s);
      }
    }
  }

  /**
   * 打包部署到指定 目录中
   * 
   * @param projectNames 项目名称
   * @param targetRoot   目标目录
   * @param version      版本
   * @param suffix       后缀
   */
  public static void deployNative(DeployEntity deployEntity) {
    for (String projectName : deployEntity.getProjects()) {
      // jar包全路径
      String path = deployEntity.getLocation() + File.separator + projectName + File.separator + FILE_TARGET
          + File.separator + projectName + "-" + deployEntity.getVersion() + "." + deployEntity.getSuffix();
      String resourcePath = deployEntity.getLocation() + File.separator + projectName + File.separator + FILE_TARGET
          + File.separator + FILE_RESOURCES;
      String libPath = deployEntity.getLocation() + File.separator + projectName + File.separator + FILE_TARGET
          + File.separator + FILE_LIB;
      // 目标目录
      String targetPath = deployEntity.getName() + File.separator + projectName;

      // 拷贝相关文件
      DataUtils.createFolder(targetPath);
      DataUtils.copyFile(path, targetPath);
      DataUtils.copyDirectory(resourcePath, targetPath);
      DataUtils.copyDirectory(libPath, deployEntity.getName());

      // 制作 Linux 启动脚本
      String readFile = DeployMethod.class.getResource("/service/start.sh").getPath();
      String writeFile = targetPath + File.separator + "start.sh";
      Map<String, Object> items = new HashMap<>(1);
      items.put("SERVICE_NAME=", "SERVICE_NAME=" + projectName);
      items.put("VERSION=", "VERSION=" + deployEntity.getVersion());
      items.put("ACTIVE=", "ACTIVE=" + deployEntity.getActive());
      DeployUtils.replaceContextFileCreate(readFile, writeFile, items);

      // 制作 Windows 启动脚本
      readFile = DeployMethod.class.getResource("/service/start.bat").getPath();
      writeFile = targetPath + File.separator + "start.bat";
      items = new HashMap<>(1);
      items.put("RESOURCE_NAME", projectName + "-" + deployEntity.getVersion() + "." + deployEntity.getSuffix());
      DeployUtils.replaceContextFileCreate(readFile, writeFile, items);
    }

  }

  /**
   * 部署到所有服务器
   * 
   * @param deployEntity 部署对象
   * @param serverEntity 服务器对象
   */
  public static void deployServer(DeployEntity deployEntity, ServerEntity serverEntity) {
    try {
      // 多个目标进行部署
      for (DeployServer server : serverEntity.getServerList()) {
        // get connection
        Connection con = DeployUtils.getConnection(server.getIp(), server.getPort());

        // 认证
        boolean auth = DeployUtils.auth(con, server.getUsername(), server.getPassword());
        LOGGER.info("Auth : {}", auth);
        if (auth) {
          List<String> commands = deploy(con, server, deployEntity, serverEntity.getServerName());
          DeployMethod.execCommands(con, commands);
        }
        // close
        DeployUtils.close(con, null, null);
      }
    } catch (Exception e) {
      LOGGER.error("deployServer error:", e);
    }
  }

  /**
   * 部署到单台服务器
   * 
   * @param con          连接对象
   * @param server       服务器对象
   * @param deployEntity 部署对象
   * @param targetRoot   部署根目录
   * @return List<String> 返回执行脚本列表
   */
  public static List<String> deploy(Connection con, DeployServer server, DeployEntity deployEntity, String targetRoot) {
    List<String> commands = new ArrayList<>(deployEntity.getProjects().size());
    for (String projectName : deployEntity.getProjects()) {
      // 上传的文件
      String localFile = deployEntity.getLocation() + File.separator + projectName + File.separator + projectName + "-"
          + deployEntity.getVersion() + "." + deployEntity.getSuffix();
      // 上传的路径
      String targetPath = targetRoot + DELIMITER + projectName + DELIMITER;
      // 配置文件
      String resourcesFile = deployEntity.getLocation() + File.separator + projectName + File.separator + FILE_RESOURCES;
      // 依赖包上传到lib
      if (DeployUtils.isMac()) {
        // mac scp方式
        DeployMethod.deploy(server, localFile, targetPath);
        DeployMethod.deploy(server, resourcesFile, targetPath);
        
        deployMac(server, deployEntity, targetRoot);
      } else {
        // windows scp方式
        DeployUtils.uploadFileMap(con, new String[] { localFile }, targetPath);
        deployWindows(con, deployEntity, targetPath, resourcesFile, targetRoot);
      }
      // 准备执行的命令
      commands.add(targetRoot + DELIMITER + projectName + DELIMITER + "start.sh");
    }
    return commands;
  }
  
  private static void deployMac(DeployServer server, DeployEntity deployEntity, String targetRoot) {
    for (String lib : deployEntity.getLibs()) {
      DeployMethod.deploy(server,
          deployEntity.getLocation() + File.separator + FILE_LIB + File.separator + lib + "-"
              + deployEntity.getVersion() + "." + deployEntity.getSuffix(),
          targetRoot + DELIMITER + FILE_LIB + DELIMITER);
    }
  }
  
  private static void deployWindows(Connection con, DeployEntity deployEntity,
      String targetPath, String resourcesFile, String targetRoot) {
    File[] files = new File(resourcesFile).listFiles();
    for (File f : files) {
      if (!f.isDirectory()) {
        DeployUtils.uploadFileMap(con, new String[] { f.getPath() }, targetPath + FILE_RESOURCES + DELIMITER);
      }
    }
    for (String lib : deployEntity.getLibs()) {
      DeployUtils.uploadFileMap(con,
          new String[] { deployEntity.getLocation() + File.separator + FILE_LIB + File.separator + lib + "-"
              + deployEntity.getVersion() + "." + deployEntity.getSuffix() },
          targetRoot + DELIMITER + FILE_LIB + DELIMITER);
    }
  }
}

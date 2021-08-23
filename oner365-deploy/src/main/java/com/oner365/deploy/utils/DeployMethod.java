package com.oner365.deploy.utils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;

import com.oner365.common.constants.PublicConstants;
import com.oner365.deploy.entity.Server;
import com.oner365.util.DataUtils;
import com.google.common.collect.Lists;

import ch.ethz.ssh2.Connection;

/**
 * 部署工具类
 * @author zhaoyong
 */
public class DeployMethod {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeployMethod.class);

    private DeployMethod() {
    }

    /**
     * 获取对象列表
     * @return List<Server>
     */
    public static List<Server> getServerList() {
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new ClassPathResource("application.yml"));
        Properties properties = yaml.getObject();

        // 服务器信息
        List<Server> deployServerList = Lists.newArrayList();
        if (properties != null) {
            String ip = properties.get("servers.ip").toString();
            String[] ips = StringUtils.split(ip, ",");
            for (int i = 0; i < ips.length; i++) {
                Server server = new Server(ips[i],
                        Integer.parseInt(StringUtils.split(properties.get("servers.port").toString(), ",")[i]),
                        StringUtils.split(properties.get("servers.username").toString(), ",")[i],
                        StringUtils.split(properties.get("servers.password").toString(), ",")[i]);
                deployServerList.add(server);
            }
        }
        return deployServerList;
    }

    /**
     * 停止服务
     * @param con 连接对象
     * @param targetServer 目标服务
     */
    public static void stop(Connection con, String targetServer) {
        //kill tomcat
        String cmd = "kill -9 `cat "+targetServer+".pid 2>/dev/null` 2>/dev/null || true";
        LOGGER.info("> {}", cmd);
        List<List<String>> execList = DeployUtils.execCommand(con, new String[] { cmd });
        for (List<String> list : execList) {
            for (String s : list) {
                LOGGER.info("> {}", s);
            }
        }
    }

    /**
     * 启动服务
     * @param con 连接对象
     * @param targetServer 目标服务
     */
    public static void start(Connection con, String targetServer) {
        //tomcat启动找不到java_home,需要设置 ln -s /opt/jdk1.6.0_32/bin/java /bin/java
        String cmd = targetServer + "/bin/startup.sh";
        LOGGER.info("> {}", cmd);
        List<List<String>> execList = DeployUtils.execCommand(con, new String[] { cmd });
        for (List<String> list : execList) {
            for (String s : list) {
                LOGGER.info("> {}", s);
            }
        }
    }

    /**
     * 上传文件到目标服务器
     */
    public static void deploy(Server server, String localFile, String targetPath) {
        // upload
        String a1 = "scp -P " + server.getPort() + " -r " + localFile + " " + server.getUsername() + "@" + server.getIp() + ":" + targetPath;
        LOGGER.info("> {}", a1);
        DeployUtils.execExecute(a1);
    }

    /**
     * 执行命令
     * @param con 连接对象
     * @param commands 命令
     */
    public static void execCommands(Connection con, String[] commands) {

        List<List<String>> execList = DeployUtils.execCommand(con, commands);
        for (List<String> list : execList) {
            for (String s : list) {
                LOGGER.info("> {}", s);
            }
        }
    }

    /**
     * 打包部署到指定 目录中
     * @param projectNames 项目名称
     * @param targetRoot 目标目录
     * @param version 版本
     * @param suffix 后缀
     */
    public static void deployNative(String[] projectNames, String targetRoot,
            String version, String suffix, String location) {
        for (String projectName : projectNames) {
            // jar包全路径
            String path = location + File.separator + projectName + File.separator +
                    "target" + File.separator + projectName + "-" + version + "." + suffix;
            String resourcePath = location + File.separator + projectName + File.separator +
                    "target" + File.separator + "resources";
            String libPath = location + File.separator + projectName + File.separator +
                    "target" + File.separator + "lib";
            // 目标目录
            String targetPath = targetRoot + File.separator + projectName;

            // 拷贝相关文件
            DataUtils.createFolder(targetPath);
            DataUtils.copyFile(path, targetPath);
            DataUtils.copyDirectory(resourcePath, targetPath);
            DataUtils.copyDirectory(libPath, targetRoot);

            // 制作 Linux 启动脚本
            String readFile = DeployMethod.class.getResource("/service/start.sh").getPath();
            String writeFile = targetPath + File.separator + "start.sh";
            Map<String, Object> items = new HashMap<>(1);
            items.put("SERVICE_NAME=", "SERVICE_NAME=" + projectName);
            items.put("VERSION=", "VERSION=" + version);
            DeployUtils.replaceContextFileCreate(readFile, writeFile, items);

            // 制作 Windows 启动脚本
            readFile = DeployMethod.class.getResource("/service/start.bat").getPath();
            writeFile = targetPath + File.separator + "start.bat";
            items = new HashMap<>(1);
            items.put("RESOURCE_NAME", projectName + "-" + version + "." + suffix);
            DeployUtils.replaceContextFileCreate(readFile, writeFile, items);
        }

    }

    public static void deployServer(List<Server> deployServerList,
            String[] projectNames, String local, String targetRoot, String version, String suffix, String libFile) {
        try {
            // 多个目标进行部署
            for (Server server : deployServerList) {
                // get connection
                Connection con = DeployUtils.getConnection(server.getIp(), server.getPort());

                // 认证
                boolean auth = DeployUtils.auth(con, server.getUsername(), server.getPassword());
                LOGGER.info("Auth : {}", auth);
                if (auth) {
                    String[] commands = deploy(con, server, projectNames, local, targetRoot, version, suffix, libFile);
                    DeployMethod.execCommands(con, commands);
                }
                // close
                DeployUtils.close(con, null, null);
            }
        } catch (Exception e) {
            LOGGER.error("deployServer error:", e);
        }
    }

    public static String[] deploy(Connection con, Server server,
            String[] projectNames, String local, String targetRoot, String version, String suffix, String libFile) {
        String[] commands = new String[projectNames.length];
        for (int i = 0; i < projectNames.length; i++) {
            // 上传的文件
            String localFile = local + File.separator + projectNames[i] + File.separator + projectNames[i] + "-" + version + "." + suffix;
            // 上传的路径
            String targetPath = targetRoot + PublicConstants.DELIMITER + projectNames[i] + PublicConstants.DELIMITER;
            // 配置文件
            String resourcesFile = local + File.separator + projectNames[i] + File.separator + "resources";
            // 依赖包上传到lib
            String[] libFiles = StringUtils.split(libFile, ",");
            if (DeployUtils.isMac()) {
                // mac scp方式
                DeployMethod.deploy(server, localFile, targetPath);
                DeployMethod.deploy(server, resourcesFile, targetPath);
                for (String l : libFiles) {
                    DeployMethod.deploy(server, local + File.separator + "lib" + File.separator + l + "-" + version + "." + suffix,
                            targetRoot + PublicConstants.DELIMITER + "lib" + PublicConstants.DELIMITER);
                }
            } else {
                // windows scp方式
                DeployUtils.uploadFileMap(con, new String[] { localFile } , targetPath);
                File[] files = new File(resourcesFile).listFiles();
                for (File f : files) {
                    if (!f.isDirectory()) {
                        DeployUtils.uploadFileMap(con, new String[] { f.getPath() } , targetPath + "/resources" + PublicConstants.DELIMITER);
                    }
                }
                for (String l : libFiles) {
                    DeployUtils.uploadFileMap(con, new String[] { local + File.separator + "lib" + File.separator + l + "-" + version + "." + suffix },
                            targetRoot + PublicConstants.DELIMITER + "lib" + PublicConstants.DELIMITER);
                }
            }
            // 准备执行的命令
            commands[i] = targetRoot + PublicConstants.DELIMITER + projectNames[i] + PublicConstants.DELIMITER + "start.sh";
        }
        return commands;
    }
}

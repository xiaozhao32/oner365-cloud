package com.oner365.monitor.deploy;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oner365.common.constants.PublicConstants;

import ch.ethz.ssh2.Connection;

/**
 * 部署主要方法
 * @author zhaoyong
 *
 */
public class DeployMethod {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeployMethod.class);

    private DeployMethod() {

    }

    /**
     * 获取服务器对象
     * @return List<Server>
     */
    public static List<DeployServer> getServerList(List<String> ipList, List<Integer> portList,
            List<String> usernameList, List<String> passwordList) {
        // 服务器信息
        List<DeployServer> deployServerList = new ArrayList<>();

        for (int i = 0; i < ipList.size(); i++) {
            DeployServer server = new DeployServer(ipList.get(i), portList.get(i), usernameList.get(i),
                    passwordList.get(i));
            deployServerList.add(server);
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
        List<String> commandList = new ArrayList<>();
        commandList.add(cmd);
        List<List<String>> execList = DeployUtils.execCommand(con, commandList);
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
        List<String> commandList = new ArrayList<>();
        commandList.add(cmd);
        List<List<String>> execList = DeployUtils.execCommand(con, commandList);
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
        String a1 = "scp -P " + server.getPort() + " " + localFile + " " + server.getUsername() + "@" + server.getIp() + ":" + targetPath;
        LOGGER.info("> {}", a1);
        DeployUtils.execExecute(a1);
    }

    /**
     * 执行命令
     * @param con 连接对象
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
     * @param projectNames 项目名称
     * @param targetRoot 目标目录
     * @param version 版本
     * @param suffix 后缀
     */
    public static void deployNative(String[] projectNames, String targetRoot, String version, String suffix) {
        for (String projectName : projectNames) {
            List<String> commands = new ArrayList<>();
            // jar包全路径
            String path = ".." + File.separator + projectName + File.separator +
                    "target" + File.separator + projectName + "-" + version + "." + suffix;
            // 目标目录
            String targetPath = targetRoot + File.separator + projectName;

            // 创建文件夹
            String mkdirCommand = "mkdir " + targetRoot + File.separator + projectName;
            if (DeployUtils.isMac()) {
                DeployUtils.execExecute(mkdirCommand);
                commands.add("cp -rf " + path + " " + targetPath);
            } else {
                DeployUtils.execExecute("cmd /c " + mkdirCommand);
                commands.add("cmd /c copy " + path + " " + targetPath);
            }

            // 制作启动脚本
            String readFile = DeployMethod.class.getResource("/service/start.sh").getPath();
            String writeFile = targetPath + File.separator + "start.sh";
            Map<String, Object> items = new HashMap<>(1);
            items.put("RESOURCE_NAME=", "RESOURCE_NAME=" + projectName + "-" + version + "." + suffix);
            DeployUtils.replaceContextFileCreate(readFile, writeFile, items);

            for (String command : commands) {
                LOGGER.info("> {}", command);
            }
            DeployUtils.execExecuteCommand(commands);
        }
    }

    public static void deployServer(List<DeployServer> deployServerList,
            String[] projectNames, String local, String targetRoot, String version, String suffix) {
        try {
            // 多个目标进行部署
            for (DeployServer server : deployServerList) {
                // get connection
                Connection con = DeployUtils.getConnection(server.getIp(), server.getPort());

                // 认证
                boolean auth = DeployUtils.auth(con, server.getUsername(), server.getPassword());
                LOGGER.info("Auth : {}", auth);
                if (auth) {
                    List<String> commandList = deploy(con, server, projectNames, local, targetRoot, version, suffix);
                    DeployMethod.execCommands(con, commandList);
                }
                // close
                DeployUtils.close(con, null, null);
            }
        } catch (Exception e) {
            LOGGER.error("deployServer error:", e);
        }
    }

    public static List<String> deploy(Connection con, DeployServer server,
            String[] projectNames, String local, String targetRoot, String version, String suffix) {
        List<String> commandList = new ArrayList<>();
        for (String projectName : projectNames) {
            String localFile = local + File.separator + projectName + File.separator + projectName + "-" + version + "." + suffix;
            String targetPath = targetRoot + PublicConstants.DELIMITER + projectName + PublicConstants.DELIMITER;
            if (DeployUtils.isMac()) {
                // mac scp方式
                DeployMethod.deploy(server, localFile, targetPath);
            } else {
                // windows scp方式
                DeployUtils.uploadFileMap(con, new String[]{localFile}, targetPath);
            }
            // 准备执行的命令
            commandList.add(targetRoot + File.separator + projectName + File.separator + "start.sh");
        }
        return commandList;
    }
}

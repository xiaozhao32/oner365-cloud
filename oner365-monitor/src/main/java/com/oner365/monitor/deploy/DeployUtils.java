
package com.oner365.monitor.deploy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DaemonExecutor;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oner365.data.commons.constants.PublicConstants;
import com.oner365.data.commons.exception.ProjectRuntimeException;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.SFTPv3Client;
import ch.ethz.ssh2.SFTPv3DirectoryEntry;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

/**
 * 安装部署工具类
 *
 * @author zhaoyong
 *
 */
public class DeployUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeployUtils.class);

    public static final int POSIX_PERMISSIONS = 755;

    private DeployUtils() {

    }

    public static boolean isMac() {
        String os = System.getProperty("os.name");
        return !os.toLowerCase().startsWith("win");
    }

    /**
     * windows程序停止
     * @param port 端口
     */
    public static void stop(int port) {
        String s = DeployUtils.execExecute("cmd /c netstat -ano | findstr \"" + port + "\" ");
        String process = StringUtils.substringAfterLast(s, " ");
        LOGGER.info("stop:{}", process);
        if (!PublicConstants.EMPTY.equals(process)) {
            String ss = DeployUtils.execExecute("cmd /c taskkill /pid " + process + " -f");
            LOGGER.info("stop:{}", ss);
        }
    }

    /**
     * 创建连接
     * @param ip ip
     * @param port 端口
     * @return Connection 连接对象
     */
    public static Connection getConnection(String ip, int port) {
        try {
            /* Connection */
            Connection con = new Connection(ip, port);
            con.connect();
            return con;
        }
        catch (IOException e) {
            LOGGER.error("getConnection error:", e);
            throw new ProjectRuntimeException();
        }
    }

    /**
     * 获取Session
     * @param con 连接对象
     * @return Session Session对象
     */
    public static Session getSession(Connection con) {
        try {
            return con.openSession();
        }
        catch (IOException e) {
            LOGGER.error("getSession error:", e);
            throw new ProjectRuntimeException();
        }
    }

    public static SFTPv3Client getClient(Connection con) {
        try {
            return new SFTPv3Client(con);
        }
        catch (IOException e) {
            LOGGER.error("getSFTPv3Client error:", e);
            throw new ProjectRuntimeException();
        }
    }

    /**
     * 关闭连接
     * @param con 连接对象
     * @param session Session对象
     * @param sftpClient sftpClient
     */
    public static void close(Connection con, Session session, SFTPv3Client sftpClient) {
        if (sftpClient != null) {
            sftpClient.close();
        }

        if (session != null) {
            session.close();
        }

        if (con != null) {
            con.close();
        }
    }

    /**
     * 验证帐号
     * @param con 连接对象
     * @param user 账号
     * @param password 密码
     * @return boolean
     */
    public static boolean auth(Connection con, String user, String password) {
        boolean result = false;
        try {
            result = con.authenticateWithPassword(user, password);
        }
        catch (IOException e) {
            LOGGER.error("auth error:", e);
        }
        return result;
    }

    /**
     * 调用方式
     * @param commands 执行命令
     * @return List<List<String>>
     */
    public static List<List<String>> execCommand(Connection con, List<String> commands) {
        List<List<String>> result = new ArrayList<>();
        try {
            for (String s : commands) {
                LOGGER.info("> {}", s);
                Session session = getSession(con);
                if (session != null) {
                    session.execCommand(s);

                    InputStream eis = new StreamGobbler(session.getStderr());
                    List<String> list = IOUtils.readLines(eis, Charset.defaultCharset());
                    eis.close();

                    if (list.isEmpty()) {
                        InputStream is = new StreamGobbler(session.getStdout());
                        list = IOUtils.readLines(is, Charset.defaultCharset());
                        is.close();
                    }
                    result.add(list);
                    close(null, session, null);
                }
            }
        }
        catch (Exception e) {
            List<String> list = new ArrayList<>();
            list.add("ssh exec command error.");
            result.add(list);
            LOGGER.error("execCommand error:", e);
        }
        return result;
    }

    /**
     * 执行命令
     * @param commands 命令
     * @return List<String>
     */
    public static List<String> execExecute(List<String> commands) {
        if (commands == null) {
            return Collections.emptyList();
        }

        List<String> result = new ArrayList<>();
        for (String command : commands) {
            result.add(execExecute(command));
        }
        return result;
    }

    /**
     * 执行命令
     * @param command 命令
     * @return String
     */
    public static String execExecute(String command) {
        if (command == null) {
            return null;
        }

        try (ByteArrayOutputStream outStream = new ByteArrayOutputStream()) {
            CommandLine commandLine = CommandLine.parse(command);

            DefaultExecutor exec = DaemonExecutor.builder().get();
            PumpStreamHandler streamHandler = new PumpStreamHandler(outStream);
            exec.setStreamHandler(streamHandler);
            exec.execute(commandLine);
            return outStream.toString(Charset.defaultCharset());
        }
        catch (IOException e) {
            LOGGER.error("execExecute error:", e);
        }
        return null;
    }

    /**
     * 非阻塞 执行命令
     * @param commands 命令
     */
    public static void execExecuteCommand(List<String> commands) {
        if (commands == null) {
            return;
        }

        for (String command : commands) {
            execExecuteCommand(command);
        }
    }

    /**
     * 非阻塞 执行命令
     * @param command 命令
     */
    public static void execExecuteCommand(String command) {
        if (command == null) {
            return;
        }

        try (ByteArrayOutputStream outStream = new ByteArrayOutputStream()) {
            CommandLine commandLine = CommandLine.parse(command);

            DefaultExecutor exec = DaemonExecutor.builder().get();
            DefaultExecuteResultHandler handler = new DefaultExecuteResultHandler();

            PumpStreamHandler streamHandler = new PumpStreamHandler(outStream);
            exec.setStreamHandler(streamHandler);
            exec.execute(commandLine, handler);
            handler.waitFor();
        }
        catch (IOException e) {
            LOGGER.error("IOException execExecuteCommand error:", e);
        }
        catch (InterruptedException e) {
            LOGGER.error("InterruptedException execExecuteCommand error:", e);
            Thread.currentThread().interrupt();
        }
    }

    public static void uploadFileMap(Connection con, String[] localFiles, String remoteTargetDirectory) {
        try {
            SFTPv3Client sftpClient = new SFTPv3Client(con);
            boolean isDirExists = forceDir(sftpClient, remoteTargetDirectory);
            LOGGER.info("> Directory: {} is not create: {}", remoteTargetDirectory, isDirExists);
            SCPClient scpClient = con.createSCPClient();
            LOGGER.info("> {} -> {}", localFiles, remoteTargetDirectory);
            scpClient.put(localFiles, remoteTargetDirectory);

            sftpClient.close();
        }
        catch (IOException e) {
            LOGGER.error("uploadFileMap error:", e);
        }
    }

    /**
     * 查询目录
     * @param sftpClient 对象
     * @param directory 目录
     * @return boolean
     */
    public static boolean ls(SFTPv3Client sftpClient, String directory) {
        try {
            sftpClient.ls(directory);
            return true;
        }
        catch (IOException e) {
            // not exception
        }
        return false;
    }

    /**
     * 查询目录
     * @param ip ip
     * @param port 端口
     * @param user 账号
     * @param password 密码
     * @param directory 目录
     * @return boolean
     */
    @SuppressWarnings("unchecked")
    public static List<SFTPv3DirectoryEntry> directoryList(String ip, int port, String user, String password,
            String directory) {
        List<SFTPv3DirectoryEntry> result = new ArrayList<>();
        try {
            Connection con = DeployUtils.getConnection(ip, port);
            SFTPv3Client sftpClient = null;
            if (DeployUtils.auth(con, user, password)) {
                sftpClient = DeployUtils.getClient(con);
                result = sftpClient.ls(directory);
            }
            DeployUtils.close(con, null, sftpClient);
        }
        catch (IOException e) {
            LOGGER.error("directoryList error:", e);
        }
        return result;
    }

    /**
     * 创建目录
     * @param sftpClient 对象
     * @param directory 目录
     * @return boolean
     */
    public static boolean forceDir(SFTPv3Client sftpClient, String directory) {
        boolean result = ls(sftpClient, directory);
        if (!result) {
            try {
                sftpClient.mkdir(directory, POSIX_PERMISSIONS);
                return true;
            }
            catch (IOException e) {
                LOGGER.error("Error mkdir:", e);
            }
        }
        return false;
    }

    /**
     * 替换文件内容后生成文件 替换内容为空时直接生成(相当于拷贝文件)
     * @param readFile 读取文件路径
     * @param writeFile 写入文件路径
     * @param items 替换模板内容：key替换成value, value不能为null, 可以为空字符串
     * @return boolean
     */
    public static boolean replaceContextFileCreate(String readFile, String writeFile, Map<String, Object> items) {
        try (FileInputStream fis = new FileInputStream(readFile)) {
            writeFile(fis, writeFile, items);
            LOGGER.debug("createFile success: {}", writeFile);
            return true;
        }
        catch (IOException e) {
            LOGGER.error("replaceContextFileCreate Error!", e);
        }
        return false;
    }

    private static void writeFile(InputStream is, String writeFile, Map<String, Object> items) {
        try (InputStreamReader isr = new InputStreamReader(is, Charset.defaultCharset());
                BufferedReader in = new BufferedReader(isr);
                FileOutputStream fos = new FileOutputStream(writeFile);
                OutputStreamWriter osw = new OutputStreamWriter(fos, Charset.defaultCharset());
                BufferedWriter out = new BufferedWriter(osw)) {

            // 替换对象
            String s;
            while ((s = in.readLine()) != null) {
                String context = replaceContext(items, s);
                out.write(context);
                out.newLine();
            }
            out.flush();
        }
        catch (IOException e) {
            LOGGER.error("writeFile Error!", e);
        }

    }

    /**
     * 替换内容
     * @param items 内容
     * @param s 结果
     */
    private static String replaceContext(Map<String, Object> items, String s) {
        String result = s;
        if (items != null) {
            for (Map.Entry<String, Object> entry : items.entrySet()) {
                final String key = entry.getKey();
                String value = StringUtils.EMPTY;
                if (items.get(key) != null) {
                    value = items.get(key).toString();
                }
                result = result.replace(key, value);
            }
        }
        return result;
    }

}

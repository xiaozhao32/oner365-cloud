package com.oner365.monitor.controller.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import com.alibaba.fastjson.JSONObject;
import com.oner365.common.enums.ResultEnum;
import com.oner365.controller.BaseController;
import com.oner365.monitor.deploy.DeployMethod;
import com.oner365.monitor.deploy.DeployServer;
import com.oner365.monitor.deploy.DeployUtils;
import com.oner365.util.DataUtils;

import ch.ethz.ssh2.Connection;
import reactor.core.publisher.Mono;

/**
 * 服务监控
 *
 * @author zhaoyong
 */
@RestController
@RequestMapping("/service")
public class ServiceController extends BaseController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Value("${spring.cloud.client.ip-address}")
    private String hostname;

    @Value("${servers.deploy}")
    private boolean deploy;

    @Value("${servers.path}")
    private String serverPath;

    @Value("${servers.version}")
    private String version;

    @Value("${servers.suffix}")
    private String suffix;

    @Value("#{'${servers.ip}'.split(',')}")
    private List<String> ipList;

    @Value("#{'${servers.port}'.split(',')}")
    private List<Integer> portList;

    @Value("#{'${servers.username}'.split(',')}")
    private List<String> usernameList;

    @Value("#{'${servers.password}'.split(',')}")
    private List<String> passwordList;

    private static final String COMMAND_START = "start.sh";

    /**
     * 基本信息
     * @return List<List<ServiceInstance>>
     */
    @GetMapping("/index")
    public List<List<ServiceInstance>> index() {
        List<List<ServiceInstance>> serviceList = new ArrayList<>();
        // 获取服务名称
        List<String> serviceNameList = discoveryClient.getServices();
        for (String serviceName : serviceNameList) {
            // 获取服务中的实例列表
            List<ServiceInstance> serviceInstances = discoveryClient.getInstances(serviceName);
            serviceList.add(serviceInstances);
        }
        return serviceList;
    }

    /**
     * 动态刷新配置
     * @return String
     */
    @GetMapping("/refreshConfig")
    public String refreshConfig() {
        return ResultEnum.SUCCESS.getName();
    }

    /**
     * 获取信息
     * @param paramJson 属性
     * @return JSONObject
     */
    @PostMapping("/getActuatorEnv")
    public JSONObject getActuatorEnv(@RequestBody JSONObject paramJson) {
        ClientHttpConnector httpConnector = new ReactorClientHttpConnector();
        WebClient client = WebClient.builder().clientConnector(httpConnector).baseUrl(paramJson.getString("uri")).build();
        
        String uri = "/actuator/env";
        Mono<JSONObject> mono = client.get().uri(uri)
                .retrieve().bodyToMono(JSONObject.class);
        return mono.block();
    }

    /**
     * 上传服务
     *
     * @param multipartFile 文件
     * @return String
     */
    @PostMapping("/uploadService")
    public String uploadService(@RequestParam("multipartFile") MultipartFile multipartFile,
            String ip, int port, String serviceName) {
        String fileName = serverPath + File.separator + multipartFile.getOriginalFilename();
        try {
            DataUtils.deleteFile(fileName);
            multipartFile.transferTo(new File(fileName));

            if (deploy) {
                // 服务器部署
                List<DeployServer> serverList = DeployMethod.getServerList(ipList, portList, usernameList, passwordList);
                for (DeployServer server : serverList) {
                    if (ip.equals(server.getIp())) {
                        List<String> commandList = new ArrayList<>();

                        // get connection
                        Connection con = DeployUtils.getConnection(server.getIp(), server.getPort());
                        // 认证
                        boolean auth = DeployUtils.auth(con, server.getUsername(), server.getPassword());
                        LOGGER.info("Auth : {}", auth);
                        if (auth) {
                            String path = serverPath + File.separator + serviceName.toLowerCase();
                            if (!hostname.equals(server.getIp())) {
                                // 上传到对应机器
                                DeployMethod.deploy(server, fileName, path);
                                // 重启服务
                                String cmd = path + File.separator + COMMAND_START;
                                commandList.add(cmd);
                                DeployMethod.execCommands(con, commandList);
                            } else {
                                // 本地文件删除后移动到目录
                                DataUtils.deleteFile(new File(path) + File.separator + multipartFile.getOriginalFilename());
                                FileUtils.moveToDirectory(new File(fileName), new File(path), true);

                                // 重启服务
                                String cmd = path + File.separator + COMMAND_START;
                                DeployUtils.execExecuteCommand(cmd);
                            }

                        }
                        // close
                        DeployUtils.close(con, null, null);
                    }
                }
            } else {
                // 本地部署
                String target = serverPath + File.separator + serviceName.toLowerCase();
                FileUtils.moveFileToDirectory(new File(fileName), new File(target), true);

                if (DeployUtils.isMac()) {
                    String cmd = serverPath + File.separator + serviceName.toLowerCase() + File.separator + COMMAND_START;
                    DeployUtils.execExecuteCommand(cmd);
                } else {
                    DeployUtils.stop(port);
                    String cmd = "cmd /c start " + serverPath + File.separator + serviceName.toLowerCase() + File.separator + "start.bat";
                    DeployUtils.execExecuteCommand(cmd);
                }
            }
        } catch (IOException e) {
            LOGGER.error("Error uploadService:", e);
        }
        return ResultEnum.SUCCESS.getName();
    }

    /**
     * 重启服务
     * @param deployServer 对象
     * @return String
     */
    @PostMapping("/resetService")
    public String resetService(@RequestBody DeployServer deployServer) {

        if (deploy) {
            // 服务器部署
            List<DeployServer> serverList = DeployMethod.getServerList(ipList, portList, usernameList, passwordList);
            serverList.forEach(server -> {
                if (deployServer.getIp().equals(server.getIp())) {
                    List<String> commandList = new ArrayList<>();

                    // get connection
                    Connection con = DeployUtils.getConnection(server.getIp(), server.getPort());
                    // 认证
                    boolean auth = DeployUtils.auth(con, server.getUsername(), server.getPassword());
                    LOGGER.info("Auth : {}", auth);
                    if (auth) {
                        String cmd = serverPath + File.separator + deployServer.getServiceName().toLowerCase() + File.separator + COMMAND_START;
                        commandList.add(cmd);
                        DeployMethod.execCommands(con, commandList);
                    }
                    // close
                    DeployUtils.close(con, null, null);
                }
            });
        } else {
            // 本地部署
            if (DeployUtils.isMac()) {
                String cmd = serverPath + File.separator + deployServer.getServiceName().toLowerCase() + File.separator + COMMAND_START;
                DeployUtils.execExecuteCommand(cmd);
            } else {
                DeployUtils.stop(deployServer.getPort());
                String cmd = "cmd /c start " + serverPath + File.separator + deployServer.getServiceName().toLowerCase() + File.separator + "start.bat";
                DeployUtils.execExecuteCommand(cmd);
            }
        }
        return ResultEnum.SUCCESS.getName();
    }

}

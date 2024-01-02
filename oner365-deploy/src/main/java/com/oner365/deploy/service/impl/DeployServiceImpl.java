package com.oner365.deploy.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.oner365.deploy.config.properties.LocalDeployProperties;
import com.oner365.deploy.config.properties.ServerDeployProperties;
import com.oner365.deploy.entity.DeployEntity;
import com.oner365.deploy.entity.DeployServer;
import com.oner365.deploy.entity.ServerEntity;
import com.oner365.deploy.service.DeployService;

/**
 * 部署服务
 * 
 * @author zhaoyong
 *
 */
@Service
public class DeployServiceImpl implements DeployService {
  
  /** 冒号 */
  private static final String COLON = ":";
  
  @Resource
  private LocalDeployProperties localDeployProperties;
  @Resource
  private ServerDeployProperties serverDeployProperties;

  @Override
  public DeployEntity getDeployEntity() {
    DeployEntity result = new DeployEntity();
    result.setLocation(localDeployProperties.getLocation());
    result.setName(localDeployProperties.getName());
    result.setSuffix(localDeployProperties.getSuffix());
    result.setVersion(localDeployProperties.getVersion());
    
    List<String> projects = new ArrayList<>();
    Map<String, Integer> proejctPorts = new HashMap<>();
    localDeployProperties.getProjects().forEach(project -> {
        String name = StringUtils.substringBefore(project, COLON);
        String portString = StringUtils.substringAfter(project, COLON);
        Integer port = StringUtils.isEmpty(portString) == true ? 0 : Integer.parseInt(portString);
        
        projects.add(name);
        proejctPorts.put(name, port);
    });
    result.setProjects(projects);
    result.setProejctPorts(proejctPorts);
    
    result.setActive(localDeployProperties.getActive());
    result.setLibs(localDeployProperties.getLibs());
    return result;
  }

  @Override
  public ServerEntity getServerEntity() {
    ServerEntity result = new ServerEntity();
    result.setIsDeploy(serverDeployProperties.getDeploy());
    result.setServerName(serverDeployProperties.getName());
    List<DeployServer> serverList = new ArrayList<>();
    List<String> servers = serverDeployProperties.getServers();
    for (String server: servers) {
      String[] splitServer = StringUtils.split(server, "@");
      if (splitServer.length > 1) {
        DeployServer deployServer = new DeployServer();
        deployServer.setIp(StringUtils.substringBefore(splitServer[0], COLON));
        deployServer.setPort(Integer.parseInt(StringUtils.substringAfter(splitServer[0], COLON)));
        deployServer.setUsername(StringUtils.substringBefore(splitServer[1], COLON));
        deployServer.setPassword(StringUtils.substringAfter(splitServer[1], COLON));
        serverList.add(deployServer);
      }
    }
    result.setServerList(serverList);
    return result;
  }

}

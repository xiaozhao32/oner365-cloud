package com.oner365.deploy;

import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;

import com.oner365.deploy.entity.Server;
import com.oner365.deploy.utils.DeployMethod;
import com.google.common.collect.Lists;

/**
 * 自动化本地项目
 *
 * 部署到本地：请先执行 maven build 生成jar到 target 下在运行
 *
 * @author zhaoyong
 *
 */
public class DeployServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeployServer.class);

    /**
     * @param args 测试
     */
    public static void main(String[] args) {
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new ClassPathResource("application.yml"));
        Properties properties = yaml.getObject();

        String project = properties.get("deploy.project").toString();
        String[] projectNames = StringUtils.split(project, ",");

        // 服务器信息
        String ip = properties.get("servers.ip").toString();
        String[] ips = StringUtils.split(ip, ",");
        List<Server> deployServerList = Lists.newArrayList();
        for (int i = 0; i < ips.length; i++) {
            Server server = new Server(ips[i],
                    Integer.parseInt(StringUtils.split(properties.get("servers.port").toString(), ",")[i]),
                    StringUtils.split(properties.get("servers.username").toString(), ",")[i],
                    StringUtils.split(properties.get("servers.password").toString(), ",")[i]);
            deployServerList.add(server);
        }
        LOGGER.info("Deploy Server name: {}", properties.getProperty("servers.name"));
        LOGGER.info("Deploy Server project: {}", deployServerList);
        // 部署服务器目录
        DeployMethod.deployServer(deployServerList, projectNames, properties.getProperty("deploy.name"),
                properties.getProperty("servers.name"), properties.getProperty("deploy.version"),
                properties.getProperty("deploy.suffix"), properties.getProperty("deploy.lib"));
    }

}

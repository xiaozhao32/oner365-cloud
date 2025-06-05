package com.oner365.zookeeper.service.impl;

import java.util.Collections;
import java.util.List;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

import com.oner365.data.commons.util.DataUtils;
import com.oner365.zookeeper.constants.ZookeeperConstants;
import com.oner365.zookeeper.service.IZookeeperService;
import com.oner365.zookeeper.vo.PathNodeVo;

/**
 * Zookeeper Service Impl
 *
 * @author zhaoyong
 *
 */
@Service
public class ZookeeperServiceImpl implements IZookeeperService {

    private final Logger logger = LoggerFactory.getLogger(ZookeeperServiceImpl.class);

    @Value("${spring.application.name}")
    private String name;

    @Resource
    private DiscoveryClient discoveryClient;

    @Resource
    private CuratorFramework client;

    @PreDestroy
    public void destory() {
        logger.info("destroy Zookeeper client.");
        client.close();
    }

    @Override
    public List<ServiceInstance> instanceList() {
        List<ServiceInstance> list = discoveryClient.getInstances(name);
        logger.info("ServiceInstance List: {}", list.size());
        return list;
    }

    @Override
    public String createNode(PathNodeVo vo, CreateMode createMode) {
        try {
            String path = ZookeeperConstants.ROOT_PATH + vo.getPath();
            logger.info("createNode path:{}", path);

            if (DataUtils.isEmpty(vo.getValue())) {
                client.create().creatingParentsIfNeeded().withMode(createMode).forPath(path);
            }
            else {
                client.create().creatingParentsIfNeeded().withMode(createMode).forPath(path, vo.getValue().getBytes());
            }
            return vo.getPath();
        }
        catch (Exception e) {
            logger.error("createNode error", e);
        }
        return null;
    }

    @Override
    public String updateNode(PathNodeVo vo) {
        try {
            String path = ZookeeperConstants.ROOT_PATH + vo.getPath();
            client.setData().forPath(path, vo.getValue().getBytes());
            return vo.getPath();
        }
        catch (Exception e) {
            logger.error("updateNode error", e);
        }
        return null;
    }

    @Override
    public String getNode(String nodePath) {
        try {
            String path = ZookeeperConstants.ROOT_PATH + nodePath;
            byte[] bytes = client.getData().forPath(path);
            if (bytes.length > 0) {
                return new String(bytes);
            }
        }
        catch (Exception e) {
            logger.error("getNode error", e);
        }
        return null;
    }

    @Override
    public Boolean checkNode(String nodePath) {
        try {
            String path = ZookeeperConstants.ROOT_PATH + nodePath;
            Stat stat = client.checkExists().forPath(path);
            if (stat != null) {
                return Boolean.TRUE;
            }
        }
        catch (Exception e) {
            logger.error("checkNode error", e);
        }
        return Boolean.FALSE;
    }

    @Override
    public Boolean deleteNode(String nodePath) {
        try {
            String path = ZookeeperConstants.ROOT_PATH + nodePath;
            client.delete().deletingChildrenIfNeeded().forPath(path);
            return Boolean.TRUE;
        }
        catch (Exception e) {
            logger.error("deleteNode error", e);
        }
        return Boolean.FALSE;
    }

    @Override
    public List<ACL> getAcl(String nodePath) {
        try {
            String path = ZookeeperConstants.ROOT_PATH + nodePath;
            return client.getACL().forPath(path);
        }
        catch (Exception e) {
            logger.error("getAcl error", e);
        }
        return Collections.emptyList();
    }

}

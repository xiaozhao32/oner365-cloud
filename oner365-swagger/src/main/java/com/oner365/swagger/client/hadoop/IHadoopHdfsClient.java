package com.oner365.swagger.client.hadoop;

import org.springframework.cloud.openfeign.FeignClient;

import com.oner365.swagger.constants.PathConstants;

/**
 * Hadoop服务 - Hdfs
 * 
 * @author zhaoyong
 *
 */
@FeignClient(value = PathConstants.FEIGN_CLIENT_HADOOP, contextId = PathConstants.CONTEXT_HADOOP_HDFS_ID)
public interface IHadoopHdfsClient {

}

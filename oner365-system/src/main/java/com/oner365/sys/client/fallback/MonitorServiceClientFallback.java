package com.oner365.sys.client.fallback;

import org.springframework.stereotype.Component;

import com.oner365.api.rabbitmq.dto.SysTaskDto;
import com.oner365.data.commons.enums.ErrorInfoEnum;
import com.oner365.data.commons.enums.ResultEnum;
import com.oner365.data.commons.reponse.ResponseData;
import com.oner365.sys.client.IMonitorServiceClient;

/**
 * 监听服务回调
 * 
 * @author zhaoyong
 */
@Component
public class MonitorServiceClientFallback implements IMonitorServiceClient {

  @Override
  public ResponseData<SysTaskDto> getInfo(String id) {
    return new ResponseData<>(ResultEnum.ERROR.getCode(), ErrorInfoEnum.PARAM.getName());
  }

}

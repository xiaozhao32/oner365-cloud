package com.oner365.sys.client.fallback;

import org.springframework.stereotype.Component;

import com.oner365.common.ResponseData;
import com.oner365.common.enums.ErrorInfoEnum;
import com.oner365.common.enums.ResultEnum;
import com.oner365.sys.client.IMonitorServiceClient;

/**
 * 监听服务回调
 * @author zhaoyong
 */
@Component
public class MonitorServiceClientFallback implements IMonitorServiceClient {

    @Override
    public ResponseData<Object> getInfo(String id) {
        return new ResponseData<>(ResultEnum.ERROR.getOrdinal(), ErrorInfoEnum.PARAM.getName());
    }


}

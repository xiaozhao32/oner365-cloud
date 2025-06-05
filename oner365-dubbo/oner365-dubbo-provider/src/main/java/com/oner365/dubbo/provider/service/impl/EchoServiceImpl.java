package com.oner365.dubbo.provider.service.impl;

import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.RpcContext;

import com.oner365.dubbo.api.service.IEchoService;

/**
 * dubbo 接收者
 *
 * @author zhaoyong
 */
@DubboService(group = "${dubbo.provider.group}", version = "${dubbo.provider.version}")
public class EchoServiceImpl implements IEchoService {

    @Override
    public String echo(String message) {
        RpcContext rpcContext = RpcContext.getServiceContext();
        return String.format("Service [port : %d] %s : Hello %s", rpcContext.getLocalPort(), rpcContext.getMethodName(),
                message);
    }

}

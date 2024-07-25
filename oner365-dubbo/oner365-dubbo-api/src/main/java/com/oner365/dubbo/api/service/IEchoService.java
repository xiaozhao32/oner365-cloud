package com.oner365.dubbo.api.service;

import javax.validation.constraints.NotEmpty;

/**
 * Echo Service
 * @author zhaoyong
 *
 */
public interface IEchoService {

    /**
     * echo
     * @param message data
     * @return String 
     */
    String echo(@NotEmpty String message);
}

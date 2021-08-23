package com.oner365.dubbo.api.service;

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
    String echo(String message);
}

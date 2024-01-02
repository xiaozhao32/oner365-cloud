package com.oner365.data.web.sequence.exception;

/**
 * sequence exception
 *
 * @author zhaoyong
 */
public class SeqException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;

    /**
     * 异常信息
     * 
     * @param message 消息
     */
    public SeqException(String message) {
        super(message);
    }

    /**
     * 异常信息
     * 
     * @param cause 异常
     */
    public SeqException(Throwable cause) {
        super(cause);
    }
}

package com.oner365.data.commons.exception;

/**
 * 运行时异常
 * @author zhaoyong
 */
public class ProjectRuntimeException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public ProjectRuntimeException() {
        super();
    }
    
    public ProjectRuntimeException(String message) {
        super(message);
    }
    
    public ProjectRuntimeException(Exception e) {
        super(e);
    }
    
    public ProjectRuntimeException(String message, Exception e) {
        super(message, e);
    }
    
}

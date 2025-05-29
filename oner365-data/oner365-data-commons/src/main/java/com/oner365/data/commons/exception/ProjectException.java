package com.oner365.data.commons.exception;

/**
 * 异常信息
 *
 * @author zhaoyong
 */
public class ProjectException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public ProjectException() {
        super();
    }

    public ProjectException(String message) {
        super(message);
    }

    public ProjectException(Exception e) {
        super(e);
    }

    public ProjectException(String message, Exception e) {
        super(message, e);
    }

}

package com.oner365.data.web.advice;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.oner365.data.commons.reponse.ResponseData;
import com.oner365.data.commons.util.DataUtils;
import com.oner365.data.web.entity.GatewayError;

/**
 * 异常处理
 *
 * @author zhaoyong
 *
 */
@ResponseBody
@ControllerAdvice
public class RestExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestExceptionHandler.class);

    private static final String ERROR_MESSAGE = "服务器异常，请联系管理员!";

    /**
     * 构造方法
     */
    public RestExceptionHandler() {
        super();
    }

    /**
     * 请求参数异常处理
     * @param request 请求对象
     * @param e 异常
     * @return ResponseData
     */
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseData<GatewayError> handleMethodArgumentNotValidException(HttpServletRequest request,
            MethodArgumentNotValidException e) {
        LOGGER.error("[参数异常] 请求路径:{}, 异常信息:{}", request.getRequestURI(), e);
        if (DataUtils.isEmpty(e.getBindingResult())) {
            return ResponseData.error(null, HttpStatus.BAD_REQUEST.value(), ERROR_MESSAGE);
        }

        FieldError fieldError = e.getBindingResult().getFieldError();
        String message = fieldError == null ? ERROR_MESSAGE : fieldError.getDefaultMessage();
        GatewayError result = getErrorAttributes(HttpStatus.BAD_REQUEST, request, message);
        return ResponseData.error(result, HttpStatus.BAD_REQUEST.value(), message);
    }

    /**
     * 异常处理
     * @param request 请求对象
     * @param e 异常
     * @return ResponseData
     */
    @ExceptionHandler(Exception.class)
    public ResponseData<GatewayError> exceptionHandler(HttpServletRequest request, Exception e) {
        LOGGER.error("[网关异常] 请求路径:{}, 异常信息:{}", request.getRequestURI(), e);
        GatewayError result = getErrorAttributes(HttpStatus.INTERNAL_SERVER_ERROR, request, e.getMessage());
        return ResponseData.error(result, HttpStatus.INTERNAL_SERVER_ERROR.value(), ERROR_MESSAGE);
    }

    private GatewayError getErrorAttributes(HttpStatus httpStatus, HttpServletRequest request, String message) {
        GatewayError result = new GatewayError();
        result.setCode(httpStatus.value());
        result.setMethod(request.getMethod());
        result.setPath(request.getRequestURI());

        result.setMessage(message);
        result.setResult(ERROR_MESSAGE);
        return result;
    }

}

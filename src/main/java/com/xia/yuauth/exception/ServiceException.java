package com.xia.yuauth.exception;

/**
 * description: 业务发生异常
 *
 * @author Andy
 * @version 1.0
 * @date 07/11/2020 16:39
 */
public class ServiceException extends RuntimeException {

    public ServiceException(Throwable cause, String message, Object... args) {
        super(String.format(message, args), cause);
    }

    public ServiceException(String message, Object... args){
        super(String.format(message, args));
    }
}

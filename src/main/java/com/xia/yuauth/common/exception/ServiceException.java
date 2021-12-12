package com.xia.yuauth.common.exception;

import com.xia.yuauth.controller.web.vo.Result;

/**
 * description: 业务发生异常
 *
 * @author Andy
 * @version 1.0
 * @date 07/11/2020 16:39
 */
public class ServiceException extends RuntimeException {

    private Result<Object> result;

    public ServiceException(Throwable cause, String message, Object... args) {
        super(String.format(message, args), cause);
    }

    public ServiceException(String message, Object... args) {
        super(String.format(message, args));
    }

    public ServiceException(Result<Object> result) {
        super();
        this.result = result;
    }

    public ServiceException(Result<Object> result, String message, Object... args) {
        super(String.format(message, args));
        this.result = result;
    }

    public Result<Object> getResult() {
        return result;
    }

    public void setResult(Result<Object> result) {
        this.result = result;
    }
}

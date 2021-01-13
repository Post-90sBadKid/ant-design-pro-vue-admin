package com.wry.common.result;


import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * @author cjbi@outlook.com
 */

public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean success;

    private Integer code;

    private String message;

    private T result;

    private static final Result EMPTY_SUCCESS_RESULT = Result.success(null);

    public static <T> Result<T> success() {
        return EMPTY_SUCCESS_RESULT;
    }

    public static <T> Result<T> success(T obj) {
       Result result= new Result<T>();
       result.setResult(obj);
       result.setCode(RestResultStatus.OK.getCode());
       result.setMessage(RestResultStatus.OK.getMessage());
       result.setSuccess(true);
       return result;
    }

    public static Result success(String msg) {
        Result result= new Result();
        result.setCode(RestResultStatus.OK.getCode());
        result.setMessage(msg);
        result.setResult(null);
        result.setSuccess(true);
        return result;
    }

    public static Result failure(ResultStatus resultStatus) {
        Result result= new Result<>();
        result.setCode(resultStatus.getCode());
        result.setMessage(resultStatus.getMessage());
        result.setSuccess(false);
        return result;
    }

    public static Result failure(ResultStatus resultStatus, Throwable e) {
        Result result= new Result<>();
        result.setCode(resultStatus.getCode());
        result.setMessage(resultStatus.getMessage());
        result.setResult(e);
        result.setSuccess(false);
        return result;
    }

    public static Result failure(ResultStatus resultStatus, String message) {
        Result result= new Result<>();
        result.setCode(resultStatus.getCode());
        result.setMessage(StringUtils.hasLength(message)?message:resultStatus.getMessage());
        result.setSuccess(false);
        return result;
    }

    public static Result failure(Integer code,String message) {
        Result result= new Result<>();
        result.setCode(code);
        result.setMessage(message);
        result.setSuccess(false);
        return result;
    }



    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public static Result getEmptySuccessResult() {
        return EMPTY_SUCCESS_RESULT;
    }
}

package com.wry.common.exception;


import com.wry.common.result.ResultStatus;

/**
 * <p>
 * 自定义业务异常
 * </p>
 *
 * @author wangruiyu
 * @since 2020/12/29
 */
public class BusinessException extends RuntimeException {
    /**
     * 异常状态码
     */
    private Integer code;
    /**
     * 异常信息
     */
    private String msg;

    public BusinessException(ResultStatus resultEnum) {
        //不生成栈追踪信息
        super(resultEnum.getMessage(), null, false, false);
        this.code = resultEnum.getCode();
        this.msg = resultEnum.getMessage();
    }

    public BusinessException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

package com.wry.common.result;

/**
 * <p>
 * 结果状态接口
 * </p>
 *
 * @author wangruiyu
 * @since 2020/12/29
 */
public interface ResultStatus {
    /**
     * 错误码
     */
    int getCode();

    /**
     * 错误信息
     */
    String getMessage();

}

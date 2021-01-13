package com.wry.common.result;


/**
 * <p>
 * REST风格结果状态枚举类
 * </p>
 *
 * @author wangruiyu
 * @since 2020/12/29
 */
public enum RestResultStatus implements ResultStatus {

    /**
     * 操作成功
     */
    OK(200, "成功"),
    /**
     * 认证异常
     */
    AUTHENTICATION_ERROR(401, "请登录"),

    /**
     * 授权异常
     */
    AUTHENTIZATION_ERROR(403, "无权限"),
    /**
     * 参数非法
     */
    PARAM_ERROR(1001, "参数非法"),
    /**
     * 登录名或密码错误
     */
    ACCOUNT_OR_PASSWORD_ERROR(1002, "登录名或密码错误"),
    /**
     * 账户已失效
     */
    ACCOUNT_EXPIRED(1003, "账户已失效"),
    /**
     * 账户未启用
     */
    ACCOUNT_NON_ENABLED(1004, "账户未启用"),
    /**
     * 账户已锁定
     */
    ACCOUNT_LOCKED(1005, "账户已锁定"),
    /**
     * 密码已失效
     */
    PASSWORD_EXPIRED(1006, "密码已失效"),
    /**
     * 该用户已存在
     */
    FAILED_USER_ALREADY_EXIST(1007, "该用户已存在"),
    /**
     * 当前未登录用户
     */
    NOT_CURRENT_LOGIN_USER(1008, "当前未登录用户"),
    /**
     * 服务异常
     */
    INTERNAL_SERVER_ERROR(1009, "服务异常"),
    /**
     * 不能删除自己
     */
    FAILED_DEL_OWN(1010, "不能删除自己"),
    /**
     * 不能禁用自己
     */
    FAILED_LOCK_OWN(1012, "不能禁用自己"),
    /**
     * 未授权
     */
    UNAUTHORIZED(1013, "未授权"),
    /**
     * 未选择需上传的文件
     */
    NO_FILE_SELECTED(10014, "未选择需上传的文件"),
    /**
     * 生成服务端路由失败
     */
    GENERATE_SERVICE_PERMISSION_ERROR(10015, "生成服务端路由失败"),
    /**
     * 文件内容为空
     */
    FILE_CONTENT_IS_EMPTY(10016, "文件内容为空"),


    ;

    private int code;
    private String message;

    RestResultStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

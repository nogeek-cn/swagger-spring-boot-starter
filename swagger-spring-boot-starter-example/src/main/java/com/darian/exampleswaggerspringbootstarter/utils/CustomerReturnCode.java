package com.darian.exampleswaggerspringbootstarter.utils;

/***
 * @author Darian
 */
public enum CustomerReturnCode implements IReturnCode {

    SUCCESS("ALL0000", "成功"),

    NEED_LOGIN("0001", "请去登陆"),

    // M：表示数据信息/处理错误类
    INPUT_VALIDATION_Exception("0002", "输入参数有误"),

    DATA_Error("0003", "数据操作异常"),

    SERVICE_UNKNOWN_EXCEPTION("0004", "系统繁忙"),

    APPLICATION_EXCEPTION("0005", "系统业务异常"),

    REMOTE_INVOKE_EXCEPTION("0006", "远程服务调用异常！"),;

    private String code;
    private String msg;
    private String CURRENT_APP_SYSTEM_ID = "COM";

    CustomerReturnCode(String code, String msg) {
        if ("ALL0000".equals(code)) {
            this.code = code;
        } else {
            this.code = this.CURRENT_APP_SYSTEM_ID + code;
        }
        this.msg = msg;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

}

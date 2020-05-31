package com.darian.exampleswaggerspringbootstarter.utils;


/**
 *
 * @author Darian
 */
public class ServiceException extends RuntimeException {
    private static final long serialVersionUID = -3255566884327078816L;
    private IReturnCode errCodeEnum;
    private String errCode;
    private String errMsg;


    public ServiceException(IReturnCode serviceReturnCode) {
        this(serviceReturnCode, null, null);
    }


    public ServiceException(IReturnCode serviceReturnCode, Throwable cause) {
        this(serviceReturnCode, null, cause);
    }

    public ServiceException(IReturnCode serviceReturnCode, String overrideErrorMsg) {
        this(serviceReturnCode, overrideErrorMsg, null);
    }

    public ServiceException(Throwable cause) {
        this(CustomerReturnCode.SERVICE_UNKNOWN_EXCEPTION, cause);
    }


    public ServiceException(IReturnCode serviceReturnCode, String overrideErrorMsg,
                            Throwable cause) {
        if (null == serviceReturnCode) {
            serviceReturnCode = CustomerReturnCode.SERVICE_UNKNOWN_EXCEPTION;
        }
        this.errCodeEnum = serviceReturnCode;
        this.errCode = serviceReturnCode.getCode();
        this.errMsg = null != overrideErrorMsg ? overrideErrorMsg : serviceReturnCode.getMsg();
    }

    public IReturnCode getErrCodeEnum() {
        return errCodeEnum;
    }

    public String getErrCode() {
        return errCode;
    }

    public String getErrMsg() {
        return null != errMsg ? errMsg : getMessage();
    }

}

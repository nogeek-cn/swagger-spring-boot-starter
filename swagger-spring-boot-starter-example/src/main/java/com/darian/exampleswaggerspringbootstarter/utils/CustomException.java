package com.darian.exampleswaggerspringbootstarter.utils;

public class CustomException extends RuntimeException {

    /***
     *
     * when you use the method,
     * exceptionMessage while show on app and pop
     * {@link ServiceException)}
     * Example:
     * {
     *     code: "0005",
     *     msg: message,
     *     data: {...},
     *     time: xxxxxxxxxx
     * }
     *
     * @param message
     */
    public static ServiceException ServiceException(String message) {
        return new ServiceException(new IReturnCode() {
            @Override
            public String getMsg() {
                return message;
            }
        });
    }

    /***
     * <p>when you use this method ,
     * <p>exceptionMessage while show on returndata.errorMessage
     * <p>"系统业务异常" while pop on app
     * <p>{@link ServiceException}
     * <p>While return
     * <p>{
     * <p>    code: "0005",
     * <p>    msg: "系统业务异常",
     * <p>    data: {
     * <p>      errorMessage: message,
     * <p>      exceptionClassName: ThisRuntimeException.className,
     * <p>    },
     * <p>    time: xxxxxxxx
     * <p>}
     *
     * @param message
     */
    public static RuntimeException RuntimeException(String message) {
        return new RuntimeException(message);
    }

    /***
     *
     * {@link CustomerReturnCode}
     * @param customerReturnCode
     * @return
     */
    public static ServiceException ErrorCode(CustomerReturnCode customerReturnCode) {
        return new ServiceException(customerReturnCode);
    }
}

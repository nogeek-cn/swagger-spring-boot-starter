package com.darian.exampleswaggerspringbootstarter.utils;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Function;

/***
 *
 */
@Slf4j
public abstract class LoggerTempleteUtils {
    private LoggerTempleteUtils() {
    }

    public static void main(String[] args) {


    }

    public static <T, P extends LoggerTempleteResult> P execute(T request,
                                                                Function<T, String> checkParmFunction,
                                                                Function<T, P> executeFunction,
                                                                Class<P> returnClass) {
        return execute(request,
                checkParmFunction,
                executeFunction,
                returnClass,
                true,
                true);

    }

    public static <T, P extends LoggerTempleteResult> P executeNotLogRequest(T request,
                                                                             Function<T, String> checkParmFunction,
                                                                             Function<T, P> executeFunction,
                                                                             Class<P> returnClass) {
        return execute(request,
                checkParmFunction,
                executeFunction,
                returnClass,
                false,
                true);
    }

    public static <T, P extends LoggerTempleteResult> P executeNotLogResponse(T request,
                                                                              Function<T, String> checkParmFunction,
                                                                              Function<T, P> executeFunction,
                                                                              Class<P> returnClass) {
        return execute(request,
                checkParmFunction,
                executeFunction,
                returnClass,
                true,
                false);
    }

    public static <T, P extends LoggerTempleteResult> P executeNotLogRequestAndResponse(T request,
                                                                                        Function<T, String> checkParmFunction,
                                                                                        Function<T, P> executeFunction,
                                                                                        Class<P> returnClass) {
        return execute(request,
                checkParmFunction,
                executeFunction,
                returnClass,
                false,
                false);
    }

    private static <T, P extends LoggerTempleteResult> P execute(T request,
                                                                 Function<T, String> checkParmFunction,
                                                                 Function<T, P> executeFunction,
                                                                 Class<P> returnClass,
                                                                 boolean loggerRequest,
                                                                 boolean loggerResponse) {
        if (loggerRequest && log.isInfoEnabled()) {
            log.info("request: " + request);
        }
        P result = null;
        try {
            result = returnClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            log.error("错误");
        }
        try {

            if (checkParmFunction != null) {
                String errMsg = checkParmFunction.apply(request);
                if (errMsg != null) {
                    result.code = ("400");
                    result.msg = (errMsg);
                    return result;
                }
            }

            result = executeFunction.apply(request);

        } catch (LoggerTempleteException e) {
            log.error("errorMsg: " + e.getMessage());
            result.code = ("500");
            result.msg = (e.getMessage());
        } catch (Exception e) {
            log.error("errorMsg: ", e);
            result.code = ("500");
            result.msg = ("系统错误");
        }
        if (loggerResponse && log.isInfoEnabled()) {
            log.info("response:" + result);
        }
        return result;
    }
}

@Slf4j
class LoggerTempleteService {

    public static String checkparm(String aaa) {
        log.info("检查的参数...........");
        if (aaa == null) {
            return "传入参数为null";
        }
        return null;
    }


    public LoggerTempleteTestResponse testService(String parm) {
        log.info("service begin........");
        LoggerTempleteTestResponse response = new LoggerTempleteTestResponse();
        if (null == parm) {
            throw new LoggerTempleteException("运行时，检查 parm 为 null");
        }
        if ("customerError".equals(parm)) {
            throw new LoggerTempleteException("运行时，自定义失败");
        } else {
            response.setResponseResult("成功");
        }
        return response;
    }
}

@Data
class LoggerTempleteTestResponse extends LoggerTempleteResult {
    private String responseResult;

    @Override
    public String toString() {
        return "LoggerTempleteTestResponse{" +
                "responseResult='" + responseResult + '\'' +
                ", code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}


class LoggerTempleteResult {
    public String code = "200";
    public String msg = "success";
}

class LoggerTempleteException extends RuntimeException {
    public LoggerTempleteException(String message) {
        super(message);
    }

    public LoggerTempleteException(String message, Throwable cause) {
        super(message, cause);
    }
}

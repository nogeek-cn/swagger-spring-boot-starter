package com.darian.exampleswaggerspringbootstarter.utils;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;


import java.util.Date;


/***
 *
 * @author Darian
 * @param <T>
 */
@Slf4j
@Data
@AllArgsConstructor
public class Response<T> {
    private String code;
    private String msg;
    private T data;
    private Date time;

    /**
     * @author Darian
     **/
    public static <T> Response<T> success(T returnDate) {
        return Response(returnDate, CustomerReturnCode.SUCCESS);
    }

    /**
     * @author Darian
     **/
    public static <T> Response<T> error(T returnDate, CustomerReturnCode darianReturnCode) {
        return Response(returnDate, darianReturnCode);
    }

    /**
     * @author Darian
     **/
    public static <T> Response<T> error(T returnDate, ServiceException pointsServiceException) {
        return Response(returnDate, pointsServiceException);
    }

    /***
     * "0000" mean "200"
     * @param okCode
     * @param <T>
     * @return
     */
    public static <T> T checkGetData(Response<T> apiResponse, String okCode) {
        // 远程服务调用异常
        if (!okCode.equals(apiResponse.getCode())) {
            log.error("远程返回值 code 不正确：" + apiResponse);
            throw new ServiceException(CustomerReturnCode.REMOTE_INVOKE_EXCEPTION);
        }
        return apiResponse.getData();
    }

    /***
     *
     * @param apiResponse
     * @param <T>
     * @return
     */
    public static <T> T getData(Response<T> apiResponse) {
        return apiResponse.getData();
    }

    /**
     * @author Darian
     **/
    public static <T> Response<T> Response(T returnDate, ServiceException e) {
        return new Response(e.getErrCode(), e.getErrMsg(), returnDate, new Date());
    }


    /**
     * @author Darian
     **/
    public static <T> Response<T> Response(T returnData, CustomerReturnCode customerReturnCode) {
        return new Response(customerReturnCode.getCode(), customerReturnCode.getMsg(), returnData, new Date());
    }
}
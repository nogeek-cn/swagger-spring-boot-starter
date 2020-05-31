package com.darian.exampleswaggerspringbootstarter.web;

import com.darian.exampleswaggerspringbootstarter.utils.BaseError;
import com.darian.exampleswaggerspringbootstarter.utils.CustomException;
import com.darian.exampleswaggerspringbootstarter.utils.CustomerReturnCode;
import com.darian.exampleswaggerspringbootstarter.utils.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.darian.exampleswaggerspringbootstarter.utils.Response.success;

@RestController
public class TestExceptionController {
    @GetMapping("/testRuntimeException")
    public Response<BaseError> testRuntimeException() {
        if (true) {
            throw CustomException.RuntimeException("异常信息放到 data 里边");
        }
        return success(null);
    }

    @GetMapping("/testServiceException")
    public Response<BaseError> testServiceException() {
        if (true) {
            throw CustomException.ServiceException("把异常信息放到msg 上");
        }
        return success(null);
    }

    @GetMapping("/testCodeException")
    public Response<BaseError> testCodeException() {
        if (true) {
            throw CustomException.ErrorCode(CustomerReturnCode.NEED_LOGIN);
        }
        return success(null);
    }

    @GetMapping("/testNull")
    public Response<BaseError> testNullPointService() {
        if (true) {
            throw new NullPointerException();
        }
        return success(null);
    }
}

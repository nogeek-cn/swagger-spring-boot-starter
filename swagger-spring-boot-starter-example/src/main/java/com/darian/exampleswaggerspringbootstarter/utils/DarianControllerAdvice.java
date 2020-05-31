package com.darian.exampleswaggerspringbootstarter.utils;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;


import static com.darian.exampleswaggerspringbootstarter.utils.Response.error;
import static java.util.stream.Collectors.toList;

/**
 * user this class to view Error View
 * {@link org.springframework.stereotype.Controller} 通知
 *
 * @author Darian
 */
@ControllerAdvice
@RestController
@Slf4j
public class DarianControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    public Response<BaseError> onERR_SERVICE_INPUT_VALIDATION_REJECTED(Exception e) {
        BindingResult bindingResult;
        if (e instanceof BindException) {
            bindingResult = BindException.class.cast(e).getBindingResult();
        } else {
            bindingResult = MethodArgumentNotValidException.class.cast(e).getBindingResult();
        }

        BaseError baseError = genertorBaseError(e);
        List<String> bindExceptionMessages = bindingResult.getAllErrors().stream()
                .map(objectError -> {
                    String errorCode = Stream.of(objectError.getCodes()).findFirst().get();
                    errorCode = errorCode.substring(errorCode.indexOf(".") + 1);
                    return "[" + errorCode + "]:" + objectError.getDefaultMessage();
                }).collect(toList());
        baseError.setBindExceptionMessages(bindExceptionMessages);
        baseError.setErrorStank(null);
        return error(baseError, CustomerReturnCode.INPUT_VALIDATION_Exception);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({SQLException.class})
    public Response<BaseError> onSQLException(Exception e) {
        BaseError baseError = genertorBaseError(e);
        return error(baseError, CustomerReturnCode.DATA_Error);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ServiceException.class)
    public Response<BaseError> onPointsServiceException(ServiceException e) {
        BaseError baseError = genertorBaseError(e);
        baseError.setErrorStank(null);
        return error(baseError, e);
    }
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    public Response<BaseError> onThrowable(Throwable e) {
        BaseError baseError = genertorBaseError(e);
        return error(baseError, CustomerReturnCode.APPLICATION_EXCEPTION);
    }

    private BaseError genertorBaseError(Throwable e) {
        BaseError baseError = new BaseError();
        baseError.setErrorMessage(e.getMessage());
        baseError.setExceptionClassName(e.getClass().getSimpleName());
        List<StackTraceElement> stackTraceElement = Stream.of(e.getStackTrace())
                .filter(ste -> ste.getClassName().startsWith("com.darian")
                ).limit(2).collect(toList());
        if (!stackTraceElement.isEmpty()) {
            baseError.setFirstAndSecondStanck(stackTraceElement);
        } else {
            baseError.setErrorStank(Arrays.asList(e.getStackTrace()));
        }
        log.error(baseError.toString());
        return baseError;
    }
}

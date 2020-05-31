
package com.darian.exampleswaggerspringbootstarter.utils;

import java.io.Serializable;

/***
 * @author Darian
 */
public interface IReturnCode extends Serializable {
    String CURRENT_APP_SYSTEM_ID = "ALL";

    default String getCode() {
        return "All2011";
    }

    default String getMsg() {
        return "系统业务异常";
    }
}

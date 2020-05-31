package com.darian.exampleswaggerspringbootstarter.utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LoggerTempleteUtilsTest {

    private LoggerTempleteService loggerTempleteService;

    @Before
    public void aaa() {
        loggerTempleteService = new LoggerTempleteService();
    }
    @Test
    public void testparmisNull(){
        String request = null;
        LoggerTempleteTestResponse response = LoggerTempleteUtils.execute(request,
                req -> LoggerTempleteService.checkparm(req),
                req -> loggerTempleteService.testService(req),
                LoggerTempleteTestResponse.class);


        Assert.assertEquals("400", response.code);
        Assert.assertEquals("传入参数为null", response.msg);
    }

    @Test
    public void testcustomerError(){

        String request = "customerError";
        LoggerTempleteTestResponse response = LoggerTempleteUtils.execute(request,
                req -> LoggerTempleteService.checkparm(req),
                req -> loggerTempleteService.testService(req),
                LoggerTempleteTestResponse.class);

        Assert.assertEquals("500", response.code);
        Assert.assertEquals("运行时，自定义失败", response.msg);
    }

    @Test
    public void testrequestisNullSkipCheckParm(){

        String request = null;
        LoggerTempleteTestResponse response = LoggerTempleteUtils.execute(request,
                null,
                req -> loggerTempleteService.testService(req),
                LoggerTempleteTestResponse.class);

        Assert.assertEquals("500", response.code);
        Assert.assertEquals("运行时，检查 parm 为 null", response.msg);
    }
    @Test
    public void testOK(){
        String request = "test";
        LoggerTempleteTestResponse response = LoggerTempleteUtils.execute(request,
                req -> LoggerTempleteService.checkparm(req),
                req -> loggerTempleteService.testService(req),
                LoggerTempleteTestResponse.class);

        Assert.assertEquals("200", response.code);
        Assert.assertEquals("success", response.msg);
    }

//    @Test
//    public void main() {
//
//
//    }
//
//    @Test
//    public void execute() {
//    }
//
//    @Test
//    public void executeNotLogRequest() {
//    }
//
//    @Test
//    public void executeNotLogResponse() {
//    }
//
//    @Test
//    public void executeNotLogRequestAndResponse() {
//    }
}
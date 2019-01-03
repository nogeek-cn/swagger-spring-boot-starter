package com.darian.swagger;


import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@ComponentScan({"com.darian.swagger"})
@EnableSwagger2
public class AutoConfiguration {
    public AutoConfiguration() {
    }
}
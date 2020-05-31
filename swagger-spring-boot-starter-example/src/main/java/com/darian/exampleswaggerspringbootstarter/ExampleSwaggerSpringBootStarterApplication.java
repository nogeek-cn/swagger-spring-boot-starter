package com.darian.exampleswaggerspringbootstarter;

import com.darian.swagger.EnableDarianSwagger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;



@EnableDarianSwagger
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ExampleSwaggerSpringBootStarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExampleSwaggerSpringBootStarterApplication.class, args);
    }

}


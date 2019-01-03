package com.darian.swagger.config;

import com.darian.swagger.utils.IPUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.darian.swagger.config.DarianSwaggerProperties.*;
import static com.darian.swagger.config.DarianSwaggerProperties.ApiInfoProperties.*;
import static com.darian.swagger.config.DarianSwaggerProperties.ParameterProperty.*;
import static java.util.stream.Collectors.*;


@Configuration
@AutoConfigureAfter(DarianSwaggerProperties.class)
@RequiredArgsConstructor
public class Swagger2Config {
    private final DarianSwaggerProperties darianSwaggerProperties;
    private final Environment env;

    /**
     * 创建API应用
     * apiInfo() 增加API相关信息
     * 通过select()函数返回一个ApiSelectorBuilder实例,用来控制哪些接口暴露给Swagger来展现，
     * 本例采用指定扫描的包路径来定义指定要建立API的目录。
     *
     * @return
     */
    @Bean
    public Docket createRestApi() {
        ApiSelectorBuilder select = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select();

        Stream.of(darianSwaggerProperties.getSelectorbasePackages()).forEach(basePackage ->
                select.apis(RequestHandlerSelectors.basePackage(basePackage))
                        .paths(PathSelectors.any())
        );
        Docket docket = select.build();
        docket.globalOperationParameters(createParameterList());
        return docket;
    }

    /**
     * 创建该API的基本信息（这些基本信息会展现在文档页面中）
     * 访问地址：http://项目实际地址/ swagger-ui.html
     *
     * @return
     */
    private ApiInfo apiInfo() {
        ApiInfoProperties apiInfoProperties = darianSwaggerProperties.getApiInfo();
        return new ApiInfoBuilder()
                .title(apiInfoProperties.getTitle())
                .description("备注:  \n   " + apiInfoProperties.getDescription() + showOrNotShowIPAndPort())
                .termsOfServiceUrl(apiInfoProperties.getTermsOfServiceUrl())
                .version(apiInfoProperties.getVersion())
                .contact(createContact())
                .license(apiInfoProperties.getLicense())
                .licenseUrl(apiInfoProperties.getLicenseUrl())
                .build();
    }

    /***
     *
     * 假如说，你配置了，显示 IP 和端口号，就会显示在 UI 上，
     * 如果配置了 false  那么就不会显示。
     * 默认是显示
     * @return
     */
    private String showOrNotShowIPAndPort() {
        if (darianSwaggerProperties.getApiInfo().getShowIpAndPort()) {
            return "\n本程序现在运行时地址 : http://" + IPUtils.getRealIp() + ":" + getPort();
        } else {
            return "";
        }
    }

    /***
     * @return 构建联系人的信息
     */
    private Contact createContact() {
        ContactProperties contactProperties = darianSwaggerProperties.getApiInfo().getContact();
        return new Contact(contactProperties.getName(), contactProperties.getUrl(), contactProperties.getEmail());
    }

    /***
     * 如果在 application.properties 中设置了 server.port
     * 将会返回你设置的 server.port
     * 如果你没有设置，就会返回 8080
     * @return
     */
    private String getPort() {
        String serverPort = env.getProperty("server.port");
        if (serverPort == null)
            return "8080";
        return serverPort;
    }

    private ModelRef createModelRef(ParameterProperty parameterProperty) {
        ModelRefProperty modelRef = parameterProperty.getModelRef();
        String type = "String";
        if (modelRef != null) {
            type = modelRef.getType();
        }
        return new ModelRef(type);
    }

    /***
     * 创建参数
     * @return
     */
    private List<Parameter> createParameterList() {
        List<Parameter> parameterList = darianSwaggerProperties
                .getParameterPropertyList()
                .stream().map(parameterProperty -> {
                            SwaggerSupportParamType paramTypeEnum = parameterProperty.getParamTypeEnum();
                            String paramTypeString = SwaggerSupportParamType.paramTypeString(paramTypeEnum);
                            return new ParameterBuilder()
                                    .parameterType(paramTypeString)
                                    .name(parameterProperty.getName())
                                    .defaultValue(parameterProperty.getDefaultValue())
                                    .description(parameterProperty.getDescription())
                                    .modelRef(createModelRef(parameterProperty))
                                    .required(parameterProperty.getRequired())
                                    .hidden(parameterProperty.getHidden())
                                    .build();
                        }
                )
                .collect(toList());
        addLanguage(parameterList);


        return parameterList;
    }

    /***
     * 如果你没有设置语言，添加进去中文
     * @param parameterList
     */
    public void addLanguage(List<Parameter> parameterList) {
        Optional<ParameterProperty> first = darianSwaggerProperties.getParameterPropertyList()
                .stream()
                .filter(parameterProperty -> {
                    SwaggerSupportParamType paramTypeEnum = parameterProperty.getParamTypeEnum();
                    String paramTypeString = SwaggerSupportParamType.paramTypeString(paramTypeEnum);
                    String name = parameterProperty.getName();
                    return "header".equals(paramTypeString) &&
                            "Accept-Language".equals(name);
                }).findFirst();

        if (!first.isPresent()) {
            Parameter languageParameter = new ParameterBuilder()
                    .parameterType("header")
                    .name("Accept-Language")
                    .defaultValue("zh-CN") //默认值
                    .description("用户可以接收的语言")
                    .modelRef(new ModelRef("string"))//指定参数值的类型
                    .required(false).build();//非必需，这里是全局配置
            parameterList.add(languageParameter);
        }
    }

//    public ParameterProperty createChineseLanguage(){
//        ParameterProperty parameterProperty = new ParameterProperty();
//        //参数类型支持header, cookie, body, query etc
//        parameterProperty.setParamTypeEnum(SwaggerSupportParamType.HEADER);
//        parameterProperty.setName("Accept-Language");//参数名
//        //默认值
//        parameterProperty.setDefaultValue("zh-CN");
//        parameterProperty.setDescription("用户可以接收的语言");
//        parameterProperty.setModelRef(new ParameterProperty.ModelRefProperty("String"));
//        parameterProperty.setRequired(false);
//    }

}
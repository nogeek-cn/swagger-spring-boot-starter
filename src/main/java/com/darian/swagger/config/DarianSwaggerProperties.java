package com.darian.swagger.config;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.*;


/***
 * Swagger 的配置类
 * @author Darian
 */
@ConfigurationProperties(prefix = "darian.swagger")
@Data
public class DarianSwaggerProperties {

    /***
     * 扫描的包
     */
    private String[] selectorbasePackages = new String[]{""};

    private List<ParameterProperty> parameterPropertyList = new ArrayList<>();

    private ApiInfoProperties apiInfo = new ApiInfoProperties();

    @Data
    public static class ApiInfoProperties {

        /***
         * 是否显示 IP 和端口号
         */
        private Boolean showIpAndPort = true;
        /***
         * 版本
         */
        private String version = "1.0";
        /***
         * 标题
         */
        private String title = "darian-swagger-默认标题";
        /***
         * 备注信息
         */
        private String description = "";
        /***
         * 服务条款的网址
         */
        private String termsOfServiceUrl;
        /***
         * 许可证
         */
        private String license;
        /***
         * 许可证链接
         */
        private String licenseUrl;
        private ContactProperties contact = new ContactProperties();
        private List<VendorExtensionProperties> vendorExtensions = new ArrayList<>();

        @Data
        public static class VendorExtensionProperties<T> {
            private String name;
            private T value;
        }

        @Data
        public static class ContactProperties {
            /***
             * 联系人的名字
             */
            private String name;
            /***
             * 联系人的 url 链接
             */
            private String url;
            /***
             * 联系人的邮箱
             */
            private String email;
        }
    }

    @Data
    public static class ParameterProperty {
        /***
         * 参数名
         */
        private String name;
        /***
         * 这个字段的注释
         */
        private String description;
        /***
         * 默认值
         */
        private String defaultValue;
        /***
         * 是否必填
         */
        private Boolean required = false;
        private Boolean allowMultiple;
        /***
         * 指定参数值的类型
         */
        private ModelRefProperty modelRef;
        private AllowableValuesProperty allowableValuesProperty;
        /***
         * 参数类型
         */
        private SwaggerSupportParamType paramTypeEnum;
        private String paramAccess;
        /***
         * 是否隐藏
         */
        private Boolean hidden = false;

        @NoArgsConstructor
        @AllArgsConstructor
        @Data
        public static class ModelRefProperty {
            private String type;
        }

        @Data
        public static class AllowableValuesProperty {

            private AllowableListValuesProperty allowableListValues = new AllowableListValuesProperty();

            @Data
            public static class AllowableListValuesProperty {
                private List<String> values;
                private SwaggerModelType valueType;
            }
        }
    }
}
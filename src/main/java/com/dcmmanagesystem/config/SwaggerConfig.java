package com.dcmmanagesystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Auther: tangweiyang
 * @Date: 2019/6/29 09:03
 * @Description: 自动生成api文档的swagger配置类
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.cyy.displaysystem"))
                .paths(PathSelectors.any()).build();
        /*basePackage 是扫描的相对路径*/
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("电子显示屏API文档")
                .description("weiyang_tang")
                .termsOfServiceUrl("")
                .version("1.0").build();
    }
}

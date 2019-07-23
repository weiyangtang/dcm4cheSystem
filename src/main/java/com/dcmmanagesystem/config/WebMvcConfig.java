package com.dcmmanagesystem.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Auther: tangweiyang
 * @Date: 2019/6/29 09:15
 * @Description:
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {


    /**
     * 上传地址
     */
    @Value("${staticResource.real_path}")
    private String filePath;
    /**
     * 显示相对地址
     */
    @Value("${staticResource.relative_path}")
    private String fileRelativePath;

    /****
     * 静态文件路径映射
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /*swagger 直接输入http://localhost:8080/swagger-ui.html 即可查看api文档*/
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        /*图片管理*/
        registry.addResourceHandler(fileRelativePath)
                .addResourceLocations("file:/" + filePath);
        super.addResourceHandlers(registry);
        registry.addResourceHandler("/templates/**")
                .addResourceLocations("classpath:/META-INF/resources/templates/");
    }
}

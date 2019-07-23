package com.dcmmanagesystem.model.Config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Auther: tangweiyang
 * @Date: 2019/7/23 13:34
 * @Description: 项目路径配置
 */
@Component
@PropertySource("classpath:pathConfig.properties")
@ConfigurationProperties(prefix = "project")
@Data
public class ProjectPathConfig {
    private String base;
    private String user_data;
    private String system_data;
    private String profile_file;
    private String bash_file;
    private String jar_package;
    private String dcm_image;
    private String other_file;
    private String zip_file;
    private String upload_file;
}
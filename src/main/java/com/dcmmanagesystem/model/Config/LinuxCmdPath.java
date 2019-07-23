package com.dcmmanagesystem.model.Config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Auther: tangweiyang
 * @Date: 2019/7/23 14:36
 * @Description:
 */
@Component
@PropertySource("classpath:pathConfig.properties")
@ConfigurationProperties(prefix = "system")
@Data
public class LinuxCmdPath {
    private String useradd_path;
    private String userdel_path;
    private String userpasswd_path;
}
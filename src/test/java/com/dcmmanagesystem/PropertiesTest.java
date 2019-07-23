package com.dcmmanagesystem;

import com.dcmmanagesystem.model.Config.LinuxCmdPath;
import com.dcmmanagesystem.model.Config.ProjectPathConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Auther: tangweiyang
 * @Date: 2019/7/23 14:29
 * @Description:
 */
@SpringBootTest(classes = DcmmanagesystemApplication.class)
@RunWith(SpringRunner.class)
public class PropertiesTest {
    @Autowired
    ProjectPathConfig config;
    @Autowired
    LinuxCmdPath linuxCmdPath;

    @Test
    public void ProjectTest() {
        System.out.println(config);
        System.out.println(linuxCmdPath);
    }
}
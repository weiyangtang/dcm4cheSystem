package com.dcmmanagesystem;

import com.dcmmanagesystem.dao.FilelogMapper;
import com.dcmmanagesystem.model.Config.ProjectPathConfig;
import com.dcmmanagesystem.model.Filelog;
import com.dcmmanagesystem.model.Project;
import com.dcmmanagesystem.service.FilelogService;
import com.dcmmanagesystem.service.ProjectService;
import com.dcmmanagesystem.util.common.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @Auther: tangweiyang
 * @Date: 2019/7/20 20:32
 * @Description:
 */
@SpringBootTest(classes = DcmmanagesystemApplication.class)
@RunWith(SpringRunner.class)
public class SpringTest {
    @Autowired
    FilelogMapper filelogMapper;

    @Autowired
    ProjectService projectService;

    @Autowired
    FilelogService filelogService;

    @Test
    public void testReturnHashMap() {
        List<HashMap> dcmList = filelogMapper.getDcmList(1);
        for (HashMap hashMap : dcmList) {
            for (Object o : hashMap.keySet()) {
                System.out.println(o + "\t" + hashMap.get(o));
            }
        }
    }

    @Test
    public void testProjectService() {
        Project project = projectService.getProjectByProjectId(1);
        System.out.println(project);
    }

    @Test
    public void testFileLogService() {
        Filelog filelog = new Filelog();
        int pid = 2;
        filelog.setProjectId((long) pid);
        LocalDateTime localDateTime = DateUtils.string2LocalDateTime("2016-09-13");
        filelog.setStudyDate(localDateTime);
        filelog.setPatientId("63730449");
        filelog = filelogService.getFilelog(filelog);
        System.out.println(filelog);
        String substring = filelog.getFilePath().substring(0, filelog.getFilePath().lastIndexOf("/"));
        System.out.println(substring);
    }

    @Test
    public void testLocalDatetime() {
        System.out.println(LocalDateTime.now(ZoneId.of("UTC+8")));
    }

    @Autowired
    ProjectPathConfig projectPathConfig;

    @Test
    public void testProjectPath() {
        System.out.println(projectPathConfig);
    }

}
package com.dcmmanagesystem.controller.common;


import com.dcmmanagesystem.model.*;
import com.dcmmanagesystem.model.Config.ProjectPathConfig;
import com.dcmmanagesystem.service.DcmFileService;
import com.dcmmanagesystem.service.FilelogService;
import com.dcmmanagesystem.service.OtherFileService;
import com.dcmmanagesystem.service.ProjectService;
import com.dcmmanagesystem.util.DicomUtils.DcmXmlParseUtils;
import com.dcmmanagesystem.util.DicomUtils.XMLParseUtil;
import com.dcmmanagesystem.util.common.DateUtils;
import com.dcmmanagesystem.util.common.FileUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.dcm4che3.data.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

//注意是只能是post方式,json格式发送回来
@RestController
public class FileUploadController {

    @Autowired
    ProjectPathConfig pathConfig;

    @Autowired
    OtherFileService otherFileService;

    @Autowired
    ProjectService projectService;

    @Autowired
    FilelogService filelogService;


    @RequestMapping(value = "/dcmfileUpload", method = RequestMethod.POST)
    public int dcmfileUpload(HttpServletRequest request, @RequestParam("pid") String pid, @RequestParam("file") MultipartFile[] files) throws ParseException {
        int count = 0;
        Project project = projectService.getProjectByPid(Integer.parseInt(pid));
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        String basepath = pathConfig.getUpload_file();
        String DCM_IMAGE__BASE_PATH = pathConfig.getDcm_image() + "/" + project.getProjectId() + "_" + project.getProjectName();
        FileUtil.saveMultiFile(basepath, files);//将所有文件先保存到服务器上,在进行处理
        for (MultipartFile file : files) {
            /**
             * filePath 上传的临时路径
             */
            String tempFilePath = basepath + "/" + file.getOriginalFilename();
            String suffixName = tempFilePath.substring(tempFilePath.lastIndexOf("."));
            System.out.println(suffixName);
            if (!suffixName.equalsIgnoreCase(".dcm"))
                continue;

            /***
             * 获取dcm文件的属性
             */
            Map<Integer, String> attrs = new HashMap<>();
            attrs.put(Tag.StudyDate, "");
            attrs.put(Tag.PatientID, "");
            Map<Integer, String> dcmAttribute = DcmXmlParseUtils.getDcmAttribute(tempFilePath, attrs);
            String PatientID = dcmAttribute.get(Tag.PatientID);
            String studyDateStr = dcmAttribute.get(Tag.StudyDate);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            Date date = simpleDateFormat.parse(studyDateStr);
            LocalDateTime studyDate = DateUtils.date2LocalDateTime(date);

            /***
             * 格式化dcm文件的存储路径：DCM_IMAGE__BASE_PATH /PatientID /StudyDate /Random.dcm
             */
            //dcm文件名
            String dcmFileName = String.valueOf(System.nanoTime()) + "" + new Random().nextInt(10000) + ".dcm";
            //dcm文件存储路径
            String dcmFilePath = DCM_IMAGE__BASE_PATH + "/" + PatientID + "/" + studyDateStr + "/" + dcmFileName;

            /**
             * dcm文件脱敏
             */
            XMLParseUtil.modifyTag(tempFilePath);

            System.out.println("上传路径" + tempFilePath);
            System.out.println("dcm文件路径 " + dcmFilePath);
            System.out.println("dcm文件名 " + dcmFileName);
            System.out.println("patientId " + PatientID);
            /***
             *dcm文件上传,写入数据库 filelog表中
             */
            Filelog filelog = new Filelog();
            filelog.setFileName(dcmFileName);
            filelog.setFilePath(dcmFilePath);
            filelog.setProjectId(Long.parseLong(pid));
            filelog.setUploadUserId(user.getUserId());
            LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC+8"));
            filelog.setUploadDate(now);
            filelog.setPatientId(PatientID);
            filelog.setStudyDate(studyDate);
            System.out.println(filelog);
            filelogService.save(filelog);

            FileUtil.fileCopy(tempFilePath, dcmFilePath);
            count++;
        }
        return count;
    }

    /**
     * 其他文件夹为了避免被覆盖,命名格式为pid/nowtime(年月日时分秒)/filename(foldername)
     */
    @RequestMapping(value = "/otherfileUpload", method = RequestMethod.POST)
    public int otherfileUpload(HttpServletRequest request, @RequestParam("pid") String pid, @RequestParam("file") MultipartFile[] files) {

        try {
            Date now = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
//            System.out.println(simpleDateFormat.format(now));
            OtherFile otherFile = new OtherFile();
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            otherFile.setUploadUserId(user.getUserId());
            otherFile.setProjectId(Integer.parseInt(pid));
            String basepath = pathConfig.getOther_file() + "/" + pid + "/" + simpleDateFormat.format(now) + "/";
            FileUtil.saveMultiFile(basepath, files);//将所有文件先保存到服务器上,在进行处理

            //写入数据库中otherfile表中
            for (MultipartFile file : files) {
                String filepath = "/" + pid + simpleDateFormat.format(now) + "/" + file.getOriginalFilename();
                otherFile.setFilePath(filepath);
                otherFile.setFileName(file.getOriginalFilename());
                otherFileService.insertOtherFile(otherFile);
            }

        } catch (Exception e) {
            return -1;
        }
        return 0;

    }
}

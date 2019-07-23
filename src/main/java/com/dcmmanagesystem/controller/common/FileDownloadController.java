package com.dcmmanagesystem.controller.common;

import cn.hutool.core.util.ZipUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dcmmanagesystem.model.Config.ProjectPathConfig;
import com.dcmmanagesystem.model.DcmServer;
import com.dcmmanagesystem.model.Filelog;
import com.dcmmanagesystem.model.common.TableSplitResult;
import com.dcmmanagesystem.model.common.Tablepar;
import com.dcmmanagesystem.service.FilelogService;
import com.dcmmanagesystem.service.ProjectService;
import com.dcmmanagesystem.util.common.DateUtils;
import com.dcmmanagesystem.util.common.FileHttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * @Auther: tangweiyang
 * @Date: 2019/7/20 20:44
 * @Description:
 */
@Controller
@RequestMapping("/fileDownload")
public class FileDownloadController {

    private String prefix = "admin/fileDownload";

    @Autowired
    FilelogService filelogService;

    @Autowired
    DcmServer dcmServer;

    @Autowired
    ProjectService projectService;

    @Autowired
    ProjectPathConfig pathConfig;


    @GetMapping("/downloadList/{projectId}")
    public String downloadListview(@PathVariable("projectId") String projectId, ModelMap modelMap) {
        modelMap.put("projectId", projectId);
        System.out.println("projectId：" + projectId);
        return prefix + "/list";
    }

    @PostMapping("/downloadList/{projectId}")
    @ResponseBody
    public TableSplitResult list(@PathVariable("projectId") String projectId, Tablepar tablepar, String searchTxt) {
        System.out.println("搜索关键字" + searchTxt);
        Page page = new Page(tablepar.getPageNum(), tablepar.getPageSize());
        IPage<HashMap> userIPage = filelogService.selectDownloadList(page, Integer.parseInt(projectId));
        TableSplitResult<HashMap> result = new TableSplitResult<>((int) userIPage.getCurrent(), userIPage.getTotal(), userIPage.getRecords());
        return result;
    }

    //patientId=" + row.patient_id + "&studyDate=" + row.study_date + "&projectId=" + projectId;
    @GetMapping("/download")
    public void download(String patientId, String studyDate, String projectId, HttpServletResponse response) {
        System.out.println("projectId：" + projectId);
        System.out.println(studyDate);
        System.out.println(patientId);
        System.out.println(dcmServer);
        System.out.println(dcmServer.getLocalAcceptDirPath());
        Filelog filelog = new Filelog();
        long pid = Long.parseLong(projectId);
        filelog.setProjectId(pid);
        LocalDateTime localDateTime = DateUtils.string2LocalDateTime(studyDate);
        filelog.setStudyDate(localDateTime);
        filelog.setPatientId(patientId);
        filelog = filelogService.getFilelog(filelog);
        System.out.println(filelog);
        String dcmZipFilePath = filelog.getFilePath().substring(0, filelog.getFilePath().lastIndexOf("/"));
//        Project project = projectService.getProjectByProjectId(Integer.parseInt(projectId));
//        String dcmZipFilePath = dcmServer.getLocalAcceptDirPath() + "/" + projectId + "_" + project.getProjectName() + "/" + patientId + "/" + studyDate.replaceAll("-", "");
        System.out.println("dcm文件压缩包路径" + dcmZipFilePath);

//        ResourceBundle bundle = ResourceBundle.getBundle("application");
//        String ZIP_BASE_PATH = bundle.getString("file.tempfile.path");
        String ZIP_BASE_PATH = pathConfig.getZip_file();
        String zipFilePath = ZIP_BASE_PATH + "/" + projectId + "_" + patientId + "_" + studyDate.replaceAll("-", "") + ".zip";
        ZipUtil.zip(dcmZipFilePath, zipFilePath);
        System.out.println("文件夹路径：" + dcmZipFilePath);
        System.out.println("dcm压缩包文件" + zipFilePath);

        try {
            FileHttpUtils.downloadFile(response, zipFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
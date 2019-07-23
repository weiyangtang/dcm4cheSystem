package com.dcmmanagesystem.controller.common;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dcmmanagesystem.model.Filelog;
import com.dcmmanagesystem.model.User;
import com.dcmmanagesystem.model.common.TableSplitResult;
import com.dcmmanagesystem.model.common.Tablepar;
import com.dcmmanagesystem.service.FilelogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: tangweiyang
 * @Date: 7/19/19 07:03
 * @Description: 文件上传日志
 */
@Controller
@RequestMapping("/fileLog")
public class FileLogController {

    @Autowired
    FilelogService filelogService;


    private String prefix = "admin/fileLogs";

    /***
     * 项目界面
     * @return
     */
    @GetMapping("/list/{projectId}")
    public String listview(@PathVariable("projectId") String projectId, ModelMap modelMap) {
        modelMap.put("projectId", projectId);
        System.out.println("projectId：" + projectId);
        return prefix + "/list";
    }

    @PostMapping("/list/{projectId}")
    @ResponseBody
    public TableSplitResult list(@PathVariable("projectId") String projectId, Tablepar tablepar, String searchTxt) {
        Page page = new Page(tablepar.getPageNum(), tablepar.getPageSize());
        IPage<Filelog> userIPage = filelogService.selectList(page, Integer.parseInt(projectId));
        TableSplitResult<Filelog> result = new TableSplitResult<>((int) userIPage.getCurrent(), userIPage.getTotal(), userIPage.getRecords());
        return result;
    }

}
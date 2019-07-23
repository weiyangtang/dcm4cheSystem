package com.dcmmanagesystem.controller.common;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dcmmanagesystem.model.Project;
import com.dcmmanagesystem.model.ProjectUser;
import com.dcmmanagesystem.model.Role;
import com.dcmmanagesystem.model.User;
import com.dcmmanagesystem.model.common.AjaxResult;
import com.dcmmanagesystem.model.common.TableSplitResult;
import com.dcmmanagesystem.model.common.Tablepar;
import com.dcmmanagesystem.service.ProjectService;
import com.dcmmanagesystem.service.ProjectUserService;
import com.dcmmanagesystem.service.UserService;
import io.swagger.annotations.Api;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: tangweiyang
 * @Date: 2019/7/17 23:50
 * @Description:
 */
@Controller
@RequestMapping("/project")
@Api(tags = "项目界面的api")
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @Autowired
    ProjectUserService projectUserService;

    @Autowired
    UserService userService;

    private String prefix = "admin/project";

    /***
     * 项目界面
     * @return
     */
    @GetMapping("/list")
    public String listview() {
        return prefix + "/list";
    }

    /***
     * 项目信息
     * @param tablepar
     * @param searchTxt
     * @return
     */
    @PostMapping("/list")
    @ResponseBody
    public TableSplitResult list(Tablepar tablepar, String searchTxt) {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        Page page = new Page(tablepar.getPageNum(), tablepar.getPageSize());
        IPage<Project> userIPage = projectService.selectList(page, user.getUserId());
        TableSplitResult<Project> result = new TableSplitResult<>((int) userIPage.getCurrent(), userIPage.getTotal(), userIPage.getRecords());
        return result;
    }

    @PostMapping("remove")
//    @RequiresPermissions("system:user:remove")
    @ResponseBody
    public AjaxResult remove(@RequestParam("userId") String projectId) {

        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        String userRole = userService.getUserRole(user.getUserId());

        if (!userRole.equalsIgnoreCase("admin"))
            return AjaxResult.error("权限不足");

        System.out.println(projectId);
        String ids[] = projectId.split(",");
        List<Project> projects = new ArrayList<>();
        Project project;
        for (String id : ids) {
            project = new Project();
            project.setProjectId(Integer.parseInt(id));
            projects.add(project);
        }
        int rowNum = projectService.delProject(projects);
        if (rowNum > 0)
            return AjaxResult.success();
        return AjaxResult.error();
    }

    @GetMapping("/add")
    public String add(ModelMap modelMap) {
        return prefix + "/add";
    }

    @PostMapping("add")
//    @RequiresPermissions("system:user:add")
    @ResponseBody
    public AjaxResult add(String projectName) {

        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();

        Project project = new Project();
        project.setProjectName(projectName);
        project.setCreateUserId(user.getUserId());

        int rowNum = projectService.insertProject(project);
        System.out.println("项目组：" + project);
        projectUserService.save(new ProjectUser(project.getProjectId(), user.getUserId()));
        if (rowNum > 0)
            return AjaxResult.success();
        return AjaxResult.error();
    }

    /**
     * 修改项目
     *
     * @param id
     * @param modelMap
     * @return
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap modelMap) {

        //查询所有角色
        Project project = projectService.getById(id);
        modelMap.put("project", project);
        return prefix + "/edit";
    }

    /**
     * 修改保存项目
     */
//    @RequiresPermissions("system:user:edit")
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Project project) {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        String userRole = userService.getUserRole(user.getUserId());
        if (!userRole.equalsIgnoreCase("admin"))
            return AjaxResult.error("权限不足");
        boolean update = projectService.updateById(project);
        if (update == true)
            return AjaxResult.success();
        return AjaxResult.error();
    }


    @PostMapping("/findByaccount")
    @ResponseBody
    public List<Project> findProjectsByUserId() {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();

        return projectService.getProjectByUserId(user.getUserId());

    }

}
package com.dcmmanagesystem.controller.common;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dcmmanagesystem.model.Project;
import com.dcmmanagesystem.model.ProjectUser;
import com.dcmmanagesystem.model.User;
import com.dcmmanagesystem.model.common.AjaxResult;
import com.dcmmanagesystem.model.common.TableSplitResult;
import com.dcmmanagesystem.model.common.Tablepar;
import com.dcmmanagesystem.service.ProjectService;
import com.dcmmanagesystem.service.ProjectUserService;
import com.dcmmanagesystem.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: tangweiyang
 * @Date: 7/18/19 02:04
 * @Description:
 */
@Controller
@RequestMapping("/projectUser")
public class ProjectUserController {

    @Autowired
    ProjectUserService projectUserService;

    @Autowired
    UserService userService;

    @Autowired
    ProjectService projectService;

    private String prefix = "admin/projectUser";

    /***
     * 项目界面
     * @return
     */
    @GetMapping("/list/{projectId}")
    public String listview(@PathVariable("projectId") String projectId, ModelMap modelMap) {
        modelMap.put("projectId", projectId);
        System.out.println("projectUser：" + projectId);
        return prefix + "/list";
    }

    @PostMapping("/list/{projectId}")
    @ResponseBody
    public TableSplitResult list(@PathVariable("projectId") String projectId, Tablepar tablepar, String searchTxt) {
        Page page = new Page(tablepar.getPageNum(), tablepar.getPageSize());
        IPage<User> userIPage = projectUserService.selectList(page, Integer.parseInt(projectId));
        TableSplitResult<User> result = new TableSplitResult<>((int) userIPage.getCurrent(), userIPage.getTotal(), userIPage.getRecords());
        return result;
    }

    @GetMapping("/add/{projectId}")
    public String add(@PathVariable("projectId") String projectId, ModelMap modelMap) {

        modelMap.put("projectId", projectId);
        return prefix + "/add";
    }

    @PostMapping("add")
//    @RequiresPermissions("system:user:add")
    @ResponseBody
    public AjaxResult add(String userId, String projectId) {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        String userRole = userService.getUserRole(user.getUserId());
        Project project = projectService.getById(projectId);
        if (!userRole.equalsIgnoreCase("admin") && !project.getCreateUserId().equals(userId))
            return AjaxResult.error("权限不足");
        if (userId == null)
            return AjaxResult.error("账号不能为空");
        user = userService.getUserById(userId);
        if (user == null)
            return AjaxResult.error("账号不存在");
        ProjectUser projectUser = projectUserService.getUserByUserId(userId, Integer.parseInt(projectId));
        if (projectUser != null)
            return AjaxResult.error("该用户已是该项目成员");
        projectUser = new ProjectUser(Integer.parseInt(projectId), userId);
        System.out.println("项目成员：" + projectUser);
        if (projectUserService.save(projectUser)) {
            System.out.println(projectUser);
            return AjaxResult.success();
        }
        return AjaxResult.error();
    }


    @PostMapping("remove/{projectId}")
//    @RequiresPermissions("system:user:remove")
    @ResponseBody
    public AjaxResult remove(@RequestParam("userId") String userId, @PathVariable("projectId") String projectId) {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        String userRole = userService.getUserRole(user.getUserId());
        Project project = projectService.getById(projectId);
        if (!userRole.equalsIgnoreCase("admin") && !project.getCreateUserId().equals(userId))
            return AjaxResult.error("权限不足");
        System.out.println(projectId);
        String ids[] = userId.split(",");
        List<ProjectUser> projects = new ArrayList<>();
        ProjectUser projectUser;
        for (String id : ids) {
            projectUser = new ProjectUser();
            projectUser.setUserId(id);
            projectUser.setProjectId(Integer.parseInt(projectId));
            projects.add(projectUser);
        }
        int rowNum = projectUserService.removeProjectUser(projects);
        if (rowNum > 0)
            return AjaxResult.success();
        return AjaxResult.error();
    }


}
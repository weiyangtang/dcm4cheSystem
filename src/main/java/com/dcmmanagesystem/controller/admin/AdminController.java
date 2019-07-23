package com.dcmmanagesystem.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dcmmanagesystem.model.Role;
import com.dcmmanagesystem.model.User;
import com.dcmmanagesystem.model.common.AjaxResult;
import com.dcmmanagesystem.model.common.TableSplitResult;
import com.dcmmanagesystem.model.common.Tablepar;
import com.dcmmanagesystem.service.RoleService;
import com.dcmmanagesystem.service.UserService;
import io.swagger.annotations.Api;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: tangweiyang
 * @Date: 2019/6/30 10:27
 * @Description: 用户管理
 */
@Controller
@Api(tags = "用户管理")
@RequestMapping("/admin/userManage")
public class AdminController {
    /****
     *1. 用户列表
     *2. 用户添加、删除
     *3. 密码管理（MD5加密）
     * 备注：（被操作对象是普通用户）
     */
    //跳转页面参数
    private String prefix = "admin/user";

    Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    UserService userService;

//    @Autowired
//    FileService fileService;

    @Autowired
    RoleService roleService;

    @GetMapping("view")
//    @RequiresPermissions("system:file:view")
    public String view(Model model) {

        // setTitle(model, new TitleVo("上传图片列表", "图片管理", false,"欢迎进入图片页面", false, false));
        return prefix + "/list";
    }

    /**
     * 用户列表
     *
     * @param tablepar
     * @param searchTxt 搜索字符
     * @return FileController/list
     */
    @PostMapping("list")
//    @RequiresPermissions("system:file:list")
    @ResponseBody
    public Object list(Tablepar tablepar, String searchTxt) {
        Page page = new Page(tablepar.getPageNum(), tablepar.getPageSize());
        IPage<User> userIPage = userService.selectUserList(page);
        TableSplitResult<User> result = new TableSplitResult<User>((int) userIPage.getCurrent(), userIPage.getTotal(), userIPage.getRecords());
        return result;
    }

    /**
     * 新增用户
     */
    @GetMapping("/add")
    public String add(ModelMap modelMap) {
        //添加角色列表
        List<Role> tsysRoleList = roleService.selectRoles();
        modelMap.put("tsysRoleList", tsysRoleList);
        return prefix + "/add";
    }


    @PostMapping("add")
//    @RequiresPermissions("system:user:add")
    @ResponseBody
    public AjaxResult add(User user) {
        Subject subject = SecurityUtils.getSubject();
        User currentUser = (User) subject.getPrincipal();
        String  role = userService.getUserRole(currentUser.getUserId());
        if (user.getUserId().equalsIgnoreCase("root"))
            return AjaxResult.error("root账户是Linux系统账户无法创建");
        if ((user.getUserRoleType()==0||user.getUserRoleType()==1)&&!role.equalsIgnoreCase("superAdmin"))
            return AjaxResult.error("添加管理员，必须有超级管理员权限");
        logger.info(user.toString());
        int rowNum = userService.insertUser(user);
        if (rowNum > 0)
            return AjaxResult.success();
        return AjaxResult.error();
    }

    /**
     * 删除用户
     *
     * @param user
     * @return
     */
    @PostMapping("remove")
//    @RequiresPermissions("system:user:remove")
    @ResponseBody
    public AjaxResult remove(User user) {
        System.out.println(user);
        String ids[] = user.getUserId().split(",");
        List<User> users = new ArrayList<>();
        for (String id : ids) {
            user = new User();
            user.setUserId(id);
            users.add(user);
            if (user.getUserId().equalsIgnoreCase("root"))
                return AjaxResult.error("root账户是Linux系统账户无法删除");
        }
        int rowNum = userService.deleteUsers(users);
        if (rowNum > 0)
            return AjaxResult.success();
        return AjaxResult.error();
    }

    /**
     * 检查用户是否存在
     *
     * @param user
     * @return
     */
    @PostMapping("checkLoginNameUnique")
    @ResponseBody
    public int checkLoginNameUnique(User user) {
        logger.info(user.toString());
        int userNum = userService.selectUserCount(user);
        return userNum > 0 ? 1 : 0;
    }


    /**
     * 修改用户
     *
     * @param id
     * @param modelMap
     * @return
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap modelMap) {
        //查询所有角色
        List<Role> tsysRoleList = roleService.selectRoles();
        modelMap.put("user", userService.getUserById(id));
        modelMap.put("tsysRoleList", tsysRoleList);

        return prefix + "/edit";
    }

    /**
     * 修改保存用户
     */
    @RequiresPermissions("system:user:edit")
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(User tsysUser, @RequestParam(value = "roles", required = false) List<String> roles) {
//        return toAjax(sysUserService.updateUserRoles(tsysUser, roles));
        return AjaxResult.error();
    }


    /**
     * 修改用户密码
     *
     * @param id
     * @param mmap
     * @return
     */
    @GetMapping("/editPwd/{id}")
    public String editPwd(@PathVariable("id") String id, ModelMap mmap) {
        mmap.put("user", userService.getUserById(id));
        return prefix + "/editPwd";
    }

    /**
     * 修改保存用户
     */
//    @RequiresPermissions("system:user:editPwd")
    @PostMapping("/editPwd")
    @ResponseBody
    public AjaxResult editPwdSave(User user) {
//        return toAjax(sysUserService.updateUserPassword(tsysUser));
        System.out.println(user);
        int rowNum = userService.updateUserPassword(user);
        if (rowNum > 0)
            return AjaxResult.success();
        return AjaxResult.error();
    }


}
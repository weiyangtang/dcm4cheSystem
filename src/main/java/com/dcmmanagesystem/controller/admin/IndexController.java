package com.dcmmanagesystem.controller.admin;


import com.dcmmanagesystem.model.Config.Config;
import com.dcmmanagesystem.model.Config.ProjectPathConfig;
import com.dcmmanagesystem.model.User;
import com.dcmmanagesystem.model.common.AjaxResult;
import com.dcmmanagesystem.model.common.BootstrapTree;
import com.dcmmanagesystem.service.ConfigService;
import com.dcmmanagesystem.service.MenuService;
import com.dcmmanagesystem.service.UserService;
import com.dcmmanagesystem.util.common.FileUploadUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ResourceBundle;

/**
 * @Auther: tangweiyang
 * @Date: 2019/6/24 13:53
 * @Description:管理员界面的首页
 * @Api(value,tag[])
 */
@Controller
@RequestMapping("/admin")
@Api(tags = "管理员界面的api")
public class IndexController {


    @Autowired
    MenuService menuService;

    @Autowired
    UserService userService;

    @Autowired
    ConfigService configService;


    Logger logger = LoggerFactory.getLogger(IndexController.class);


    @GetMapping("/index")
    @ApiOperation("管理员首页动态菜单")
    public ModelAndView mainPage(HttpServletRequest request) {
        ModelAndView view = new ModelAndView();
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
//        User userRole = userService.getUserRole(user.getUserId());
        user = userService.getUserById(user.getUserId());
        BootstrapTree bootstrapTree = menuService.getBootstrapTree("admin");
        request.getSession().setAttribute("bootstrapTree", bootstrapTree);
        request.getSession().setAttribute("sessionUserName", user.getUserName());
        Config ip = configService.getConfigByName("remote_ip");
        logger.info("remote_ip" + ip.getConfigValue());
        view.addObject("remote_ip", ip.getConfigValue());
/*        System.out.println(user.getUserHeaderImage());
        request.getSession().setAttribute("sessionHeaderPic", "/images/" + user.getUserHeaderImage());*/

        /*设置客户端天气*/
//        TodayWeather todayWeather = todayWeatherService.todayWeather(request);
//        logger.info(todayWeather.toString());
//        request.getSession().setAttribute("todayWeather", todayWeather);
        view.setViewName("admin/index");
        return view;
    }

    @Autowired
    ProjectPathConfig pathConfig;

    /***
     * 个人信息修改,头像，密码变更
     * @param session
     * @param uploadFile
     * @param userName
     * @param userPassword
     * @return
     */
    @PostMapping("/updatePersonalInfo")
    @ResponseBody
    public AjaxResult updateStudentInfo(HttpSession session,
                                        @RequestParam(value = "uploadFile") MultipartFile uploadFile, String userName, String userPassword) {

        try {
            Subject subject = SecurityUtils.getSubject();
            User user = (User) subject.getPrincipal();
            String userId = user.getUserId();
            user = new User();
            if (uploadFile != null && uploadFile.getSize() > 0) {
                String fileName = FileUploadUtils.upload(pathConfig.getProfile_file(), uploadFile);
                user.setUserHeaderImage(fileName);
            }
            user.setUserId(userId);
            user.setUserName(userName);
            user.setUserPassword(userPassword);

            logger.info(user.toString());
            System.out.println(user);
            int rowNum = userService.updateUser(user);
            if (rowNum > 0)
                return AjaxResult.success();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return AjaxResult.error();

    }


    @GetMapping("/getHeadPic")
    @ApiOperation(value = "头像")
    public ModelAndView getCaptchaCode(HttpServletRequest request, HttpServletResponse response) {
        ServletOutputStream out = null;
        try {
//            HttpSession session = request.getSession();
            Subject subject = SecurityUtils.getSubject();
            User user = (User) subject.getPrincipal();
            user = userService.getUserById(user.getUserId());
            String fileName = user.getUserHeaderImage();
//            ResourceBundle bundle = ResourceBundle.getBundle("application");
//            String dir = bundle.getString("file.upload.path");
            String dir = pathConfig.getProfile_file();
            String filePath = dir + "/" + fileName;
            response.setDateHeader("Expires", 0);
            response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
            response.addHeader("Cache-Control", "post-check=0, pre-check=0");
            response.setHeader("Pragma", "no-cache");
            response.setContentType("image/jpeg");
            File file = new File(filePath);
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] data = new byte[fileInputStream.available()];
            fileInputStream.read(data);
            out = response.getOutputStream();
            out.write(data);
            out.close();
//            ImageIO.write(bi, "jpg", out);

            out.flush();

        } catch (Exception e) {
//            e.printStackTrace();
            logger.error(e.toString());
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


}
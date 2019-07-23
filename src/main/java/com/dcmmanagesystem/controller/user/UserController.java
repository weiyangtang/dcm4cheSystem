package com.dcmmanagesystem.controller.user;

import com.dcmmanagesystem.controller.admin.IndexController;
import com.dcmmanagesystem.model.User;
import com.dcmmanagesystem.model.common.BootstrapTree;
import com.dcmmanagesystem.service.MenuService;
import com.dcmmanagesystem.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @Auther: tangweiyang
 * @Date: 2019/7/17 23:16
 * @Description:
 */
@Controller
@RequestMapping("/user")
@Api(tags = "普通用户界面的api")
public class UserController {
    @Autowired
    MenuService menuService;

    @Autowired
    UserService userService;

    Logger logger = LoggerFactory.getLogger(IndexController.class);

    @GetMapping("/index")
    @ApiOperation("普通用户首页动态菜单")
    public ModelAndView mainPage(HttpServletRequest request) {
        ModelAndView view = new ModelAndView();
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
//        User userRole = userService.getUserRole(user.getUserId());
        user = userService.getUserById(user.getUserId());
        BootstrapTree bootstrapTree = menuService.getBootstrapTree("all");
        request.getSession().setAttribute("bootstrapTree", bootstrapTree);
        request.getSession().setAttribute("sessionUserName", user.getUserName());
        System.out.println(user.getUserHeaderImage());
        request.getSession().setAttribute("sessionHeaderPic", "/images/" + user.getUserHeaderImage());

        /*设置客户端天气*/
//        TodayWeather todayWeather = todayWeatherService.todayWeather(request);
//        logger.info(todayWeather.toString());
//        request.getSession().setAttribute("todayWeather", todayWeather);
        view.setViewName("admin/index");
        return view;
    }
}
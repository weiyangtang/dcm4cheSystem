package com.dcmmanagesystem.controller;

import com.dcmmanagesystem.model.User;
import com.dcmmanagesystem.model.common.AjaxResult;
import com.dcmmanagesystem.service.UserService;
import com.dcmmanagesystem.util.common.MD5Util;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @Auther: tangweiyang
 * @Date: 2019/6/24 10:58
 * @Description:登录注册验证码
 */
@Controller
@Api(tags = "身份管理")
public class HomeController {

    @Autowired
    UserService userService;

    Logger logger = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping(value = {"/login", "/"}, method = RequestMethod.GET)
    @ApiOperation("登录界面")
    public String login(HttpServletRequest httpServletRequest) {
//        String ipAddr = IPUtils.getIpAddr(httpServletRequest);
//        logger.info("ip:" + ipAddr);
        /**
         * 如果已经认证了，直接进入系统中
         */
        if (SecurityUtils.getSubject().isAuthenticated())
            return "redirect:/index";
        return "login";
    }

    /***
     *登录验证，shiro安全框架，返回值类型AjaxResult（code,msg）
     * @param request
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    @ApiOperation(value = "身份验证")
    public AjaxResult login(HttpServletRequest request, User user, @RequestParam(name = "code") String code) {
        HttpSession session = request.getSession();
        //获取验证码
        String verfyCode = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
        /***
         * 若验证码不为空且正确，进入shiro密码验证阶段
         */
        if (code!=null && code.equalsIgnoreCase(verfyCode)) {
            /***
             * md5加密密码
             */
            String md5Passwd= MD5Util.encode(user.getUserPassword());
            user.setUserPassword(md5Passwd);

            //1.获取subject
            Subject subject = SecurityUtils.getSubject();
            //封装数据
            UsernamePasswordToken token = new UsernamePasswordToken(user.getUserId(), user.getUserPassword());
            //3. 登录
            try {
                subject.login(token);
                //校验正确
                logger.info(user.getUserId() + "登录成功");
                return AjaxResult.success("登录成功");
            } catch (Exception e) {
                //验证失败
                logger.info(user.getUserId() + "登录失败");
                return AjaxResult.error("账号和密码不一致");
            }
        }

        return AjaxResult.error("验证码不正确");
    }

    @Autowired
    private Producer captchaProducer;

    @Autowired
    private Producer captchaProducerMath;

    /***
     *获取验证码，利用Google工具生成验证码,需要配置Google验证码的配置文件，在Config/KaptchaConfig
     * @param request
     * @param response
     */
    @GetMapping("/getCaptchaCode")
    @ApiOperation(value = "验证码")
    public ModelAndView getCaptchaCode(HttpServletRequest request, HttpServletResponse response) {
        ServletOutputStream out = null;
        try {
            HttpSession session = request.getSession();
            response.setDateHeader("Expires", 0);
            response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
            response.addHeader("Cache-Control", "post-check=0, pre-check=0");
            response.setHeader("Pragma", "no-cache");
            response.setContentType("image/jpeg");

            String type = request.getParameter("type");
            String capStr = null;
            String code = null;
            BufferedImage bi = null;
            if ("math".equals(type))//验证码为算数 8*9 类型
            {
                String capText = captchaProducerMath.createText();
                capStr = capText.substring(0, capText.lastIndexOf("@"));
                code = capText.substring(capText.lastIndexOf("@") + 1);
                bi = captchaProducerMath.createImage(capStr);
            } else if ("char".equals(type))//验证码为 abcd类型
            {
                capStr = code = captchaProducer.createText();
                bi = captchaProducer.createImage(capStr);
            }
            session.setAttribute(Constants.KAPTCHA_SESSION_KEY, code);
            out = response.getOutputStream();
            ImageIO.write(bi, "jpg", out);
            out.flush();

        } catch (Exception e) {
            e.printStackTrace();
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

    /***
     *主页面的设置,分流，管理员界面和普通用户界面
     */
    @GetMapping("/index")
    @ApiOperation(value = "管理员/普通用户界面分流")
    public String index() {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        logger.info(user.getUserId());
        String role = userService.getUserRole(user.getUserId());
        if (role.equalsIgnoreCase("admin")||role.equalsIgnoreCase("superAdmin"))
            return "redirect:/admin/index";
        return "redirect:/user/index";
    }


    @GetMapping("/logout")
    @ApiOperation("注销")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/login";

    }

    @GetMapping("/main")
    @ApiOperation("")
    public String main() {
        return "/admin/main.html";

    }


}
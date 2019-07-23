package com.dcmmanagesystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dcmmanagesystem.model.User;
import com.dcmmanagesystem.dao.UserMapper;
import com.dcmmanagesystem.service.ProjectUserService;
import com.dcmmanagesystem.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dcmmanagesystem.util.DicomUtils.CommandUtil;
import com.dcmmanagesystem.util.common.MD5Util;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author tangweiyang
 * @since 2019-07-17
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    UserMapper userMapper;

    @Value("${system.useradd_path}")
    String useradd_path;

    @Value("${system.userdel_path}")
    String userdel_path;

    @Value("${system.userpasswd_path}")
    String userpassed_path;


    @Override
    public String getUserRole(String userId) {
        return userMapper.getUserRole(userId);
    }

    @Override
    public User getUserById(String userId) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public int updateUser(User user) {
        if (user==null||user.getUserPassword()==null)
            return 0;
        /****
         * 修改Ubuntu用户密码
         *
         */
        String cmd = "bash " + userpassed_path + " " + user.getUserId() + " " + user.getUserPassword();
        /***
         * md5加密密码
         */
        String md5Passwd= MD5Util.encode(user.getUserPassword());
        user.setUserPassword(md5Passwd);
        return userMapper.updateUser(user);
    }

    @Override
    public int updateUserPassword(User user) {
        /****
         * 修改Ubuntu用户密码
         *
         */
        if (user==null||user.getUserPassword()==null)
            return 0;
        String cmd = "bash " + userpassed_path + " " + user.getUserId() + " " + user.getUserPassword();
        System.out.println(CommandUtil.exec(cmd));

        /***
         * md5加密密码
         */
        String md5Passwd= MD5Util.encode(user.getUserPassword());
        user.setUserPassword(md5Passwd);
        return userMapper.updateUserPassword(user);
    }

    /***
     * 分页查询
     * @param page
     * @param queryWrapper
     * @return
     */
    @Override
    public IPage<User> selectPage(Page page, QueryWrapper queryWrapper) {

        return userMapper.selectPage(page, queryWrapper);
    }

    @Override
    public Page<User> selectPageById(Page page, String id) {

        return page.setRecords(userMapper.selectPageById(page, id));
    }


    /*************************************对普通用户操作***********************************************/
    /****
     *1. 用户列表
     *2. 用户添加、删除
     *3. 密码管理（MD5加密）
     * 备注：（被操作对象是普通用户）
     */

    @Override
    public IPage<User> selectUserList(Page page) {

        QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.select("user.user_id,user.user_name,user.user_password,user.user_header_image");

        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        String userRole = userMapper.getUserRole(user.getUserId());
        if (userRole.equalsIgnoreCase("superAdmin"))
            return userMapper.selectPage(page,queryWrapper);
        /**
         * user_role_type=2表示是普通用户
         */
        queryWrapper.eq("user_role_type", 2);
        return userMapper.selectPage(page, queryWrapper);
    }





    /***
     * 添加用户到ubuntu
     *添加用户时，如果用户头像为空，则赋值给一个默认的头像person.jpg
     * @param user
     * @return
     */
    @Override
    public int insertUser(User user) {
        if (user == null || user.getUserId() == null || user.getUserPassword()==null)
            return 0;
        else {
            String cmd = "bash " + useradd_path + " " + user.getUserId() + " " + user.getUserPassword();
            System.out.println(CommandUtil.exec(cmd));
            /***
             * 将密码用md5算法加密
             */
            String md5Passwd= MD5Util.encode(user.getUserPassword());
            user.setUserPassword(md5Passwd);

        }
//        if (user.getUserHeaderImage() == null)
            user.setUserHeaderImage("person.png");
        user.setCreateTime(LocalDateTime.now());
        return userMapper.insertUser(user);
    }

    @Autowired
    ProjectUserService projectUserService;

    /***
     * 批量删除用户
     * @param users
     * @return
     */
    @Override
    public int deleteUsers(List<User> users) {
        int returnInt = 0;

        if (users != null) {
            for (User user : users) {
                if (user != null && user.getUserId() != null){
                    /***
                     * 从Linux系统删除用户
                     */
                    String cmd = "bash " + userdel_path + " " + user.getUserId();
                    System.out.println(CommandUtil.exec(cmd));
                    /***
                     * 从项目成员表中删除用户
                     *
                     */
                    QueryWrapper queryWrapper=new QueryWrapper();
                    queryWrapper.eq("user_id",user.getUserId());
                    projectUserService.remove(queryWrapper);
                    /***
                     * 从用户表中删除
                     */
                    returnInt = returnInt + userMapper.deleteById(user.getUserId());
                }

            }
        }
        return returnInt;
    }

    /***
     * 删除单个用户
     * @param user
     * @return
     */
    @Override
    public int deleteUser(User user) {
        if (user != null&&user.getUserId()!=null){
            String cmd = "bash " + userdel_path + " " + user.getUserId();
            System.out.println(CommandUtil.exec(cmd));
            return userMapper.deleteById(user.getUserId());
        }

        return 0;
    }

    @Override
    public int selectUserCount(User user) {
        if (user != null) {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("user_id", user.getUserId());
            return userMapper.selectCount(queryWrapper);
        }
        return -1;
    }

    /*************************************end 对普通用户操作***********************************************/

}

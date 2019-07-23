package com.dcmmanagesystem.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dcmmanagesystem.model.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author tangweiyang
 * @since 2019-07-17
 */
public interface UserService extends IService<User> {

    /***
     * 获取用户的角色
     * @param userId
     * @return
     */
    public String getUserRole(String userId);

    /***
     * 根据账号查找用户
     * @param userId
     * @return
     */
    public User getUserById(String userId);

    int updateUser(User user);

    int updateUserPassword(User user);

    IPage<User> selectPage(Page page, QueryWrapper queryWrapper);

    Page<User> selectPageById(Page page, String id);




    /*************************************对普通用户操作***********************************************/
    /****
     *1. 用户列表
     *2. 用户添加、删除
     *3. 密码管理（MD5加密）
     * 备注：（被操作对象是普通用户）
     */
    IPage<User> selectUserList(Page page);

    int insertUser(User user);



    /***
     * 删除多个用户
     */
    int deleteUsers(List<User> users);

    int deleteUser(User user);


    int selectUserCount(User user);


    /*************************************end 对普通用户操作***********************************************/

}

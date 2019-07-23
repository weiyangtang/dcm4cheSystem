package com.dcmmanagesystem.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dcmmanagesystem.model.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author tangweiyang
 * @since 2019-07-17
 */
public interface UserMapper extends BaseMapper<User> {

    /***
     * 获取用户的角色
     * @param userId
     * @return
     */
    @Select("select role.role_name from user,role where role.role_type=user.user_role_type and user.user_id=#{userId}")
    public String getUserRole(String userId);

    @Select("SELECT * FROM `user` LIMIT #{page.current},#{page.size};")
    List<User> selectPageById(@Param("page") Page page, @Param("id") String id);

    public int updateUser(User user);

    @Update("UPDATE user SET  user_password=#{userPassword} WHERE user_id=#{userId} ")
    public int updateUserPassword(User user);

    @Insert("INSERT INTO user ( user_id,user_name, user_password, user_role_type,create_time,user_header_image ) VALUES ( #{userId}, #{userName}, #{userPassword},#{userRoleType},#{createTime},#{userHeaderImage})")
    public int insertUser(User user);
}

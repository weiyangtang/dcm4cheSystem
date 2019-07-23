package com.dcmmanagesystem.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dcmmanagesystem.model.ProjectUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dcmmanagesystem.model.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author tangweiyang
 * @since 2019-07-17
 */
public interface ProjectUserMapper extends BaseMapper<ProjectUser> {

    @Select("select user.user_id,user_name from project_user,user where project_user.user_id=user.user_id and project_user.project_id=#{pid}")
    public List<User> getProjectUsersByPid(int pid, Page page);


}

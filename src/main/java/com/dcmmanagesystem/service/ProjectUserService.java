package com.dcmmanagesystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dcmmanagesystem.model.ProjectUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dcmmanagesystem.model.User;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author tangweiyang
 * @since 2019-07-17
 */
public interface ProjectUserService extends IService<ProjectUser> {

    public IPage<User> selectList(Page page, int pid);

    ProjectUser getUserByUserId(String userId,int pid);

    int removeProjectUser(List<ProjectUser> projects);
}

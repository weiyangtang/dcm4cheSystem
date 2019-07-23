package com.dcmmanagesystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dcmmanagesystem.model.ProjectUser;
import com.dcmmanagesystem.dao.ProjectUserMapper;
import com.dcmmanagesystem.model.User;
import com.dcmmanagesystem.service.ProjectUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class ProjectUserServiceImpl extends ServiceImpl<ProjectUserMapper, ProjectUser> implements ProjectUserService {

    @Autowired
    ProjectUserMapper projectUserMapper;

    @Override
    public IPage<User> selectList(Page page, int pid) {
        List<User> users = projectUserMapper.getProjectUsersByPid(pid, page);
        return page.setRecords(users);
    }

    @Override
    public ProjectUser getUserByUserId(String userId,int pid) {
        QueryWrapper<ProjectUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("project_id",pid);
//        projectUserMapper.selectOne(queryWrapper);
        return projectUserMapper.selectOne(queryWrapper);
    }

    @Override
    public int removeProjectUser(List<ProjectUser> projects) {
        int count = 0;
        for (ProjectUser project : projects) {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("user_id", project.getUserId());
            queryWrapper.eq("project_id", project.getProjectId());
            count += projectUserMapper.delete(queryWrapper);
        }
        return count;
    }
}

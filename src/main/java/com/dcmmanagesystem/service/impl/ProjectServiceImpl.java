package com.dcmmanagesystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dcmmanagesystem.model.Project;
import com.dcmmanagesystem.dao.ProjectMapper;
import com.dcmmanagesystem.service.ProjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dcmmanagesystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements ProjectService {

    @Autowired
    ProjectMapper projectMapper;

    @Autowired
    UserService userService;


    @Override
    public Project getProjectByPid(int pid) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("project_id", pid);
        return projectMapper.selectOne(queryWrapper);
    }

    @Override
    public int getPortByPid(int parseInt) {

        return projectMapper.getPortByPid(parseInt);
    }

    @Override
    public List<Project> getAllPorts() {
        QueryWrapper<Project> queryWrapper = new QueryWrapper<>();
        return projectMapper.selectList(queryWrapper);
    }

    @Override
    public int updateProjectPort(int port, Integer projectId) {
        return projectMapper.updateProjectPort(port, projectId);
    }

    /***
     * 管理员获取所有项目,普通用户获取自己参加的项目
     * @param page
     * @param userId
     * @return
     */
    @Override
    public IPage<Project> selectList(Page page, String userId) {
        String userRole = userService.getUserRole(userId);
        if (userRole.equalsIgnoreCase("admin"))
            return projectMapper.selectPage(page, null);
        else {
            /**
             * page封装分页
             */
            return page.setRecords(projectMapper.getProjectByUserId(userId));
        }
    }

    @Override
    public int delProject(List<Project> projects) {
        int count = 0;
        for (Project project : projects) {
            projectMapper.deleteById(project.getProjectId());
            count++;
        }
        return count;

    }

    @Override
    public int insertProject(Project project) {
        LocalDateTime now = LocalDateTime.now();
        project.setCreateTime(now);
        Integer tempMaxPort = projectMapper.getMaxPort();
        int port = 10240;
        if (tempMaxPort != null)
            port = projectMapper.getMaxPort() + 1;
        project.setProjectPort(port);
        return projectMapper.insertProject(project);
    }

    @Override
    public List<Project> getProjectByUserId(String userId) {
        return projectMapper.getProjectByUserId(userId);
    }

    @Override
    public Project getProjectByProjectId(int projectId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("project_id", projectId);
        return projectMapper.selectOne(queryWrapper);
    }
}

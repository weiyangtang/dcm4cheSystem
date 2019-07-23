package com.dcmmanagesystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dcmmanagesystem.model.Project;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tangweiyang
 * @since 2019-07-17
 */
public interface ProjectService extends IService<Project> {

    Project getProjectByPid(int pid);

    int getPortByPid(int parseInt);

    List<Project> getAllPorts();

    int updateProjectPort(int port, Integer projectId);

    IPage<Project> selectList(Page page,String userId);

    int delProject(List<Project> projects);

    int insertProject(Project project);

    List<Project> getProjectByUserId(String userId);

    Project getProjectByProjectId(int projectId);
}

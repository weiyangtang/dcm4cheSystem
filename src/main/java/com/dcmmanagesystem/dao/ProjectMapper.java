package com.dcmmanagesystem.dao;

import com.dcmmanagesystem.model.Project;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
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
public interface ProjectMapper extends BaseMapper<Project> {

    @Select("select project_port from project where project_id=#{pid}")
    public int getPortByPid(int pid);

    @Update("UPDATE project SET project_port=#{port} WHERE project_id=#{pid}")
    public int updateProjectPort(int port, int pid);

    @Select("select project.project_id,project.project_name,project.create_time,project.create_user_id  from project_user,project where project_user.project_id=project.project_id and project_user.user_id=#{userId}")
    public List<Project> getProjectByUserId(String userId);

    /***
     * 获取最大项目最大端口号
     * @return
     */
    @Select("select project_port from project ORDER BY  project_port desc limit 1")
    public Integer getMaxPort();

    @Insert("insert into project(project_name,create_time,create_user_id,project_port) values(#{projectName},#{createTime},#{createUserId},#{projectPort})")
    @Options(useGeneratedKeys=true, keyProperty="projectId", keyColumn="project_id")
    int insertProject(Project project);

}

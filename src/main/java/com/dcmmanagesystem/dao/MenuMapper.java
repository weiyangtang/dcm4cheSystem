package com.dcmmanagesystem.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dcmmanagesystem.model.Menu;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 权限表 Mapper 接口
 * </p>
 *
 * @author tangweiyang
 * @since 2019-06-24
 */
public interface MenuMapper extends BaseMapper<Menu> {
    /***
     * 管理员菜单
     * @return
     */
    @Select("SELECT * FROM menu")
    public List<Menu> getAdminMenus();

    /***
     *根据父节点找子菜单
     */


    @Select("SELECT * FROM menu WHERE pid=#{pid}")
    public List<Menu> getAdminMenusByPid(int Pid);


    /***
     * 普通用户菜单
     * @return
     */
    @Select("SELECT * FROM menu WHERE perms='all'")
    public List<Menu> getUserMenus();

    /***
     *
     * @param Pid
     * @return
     */
    @Select("SELECT * FROM menu WHERE perms='all' and pid=#{pid}")
    public List<Menu> getUserMenusByPid(int Pid);


}

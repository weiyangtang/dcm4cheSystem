package com.dcmmanagesystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dcmmanagesystem.model.Menu;
import com.dcmmanagesystem.model.User;
import com.dcmmanagesystem.model.common.BootstrapTree;

/**
 * <p>
 * 菜单管理
 * </p>
 *
 * @author tangweiyang
 * @since 2019-06-24
 */
public interface MenuService extends IService<Menu> {
    /***
     * 动态获取菜单（管理员和普通用户不同）
     * @param role
     * @return
     */
    public BootstrapTree getBootstrapTree(String role);


}

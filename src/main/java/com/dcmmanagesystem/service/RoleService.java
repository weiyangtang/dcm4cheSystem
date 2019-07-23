package com.dcmmanagesystem.service;

import com.dcmmanagesystem.model.Role;
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
public interface RoleService extends IService<Role> {

    List<Role> selectRoles();
}

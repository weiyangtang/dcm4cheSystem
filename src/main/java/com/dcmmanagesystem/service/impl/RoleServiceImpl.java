package com.dcmmanagesystem.service.impl;

import com.dcmmanagesystem.model.Role;
import com.dcmmanagesystem.dao.RoleMapper;
import com.dcmmanagesystem.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tangweiyang
 * @since 2019-07-17
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    RoleMapper roleMapper;

    @Override
    public List<Role> selectRoles() {
        return roleMapper.selectList(null);
    }
}

package com.dcmmanagesystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dcmmanagesystem.model.Config.Config;
import com.dcmmanagesystem.dao.ConfigMapper;
import com.dcmmanagesystem.service.ConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 配置表 服务实现类
 * </p>
 *
 * @author tangweiyang
 * @since 2019-07-18
 */
@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements ConfigService {
    @Autowired
    ConfigMapper configMapper;

    @Override
    public Config getConfigByName(String remote_ip) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("config_name",remote_ip);
        return configMapper.selectOne(queryWrapper);
    }
}

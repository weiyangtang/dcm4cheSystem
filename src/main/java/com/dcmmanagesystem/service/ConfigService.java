package com.dcmmanagesystem.service;

import com.dcmmanagesystem.model.Config.Config;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 配置表 服务类
 * </p>
 *
 * @author tangweiyang
 * @since 2019-07-18
 */
public interface ConfigService extends IService<Config> {

    Config getConfigByName(String remote_ip);
}

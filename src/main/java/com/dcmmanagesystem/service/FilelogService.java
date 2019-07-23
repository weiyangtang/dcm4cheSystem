package com.dcmmanagesystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dcmmanagesystem.model.Filelog;

import java.util.HashMap;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author tangweiyang
 * @since 2019-07-19
 */
public interface FilelogService extends IService<Filelog> {

    IPage<Filelog> selectList(Page page, int parseInt);

    IPage<HashMap> selectDownloadList(Page page, int pid);

    public Filelog getFilelog(Filelog filelog);
}

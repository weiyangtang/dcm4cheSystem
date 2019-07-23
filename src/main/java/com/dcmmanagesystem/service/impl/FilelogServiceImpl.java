package com.dcmmanagesystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dcmmanagesystem.model.Filelog;
import com.dcmmanagesystem.dao.FilelogMapper;
import com.dcmmanagesystem.model.User;
import com.dcmmanagesystem.service.FilelogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author tangweiyang
 * @since 2019-07-19
 */
@Service
public class FilelogServiceImpl extends ServiceImpl<FilelogMapper, Filelog> implements FilelogService {

    @Autowired
    FilelogMapper filelogMapper;

    @Override
    public IPage<Filelog> selectList(Page page, int parseInt) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("project_id", parseInt);
        queryWrapper.orderByDesc("study_date");
        return filelogMapper.selectPage(page, queryWrapper);
    }

    @Override
    public IPage<HashMap> selectDownloadList(Page page, int pid) {
        return page.setRecords(filelogMapper.getDcmList(pid));
    }

    @Override
    public Filelog getFilelog(Filelog filelog) {
        return filelogMapper.getFilelog(filelog);
    }
}

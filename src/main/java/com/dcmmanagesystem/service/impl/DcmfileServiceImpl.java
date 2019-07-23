package com.dcmmanagesystem.service.impl;

import com.dcmmanagesystem.model.DcmFile;
import com.dcmmanagesystem.dao.DcmFileMapper;
import com.dcmmanagesystem.service.DcmFileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author tangweiyang
 * @since 2019-07-17
 */
@Service
public class DcmfileServiceImpl extends ServiceImpl<DcmFileMapper, DcmFile> implements DcmFileService {

    @Autowired
    DcmFileMapper dcmFileMapper;

    @Override
    public int insertDcmfile(DcmFile dcmFile) {
        LocalDateTime now = LocalDateTime.now();
        dcmFile.setUploadDate(now);
        return dcmFileMapper.insertDcmfile(dcmFile);
    }
}

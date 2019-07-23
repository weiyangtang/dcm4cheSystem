package com.dcmmanagesystem.service.impl;

import com.dcmmanagesystem.model.OtherFile;
import com.dcmmanagesystem.dao.OtherFileMapper;
import com.dcmmanagesystem.service.OtherFileService;
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
public class OtherfileServiceImpl extends ServiceImpl<OtherFileMapper, OtherFile> implements OtherFileService {

    @Autowired
    OtherFileMapper otherfileMapper;

    @Override
    public int insertOtherFile(OtherFile otherFile) {
        LocalDateTime now = LocalDateTime.now();
        otherFile.setUploadTime(now);
        return otherfileMapper.insert(otherFile);
    }
}

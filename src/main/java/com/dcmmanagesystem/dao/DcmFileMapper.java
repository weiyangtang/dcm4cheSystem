package com.dcmmanagesystem.dao;

import com.dcmmanagesystem.model.DcmFile;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;

import java.time.LocalDateTime;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author tangweiyang
 * @since 2019-07-17
 */
public interface DcmFileMapper extends BaseMapper<DcmFile> {

    @Insert("insert into dcmfile(project_id,file_path,upload_date,upload_user_id) values(#{projectId},#{filePath},#{uploadDate},#{uploadUserId})")
    public int insertDcmfile(DcmFile dcmFile);
}

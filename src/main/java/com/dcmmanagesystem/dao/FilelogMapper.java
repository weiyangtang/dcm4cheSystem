package com.dcmmanagesystem.dao;

import com.dcmmanagesystem.model.Filelog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author tangweiyang
 * @since 2019-07-19
 */
public interface FilelogMapper extends BaseMapper<Filelog> {
    @Select("select patient_id,DATE_FORMAT(study_date,'%Y-%c-%d') study_date,count(id) from filelog where project_id=#{pid} group by patient_id,study_date order by  study_date  desc")
    public List<HashMap> getDcmList(int pid);
    @Select("select * from filelog where patient_id=#{patientId} and study_date=#{studyDate} and project_id=#{projectId} limit 1")
    public Filelog getFilelog(Filelog filelog);
}

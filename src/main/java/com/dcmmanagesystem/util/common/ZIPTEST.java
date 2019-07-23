package com.dcmmanagesystem.util.common;

import cn.hutool.core.util.ZipUtil;
import com.dcmmanagesystem.util.DicomUtils.DcmXmlParseUtils;
import org.dcm4che3.data.Tag;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: tangweiyang
 * @Date: 2019/7/20 16:20
 * @Description:
 */
public class ZIPTEST {
    public static void main(String[] args) throws ParseException {
        String src="D:\\home\\jh\\dcm4che\\data\\Dcm_Image\\1_常州医疗";
        String dest="D:\\home\\jh\\dcm4che\\data\\download\\901720313.zip";
        ZipUtil.zip(src,dest);
        System.out.println("压缩结束");
        String filePath = "D:\\工作\\dcm4che代码备份\\weiyang_tang\\DCM_Image\\011958333339.dcm";
        Map<Integer, String> attrs = new HashMap<>();
        attrs.put(Tag.StudyDate, "");
        attrs.put(Tag.PatientID, "");
        Map<Integer, String> dcmAttribute = DcmXmlParseUtils.getDcmAttribute(filePath, attrs);
        for (Integer integer : dcmAttribute.keySet()) {
            System.out.println(dcmAttribute.get(integer));
        }
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMdd");
        Date date = simpleDateFormat.parse(dcmAttribute.get(Tag.StudyDate));
        LocalDateTime localDateTime1 = DateUtils.date2LocalDateTime(date);
        System.out.println(localDateTime1);

        Instant instant = date.toInstant();//An instantaneous point on the time-line.(时间线上的一个瞬时点。)
        ZoneId zoneId = ZoneId.systemDefault();//A time-zone ID, such as {@code Europe/Paris}.(时区)
        LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
        System.out.println(localDateTime);

    }
}
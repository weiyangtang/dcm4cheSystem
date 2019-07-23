package com.dcmmanagesystem.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author tangweiyang
 * @since 2019-07-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class OtherFile implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文件id
     */
    @TableId(value = "file_id", type = IdType.AUTO)
    private Integer fileId;

    /**
     * 项目id
     */
    private Integer projectId;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件保存路径
     */
    private String filePath;

    /**
     * 上传人id
     */
    private String uploadUserId;

    /**
     * 上传时间
     */
    private LocalDateTime uploadTime;


}

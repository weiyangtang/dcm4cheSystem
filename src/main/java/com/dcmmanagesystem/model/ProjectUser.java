package com.dcmmanagesystem.model;

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
public class ProjectUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 项目id
     */
    private Integer projectId;

    /**
     * 用户id
     */
    private String userId;

    public ProjectUser(Integer projectId, String userId) {
        this.projectId = projectId;
        this.userId = userId;
    }

    public ProjectUser() {
    }
}

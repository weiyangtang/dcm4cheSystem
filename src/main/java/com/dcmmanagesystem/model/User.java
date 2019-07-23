package com.dcmmanagesystem.model;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
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
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户账号
     */
    @TableId
    private String userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户密码
     */
    private String userPassword;

    /**
     * 头像
     */
    private String userHeaderImage;

    /**
     * 用户类型
     */
    private Integer userRoleType;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    public User(String userId, String userName, String userPassword, String userHeaderImage, Integer userRoleType, LocalDateTime createTime) {
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userHeaderImage = userHeaderImage;
        this.userRoleType = userRoleType;
        this.createTime = createTime;
    }

    public User() {
    }
    //    private String roleName;


}

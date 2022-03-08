package com.project.recruit_and_employ.pojo;

import lombok.Data;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author xmljeff
 * @since 2022-03-08
 */

@TableName("user")
@Data
public class UserPO implements Serializable {
    private static final Long serialVersionUID = 1L;

/**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    //@TableId(value = "user_id", type = IdType.ID_WORKER)
    //private Long id;//如果是Long类型的主键.则需要IdType.ID_WORKER;它会自动使用雪花算法生成不重复的ID.在新增的时候.自动赋值
    @TableId(type = IdType.AUTO)
    private Long userId;
    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String userName;
    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    private String password;
    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称")
    private String nickName;
    /**
     * 性别：0：男；1：女，2：未知
     */
    @ApiModelProperty(value = "性别：0：男；1：女，2：未知")
    private Integer sex;
    /**
     * 角色：1：管理员；2：求职者；3：公司
     */
    @ApiModelProperty(value = "角色：1：管理员；2：求职者；3：公司")
    private Integer role;
    /**
     * 电话号码
     */
    @ApiModelProperty(value = "电话号码")
    private Integer phoneNum;
    
}
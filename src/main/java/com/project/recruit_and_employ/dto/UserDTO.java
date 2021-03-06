package com.project.recruit_and_employ.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author ：xmljeff
 * @date ：Created in 2022/1/16 19:06
 * @description：用户DTO
 * @modified By：
 * @version: $
 */
@Data
public class UserDTO implements Serializable {

    /**
     * 用户ids
     */
    @ApiModelProperty(value = "用户ids")
    private List<Long> userIds;
    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
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
     * 角色：0：超级管理员；1：管理员；2：求职者；3：公司
     */
    @ApiModelProperty(value = "角色：0：超级管理员；1：管理员；2：求职者；3：公司")
    private Integer role;
    /**
     * 电话号码
     */
    @ApiModelProperty(value = "电话号码")
    private Long phoneNum;
    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称,当角色为公司时，需选择或填写")
    private String companyName;
    /**
     * 公司详情
     */
    @ApiModelProperty(value = "公司详情，当角色为公司时，需选择或填写")
    private String companyDetail;

    @ApiModelProperty(value = "页码", example = "1", notes = "必填")
    private Integer pageNum;

    @ApiModelProperty(value = "每页条数", example = "10", notes = "必填")
    private Integer pageSize;
}

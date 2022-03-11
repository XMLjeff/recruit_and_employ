package com.project.recruit_and_employ.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author ：xmljeff
 * @date ：Created in 2022/3/11 19:44
 * @description：
 * @modified By：
 * @version: $
 */
@Data
public class CompanyUserDTO {

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
     * 电话号码
     */
    @ApiModelProperty(value = "电话号码")
    private Long phoneNum;
    /**
     * 公司id
     */
    @ApiModelProperty(value = "公司id")
    private Long companyId;
    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    private String companyName;

    @ApiModelProperty(value = "页码", example = "1", notes = "必填")
    private Integer pageNum;

    @ApiModelProperty(value = "每页条数", example = "10", notes = "必填")
    private Integer pageSize;
}

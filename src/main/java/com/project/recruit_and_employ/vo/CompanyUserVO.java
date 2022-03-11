package com.project.recruit_and_employ.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ：xmljeff
 * @date ：Created in 2022/3/11 20:12
 * @description：
 * @modified By：
 * @version: $
 */
@Data
public class CompanyUserVO {
    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Long userId;
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
    /**
     * 公司详情
     */
    @ApiModelProperty(value = "公司详情")
    private String companyDetail;
}

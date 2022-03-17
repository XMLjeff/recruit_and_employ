package com.project.recruit_and_employ.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @version 1.0
 * @author:xmljeff
 * @date:2022/3/10
 * @description TODO
 **/
@Data
public class ChartVO {

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
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    private String companyName;
    /**
     * 公司详情
     */
    @ApiModelProperty(value = "公司详情")
    private String companyDetail;
    /**
     * 院校
     */
    @ApiModelProperty(value = "院校")
    private String university;
    /**
     * 专业
     */
    @ApiModelProperty(value = "专业")
    private String major;
    /**
     * 意向岗位
     */
    @ApiModelProperty(value = "意向岗位")
    private String intendedPosition;
}

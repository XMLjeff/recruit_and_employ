package com.project.recruit_and_employ.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author ：xmljeff
 * @date ：Created in 2022/3/10 21:54
 * @description：
 * @modified By：
 * @version: $
 */
@Data
public class MaJobSeekersDTO {

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
     * 意向岗位
     */
    @ApiModelProperty(value = "意向岗位")
    private String intendedPosition;
    /**
     * 意向工作地点
     */
    @ApiModelProperty(value = "意向工作地点")
    private String intendedPlaceOfWork;
    /**
     * 期望薪资
     */
    @ApiModelProperty(value = "期望薪资")
    private BigDecimal salaryExpectation;
    /**
     * 奖学金信息
     */
    @ApiModelProperty(value = "奖学金信息")
    private String scholarshipInfo;
    /**
     * 个人介绍
     */
    @ApiModelProperty(value = "个人介绍")
    private String introduction;
    /**
     * 简历url
     */
    @ApiModelProperty(value = "简历url")
    private String resumeUrl;

    @ApiModelProperty(value = "页码", example = "1", notes = "必填")
    private Integer pageNum;

    @ApiModelProperty(value = "每页条数", example = "10", notes = "必填")
    private Integer pageSize;
}
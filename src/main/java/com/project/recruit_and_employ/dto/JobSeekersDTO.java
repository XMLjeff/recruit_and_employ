package com.project.recruit_and_employ.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author ：xmljeff
 * @date ：Created in 2022/3/9 20:09
 * @description：求职者DTO
 * @modified By：
 * @version: $
 */
@Data
public class JobSeekersDTO implements Serializable {

    /**
     * 用户id（主键）
     */
    @ApiModelProperty(value = "用户id（主键）")
    private Long userId;
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
    /**
     * 工作经历
     */
    @ApiModelProperty(value = "工作经历")
    private String workExperience;
    /**
     * 毕业时间
     */
    @ApiModelProperty(value = "毕业时间")
    private LocalDate graduationTime;
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
     * 专业技能
     */
    @ApiModelProperty(value = "专业技能")
    private String professionalSkill;

    @ApiModelProperty(value = "页码", example = "1", notes = "必填")
    private Integer pageNum;

    @ApiModelProperty(value = "每页条数", example = "10", notes = "必填")
    private Integer pageSize;
}

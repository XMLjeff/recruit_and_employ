package com.project.recruit_and_employ.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author ：xmljeff
 * @date ：Created in 2022/3/17 20:32
 * @description：
 * @modified By：
 * @version: $
 */
@Data
public class JobSeekersVO {

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
}

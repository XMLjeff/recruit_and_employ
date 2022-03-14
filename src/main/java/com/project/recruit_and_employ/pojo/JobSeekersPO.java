package com.project.recruit_and_employ.pojo;

import io.swagger.annotations.ApiModel;

import java.math.BigDecimal;
import java.time.*;

import lombok.Data;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 求职者
 * </p>
 *
 * @author xmljeff
 * @since 2022-03-08
 */

@TableName("job_seekers")
@Data
public class JobSeekersPO implements Serializable {
    private static final Long serialVersionUID = 1L;

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

}
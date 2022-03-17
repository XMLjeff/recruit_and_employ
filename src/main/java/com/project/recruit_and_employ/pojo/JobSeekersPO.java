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
 * @since 2022-03-17
 */

@TableName("job_seekers")
@Data
public class JobSeekersPO implements Serializable {
    private static final Long serialVersionUID = 1L;

/**
     * 用户id（主键）
     */
    @ApiModelProperty(value = "用户id（主键）")
    //@TableId(value = "user_id", type = IdType.ID_WORKER)
    //private Long id;//如果是Long类型的主键.则需要IdType.ID_WORKER;它会自动使用雪花算法生成不重复的ID.在新增的时候.自动赋值
    @TableId(type = IdType.AUTO)
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
    
}
package com.project.recruit_and_employ.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author ：xmljeff
 * @date ：Created in 2022/3/9 21:58
 * @description：
 * @modified By：
 * @version: $
 */
@Data
public class PositionDTO {

    /**
     * 岗位ids
     */
    @ApiModelProperty(value = "岗位ids")
    private List<Long> positionIds;
    /**
     * 岗位id
     */
    @ApiModelProperty(value = "岗位id")
    private Long positionId;
    /**
     * 招聘人的id
     */
    @ApiModelProperty(value = "招聘人的id")
    private Long userId;
    /**
     * 岗位名称
     */
    @ApiModelProperty(value = "岗位名称")
    private String positionName;
    /**
     * 岗位类别（1表示校招；2表示社招）
     */
    @ApiModelProperty(value = "岗位类别（1表示校招；2表示社招）")
    private Integer positionCategory;
    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    private String companyName;
    /**
     * 岗位薪资
     */
    @ApiModelProperty(value = "岗位薪资")
    private BigDecimal positionSalary;
    /**
     * 工作地点
     */
    @ApiModelProperty(value = "工作地点")
    private String placeOfWork;
    /**
     * 岗位详情
     */
    @ApiModelProperty(value = "岗位详情")
    private String positionDetail;
    /**
     * 工作经验要求
     */
    @ApiModelProperty(value = "工作经验要求")
    private String workExperience;
    /**
     * 学历要求
     */
    @ApiModelProperty(value = "学历要求")
    private String education;
    /**
     * 岗位职责
     */
    @ApiModelProperty(value = "岗位职责")
    private String jobResponsibility;
    /**
     * 任职要求
     */
    @ApiModelProperty(value = "任职要求")
    private String jobRequirement;
    /**
     * 职位福利
     */
    @ApiModelProperty(value = "职位福利")
    private String positionBenefits;
    /**
     * 工作年限
     */
    @ApiModelProperty(value = "工作年限")
    private Integer workingYears;

    @ApiModelProperty(value = "页码", example = "1", notes = "必填")
    private Integer pageNum;

    @ApiModelProperty(value = "每页条数", example = "10", notes = "必填")
    private Integer pageSize;
}

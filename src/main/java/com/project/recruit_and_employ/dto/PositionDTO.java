package com.project.recruit_and_employ.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

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

    @ApiModelProperty(value = "页码", example = "1", notes = "必填")
    private Long pageNum;

    @ApiModelProperty(value = "每页条数", example = "10", notes = "必填")
    private Long pageSize;
}

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
 * 岗位表
 * </p>
 *
 * @author xmljeff
 * @since 2022-03-08
 */

@TableName("position")
@Data
public class PositionPO implements Serializable {
    private static final Long serialVersionUID = 1L;

/**
     * 岗位id
     */
    @ApiModelProperty(value = "岗位id")
    //@TableId(value = "position_id", type = IdType.ID_WORKER)
    //private Long id;//如果是Long类型的主键.则需要IdType.ID_WORKER;它会自动使用雪花算法生成不重复的ID.在新增的时候.自动赋值
    @TableId(type = IdType.AUTO)
    private Long positionId;
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
     * 公司id
     */
    @ApiModelProperty(value = "公司id")
    private Long companyId;
    /**
     * 岗位薪资
     */
    @ApiModelProperty(value = "岗位薪资")
    private BigDecimal positionSalary;
    /**
     * 岗位详情
     */
    @ApiModelProperty(value = "岗位详情")
    private String positionDetail;
    /**
     * 工作地点
     */
    @ApiModelProperty(value = "工作地点")
    private String placeOfWork;
    /**
     * 是否停止招聘（0表示停止招聘，1表示正在招聘）
     */
    @ApiModelProperty(value = "是否停止招聘（0表示停止招聘，1表示正在招聘）")
    private Integer recruitFlag;
    
}
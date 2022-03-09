package com.project.recruit_and_employ.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author ：xmljeff
 * @date ：Created in 2022/3/9 21:22
 * @description：
 * @modified By：
 * @version: $
 */
@Data
public class PositionVO {

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
     * 公司详情
     */
    @ApiModelProperty(value = "公司详情")
    private String companyDetail;
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
    /**
     * 招聘人的id
     */
    @ApiModelProperty(value = "招聘人的id")
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
}

package com.project.recruit_and_employ.pojo;

import lombok.Data;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 公司表
 * </p>
 *
 * @author xmljeff
 * @since 2022-03-08
 */

@TableName("company")
@Data
public class CompanyPO implements Serializable {
    private static final Long serialVersionUID = 1L;

/**
     * 公司id
     */
    @ApiModelProperty(value = "公司id")
    //@TableId(value = "company_id", type = IdType.ID_WORKER)
    //private Long id;//如果是Long类型的主键.则需要IdType.ID_WORKER;它会自动使用雪花算法生成不重复的ID.在新增的时候.自动赋值
    @TableId(type = IdType.AUTO)
    private Long companyId;
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
    
}
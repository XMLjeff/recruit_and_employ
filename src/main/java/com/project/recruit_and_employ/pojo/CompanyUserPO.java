package com.project.recruit_and_employ.pojo;

import lombok.Data;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 公司、用户关联表
 * </p>
 *
 * @author xmljeff
 * @since 2022-03-08
 */

@TableName("company_user")
@Data
public class CompanyUserPO implements Serializable {
    private static final Long serialVersionUID = 1L;

/**
     * 公司id
     */
    @ApiModelProperty(value = "公司id")
    private Long companyId;
    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Long userId;
    
}
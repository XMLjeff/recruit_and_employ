package com.project.recruit_and_employ.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author ：xmljeff
 * @date ：Created in 2022/3/10 23:09
 * @description：
 * @modified By：
 * @version: $
 */
@Data
public class CompanyDTO {

    /**
     * 公司ids
     */
    @ApiModelProperty(value = "公司ids")
    private List<Long> companyIds;
    /**
     * 公司id
     */
    @ApiModelProperty(value = "公司id")
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

    @ApiModelProperty(value = "页码", example = "1", notes = "必填")
    private Integer pageNum;

    @ApiModelProperty(value = "每页条数", example = "10", notes = "必填")
    private Integer pageSize;
}

package com.project.recruit_and_employ.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @version 1.0
 * @author:xmljeff
 * @date:2022/3/10
 * @description TODO
 **/
@Data
public class ChartVO {

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Long userId;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称")
    private String nickName;
}

package com.project.recruit_and_employ.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ：xmljeff
 * @date ：Created in 2022/3/9 21:01
 * @description：
 * @modified By：
 * @version: $
 */
@Data
public class UserVO {
    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String userName;
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
    /**
     * 电话号码
     */
    @ApiModelProperty(value = "电话号码")
    private Long phoneNum;
}

package com.project.recruit_and_employ.pojo;

import io.swagger.annotations.ApiModel;
import java.time.*;
import lombok.Data;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 电话交换
 * </p>
 *
 * @author xmljeff
 * @since 2022-03-08
 */

@TableName("phone_exchange")
@Data
public class PhoneExchangePO implements Serializable {
    private static final Long serialVersionUID = 1L;

/**
     * 电话发送者id
     */
    @ApiModelProperty(value = "电话发送者id")
    private Long senderId;
    /**
     * 电话
     */
    @ApiModelProperty(value = "电话")
    private Long phone;
    /**
     * 电话接收者id
     */
    @ApiModelProperty(value = "电话接收者id")
    private Long recipientId;
    
}
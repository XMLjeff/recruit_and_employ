package com.project.recruit_and_employ.pojo;

import io.swagger.annotations.ApiModel;

import java.time.*;

import lombok.Data;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 留言表
 * </p>
 *
 * @author xmljeff
 * @since 2022-03-08
 */

@TableName("message")
@Data
public class MessagePO implements Serializable {
    private static final Long serialVersionUID = 1L;

    /**
     * 留言发送者
     */
    @ApiModelProperty(value = "留言发送者")
    private Long senderId;
    /**
     * 留言接收者
     */
    @ApiModelProperty(value = "留言接收者")
    private Long recipientId;
    /**
     * 留言信息
     */
    @ApiModelProperty(value = "留言信息")
    private String message;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

}
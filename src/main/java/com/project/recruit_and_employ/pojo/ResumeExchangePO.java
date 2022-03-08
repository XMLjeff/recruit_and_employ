package com.project.recruit_and_employ.pojo;

import lombok.Data;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 简历交换
 * </p>
 *
 * @author xmljeff
 * @since 2022-03-08
 */

@TableName("resume_exchange")
@Data
public class ResumeExchangePO implements Serializable {
    private static final Long serialVersionUID = 1L;

/**
     * 简历上传者id
     */
    @ApiModelProperty(value = "简历上传者id")
    private Long senderId;
    /**
     * 简历接收者id
     */
    @ApiModelProperty(value = "简历接收者id")
    private Long recipientId;
    /**
     * 简历url
     */
    @ApiModelProperty(value = "简历url")
    private String resumeUrl;
    
}
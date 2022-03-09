package com.project.recruit_and_employ.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ：xmljeff
 * @date ：Created in 2022/3/9 22:33
 * @description：
 * @modified By：
 * @version: $
 */
@Data
public class MessageVO {

    /**
     * 留言发送者名称
     */
    @ApiModelProperty(value = "留言发送者名称")
    private String senderName;
    /**
     * 留言接收者名称
     */
    @ApiModelProperty(value = "留言接收者名称")
    private String recipientName;
    /**
     * 留言信息
     */
    @ApiModelProperty(value = "留言信息")
    private String message;
    /**
     * 是否是自己发的，用于判断消息的显示位置
     */
    @ApiModelProperty(value = "是否是自己发的，用于判断消息的显示位置")
    private Boolean owner;

}

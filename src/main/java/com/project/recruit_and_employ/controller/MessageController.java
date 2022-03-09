package com.project.recruit_and_employ.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.project.recruit_and_employ.pojo.MessagePO;
import com.project.recruit_and_employ.pojo.UserPO;
import com.project.recruit_and_employ.service.*;
import com.project.recruit_and_employ.vo.MessageVO;
import com.project.recruit_and_employ.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ：xmljeff
 * @date ：Created in 2022/3/9 23:04
 * @description：
 * @modified By：
 * @version: $
 */
@RestController
@RequestMapping("message")
@Api(tags = "留言")
public class MessageController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IMessageService messageService;

    @ApiOperation(value = "留言")
    @PostMapping("leaveMessage")
    @ApiOperationSupport(ignoreParameters = {"po.createTime"})
    public ResultVO leaveMessage(@RequestBody MessagePO po) {
        po.setCreateTime(LocalDateTime.now());
        messageService.save(po);
        return ResultVO.ok();
    }

    @ApiOperation(value = "查看留言")
    @PostMapping("queryMessage")
    @ApiOperationSupport(includeParameters = {"po.senderId", "po.recipientId"})
    public ResultVO<List<MessageVO>> queryMessage(@RequestBody MessagePO po) {

        List<UserPO> userPOS = userService.list();
        Map<Long, String> nickNameMap = userPOS.stream().collect(Collectors.toMap(UserPO::getUserId, UserPO::getNickName));

        List<MessagePO> messagePOS = messageService.list(Wrappers.lambdaQuery(MessagePO.class)
                .eq(MessagePO::getSenderId, po.getSenderId())
                .eq(MessagePO::getRecipientId, po.getRecipientId())
                .or()
                .eq(MessagePO::getSenderId, po.getRecipientId())
                .eq(MessagePO::getRecipientId, po.getSenderId())
                .orderByAsc(MessagePO::getCreateTime));

        List<MessageVO> messageVOS = new ArrayList<>();
        for (MessagePO messagePO : messagePOS) {
            MessageVO messageVO = new MessageVO();
            messageVO.setSenderName(nickNameMap.get(messagePO.getSenderId()));
            messageVO.setRecipientName(nickNameMap.get(messagePO.getRecipientId()));
            messageVO.setMessage(messagePO.getMessage());
            if (po.getSenderId().equals(messagePO.getSenderId())) {
                messageVO.setOwner(true);
            } else {
                messageVO.setOwner(false);
            }
            messageVOS.add(messageVO);
        }

        return ResultVO.ok().setData(messageVOS);
    }
}

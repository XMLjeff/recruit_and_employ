package com.project.recruit_and_employ.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.project.recruit_and_employ.constant.UserConstant;
import com.project.recruit_and_employ.dto.UserDTO;
import com.project.recruit_and_employ.pojo.*;
import com.project.recruit_and_employ.service.*;
import com.project.recruit_and_employ.vo.ChartVO;
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
import java.util.*;
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
    @Autowired
    private ICompanyUserService companyUserService;
    @Autowired
    private ICompanyService companyService;
    @Autowired
    private IJobSeekersService jobSeekersService;

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

    @ApiOperation(value = "得到聊天列表")
    @PostMapping("getChartList")
    @ApiOperationSupport(includeParameters = {"dto.userId"})
    public ResultVO<List<ChartVO>> getChartList(@RequestBody UserDTO dto) {
        //select max(create_time) create_time,recipient_id ,sender_id from message group by recipient_id, sender_id having sender_id = 1 ;
        QueryWrapper<MessagePO> queryWrapper = new QueryWrapper();
        queryWrapper.select("max(create_time) create_time , recipient_id , sender_id");
        queryWrapper.groupBy("recipient_id, sender_id");
        queryWrapper.having("sender_id = " + dto.getUserId());
        List<MessagePO> list = messageService.list(queryWrapper);

        //select max(create_time)  create_time,recipient_id ,sender_id from message group by sender_id,recipient_id having recipient_id = 1 ;
        queryWrapper = new QueryWrapper();
        queryWrapper.select("max(create_time) create_time , recipient_id , sender_id");
        queryWrapper.groupBy("recipient_id, sender_id");
        queryWrapper.having("recipient_id = " + dto.getUserId());
        List<MessagePO> list1 = messageService.list(queryWrapper);

        Map<Long, LocalDateTime> map = new HashMap<>();

        for (MessagePO messagePO : list) {
            if (!map.containsKey(messagePO.getRecipientId())) {
                map.put(messagePO.getRecipientId(), messagePO.getCreateTime());
            } else {
                if (messagePO.getCreateTime().isAfter(map.get(messagePO.getRecipientId()))) {
                    map.put(messagePO.getRecipientId(), messagePO.getCreateTime());
                }
            }
        }

        for (MessagePO messagePO : list1) {
            if (!map.containsKey(messagePO.getSenderId())) {
                map.put(messagePO.getSenderId(), messagePO.getCreateTime());
            } else {
                if (messagePO.getCreateTime().isAfter(map.get(messagePO.getSenderId()))) {
                    map.put(messagePO.getSenderId(), messagePO.getCreateTime());
                }
            }
        }

        List<MessagePO> messagePOS = new ArrayList<>();
        for (Map.Entry<Long, LocalDateTime> entry : map.entrySet()) {
            MessagePO messagePO = new MessagePO();
            messagePO.setSenderId(entry.getKey());
            messagePO.setCreateTime(entry.getValue());
            messagePOS.add(messagePO);
        }

        messagePOS.stream().sorted(Comparator.comparing(MessagePO::getCreateTime).reversed());

        List<UserPO> userPOS = userService.list();
        Map<Long, String> nickNameMap = userPOS.stream().collect(Collectors.toMap(UserPO::getUserId, UserPO::getNickName));

        List<ChartVO> chartVOS = new ArrayList<>();
        for (MessagePO messagePO : messagePOS) {
            ChartVO chartVO = new ChartVO();
            chartVO.setUserId(messagePO.getSenderId());
            chartVO.setNickName(nickNameMap.get(messagePO.getSenderId()));
            chartVOS.add(chartVO);
        }

        if (!CollectionUtils.isEmpty(chartVOS)) {
            List<Long> userIds = chartVOS.stream().map(t -> t.getUserId()).collect(Collectors.toList());
            Map<Long, Integer> sexMap = userService.list(Wrappers.lambdaQuery(UserPO.class).in(UserPO::getUserId, userIds))
                    .stream().collect(Collectors.toMap(UserPO::getUserId, UserPO::getSex));
            UserPO userPO = userService.getById(chartVOS.get(0).getUserId());
            //如果是公司，加入公司信息
            if (UserConstant.ROLE_COMPANY.equals(userPO.getRole())) {
                List<CompanyUserPO> companyUserPOS = companyUserService.list(Wrappers.lambdaQuery(CompanyUserPO.class).in(CompanyUserPO::getUserId, userIds));
                Map<Long, Long> companyUserMap = companyUserPOS.stream().collect(Collectors.toMap(CompanyUserPO::getUserId, CompanyUserPO::getCompanyId));
                List<Long> companyIds = companyUserPOS.stream().map(t -> t.getCompanyId()).collect(Collectors.toList());
                List<CompanyPO> companyPOS = companyService.list(Wrappers.lambdaQuery(CompanyPO.class).in(CompanyPO::getCompanyId, companyIds));
                Map<Long, String> companyNameMap = companyPOS.stream().collect(Collectors.toMap(CompanyPO::getCompanyId, CompanyPO::getCompanyName));
                Map<Long, String> companyDetailMap = companyPOS.stream().collect(Collectors.toMap(CompanyPO::getCompanyId, CompanyPO::getCompanyDetail));
                chartVOS.forEach(t -> {
                    t.setSex(sexMap.get(t.getUserId()));
                    t.setCompanyName(companyNameMap.get(companyUserMap.get(t.getUserId())));
                    t.setCompanyDetail(companyDetailMap.get(companyUserMap.get(t.getUserId())));
                });
            }

            //如果是求职者，加入求职者信息
            if (UserConstant.ROLE_JOB_SEEKERS.equals(userPO.getRole())) {
                List<JobSeekersPO> jobSeekersPOS = jobSeekersService.list(Wrappers.lambdaQuery(JobSeekersPO.class).in(JobSeekersPO::getUserId, userIds));
                Map<Long, String> universityMap = jobSeekersPOS.stream().collect(Collectors.toMap(JobSeekersPO::getUserId, JobSeekersPO::getUniversity));
                Map<Long, String> majorMap = jobSeekersPOS.stream().collect(Collectors.toMap(JobSeekersPO::getUserId, JobSeekersPO::getMajor));
                Map<Long, String> intendedPositionMap = jobSeekersPOS.stream().collect(Collectors.toMap(JobSeekersPO::getUserId, JobSeekersPO::getIntendedPosition));
                chartVOS.forEach(t -> {
                    t.setSex(sexMap.get(t.getUserId()));
                    t.setUniversity(universityMap.get(t.getUserId()));
                    t.setMajor(majorMap.get(t.getUserId()));
                    t.setIntendedPosition(intendedPositionMap.get(t.getUserId()));
                });
            }
        }

        return ResultVO.ok().setData(chartVOS);
    }

}

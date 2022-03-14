package com.project.recruit_and_employ.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.project.recruit_and_employ.enums.MessageEnum;
import com.project.recruit_and_employ.pojo.JobSeekersPO;
import com.project.recruit_and_employ.pojo.PhoneExchangePO;
import com.project.recruit_and_employ.pojo.ResumeExchangePO;
import com.project.recruit_and_employ.pojo.UserPO;
import com.project.recruit_and_employ.service.IJobSeekersService;
import com.project.recruit_and_employ.service.IResumeExchangeService;
import com.project.recruit_and_employ.service.IUserService;
import com.project.recruit_and_employ.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version 1.0
 * @author:xmljeff
 * @date:2022/3/10
 * @description TODO
 **/
@RestController
@RequestMapping("resumeExchange")
@Api(tags = "简历交换")
public class ResumeExchangeController {

    @Autowired
    private IJobSeekersService jobSeekersService;
    @Autowired
    private IResumeExchangeService resumeExchangeService;

    @ApiOperation(value = "交换简历")
    @PostMapping("exchangeResume")
    @ApiOperationSupport(ignoreParameters = {"po.resumeUrl"})
    public ResultVO exchangeResume(@RequestBody ResumeExchangePO po) {

        JobSeekersPO jobSeekersPO = jobSeekersService.getById(po.getSenderId());
        if (jobSeekersPO == null) {
            return new ResultVO(MessageEnum.WRITE_INFO);
        }

        ResumeExchangePO resumeExchangePO = resumeExchangeService.getOne(Wrappers.lambdaQuery(ResumeExchangePO.class)
                .eq(ResumeExchangePO::getSenderId, po.getSenderId())
                .eq(ResumeExchangePO::getRecipientId, po.getRecipientId()));

        if (resumeExchangePO == null) {
            po.setResumeUrl(jobSeekersPO.getResumeUrl());
            resumeExchangeService.save(po);
        } else {
            resumeExchangeService.update(Wrappers.lambdaUpdate(ResumeExchangePO.class)
                    .set(ResumeExchangePO::getResumeUrl, jobSeekersPO.getResumeUrl())
                    .eq(ResumeExchangePO::getSenderId, po.getSenderId())
                    .eq(ResumeExchangePO::getRecipientId, po.getRecipientId()));
        }

        return ResultVO.ok();
    }

    @ApiOperation(value = "显示简历")
    @PostMapping("showResume")
    @ApiOperationSupport(ignoreParameters = {"po.resumeUrl"})
    public ResultVO<ResumeExchangePO> showResume(@RequestBody ResumeExchangePO po) {

        ResumeExchangePO resumeExchangePO = resumeExchangeService.getOne(Wrappers.lambdaQuery(ResumeExchangePO.class)
                .eq(ResumeExchangePO::getSenderId, po.getSenderId())
                .eq(ResumeExchangePO::getRecipientId, po.getRecipientId()));

        return ResultVO.ok().setData(resumeExchangePO);
    }
}

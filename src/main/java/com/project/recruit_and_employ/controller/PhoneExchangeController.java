package com.project.recruit_and_employ.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.project.recruit_and_employ.pojo.PhoneExchangePO;
import com.project.recruit_and_employ.pojo.UserPO;
import com.project.recruit_and_employ.service.IPhoneExchangeService;
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
@RequestMapping("phoneExchange")
@Api(tags = "电话交换")
public class PhoneExchangeController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IPhoneExchangeService phoneExchangeService;

    @ApiOperation(value = "交换电话")
    @PostMapping("exchangePhone")
    @ApiOperationSupport(ignoreParameters = {"po.phone"})
    public ResultVO exchangePhone(@RequestBody PhoneExchangePO po) {

        UserPO sender = userService.getById(po.getSenderId());

        PhoneExchangePO phoneExchangePO = phoneExchangeService.getOne(Wrappers.lambdaQuery(PhoneExchangePO.class)
                .eq(PhoneExchangePO::getSenderId, po.getSenderId())
                .eq(PhoneExchangePO::getRecipientId, po.getRecipientId()));

        if (phoneExchangePO == null) {
            po.setPhone(sender.getPhoneNum());
            phoneExchangeService.save(po);
        } else {
            phoneExchangeService.update(Wrappers.lambdaUpdate(PhoneExchangePO.class)
                    .set(PhoneExchangePO::getPhone, sender.getPhoneNum())
                    .eq(PhoneExchangePO::getSenderId, po.getSenderId())
                    .eq(PhoneExchangePO::getRecipientId, po.getRecipientId()));
        }

        return ResultVO.ok();
    }

    @ApiOperation(value = "显示电话")
    @PostMapping("showPhone")
    @ApiOperationSupport(ignoreParameters = {"po.phone"})
    public ResultVO<PhoneExchangePO> showPhone(@RequestBody PhoneExchangePO po) {

        PhoneExchangePO phoneExchangePO = phoneExchangeService.getOne(Wrappers.lambdaQuery(PhoneExchangePO.class)
                .eq(PhoneExchangePO::getSenderId, po.getSenderId())
                .eq(PhoneExchangePO::getRecipientId, po.getRecipientId()));

        return ResultVO.ok().setData(phoneExchangePO);
    }
}

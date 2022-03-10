package com.project.recruit_and_employ.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.project.recruit_and_employ.constant.UserConstant;
import com.project.recruit_and_employ.dto.UserDTO;
import com.project.recruit_and_employ.enums.MessageEnum;
import com.project.recruit_and_employ.mapstruct.UserConverter;
import com.project.recruit_and_employ.pojo.CompanyPO;
import com.project.recruit_and_employ.pojo.CompanyUserPO;
import com.project.recruit_and_employ.pojo.UserPO;
import com.project.recruit_and_employ.service.ICompanyService;
import com.project.recruit_and_employ.service.ICompanyUserService;
import com.project.recruit_and_employ.service.IUserService;
import com.project.recruit_and_employ.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：xmljeff
 * @date ：Created in 2022/3/9 19:38
 * @description：用户登录
 * @modified By：
 * @version: $
 */
@RestController
@RequestMapping("userLogin")
@Api(tags = "用户登录")
public class UserLoginController {

    @Autowired
    private IUserService userService;
    @Autowired
    private ICompanyService companyService;

    @ApiOperation(value = "用户注册")
    @PostMapping("register")
    @ApiOperationSupport(ignoreParameters = {"dto.userId", "dto.userIds", "dto.pageNum", "dto.pageSize"})
    public ResultVO register(@RequestBody UserDTO dto) {

        UserPO user = userService.getOne(Wrappers.lambdaQuery(UserPO.class).eq(UserPO::getUserName, dto.getUserName()));
        if (user != null) {
            return new ResultVO(MessageEnum.USER_EXIST);
        }

        UserPO userPO = UserConverter.INSTANCE.convertToPO(dto);
        //密码md5加密
        userPO.setPassword(DigestUtils.md5DigestAsHex(userPO.getPassword().getBytes()));

        userService.save(userPO);

        if (UserConstant.ROLE_COMPANY.equals(dto.getRole())) {
            CompanyPO companyPO = companyService.getOne(Wrappers.lambdaQuery(CompanyPO.class).eq(CompanyPO::getCompanyName, dto.getCompanyName()));
            if (companyPO != null) {
                companyService.update(Wrappers.lambdaUpdate(CompanyPO.class)
                        .set(CompanyPO::getCompanyDetail, dto.getCompanyDetail())
                        .eq(CompanyPO::getCompanyName, dto.getCompanyName()));
            } else {
                companyPO = new CompanyPO();
                companyPO.setCompanyName(dto.getCompanyName());
                companyPO.setCompanyDetail(dto.getCompanyDetail());
                companyService.save(companyPO);
            }

            CompanyUserPO companyUserPO = new CompanyUserPO();
            companyUserPO.setCompanyId(companyPO.getCompanyId());
            companyUserPO.setUserId(userPO.getUserId());
        }

        return ResultVO.ok();
    }

    @ApiOperation(value = "登录")
    @PostMapping("login")
    @ApiOperationSupport(includeParameters = {"dto.userName", "dto.password"})
    public ResultVO<UserPO> login(@RequestBody UserDTO dto) {

        UserPO user = userService.getOne(Wrappers.lambdaQuery(UserPO.class).eq(UserPO::getUserName, dto.getUserName()));
        if (user == null) {
            return new ResultVO(MessageEnum.USER_NOT_EXIST);
        }

        if (!user.getPassword().equals(DigestUtils.md5DigestAsHex(dto.getPassword().getBytes()))) {
            return new ResultVO(MessageEnum.ACCOUNT_PASSWORD_WRONG);
        }

        return ResultVO.ok().setData(user);
    }

    @ApiOperation(value = "修改密码")
    @PostMapping("updatePassword")
    @ApiOperationSupport(includeParameters = {"dto.userId", "dto.password"})
    public ResultVO updatePassword(@RequestBody UserDTO dto) {
        if (StringUtils.isEmpty(dto.getPassword())) {
            return new ResultVO(MessageEnum.PASSWORD_CANT_NULL);
        }

        UserPO userPO1 = new UserPO();
        userPO1.setUserId(dto.getUserId());
        userPO1.setPassword(DigestUtils.md5DigestAsHex(dto.getPassword().getBytes()));

        userService.updateById(userPO1);

        return ResultVO.ok();
    }

    @ApiOperation(value = "重置密码")
    @PostMapping("resetPassword")
    @ApiOperationSupport(includeParameters = {"dto.userName"})
    public ResultVO resetPassword(@RequestBody UserDTO dto) {

        UserPO one = userService.getOne(Wrappers.lambdaQuery(UserPO.class).eq(UserPO::getUserName, dto.getUserName()));
        if (one == null) {
            return new ResultVO(MessageEnum.USERNAME_NOT_EXIST);
        }

        userService.update(Wrappers.lambdaUpdate(UserPO.class)
                .eq(UserPO::getUserName, dto.getUserName())
                .set(UserPO::getPassword, DigestUtils.md5DigestAsHex(UserConstant.DEFAULT_PASSWORD.getBytes())));

        return new ResultVO(MessageEnum.DEFAULT_PASSWORD);
    }

    @ApiOperation(value = "退出登录")
    @PostMapping("quit")
    public ResultVO quit() {
        return ResultVO.ok();
    }
}

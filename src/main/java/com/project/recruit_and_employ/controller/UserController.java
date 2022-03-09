package com.project.recruit_and_employ.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.project.recruit_and_employ.dto.UserDTO;
import com.project.recruit_and_employ.mapstruct.UserConverter;
import com.project.recruit_and_employ.pojo.UserPO;
import com.project.recruit_and_employ.service.IUserService;
import com.project.recruit_and_employ.vo.ResultVO;
import com.project.recruit_and_employ.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：xmljeff
 * @date ：Created in 2022/3/9 20:51
 * @description：用户信息
 * @modified By：
 * @version: $
 */
@RestController
@RequestMapping("user")
@Api(tags = "用户信息")
public class UserController {

    @Autowired
    private IUserService userService;

    @ApiOperation(value = "编辑基本信息")
    @PostMapping("editUserInfo")
    @ApiOperationSupport(includeParameters = {"dto.userId", "dto.nickName", "dto.sex", "dto.phoneNum"})
    public ResultVO editUserInfo(@RequestBody UserDTO dto) {

        UserPO userPO = UserConverter.INSTANCE.convertToPO(dto);
        userService.updateById(userPO);
        return ResultVO.ok();
    }

    @ApiOperation(value = "查询基本信息")
    @PostMapping("queryUserInfo")
    @ApiOperationSupport(includeParameters = {"dto.userId"})
    public ResultVO<UserVO> queryUserInfo(@RequestBody UserDTO dto) {

        UserPO userPO = userService.getById(dto.getUserId());
        UserVO userVO = UserConverter.INSTANCE.convertToVO(userPO);

        return ResultVO.ok().setData(userVO);
    }


}

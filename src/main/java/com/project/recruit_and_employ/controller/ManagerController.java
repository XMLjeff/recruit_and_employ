package com.project.recruit_and_employ.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.project.recruit_and_employ.constant.UserConstant;
import com.project.recruit_and_employ.dto.CompanyDTO;
import com.project.recruit_and_employ.dto.MaJobSeekersDTO;
import com.project.recruit_and_employ.dto.UserDTO;
import com.project.recruit_and_employ.enums.MessageEnum;
import com.project.recruit_and_employ.mapstruct.CompanyConverter;
import com.project.recruit_and_employ.mapstruct.JobSeekersConverter;
import com.project.recruit_and_employ.mapstruct.UserConverter;
import com.project.recruit_and_employ.pojo.*;
import com.project.recruit_and_employ.service.*;
import com.project.recruit_and_employ.vo.MaJobSeekersVO;
import com.project.recruit_and_employ.vo.PageInfoVO;
import com.project.recruit_and_employ.vo.ResultVO;
import com.project.recruit_and_employ.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author ：xmljeff
 * @date ：Created in 2022/3/10 21:12
 * @description：
 * @modified By：
 * @version: $
 */
@RestController
@RequestMapping("manager")
@Api(tags = "管理员")
public class ManagerController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IJobSeekersService jobSeekersService;
    @Autowired
    private ICompanyService companyService;
    @Autowired
    private ICompanyUserService companyUserService;
    @Autowired
    private IPositionService positionService;

    @ApiOperation(value = "新增管理员")
    @PostMapping("insertManager")
    @ApiOperationSupport(includeParameters = {"dto.userName", "dto.nickName", "dto.sex", "dto.phoneNum"})
    public ResultVO insertManager(@RequestBody UserDTO dto) {

        UserPO user = userService.getOne(Wrappers.lambdaQuery(UserPO.class).eq(UserPO::getUserName, dto.getUserName()));
        if (user != null) {
            return new ResultVO(MessageEnum.USER_EXIST);
        }

        UserPO userPO = UserConverter.INSTANCE.convertToPO(dto);
        userPO.setPassword(DigestUtils.md5DigestAsHex(UserConstant.DEFAULT_PASSWORD.getBytes()));
        userPO.setRole(UserConstant.ROLE_MANAGER);
        userService.save(userPO);

        return ResultVO.ok();
    }

    @ApiOperation(value = "修改管理员")
    @PostMapping("editManager")
    @ApiOperationSupport(includeParameters = {"dto.userId", "dto.nickName", "dto.sex", "dto.phoneNum"})
    public ResultVO editManager(@RequestBody UserDTO dto) {

        UserPO userPO = UserConverter.INSTANCE.convertToPO(dto);
        userService.updateById(userPO);

        return ResultVO.ok();
    }

    @ApiOperation(value = "删除管理员")
    @PostMapping("deleteManager")
    @ApiOperationSupport(includeParameters = {"dto.userIds"})
    public ResultVO deleteManager(@RequestBody UserDTO dto) {

        userService.removeByIds(dto.getUserIds());

        return ResultVO.ok();
    }

    @ApiOperation(value = "查询管理员")
    @PostMapping("queryManager")
    @ApiOperationSupport(includeParameters = {"dto.nickName", "dto.sex", "dto.pageNum", "dto.pageSize"})
    public ResultVO<List<UserVO>> queryManager(@RequestBody UserDTO dto) {

        Page<UserPO> page = userService.page(new Page<>(dto.getPageNum(), dto.getPageSize()), Wrappers.lambdaQuery(UserPO.class)
                .like(!StringUtils.isEmpty(dto.getNickName()), UserPO::getNickName, dto.getNickName())
                .eq(dto.getSex() != null, UserPO::getSex, dto.getSex())
                .eq(UserPO::getRole, UserConstant.ROLE_MANAGER));

        List<UserPO> userPOS = page.getRecords();

        List<UserVO> userVOS = UserConverter.INSTANCE.convertToVO(userPOS);

        return ResultVO.ok().setData(userVOS);
    }


    @ApiOperation(value = "新增求职者")
    @PostMapping("insertJobSeekers")
    @ApiOperationSupport(ignoreParameters = {"dto.userIds", "dto.userId", "dto.pageNum", "dto.pageSize"})
    public ResultVO insertJobSeekers(@RequestBody MaJobSeekersDTO dto) {

        UserPO userPO = UserConverter.INSTANCE.convertToPO(dto);
        userPO.setRole(UserConstant.ROLE_JOB_SEEKERS);
        userPO.setPassword(DigestUtils.md5DigestAsHex(UserConstant.DEFAULT_PASSWORD.getBytes()));
        userService.save(userPO);

        JobSeekersPO jobSeekersPO = JobSeekersConverter.INSTANCE.convertToPO(dto);
        jobSeekersPO.setUserId(userPO.getUserId());
        jobSeekersService.save(jobSeekersPO);

        return ResultVO.ok();
    }

    @ApiOperation(value = "修改求职者")
    @PostMapping("editJobSeekers")
    @ApiOperationSupport(ignoreParameters = {"dto.userIds", "dto.userName", "dto.pageNum", "dto.pageSize"})
    public ResultVO editJobSeekers(@RequestBody MaJobSeekersDTO dto) {

        UserPO userPO = UserConverter.INSTANCE.convertToPO(dto);
        userService.updateById(userPO);

        JobSeekersPO jobSeekersPO = JobSeekersConverter.INSTANCE.convertToPO(dto);
        jobSeekersService.updateById(jobSeekersPO);

        return ResultVO.ok();
    }

    @ApiOperation(value = "删除求职者")
    @PostMapping("deleteJobSeekers")
    @ApiOperationSupport(includeParameters = {"dto.userIds"})
    public ResultVO deleteJobSeekers(@RequestBody MaJobSeekersDTO dto) {

        userService.removeByIds(dto.getUserIds());
        jobSeekersService.removeByIds(dto.getUserIds());

        return ResultVO.ok();
    }

    @ApiOperation(value = "查询求职者")
    @PostMapping("queryJobSeekers")
    @ApiOperationSupport(includeParameters = {"dto.nickName", "dto.sex", "dto.intendedPosition", "dto.intendedPlaceOfWork", "dto.salaryExpectation", "dto.pageNum", "dto.pageSize"})
    public ResultVO<List<MaJobSeekersVO>> queryJobSeekers(@RequestBody MaJobSeekersDTO dto) {

        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        List<MaJobSeekersVO> jobSeekersInfo = userService.getJobSeekersInfo(dto);
        PageInfo<MaJobSeekersVO> page = new PageInfo<>(jobSeekersInfo);

        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), page.getList()));
    }

    @ApiOperation(value = "新增公司")
    @PostMapping("insertCompany")
    @ApiOperationSupport(includeParameters = {"dto.companyName", "dto.companyDetail"})
    public ResultVO insertCompany(@RequestBody CompanyDTO dto) {

        CompanyPO companyPO = CompanyConverter.INSTANCE.convertToPO(dto);
        companyService.save(companyPO);

        return ResultVO.ok();
    }

    @ApiOperation(value = "修改公司")
    @PostMapping("editCompany")
    @ApiOperationSupport(includeParameters = {"dto.companyId", "dto.companyName", "dto.companyDetail"})
    public ResultVO editCompany(@RequestBody CompanyDTO dto) {

        CompanyPO companyPO = CompanyConverter.INSTANCE.convertToPO(dto);
        companyService.updateById(companyPO);

        return ResultVO.ok();
    }

    @ApiOperation(value = "删除公司")
    @PostMapping("deleteCompany")
    @ApiOperationSupport(includeParameters = {"dto.companyIds"})
    public ResultVO deleteCompany(@RequestBody CompanyDTO dto) {

        //删除公司
        companyService.removeByIds(dto.getCompanyIds());
        List<CompanyUserPO> companyUserPOS = companyUserService.list(Wrappers.lambdaQuery(CompanyUserPO.class).in(CompanyUserPO::getCompanyId, dto.getCompanyIds()));
        List<Long> userIds = null;
        if (!CollectionUtils.isEmpty(companyUserPOS)) {
            userIds = companyUserPOS.stream().map(t -> t.getUserId()).collect(Collectors.toList());
            //删除公司用户关联表
            companyUserService.remove(Wrappers.lambdaQuery(CompanyUserPO.class).in(CompanyUserPO::getCompanyId, dto.getCompanyIds()));
            //删除岗位
            positionService.remove(Wrappers.lambdaQuery(PositionPO.class).in(PositionPO::getCompanyId, dto.getCompanyIds()));
        }

        if (!CollectionUtils.isEmpty(userIds)) {
            //删除用户
            userService.removeByIds(userIds);
        }

        return ResultVO.ok();
    }

    @ApiOperation(value = "查询公司")
    @PostMapping("queryCompany")
    @ApiOperationSupport(includeParameters = {"dto.companyName", "dto.pageNum", "dto.pageSize"})
    public ResultVO<List<CompanyPO>> queryCompany(@RequestBody CompanyDTO dto) {

        Page<CompanyPO> page = companyService.page(new Page<>(dto.getPageNum(), dto.getPageSize()), Wrappers.lambdaQuery(CompanyPO.class)
                .like(!StringUtils.isEmpty(dto.getCompanyName()), CompanyPO::getCompanyName, dto.getCompanyName()));

        List<CompanyPO> companyPOS = page.getRecords();

        return ResultVO.ok().setData(companyPOS);
    }

}

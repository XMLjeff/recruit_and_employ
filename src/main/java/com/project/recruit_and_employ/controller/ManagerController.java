package com.project.recruit_and_employ.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.project.recruit_and_employ.constant.UserConstant;
import com.project.recruit_and_employ.dto.*;
import com.project.recruit_and_employ.enums.MessageEnum;
import com.project.recruit_and_employ.mapstruct.CompanyConverter;
import com.project.recruit_and_employ.mapstruct.JobSeekersConverter;
import com.project.recruit_and_employ.mapstruct.PositionConverter;
import com.project.recruit_and_employ.mapstruct.UserConverter;
import com.project.recruit_and_employ.pojo.*;
import com.project.recruit_and_employ.service.*;
import com.project.recruit_and_employ.vo.*;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @ApiOperation(value = "新增公司用户")
    @PostMapping("insertCompanyUser")
    @ApiOperationSupport(includeParameters = {"dto.companyId", "dto.userName", "dto.nickName", "dto.sex", "dto.phoneNum"})
    public ResultVO insertCompanyUser(@RequestBody CompanyUserDTO dto) {

        UserPO user = userService.getOne(Wrappers.lambdaQuery(UserPO.class).eq(UserPO::getUserName, dto.getUserName()));
        if (user != null) {
            return new ResultVO(MessageEnum.USER_EXIST);
        }

        UserPO userPO = UserConverter.INSTANCE.convertToPO(dto);
        userPO.setPassword(DigestUtils.md5DigestAsHex(UserConstant.DEFAULT_PASSWORD.getBytes()));
        userPO.setRole(UserConstant.ROLE_COMPANY);
        userService.save(userPO);

        CompanyUserPO companyUserPO = new CompanyUserPO();
        companyUserPO.setCompanyId(dto.getCompanyId());
        companyUserPO.setUserId(userPO.getUserId());
        companyUserService.save(companyUserPO);

        return ResultVO.ok();
    }

    @ApiOperation(value = "修改公司用户")
    @PostMapping("editCompanyUser")
    @ApiOperationSupport(includeParameters = {"dto.userId", "dto.nickName", "dto.sex", "dto.phoneNum"})
    public ResultVO editCompanyUser(@RequestBody UserDTO dto) {

        UserPO userPO = UserConverter.INSTANCE.convertToPO(dto);
        userService.updateById(userPO);

        return ResultVO.ok();
    }

    @ApiOperation(value = "删除公司用户")
    @PostMapping("deleteCompanyUser")
    @ApiOperationSupport(includeParameters = {"dto.userIds"})
    public ResultVO deleteCompanyUser(@RequestBody UserDTO dto) {

        userService.removeByIds(dto.getUserIds());
        positionService.remove(Wrappers.lambdaQuery(PositionPO.class).in(PositionPO::getUserId, dto.getUserIds()));

        return ResultVO.ok();
    }

    @ApiOperation(value = "查询公司用户")
    @PostMapping("queryCompanyUser")
    @ApiOperationSupport(includeParameters = {"dto.companyName", "dto.nickName", "dto.sex", "dto.pageNum", "dto.pageSize"})
    public ResultVO<List<CompanyUserVO>> queryCompanyUser(@RequestBody CompanyUserDTO dto) {

        List<Long> companyIds = null;
        if (!StringUtils.isEmpty(dto.getCompanyName())) {
            List<CompanyPO> companyPOS = companyService.list(Wrappers.lambdaQuery(CompanyPO.class)
                    .like(CompanyPO::getCompanyName, dto.getCompanyName()));
            if (!CollectionUtils.isEmpty(companyPOS)) {
                companyIds = companyPOS.stream().map(t -> t.getCompanyId()).collect(Collectors.toList());
            }
        }

        List<Long> userIds = null;
        if (!CollectionUtils.isEmpty(companyIds)) {
            List<CompanyUserPO> companyUserPOS = companyUserService.list(Wrappers.lambdaQuery(CompanyUserPO.class).in(CompanyUserPO::getCompanyId, companyIds));
            if (!CollectionUtils.isEmpty(companyUserPOS)) {
                userIds = companyUserPOS.stream().map(t -> t.getUserId()).collect(Collectors.toList());
            }
        }

        Page<UserPO> page = userService.page(new Page<>(dto.getPageNum(), dto.getPageSize()), Wrappers.lambdaQuery(UserPO.class)
                .in(!CollectionUtils.isEmpty(userIds), UserPO::getUserId, userIds)
                .like(!StringUtils.isEmpty(dto.getNickName()), UserPO::getNickName, dto.getNickName())
                .eq(dto.getSex() != null, UserPO::getSex, dto.getSex())
                .eq(UserPO::getRole, UserConstant.ROLE_COMPANY));


        List<UserPO> userPOS = page.getRecords();
        List<Long> userIdList = null;
        if (!CollectionUtils.isEmpty(userPOS)) {
            userIdList = userPOS.stream().map(t -> t.getUserId()).collect(Collectors.toList());
        }
        List<Long> companyIdList = null;
        Map<Long, Long> userCompanyMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(userIdList)) {
            List<CompanyUserPO> list = companyUserService.list(Wrappers.lambdaQuery(CompanyUserPO.class).in(CompanyUserPO::getUserId, userIdList));
            if (!CollectionUtils.isEmpty(list)) {
                companyIdList = list.stream().map(t -> t.getCompanyId()).collect(Collectors.toList());
                userCompanyMap = list.stream().collect(Collectors.toMap(CompanyUserPO::getUserId, CompanyUserPO::getCompanyId));
            }
        }

        Map<Long, String> companyNameMap = new HashMap<>();
        Map<Long, String> companyDetailMap = new HashMap<>();
        List<CompanyPO> companyPOS = null;
        if (!CollectionUtils.isEmpty(companyIdList)) {
            companyPOS = companyService.list(Wrappers.lambdaQuery(CompanyPO.class).in(CompanyPO::getCompanyId, companyIdList));
        }

        if (!CollectionUtils.isEmpty(companyPOS)) {
            companyNameMap = companyPOS.stream().collect(Collectors.toMap(CompanyPO::getCompanyId, CompanyPO::getCompanyName));
            companyDetailMap = companyPOS.stream().collect(Collectors.toMap(CompanyPO::getCompanyId, CompanyPO::getCompanyDetail));
        }
        List<CompanyUserVO> companyUserVOS = CompanyConverter.INSTANCE.convertToVO(userPOS);
        Map<Long, Long> finalUserCompanyMap = userCompanyMap;
        Map<Long, String> finalCompanyNameMap = companyNameMap;
        Map<Long, String> finalCompanyDetailMap = companyDetailMap;
        companyUserVOS.forEach(t -> {
            t.setCompanyId(finalUserCompanyMap.get(t.getUserId()));
            t.setCompanyName(finalCompanyNameMap.get(finalUserCompanyMap.get(t.getUserId())));
            t.setCompanyDetail(finalCompanyDetailMap.get(finalUserCompanyMap.get(t.getUserId())));
        });

        return ResultVO.ok().setData(companyUserVOS);
    }

    @ApiOperation(value = "新增岗位")
    @PostMapping("insertPosition")
    @ApiOperationSupport(ignoreParameters = {"dto.positionId", "dto.pageNum", "dto.pageSize", "dto.companyName", "dto.positionIds"})
    public ResultVO insertPosition(@RequestBody PositionDTO dto) {
        PositionPO positionPO = PositionConverter.INSTANCE.convertToPO(dto);
        CompanyUserPO companyUserPO = companyUserService.getOne(Wrappers.lambdaQuery(CompanyUserPO.class).eq(CompanyUserPO::getUserId, dto.getUserId()));
        positionPO.setCompanyId(companyUserPO.getCompanyId());

        positionService.save(positionPO);

        return ResultVO.ok();
    }

    @ApiOperation(value = "修改岗位")
    @PostMapping("editPosition")
    @ApiOperationSupport(ignoreParameters = {"dto.userId", "dto.pageNum", "dto.pageSize", "dto.companyName", "dto.positionIds"})
    public ResultVO editPosition(@RequestBody PositionDTO dto) {
        PositionPO positionPO = PositionConverter.INSTANCE.convertToPO(dto);
        positionService.updateById(positionPO);

        return ResultVO.ok();
    }

    @ApiOperation(value = "删除岗位")
    @PostMapping("deletePosition")
    @ApiOperationSupport(includeParameters = {"dto.positionIds"})
    public ResultVO deletePosition(@RequestBody PositionDTO dto) {
        positionService.removeByIds(dto.getPositionIds());
        return ResultVO.ok();
    }

    @ApiOperation(value = "查询岗位")
    @PostMapping("queryPosition")
    @ApiOperationSupport(ignoreParameters = {"dto.userId", "dto.positionId", "dto.positionIds"})
    public ResultVO<List<PositionVO>> queryPosition(@RequestBody PositionDTO dto) {

        List<CompanyPO> companyPOList = companyService.list();
        Map<Long, String> companyNameMap = companyPOList.stream().collect(Collectors.toMap(CompanyPO::getCompanyId, CompanyPO::getCompanyName));
        Map<Long, String> companyDetailMap = companyPOList.stream().collect(Collectors.toMap(CompanyPO::getCompanyId, CompanyPO::getCompanyDetail));

        List<UserPO> userPOS = userService.list();
        Map<Long, String> nickNameMap = userPOS.stream().collect(Collectors.toMap(UserPO::getUserId, UserPO::getNickName));
        Map<Long, Integer> sexMap = userPOS.stream().collect(Collectors.toMap(UserPO::getUserId, UserPO::getSex));

        List<CompanyPO> companyPOS = null;
        List<Long> collect = null;
        if (!StringUtils.isEmpty(dto.getCompanyName())) {
            companyPOS = companyService.list(Wrappers.lambdaQuery(CompanyPO.class).like(CompanyPO::getCompanyName, dto.getCompanyName()));
            if (!CollectionUtils.isEmpty(companyPOS)) {
                collect = companyPOS.stream().map(t -> t.getCompanyId()).collect(Collectors.toList());
            }
        }

        LambdaQueryWrapper<PositionPO> wrapper = Wrappers.lambdaQuery(PositionPO.class);
        wrapper.like(!StringUtils.isEmpty(dto.getPositionName()), PositionPO::getPositionName, dto.getPositionName())
                .eq(dto.getPositionCategory() != null, PositionPO::getPositionCategory, dto.getPositionCategory())
                .in(!CollectionUtils.isEmpty(collect), PositionPO::getCompanyId, collect)
                .ge(dto.getPositionSalary() != null, PositionPO::getPositionSalary, dto.getPositionSalary());
        if (dto.getPositionSalary() != null) {
            wrapper.le(PositionPO::getPositionSalary, dto.getPositionSalary().add(new BigDecimal(2000)));
        }
        wrapper.like(!StringUtils.isEmpty(dto.getPlaceOfWork()), PositionPO::getPlaceOfWork, dto.getPlaceOfWork());

        Page<PositionPO> page = positionService.page(new Page<>(dto.getPageNum(), dto.getPageSize()), wrapper);

        List<PositionPO> positionPOS = page.getRecords();

        List<PositionVO> positionVOS = new ArrayList<>();

        for (PositionPO positionPO : positionPOS) {
            PositionVO positionVO = new PositionVO();
            positionVO.setPositionId(positionPO.getPositionId());
            positionVO.setUserId(positionPO.getUserId());
            positionVO.setCompanyDetail(companyDetailMap.get(positionPO.getCompanyId()));
            positionVO.setCompanyName(companyNameMap.get(positionPO.getCompanyId()));
            positionVO.setNickName(nickNameMap.get(positionPO.getUserId()));
            positionVO.setPlaceOfWork(positionPO.getPlaceOfWork());
            positionVO.setPositionCategory(positionPO.getPositionCategory());
            positionVO.setPositionDetail(positionPO.getPositionDetail());
            positionVO.setPositionName(positionPO.getPositionName());
            positionVO.setPositionSalary(positionPO.getPositionSalary());
            positionVO.setSex(sexMap.get(positionPO.getUserId()));
            positionVOS.add(positionVO);
        }

        return ResultVO.ok().setData(positionVOS);
    }
}

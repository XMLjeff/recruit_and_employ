package com.project.recruit_and_employ.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.project.recruit_and_employ.dto.JobSeekersDTO;
import com.project.recruit_and_employ.dto.PositionDTO;
import com.project.recruit_and_employ.mapstruct.JobSeekersConverter;
import com.project.recruit_and_employ.mapstruct.PositionConverter;
import com.project.recruit_and_employ.mapstruct.UserConverter;
import com.project.recruit_and_employ.pojo.*;
import com.project.recruit_and_employ.service.ICompanyUserService;
import com.project.recruit_and_employ.service.IJobSeekersService;
import com.project.recruit_and_employ.service.IPositionService;
import com.project.recruit_and_employ.service.IUserService;
import com.project.recruit_and_employ.vo.JobSeekersVO;
import com.project.recruit_and_employ.vo.PageInfoVO;
import com.project.recruit_and_employ.vo.ResultVO;
import com.project.recruit_and_employ.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ：xmljeff
 * @date ：Created in 2022/3/10 19:59
 * @description：
 * @modified By：
 * @version: $
 */
@RestController
@RequestMapping("company")
@Api(tags = "公司")
public class CompanyController {

    @Autowired
    private IPositionService positionService;
    @Autowired
    private ICompanyUserService companyUserService;
    @Autowired
    private IJobSeekersService jobSeekersService;
    @Autowired
    private IUserService userService;

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
    @ApiOperationSupport(includeParameters = {"dto.userId", "dto.positionName", "dto.positionCategory", "dto.pageNum", "dto.pageSize"})
    public ResultVO<PageInfoVO<PositionPO>> queryPosition(@RequestBody PositionDTO dto) {

        Page<PositionPO> page = positionService.page(new Page<>(dto.getPageNum(), dto.getPageSize()), Wrappers.lambdaQuery(PositionPO.class)
                .eq(PositionPO::getUserId, dto.getUserId())
                .like(!StringUtils.isEmpty(dto.getPositionName()), PositionPO::getPositionName, dto.getPositionName())
                .eq(dto.getPositionCategory() != null, PositionPO::getPositionCategory, dto.getPositionCategory()));

        List<PositionPO> records = page.getRecords();

        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), records));
    }

    @ApiOperation(value = "查询求职者")
    @PostMapping("queryJobSeekers")
    @ApiOperationSupport(includeParameters = {"dto.intendedPosition", "dto.intendedPlaceOfWork", "dto.salaryExpectation", "dto.pageNum", "dto.pageSize"})
    public ResultVO<PageInfoVO<JobSeekersVO>> queryJobSeekers(@RequestBody JobSeekersDTO dto) {

        LambdaQueryWrapper<JobSeekersPO> wrapper = Wrappers.lambdaQuery(JobSeekersPO.class);
        wrapper.like(!StringUtils.isEmpty(dto.getIntendedPosition()), JobSeekersPO::getIntendedPosition, dto.getIntendedPosition())
                .like(!StringUtils.isEmpty(dto.getIntendedPlaceOfWork()), JobSeekersPO::getIntendedPlaceOfWork, dto.getIntendedPlaceOfWork())
                .ge(dto.getSalaryExpectation() != null, JobSeekersPO::getSalaryExpectation, dto.getSalaryExpectation());
        if (dto.getSalaryExpectation() != null) {
            wrapper.le(JobSeekersPO::getSalaryExpectation, dto.getSalaryExpectation().add(new BigDecimal(2000)));
        }

        Page<JobSeekersPO> page = jobSeekersService.page(new Page<>(dto.getPageNum(), dto.getPageSize()), wrapper);

        List<JobSeekersPO> records = page.getRecords();

        List<JobSeekersVO> jobSeekersVOS = new ArrayList<>();
        List<Long> userIds = null;
        if (!CollectionUtils.isEmpty(records)) {
            userIds = records.stream().map(t -> t.getUserId()).collect(Collectors.toList());
            jobSeekersVOS = JobSeekersConverter.INSTANCE.convertToVO(records);
            List<UserPO> userPOS = userService.list(Wrappers.lambdaQuery(UserPO.class).in(UserPO::getUserId, userIds));
            Map<Long, String> userNameMap = userPOS.stream().collect(Collectors.toMap(UserPO::getUserId, UserPO::getUserName));
            Map<Long, String> nickNameMap = userPOS.stream().collect(Collectors.toMap(UserPO::getUserId, UserPO::getNickName));
            Map<Long, Integer> sexMap = userPOS.stream().collect(Collectors.toMap(UserPO::getUserId, UserPO::getSex));
            jobSeekersVOS.forEach(t -> {
                t.setSex(sexMap.get(t.getUserId()));
                t.setNickName(nickNameMap.get(t.getUserId()));
                t.setUserName(userNameMap.get(t.getUserId()));
            });
        }

        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), jobSeekersVOS));
    }

}

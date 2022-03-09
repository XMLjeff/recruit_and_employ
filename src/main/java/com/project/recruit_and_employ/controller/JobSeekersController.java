package com.project.recruit_and_employ.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.project.recruit_and_employ.constant.UserConstant;
import com.project.recruit_and_employ.dto.JobSeekersDTO;
import com.project.recruit_and_employ.dto.PositionDTO;
import com.project.recruit_and_employ.enums.MessageEnum;
import com.project.recruit_and_employ.mapstruct.JobSeekersConverter;
import com.project.recruit_and_employ.pojo.*;
import com.project.recruit_and_employ.service.*;
import com.project.recruit_and_employ.vo.PositionVO;
import com.project.recruit_and_employ.vo.ResultVO;
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
 * @date ：Created in 2022/3/9 20:05
 * @description：求职者
 * @modified By：
 * @version: $
 */
@RestController
@RequestMapping("jobSeekers")
@Api(tags = "求职者")
public class JobSeekersController {

    @Autowired
    private IJobSeekersService jobSeekersService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IPositionService positionService;
    @Autowired
    private ICompanyService companyService;

    @ApiOperation(value = "填写求职者信息")
    @PostMapping("writeJobSeekersInfo")
    @ApiOperationSupport(ignoreParameters = {"dto.pageNum", "dto.pageSize"})
    public ResultVO writeJobSeekersInfo(@RequestBody JobSeekersDTO dto) {

        if (dto.getUserId() == null) {
            return ResultVO.error();
        }

        UserPO userPO = userService.getById(dto.getUserId());
        if (!UserConstant.ROLE_JOB_SEEKERS.equals(userPO.getRole())) {
            return new ResultVO(MessageEnum.ROLE_ERROR);
        }

        JobSeekersPO jobSeekersPO = jobSeekersService.getById(dto.getUserId());
        if (jobSeekersPO == null) {
            jobSeekersService.save(JobSeekersConverter.INSTANCE.convertToPO(dto));
        } else {
            jobSeekersService.updateById(JobSeekersConverter.INSTANCE.convertToPO(dto));
        }

        return ResultVO.ok();
    }

    @ApiOperation(value = "查询求职者信息")
    @PostMapping("queryJobSeekersInfo")
    @ApiOperationSupport(includeParameters = {"dto.userId"})
    public ResultVO<JobSeekersPO> queryJobSeekersInfo(@RequestBody JobSeekersDTO dto) {

        JobSeekersPO jobSeekersPO = jobSeekersService.getById(dto.getUserId());

        return ResultVO.ok().setData(jobSeekersPO);
    }

    @ApiOperation(value = "推荐岗位")
    @PostMapping("recommendPosition")
    @ApiOperationSupport(includeParameters = {"dto.userId", "dto.pageNum", "dto.pageSize"})
    public ResultVO<List<PositionVO>> recommendPosition(@RequestBody JobSeekersDTO dto) {

        List<CompanyPO> companyPOS = companyService.list();
        Map<Long, String> companyNameMap = companyPOS.stream().collect(Collectors.toMap(CompanyPO::getCompanyId, CompanyPO::getCompanyName));
        Map<Long, String> companyDetailMap = companyPOS.stream().collect(Collectors.toMap(CompanyPO::getCompanyId, CompanyPO::getCompanyDetail));

        List<UserPO> userPOS = userService.list();
        Map<Long, String> nickNameMap = userPOS.stream().collect(Collectors.toMap(UserPO::getUserId, UserPO::getNickName));
        Map<Long, Integer> sexMap = userPOS.stream().collect(Collectors.toMap(UserPO::getUserId, UserPO::getSex));

        JobSeekersPO jobSeekersPO = jobSeekersService.getById(dto.getUserId());

        Page<PositionPO> page = positionService.page(new Page<>(dto.getPageNum(), dto.getPageSize()), Wrappers.lambdaQuery(PositionPO.class)
                .like(PositionPO::getPlaceOfWork, jobSeekersPO.getIntendedPlaceOfWork())
                .like(PositionPO::getPositionName, jobSeekersPO.getIntendedPosition())
                .ge(PositionPO::getPositionSalary, jobSeekersPO.getSalaryExpectation())
                .le(PositionPO::getPositionSalary, jobSeekersPO.getSalaryExpectation().add(new BigDecimal(2000))));

        List<PositionPO> positionPOS = page.getRecords();

        List<PositionVO> positionVOS = new ArrayList<>();

        for (PositionPO positionPO : positionPOS) {
            PositionVO positionVO = new PositionVO();
            positionVO.setUserId(positionPO.getUserId());
            positionVO.setCompanyDetail(companyDetailMap.get(positionPO.getCompanyId()));
            positionVO.setCompanyName(companyNameMap.get(positionPO.getCompanyId()));
            positionVO.setNickName(nickNameMap.get(positionPO.getUserId()));
            positionVO.setPlaceOfWork(positionPO.getPlaceOfWork());
            positionVO.setPositionCategory(positionPO.getPositionCategory());
            positionVO.setPositionDetail(positionPO.getPositionDetail());
            positionVO.setPositionName(positionPO.getPositionName());
            positionVO.setPositionSalary(positionPO.getPositionSalary());
            positionVO.setRecruitFlag(positionPO.getRecruitFlag());
            positionVO.setSex(sexMap.get(positionPO.getUserId()));
            positionVOS.add(positionVO);
        }

        return ResultVO.ok().setData(positionVOS);
    }

    @ApiOperation(value = "招聘信息查询")
    @PostMapping("recruitInfo")
    public ResultVO<List<PositionVO>> recruitInfo(@RequestBody PositionDTO dto) {

        List<CompanyPO> companyPOList = companyService.list();
        Map<Long, String> companyNameMap = companyPOList.stream().collect(Collectors.toMap(CompanyPO::getCompanyId, CompanyPO::getCompanyName));
        Map<Long, String> companyDetailMap = companyPOList.stream().collect(Collectors.toMap(CompanyPO::getCompanyId, CompanyPO::getCompanyDetail));

        List<UserPO> userPOS = userService.list();
        Map<Long, String> nickNameMap = userPOS.stream().collect(Collectors.toMap(UserPO::getUserId, UserPO::getNickName));
        Map<Long, Integer> sexMap = userPOS.stream().collect(Collectors.toMap(UserPO::getUserId, UserPO::getSex));

        List<CompanyPO> companyPOS = null;
        if (!StringUtils.isEmpty(dto.getCompanyName())) {
            companyPOS = companyService.list(Wrappers.lambdaQuery(CompanyPO.class).like(CompanyPO::getCompanyName, dto.getCompanyName()));
        }

        Page<PositionPO> page = positionService.page(new Page<>(dto.getPageNum(), dto.getPageSize()), Wrappers.lambdaQuery(PositionPO.class)
                .like(!StringUtils.isEmpty(dto.getPositionName()), PositionPO::getPositionName, dto.getPositionName())
                .eq(dto.getPositionCategory() != null, PositionPO::getPositionCategory, dto.getPositionCategory())
                .in(!CollectionUtils.isEmpty(companyPOS), PositionPO::getCompanyId, companyPOS.stream().map(t -> t.getCompanyId()).collect(Collectors.toList()))
                .ge(dto.getPositionSalary() != null, PositionPO::getPositionSalary, dto.getPositionSalary())
                .le(dto.getPositionSalary() != null, PositionPO::getPositionSalary, dto.getPositionSalary().add(new BigDecimal(2000)))
                .like(!StringUtils.isEmpty(dto.getPlaceOfWork()), PositionPO::getPlaceOfWork, dto.getPlaceOfWork()));

        List<PositionPO> positionPOS = page.getRecords();

        List<PositionVO> positionVOS = new ArrayList<>();

        for (PositionPO positionPO : positionPOS) {
            PositionVO positionVO = new PositionVO();
            positionVO.setUserId(positionPO.getUserId());
            positionVO.setCompanyDetail(companyDetailMap.get(positionPO.getCompanyId()));
            positionVO.setCompanyName(companyNameMap.get(positionPO.getCompanyId()));
            positionVO.setNickName(nickNameMap.get(positionPO.getUserId()));
            positionVO.setPlaceOfWork(positionPO.getPlaceOfWork());
            positionVO.setPositionCategory(positionPO.getPositionCategory());
            positionVO.setPositionDetail(positionPO.getPositionDetail());
            positionVO.setPositionName(positionPO.getPositionName());
            positionVO.setPositionSalary(positionPO.getPositionSalary());
            positionVO.setRecruitFlag(positionPO.getRecruitFlag());
            positionVO.setSex(sexMap.get(positionPO.getUserId()));
            positionVOS.add(positionVO);
        }

        return ResultVO.ok().setData(positionVOS);
    }


}
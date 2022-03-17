package com.project.recruit_and_employ.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.project.recruit_and_employ.constant.Constant;
import com.project.recruit_and_employ.constant.UserConstant;
import com.project.recruit_and_employ.dto.JobSeekersDTO;
import com.project.recruit_and_employ.dto.PositionDTO;
import com.project.recruit_and_employ.enums.MessageEnum;
import com.project.recruit_and_employ.mapstruct.JobSeekersConverter;
import com.project.recruit_and_employ.pojo.*;
import com.project.recruit_and_employ.service.*;
import com.project.recruit_and_employ.utils.UploadFile;
import com.project.recruit_and_employ.vo.PageInfoVO;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

        JobSeekersPO jobSeekersPO = jobSeekersService.getOne(Wrappers.lambdaQuery(JobSeekersPO.class).eq(JobSeekersPO::getUserId, dto.getUserId()));
        if (jobSeekersPO == null) {
            jobSeekersService.save(JobSeekersConverter.INSTANCE.convertToPO(dto));
        } else {
            jobSeekersService.update(Wrappers.lambdaUpdate(JobSeekersPO.class).eq(JobSeekersPO::getUserId, dto.getUserId())
                    .set(!StringUtils.isEmpty(dto.getIntendedPosition()), JobSeekersPO::getIntendedPosition, dto.getIntendedPosition())
                    .set(!StringUtils.isEmpty(dto.getIntendedPlaceOfWork()), JobSeekersPO::getIntendedPlaceOfWork, dto.getIntendedPlaceOfWork())
                    .set(dto.getSalaryExpectation() != null, JobSeekersPO::getSalaryExpectation, dto.getSalaryExpectation())
                    .set(!StringUtils.isEmpty(dto.getScholarshipInfo()), JobSeekersPO::getScholarshipInfo, dto.getScholarshipInfo())
                    .set(!StringUtils.isEmpty(dto.getIntroduction()), JobSeekersPO::getIntroduction, dto.getIntroduction())
                    .set(!StringUtils.isEmpty(dto.getResumeUrl()), JobSeekersPO::getResumeUrl, dto.getResumeUrl()));
        }

        return ResultVO.ok();
    }

    @ApiOperation(value = "显示求职者信息")
    @PostMapping("showJobSeekersInfo")
    @ApiOperationSupport(includeParameters = {"dto.userId"})
    public ResultVO<JobSeekersPO> showJobSeekersInfo(@RequestBody JobSeekersDTO dto) {

        JobSeekersPO jobSeekersPO = jobSeekersService.getOne(Wrappers.lambdaQuery(JobSeekersPO.class).eq(JobSeekersPO::getUserId, dto.getUserId()));

        return ResultVO.ok().setData(jobSeekersPO);
    }

    @ApiOperation(value = "推荐岗位")
    @PostMapping("recommendPosition")
    @ApiOperationSupport(includeParameters = {"dto.userId", "dto.pageNum", "dto.pageSize"})
    public ResultVO<PageInfoVO<PositionVO>> recommendPosition(@RequestBody JobSeekersDTO dto) {

        List<CompanyPO> companyPOS = companyService.list();
        Map<Long, String> companyNameMap = companyPOS.stream().collect(Collectors.toMap(CompanyPO::getCompanyId, CompanyPO::getCompanyName));
        Map<Long, String> companyDetailMap = companyPOS.stream().collect(Collectors.toMap(CompanyPO::getCompanyId, CompanyPO::getCompanyDetail));

        List<UserPO> userPOS = userService.list();
        Map<Long, String> nickNameMap = userPOS.stream().collect(Collectors.toMap(UserPO::getUserId, UserPO::getNickName));
        Map<Long, Integer> sexMap = userPOS.stream().collect(Collectors.toMap(UserPO::getUserId, UserPO::getSex));

        JobSeekersPO jobSeekersPO = jobSeekersService.getOne(Wrappers.lambdaQuery(JobSeekersPO.class).eq(JobSeekersPO::getUserId, dto.getUserId()));

        Page<PositionPO> page = positionService.page(new Page<>(dto.getPageNum(), dto.getPageSize()), Wrappers.lambdaQuery(PositionPO.class)
                .like(PositionPO::getPlaceOfWork, jobSeekersPO.getIntendedPlaceOfWork())
                .like(PositionPO::getPositionName, jobSeekersPO.getIntendedPosition())
                .ge(PositionPO::getPositionSalary, jobSeekersPO.getSalaryExpectation())
                .le(PositionPO::getPositionSalary, jobSeekersPO.getSalaryExpectation().add(new BigDecimal(2000))));

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

        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), positionVOS));
    }

    @ApiOperation(value = "招聘信息查询")
    @PostMapping("recruitInfo")
    @ApiOperationSupport(ignoreParameters = {"dto.userId", "dto.positionId", "dto.positionIds","dto.positionDetail"})
    public ResultVO<PageInfoVO<PositionVO>> recruitInfo(@RequestBody PositionDTO dto) {

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
            wrapper.le(dto.getPositionSalary() != null, PositionPO::getPositionSalary, dto.getPositionSalary().add(new BigDecimal(2000)));
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

        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), positionVOS));
    }

    @ApiOperation(value = "简历上传")
    @PostMapping("uploadResume")
    public ResultVO<String> uploadResume(MultipartFile file) throws IOException {
        Map<String, String> map = UploadFile.upload(Constant.BASE_DIR, file, Constant.DEFAULT_ALLOWED_EXTENSION);
        if (!Constant.SUCCESS.equals(map.get("msg"))) {
            return new ResultVO<>(MessageEnum.FILE_FAIL);
        }

        return ResultVO.ok().setData(map.get("path"));
    }

}
package com.project.recruit_and_employ.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.project.recruit_and_employ.dto.CompanyDTO;
import com.project.recruit_and_employ.pojo.CompanyPO;
import com.project.recruit_and_employ.pojo.CompanyUserPO;
import com.project.recruit_and_employ.service.ICompanyService;
import com.project.recruit_and_employ.service.ICompanyUserService;
import com.project.recruit_and_employ.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ：xmljeff
 * @date ：Created in 2022/3/10 21:20
 * @description：
 * @modified By：
 * @version: $
 */
@RestController
@RequestMapping("base")
@Api(tags = "基础数据")
public class BaseController {

    @Autowired
    private ICompanyService companyService;
    @Autowired
    private ICompanyUserService companyUserService;

    @ApiOperation(value = "得到所有公司")
    @PostMapping("queryAllCompany")
    public ResultVO<List<CompanyPO>> queryAllCompany() {
        List<CompanyPO> list = companyService.list();
        return ResultVO.ok().setData(list);
    }

    @ApiOperation(value = "得到公司下面的所有用户")
    @PostMapping("queryAllCompanyUser")
    @ApiOperationSupport(includeParameters = {"dto.companyId"})
    public ResultVO<List<CompanyUserPO>> queryAllCompanyUser(@RequestBody CompanyDTO dto) {
        List<CompanyUserPO> list = companyUserService.list(Wrappers.lambdaQuery(CompanyUserPO.class).eq(CompanyUserPO::getCompanyId, dto.getCompanyId()));
        return ResultVO.ok().setData(list);
    }
}

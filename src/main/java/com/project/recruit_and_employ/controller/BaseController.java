package com.project.recruit_and_employ.controller;

import com.project.recruit_and_employ.pojo.CompanyPO;
import com.project.recruit_and_employ.service.ICompanyService;
import com.project.recruit_and_employ.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
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

    @ApiOperation(value = "得到所有公司")
    @PostMapping("queryAllCompany")
    public ResultVO<List<CompanyPO>> queryAllCompany() {
        List<CompanyPO> list = companyService.list();
        return ResultVO.ok().setData(list);
    }
}

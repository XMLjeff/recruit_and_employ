package com.project.recruit_and_employ.mapstruct;

import com.project.recruit_and_employ.dto.CompanyDTO;
import com.project.recruit_and_employ.pojo.CompanyPO;
import org.apache.ibatis.annotations.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author ：xmljeff
 * @date ：Created in 2022/3/10 23:13
 * @description：
 * @modified By：
 * @version: $
 */
@Mapper
public interface CompanyConverter {

    CompanyConverter INSTANCE = Mappers.getMapper(CompanyConverter.class);

    @Mappings({})
    CompanyPO convertToPO(CompanyDTO dto);
}

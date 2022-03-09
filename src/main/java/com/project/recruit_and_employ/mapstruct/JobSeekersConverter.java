package com.project.recruit_and_employ.mapstruct;

import com.project.recruit_and_employ.dto.JobSeekersDTO;
import com.project.recruit_and_employ.pojo.JobSeekersPO;
import org.apache.ibatis.annotations.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author ：xmljeff
 * @date ：Created in 2022/3/9 20:15
 * @description：
 * @modified By：
 * @version: $
 */
@Mapper
public interface JobSeekersConverter {

    JobSeekersConverter INSTANCE = Mappers.getMapper(JobSeekersConverter.class);

    @Mappings({})
    JobSeekersPO convertToPO(JobSeekersDTO dto);
}

package com.project.recruit_and_employ.mapstruct;

import com.project.recruit_and_employ.dto.JobSeekersDTO;
import com.project.recruit_and_employ.dto.MaJobSeekersDTO;
import com.project.recruit_and_employ.pojo.JobSeekersPO;
import com.project.recruit_and_employ.vo.JobSeekersVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface JobSeekersConverter {

    JobSeekersConverter INSTANCE = Mappers.getMapper(JobSeekersConverter.class);

    @Mappings({})
    JobSeekersPO convertToPO(JobSeekersDTO dto);

    @Mappings({})
    JobSeekersPO convertToPO(MaJobSeekersDTO dto);

    @Mappings({})
    List<JobSeekersVO> convertToVO(List<JobSeekersPO> pos);
}

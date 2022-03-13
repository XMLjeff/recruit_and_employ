package com.project.recruit_and_employ.mapstruct;

import com.project.recruit_and_employ.dto.JobSeekersDTO;
import com.project.recruit_and_employ.dto.MaJobSeekersDTO;
import com.project.recruit_and_employ.pojo.JobSeekersPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface JobSeekersConverter {

    JobSeekersConverter INSTANCE = Mappers.getMapper(JobSeekersConverter.class);

    @Mappings({})
    JobSeekersPO convertToPO(JobSeekersDTO dto);

    @Mappings({})
    JobSeekersPO convertToPO(MaJobSeekersDTO dto);
}

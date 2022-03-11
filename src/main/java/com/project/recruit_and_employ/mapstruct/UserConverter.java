package com.project.recruit_and_employ.mapstruct;

import com.project.recruit_and_employ.dto.CompanyUserDTO;
import com.project.recruit_and_employ.dto.MaJobSeekersDTO;
import com.project.recruit_and_employ.dto.UserDTO;
import com.project.recruit_and_employ.pojo.UserPO;
import com.project.recruit_and_employ.vo.CompanyUserVO;
import com.project.recruit_and_employ.vo.UserVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserConverter {

    UserConverter INSTANCE = Mappers.getMapper(UserConverter.class);

    @Mappings({})
    UserPO convertToPO(UserDTO dto);

    @Mappings({})
    UserVO convertToVO(UserPO po);

    @Mappings({})
    List<UserVO> convertToVO(List<UserPO> pos);

    @Mappings({})
    UserPO convertToPO(MaJobSeekersDTO dto);

    @Mappings({})
    UserPO convertToPO(CompanyUserDTO dto);


}

package com.project.recruit_and_employ.mapstruct;

import com.project.recruit_and_employ.dto.PositionDTO;
import com.project.recruit_and_employ.pojo.PositionPO;
import com.project.recruit_and_employ.vo.PositionVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PositionConverter {
    PositionConverter INSTANCE = Mappers.getMapper(PositionConverter.class);

    @Mappings({})
    PositionVO convertToVO(PositionPO po);

    @Mappings({})
    List<PositionVO> convertToVO(List<PositionPO> pos);

    @Mappings({})
    PositionPO convertToPO(PositionDTO dto);
}

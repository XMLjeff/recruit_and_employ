package com.project.recruit_and_employ.mapper;

import com.project.recruit_and_employ.dto.MaJobSeekersDTO;
import com.project.recruit_and_employ.pojo.UserPO;
import com.project.recruit_and_employ.vo.MaJobSeekersVO;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @author xmljeff
 * @since 2022-03-08
 */
@Mapper
public interface UserMapper extends BaseMapper<UserPO> {

    List<MaJobSeekersVO> getJobSeekersInfo(MaJobSeekersDTO dto);
}


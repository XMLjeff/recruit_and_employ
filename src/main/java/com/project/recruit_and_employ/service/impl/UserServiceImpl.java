package com.project.recruit_and_employ.service.impl;

import com.project.recruit_and_employ.dto.MaJobSeekersDTO;
import com.project.recruit_and_employ.service.IUserService;
import com.project.recruit_and_employ.mapper.UserMapper;
import com.project.recruit_and_employ.pojo.UserPO;
import com.project.recruit_and_employ.vo.MaJobSeekersVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @author xmljeff
 * @since 2022-03-08
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserPO> implements IUserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public List<MaJobSeekersVO> getJobSeekersInfo(MaJobSeekersDTO dto) {
        return userMapper.getJobSeekersInfo(dto);
    }
}

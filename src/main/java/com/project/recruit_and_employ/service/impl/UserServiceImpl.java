package com.project.recruit_and_employ.service.impl;

import com.project.recruit_and_employ.mapper.UserMapper;
import com.project.recruit_and_employ.service.IUserService;
import com.project.recruit_and_employ.pojo.UserPO;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
/**
 *
 * @author xmljeff
 * @since 2022-03-08
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserPO> implements IUserService {


}

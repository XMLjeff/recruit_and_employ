package com.project.recruit_and_employ.service;

import com.project.recruit_and_employ.dto.MaJobSeekersDTO;
import com.project.recruit_and_employ.pojo.UserPO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.project.recruit_and_employ.vo.MaJobSeekersVO;

import java.util.List;

/**
 * @author xmljeff
 * @since 2022-03-08
 */
public interface IUserService extends IService<UserPO> {

    List<MaJobSeekersVO> getJobSeekersInfo(MaJobSeekersDTO dto);


}

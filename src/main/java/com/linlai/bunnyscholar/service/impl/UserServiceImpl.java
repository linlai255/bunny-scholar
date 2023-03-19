package com.linlai.bunnyscholar.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.linlai.bunnyscholar.entity.User;
import com.linlai.bunnyscholar.mapper.UserMapper;
import com.linlai.bunnyscholar.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author linlai
 * @since 2023-03-18
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public User getByEmail(String email) {
        return this.getBaseMapper().selectOne(new LambdaQueryWrapper<User>().eq(User::getEmail, email));
    }

    @Override
    public User getById(Integer id) {
        return this.getBaseMapper().selectById(id);
    }
}

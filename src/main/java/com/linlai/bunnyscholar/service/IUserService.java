package com.linlai.bunnyscholar.service;

import com.linlai.bunnyscholar.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author linlai
 * @since 2023-03-18
 */
public interface IUserService extends IService<User> {
    User getByEmail(String email);

    User getById(Integer id);
}

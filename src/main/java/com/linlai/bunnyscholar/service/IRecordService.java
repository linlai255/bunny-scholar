package com.linlai.bunnyscholar.service;

import com.linlai.bunnyscholar.entity.Record;
import com.baomidou.mybatisplus.extension.service.IService;
import com.linlai.bunnyscholar.pojo.LoginUser;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author linlai
 * @since 2023-03-18
 */
public interface IRecordService extends IService<Record> {

    String submit(String content, LoginUser user);
}

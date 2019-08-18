package com.wyj.apps.common.spring.mapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * mapper 统一接口
 * Created
 * Author: wyj
 * Date: 2019/8/16
 */
public interface IMapper<T> extends MySqlMapper<T>, Mapper<T> {
}

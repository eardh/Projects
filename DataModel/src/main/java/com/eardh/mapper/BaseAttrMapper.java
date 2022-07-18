package com.eardh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eardh.model.BaseAttribute;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 属性统一接口
 * @author eardh
 * @date 2022/6/15 11:14
 */
@Repository
public interface BaseAttrMapper<T extends BaseAttribute> extends BaseMapper<T>{
}

package com.eardh.mapper;

import com.eardh.model.OomAttribute;
import org.apache.ibatis.annotations.Mapper;

/**
 * 面向对象属性持久化层接口
 * @author eardh
 * @date 2022/6/11 22:40
 */
@Mapper
public interface OomAttrMapper extends BaseAttrMapper<OomAttribute> {
}

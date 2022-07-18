package com.eardh.mapper;

import com.eardh.model.LdmAttribute;
import org.apache.ibatis.annotations.Mapper;

/**
 * 字段逻辑属性持久化层接口
 * @author eardh
 * @date 2022/6/11 22:34
 */
@Mapper
public interface LdmAttrMapper extends BaseAttrMapper<LdmAttribute> {
}

package com.eardh.mapper;

import com.eardh.model.PdmAttribute;
import org.apache.ibatis.annotations.Mapper;

/**
 * 字段物理属性持久化层接口
 * @author eardh
 * @date 2022/6/11 22:39
 */
@Mapper
public interface PdmAttrMapper extends BaseAttrMapper<PdmAttribute> {
}

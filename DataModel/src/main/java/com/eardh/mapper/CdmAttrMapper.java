package com.eardh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eardh.model.CdmAttribute;
import org.apache.ibatis.annotations.Mapper;

/**
 * 字段概念属性持久化层接口
 * @author eardh
 * @date 2022/6/11 22:34
 */
@Mapper
public interface CdmAttrMapper extends BaseAttrMapper<CdmAttribute> {
}

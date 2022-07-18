package com.eardh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eardh.model.Field;
import com.eardh.model.Project;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 字段持久化层接口
 * @author eardh
 * @date 2022/6/11 18:50
 */
@Mapper
public interface FieldMapper extends BaseMapper<Field> {

    /**
     * 获取字段列表，分页或全部
     * @param page {@link Page}
     * @param entityId 实体ID
     * @return 字段列表
     */
    List<Field> getAllOrPageByEntityId(@Param("page") Page<Field> page, @Param("entityId") String entityId);

    /**
     * 获取实体下字段列表，带有各模型属性
     * @param entityId 实体ID
     * @return 字段列表
     */
    List<Field> getFieldWithAttributeByEntityId(@Param("entityId") String entityId);

}

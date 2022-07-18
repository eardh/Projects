package com.eardh.service;

import cn.hutool.core.lang.Editor;
import com.baomidou.mybatisplus.extension.service.IService;
import com.eardh.model.BaseAttribute;
import com.eardh.model.Field;
import com.eardh.model.enums.AttrOprType;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 字段服务层
 * @author eardh
 * @date 2022/6/11 22:32
 */
@Transactional(propagation= Propagation.REQUIRED)
public interface IFieldService extends IService<Field> {

    /**
     * 查询实体下的字段，可进行分页操作
     * @param entityId 实体ID
     * @param currentPage 当前页码
     * @param pageSize 页面大小 0 - 表示查询全部
     * @return 字段列表
     */
    List<Field> getFieldsByEntityId(String entityId, Integer currentPage, Integer pageSize);

    /**
     * 向字段属性表中填充属性
     * @param fieldId 字段ID
     * @param attributes 映射的填充的属性键值对
     * @return 操作是否成功
     */
    <T extends BaseAttribute> boolean addOrUpdate(String fieldId, Map<String, Object> attributes);

    /**
     * 获取字段属性值
     * @param fieldId 字段ID
     * @param attrOprType 操作类型 {@link AttrOprType}
     * @return 获取的字段属性对象
     */
    BaseAttribute selectFieldAttribute(String fieldId, AttrOprType attrOprType);

    /**
     * 删除字段
     * @param fieldId 字段ID
     * @return 操作是否成功
     */
    boolean removeField(String fieldId);

    /**
     * 创建字段
     * @param field {@link Field}
     * @return 操作是否成功
     */
    boolean createField(Field field);

}

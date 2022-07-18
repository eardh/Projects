package com.eardh.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.exceptions.CheckedUtil;
import cn.hutool.core.lang.func.Func1;
import cn.hutool.core.util.EnumUtil;
import cn.hutool.extra.validation.ValidationUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eardh.config.exception.DataOprException;
import com.eardh.config.exception.IllegalOprException;
import com.eardh.mapper.*;
import com.eardh.model.*;
import com.eardh.model.enums.AttrOprType;
import com.eardh.model.enums.IEnum;
import com.eardh.service.IFieldService;
import com.eardh.utils.BeanUtils;
import com.eardh.utils.ValidateUtil;
import com.eardh.validate.Crud;
import com.eardh.validate.ID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.groups.Default;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

import static com.eardh.model.enums.AttrOprType.CDM;
import static com.eardh.model.enums.AttrOprType.LDM;

/**
 * @author eardh
 * @date 2022/6/12 19:06
 */
@Service
@Slf4j
public class FieldServiceImpl extends ServiceImpl<FieldMapper, Field> implements IFieldService {

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private FieldMapper fieldMapper;

    @Autowired
    private CdmAttrMapper cdmAttrMapper;

    @Autowired
    private LdmAttrMapper ldmAttrMapper;

    @Autowired
    private PdmAttrMapper pdmAttrMapper;

    @Autowired
    private OomAttrMapper oomAttrMapper;

    @Override
    public List<Field> getFieldsByEntityId(String entityId, Integer currentPage, Integer pageSize) {
        LambdaQueryWrapper<Entity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Entity::getId, entityId);
        if (!entityMapper.exists(wrapper)) {
            throw new IllegalOprException("操作尚未存在的实体");
        }
        Page<Field> page = null;
        if (pageSize != 0) {
            page = new Page<>(currentPage, pageSize);
        }
        return fieldMapper.getAllOrPageByEntityId(page, entityId);
    }

    @Override
    public <T extends BaseAttribute> boolean  addOrUpdate(String fieldId, Map<String, Object> attributes) {
        LambdaQueryWrapper<Field> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Field::getId, fieldId);
        if (!fieldMapper.exists(wrapper)) {
            throw new IllegalOprException("操作尚未存在的字段");
        }
        boolean r;
        String oprType = attributes.get("attrOprType").toString();
        AttrOprType attrOprType = EnumUtil.getBy(AttrOprType.class, attrOprType1 -> {
            if (attrOprType1.toString().equals(oprType) || attrOprType1.getCode().toString().equals(oprType)) {
                return true;
            }
            return false;
        });
        log.info("设置字段的四种模型的属性值，字段ID：{}，操作类型：{}", fieldId, attrOprType.toString());
        T baseAttribute;
        BaseAttrMapper<T> baseAttrMapper;
        switch (attrOprType) {
            case CDM:
                baseAttribute = (T) BeanUtils.mapToBean(CdmAttribute.class, attributes);
                baseAttrMapper = (BaseAttrMapper<T>) cdmAttrMapper;
                break;
            case LDM:
                baseAttribute = (T) BeanUtils.mapToBean(LdmAttribute.class, attributes);
                baseAttrMapper = (BaseAttrMapper<T>) ldmAttrMapper;
                break;
            case PDM:
                baseAttribute = (T) BeanUtils.mapToBean(PdmAttribute.class, attributes);
                baseAttrMapper = (BaseAttrMapper<T>) pdmAttrMapper;
                break;
            case OOM:
                baseAttribute = (T) BeanUtils.mapToBean(OomAttribute.class, attributes);
                baseAttrMapper = (BaseAttrMapper<T>) oomAttrMapper;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + attrOprType);
        }
        baseAttribute.setFieldId(fieldId);
        T t = baseAttrMapper.selectById(fieldId);
        System.out.println(t);
        r = addOrUpdateXXX(baseAttribute, baseAttrMapper);
        return r;
    }

    @Override
    public BaseAttribute selectFieldAttribute(String fieldId, AttrOprType attrOprType) {
        LambdaQueryWrapper<Field> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Field::getId, fieldId);
        if (!fieldMapper.exists(wrapper)) {
            throw new IllegalOprException("操作尚未存在的字段");
        }
        BaseAttribute ret;
        switch (attrOprType) {
            case CDM:
                ret = cdmAttrMapper.selectById(fieldId);
                break;
            case LDM:
                ret = ldmAttrMapper.selectById(fieldId);
                break;
            case PDM:
                ret = pdmAttrMapper.selectById(fieldId);
                break;
            case OOM:
                ret = oomAttrMapper.selectById(fieldId);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + attrOprType);
        }
        return ret;
    }

    @Override
    public boolean removeField(String fieldId) {
        if (fieldMapper.deleteById(fieldId) == 0) {
            throw new DataOprException("字段删除异常");
        }
        cdmAttrMapper.deleteById(fieldId);
        ldmAttrMapper.deleteById(fieldId);
        pdmAttrMapper.deleteById(fieldId);
        oomAttrMapper.deleteById(fieldId);
        return true;
    }

    @Override
    public boolean createField(Field field) {
        LambdaQueryWrapper<Entity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Entity::getId, field.getEntityId());
        if (!entityMapper.exists(wrapper)) {
            throw new IllegalOprException("操作尚未存在的实体");
        }
        return fieldMapper.insert(field) == 1;
    }

    private <T extends BaseAttribute> boolean addOrUpdateXXX(T t, BaseAttrMapper<T> baseAttrMapper) {
        String fieldId = t.getFieldId();
        int r = 0;
        if (!StringUtils.checkValNull(fieldId)) {
            if (!Objects.isNull(baseAttrMapper.selectById(fieldId))) {
                ValidateUtil.validate(t);
                r = baseAttrMapper.updateById(t);
            } else {
                ValidateUtil.validate(t, Crud.Create.class, Default.class);
                r = baseAttrMapper.insert(t);
            }
        }
        return r == 1;
    }
}

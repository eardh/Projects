package com.eardh.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.eardh.model.BaseAttribute;
import com.eardh.model.CdmAttribute;
import com.eardh.model.Field;
import com.eardh.model.LdmAttribute;
import com.eardh.model.enums.DataType;
import com.eardh.model.enums.SearchType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class LdmAttrMapperTest {

    @Autowired
    private LdmAttrMapper ldmAttrMapper;

    @Test
    public void insert() {
        LdmAttribute ldmAttribute = new LdmAttribute("1535620353618472962", false, false ,false ,false, SearchType.TYPE_1);
        int i = ldmAttrMapper.insert(ldmAttribute);
        Assertions.assertEquals(1, i);
    }

    @Test
    public void delete() {
        LambdaQueryWrapper<LdmAttribute> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LdmAttribute::getFieldId, "1535620353618472961");
        int i = ldmAttrMapper.delete(wrapper);
        Assertions.assertEquals(1, i);
    }

    @Test
    public void update() {
        LambdaUpdateWrapper<LdmAttribute> wrapper = new LambdaUpdateWrapper<>();
        LdmAttribute ldmAttribute = new LdmAttribute("1535620353618472961", true, false ,false ,false, SearchType.TYPE_1);
        wrapper.eq(LdmAttribute::getFieldId, "1535620353618472961");
        int i = ldmAttrMapper.update(ldmAttribute, wrapper);
        Assertions.assertEquals(1, i);
    }

    @Test
    public void query() {
        LambdaQueryWrapper<LdmAttribute> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LdmAttribute::getFieldId, "1535620353618472961");
        LdmAttribute ldmAttribute = ldmAttrMapper.selectOne(wrapper);
        LdmAttribute ldmAttribute1 = new LdmAttribute("1535620353618472961", false, false ,false ,false, SearchType.TYPE_1);
        Assertions.assertEquals(ldmAttribute1, ldmAttribute);
    }

}
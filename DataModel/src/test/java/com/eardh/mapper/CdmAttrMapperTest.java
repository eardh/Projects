package com.eardh.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.eardh.model.CdmAttribute;
import com.eardh.model.Field;
import com.eardh.model.enums.DataType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class CdmAttrMapperTest {

    @Autowired
    private CdmAttrMapper cdmAttrMapper;

    @Test
    public void insert() {
        CdmAttribute cdmAttribute = new CdmAttribute("1535620353618472962", DataType.INT, 10);
        int i = cdmAttrMapper.insert(cdmAttribute);
        Assertions.assertEquals(1, i);
    }

    @Test
    public void delete() {
        LambdaQueryWrapper<CdmAttribute> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CdmAttribute::getFieldId, "1535620353618472961");
        int i = cdmAttrMapper.delete(wrapper);
        Assertions.assertEquals(1, i);
    }

    @Test
    public void update() {
        LambdaUpdateWrapper<CdmAttribute> wrapper = new LambdaUpdateWrapper<>();
        CdmAttribute cdmAttribute = new CdmAttribute("1535620353618472961", DataType.BIGINT, 10);
        wrapper.eq(CdmAttribute::getFieldId, "1535620353618472961");
        int i = cdmAttrMapper.update(cdmAttribute, wrapper);
        Assertions.assertEquals(1, i);
    }

    @Test
    public void query() {
        LambdaQueryWrapper<CdmAttribute> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CdmAttribute::getFieldId, "1535620353618472961");
        CdmAttribute cdmAttribute = cdmAttrMapper.selectOne(wrapper);
        CdmAttribute cdmAttribute1 = new CdmAttribute("1535620353618472961", DataType.INT, 10);
        Assertions.assertEquals(cdmAttribute1, cdmAttribute);
    }

}
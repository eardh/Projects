package com.eardh.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.eardh.model.Field;
import com.eardh.model.LdmAttribute;
import com.eardh.model.PdmAttribute;
import com.eardh.model.enums.DataType;
import com.eardh.model.enums.SearchType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PdmAttrMapperTest {

    @Autowired
    private PdmAttrMapper pdmAttrMapper;

    @Test
    public void insert() {
        PdmAttribute pdmAttribute = new PdmAttribute("1535620353618472962", "stId", DataType.BIGINT, 10, 0, true, true, true, true, null);
        int i = pdmAttrMapper.insert(pdmAttribute);
        Assertions.assertEquals(1, i);
    }

    @Test
    public void delete() {
        LambdaQueryWrapper<PdmAttribute> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PdmAttribute::getFieldId, "1535620353618472961");
        int i = pdmAttrMapper.delete(wrapper);
        Assertions.assertEquals(1, i);
    }

    @Test
    public void update() {
        LambdaUpdateWrapper<PdmAttribute> wrapper = new LambdaUpdateWrapper<>();
        PdmAttribute pdmAttribute = new PdmAttribute("1535620353618472961", "stId", DataType.INT, 10, 0, true, true, true, true, null);
        wrapper.eq(PdmAttribute::getFieldId, "1535620353618472961");
        int i = pdmAttrMapper.update(pdmAttribute, wrapper);
        Assertions.assertEquals(1, i);
    }

    @Test
    public void query() {
        LambdaQueryWrapper<PdmAttribute> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PdmAttribute::getFieldId, "1535620353618472961");
        PdmAttribute pdmAttribute = pdmAttrMapper.selectOne(wrapper);
        PdmAttribute pdmAttribute1 = new PdmAttribute("1535620353618472961", "stId", DataType.BIGINT, 10, 0, true, true, true, true, null);
        Assertions.assertEquals(pdmAttribute1, pdmAttribute);
    }

}
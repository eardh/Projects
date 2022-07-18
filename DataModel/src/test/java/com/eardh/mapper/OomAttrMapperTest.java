package com.eardh.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.eardh.model.Field;
import com.eardh.model.LdmAttribute;
import com.eardh.model.OomAttribute;
import com.eardh.model.enums.SearchType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@Transactional
class OomAttrMapperTest {

    @Autowired
    private OomAttrMapper oomAttrMapper;

    @Test
    public void insert() {
        OomAttribute oomAttribute = new OomAttribute("1535620353618472962", true, true, true,true, true, 10, true);
        int i = oomAttrMapper.insert(oomAttribute);
        Assertions.assertEquals(1, i);
    }

    @Test
    public void delete() {
        LambdaQueryWrapper<OomAttribute> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OomAttribute::getFieldId, "1535620353618472961");
        int i = oomAttrMapper.delete(wrapper);
        Assertions.assertEquals(1, i);
    }

    @Test
    public void update() {
        LambdaUpdateWrapper<OomAttribute> wrapper = new LambdaUpdateWrapper<>();
        OomAttribute oomAttribute = new OomAttribute("1535620353618472961", true, true, true, true, true, 10, true);
        wrapper.eq(OomAttribute::getFieldId, "1535620353618472961");
        int i = oomAttrMapper.update(oomAttribute, wrapper);
        Assertions.assertEquals(1, i);
    }

    @Test
    public void query() {
        LambdaQueryWrapper<OomAttribute> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OomAttribute::getFieldId, "1535620353618472961");
        OomAttribute oomAttribute = oomAttrMapper.selectOne(wrapper);
        OomAttribute oomAttribute1 = new OomAttribute("1535620353618472961", true, true,true, true, true, 10, true);
        Assertions.assertEquals(oomAttribute1, oomAttribute);
    }

}
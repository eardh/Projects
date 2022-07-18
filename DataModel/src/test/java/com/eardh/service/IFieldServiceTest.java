package com.eardh.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.eardh.model.*;
import com.eardh.model.enums.AttrOprType;
import com.eardh.model.enums.DataType;
import com.eardh.model.enums.SearchType;
import com.eardh.utils.BeanUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class IFieldServiceTest {

    @Autowired
    private IFieldService fieldService;

    @Test
    public void save() {
        Field field = new Field(null, "1535617804878344194", "选修课程", "学生选修", null, null);
        boolean b = fieldService.save(field);
        assertTrue(b);
    }

    @Test
    public void remove() {
        LambdaQueryWrapper<Field> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Field::getId, "1535620353618472961");
        boolean remove = fieldService.remove(wrapper);
        assertTrue(remove);
    }

    @Test
    public void update() {
        LambdaUpdateWrapper<Field> wrapper = new LambdaUpdateWrapper<>();
        Field field = new Field(null, "1535617804878344194", "学号", "唯一标识学生", "标识", null);
        wrapper.eq(Field::getId, "1535620353618472961");
        boolean update = fieldService.update(field, wrapper);
        assertTrue(update);
    }

    @Test
    public void getOne() {
        Date date = DateUtil.parse("2022-06-11 21:49:57").toJdkDate();
        LambdaQueryWrapper<Field> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Field::getId, "1535620353618472961");
        Field field = fieldService.getOne(wrapper);
        Field field1 = new Field("1535620353618472961", "1535617804878344194", "学号", "唯一标识学生", "标识", date);
        Assertions.assertEquals(field1, field);
    }

    @Test
    void getFieldsByEntityId() {
        List<Field> fields1 = fieldService.getFieldsByEntityId("1535617804878344194", 0, 0);
        Assertions.assertTrue(CollectionUtil.isNotEmpty(fields1));
        List<Field> fields2 = fieldService.getFieldsByEntityId("1535617804878344194", 1, 1);
        Assertions.assertTrue(CollectionUtil.isNotEmpty(fields2));
    }

    @Test
    void addOrUpdateCDM() {
        // 测试增加CDM
        CdmAttribute cdmAttribute1 = new CdmAttribute(null, DataType.INT, 10);
        Map<String, Object> cdm1 = BeanUtils.beanToMap(cdmAttribute1);
        cdm1.put("attrOprType", AttrOprType.CDM);
        boolean b = fieldService.addOrUpdate("1537322493307105281", cdm1);
        Assertions.assertTrue(b);

        // 测试修改CDM
        CdmAttribute cdmAttribute2 = new CdmAttribute(null, DataType.BIGINT, 10);
        Map<String, Object> cdm2 = BeanUtils.beanToMap(cdmAttribute2);
        cdm2.put("attrOprType", AttrOprType.CDM);
        boolean b1 = fieldService.addOrUpdate("1535620353618472961", cdm2);
        Assertions.assertTrue(b1);
    }

    @Test
    void addOrUpdateLDM() {
        // 测试增加LDM
        LdmAttribute ldmAttribute1 = new LdmAttribute(null, true, false ,false ,false, SearchType.TYPE_1);
        Map<String, Object> ldm1 = BeanUtils.beanToMap(ldmAttribute1);
        ldm1.put("attrOprType", AttrOprType.LDM);
        boolean b = fieldService.addOrUpdate("1537322493307105281", ldm1);
        Assertions.assertTrue(b);

        // 测试修改LDM
        LdmAttribute ldmAttribute2 = new LdmAttribute("1535620353618472961", false, false ,false ,false, SearchType.TYPE_1);
        Map<String, Object> ldm2 = BeanUtils.beanToMap(ldmAttribute2);
        ldm2.put("attrOprType", AttrOprType.LDM);
        boolean b1 = fieldService.addOrUpdate("1535620353618472961", ldm2);
        Assertions.assertTrue(b1);
    }

    @Test
    void addOrUpdatePDM() {
        // 测试增加PDM
        PdmAttribute pdmAttribute1 = new PdmAttribute("1537322493307105281", "stId", DataType.BIGINT, 10, 0, true, true, true, true, null);
        Map<String, Object> pdm1 = BeanUtils.beanToMap(pdmAttribute1);
        pdm1.put("attrOprType", AttrOprType.PDM);
        boolean b = fieldService.addOrUpdate("1537322493307105281", pdm1);
        Assertions.assertTrue(b);

        // 测试修改PDM
        PdmAttribute pdmAttribute2 = new PdmAttribute(null, "stId", DataType.BIGINT, 10, 0, true, true, true, true, null);
        Map<String, Object> pdm2 = BeanUtils.beanToMap(pdmAttribute2);
        pdm2.put("attrOprType", AttrOprType.PDM);
        boolean b1 = fieldService.addOrUpdate("1535620353618472961", pdm2);
        Assertions.assertTrue(b1);
    }

    @Test
    void addOrUpdateOOM() {
        // 测试增加OOM
        OomAttribute oomAttribute1 = new OomAttribute(null, true, true,true, true, true, 10, true);
        Map<String, Object> oom1 = BeanUtils.beanToMap(oomAttribute1);
        oom1.put("attrOprType", AttrOprType.OOM);
        boolean b = fieldService.addOrUpdate("1537322493307105281", oom1);
        Assertions.assertTrue(b);

        // 测试修改OOM
        OomAttribute oomAttribute2 = new OomAttribute(null, true, true,true, true, true, 10, true);
        Map<String, Object> oom2 = BeanUtils.beanToMap(oomAttribute2);
        oom2.put("attrOprType", AttrOprType.OOM);
        boolean b1 = fieldService.addOrUpdate("1535620353618472961", oom2);
        Assertions.assertTrue(b1);
    }

    @Test
    void selectFieldAttribute() {
        CdmAttribute cdmAttribute = new CdmAttribute("1535620353618472961", DataType.INT, 10);
        BaseAttribute baseAttribute1 = fieldService.selectFieldAttribute("1535620353618472961", AttrOprType.CDM);
        Assertions.assertEquals(cdmAttribute, baseAttribute1);

        BaseAttribute baseAttribute2 = fieldService.selectFieldAttribute("1535620353618472961", AttrOprType.LDM);
        LdmAttribute ldmAttribute = new LdmAttribute("1535620353618472961", false, false ,false ,false, SearchType.TYPE_1);
        Assertions.assertEquals(ldmAttribute, baseAttribute2);

        BaseAttribute baseAttribute3 = fieldService.selectFieldAttribute("1535620353618472961", AttrOprType.PDM);
        PdmAttribute pdmAttribute = new PdmAttribute("1535620353618472961", "stId", DataType.BIGINT, 10, 0, true, true, true, true, null);
        Assertions.assertEquals(pdmAttribute, baseAttribute3);

        BaseAttribute baseAttribute4 = fieldService.selectFieldAttribute("1535620353618472961", AttrOprType.OOM);
        OomAttribute oomAttribute = new OomAttribute("1535620353618472961", true,true, true, true, true, 10, true);
        Assertions.assertEquals(oomAttribute, baseAttribute4);
    }

    @Test
    void removeField() {
        boolean b = fieldService.removeField("1535620353618472961");
        Assertions.assertTrue(b);
    }

    @Test
    void createField() {
        Field field = new Field(null, "1535608880359800838", "学号", "唯一标识学生", "标识", null);
        boolean b = fieldService.save(field);
        assertTrue(b);
    }
}
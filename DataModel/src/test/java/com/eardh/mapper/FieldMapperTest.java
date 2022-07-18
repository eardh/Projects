package com.eardh.mapper;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eardh.model.Entity;
import com.eardh.model.Field;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class FieldMapperTest {

    @Autowired
    private FieldMapper fieldMapper;

    private final ObjectMapper objectMapper = new ObjectMapper().setTimeZone(TimeZone.getTimeZone("GMT+8"));

    @Test
    public void insert() {
        Field field = new Field(null, "1535617804878344194", "学号", "唯一标识学生", "标识", null);
        int i = fieldMapper.insert(field);
        Assertions.assertEquals(1, i);
    }

    @Test
    public void delete() {
        LambdaQueryWrapper<Field> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Field::getId, "1535620353618472961");
        int i = fieldMapper.delete(wrapper);
        Assertions.assertEquals(1, i);
    }

    @Test
    public void update() {
        LambdaUpdateWrapper<Field> wrapper = new LambdaUpdateWrapper<>();
        Field field = new Field(null, "1535617804878344194", "学号", "唯一标识学生", "标识", null);
        wrapper.eq(Field::getId, "1535620353618472961");
        int i = fieldMapper.update(field, wrapper);
        Assertions.assertEquals(1, i);
    }

    @Test
    public void query() {
        Date date = DateUtil.parse("2022-06-11 21:49:57").toJdkDate();
        LambdaQueryWrapper<Field> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Field::getId, "1535620353618472961");
        Field field = fieldMapper.selectOne(wrapper);
        Field field1 = new Field("1535620353618472961", "1535617804878344194", "学号", "唯一标识学生", "标识", date);
        Assertions.assertEquals(field1, field);
    }

    @Test
    void getAllOrPageByEntityId() {
        List<Field> fields = fieldMapper.getAllOrPageByEntityId(null, "1535617804878344194");
        Assertions.assertTrue(CollectionUtil.isNotEmpty(fields));
        List<Field> fields1 = fieldMapper.getAllOrPageByEntityId(new Page<>(1, 1), "1535617804878344194");
        Assertions.assertTrue(CollectionUtil.isNotEmpty(fields1));
    }

    @Test
    void getFieldWithAttributeByEntityId() throws JsonProcessingException {
        String json = "[{\"id\":\"1535620353618472961\",\"entityId\":\"1535617804878344194\",\"fieldName\":\"学号\",\"description\":\"唯一标识学生\",\"remark\":\"标识\",\"createTime\":\"2022-06-11 21:49:57\",\"cdmAttribute\":{\"fieldId\":\"1535620353618472961\",\"dataType\":\"INT\",\"dataLength\":10},\"ldmAttribute\":{\"fieldId\":\"1535620353618472961\",\"isForeignKey\":false,\"isSearch\":false,\"isSorted\":false,\"isUnique\":false,\"searchType\":\"TYPE_1\"},\"pdmAttribute\":{\"fieldId\":\"1535620353618472961\",\"codeName\":\"stId\",\"dataType\":\"BIGINT\",\"dataLength\":10,\"dataPrecision\":0,\"primaryKey\":true,\"isForeignKey\":true,\"isUnique\":true,\"notNull\":true,\"defaultValue\":null},\"oomAttribute\":{\"fieldId\":\"1535620353618472961\",\"addReq\":true,\"updateReq\":true,\"deleteReq\":true,\"queryReq\":true,\"isEnum\":true,\"assoProgress\":10,\"progressReq\":true}}]";
        List<Field> fields = fieldMapper.getFieldWithAttributeByEntityId("1535617804878344194");
        Assertions.assertEquals(json, objectMapper.writeValueAsString(fields));
    }
}
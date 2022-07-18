package com.eardh.mapper;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eardh.model.Entity;
import com.eardh.model.Project;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class EntityMapperTest {

    @Autowired
    private EntityMapper entityMapper;

    @Test
    public void insert() {
        Entity entity = new Entity(null, "1535608880359800833", "学生", "student", "记录学生信息", null, null);
        int i = entityMapper.insert(entity);
        Assertions.assertEquals(1, i);
    }

    @Test
    public void delete() {
        LambdaQueryWrapper<Entity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Entity::getId, "1535617804878344194");
        int i = entityMapper.delete(wrapper);
        Assertions.assertEquals(1, i);
    }

    @Test
    public void update() {
        LambdaUpdateWrapper<Entity> wrapper = new LambdaUpdateWrapper<>();
        Entity entity = new Entity(null, "1535617804878344194", "学生表", "student", "记录学生信息", null, null);
        wrapper.eq(Entity::getId, "1535617804878344194");
        int i = entityMapper.update(entity, wrapper);
        Assertions.assertEquals(1, i);
    }

    @Test
    public void query() {
        Date date = DateUtil.parse("2022-06-11 21:39:49").toJdkDate();
        LambdaQueryWrapper<Entity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Entity::getId, "1535617804878344194");
        Entity entity = entityMapper.selectOne(wrapper);
        Entity entity1 = new Entity("1535617804878344194", "1535608880359800833", "学生", "student", "记录学生信息", date, date);
        Assertions.assertEquals(entity1, entity);
    }

    @Test
    public void queryAllOrPageByProjectId() {
        List<Entity> entities = entityMapper.queryAllOrPageByProjectId(null, "1535608880359800833");
        Assertions.assertTrue(CollectionUtil.isNotEmpty(entities));
        List<Entity> entities1 = entityMapper.queryAllOrPageByProjectId(new Page<>(1,1), "1535608880359800833");
        Assertions.assertTrue(CollectionUtil.isNotEmpty(entities1));
    }
}
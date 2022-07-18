package com.eardh.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.eardh.model.Entity;
import com.eardh.model.enums.InterFaceType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class IEntityServiceTest {

    @Autowired
    private IEntityService entityService;

    @Test
    public void save() {
        Entity entity = new Entity(null, "1535608880359800833", "学生", "student", "记录学生信息", null, null);
        boolean b = entityService.save(entity);
        assertTrue(b);
    }

    @Test
    public void remove() {
        LambdaQueryWrapper<Entity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Entity::getId, "1535617804878344194");
        boolean remove = entityService.remove(wrapper);
        assertTrue(remove);
    }

    @Test
    public void update() {
        LambdaUpdateWrapper<Entity> wrapper = new LambdaUpdateWrapper<>();
        Entity entity = new Entity(null, "1535617804878344194", "学生表", "student", "记录学生信息", null, null);
        wrapper.eq(Entity::getId, "1535617804878344194");
        boolean update = entityService.update(entity, wrapper);
        assertTrue(update);
    }

    @Test
    public void getOne() {
        Date date = DateUtil.parse("2022-06-11 21:39:49").toJdkDate();
        LambdaQueryWrapper<Entity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Entity::getId, "1535617804878344194");
        Entity entity = entityService.getOne(wrapper);
        Entity entity1 = new Entity("1535617804878344194", "1535608880359800833", "学生", "student", "记录学生信息", date, date);
        Assertions.assertEquals(entity1, entity);
    }

    @Test
    public void getEntitiesByProjectId() {
        List<Entity> entities1 = entityService.getEntitiesByProjectId("1535608880359800833", 0, 0);
        Assertions.assertTrue(CollectionUtil.isNotEmpty(entities1));
        List<Entity> entities2 = entityService.getEntitiesByProjectId("1535608880359800833", 1, 1);
        Assertions.assertTrue(CollectionUtil.isNotEmpty(entities2));
    }

    @Test
    void createEntity() {
        Entity entity = new Entity(null, "1535608880359800833", "学生", "student", "记录学生信息", null, null);
        boolean b = entityService.createEntity(entity);
        Assertions.assertTrue(b);
    }

    @Test
    void removeEntity() {
        boolean b = entityService.removeEntity("1535617804878344194");
        Assertions.assertTrue(b);
    }

    @Test
    void dataStructureDocs() throws FileNotFoundException {
        entityService.dataStructureDocs("1535617804878344194");
    }

    @Test
    void interfaceList() {
        List<Map<Object, Object>> maps = entityService.interfaceList("1535617804878344194");
        System.out.println(maps);
    }

    @Test
    void interfaceDocs() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, FileNotFoundException {
        String s = entityService.interfaceDocs("1535617804878344194", InterFaceType.PROGRESS_CHANGE);
        System.out.println(s);
    }
}
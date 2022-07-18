package com.eardh.mapper;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eardh.model.Entity;
import com.eardh.model.Project;
import com.eardh.utils.Constant;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;

@SpringBootTest
@Transactional
class ProjectMapperTest {

    @Autowired
    private ProjectMapper projectMapper;

    @Test
    public void insert() {
        Project project = new Project(null, Constant.userId, "数据建模", "数据模型生成", null, null);
        int i = projectMapper.insert(project);
        Assertions.assertEquals(1, i);
    }

    @Test
    public void delete() {
        LambdaQueryWrapper<Project> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Project::getId, "1535608880359800833");
        int i = projectMapper.delete(wrapper);
        Assertions.assertEquals(1, i);
    }

    @Test
    public void update() {
        LambdaUpdateWrapper<Project> wrapper = new LambdaUpdateWrapper<>();
        Project project = new Project(null, Constant.userId, "数据建模", "数据模型", null, null);
        wrapper.eq(Project::getId, "1535608880359800833");
        int i = projectMapper.update(project, wrapper);
        Assertions.assertEquals(1, i);
    }

    @Test
    public void query() {
        Date date = DateUtil.parse("2022-06-11 21:04:21").toJdkDate();
        LambdaQueryWrapper<Project> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Project::getId, "1535608880359800833");
        Project project = projectMapper.selectOne(wrapper);
        Project project1 = new Project("1535608880359800833", Constant.userId, "数据建模", "数据模型生成", date, date);
        Assertions.assertEquals(project1, project);
    }

    @Test
    public void queryAllOrPageByUserId() {
        List<Project> projects = projectMapper.queryAllOrPageByUserId(null, Constant.userId);
        Assertions.assertTrue(CollectionUtil.isNotEmpty(projects));
        List<Project> projects1 = projectMapper.queryAllOrPageByUserId(new Page<>(1,1), Constant.userId);
        Assertions.assertTrue(CollectionUtil.isNotEmpty(projects1));
    }
}
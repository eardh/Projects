package com.eardh.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.eardh.model.Answer;
import com.eardh.model.Entity;
import com.eardh.model.Project;
import com.eardh.utils.Constant;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class IProjectServiceTest {

    @Autowired
    private IProjectService projectService;

    @Test
    public void save() {
        Project project = new Project(null, Constant.userId, "数据建模", "数据模型生成", null, null);
        boolean b = projectService.save(project);
        assertTrue(b);
    }

    @Test
    public void remove() {
        LambdaQueryWrapper<Project> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Project::getId, "1535608880359800833");
        boolean remove = projectService.remove(wrapper);
        assertTrue(remove);
    }

    @Test
    public void update() {
        LambdaUpdateWrapper<Project> wrapper = new LambdaUpdateWrapper<>();
        Project project = new Project(null, Constant.userId, "数据建模", "数据模型PP", null, null);
        wrapper.eq(Project::getId, "1535608880359800833");
        boolean update = projectService.update(project, wrapper);
        assertTrue(update);
    }

    @Test
    public void getOne() {
        Date date = DateUtil.parse("2022-06-11 21:04:21").toJdkDate();
        LambdaQueryWrapper<Project> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Project::getId, "1535608880359800833");
        Project project = projectService.getOne(wrapper);
        Project project1 = new Project("1535608880359800833", Constant.userId, "数据建模", "数据模型生成", date, date);
        Assertions.assertEquals(project1, project);
    }

    @Test
    public void getProjectsByUserId() {
        List<Project> projects1 = projectService.getProjectsByUserId(Constant.userId, 0, 0);
        Assertions.assertTrue(CollectionUtil.isNotEmpty(projects1));
        List<Project> projects2 = projectService.getProjectsByUserId(Constant.userId, 1, 1);
        Assertions.assertTrue(CollectionUtil.isNotEmpty(projects2));
    }

    @Test
    void removeProject() {
        boolean b = projectService.removeProject("1535608880359800833");
        Assertions.assertTrue(b);
    }
}
package com.eardh.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONUtil;
import com.eardh.model.Answer;
import com.eardh.model.Entity;
import com.eardh.model.Project;
import com.eardh.utils.Constant;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.ContentResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.StatusResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class ProjectControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeAll
    void setUp(@Autowired MockMvc mockMvc) {
        this.mockMvc = mockMvc;
        objectMapper = new ObjectMapper().setTimeZone(TimeZone.getTimeZone("GMT+8"));
    }

    @Test
    void getProjects() throws Exception {
        Date date = DateUtil.parse("2022-06-11 21:04:21").toJdkDate();
        List<Project> list = new ArrayList<>();
        list.add(new Project("1535608880359800833", Constant.userId, "数据建模", "数据模型生成", date, date));
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get("/project");
        StatusResultMatchers status = MockMvcResultMatchers.status();
        ContentResultMatchers content = MockMvcResultMatchers.content();
        ResultActions actions = mockMvc
                .perform(builder)
                .andExpect(status.isOk())
                .andExpect(content.json(objectMapper.writeValueAsString(Answer.success(list))));

        MockHttpServletRequestBuilder builder2 = MockMvcRequestBuilders
                .get("/project")
                .queryParam("currentPage", "1")
                .queryParam("pageSize", "1");
        ResultActions actions2 = mockMvc
                .perform(builder2)
                .andExpect(status.isOk())
                .andExpect(content.json(objectMapper.writeValueAsString(Answer.success(list))));
    }

    @Test
    void createProject() throws Exception {
        Project project = new Project(null, null, "数据建模", "数据模型生成", null, null);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .post("/project")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(project));
        StatusResultMatchers status = MockMvcResultMatchers.status();
        ContentResultMatchers content = MockMvcResultMatchers.content();
        ResultActions actions = mockMvc
                .perform(builder)
                .andExpect(status.isOk());
    }

    @Test
    void updateProject() throws Exception {
        Project project = new Project(null, null, "数据建模项目", "数据模型生成", null, null);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .put("/project/{projectId}", "1535608880359800833")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(project));
        StatusResultMatchers status = MockMvcResultMatchers.status();
        ContentResultMatchers content = MockMvcResultMatchers.content();
        ResultActions actions = mockMvc
                .perform(builder)
                .andExpect(status.isOk())
                .andExpect(content.json(objectMapper.writeValueAsString(Answer.success())));
    }

    @Test
    void removeProject() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .delete("/project/{projectId}", "1535608880359800833")
                .contentType("application/json");
        StatusResultMatchers status = MockMvcResultMatchers.status();
        ContentResultMatchers content = MockMvcResultMatchers.content();
        ResultActions actions = mockMvc
                .perform(builder)
                .andExpect(status.isOk())
                .andExpect(content.json(objectMapper.writeValueAsString(Answer.success())));
    }

    @Test
    void getProjectDetail() throws Exception {
        Date date = DateUtil.parse("2022-06-11 21:04:21").toJdkDate();
        Project expect = new Project("1535608880359800833", Constant.userId, "数据建模", "数据模型生成", date, date);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get("/project/{projectId}", "1535608880359800833")
                .contentType("application/json");
        StatusResultMatchers status = MockMvcResultMatchers.status();
        ContentResultMatchers content = MockMvcResultMatchers.content();
        ResultActions actions = mockMvc
                .perform(builder)
                .andExpect(status.isOk())
                .andExpect(content.json(objectMapper.writeValueAsString(Answer.success(expect))));
    }
}
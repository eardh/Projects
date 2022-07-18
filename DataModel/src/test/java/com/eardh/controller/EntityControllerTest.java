package com.eardh.controller;

import cn.hutool.core.date.DateUtil;
import com.eardh.model.Answer;
import com.eardh.model.Entity;
import com.eardh.model.Project;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class EntityControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeAll
    void setUp(@Autowired MockMvc mockMvc) {
        this.mockMvc = mockMvc;
        objectMapper = new ObjectMapper().setTimeZone(TimeZone.getTimeZone("GMT+8"));
    }

    @Test
    void getEntityList() throws Exception {

        Date date = DateUtil.parse("2022-06-11 21:39:49").toJdkDate();
        List<Entity> list = new ArrayList<>();
        list.add(new Entity("1535617804878344194", "1535608880359800833", "学生", "student", "记录学生信息", date, date));
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get("/entity")
                .queryParam("projectId", "1535608880359800833");
        StatusResultMatchers status = MockMvcResultMatchers.status();
        ContentResultMatchers content = MockMvcResultMatchers.content();
        System.out.println(new ObjectMapper().writeValueAsString(Answer.success(list)));
        ResultActions actions = mockMvc
                .perform(builder)
                .andExpect(status.isOk())
                .andExpect(content.json(objectMapper.writeValueAsString(Answer.success(list))));

        MockHttpServletRequestBuilder builder2 = MockMvcRequestBuilders
                .get("/entity")
                .queryParam("projectId", "1535608880359800833")
                .queryParam("currentPage", "1")
                .queryParam("pageSize", "1");
        ResultActions actions2 = mockMvc
                .perform(builder2)
                .andExpect(status.isOk())
                .andExpect(content.json(objectMapper.writeValueAsString(Answer.success(list))));

    }

    @Test
    void createEntity() throws Exception {
        Entity entity = new Entity(null, "1535608880359800833", "学生", "student", "记录学生信息", null, null);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .post("/entity")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(entity));
        StatusResultMatchers status = MockMvcResultMatchers.status();
        ContentResultMatchers content = MockMvcResultMatchers.content();
        ResultActions actions = mockMvc
                .perform(builder)
                .andExpect(status.isOk())
                .andExpect(content.json(objectMapper.writeValueAsString(Answer.success())));
    }

    @Test
    void updateEntity() throws Exception {
        Entity entity = new Entity(null, "1535617804878344194", "学生表", "student", "记录学生信息", null, null);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .put("/entity/{entityId}", "1535617804878344194")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(entity));
        StatusResultMatchers status = MockMvcResultMatchers.status();
        ContentResultMatchers content = MockMvcResultMatchers.content();
        ResultActions actions = mockMvc
                .perform(builder)
                .andExpect(status.isOk())
                .andExpect(content.json(objectMapper.writeValueAsString(Answer.success())));

    }

    @Test
    void removeEntity() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .delete("/entity/{entityId}", "1535617804878344194")
                .contentType("application/json");
        StatusResultMatchers status = MockMvcResultMatchers.status();
        ContentResultMatchers content = MockMvcResultMatchers.content();
        ResultActions actions = mockMvc
                .perform(builder)
                .andExpect(status.isOk());
    }

    @Test
    void getEntityDetail() throws Exception {
        Date date = DateUtil.parse("2022-06-11 21:39:49").toJdkDate();
        Entity entity = new Entity("1535617804878344194", "1535608880359800833", "学生", "student", "记录学生信息", date, date);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get("/entity/{entityId}", "1535617804878344194")
                .contentType("application/json");
        StatusResultMatchers status = MockMvcResultMatchers.status();
        ContentResultMatchers content = MockMvcResultMatchers.content();
        ResultActions actions = mockMvc
                .perform(builder)
                .andExpect(status.isOk())
                .andExpect(content.json(objectMapper.writeValueAsString(Answer.success(entity))));
    }

    @Test
    void getDataStructDocs() {
    }

    @Test
    void getInterfaceList() {
    }

    @Test
    void getInterfaceDocs() {
    }
}
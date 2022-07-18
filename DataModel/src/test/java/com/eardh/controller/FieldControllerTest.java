package com.eardh.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.unit.DataUnit;
import com.eardh.model.*;
import com.eardh.model.enums.AttrOprType;
import com.eardh.model.enums.DataType;
import com.eardh.model.enums.SearchType;
import com.eardh.utils.BeanUtils;
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

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class FieldControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeAll
    void setUp(@Autowired MockMvc mockMvc) {
        this.mockMvc = mockMvc;
        objectMapper = new ObjectMapper().setTimeZone(TimeZone.getTimeZone("GMT+8"));
    }

    @Test
    void getFieldList() throws Exception {
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
    void createField() throws Exception {
        Field field = new Field(null, "1535617804878344194", "学号", "唯一标识学生", "标识", null);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .post("/field")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(field));
        StatusResultMatchers status = MockMvcResultMatchers.status();
        ContentResultMatchers content = MockMvcResultMatchers.content();
        ResultActions actions = mockMvc
                .perform(builder)
                .andExpect(status.isOk());
    }

    @Test
    void updateField() throws Exception {
        Field field = new Field(null, "1535617804878344194", "学号", "唯一标识学生", "标识", null);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .put("/field/{fieldId}", "1535620353618429611")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(field));
        StatusResultMatchers status = MockMvcResultMatchers.status();
        ContentResultMatchers content = MockMvcResultMatchers.content();
        ResultActions actions = mockMvc
                .perform(builder)
                .andExpect(status.isOk())
                .andExpect(content.json(objectMapper.writeValueAsString(Answer.success())));
    }

    @Test
    void removeField() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .delete("/field/{fieldId}", "1535620353618472961")
                .contentType("application/json");
        StatusResultMatchers status = MockMvcResultMatchers.status();
        ContentResultMatchers content = MockMvcResultMatchers.content();
        ResultActions actions = mockMvc
                .perform(builder)
                .andExpect(status.isOk())
                .andExpect(content.json(objectMapper.writeValueAsString(Answer.success())));
    }

    @Test
    void getFieldDetail() throws Exception {
        Date date = DateUtil.parse("2022-06-11 21:49:57").toJdkDate();
        Field field = new Field("1535620353618472961", "1535617804878344194", "学号", "唯一标识学生", "标识", date);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get("/field/{fieldId}", "1535620353618472961")
                .contentType("application/json");
        StatusResultMatchers status = MockMvcResultMatchers.status();
        ContentResultMatchers content = MockMvcResultMatchers.content();
        ResultActions actions = mockMvc
                .perform(builder)
                .andExpect(status.isOk())
                .andExpect(content.json(objectMapper.writeValueAsString(Answer.success(field))));
    }

    @Test
    void getCDMAttribute() throws Exception {
        CdmAttribute cdmAttribute = new CdmAttribute("1535620353618472961", DataType.INT, 10);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get("/field/{fieldId}/attribute", "1535620353618472961")
                .contentType("application/json")
                .queryParam("attrOprType", String.valueOf(AttrOprType.CDM));
        StatusResultMatchers status = MockMvcResultMatchers.status();
        ContentResultMatchers content = MockMvcResultMatchers.content();
        ResultActions actions = mockMvc
                .perform(builder)
                .andExpect(status.isOk())
                .andExpect(content.json(objectMapper.writeValueAsString(Answer.success(cdmAttribute))));
    }

    @Test
    void getLDMAttribute() throws Exception {
        LdmAttribute ldmAttribute = new LdmAttribute("1535620353618472961", false, false ,false ,false, SearchType.TYPE_1);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get("/field/{fieldId}/attribute", "1535620353618472961")
                .contentType("application/json")
                .queryParam("attrOprType", String.valueOf(AttrOprType.LDM));
        StatusResultMatchers status = MockMvcResultMatchers.status();
        ContentResultMatchers content = MockMvcResultMatchers.content();
        ResultActions actions = mockMvc
                .perform(builder)
                .andExpect(status.isOk())
                .andExpect(content.json(objectMapper.writeValueAsString(Answer.success(ldmAttribute))));
    }

    @Test
    void getPDMAttribute() throws Exception {
        PdmAttribute pdmAttribute = new PdmAttribute("1535620353618472961", "stId", DataType.BIGINT, 10, 0, true, true, true, true, null);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get("/field/{fieldId}/attribute", "1535620353618472961")
                .contentType("application/json")
                .queryParam("attrOprType", String.valueOf(AttrOprType.PDM));
        StatusResultMatchers status = MockMvcResultMatchers.status();
        ContentResultMatchers content = MockMvcResultMatchers.content();
        ResultActions actions = mockMvc
                .perform(builder)
                .andExpect(status.isOk())
                .andExpect(content.json(objectMapper.writeValueAsString(Answer.success(pdmAttribute))));
    }

    @Test
    void getOOMAttribute() throws Exception {
        OomAttribute oomAttribute = new OomAttribute("1535620353618472961", true, true,true, true, true, 10, true);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get("/field/{fieldId}/attribute", "1535620353618472961")
                .contentType("application/json")
                .queryParam("attrOprType", String.valueOf(AttrOprType.OOM));
        StatusResultMatchers status = MockMvcResultMatchers.status();
        ContentResultMatchers content = MockMvcResultMatchers.content();
        ResultActions actions = mockMvc
                .perform(builder)
                .andExpect(status.isOk())
                .andExpect(content.json(objectMapper.writeValueAsString(Answer.success(oomAttribute))));
    }


    @Test
    void setCDMAttribute() throws Exception {
        // 更新CDM
        CdmAttribute cdmAttribute = new CdmAttribute(null, DataType.BIGINT, 10);
        Map<String, Object> map = BeanUtils.beanToMap(cdmAttribute);
        map.put("attrOprType", AttrOprType.CDM);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .post("/field/{fieldId}/attribute", "1535620353618472961")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(map));
        StatusResultMatchers status = MockMvcResultMatchers.status();
        ContentResultMatchers content = MockMvcResultMatchers.content();
        ResultActions actions = mockMvc
                .perform(builder)
                .andExpect(status.isOk())
                .andExpect(content.json(objectMapper.writeValueAsString(Answer.success())));

        //  插入CDM
        CdmAttribute cdmAttribute1 = new CdmAttribute(null, DataType.BIGINT, 10);
        Map<String, Object> map1 = BeanUtils.beanToMap(cdmAttribute1);
        map1.put("attrOprType", AttrOprType.CDM);
        MockHttpServletRequestBuilder builder1 = MockMvcRequestBuilders
                .post("/field/{fieldId}/attribute", "1537322493307105281")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(map1));
        ResultActions actions1 = mockMvc
                .perform(builder1)
                .andExpect(status.isOk())
                .andExpect(content.json(objectMapper.writeValueAsString(Answer.success())));
    }

    @Test
    void setLDMAttribute() throws Exception {
        // 更新LDM
        LdmAttribute ldmAttribute = new LdmAttribute(null, true, false ,false ,false, SearchType.TYPE_1);
        Map<String, Object> map = BeanUtils.beanToMap(ldmAttribute);
        map.put("attrOprType", AttrOprType.LDM);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .post("/field/{fieldId}/attribute", "1535620353618472961")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(map));
        StatusResultMatchers status = MockMvcResultMatchers.status();
        ContentResultMatchers content = MockMvcResultMatchers.content();
        ResultActions actions = mockMvc
                .perform(builder)
                .andExpect(status.isOk())
                .andExpect(content.json(objectMapper.writeValueAsString(Answer.success())));

        //  插入LDM
        LdmAttribute ldmAttribute1 = new LdmAttribute(null, true, false ,false ,false, SearchType.TYPE_1);
        Map<String, Object> map1 = BeanUtils.beanToMap(ldmAttribute1);
        map1.put("attrOprType", AttrOprType.LDM);
        MockHttpServletRequestBuilder builder1 = MockMvcRequestBuilders
                .post("/field/{fieldId}/attribute", "1537322493307105281")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(map1));
        ResultActions actions1 = mockMvc
                .perform(builder1)
                .andExpect(status.isOk())
                .andExpect(content.json(objectMapper.writeValueAsString(Answer.success())));
    }

    @Test
    void setPDMAttribute() throws Exception {
        // 更新PDM
        PdmAttribute pdmAttribute = new PdmAttribute("1535620353618472961", "stId", DataType.INT, 10, 0, true, true, true, true, null);
        Map<String, Object> map = BeanUtils.beanToMap(pdmAttribute);
        map.put("attrOprType", AttrOprType.PDM);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .post("/field/{fieldId}/attribute", "1535620353618472961")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(map));
        StatusResultMatchers status = MockMvcResultMatchers.status();
        ContentResultMatchers content = MockMvcResultMatchers.content();
        ResultActions actions = mockMvc
                .perform(builder)
                .andExpect(status.isOk())
                .andExpect(content.json(objectMapper.writeValueAsString(Answer.success())));

        //  插入PDM
        PdmAttribute pdmAttribute1 = new PdmAttribute("1535620353618472961", "stId", DataType.INT, 10, 0, true, true, true, true, null);
        Map<String, Object> map1 = BeanUtils.beanToMap(pdmAttribute1);
        map1.put("attrOprType", AttrOprType.PDM);
        MockHttpServletRequestBuilder builder1 = MockMvcRequestBuilders
                .post("/field/{fieldId}/attribute", "1537322493307105281")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(map1));
        ResultActions actions1 = mockMvc
                .perform(builder1)
                .andExpect(status.isOk())
                .andExpect(content.json(objectMapper.writeValueAsString(Answer.success())));
    }

    @Test
    void setOOMAttribute() throws Exception {
        // 更新PDM
        OomAttribute oomAttribute = new OomAttribute("1535620353618472961", true, true,true, true, true, 10, true);
        Map<String, Object> map = BeanUtils.beanToMap(oomAttribute);
        map.put("attrOprType", AttrOprType.OOM);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .post("/field/{fieldId}/attribute", "1535620353618472961")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(map));
        StatusResultMatchers status = MockMvcResultMatchers.status();
        ContentResultMatchers content = MockMvcResultMatchers.content();
        ResultActions actions = mockMvc
                .perform(builder)
                .andExpect(status.isOk())
                .andExpect(content.json(objectMapper.writeValueAsString(Answer.success())));

        //  插入PDM
        OomAttribute oomAttribute1 = new OomAttribute("1535620353618472961", true, true, true,true, true, 10, true);
        Map<String, Object> map1 = BeanUtils.beanToMap(oomAttribute1);
        map1.put("attrOprType", AttrOprType.OOM);
        MockHttpServletRequestBuilder builder1 = MockMvcRequestBuilders
                .post("/field/{fieldId}/attribute", "1537322493307105281")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(map1));
        ResultActions actions1 = mockMvc
                .perform(builder1)
                .andExpect(status.isOk())
                .andExpect(content.json(objectMapper.writeValueAsString(Answer.success())));
    }
}
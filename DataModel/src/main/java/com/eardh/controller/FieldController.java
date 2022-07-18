package com.eardh.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.cron.pattern.CronPatternUtil;
import cn.hutool.extra.tokenizer.Result;
import cn.hutool.http.HttpResponse;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.eardh.mapper.FieldMapper;
import com.eardh.model.Answer;
import com.eardh.model.CdmAttribute;
import com.eardh.model.Entity;
import com.eardh.model.Field;
import com.eardh.model.enums.AttrOprType;
import com.eardh.service.IFieldService;
import com.eardh.validate.Crud;
import com.eardh.validate.ID;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.apache.tomcat.util.http.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

import javax.validation.constraints.Pattern;
import javax.validation.groups.Default;
import java.util.List;
import java.util.Map;

/**
 * 字段操作接口
 * @author eardh
 * @date 2022/6/12 19:21
 */
@RestController
@RequestMapping("/field")
@Validated
@Slf4j
public class FieldController {

    @Autowired
    private IFieldService fieldService;

    /**
     * 获取字段列表
     * @param entityId 项目ID
     * @param currentPage 当前页面
     * @param pageSize 页面大小
     * @return 响应体
     */
    @GetMapping
    public Answer getFieldList(@ID @RequestParam("entityId") String entityId,
                               @RequestParam(value = "currentPage", required = false) Integer currentPage,
                               @RequestParam(value = "pageSize", defaultValue = "0", required = false) Integer pageSize) {
        log.info("获取字段列表，实体ID：{}", entityId);
        List<Field> fields = fieldService.getFieldsByEntityId(entityId, currentPage, pageSize);
        return Answer.success(fields);
    }

    /**
     * 创建字段
     * @param field {@link Field}
     * @return 响应体
     */
    @PostMapping
    public Answer createField(@Validated(value = {Crud.Create.class, Default.class}) @RequestBody Field field) {
        log.info("创建字段，实体ID：{}", field.getEntityId());
        boolean save = fieldService.createField(field);
        if (!save) {
            Answer.fail("操作失败");
        }
        return Answer.success(MapUtil.builder().put("fieldId", field.getId()).build());
    }

    /**
     * 更新字段
     * @param fieldId 字段ID
     * @param field {@link Field}
     * @return 响应体
     */
    @PutMapping("/{fieldId}")
    public Answer updateField(@ID @PathVariable String fieldId,
                              @Validated @RequestBody Field field) {
        log.info("更新字段，字段ID：{}", fieldId);
        LambdaUpdateWrapper<Field> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Field::getId, fieldId);
        boolean update = fieldService.update(field, wrapper);
        if (!update) {
            Answer.fail("操作失败");
        }
        return Answer.success();
    }

    /**
     * 删除字段
     * @param fieldId
     * @return
     */
    @DeleteMapping("/{fieldId}")
    public Answer removeField(@ID @PathVariable String fieldId) {
        log.info("删除字段，字段ID：{}", fieldId);
        boolean b = fieldService.removeField(fieldId);
        if (!b) {
            Answer.fail("操作失败");
        }
        return Answer.success();
    }

    /**
     * 获取字段详情
     * @param fieldId 字段ID
     * @return 响应体
     */
    @GetMapping("/{fieldId}")
    public Answer getFieldDetail(@ID @PathVariable String fieldId) {
        log.info("获取字段详情，字段ID：{}", fieldId);
        LambdaUpdateWrapper<Field> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Field::getId, fieldId);
        Field field = fieldService.getOne(wrapper);
        if (StringUtils.checkValNull(field)) {
            return Answer.fail("字段不存在");
        }
        return Answer.success(field);
    }

    /**
     * 获取字段的四种模型的属性值
     * @param fieldId 字段ID
     * @param attrOprType {@link AttrOprType} 模型类型
     * @return 响应体
     */
    @GetMapping("/{fieldId}/attribute")
    public Answer getFieldAttribute(@ID @PathVariable String fieldId,
                                    @RequestParam("attrOprType") AttrOprType attrOprType) {
        log.info("获取字段的四种模型的属性值，字段ID：{}，操作类型：{}", fieldId, attrOprType.toString());
        Object obj = fieldService.selectFieldAttribute(fieldId, attrOprType);
        if (ObjectUtil.isNull(obj)) {
            return Answer.fail("尚未设置该模型属性");
        }
        return Answer.success(obj);
    }

    /**
     * 设置字段属性值
     * @param fieldId 字段ID
     * @param attributes {@link Map} 请求体键值对
     * @return 响应体
     */
    @PostMapping("/{fieldId}/attribute")
    public Answer setFieldAttribute(@ID @PathVariable String fieldId,
                                    @RequestBody Map<String, Object> attributes) {
        boolean b = fieldService.addOrUpdate(fieldId, attributes);
        if (!b) {
            Answer.fail("操作失败");
        }
        return Answer.success();
    }
}

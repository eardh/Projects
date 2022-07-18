package com.eardh.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.eardh.mapper.EntityMapper;
import com.eardh.model.Answer;
import com.eardh.model.Entity;
import com.eardh.model.Project;
import com.eardh.model.enums.InterFaceType;
import com.eardh.service.IEntityService;
import com.eardh.validate.Crud;
import com.eardh.validate.ID;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Pattern;
import javax.validation.groups.Default;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 实体操作接口
 * @author eardh
 * @date 2022/6/12 19:21
 */
@RestController
@RequestMapping(("/entity"))
@Validated
@Slf4j
public class EntityController {

    @Autowired
    private IEntityService entityService;

    /**
     * 获取实体列表
     * @param projectId 项目ID
     * @param currentPage 当前页面
     * @param pageSize 页面大小
     * @return 响应体
     */
    @GetMapping
    public Answer getEntityList(@ID @RequestParam(value = "projectId") String projectId,
                                @RequestParam(value = "currentPage", required = false) Integer currentPage,
                                @RequestParam(value = "pageSize", defaultValue = "0", required = false) Integer pageSize) {
        log.info("查询项目实体列表，项目ID:{}", projectId);
        List<Entity> entities = entityService.getEntitiesByProjectId(projectId, currentPage, pageSize);
        return Answer.success(entities);
    }

    /**
     * 创建实体
     * @param entity {@link Entity}
     * @return 响应体
     */
    @PostMapping
    public Answer createEntity(@Validated(value = {Crud.Create.class, Default.class}) @RequestBody Entity entity) {
        log.info("{创建实体，项目ID：{}", entity.getProjectId());
        boolean save = entityService.createEntity(entity);
        if (!save) {
            Answer.fail("操作失败");
        }
        return Answer.success(MapUtil.builder().put("entityId", entity.getId()).build());
    }

    /**
     * 更新实体
     * @param entityId 实体ID
     * @param entity {@link Entity}
     * @return 响应体
     */
    @PutMapping("/{entityId}")
    public Answer updateEntity(@ID @PathVariable String entityId,
                               @Validated @RequestBody Entity entity) {
        log.info("{更新实体，实体ID：{}", entityId);
        LambdaUpdateWrapper<Entity> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Entity::getId, entityId);
        boolean update = entityService.update(entity, wrapper);
        if (!update) {
            Answer.fail("操作失败");
        }
        return Answer.success();
    }

    /**
     * 删除实体
     * @param entityId 实体ID
     * @return 响应体
     */
    @DeleteMapping("/{entityId}")
    public Answer removeEntity(@ID @PathVariable String entityId) {
        log.info("{删除实体，实体ID：{}", entityId);
        boolean b = entityService.removeEntity(entityId);
        if (!b) {
            Answer.fail("操作失败");
        }
        return Answer.success();
    }

    /**
     * 获取实体详情
     * @param entityId 实体ID
     * @return 响应体
     */
    @GetMapping("/{entityId}")
    public Answer getEntityDetail(@ID @PathVariable String entityId) {
        log.info("{获取实体的详细信息，实体ID：{}", entityId);
        LambdaUpdateWrapper<Entity> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Entity::getId, entityId);
        Entity entity = entityService.getOne(wrapper);
        if (StringUtils.checkValNull(entity)) {
            return Answer.fail("实体不存在");
        }
        return Answer.success(entity);
    }

    @GetMapping("/{entityId}/ds_docs")
    public Answer getDataStructureDocs(@ID @PathVariable String entityId, HttpServletResponse response) throws IOException {
        log.info("{获取实体的数据结构文档，实体ID：{}", entityId);
        String url = entityService.dataStructureDocs(entityId);
        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        File file = new File(url);
        String encode = URLEncoder.encode(file.getName(), StandardCharsets.UTF_8);
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "attachment;filename=" + encode);
        response.getOutputStream().write(FileUtil.readBytes(url));
        return Answer.success();
    }

    @GetMapping("/{entityId}/interface_list")
    public Answer getInterfaceList(@ID @PathVariable String entityId) {
        log.info("{获取实体的接口列表，实体ID：{}", entityId);
        return Answer.success(entityService.interfaceList(entityId));
    }

    @GetMapping("/{entityId}/interface_docs")
    public Answer getInterfaceDocs(@ID @PathVariable String entityId,
                                   @RequestParam(value = "interfaceType") InterFaceType interFaceType) throws Exception {
        log.info("获取接口文档，实体ID：{}，接口类型：{} ", entityId, interFaceType.toString());
        String docs = entityService.interfaceDocs(entityId, interFaceType);
        if (ObjectUtil.isNull(docs)) {
            throw new Exception("文档生成异常");
        }
        return Answer.success(docs);
    }
}

package com.eardh.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.PathUtil;
import cn.hutool.core.map.MapBuilder;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.PageUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eardh.config.exception.DataOprException;
import com.eardh.config.exception.IllegalOprException;
import com.eardh.mapper.EntityMapper;
import com.eardh.mapper.FieldMapper;
import com.eardh.mapper.ProjectMapper;
import com.eardh.model.*;
import com.eardh.model.enums.DataType;
import com.eardh.model.enums.InterFaceType;
import com.eardh.service.IEntityService;
import com.eardh.service.IFieldService;
import com.eardh.utils.Constant;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.extern.slf4j.Slf4j;
import net.steppschuh.markdowngenerator.MarkdownBuilder;
import net.steppschuh.markdowngenerator.rule.HorizontalRule;
import net.steppschuh.markdowngenerator.table.Table;
import net.steppschuh.markdowngenerator.text.Text;
import net.steppschuh.markdowngenerator.text.TextBuilder;
import net.steppschuh.markdowngenerator.text.emphasis.BoldText;
import net.steppschuh.markdowngenerator.text.heading.Heading;
import org.apache.ibatis.javassist.ClassPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.sql.JDBCType;
import java.sql.SQLType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author eardh
 * @date 2022/6/12 19:06
 */
@Service
@Slf4j
public class EntityServiceImpl extends ServiceImpl<EntityMapper, Entity> implements IEntityService {

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private FieldMapper fieldMapper;

    @Autowired
    private IFieldService fieldService;

    @Override
    public List<Entity> getEntitiesByProjectId(String projectId, Integer currentPage, Integer pageSize) {
        Page<Entity> page = null;
        if (pageSize != 0) {
            page = new Page<>(currentPage, pageSize);
        }
        return entityMapper.queryAllOrPageByProjectId(page, projectId);
    }

    @Override
    public boolean createEntity(Entity entity) {
        String projectId = entity.getProjectId();
        LambdaQueryWrapper<Project> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Project::getId, projectId);
        if (!projectMapper.exists(wrapper)) {
            throw new IllegalOprException("操作尚未存在的项目");
        }
        return entityMapper.insert(entity) == 1;
    }

    @Override
    public boolean removeEntity(String entityId) {
        LambdaQueryWrapper<Entity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Entity::getId, entityId);
        int i = entityMapper.deleteById(entityId);
        if (i == 0) {
            throw new DataOprException("实体删除异常");
        }

        LambdaQueryWrapper<Field> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.eq(Field::getEntityId, entityId)
                .select(Field::getId);
        List<Field> fields = fieldMapper.selectList(wrapper1);
        for (Field field : fields) {
            fieldService.removeField(field.getId());
        }
        return true;
    }

    @Override
    public String dataStructureDocs(String entityId) {
        Entity entity = getEntityWithField(entityId);
        String docUrl = Constant.CLASSPATH  + "/temp/" + entity.getEntityName() + "_" + System.currentTimeMillis() + ".md";
        log.info("生成临时文件 {}", docUrl);
        FileUtil.writeBytes(generateMarkdown(entity).getBytes(StandardCharsets.UTF_8), new File(docUrl));
        return docUrl;
    }

    @Override
    public List<Map<Object, Object>> interfaceList(String entityId) {
        Entity entity = getEntityWithField(entityId);
        List<Map<Object, Object>> list = new ArrayList<>();
        list.add(MapUtil.builder()
                .put("interfaceName", entity.getEntityName() + "新增接口")
                .put("interfaceType", InterFaceType.CREATE.toString()).build());
        list.add(MapUtil.builder().put("interfaceName", entity.getEntityName() + "更新接口")
                .put("interfaceType", InterFaceType.UPDATE.toString()).build());
        list.add(MapUtil.builder().put("interfaceName", entity.getEntityName() + "删除接口")
                .put("interfaceType", InterFaceType.DELETE.toString()).build());
        list.add(MapUtil.builder().put("interfaceName", entity.getEntityName() + "查询接口")
                .put("interfaceType", InterFaceType.QUERY.toString()).build());
        boolean p = false;
        for (Field field : entity.getFields()) {
            if (!ObjectUtil.isNull(field.getOomAttribute().getAssoProgress())) {
                p = true;
            }
        }
        if (p) {
            list.add(MapUtil.builder().put("interfaceName", entity.getEntityName() + "进度变更接口")
                    .put("interfaceType", InterFaceType.PROGRESS_CHANGE.toString()).build());
        }
        return list;
    }

    @Override
    public String interfaceDocs(String entityId, InterFaceType interFaceType) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, FileNotFoundException {
        Entity entity = getEntityWithField(entityId);
        String r;
        switch (interFaceType) {
            case CREATE:
                r = addReq(entity);
                break;
            case UPDATE:
                r = updateReq(entity);
                break;
            case DELETE:
                r = deleteReq(entity);
                break;
            case QUERY:
                r = queryReq(entity);
                break;
            case PROGRESS_CHANGE:
                r = progressChange(entity);
                break;
            default:
                throw new IllegalOprException("枚举错误");
        }
        return r;
    }

    private Entity getEntityWithField(String entityId) {
        LambdaQueryWrapper<Entity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Entity::getId, entityId);
        Entity entity = entityMapper.selectOne(wrapper);
        if (ObjectUtil.isNull(entity)) {
            throw new IllegalOprException("操作尚未存在的实体");
        }
        List<Field> fieldList = fieldMapper.getFieldWithAttributeByEntityId(entityId);
        entity.setFields(fieldList);
        return entity;
    }

    private String generateMarkdown(Entity entity) {
        Table.Builder tableBuilder = new Table.Builder()
                .withAlignment(Table.ALIGN_CENTER)
                .addRow("字段名", "说明", "类型", "非空", "默认值", "外键");

        Table.Builder onlyOne = new Table.Builder()
                .withAlignment(Table.ALIGN_CENTER)
                .addRow("字段名", "相关约束字段");

        List<Field> fields = entity.getFields();
        for (Field field : fields) {
            LdmAttribute ldmAttribute = field.getLdmAttribute();
            PdmAttribute pdmAttribute = field.getPdmAttribute();
            tableBuilder.addRow(pdmAttribute.getCodeName(),
                    field.getDescription(),
                    pdmAttribute.getDataType().toString().toLowerCase() + "(" + pdmAttribute.getDataLength() + ")",
                    pdmAttribute.getNotNull(),
                    pdmAttribute.getDefaultValue(),
                    pdmAttribute.getIsForeignKey() ? "物理外键" : (ldmAttribute.getIsForeignKey() ? "逻辑外键" : ""));
            if (pdmAttribute.getIsUnique()) {
                onlyOne.addRow(pdmAttribute.getCodeName(), "仅自身");
            }
        }
        MarkdownBuilder<TextBuilder, Text> builder = new TextBuilder();
        builder.append(new Heading(entity.getEntityName() + "数据结构文档", 2)).newLine()
                .append(new Heading("数据结构", 3)).newLine()
                .append(tableBuilder.build())
                .newLines(4)
                .append(new Heading("唯一约束", 3)).newLine()
                .append(onlyOne.build().toString());
        return builder.toString();
    }

    private String addReq(Entity entity) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String url = "http://localhost:port/" + entity.getTableName();
        MarkdownBuilder<TextBuilder, Text> builder = interfaceDocsMD(entity.getFields(),
                OomAttribute.class.getMethod("getAddReq"),
                url,
                entity.getEntityName() + "新增接口",
                "POST",
                "application/json");
        Table.Builder response = new Table.Builder()
                .withAlignment(Table.ALIGN_CENTER)
                .addRow("参数名", "参数类型", "参数描述")
                .addRow("code", "Integer", "业务码")
                .addRow("message", "String", "提示消息")
                .addRow("data", "Object", "响应数据");
        builder.append(response.build());
        return builder.toString();
    }

    private String updateReq(Entity entity) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String url = "http://localhost:port/" + entity.getTableName();
        MarkdownBuilder<TextBuilder, Text> builder = interfaceDocsMD(entity.getFields(),
                OomAttribute.class.getMethod("getUpdateReq"),
                url,
                entity.getEntityName() + "更新接口",
                "PUT",
                "application/json");
        Table.Builder response = new Table.Builder()
                .withAlignment(Table.ALIGN_CENTER)
                .addRow("参数名", "参数类型", "参数描述")
                .addRow("code", "Integer", "业务码")
                .addRow("message", "String", "提示消息")
                .addRow("data", "Object", "响应数据");
        builder.append(response.build());
        return builder.toString();
    }

    private String deleteReq(Entity entity) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String url = "http://localhost:port/" + entity.getTableName();
        MarkdownBuilder<TextBuilder, Text> builder = interfaceDocsMD(entity.getFields(),
                OomAttribute.class.getMethod("getDeleteReq"),
                url,
                entity.getEntityName() + "删除接口",
                "DELETE",
                "none");
        Table.Builder response = new Table.Builder()
                .withAlignment(Table.ALIGN_CENTER)
                .addRow("参数名", "参数类型", "参数描述")
                .addRow("code", "Integer", "业务码")
                .addRow("message", "String", "提示消息")
                .addRow("data", "Object", "响应数据");
        builder.append(response.build());
        return builder.toString();
    }

    private String queryReq(Entity entity) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String url = "http://localhost:port/" + entity.getTableName();
        MarkdownBuilder<TextBuilder, Text> builder = interfaceDocsMD(entity.getFields(),
                OomAttribute.class.getMethod("getQueryReq"),
                url,
                entity.getEntityName() + "查询接口",
                "GET",
                "none");
        Table.Builder response = new Table.Builder()
                .withAlignment(Table.ALIGN_CENTER)
                .addRow("参数名", "参数类型", "参数描述")
                .addRow("code", "Integer", "业务码")
                .addRow("message", "String", "提示消息")
                .addRow("data", "Object", "响应数据");
        for (Field field : entity.getFields()) {
            PdmAttribute pdmAttribute = field.getPdmAttribute();
            String s = Constant.javaType.get(pdmAttribute.getDataType().toString());
            response.addRow("data." + pdmAttribute.getCodeName(),
                    s != null ? s : "" ,
                    field.getDescription());
        }
        builder.append(response.build());
        return builder.toString();
    }

    private String progressChange(Entity entity) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String url = "http://localhost:port/" + entity.getTableName() + "/schedule";
        MarkdownBuilder<TextBuilder, Text> builder = interfaceDocsMD(entity.getFields(),
                OomAttribute.class.getMethod("getProgressReq"),
                url,
                entity.getEntityName() + "进度更新接口",
                "PUT",
                "application/json");
        Table.Builder response = new Table.Builder()
                .withAlignment(Table.ALIGN_CENTER)
                .addRow("参数名", "参数类型", "参数描述")
                .addRow("code", "Integer", "业务码")
                .addRow("message", "String", "提示消息")
                .addRow("data", "Object", "响应数据");
        builder.append(response.build());
        return builder.toString();
    }

    private MarkdownBuilder<TextBuilder, Text> interfaceDocsMD(List<Field> fields, Method method, String url, String interfaceName, String type, String contentType) throws InvocationTargetException, IllegalAccessException {
        Table.Builder tableBuilder = new Table.Builder()
                .withAlignment(Table.ALIGN_CENTER)
                .addRow("参数名", "参数类型", "是否必填", "参数描述");
        for (Field field : fields) {
            Boolean b = (Boolean) method.invoke(field.getOomAttribute());
            DataType dataType = field.getPdmAttribute().getDataType();
            tableBuilder.addRow(field.getPdmAttribute().getCodeName(),
                    Constant.javaType.get(dataType) != null ? dataType : "String",
                    b ? "是" : "否",
                    field.getDescription());
        }
        MarkdownBuilder<TextBuilder, Text> builder = new TextBuilder();
        builder.append(new Heading(interfaceName, 2)).newLine()
                .append(new Heading("基本信息", 3)).newLine()
                .beginList()
                .append(new BoldText("接口URL：") + "`" + url + "`")
                .append(new BoldText("请求方式：") + "`" + type + "`")
                .append(new BoldText("Content-Type：") + "`" + contentType + "`")
                .end()
                .newLines(4)
                .append(new Heading("请求参数", 3)).newLine()
                .append(tableBuilder.build())
                .newLines(4)
                .append(new Heading("响应参数", 3)).newLine();
        return builder;
    }
}

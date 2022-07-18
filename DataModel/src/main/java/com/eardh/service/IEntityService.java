package com.eardh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eardh.model.Entity;
import com.eardh.model.enums.InterFaceType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * 实体服务层
 * @author eardh
 * @date 2022/6/11 22:32
 */
@Transactional(propagation= Propagation.REQUIRED)
public interface IEntityService extends IService<Entity> {

    /**
     * 获取项目下的实体
     * @param projectId 实体所属的项目
     * @param currentPage 当前页码
     * @param pageSize 页面大小 0 - 表示查询全部
     * @return 实体列表
     */
    List<Entity> getEntitiesByProjectId(String projectId, Integer currentPage, Integer pageSize);

    /**
     * 创建实体
     * @param entity {@link Entity}
     * @return 是否创建成功
     */
    boolean createEntity(Entity entity);

    /**
     * 删除实体，级联删除字段及属性
     * @param entityId 实体ID
     * @return 是否删除成功
     */
    boolean removeEntity(String entityId);

    /**
     * 获取数据结构文档
     * @param entityId 实体ID
     * @return 生成的文件url
     */
    String dataStructureDocs(String entityId) throws FileNotFoundException;

    /**
     * 获取接口列表
     * @param entityId 接口ID
     * @return 接口名列表
     */
    List<Map<Object, Object>> interfaceList(String entityId);

    /**
     * 获取接口文档
     * @param entityId 实体ID
     * @param interFaceType 接口类型
     * @return 接口文档文本串
     */
    String interfaceDocs(String entityId, InterFaceType interFaceType) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, FileNotFoundException;
}
